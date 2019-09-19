package com.lzhenxing.javascaffold.util.job;

import java.lang.reflect.Type;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.alibaba.fastjson.util.ParameterizedTypeImpl;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.lzhenxing.javascaffold.util.json.FastJsonUtil;

/**
 *
 * ClassName: FieldCompare <br/>
 * Function: <br/>
 *
 * @author zhenxing.liu
 * @date 2019/9/9
 */
public class FieldCompare {

    public static String source = "{\"self.NQRPCS\":{\"dataType\":\"Input\",\"value\":\"拟迁入地派出所\"},\"self"
        + ".TYLX\":{\"dataType\":\"Select\"},\"self.YLBXCBLX\":{\"dataType\":\"Select\"},\"self"
        + ".LHLX\":{\"dataType\":\"Select\"},\"self.ZY\":{\"dataType\":\"Select\",\"value\":{\"text\":\"其他\","
        + "\"value\":\"QT\"}},\"self.HKZZ\":{\"dataType\":\"Input\",\"value\":\"户口住址\"},\"self"
        + ".RTSJ\":{\"dataType\":\"Date\",\"value\":1568029440000},\"self.JFJS\":{\"dataType\":\"Input\","
        + "\"value\":\"2而返工后\"},\"self.DYZZSJ\":{\"dataType\":\"Date\",\"value\":1568029440000},\"self"
        + ".YHWD\":{\"dataType\":\"Select\"},\"self.RWSJ\":{\"dataType\":\"Date\",\"value\":1566906240000},\"self"
        + ".LJTHFCLX\":{\"dataType\":\"Select\"},\"self.TYSJ\":{\"dataType\":\"Date\",\"value\":1568029440000},\"self"
        + ".BDLXRLXFS\":{\"dataType\":\"Input\",\"value\":\"123456789\"},\"self.XZDDZ\":{\"dataType\":\"Input\","
        + "\"value\":\"现住地址\"},\"self.JZ\":{\"dataType\":\"Input\",\"value\":\"军种\"},\"self"
        + ".IDCARD\":{\"dataType\":\"Input\",\"value\":\"234567890456789\"},\"self"
        + ".CBD\":{\"dataType\":\"AddressSelect\",\"value\":[{\"text\":\"北京\",\"value\":\"110000\"},"
        + "{\"text\":\"北京市\",\"value\":\"110100\"},{\"text\":\"东城区\",\"value\":\"110101\"}]},\"self"
        + ".RDSJ\":{\"dataType\":\"Date\",\"value\":1568029440000},\"self.SSJMSF\":{\"dataType\":\"Select\"},\"self"
        + ".TYBD\":{\"dataType\":\"Input\",\"value\":\"退役部队\"},\"self.BDLXR\":{\"dataType\":\"Input\","
        + "\"value\":\"部队联系人\"},\"self.NAME\":{\"dataType\":\"Input\",\"value\":\"测试数据\"},\"self"
        + ".WHCD\":{\"dataType\":\"Select\"},\"self.HKSSX\":{\"dataType\":\"AddressSelect\","
        + "\"value\":[{\"text\":\"河北省\",\"value\":\"130000\"},{\"text\":\"唐山市\",\"value\":\"130200\"},"
        + "{\"text\":\"路北区\",\"value\":\"130203\"}]},\"self.BDLXRZW\":{\"dataType\":\"Input\",\"value\":\"职务\"},"
        + "\"self.ZJYXQZ\":{\"dataType\":\"Date\",\"value\":1568029440000},\"self.MZ\":{\"dataType\":\"Select\","
        + "\"value\":{\"text\":\"汉族\",\"value\":\"01\"}},\"self.XB\":{\"dataType\":\"Select\","
        + "\"value\":{\"text\":\"男\",\"value\":\"NAN\"}},\"self.CJSZDDZ\":{\"dataType\":\"Input\",\"value\":\"所在地\"},"
        + "\"self.YHMZ\":{\"dataType\":\"Select\"},\"self.LXDZ\":{\"dataType\":\"Input\",\"value\":\"联系地址\"},\"self"
        + ".SFHYJ\":{\"dataType\":\"Select\"},\"self.ZJYXQQ\":{\"dataType\":\"Date\",\"value\":1568029560000},\"self"
        + ".WDDZ\":{\"dataType\":\"Input\",\"value\":\"网点地址\"},\"self.SSHM\":{\"dataType\":\"Input\","
        + "\"value\":\"18514528944\"},\"self.ZZMM\":{\"dataType\":\"Select\"},\"self.CSRQ\":{\"dataType\":\"Date\","
        + "\"value\":1568115840000},\"self.YBCB\":{\"dataType\":\"Select\",\"value\":{\"text\":\"城乡居民医疗保险登记\","
        + "\"value\":\"CXJMYLBXDJ\"}}}";

    public static String source1 = "{\"self.NQRPCS\":{\"dataType\":\"Input\",\"value\":\"拟迁入地派出所\"},\"self"
        + ".TYLX\":{\"dataType\":\"Select\"},\"self.YLBXCBLX\":{\"dataType\":\"Select\"},\"self"
        + ".LHLX\":{\"dataType\":\"Select\"},\"self.ZY\":{\"dataType\":\"Select\",\"value\":{\"text\":\"其他\","
        + "\"value\":\"QT\"}},\"self.HKZZ\":{\"dataType\":\"Input\",\"value\":\"户口住址\"},\"self"
        + ".RTSJ\":{\"dataType\":\"Date\",\"value\":1568029440000},\"self.JFJS\":{\"dataType\":\"Input\","
        + "\"value\":\"2而返工后\"},\"self.DYZZSJ\":{\"dataType\":\"Date\",\"value\":1568029440000},\"self"
        + ".YHWD\":{\"dataType\":\"Select\"},\"self.RWSJ\":{\"dataType\":\"Date\",\"value\":1566906240000},\"self"
        + ".LJTHFCLX\":{\"dataType\":\"Select\"},\"self.TYSJ\":{\"dataType\":\"Date\",\"value\":1568029440000},\"self"
        + ".BDLXRLXFS\":{\"dataType\":\"Input\",\"value\":\"123456789\"},\"self.XZDDZ\":{\"dataType\":\"Input\","
        + "\"value\":\"现住地址\"},\"self.JZ\":{\"dataType\":\"Input\",\"value\":\"军种\"},\"self"
        + ".IDCARD\":{\"dataType\":\"Input\",\"value\":\"234567890456789\"},\"self"
        + ".CBD\":{\"dataType\":\"AddressSelect\",\"value\":[{\"text\":\"北京\",\"value\":\"110000\"},"
        + "{\"text\":\"北京市\",\"value\":\"110100\"},{\"text\":\"东城区\",\"value\":\"110101\"}]},\"self"
        + ".RDSJ\":{\"dataType\":\"Date\",\"value\":1568029440000},\"self.SSJMSF\":{\"dataType\":\"Select\"},\"self"
        + ".TYBD\":{\"dataType\":\"Input\",\"value\":\"退役部队\"},\"self.BDLXR\":{\"dataType\":\"Input\","
        + "\"value\":\"部队联系人\"},\"self.NAME\":{\"dataType\":\"Input\",\"value\":\"测试数据\"},\"self"
        + ".WHCD\":{\"dataType\":\"Select\"},\"self.HKSSX\":{\"dataType\":\"AddressSelect\","
        + "\"value\":[{\"text\":\"河北省\",\"value\":\"130000\"},{\"text\":\"唐山市\",\"value\":\"130200\"},"
        + "{\"text\":\"路北区\",\"value\":\"130203\"}]},\"self.BDLXRZW\":{\"dataType\":\"Input\",\"value\":\"职务\"},"
        + "\"self.ZJYXQZ\":{\"dataType\":\"Date\",\"value\":1568029440000},\"self.MZ\":{\"dataType\":\"Select\","
        + "\"value\":{\"text\":\"汉族\",\"value\":\"01\"}},\"self.XB\":{\"dataType\":\"Select\","
        + "\"value\":{\"text\":\"男\",\"value\":\"NAN\"}},\"self.CJSZDDZ\":{\"dataType\":\"Input\",\"value\":\"所在地\"},"
        + "\"self.YHMZ\":{\"dataType\":\"Select\"},\"self.LXDZ\":{\"dataType\":\"Input\",\"value\":\"联系地址\"},\"self"
        + ".SFHYJ\":{\"dataType\":\"Select\"},\"self.ZJYXQQ\":{\"dataType\":\"Date\",\"value\":1568029560000},\"self"
        + ".WDDZ\":{\"dataType\":\"Input\",\"value\":\"网点地址\"},\"self.SSHM\":{\"dataType\":\"Input\","
        + "\"value\":\"18514528944\"},\"self.ZZMM\":{\"dataType\":\"Select\"},\"self.CSRQ\":{\"dataType\":\"Date\","
        + "\"value\":1568115840000},\"self.YBCB\":{\"dataType\":\"Select\",\"value\":{\"text\":\"城乡居民医疗保险登记\","
        + "\"value\":\"CXJMYLBXDJ\"}}}";

    public static String target = "[{\"type\":\"TABLE_FIELD_CONFIG\",\"list\":[{\"originalKey\":\"self.NQRPCS\","
        + "\"originalKeyCN\":\"拟迁入派出所\",\"forceValidate\":true,\"mapKey\":\"PCS\",\"KeyResource\":\"request\","
        + "\"mapKeyCN\":\"派出所\"},{\"originalKey\":\"self.IDCARD\",\"originalKeyCN\":\"公民身份号码\","
        + "\"forceValidate\":true,\"mapKey\":\"SQRGMSFHM\",\"KeyResource\":\"request\",\"mapKeyCN\":\"申请人公民身份号码\"},"
        + "{\"originalKey\":\"self.NAME\",\"originalKeyCN\":\"姓名\",\"forceValidate\":true,\"mapKey\":\"SQRXM\","
        + "\"KeyResource\":\"request\",\"mapKeyCN\":\"申请人姓名\"},{\"originalKey\":\"self.SSHM\","
        + "\"originalKeyCN\":\"手机号码\",\"forceValidate\":true,\"mapKey\":\"SQRLXDH\",\"KeyResource\":\"request\","
        + "\"mapKeyCN\":\"申请人联系电话\"},{\"originalKey\":\"self.HKZZ\",\"originalKeyCN\":\"拟迁入地址\","
        + "\"forceValidate\":true,\"mapKey\":\"SQRDZ\",\"KeyResource\":\"request\",\"mapKeyCN\":\"申请人地址\"},"
        + "{\"originalKey\":\"YSQRGX\",\"originalKeyCN\":\"与申请人关系\",\"forceValidate\":true,\"mapKey\":\"YSQRGX\","
        + "\"KeyResource\":\"request\",\"mapKeyCN\":\"与申请人关系\",\"defaultValue\":\"8006\"},{\"originalKey\":\"self"
        + ".IDCARD\",\"originalKeyCN\":\"公民身份号码\",\"forceValidate\":true,\"mapKey\":\"GMSFHM\","
        + "\"KeyResource\":\"request\",\"mapKeyCN\":\"公民身份号码\"},{\"originalKey\":\"self.NAME\","
        + "\"originalKeyCN\":\"姓名\",\"forceValidate\":true,\"mapKey\":\"XM\",\"KeyResource\":\"request\","
        + "\"mapKeyCN\":\"姓名\"},{\"originalKey\":\"self.SSHM\",\"originalKeyCN\":\"手机号码\",\"forceValidate\":true,"
        + "\"mapKey\":\"LXDH\",\"KeyResource\":\"request\",\"mapKeyCN\":\"联系电话\"},{\"originalKey\":\"self.NQRDZ\","
        + "\"originalKeyCN\":\"拟迁入省市县区\",\"forceValidate\":true,\"mapKey\":\"QRSSXQ\",\"KeyResource\":\"request\","
        + "\"mapKeyCN\":\"拟迁入省市县区\"},{\"originalKey\":\"self.HKZZ\",\"originalKeyCN\":\"拟迁入地址\","
        + "\"forceValidate\":true,\"mapKey\":\"QRDZ\",\"KeyResource\":\"request\",\"mapKeyCN\":\"拟迁入地址\"},"
        + "{\"originalKey\":\"self.RWSJ\",\"originalKeyCN\":\"入伍时间\",\"forceValidate\":true,\"mapKey\":\"RWSJ\","
        + "\"KeyResource\":\"request\",\"mapKeyCN\":\"入伍时间\"},{\"originalKey\":\"self.TYSJ\","
        + "\"originalKeyCN\":\"退役时间\",\"forceValidate\":true,\"mapKey\":\"TYSJ\",\"KeyResource\":\"request\","
        + "\"mapKeyCN\":\"退役时间\"},{\"originalKey\":\"self.TYLX\",\"originalKeyCN\":\"退役原因\",\"forceValidate\":true,"
        + "\"mapKey\":\"TYYY\",\"KeyResource\":\"request\",\"mapKeyCN\":\"退役原因\"},{\"originalKey\":\"self.TYBD\","
        + "\"originalKeyCN\":\"所在部队\",\"forceValidate\":true,\"mapKey\":\"SZBD\",\"KeyResource\":\"request\","
        + "\"mapKeyCN\":\"所在部队\"},{\"originalKey\":\"self.BTKR\",\"originalKeyCN\":\"被投靠人\",\"forceValidate\":true,"
        + "\"mapKey\":\"BTKR\",\"KeyResource\":\"request\",\"mapKeyCN\":\"被投靠人\"},{\"originalKey\":\"self.BTKRXM\","
        + "\"originalKeyCN\":\"被投靠人姓名\",\"forceValidate\":true,\"mapKey\":\"BTKRXM\",\"KeyResource\":\"request\","
        + "\"mapKeyCN\":\"被投靠人姓名\"},{\"originalKey\":\"self.BTKRGMSFHM\",\"originalKeyCN\":\"被投靠人公民身份号码\","
        + "\"forceValidate\":true,\"mapKey\":\"BTKRGMSFHM\",\"KeyResource\":\"request\",\"mapKeyCN\":\"被投靠人公民身份号码\"},"
        + "{\"originalKey\":\"self.YQRSQRGX\",\"originalKeyCN\":\"与迁入申请人关系\",\"forceValidate\":true,"
        + "\"mapKey\":\"BTKRYSQRGX\",\"KeyResource\":\"request\",\"mapKeyCN\":\"被投靠人与迁移人关系\","
        + "\"defaultValue\":\"8006\"},{\"originalKey\":\"self.BTKRLXDH\",\"originalKeyCN\":\"被投靠人联系电话\","
        + "\"forceValidate\":true,\"mapKey\":\"BTKRLXDH\",\"KeyResource\":\"request\",\"mapKeyCN\":\"被投靠人联系电话\"}]},"
        + "{\"type\":\"MATERIALS_FIELD_CONFIG\",\"list\":[{\"originalKey\":\"self.105100017\","
        + "\"originalKeyCN\":\"退出现役证件\",\"forceValidate\":true,\"mapKey\":\"1\",\"KeyResource\":\"request\","
        + "\"mapKeyCN\":\"退伍证\"},{\"originalKey\":\"self.105100001\",\"originalKeyCN\":\"公民身份证或军人身份证号码登记表\","
        + "\"forceValidate\":true,\"mapKey\":\"2\",\"KeyResource\":\"request\",\"mapKeyCN\":\"公民身份证或军人身份证号码登记表\"},"
        + "{\"originalKey\":\"self.105100020\",\"originalKeyCN\":\"迁入地居民户口簿\",\"forceValidate\":true,"
        + "\"mapKey\":\"3\",\"KeyResource\":\"request\",\"mapKeyCN\":\"居民户口簿\"},{\"originalKey\":\"self.105100019\","
        + "\"originalKeyCN\":\"户籍注销证明\",\"forceValidate\":true,\"mapKey\":\"4\",\"KeyResource\":\"request\","
        + "\"mapKeyCN\":\"户籍注销证明\"},{\"originalKey\":\"self.105100018\",\"originalKeyCN\":\"退出现役行政介绍信\","
        + "\"forceValidate\":true,\"mapKey\":\"5\",\"KeyResource\":\"request\",\"mapKeyCN\":\"退出现役行政介绍信\"},"
        + "{\"originalKey\":\"self.105100022\",\"originalKeyCN\":\"单位同意落户意见\",\"forceValidate\":true,"
        + "\"mapKey\":\"6\",\"KeyResource\":\"request\",\"mapKeyCN\":\"单位同意落户意见\"},"
        + "{\"originalKey\":\"self.105100028\",\"originalKeyCN\":\"亲属关系证明\",\"forceValidate\":true,\"mapKey\":\"7\","
        + "\"KeyResource\":\"request\",\"mapKeyCN\":\"居民户口簿\"},{\"originalKey\":\"self.105100021\","
        + "\"originalKeyCN\":\"房屋权属证明\",\"forceValidate\":true,\"mapKey\":\"8\",\"KeyResource\":\"request\","
        + "\"mapKeyCN\":\"不动产权证书\"},{\"originalKey\":\"self.105100023\",\"originalKeyCN\":\"房屋查询记录\","
        + "\"forceValidate\":true,\"mapKey\":\"9\",\"KeyResource\":\"request\",\"mapKeyCN\":\"房屋查询记录\"}]}]";


    public static String source2 = "{\"male.MZ\":{\"dataType\":\"Select\",\"value\":{\"text\":\"汉族\","
        + "\"value\":\"01\"}},\"self.JHZZH\":{\"dataType\":\"Input\",\"value\":\"223466\"},\"female"
        + ".MZ\":{\"dataType\":\"Select\",\"value\":{\"text\":\"汉族\",\"value\":\"01\"}},\"trustee"
        + ".NAME\":{\"dataType\":\"Input\"},\"female.IDCARD\":{\"dataType\":\"Input\","
        + "\"value\":\"123365199008152356\"},\"male.NAME\":{\"dataType\":\"Input\",\"value\":\"测试\"},\"trustee"
        + ".IDCARD\":{\"dataType\":\"Input\"},\"trustee.SSHM\":{\"dataType\":\"Input\"},\"female"
        + ".HKSSX\":{\"dataType\":\"AddressSelect\",\"value\":[{\"text\":\"浙江省\",\"value\":\"330000\"},"
        + "{\"text\":\"杭州市\",\"value\":\"330100\"},{\"text\":\"余杭区\",\"value\":\"330110\"}]},\"trustedPerson"
        + ".HKZZ\":{\"dataType\":\"Input\"},\"trustedPerson.MZ\":{\"dataType\":\"Select\"},\"male"
        + ".HKSSX\":{\"dataType\":\"AddressSelect\",\"value\":[{\"text\":\"浙江省\",\"value\":\"330000\"},"
        + "{\"text\":\"杭州市\",\"value\":\"330100\"},{\"text\":\"余杭区\",\"value\":\"330110\"}]},\"female"
        + ".HKZZ\":{\"dataType\":\"Input\",\"value\":\"测试地址\"},\"female.NAME\":{\"dataType\":\"Input\","
        + "\"value\":\"测试\"},\"trustedPerson.NAME\":{\"dataType\":\"Input\"},\"male.IDCARD\":{\"dataType\":\"Input\","
        + "\"value\":\"523123199004122356\"},\"male.HKZZ\":{\"dataType\":\"Input\",\"value\":\"测试地址\"},\"self"
        + ".TKLHFX\":{\"dataType\":\"Select\",\"value\":{\"text\":\"女方户口投靠至男方户内\",\"value\":\"NVTOUNAN\"}},\"trustee"
        + ".HJDPCS\":{\"dataType\":\"Input\"},\"trustee.MZ\":{\"dataType\":\"Select\"},\"self"
        + ".JHDJJG\":{\"dataType\":\"Input\",\"value\":\"客服\"},\"self.JHDJRQ\":{\"dataType\":\"Date\","
        + "\"value\":1568191020000},\"female.SSHM\":{\"dataType\":\"Input\",\"value\":\"13212361235\"},"
        + "\"trustedPerson.HJDPCS\":{\"dataType\":\"Input\"},\"male.SSHM\":{\"dataType\":\"Input\","
        + "\"value\":\"15231456145\"},\"trustedPerson.IDCARD\":{\"dataType\":\"Input\"},\"trustedPerson"
        + ".SSHM\":{\"dataType\":\"Input\"},\"trustee.HKZZ\":{\"dataType\":\"Input\"}}";

    public static String target2 = "[{\"type\":\"TABLE_FIELD_CONFIG\",\"list\":[{\"originalKey\":\"self.JHDJJG\","
        + "\"originalKeyCN\":\"结婚登记机关\",\"forceValidate\":true,\"mapKey\":\"PCS\",\"KeyResource\":\"request\","
        + "\"mapKeyCN\":\"派出所\"},{\"originalKey\":\"self.NAME\",\"originalKeyCN\":\"姓名\",\"forceValidate\":true,"
        + "\"mapKey\":\"XM\",\"KeyResource\":\"request\",\"mapKeyCN\":\"姓名\"},{\"originalKey\":\"YSQRGX\","
        + "\"originalKeyCN\":\"与申请人关系\",\"forceValidate\":true,\"mapKey\":\"YSQRGX\",\"KeyResource\":\"request\","
        + "\"mapKeyCN\":\"与申请人关系\",\"defaultValue\":\"01\"},{\"originalKey\":\"BGHNR\",\"originalKeyCN\":\"变更后内容\","
        + "\"forceValidate\":true,\"mapKey\":\"BGHNR\",\"KeyResource\":\"request\",\"mapKeyCN\":\"变更后内容\","
        + "\"defaultValue\":\"20\"},{\"originalKey\":\"self.IDCARD\",\"originalKeyCN\":\"公民身份号码\","
        + "\"forceValidate\":true,\"mapKey\":\"SQRGMSFHM\",\"KeyResource\":\"request\",\"mapKeyCN\":\"申请人公民身份号码\"},"
        + "{\"originalKey\":\"self.NAME\",\"originalKeyCN\":\"姓名\",\"forceValidate\":true,\"mapKey\":\"SQRXM\","
        + "\"KeyResource\":\"request\",\"mapKeyCN\":\"申请人姓名\"},{\"originalKey\":\"self.SSHM\","
        + "\"originalKeyCN\":\"手机号码\",\"forceValidate\":true,\"mapKey\":\"SQRLXDH\",\"KeyResource\":\"request\","
        + "\"mapKeyCN\":\"申请人联系电话\"},{\"originalKey\":\"self.HKZZ\",\"originalKeyCN\":\"户口住址\","
        + "\"forceValidate\":true,\"mapKey\":\"SQRDZ\",\"KeyResource\":\"request\",\"mapKeyCN\":\"申请人地址\"},"
        + "{\"originalKey\":\"self.HKZZ\",\"originalKeyCN\":\"户口住址\",\"forceValidate\":true,\"mapKey\":\"HKSZDZ\","
        + "\"KeyResource\":\"request\",\"mapKeyCN\":\"户口所在地址\"},{\"originalKey\":\"self.HKSSX\","
        + "\"originalKeyCN\":\"户口省市县\",\"forceValidate\":true,\"mapKey\":\"HKSZSSXQ\",\"KeyResource\":\"request\","
        + "\"mapKeyCN\":\"户口所在省市县区\"}]}]";

    private static void compare1(){
        String source = "";
        String target = "";
        compareField(source, target);

    }

    public static void parseSource(String source){
        SourceData sourceData = FastJsonUtil.json2Bean(source, new TypeReference<SourceData>(){});
        System.out.println(JSON.toJSONString(sourceData));
    }


    public static void parseTarget(String target){
        //ParameterizedTypeImpl inner = new ParameterizedTypeImpl(new Type[]{clazz}, null, List.class);
        //ParameterizedTypeImpl outer = new ParameterizedTypeImpl(new Type[]{inner}, null, );
        //List<TargetData> targetDataList = FastJsonUtil.json2Bean(target, new TypeReference<List<TargetData>>(){});
        //System.out.println(targetDataList.get(0).getMapFields().get(0).getOriginalKey());
    }

    private static void compareField(String source, String target){
        Map<String, String> originKeyMap = Maps.newHashMap();
        List<String> orignKeyList = Lists.newArrayList();
        JSONArray targetArray  = JSON.parseArray(target);

        for (Iterator iterator = targetArray.iterator(); iterator.hasNext();) {
            JSONObject jsonObject = (JSONObject) iterator.next();
            JSONArray jsonArray = jsonObject.getJSONArray("list");
            for(Iterator iterator1 = jsonArray.iterator(); iterator1.hasNext();){
                JSONObject jsonObject1 = (JSONObject) iterator1.next();
                orignKeyList.add(jsonObject1.getString("originalKey"));
                originKeyMap.put(jsonObject1.getString("originalKey"), jsonObject1.getString("originalKeyCN"));
            }
        }
        System.out.println(orignKeyList.size());

        JSONObject sourceObject  = JSON.parseObject(source);
        for(Map.Entry<String, String> origin : originKeyMap.entrySet()){
            if(!sourceObject.containsKey(origin.getKey())){
                System.out.println("no key:  " + origin.getKey() + "," + origin.getValue());
                continue;
            }
            if(!sourceObject.getJSONObject(origin.getKey()).containsKey("value")){
                System.out.println("has key no value:  " + origin.getKey() + "," + origin.getValue());
            }
        }
    }


    public static void main(String[] args) {

        //compareField(source1, target);
        compareField(source2, target2);

    }



}
