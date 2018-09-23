package com.lzhenxing.javascaffold.javabase.grammar.clone;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

/**
 *
 * ClassName: BookingInfo <br/>
 * Function: <br/>
 *
 * @author zhenxing.liu
 * @date 2018/8/19
 */
public class BookingInfo {

    private static final long serialVersionUID = -1L;

    /**
     * 入住时间
     * 优惠查询必传,预变价和变价必传
     */
    @Getter
    @Setter
    private Date checkIn;

    /**
     * 离店时间
     * 优惠查询必传,预变价和变价必传
     */
    @Getter
    @Setter
    private Date checkOut;

    /**
     * 入住晚数
     * 优惠查询必传,预变价和变价必传
     */
    @Getter
    @Setter
    private Integer nights;

    /**
     * 预定房间数
     * 优惠查询必传,预变价和变价必传
     */
    @Getter
    @Setter
    private Integer rooms;

    /**
     * 商品信息
     */
    @Getter
    @Setter
    private CommodityInfo commodityInfo;

    /**
     * 总价，单位分
     * 优惠查询必传,预变价和变价必传
     */
    @Getter
    @Setter
    private Long totalRoomPrice;

    /**
     * 杂费金额，单位：分
     * 优惠查询必传,预变价和变价必传
     */
    @Getter
    @Setter
    private Long totalIncidentalPrice;

    /**
     * 入住人是否包含本人
     */
    @Getter
    @Setter
    private boolean isContainSelf = true;

    /**
     * 外币价格是否变低  1 变低 0 没有变低
     * 预变价使用字段
     */
    @Getter
    @Setter
    private Short foreignPriceDrop;

}