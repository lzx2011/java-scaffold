package com.lzhenxing.javascaffold.util.exceljar.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * ClassName: ExcelFieldMappingByHeadName <br/>
 * Function: <br/>
 *
 * @author gary.liu
 * @date 2017/6/30
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({java.lang.annotation.ElementType.FIELD})
public @interface ExcelFieldMappingByHeadName
{
    String headName();

    String importDataFormat() default "yyyy-MM-dd HH:mm:ss";

    String exportDataFormat() default "yyyy-MM-dd HH:mm:ss";

    int exportFieldWidth() default 12;

    boolean notExport() default false;
}
