package util;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.Map;

/**
 * 请求数据和返回结果的管理工具类
 * Created by gary on 16/4/2.
 */
public class MVCUtil {
    private static final Logger logger = LoggerFactory.getLogger(MVCUtil.class);

    public static void returnData(HttpServletResponse response, String reponseData) {
        if (reponseData == null) {
            reponseData = "";
        }

        PrintWriter printWriter = null;
        try {
            response.setHeader("Pragma", "No-cache");
            response.setDateHeader("Expires", 0);
            response.setContentType("text/plain;charset=UTF-8");
            printWriter = response.getWriter();
            printWriter.write(reponseData);
        } catch (Exception e) {
            logger.error("MVCUtil.returnJsonData faild!", e);
        }
    }

    public static String getParam(HttpServletRequest request, String name) {
        Map<String, String[]> params = request.getParameterMap();
        if ((params != null) && (params.get(name) != null)) {
            return params.get(name)[0];
        }
        return null;
    }

    public static String[] getParamArray(HttpServletRequest request, String name) {
        Map<String, String[]> params = request.getParameterMap();
        if ((params != null) && (params.get(name) != null)) {
            return params.get(name);
        }
        return null;
    }

    public static int getIntParam(HttpServletRequest request, String name) {
        Map<String, String[]> params = request.getParameterMap();
        if ((params != null) && (params.get(name) != null)) {
            if (StringUtils.isNumeric(params.get(name)[0])) {
                return Integer.parseInt(params.get(name)[0]);
            }
        }
        return 0;
    }

    public static int[] getIntParamArray(HttpServletRequest request, String name) {
        int[] intparams = new int[0];
        String[] params = getParamArray(request, name);
        if ((params != null) && (params.length > 0)) {
            intparams = new int[params.length];
            for (int i = 0; i < params.length; i++) {
                intparams[i] = Integer.parseInt(params[i]);
            }
        }
        return intparams;
    }

    public static long getLongParam(HttpServletRequest request, String name) {
        Map<String, String[]> params = request.getParameterMap();
        if ((params != null) && (params.get(name) != null)) {
            if (StringUtils.isNumeric(params.get(name)[0])) {
                return Long.parseLong(params.get(name)[0]);
            }
        }
        return 0;
    }

    public static long[] getLongParamArray(HttpServletRequest request, String name) {
        long[] longparams = new long[0];
        String[] params = getParamArray(request, name);
        if ((params != null) && (params.length > 0)) {
            longparams = new long[params.length];
            for (int i = 0; i < params.length; i++) {
                longparams[i] = Long.parseLong(params[i]);
            }
        }
        return longparams;
    }

    /**
     * 获取请求头的特殊的参数值
     *
     * @param name
     * @return
     */
    public static Long getRequestDataHead(HttpServletRequest request, String name) {
        Long result = request.getDateHeader(name);
        return result;
    }

    public static HttpServletRequest currentRequest() {
        return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
    }
}
