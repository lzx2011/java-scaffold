package com.lzhenxing.javascaffold.util;

import org.springframework.core.LocalVariableTableParameterNameDiscoverer;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * ClassName: ReflectUtils <br/>
 * Function: <br/>
 *
 * @author gary.liu
 * @date 2017/4/30
 */
public class ReflectUtils {

	private ReflectUtils() {

	}

    /**
     * 获取某个方法的参数名称
     * @param clazz
     * @param methodName
     * @return
     */
	public static List<String> getParameterName(Class clazz, String methodName){
        LocalVariableTableParameterNameDiscoverer u = new LocalVariableTableParameterNameDiscoverer();
        Method[] methods = clazz.getDeclaredMethods();
        for (Method method : methods) {
            if (methodName.equals(method.getName())) {
                String[] params = u.getParameterNames(method);
                return Arrays.asList(params);
            }
        }

        return null;
    }

    /**
     * 获取某个方法的参数名称,只适用于 java8
     * @param clazz
     * @param methodName
     * @return
     */
    public static List<String> getParameterNameJava8(Class clazz, String methodName){
        List<String> paramterList = new ArrayList<>();
        Method[] methods = clazz.getDeclaredMethods();
        for (Method method : methods) {
            if (methodName.equals(method.getName())) {
                Parameter[] params = method.getParameters();
                for(Parameter parameter : params){
                    paramterList.add(parameter.getName());
                }

            }
        }

        return paramterList;
    }




	public static void main(String[] args) {
        //System.out.print(getParameterName(ListUtils.class, "splitList").toString());
        System.out.print(getParameterNameJava8(ListUtils.class, "splitList").toString());

	}
}
