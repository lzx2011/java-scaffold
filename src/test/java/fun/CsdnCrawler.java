package fun;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.jsoup.Jsoup;

/**
 * 参考代码:https://github.com/cighao/csdn
 * ClassName: CsdnCrawler <br/>
 * Function: <br/>
 *
 * @author gary.liu
 * @date 2017/4/19
 */
public class CsdnCrawler {

    public static final String CSDN_DOMAIN = "http://blog.csdn.net";
    public static final String NICK_NAME = "revitalizing";

    /**
     * 获取总页数
     * @param userName
     * @return
     */
    public static int getTotalPageNum(String userName) {
        int totalPageNum = 0;
        String url = CSDN_DOMAIN + "/" + userName; // 博客主页
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpGet httpget = new HttpGet(url);
        httpget.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; rv:6.0.2) Gecko/20100101 Firefox/6.0.2");
        try {
            HttpResponse response = httpclient.execute(httpget);
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                java.io.InputStream is = entity.getContent();
                InputStreamReader isr = new InputStreamReader(is);
                BufferedReader br = new BufferedReader(isr);
                String info = null;
                String pattern = "<span>.*?条.*?共(.*?)页</span>"; // 获取总页数
                Pattern r = Pattern.compile(pattern);
                while ((info = br.readLine()) != null) { // 循环读取页面信息
                    // System.out.println(info);
                    Matcher m = r.matcher(info);
                    if (m.find()) {
                        totalPageNum = Integer.parseInt(m.group(1));
                        break;
                    }
                }
                br.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {

        }

        return totalPageNum;

    }

    /**
     * 获取每页的文章url和标题
     * @param userName
     * @param num
     * @return
     * @throws Exception
     */
    public static Map<String, String> getArticleByPage(String userName, int num) throws Exception {
        Map<String, String> articles = new HashMap<>();
        String url = CSDN_DOMAIN + "/" + userName + "/article/list/" + num; // 第num页文章
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
        //String pattern = "<span class=\"link_title\"><a href=\"/" + userName + "/article/details/(.*?)\">"
        //		+ "\n(.*?)\n";
        String pattern = "<h3 class=\"list_c_t\"><a href=\"/" + userName + "/article/details/(.*?)\">"
                + "(.*?)</a>";
        Pattern r = Pattern.compile(pattern);
        Matcher m = r.matcher(content);
        while (m.find()) {
            String id = m.group(1);
            String articleURL = CSDN_DOMAIN + "/" + NICK_NAME + "/article/details/" + id;
            String title = m.group(2);
            title = title.trim();
            articles.put(articleURL, title);
        }
        return articles;
    }

    public static String downloadPage1(String url) throws IOException{
        String page;
        page = Jsoup.connect(url).get().html();
        return page;
    }

    public static void main(String[] args) throws Exception{
        String userName = "lzx_2011";
        //System.out.println(getTotalPageNum(userName));
        //Map<String, String> map = getArticleByPage(userName, 1);
        //for(Map.Entry entry: map.entrySet()){
        //   System.out.println(entry.getKey());
        //   System.out.println(entry.getValue());
        //}
        //String url = "http://blog.csdn.net/lzx_2011/article/details/55053195";  //302会重定向
        //String url = "http://blog.csdn.net/revitalizing/article/details/55053195";
        //String content = downloadPage(url);
        //writeToFile(content, "mytest");

        //formal
        int totalPageNum = getTotalPageNum(userName);
        for(int i = 1; i < totalPageNum+1; i++){
            Map<String, String> pageMap = getArticleByPage(userName, i);
            DownloadPageThread downloadPageThread = new DownloadPageThread(pageMap);
            downloadPageThread.start();
        }

    }
}

class DownloadPageThread extends Thread{

    public static final String BLOG_BACKUP_DIR = "/Users/gary/Documents/Life/Fun/Blog/csdn/csdnBackup";
    private Map<String, String> pageMap;

    public DownloadPageThread(Map<String, String> pageMap){
        this.pageMap = pageMap;
    }

    public void run(){
        try{
            for(Map.Entry<String, String> map : pageMap.entrySet()){
                String content = downloadPage(map.getKey());
                writeToFile(content, map.getValue());
                Thread.sleep(1000);
            }

        }catch (IOException e){
            e.printStackTrace();
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }

    public String downloadPage(String url) throws IOException{
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
                //content.append(content).append(info).append("\n");  //outOfMemory
            }
        }

        return content;
    }

    public void writeToFile(String content, String fileName){
        String file = BLOG_BACKUP_DIR.concat("/").concat(fileName).concat(".html");
        try(PrintWriter out = new PrintWriter(file)){
            out.println(content);
        }catch (FileNotFoundException e){
            e.printStackTrace();
        }
    }
}
