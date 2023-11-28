package com.hb.core.aspect;

import com.alibaba.fastjson2.JSON;
import com.hb.common.utils.ServletUtils;
import com.hb.core.annotations.WebLog;
import com.hb.system.entity.SysOperateLog;
import com.hb.system.mapper.SysOperateLogMapper;
import com.hb.system.model.LoginUser;
import com.hb.system.model.LoginUserContextHolder;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.HandlerMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collection;
import java.util.Map;
import java.util.Objects;

/**
 * @author zhaochengshui
 * @description 用户操作记录日志切面
 * @date 2022/8/24
 */
@Aspect
@Component
public class WebLogAspect {

    private static final Logger log = LoggerFactory.getLogger(WebLogAspect.class);
    @Resource
    private SysOperateLogMapper operateLogMapper;

    /**
     * 处理完请求后执行
     *
     * @param joinPoint 切点
     */
    @AfterReturning(pointcut = "@annotation(controllerLog)", returning = "jsonResult")
    public void doAfterReturning(JoinPoint joinPoint, WebLog controllerLog, Object jsonResult) {
        handleLog(joinPoint, controllerLog, null, jsonResult);
    }

    protected void handleLog(final JoinPoint joinPoint, WebLog controllerLog, final Exception e, Object jsonResult) {
        try {
            // 操作日志信息
            SysOperateLog operateLog = new SysOperateLog();
            LoginUser loginUser = LoginUserContextHolder.getLoginUser();
            operateLog.setCreatedBy(loginUser.getId().toString());
            operateLog.setOperatorIp(loginUser.getIp());
            operateLog.setMethod(joinPoint.getSignature().getName());
            operateLog.setRequestMethod(ServletUtils.getRequestMethod());
            // 处理设置注解上的参数
            getControllerMethodDescription(joinPoint, controllerLog, operateLog, jsonResult);
            // 保存数据库
            operateLogMapper.insert(operateLog);
        } catch (Exception exp) {
            log.error("异常信息:{}", exp.getMessage());
            exp.printStackTrace();
        }
    }

    /**
     * 获取注解中对方法的描述信息 用于Controller层注解
     *
     * @param log        日志
     * @param operateLog 操作日志
     */
    public void getControllerMethodDescription(JoinPoint joinPoint, WebLog log, SysOperateLog operateLog, Object jsonResult) {
        // 设置操作业务类型
        operateLog.setBusinessType(log.businessType().ordinal());
        // 设置标题
        operateLog.setModule(log.module());
        // 是否需要保存request，参数和值
        if (log.isSaveRequestData()) {
            // 设置参数的信息
            setRequestValue(joinPoint, operateLog);
        }
        // 是否需要保存response，参数和值
        if (log.isSaveResponseData() && !Objects.isNull(jsonResult)) {
            operateLog.setJsonResult(JSON.toJSONString(jsonResult));
        }
    }

    /**
     * 获取请求的参数，放到log中
     *
     * @param operateLog 操作日志
     */
    private void setRequestValue(JoinPoint joinPoint, SysOperateLog operateLog) {
        String requestMethod = operateLog.getRequestMethod();
        if (HttpMethod.PUT.name().equals(requestMethod) || HttpMethod.POST.name().equals(requestMethod)) {
            String params = argsArrayToString(joinPoint.getArgs());
            operateLog.setOperatorParam(params);
        } else {
            Map<?, ?> paramsMap = (Map<?, ?>) ServletUtils.getRequest().getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
            operateLog.setOperatorParam(paramsMap.toString());
        }
    }

    /**
     * 参数拼装
     */
    private String argsArrayToString(Object[] paramsArray) {
        StringBuilder params = new StringBuilder();
        if (paramsArray != null && paramsArray.length > 0) {
            for (Object object : paramsArray) {
                // 不为空 并且是不需要过滤的 对象
                if (!Objects.isNull(object) && !isFilterObject(object)) {
                    Object jsonObj = JSON.toJSON(object);
                    params.append(jsonObj.toString()).append(" ");
                }
            }
        }
        return params.toString().trim();
    }

    /**
     * 判断是否需要过滤的对象。
     *
     * @param object 对象信息。
     * @return 如果是需要过滤的对象，则返回true；否则返回false。
     */
    @SuppressWarnings("rawtypes")
    public boolean isFilterObject(final Object object) {
        Class<?> clazz = object.getClass();
        if (clazz.isArray()) {
            return clazz.getComponentType().isAssignableFrom(MultipartFile.class);
        } else if (Collection.class.isAssignableFrom(clazz)) {
            Collection collection = (Collection) object;
            for (Object value : collection) {
                return value instanceof MultipartFile;
            }
        } else if (Map.class.isAssignableFrom(clazz)) {
            Map map = (Map) object;
            for (Object value : map.entrySet()) {
                Map.Entry entry = (Map.Entry) value;
                return entry.getValue() instanceof MultipartFile;
            }
        }
        return object instanceof MultipartFile || object instanceof HttpServletRequest
                || object instanceof HttpServletResponse || object instanceof BindingResult;
    }

}
