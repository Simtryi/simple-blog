package com.simple.blog.common.util;

import cn.hutool.core.util.StrUtil;
import org.slf4j.helpers.MessageFormatter;

/**
 * String 工具
 */
public class StringUtil extends StrUtil {

    /**
     * 格式化字符串
     */
    public static String format(String messagePattern, Object... arguments) {
        return MessageFormatter.format(messagePattern, arguments).getMessage();
    }

}
