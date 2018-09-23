package com.lzhenxing.javascaffold.crawler;

import java.io.IOException;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

/**
 *
 * ClassName: CrawlerTest <br/>
 * Function: <br/>
 *
 * @author zhenxing.liu
 * @date 2018/1/30
 */
public class CrawlerTest {

    public static void main(String[] args) {
        //获取配置参数
        String lotteryNo = "2017-03-08";
        String Multiunm = "1";
        String minhit = "0";
        String maxhit = "0";
        String type = "SportterySoccerMix";
        String matches = "3002:16;3003:15,11;3004:14,13;3005:11,13";
        String absmatche = "";
        String passTypestr = "4_1,2_1,3_1";
        try {
            //jsoup向指定页面发送post请求
            postOk1(lotteryNo, Multiunm, minhit, maxhit, type, matches, absmatche, passTypestr);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String postOk(String lotteryNo,String Multiunm ,String minhit,String maxhit,String type ,String matches,String absmatche,String passTypestr ) throws IOException{
        //获取请求连接
        Connection con = Jsoup.connect("http://www.okooo.com/Lottery06/SportterySoccer/ajax.php?action=getLotteryBonus").userAgent("Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.31 (KHTML, like Gecko) Chrome/26.0.1410.64 Safari/537.31");
        //发送参数
        con.data("LotteryNo", "2017-03-08");
        con.data("MultiNum",Multiunm);
        con.data("MinHit", minhit);
        con.data("MaxHit", maxhit);
        con.data("type",type);
        con.data("Matches",matches);
        con.data("AbsMatches", absmatche);
        con.data("PassTypeStr", passTypestr);
        Document doc = con.post();
        //将获取到的内容打印出来
        System.out.println(doc.body().text());
        return doc.toString();
    }

    public static String postOk1(String lotteryNo,String Multiunm ,String minhit,String maxhit,String type ,String matches,String absmatche,String passTypestr ) throws IOException{
        //获取请求连接
        Connection con = Jsoup.connect("http://hotels.ctrip.com/international/Tool/AjaxHotelRoomInfoDetailPart.aspx").userAgent("Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.31 (KHTML, like Gecko) Chrome/26.0.1410.64 Safari/537.31").ignoreContentType(true);
        //发送参数
        //con.data("LotteryNo", "2017-03-08");
        //con.data("MultiNum",Multiunm);
        //con.data("MinHit", minhit);
        //con.data("MaxHit", maxhit);
        //con.data("type",type);
        //con.data("Matches",matches);
        //con.data("AbsMatches", absmatche);
        //con.data("PassTypeStr", passTypestr);

        con.data("City", String.valueOf(65189));
        con.data("Hotel", String.valueOf(7191725));
        //con.data("Content-Type", "application/json; charset=utf-8");

        Document doc = con.post();
        //将获取到的内容打印出来
        System.out.println(doc.body().text());
        return doc.toString();
    }
}
