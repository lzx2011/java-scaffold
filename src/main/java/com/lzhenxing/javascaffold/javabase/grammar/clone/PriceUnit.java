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
     * ����
     */
    @Getter
    @Setter
    private Date date;

    /**
     * ��ҹ�۸��Է�Ϊ��λ
     */
    @Getter
    @Setter
    private Long price;

}