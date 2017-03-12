package util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by gary on 16/6/1.
 */
public class DateUtils {
    /**
     * 英文简写（默认）如：2010-12-01
     */
    public static String FORMAT_SHORT = "yyyy-MM-dd";

    /**
     * 英文全称  如：2010-12-01 23:15:06
     */
    public static String FORMAT_LONG = "yyyy-MM-dd HH:mm:ss";

    public static String FORMATE_LONG_MINUTE="yyyy-MM-dd HH:mm";

    /**
     * 精确到毫秒的完整时间    如：yyyy-MM-dd HH:mm:ss.S
     */
    public static String FORMAT_FULL = "yyyy-MM-dd HH:mm:ss.S";

    /**
     * 中文简写  如：2010年12月01日
     */
    public static String FORMAT_SHORT_CN = "yyyy年MM月dd";

    /**
     * 中文全称  如：2010年12月01日  23时15分06秒
     */
    public static String FORMAT_LONG_CN = "yyyy年MM月dd日  HH时mm分ss秒";

    /**
     * 精确到毫秒的完整中文时间
     */
    public static String FORMAT_FULL_CN = "yyyy年MM月dd日  HH时mm分ss秒SSS毫秒";

    /**
     *
     * @return
     */
    public static Date getDate(){
        return Calendar.getInstance().getTime();
    }

    public static Date string2date(String timeString, String format){

        DateFormat fmt = new SimpleDateFormat(format);
        try{
            Date date = fmt.parse(timeString);
            return  date;

        }catch(ParseException e){
            e.printStackTrace();
        }

        return null;
    }

    /**
     * 获取时间戳
     */
    public static String getTimeString(Date date,String format) {
        SimpleDateFormat df = new SimpleDateFormat(format);
        return df.format(date);
    }

    public static String getTimeString(long millis,String format){
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(millis);
        return getTimeString(calendar.getTime(),format);
    }

    /**
     * 获取日期年份
     * @param date 日期
     * @return
     */
    public static int getYear(Date date) {
//        return format(date).substring(0, 4);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.YEAR);
    }
    /**
     * 功能描述：返回月
     *
     * @param date
     *            Date 日期
     * @return 返回月份
     */
    public static int getMonth(Date date) {
       Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.MONTH) + 1;
    }

    /**
     * 功能描述：返回日
     *
     * @param date
     *            Date 日期
     * @return 返回日份
     */
    public static int getDay(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.DAY_OF_MONTH);
    }

    /**
     * 功能描述：返回小
     *
     * @param date
     *            日期
     * @return 返回小时
     */
    public static int getHour(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.HOUR_OF_DAY);
    }

    /**
     * 功能描述：返回分
     *
     * @param date
     *            日期
     * @return 返回分钟
     */
    public static int getMinute(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.MINUTE);
    }

    /**
     * 返回秒钟
     *
     * @param date
     *            Date 日期
     * @return 返回秒钟
     */
    public static int getSecond(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.SECOND);
    }

    /**
     * 功能描述：返回毫
     *
     * @param date
     *            日期
     * @return 返回毫
     */
    public static long getMillis(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.getTimeInMillis();
    }

    public static void main(String[] args){
//        System.out.println(TimeUtils.getTimeString(TimeUtils.getDate()));
//        System.out.println(TimeUtils.getMinute(TimeUtils.getDate()));
//        long millis = System.currentTimeMillis();
//        System.out.println(millis);
//        int minuteId = (int)(millis/60000);
//        System.out.println(minuteId);
//        int minuteId = 24387227;
//        System.out.println(TimeUtils.getTimeString((long)minuteId*60000,FORMATE_LONG_MINUTE));
//
//        long millis = 1465021296L;
//        System.out.println(getTimeString(millis*1000,FORMAT_LONG));

        System.out.println(getDate());
        System.out.println(string2date("2016-08-30", FORMAT_SHORT));

    }
}
