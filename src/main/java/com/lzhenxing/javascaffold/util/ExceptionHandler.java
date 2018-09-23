package com.lzhenxing.javascaffold.util;

import com.lzhenxing.javascaffold.util.json.FastJsonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by gary on 16/4/2.
 */
public class ExceptionHandler implements HandlerExceptionResolver {
    private final Logger logger = LoggerFactory.getLogger(ExceptionHandler.class);

    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler,
                                         Exception ex) {
        // 异常处理
        if (ex != null) {
            String responseData;
            if (ex instanceof ProcessException) {
                if (ex.getCause() != null) {
                    Throwable causeBy = ex.getCause();
                    logger.error("ProcessException_Cause_By:" + causeBy.getMessage(), causeBy);
                    String exName = causeBy.getClass().getSimpleName();
//                    StatisticsEntity entity = Statistics.entity(exName);
//                    try {
//                        entity.onBegin();
//                        entity.onException(causeBy);
//                    } finally {
//                        entity.onEnd();
//                    }
                }
                Map<String, Object> result = new LinkedHashMap<>();
//                result.put("code", ((ProcessException) ex).errorCode());
                result.put("msg", ex.getMessage());
                result.put("result", Boolean.FALSE);
                responseData = FastJsonUtil.bean2Json(result);
            } else {
                logger.error(ex.getMessage(), ex);
                responseData = FastJsonUtil.bean2Json(ex);
            }
            MVCUtil.returnData(response, responseData);
        }
        return new ModelAndView();
    }
}
