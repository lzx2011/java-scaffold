package com.lzhenxing.javascaffold.util;

import org.springframework.context.ApplicationContext;

/**
 * ClassName: ApplicationContextCache <br/>
 * Function: <br/>
 *
 * @author gary.liu
 * @date 2017/7/11
 */
public class ApplicationContextCache {

    private static ApplicationContextCache applicationContextCache = null;

    private ApplicationContext context = null;

    private ApplicationContextCache() {

    }

    public synchronized static ApplicationContextCache getInstance() {
        if (applicationContextCache == null) {
            applicationContextCache = new ApplicationContextCache();
        }
        return applicationContextCache;
    }

    public static void clean() {
        applicationContextCache = null;
    }



    public ApplicationContext getContext() {
        return context;
    }

    public void setContext(ApplicationContext context) {
        this.context = context;
    }

}
