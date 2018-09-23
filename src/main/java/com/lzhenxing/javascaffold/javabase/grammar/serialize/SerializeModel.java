package com.lzhenxing.javascaffold.javabase.grammar.serialize;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.google.common.collect.Lists;
import com.google.gson.Gson;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.List;

/**
 * ClassName: SerializeModel <br/>
 * Function: 循环引用序列化<br/>
 * 循环引用：包括自循环，如本类和A、B互相引用 （都是类设计的不好啊）
 *
 * stackOverflowError分析原因：当序列化引擎解析map1时，它发现这个对象持有一个map2的引用，转而去解析map2。解析map2时，发现他又持有map1的引用，又转回map1。如此产生StackOverflowError异常。
 *
 * @author gary.liu
 * @date 2018/9/23
 */
public class SerializeModel {

    private int id;

    private List<SerializeModel> models;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<SerializeModel> getModels() {
        return models;
    }

    public void setModels(List<SerializeModel> models) {
        this.models = models;
    }

    public String toString(){
        return ToStringBuilder.reflectionToString(this);
    }

    public static void main(String[] args) {
        List<SerializeModel> models = Lists.newArrayList();
        SerializeModel model = new SerializeModel();
        model.setId(1);
        models.add(model);
        model.setModels(models);

        System.out.println(ToStringBuilder.reflectionToString(model));
        System.out.println(JSON.toJSONString(model));
        System.out.println(JSON.toJSONString(ToStringBuilder.reflectionToString(model)));

        //下面会报stackoverflow error
        System.out.println(new Gson().toJson(model));
        System.out.println(JSON.toJSON(model));
        System.out.println(JSON.toJSONString(model, SerializerFeature.DisableCircularReferenceDetect));

    }

}
