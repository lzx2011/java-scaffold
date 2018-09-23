package com.lzhenxing.javascaffold.util.emoji;

import org.apache.commons.lang3.StringUtils;

/**
 *
 * ClassName: EmojFilter <br/>
 * Function: <br/>
 *
 * @author zhenxing.liu
 * @date 2018/3/26
 */
public class EmojiFilter {
    /**
     * 检测是否有emoji字符
     *
     * @param source
     * @return 一旦含有就抛出
     */
    public static boolean containsEmoji(String source) {
        if (StringUtils.isBlank(source)) {
            return false;
        }
        int len = source.length();
        for (int i = 0; i < len; i++) {
            char codePoint = source.charAt(i);
            if (!isNotEmojiCharacter(codePoint)) {
                //do nothing，判断到了这里表明，确认有表情字符
                return true;
            }
        }
        return false;
    }

    private static boolean isNotEmojiCharacter(char codePoint) {
        return (codePoint == 0x0) ||
            (codePoint == 0x9) ||
            (codePoint == 0xA) ||
            (codePoint == 0xD) ||
            ((codePoint >= 0x20) && (codePoint <= 0xD7FF)) ||
            ((codePoint >= 0xE000) && (codePoint <= 0xFFFD)) ||
            ((codePoint >= 0x10000) && (codePoint <= 0x10FFFF));
    }

    /**
     * 过滤emoji 或者 其他非文字类型的字符
     *
     * @param source
     * @return
     */
    public static String filterEmoji(String source) {
        source = source.replaceAll("[\\ud800\\udc00-\\udbff\\udfff\\ud800-\\udfff]", "");
        if (!containsEmoji(source)) {
            return source;
        }
        StringBuilder buf = null;
        int len = source.length();

        for (int i = 0; i < len; i++) {
            char codePoint = source.charAt(i);
            if (isNotEmojiCharacter(codePoint)) {
                if (buf == null) {
                    buf = new StringBuilder(source.length());
                }
                buf.append(codePoint);
            } else {
                //buf.append("*");
            }
        }

        if (buf == null) {
            //如果没有找到 emoji表情，则返回源字符串
            return source;
        } else {
            //这里的意义在于尽可能少的toString，因为会重新生成字符串
            if (buf.length() == len) {
                return source;
            } else {
                return buf.toString();
            }
        }

    }

    public static void main(String args[]) {
        EmojiFilter obj = new EmojiFilter();
        obj.Test();
        //obj.test1();
    }

    private void Test() {
        String str = "An 😀awesome 😃string with a few 😉emojis!";
        System.out.println("转换前：" + str);
        //emoji字符替换成*
        String res = filterEmoji(str);
        System.out.println("转换后：" + res);
    }

    private void test1(){
        String test
            = "郑青琦(郑青琦) ?假期开始了，你找不到我 订单号：137606484121374182  "
            + "重新发起扣款原因：扣款金额已经核实好可以发起扣款，卖家淘酒店专营店，酒店：联系酒店签约人朱女士告知扣款金额是172无误。已核对会员金额无误，故发起扣款172元@钱海江(孟庭)";
        System.out.println(filterEmoji(test));
    }

}
