package com.lzhenxing.javascaffold.util.emoji;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * ClassName: EmojiCache <br/>
 * Function: <br/>
 *
 * @author gary.liu
 * @date 2017/7/6
 */
@Component("emojiCache")
public class EmojiCache {
    public static final Logger logger = LoggerFactory.getLogger(EmojiCache.class);
    // 表情地址 http://apps.timwhitlock.info/emoji/tables/unicode#block-4-enclosed-characters
    // 所有 emoji 两个 unicode
    private final static Integer[][] EMOJI_UNICODE_TWO = { { 0x0038, 0x20E3 }, { 0x0039, 0x20E3 }, { 0x0037, 0x20E3 },
            { 0x0036, 0x20E3 }, { 0x0031, 0x20E3 }, { 0x0030, 0x20E3 }, { 0x0032, 0x20E3 }, { 0x0033, 0x20E3 },
            { 0x0035, 0x20E3 }, { 0x0034, 0x20E3 }, { 0x0023, 0x20E3 }, { 0x1F1E9, 0x1F1EA }, { 0x1F1EC, 0x1F1E7 },
            { 0x1F1EF, 0x1F1F5 }, { 0x1F1F0, 0x1F1F7 }, { 0x1F1EB, 0x1F1F7 }, { 0x1F1EA, 0x1F1F8 },
            { 0x1F1EE, 0x1F1F9 }, { 0x1F1FA, 0x1F1F8 }, { 0x1F1F7, 0x1F1FA } };

    // 2个unicode
    private static Map<String, List<Integer>> REVERT_EMOJI_TWO  = new HashMap<String, List<Integer>>();
    // 保存在数据库中前缀
    private final static String   PREFIX     = "[e]";
    // 保存在数据库中后缀
    private final static String   SUFFIX     = "[/e]";
    // 匹配字符串
    private final static String   REGEX      = "\\[e\\](.*?)\\[/e\\]";

    // 初始化
    static {
        // 两个unicode
        for(Integer[] array : EMOJI_UNICODE_TWO){
            List<Integer> listCode = new ArrayList<Integer>();
            listCode.add(array[0]);
            listCode.add(array[1]);
            String content = PREFIX + array[0] + "," + array[1] + SUFFIX;
            REVERT_EMOJI_TWO.put(content, listCode);
        }
    }

    /**
     * 简化版的表情转义函数
     * simpleConvert:(这里用一句话描述这个方法的作用). <br/>
     *
     * @author jack01.he
     * @param content 内容
     * @param maxlen 最大字符长度，超过这个长度后面字符被截断
     * @return
     */
    public String simpleConvert(String content,int maxlen){
        if(content == null || "".equals(content)){
            return "";
        }

        char[] ch = content.toCharArray();
        StringBuilder sb = new StringBuilder();
        try{
            String temp ;
            for(int i = 0 ,cp ; i < content.length(); i += Character.charCount(cp)){

                cp = Character.codePointAt(ch, i);
                if(Character.charCount(cp) > 1){
                    temp = PREFIX+ cp +SUFFIX;
                }
                else{
                    temp = String.valueOf(ch[i]);
                }
                if(temp == null){
                    temp = "";
                }
                if(sb.length() + temp.length()  <= maxlen){
                    sb.append(temp);
                }else
                {
                    break;
                }
            }
        }catch(Exception e){
            logger.error("[emoji] 评价内容转义失败",e);
        }
        return sb.toString();
    }

    /**
     * 简化版
     * simpleReconvert:(这里用一句话描述这个方法的作用). <br/>
     *
     * @author jack01.he
     * @param content
     * @param isallowOld 是否兼容老数据参数
     * @return
     */
    public String simpleReconvert(String content,boolean isallowOld){
        if(content == null || "".equals(content)){
            return "";
        }
        StringBuilder sb = new StringBuilder();

        Pattern pattern = Pattern.compile(REGEX);
        Matcher matcher = pattern.matcher(content);
        int index = 0;
        while (matcher.find()) {
            sb.append(content.substring(index, matcher.start()));
            index = matcher.end();
            String emoji = matcher.group();

            if (isallowOld && REVERT_EMOJI_TWO.containsKey(emoji)) {//兼容老数据保留两个表情符
                List<Integer> listCode = REVERT_EMOJI_TWO.get(emoji);

                sb.append(String.valueOf(Character.toChars(listCode.get(0))));
                sb.append(String.valueOf(Character.toChars(listCode.get(1))));
            }
            else{
                emoji = emoji.replace(PREFIX, "");
                emoji = emoji.replace(SUFFIX, "");
                try{
                    sb.append(Character.toChars(Integer.parseInt(emoji)));
                }catch(Exception e){
                    logger.error("[emoji] 评价内容反转义失败",e);
                    sb.append(emoji);
                }
            }
        }
        sb.append(content.substring(index, content.length()));
        return sb.toString();
    }

}
