package com.lzhenxing.javascaffold.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ExecutionException;

import com.lzhenxing.javascaffold.concurrent.ConcurrentTasksExecutor;
import com.lzhenxing.javascaffold.concurrent.TaskExecutor;
import net.rubyeye.xmemcached.MemcachedClient;
import net.rubyeye.xmemcached.MemcachedClientBuilder;
import net.rubyeye.xmemcached.XMemcachedClientBuilder;
import net.rubyeye.xmemcached.utils.AddrUtil;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;

/**
 * ClassName: MemcachedUtils <br/>
 * Function: <br/>
 *
 * @author gary.liu
 * @date 2017/5/13
 */

public class MemcachedUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(MemcachedUtils.class);

    private String servers; // MC服务配置
    private int expire = 604800; // MC失效时间
    private int maxSaveTimes = 1; // 默认最大保存数据到MC的次数
    private int connectTimeout = 500; // 默认连接超时时间
    private int opTimeout = 200; // 默认MC操作超时时间
    private int connectionPoolSize = 1; // 默认连接池大小

    private String[] serverAddrs;
    private MemcachedClient[] clients; // MC连接客户端
    private List<Integer> clientIdList = new ArrayList<Integer>();
    private int serverCount; // mc实例数

    public void init() {
        if (StringUtils.isEmpty(servers)) {
            throw new RuntimeException("Memcached servers is configure null");
        }
        serverAddrs = servers.split(",");
        serverCount = serverAddrs.length;
        clients = new MemcachedClient[serverAddrs.length];
        for (int i = 0; i < serverAddrs.length; i++) {
            MemcachedClientBuilder builder = new XMemcachedClientBuilder(AddrUtil.getAddresses(serverAddrs[i]));
            builder.setName(serverAddrs[i]);
            builder.setOpTimeout(opTimeout);
            builder.setConnectTimeout(connectTimeout);
            builder.setConnectionPoolSize(connectionPoolSize);
            builder.setHealSessionInterval(1000);
            try {
                clientIdList.add(i);
                clients[i] = builder.build();
            } catch (IOException e) {
                throw new RuntimeException("Memcached client is build error, server:" + serverAddrs[i]);
            }
        }
    }

    public boolean setObject(String id, Object object, Species species) {
        return setObject(id, object, expire, species);
    }

    public boolean setObject(final String id, final Object object, final int expire, final Species species) {
        List<Boolean> resultList = null;
        boolean flag = true;
        try {
            resultList = ConcurrentTasksExecutor.saveToMemcached(new TaskExecutor<Integer, Boolean>() {

                @Override
                public Boolean execute(Integer clientId) {
                    return setObject(clientId, id, object, expire, species);
                }

            }, clientIdList);
            if (resultList != null && resultList.size() > 0) {
                for (Boolean result : resultList) {
                    if (!result) {
                        flag = false;
                        break;
                    }
                }
            }
        } catch (InterruptedException | ExecutionException e) {
            LOGGER.error("[MemcachedUtils][setObject][Thread is interrupted] " + e.getMessage(), e);
            return false;
        }
        return flag;
    }

    public boolean setObject(int clientId, String id, Object object, int expire, Species species) {
        for (int i = 0; i < maxSaveTimes; i++) {
            try {
                if (clients[clientId].set(this.getKey(id, species), expire, object)) {
                    return true;
                }
            } catch (Exception e) {
                LOGGER.error("[MemcachedUtils][setObject][key:" + this.getKey(id, species) + "][serverAddr:"
                        + serverAddrs[clientId] + "]" + e.getMessage(), e);
            }
        }
        return false;
    }

    public boolean deleteObject(final String id, final Species species) {
        List<Boolean> resultList = null;
        boolean flag = true;
        try {
            resultList = ConcurrentTasksExecutor.saveToMemcached(new TaskExecutor<Integer, Boolean>() {

                @Override
                public Boolean execute(Integer clientId) {
                    return deleteObject(clientId, id, species);
                }

            }, clientIdList);
            if (resultList != null && resultList.size() > 0) {
                for (Boolean result : resultList) {
                    if (!result) {
                        flag = false;
                        break;
                    }
                }
            }


        } catch (InterruptedException | ExecutionException e) {
            LOGGER.error("[MemcachedUtils][deleteObject][Thread is interrupted] " + e.getMessage(), e);
        }
        return flag;
    }

    private boolean deleteObject(int clientId, String id, Species species) {
        try {
            for (int i = 0; i < maxSaveTimes; i++) {
                if (clients[clientId].delete(this.getKey(id, species))) {
                    return true;
                }
            }
        } catch (Exception e) {
            LOGGER.error("[MemcachedUtils][deleteObject][key:" + this.getKey(id, species) + "][serverAddr:"
                    + serverAddrs[clientId] + "]" + e.getMessage(), e);
        }
        return false;
    }

    public Object getObject(String id, Species species) {

        String key = this.getKey(id, species);

        int clientId = new Random().nextInt(serverCount);

        Object object = null;
        boolean flag = true;
        try {
            object = clients[clientId].get(key);
        } catch (Exception e) {
            flag = false;
            LOGGER.error(
                    "[MemcachedUtils][getObject][key:" + key + "][serverAddr:" + serverAddrs[clientId] + "]"
                            + e.getMessage(), e);
        }

        int nextClientId = this.getNext(serverCount, clientId);
        while (!flag && nextClientId != clientId) {
            try {
                object = clients[nextClientId].get(key);
                flag = true;
            } catch (Exception e) {
                flag = false;
                nextClientId = this.getNext(serverCount, nextClientId);
                LOGGER.error("[MemcachedUtils][getObject][key: " + key + "][serverAddr:" + serverAddrs[nextClientId]
                        + "]" + e.getMessage(), e);
            }
        }
        return object;
    }

    public Map<String, byte[]> getObjectList(List<?> ids, Species species) {

        if (StringUtil.isEmptyList(ids))
            return null;

        List<String> keyList = new ArrayList<String>();
        for (Object id : ids) {
            if (id != null) {
                keyList.add(this.getKey(String.valueOf(id), species));
            }
        }

        int clientId = new Random().nextInt(serverCount);

        Map<String, byte[]> objsMap = null;
        boolean flag = true;
        try {
            objsMap = clients[clientId].get(keyList);
        } catch (Exception e) {
            flag = false;
            LOGGER.error("[MemcachedUtils][getObjectList][keys:" +new Gson().toJson(keyList) + "][serverAddr:"
                    + serverAddrs[clientId] + "]" + e.getMessage(), e);
        }

        int nextClientId = this.getNext(serverCount, clientId);
        while (!flag && nextClientId != clientId) {
            try {
                objsMap = clients[nextClientId].get(keyList);
                flag = true;
            } catch (Exception e) {
                flag = false;
                nextClientId = (int) this.getNext(serverCount, nextClientId);
                LOGGER.error("[MemcachedUtils][getObjectList][key: " + new Gson().toJson(keyList) + "][serverAddr:"
                        + serverAddrs[nextClientId] + "]" + e.getMessage(), e);
            }
        }
        return objsMap;
    }

    public final int getNext(int size, int start) {
        if (start == size - 1) {
            return 0;
        } else {
            return start + 1;
        }
    }

    /**
     * 根据ID获取MC缓存key
     * @param id
     * @param species
     * @return
     */
    public String getKey(String id, Species species) {
        // 因为memcached不支持空格,所以将所有空格转换为+号
        return (String.valueOf(species)+"_"+id).replaceAll("　", "+").replaceAll(" ", "+").replaceAll("\n", "_");
    }

    public enum Species {
        CATEGORY_STANDARD(0); // 分类属性标准化 key:categoryid + brandid +customoption  value: optionid


        private final int value;

        private Species(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }

        public static Species findByValue(int value) {
            switch (value) {
                case 0:
                    return CATEGORY_STANDARD;
                default:
                    return null;
            }
        }
    }




    public void setExpire(int expire) {
        this.expire = expire;
    }

    public int getExpire() {
        return expire;
    }

    public void setMaxSaveTimes(int maxSaveTimes) {
        this.maxSaveTimes = maxSaveTimes;
    }

    public void setConnectTimeout(int connectTimeout) {
        this.connectTimeout = connectTimeout;
    }

    public void setOpTimeout(int opTimeout) {
        this.opTimeout = opTimeout;
    }

    public void setConnectionPoolSize(int connectionPoolSize) {
        this.connectionPoolSize = connectionPoolSize;
    }

    public void setServers(String servers) {
        this.servers = servers;
    }

    public String getServers() {
        return servers;
    }

    public MemcachedClient[] getClients() {
        return clients;
    }

    public List<Integer> getClientIdList() {
        return clientIdList;
    }

}

