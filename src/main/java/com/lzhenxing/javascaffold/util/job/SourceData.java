package com.lzhenxing.javascaffold.util.job;

import java.util.Map;

/**
 *
 * ClassName: SourceData <br/>
 * Function: <br/>
 *
 * @author zhenxing.liu
 * @date 2019/9/9
 */
public class SourceData {

    private Map<String, Source> dataMap;

    //private String key;
    //
    //private Source source;

    //public String getKey() {
    //    return key;
    //}
    //
    //public void setKey(String key) {
    //    this.key = key;
    //}
    //
    //public Source getSource() {
    //    return source;
    //}
    //
    //public void setSource(Source source) {
    //    this.source = source;
    //}

    static class Source{
        String dateType;
        Object value;

        public String getDateType() {
            return dateType;
        }

        public void setDateType(String dateType) {
            this.dateType = dateType;
        }

        public Object getValue() {
            return value;
        }

        public void setValue(Object value) {
            this.value = value;
        }
    }
}
