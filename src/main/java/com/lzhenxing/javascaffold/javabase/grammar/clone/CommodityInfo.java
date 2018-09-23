package com.lzhenxing.javascaffold.javabase.grammar.clone;

import java.util.List;
import java.util.Set;

import lombok.Getter;
import lombok.Setter;

/**
 *
 * ClassName: CommodityInfo <br/>
 * Function: <br/>
 *
 * @author zhenxing.liu
 * @date 2018/8/19
 */
public class CommodityInfo {

    private static final long serialVersionUID = -1L;

    /**
     * 供应商id
     * 如果存在优惠查询必传递
     */
    @Getter
    @Setter
    private String supplierId;

    /**
     * 卖家酒店id
     * 优惠查询必传
     */
    @Getter
    @Setter
    private Long hid;

    /**
     * 商品ICid
     * 优惠查询必传(如国际酒店等商品不落地场景可以不传)
     */
    @Getter
    @Setter
    private Long itemId;

    /**
     * RatePlan价格计划id
     * 优惠查询必传(如国际酒店等商品不落地场景可以不传)
     */
    @Getter
    @Setter
    private Long rpId;

    /**
     * RateID
     * 优惠查询必传(如国际酒店等商品不落地场景可以不传)
     */
    @Getter
    @Setter
    private Long rateId;

    /**
     * 标准酒店id
     * 优惠查询必传
     */
    @Getter
    @Setter
    private Long shid;

    /**
     * 标准房型id
     * 优惠查询必传
     */
    @Getter
    @Setter
    private Long srid;

    /**
     * 多维度(卖家、酒店、rp、rate等)业务标签集合
     * 优惠查询必传,预变价和变价无需传递
     */
    @Getter
    @Setter
    private Set<String> businessTags;

    /**
     * 支付类型，1预付，5面付，6后付
     * 优惠查询必传
     */
    @Getter
    @Setter
    private Integer paymentType;

    /**
     * 入住离店日期内的每天的价格
     * 优惠查询必传,预变价和变价必传
     * 针对预付多间房涉及多rate场景，每间房的日历价格均需传递，跟房间数匹配
     */
    @Getter
    @Setter
    private List<List<PriceUnit>> prices;

}

