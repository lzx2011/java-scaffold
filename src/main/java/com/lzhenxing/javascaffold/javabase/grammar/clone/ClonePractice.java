package com.lzhenxing.javascaffold.javabase.grammar.clone;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.google.common.collect.Lists;
import com.lzhenxing.javascaffold.util.DateUtils;
import com.lzhenxing.javascaffold.util.json.FastJsonUtil;
import org.springframework.beans.BeanUtils;

/**
 *
 * ClassName: ClonePractice <br/>
 * Function: <br/>
 *
 * @author zhenxing.liu
 * @date 2018/8/19
 */
public class ClonePractice {

    /**
     * 这个测试中settleBookInfo中的settlePriceList变了会导致原来的bookPriceList也变了，因为这是浅拷贝
     * 用序列化解决浅拷贝实现深拷贝
     */
    public static void cloneTest(){
        BookingInfo bookingInfo = new BookingInfo();
        List<List<PriceUnit>> bookPriceList = Lists.newArrayList();
        List<PriceUnit> priceUnits = Lists.newArrayList();
        for(int i = 0; i < 3; i++){
            PriceUnit priceUnit = new PriceUnit();
            priceUnit.setDate(new Date());
            priceUnit.setPrice(20L);
            priceUnits.add(priceUnit);
        }
        bookPriceList.add(priceUnits);
        CommodityInfo commodityInfo = new CommodityInfo();
        commodityInfo.setPrices(bookPriceList);
        bookingInfo.setCommodityInfo(commodityInfo);
        bookingInfo.setNights(2);
        bookingInfo.setCheckIn(DateUtils.string2date("2017-06-02", DateUtils.FORMAT_SHORT));

        BookingInfo settleBookInfo = new BookingInfo();
        BeanUtils.copyProperties(bookingInfo, settleBookInfo);
        System.out.println(FastJsonUtil.bean2Json(bookingInfo));

        List<List<PriceUnit>> settlePriceList = Lists.newArrayList();
        List<PriceUnit> settlePriceUnits = Lists.newArrayList();
        settleBookInfo.setCheckIn(DateUtils.string2date("2018-08-02", DateUtils.FORMAT_SHORT));
        settleBookInfo.setNights(5);
        settleBookInfo.getCommodityInfo().getPrices().clear();
        for(int i = 0; i < 3; i++){
            PriceUnit priceUnit = new PriceUnit();
            priceUnit.setDate(new Date());
            priceUnit.setPrice(50L);
            settlePriceUnits.add(priceUnit);
        }
        settlePriceList.add(settlePriceUnits);
        settleBookInfo.getCommodityInfo().setPrices(settlePriceList);

        System.out.println("settlebookInfo: " + FastJsonUtil.bean2Json(settleBookInfo));
        System.out.println("newBookingInfo: " + FastJsonUtil.bean2Json(bookingInfo));
    }

    /**
     * 序列化实现深拷贝
     */
    public static void implmentDeepClone(){
        BookingInfo bookingInfo = new BookingInfo();
        List<List<PriceUnit>> bookPriceList = Lists.newArrayList();
        List<PriceUnit> priceUnits = Lists.newArrayList();
        for(int i = 0; i < 3; i++){
            PriceUnit priceUnit = new PriceUnit();
            priceUnit.setDate(new Date());
            priceUnit.setPrice(20L);
            priceUnits.add(priceUnit);
        }
        bookPriceList.add(priceUnits);
        CommodityInfo commodityInfo = new CommodityInfo();
        commodityInfo.setPrices(bookPriceList);
        bookingInfo.setCommodityInfo(commodityInfo);
        bookingInfo.setNights(2);
        bookingInfo.setCheckIn(DateUtils.string2date("2017-06-02", DateUtils.FORMAT_SHORT));

        //String jsonString = FastJsonUtil.bean2Json(bookingInfo);

        BookingInfo settleBookInfo = new BookingInfo();
        BeanUtils.copyProperties(bookingInfo, settleBookInfo);

        CommodityInfo commodityInfo1 = new CommodityInfo();
        BeanUtils.copyProperties(commodityInfo, commodityInfo1);

        settleBookInfo.setCommodityInfo(commodityInfo1);

        List<List<PriceUnit>> priceUnitList = Lists.newArrayList();

        commodityInfo1.setPrices(priceUnitList);
        //commodityInfo1.setPrices(Lists.newArrayList());

        settleBookInfo.setCommodityInfo(commodityInfo1);

        //BookingInfo settleBookInfo = FastJsonUtil.json2Bean(jsonString, BookingInfo.class);
        System.out.println(FastJsonUtil.bean2Json(bookingInfo));
        //settleBookInfo.getCommodityInfo().getPrices().clear();
        //List<List<PriceUnit>> settlePriceList = Lists.newArrayList();
        //List<PriceUnit> settlePriceUnits = Lists.newArrayList();
        settleBookInfo.setCheckIn(DateUtils.string2date("2018-08-02", DateUtils.FORMAT_SHORT));
        List<PriceUnit> settlePriceUnits = new ArrayList<>();

        settleBookInfo.setNights(5);
        for(int i = 0; i < 3; i++){
            PriceUnit priceUnit = new PriceUnit();
            priceUnit.setDate(new Date());
            priceUnit.setPrice(50L);
            settlePriceUnits.add(priceUnit);
        }
        //priceUnitList.add(settlePriceUnits);
        //commodityInfo1.setPrices(priceUnitList);
        //settleBookInfo.getCommodityInfo().setPrices(priceUnitList);
        settleBookInfo.getCommodityInfo().getPrices().add(settlePriceUnits);

        System.out.println("settlebookInfo: " + FastJsonUtil.bean2Json(settleBookInfo));
        System.out.println("newBookingInfo: " + FastJsonUtil.bean2Json(bookingInfo));


    }


    public static void main(String[] args) {

        //cloneTest();
        implmentDeepClone();
    }
}
