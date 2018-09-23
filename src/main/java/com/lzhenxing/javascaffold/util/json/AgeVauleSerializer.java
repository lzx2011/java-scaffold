package com.lzhenxing.javascaffold.util.json;

import java.io.IOException;
import java.lang.reflect.Type;

import com.alibaba.fastjson.serializer.JSONSerializer;
import com.alibaba.fastjson.serializer.ObjectSerializer;

/**
 *
 * ClassName: DateVauleSerializer <br/>
 * Function: �Զ����ֶε����л���ʽ<br/>
 *
 * @author zhenxing.liu
 * @date 2018/3/8
 */
public class AgeVauleSerializer implements ObjectSerializer {

    @Override
    public void write(JSONSerializer serializer, Object object, Object fieldName, Type fieldType,
                      int features) throws IOException {
        Integer value = (Integer) object;
        String text = value + " year-old";
        serializer.write(text);
    }
}
