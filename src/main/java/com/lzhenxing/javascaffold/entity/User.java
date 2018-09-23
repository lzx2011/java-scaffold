package com.lzhenxing.javascaffold.entity;

import java.util.Date;
import java.util.List;

import com.alibaba.fastjson.annotation.JSONField;

import com.lzhenxing.javascaffold.util.json.AgeVauleSerializer;
import com.lzhenxing.javascaffold.util.json.FastJsonUtil;
import com.lzhenxing.javascaffold.util.json.TimestampDeserializer;
import com.lzhenxing.javascaffold.util.json.TimestampValueDeserializer;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 *
 * ClassName: User <br/>
 * Function: <br/>
 *
 * @author zhenxing.liu
 * @date 2018/1/22
 */
public class User {

    private String name;

    @JSONField(serializeUsing = AgeVauleSerializer.class)
    private int age;

    private String desc;

    /**
     * �Զ���date�����л���ʽ,ע�����л�ʱҲ�ᱻ���л���ʱ���
     * format="yyyy-MM-dd HH:mm:ss"�������л���ʽ
     */
    @JSONField(deserializeUsing = TimestampValueDeserializer.class)
    //@JSONField(deserializeUsing = TimestampDeserializer.class)  �����ʱ�����ʽ����
    private Date date;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public String toString(){
        return ToStringBuilder.reflectionToString(this);
    }

    public static void main(String[] args){

        //���л�����
        User user = new User();
        user.setDate(new Date());
        user.setName("lily");
        user.setAge(10);
        System.out.println(FastJsonUtil.bean2Json(user));

        //�����л�����
        String json = "[{\"date\":1519747200000,\"name\":'lucy',\"age\":10},{\"date\":1519833600000,\"name\":'lily',"
            + "\"age\":20}]";
        List<User> users = FastJsonUtil.json2BeanList(json, User.class);
        System.out.println(users.toString());
    }
}
