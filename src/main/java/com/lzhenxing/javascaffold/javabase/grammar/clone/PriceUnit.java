package com.lzhenxing.javascaffold.javabase.grammar.clone;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

/**
 *
 * ClassName: PriceUnit <br/>
 * Function: <br/>
 *
 * @author zhenxing.liu
 * @date 2018/8/19
 */
public class PriceUnit {

    private static final long serialVersionUID = -1L;

    /**
     * 日期
     */
    @Getter
    @Setter
    private Date date;

    /**
     * 间夜价格，以分为单位
     */
    @Getter
    @Setter
    private Long price;

}