package com.lzhenxing.javascaffold.util.json;

import java.lang.reflect.Type;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.Date;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.parser.JSONToken;
import com.alibaba.fastjson.parser.deserializer.AbstractDateDeserializer;
import com.alibaba.fastjson.parser.deserializer.ObjectDeserializer;

/**
 *
 * ClassName: TimestampDeserializer <br/>
 * Function: implement by company<br/>
 *
 * @author zhenxing.liu
 * @date 2018/3/8
 */
public class TimestampDeserializer extends AbstractDateDeserializer implements ObjectDeserializer {

    public final static TimestampDeserializer instance = new TimestampDeserializer();

    @SuppressWarnings("unchecked")
    @Override
    protected <T> T cast(DefaultJSONParser parser, Type clazz, Object fieldName, Object val) {

        if (val == null) {
            return null;
        }

        if (val instanceof Date) {
            return (T) new Timestamp(((Date) val).getTime());
        }

        if (val instanceof Number) {
            return (T) new Timestamp(((Number) val).longValue());
        }

        if (val instanceof String) {
            String strVal = (String) val;
            if (strVal.length() == 0) {
                return null;
            }

            DateFormat dateFormat = parser.getDateFormat();
            try {
                Date date = (Date) dateFormat.parse(strVal);
                return (T) new Timestamp(date.getTime());
            } catch (ParseException e) {
                // skip
            }

            long longVal = Long.parseLong(strVal);
            return (T) new Timestamp(longVal);
        }

        throw new JSONException("parse error");
    }

    @Override
    public int getFastMatchToken() {
        return JSONToken.LITERAL_INT;
    }
}
