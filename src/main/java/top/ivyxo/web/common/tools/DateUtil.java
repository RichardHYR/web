package top.ivyxo.web.common.tools;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 日期操作工具类 Richard - 2019-12-4 23:44:22
 * @author HYR
 */
public class DateUtil {

    /**
     * 将yyyy-MM-dd格式的字符串转换为Date类型 Richard - 2019-12-4 23:43:56
     * @param date yyyy-MM-dd格式的字符串
     * @return
     */
    public static Date parseDate(String date){
        //转换提日期输出格式
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            return dateFormat.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取格式化后的时间 Richard - 2019-12-10 21:28:58
     * @return
     */
    public static String getNowTime(){
        //得到long类型当前时间
        long l = System.currentTimeMillis();
        //new日期对象
        Date date = new Date(l);
        //转换提日期输出格式
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return dateFormat.format(date);
    }

}
