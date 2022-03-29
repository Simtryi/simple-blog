package com.simple.blog.common.util;

import com.simple.blog.common.exception.ApiException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 日期工具
 */
public class DateUtil extends cn.hutool.core.date.DateUtil {

    public static final String DEFAULT_FORMAT = "yyyy-MM-dd HH:mm:ss";
    public static final String TIME_FORMAT = "HH:mm";
    public static final String YEAR_MONTH_FORMAT = "yyyy-MM";
    public static final String DATE_FORMAT = "yyyy-MM-dd";
    public static final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm";

    /**
     * 时间戳转换为字符串
     */
    public static String convertTimestampToString(Long timestamp) {
        //  时间戳转 Date
        Date date = date(timestamp);
        //  格式化输出日期
        return format(date, DEFAULT_FORMAT);
    }

    /**
     * 字符串转换为日期
     */
    public static Date convertStringToDate(String dateStr) {
        return convertStringToDate(dateStr, DEFAULT_FORMAT);
    }

    /**
     * 字符串转换为日期
     */
    public static Date convertStringToDate(String dateStr, String format) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        try {
            return simpleDateFormat.parse(dateStr);
        } catch (ParseException e) {
            throw new ApiException(StringUtil.format("{}无法转换成日期格式", dateStr));
        }
    }

    /**
     * 日期转换为字符串
     */
    public static String convertDateToString(Date date) {
        return convertDateToString(date, DEFAULT_FORMAT);
    }

    /**
     * 日期转换为字符串
     */
    public static String convertDateToString(Date date, String format) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        return simpleDateFormat.format(date);
    }

}
