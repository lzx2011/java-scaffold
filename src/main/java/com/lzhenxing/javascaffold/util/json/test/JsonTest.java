package com.lzhenxing.javascaffold.util.json.test;

import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;

import com.google.common.collect.Lists;
import com.lzhenxing.javascaffold.entity.Manager;
import com.lzhenxing.javascaffold.entity.User;
import org.junit.Test;

/**
 *
 * ClassName: JsonTest <br/>
 * Function: <br/>
 *
 * @author zhenxing.liu
 * @date 2018/3/27
 */
public class JsonTest {

    public static void test(){
        Manager manager = new Manager();
        manager.setName("liu");
        List<User> users = Lists.newArrayList();
        User user = new User();
        user.setName("sf");
        users.add(user);
        User user1 = new User();
        user1.setName("sf");
        users.add(user1);
        manager.setUsers(users);
        //System.out.println(ToStringBuilder.reflectionToString(manager));
        System.out.println(JSON.toJSONString(manager));
    }

    public static void test1(){
        for(int i = 0; i < 1; i++){
            System.out.println(i);
        }
    }

    @Test
    //public void json2Map(){
    //    String test = "{\"compatible\":\"1\",\"subPlatformType\":\"xyz_st1_hb0\",\"PROMOTIONV2\":\"1\","
    //        + "\"overdueAdvanceFlag\":\"1\"}";
    //    Map<String, String> attrMap = JSON.parseObject(test, Map.class);
    //    if(attrMap.containsKey("overdueAdvanceFlag") && CommonConst.ATTRIBUTE_YES.equals(attrMap.get("overdueAdvanceFlag"))){
    //        System.out.println("true");
    //    }
    //}

    public static void main(String[] args) {
        test1();
    }
}
