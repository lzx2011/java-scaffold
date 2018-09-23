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
     * ��Ӧ��id
     * ��������Żݲ�ѯ�ش���
     */
    @Getter
    @Setter
    private String supplierId;

    /**
     * ���ҾƵ�id
     * �Żݲ�ѯ�ش�
     */
    @Getter
    @Setter
    private Long hid;

    /**
     * ��ƷICid
     * �Żݲ�ѯ�ش�(����ʾƵ����Ʒ����س������Բ���)
     */
    @Getter
    @Setter
    private Long itemId;

    /**
     * RatePlan�۸�ƻ�id
     * �Żݲ�ѯ�ش�(����ʾƵ����Ʒ����س������Բ���)
     */
    @Getter
    @Setter
    private Long rpId;

    /**
     * RateID
     * �Żݲ�ѯ�ش�(����ʾƵ����Ʒ����س������Բ���)
     */
    @Getter
    @Setter
    private Long rateId;

    /**
     * ��׼�Ƶ�id
     * �Żݲ�ѯ�ش�
     */
    @Getter
    @Setter
    private Long shid;

    /**
     * ��׼����id
     * �Żݲ�ѯ�ش�
     */
    @Getter
    @Setter
    private Long srid;

    /**
     * ��ά��(���ҡ��Ƶꡢrp��rate��)ҵ���ǩ����
     * �Żݲ�ѯ�ش�,Ԥ��ۺͱ�����贫��
     */
    @Getter
    @Setter
    private Set<String> businessTags;

    /**
     * ֧�����ͣ�1Ԥ����5�渶��6��
     * �Żݲ�ѯ�ش�
     */
    @Getter
    @Setter
    private Integer paymentType;

    /**
     * ��ס��������ڵ�ÿ��ļ۸�
     * �Żݲ�ѯ�ش�,Ԥ��ۺͱ�۱ش�
     * ���Ԥ����䷿�漰��rate������ÿ�䷿�������۸���贫�ݣ���������ƥ��
     */
    @Getter
    @Setter
    private List<List<PriceUnit>> prices;

}

