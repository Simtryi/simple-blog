package com.simple.blog.common.log;

import com.simple.blog.common.util.DateUtil;
import com.simple.blog.common.util.IpUtil;
import com.simple.blog.common.util.JsonUtil;
import com.simple.blog.common.util.StringUtil;
import net.logstash.logback.marker.Markers;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.*;

/**
 * 接口访问切面
 */
@Aspect
@Component
public class ApiAspect {

    private static final Logger log = LoggerFactory.getLogger("API_LOGGER");

    /**
     * 切入点
     */
    @Pointcut("execution(public * com.simple.blog.controller.*.*(..)) || execution(public * com.simple.blog.*.controller.*.*(..))")
    public void api() {}

    /**
     * 环绕操作
     * @param joinPoint 切入点
     * @return  原方法返回值
     */
    @Around("api()")
    public Object doAround(ProceedingJoinPoint joinPoint) throws Throwable {
        //  请求开始时间
        long startTime = System.currentTimeMillis();
        Object result = joinPoint.proceed();
        //  请求结束时间
        long endTime = System.currentTimeMillis();

        //  获取请求对象
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = Objects.requireNonNull(servletRequestAttributes).getRequest();

        //  获取请求方法
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Method method = methodSignature.getMethod();

        //  请求日志
        ApiLog apiLog = new ApiLog();
        apiLog.setUsername(request.getRemoteUser());
        apiLog.setStartTime(DateUtil.convertTimestampToString(startTime));
        apiLog.setSpendTime((int) (endTime - startTime));
        apiLog.setUrl(request.getRequestURL().toString());
        apiLog.setUri(request.getRequestURI());
        apiLog.setMethod(request.getMethod());
        apiLog.setParameter(getRequestParameter(method, joinPoint.getArgs()));
        apiLog.setResult(result);
        apiLog.setIp(IpUtil.getIp(request));

        Map<String, Object> logMap = new HashMap<>();
        logMap.put("url", apiLog.getUrl());
        logMap.put("method", apiLog.getMethod());
        logMap.put("parameter", apiLog.getParameter());
        logMap.put("spendTime", apiLog.getSpendTime());
        log.info(Markers.appendEntries(logMap), JsonUtil.parse(apiLog).toString());
        return result;
    }

    /**
     * 获取请求参数
     */
    private Object getRequestParameter(Method method, Object[] args) {
        List<Object> argList = new ArrayList<>();
        //  方法参数
        Parameter[] parameters = method.getParameters();

        for (int i = 0; i < parameters.length; i++) {
            //  请求参数的注解为 @RequestBody
            RequestBody requestBody = parameters[i].getAnnotation(RequestBody.class);
            if (null != requestBody) {
                argList.add(args[i]);
            }

            //  请求参数的注解为 @RequestParam
            RequestParam requestParam = parameters[i].getAnnotation(RequestParam.class);
            if (null != requestParam) {
                Map<String, Object> map = new HashMap<>();
                String key = parameters[i].getName();
                if (!StringUtil.isEmpty(requestParam.value())) {
                    key = requestParam.value();
                }
                map.put(key, args[i]);
                argList.add(map);
            }
        }

        if (argList.size() == 0) {
            return null;
        } else if (argList.size() == 1) {
            return argList.get(0);
        } else {
            return argList;
        }
    }

}
