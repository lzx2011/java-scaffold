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
     * ��סʱ��
     * �Żݲ�ѯ�ش�,Ԥ��ۺͱ�۱ش�
     */
    @Getter
    @Setter
    private Date checkIn;

    /**
     * ���ʱ��
     * �Żݲ�ѯ�ش�,Ԥ��ۺͱ�۱ش�
     */
    @Getter
    @Setter
    private Date checkOut;

    /**
     * ��ס����
     * �Żݲ�ѯ�ش�,Ԥ��ۺͱ�۱ش�
     */
    @Getter
    @Setter
    private Integer nights;

    /**
     * Ԥ��������
     * �Żݲ�ѯ�ش�,Ԥ��ۺͱ�۱ش�
     */
    @Getter
    @Setter
    private Integer rooms;

    /**
     * ��Ʒ��Ϣ
     */
    @Getter
    @Setter
    private CommodityInfo commodityInfo;

    /**
     * �ܼۣ���λ��
     * �Żݲ�ѯ�ش�,Ԥ��ۺͱ�۱ش�
     */
    @Getter
    @Setter
    private Long totalRoomPrice;

    /**
     * �ӷѽ���λ����
     * �Żݲ�ѯ�ش�,Ԥ��ۺͱ�۱ش�
     */
    @Getter
    @Setter
    private Long totalIncidentalPrice;

    /**
     * ��ס���Ƿ��������
     */
    @Getter
    @Setter
    private boolean isContainSelf = true;

    /**
     * ��Ҽ۸��Ƿ���  1 ��� 0 û�б��
     * Ԥ���ʹ���ֶ�
     */
    @Getter
    @Setter
    private Short foreignPriceDrop;

}