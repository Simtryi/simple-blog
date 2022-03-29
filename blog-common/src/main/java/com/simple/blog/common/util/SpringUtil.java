package com.simple.blog.common.util;

import com.simple.blog.common.exception.ApiException;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.ResolvableType;
import org.springframework.stereotype.Component;

/**
 * Spring 工具
 */
@Component
public class SpringUtil implements ApplicationContextAware {

    /**
     * Spring 应用上下文
     */
    private static ApplicationContext applicationContext;

    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        if (null == SpringUtil.applicationContext) {
            SpringUtil.applicationContext = applicationContext;
        }
    }

    /**
     * 通过 name 获取 Bean
     */
    public static Object getBean(String name) {
        return getApplicationContext().getBean(name);
    }

    /**
     * 通过 class 获取 Bean
     */
    public static <T> T getBean(Class<T> clazz) {
        return getApplicationContext().getBean(clazz);
    }

    /**
     * 通过 name、class 获取 Bean
     */
    public static <T> T getBean(String name, Class<T> clazz) {
        return getApplicationContext().getBean(name, clazz);
    }

    /**
     * 通过泛型获取 Bean
     */
    public static Object getBean(Class<?> clazz, Class<?>... generics) {
        ResolvableType resolvableType = ResolvableType.forClassWithGenerics(clazz, generics);
        String[] beanNames = getApplicationContext().getBeanNamesForType(resolvableType);

        if (beanNames.length > 0 && null != beanNames[0]) {
            return getApplicationContext().getBean(beanNames[0]);
        }

        throw new ApiException(StringUtil.format("不存在{}相关的Bean", resolvableType.toString()));
    }

}
