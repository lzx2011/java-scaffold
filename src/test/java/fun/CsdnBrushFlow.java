package fun;

import com.lzhenxing.javascaffold.util.IpUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * ClassName: CsdnBrushFlow <br/>
 * Function: <br/>
 *
 * @author gary.liu
 * @date 2017/4/19
 */
public class CsdnBrushFlow {
    public static final int TOTAL_PAGE_NUM = 13;
    public static final String USER_NAME = "lzx_2011";
    public static final String CSDN_DOMAIN = "http://blog.csdn.net";
    public static final String NICK_NAME = "revitalizing";
    public static final String COOKIE = "bdshare_firstime=1486719134076; uuid_tt_dd=-382987571716603217_20170210; CNZZDATA1259094876=308187314-1486916862-null%7C1486916862; uuid=12275cb2-eaa2-441c-8d96-f6953d055671; Hm_lvt_6bcd52f51e9b3dce32bec4a3997715ac=1488042210,1488043042,1488043056,1488104936; Hm_lpvt_6bcd52f51e9b3dce32bec4a3997715ac=1488104936; avh=55052856; _ga=GA1.2.1919960375.1488046328; _gat=1; dc_tos=olzlkt; dc_session_id=1488121074926; UserName=lzx_2011; UserInfo=2HGRg1EL5fSH%2BP6LUfRypWlrUUPQ2cs0u9J7a7FcaPjrBwajFFGltXkRBWfPAq9rR%2FuKjl8nv0sQducuRqgzsvwZqEIDZN%2Fsht0OI%2BULQW7Yvx2p1Q3JspYyuHwJD7tZ; UserNick=tarry_1; AU=160; UN=lzx_2011; UE=\"lzx20110@163.com\"; BT=1488121088134; access-token=27dd450e-e6fe-4ee8-b11b-e1c6be968314";

    private static Proxy proxy;
    static {
        SocketAddress sa = new InetSocketAddress("223.19.196.232", 80);
        proxy=new Proxy(java.net.Proxy.Type.HTTP, sa);
    }


    /**
     * 获取每页文章的url
     * @param num
     * @return
     * @throws IOException
     */
    public static List<String> getArticlUrl(int num) throws IOException {
        List<String> urlList = new ArrayList<>();
        String url = CSDN_DOMAIN + "/" + USER_NAME + "/article/list/" + num; // 第num页文章
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpGet httpget = new HttpGet(url);
        httpget.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; rv:6.0.2) Gecko/20100101 Firefox/6.0.2");
        HttpResponse response = httpclient.execute(httpget);
        HttpEntity entity = response.getEntity();
        String content = ""; // 保存整个网页的内容
        //StringBuilder content = new StringBuilder();
        if (entity != null) {
            java.io.InputStream is = entity.getContent();
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);
            String info = null;
            while ((info = br.readLine()) != null) { // 循环读取客户端的信息
                content = content + info + "\n";
                //content.append(content).append(info).append("\n");
            }
            // System.out.println(content);
        }
        // 获取文章url和标题
        String pattern = "<h3 class=\"list_c_t\"><a href=\"/" + USER_NAME + "/article/details/(.*?)\">";
        Pattern r = Pattern.compile(pattern);
        Matcher m = r.matcher(content);
        while (m.find()) {
            String id = m.group(1);
            String articleURL = CSDN_DOMAIN + "/" + NICK_NAME + "/article/details/" + id;
            //String title = m.group(2);
            //title = title.trim();
            //articles.put(articleURL, title);
            urlList.add(articleURL);
        }
        //System.out.println(urlList);
        return urlList;
    }

    /**
     * 获取所有文章url
     * @return
     * @throws IOException
     */
    public static List<String> totalUrlList() throws IOException{
        List<String> totalUrlList = new ArrayList<>();
        for(int i = 1; i < TOTAL_PAGE_NUM + 1; i++){
            totalUrlList.addAll(getArticlUrl(i));
        }
        return totalUrlList;
    }


    /**
     * 随机刷文章多少次
     * @param urlList
     * @param num
     */
    public static void visitArticle(List<String> urlList, int num){
        int size = urlList.size();
        for(int i = 0; i < num; i++){
            int randomNum = ThreadLocalRandom.current().nextInt(0, size);
            int randTime = ThreadLocalRandom.current().nextInt(1000, 2000);
            String url = urlList.get(randomNum);
            try {
                Thread.sleep(randTime);// 间隔一秒
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("visit article：" + url);
            CloseableHttpClient httpclient = HttpClients.createDefault();
            HttpGet httpget = new HttpGet(url);
            httpget.setHeader("User-Agent",
                    "Mozilla/5.0 (Windows NT 6.1; rv:6.0.2) Gecko/20100101 Firefox/6.0.2");
            String ip = IpUtils.getRandomIp();
            httpget.setHeader("X-Forwarded-For", ip);
            httpget.setHeader("CLIENT-IP", ip);
            HttpResponse response = null;
            try {
                response = httpclient.execute(httpget);
            } catch (IOException e) {
                e.printStackTrace();
            }
            HttpEntity entity = response.getEntity();

        }

    }

    /**
     * 使用代理
     * @param url
     */
    public void visitByProxy(String url) {
        try{
            HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection(proxy);
            connection.setRequestProperty("User-Agent", "");
            connection.getInputStream();
            connection.disconnect();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void brushDigg(String diggUrl, String articleUrl){

        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpGet httpget = new HttpGet(diggUrl);
        httpget.setHeader("User-Agent",
                "Mozilla/5.0 (Windows NT 6.1; rv:6.0.2) Gecko/20100101 Firefox/6.0.2");
        httpget.setHeader("Cookie", COOKIE);
        httpget.setHeader("Referer", articleUrl);
        httpget.setHeader("Host", "blog.csdn.net");
        httpget.setHeader("Accept","*/*");
        String ip = IpUtils.getRandomIp();
        httpget.setHeader("X-Forwarded-For", ip);
        httpget.setHeader("CLIENT-IP", ip);
        HttpResponse response = null;
        try {
            response = httpclient.execute(httpget);
        } catch (IOException e) {
            e.printStackTrace();
        }
        HttpEntity entity = response.getEntity();
    }

    public static void brushAllDigg(List<String> urlList){
        for(String url : urlList){
            String id = url.substring(url.lastIndexOf("/")+1);
            String diggUrl = "http://blog.csdn.net/revitalizing/article/digg?ArticleId=".concat(id);
            System.out.println(diggUrl);
            brushDigg(diggUrl, url);
            try{
                Thread.sleep(1000);
            }catch (InterruptedException e){
                e.printStackTrace();
            }
        }
    }


    //http://blog.csdn.net/revitalizing/article/digg?ArticleId=55052856   点赞

    public static void main(String[] args) throws IOException{


        //getArticlUrl(1);
        //刷访问量
        visitArticle(totalUrlList(), 500);
        //刷赞 bbbb
        //String url = "http://blog.csdn.net/revitalizing/article/details/55052856";
        //System.out.println(url.substring(url.lastIndexOf("/")+1));

        //String url = "http://blog.csdn.net/revitalizing/article/digg?ArticleId=55052856";
        //for(int i = 0; i < 1; i++){
        //    brushDigg(url);
        //}
        //brushAllDigg(totalUrlList());
    }
}
