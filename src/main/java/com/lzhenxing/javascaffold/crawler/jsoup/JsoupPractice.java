package com.lzhenxing.javascaffold.crawler.jsoup;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 *
 * ClassName: JsoupPractice <br/>
 * Function: Jsoup usage practice<br/>
 *
 * @author zhenxing.liu
 * @date 2018/2/10
 */
public class JsoupPractice {

    public static void doGet() throws Exception{
        Document doc = Jsoup.connect("https://www.baidu.com/").get();
        Elements elements = doc.select("div#u1").first().getElementsByTag("a");
        for (Element element : elements) {
            String href = element.attr("href");
            String text = element.text();
            System.out.println(href + " " + text);
        }
    }

    public static void doPost(){

    }


    public static void main(String[] args) throws Exception{
        doGet();

    }
}
