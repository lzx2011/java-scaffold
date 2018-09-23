package com.lzhenxing.javascaffold.util.json;

import java.lang.reflect.Type;
import java.util.Date;

import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.parser.deserializer.ObjectDeserializer;

/**
 *
 * ClassName: TimestampValueDeserializer <br/>
 * Function: 时间戳转date反序列化<br/>
 *
 * @author zhenxing.liu
 * @date 2018/3/8
 */
public class TimestampValueDeserializer implements ObjectDeserializer {

    @Override
    public Date deserialze(DefaultJSONParser parser, Type type, Object fieldName) {
        long timestamp = parser.getLexer().longValue();
        return new Date(timestamp);

    }

    @Override
    public int getFastMatchToken() {
        return 0;
    }

}
