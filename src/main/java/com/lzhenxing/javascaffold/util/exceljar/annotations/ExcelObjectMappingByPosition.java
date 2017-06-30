package com.lzhenxing.javascaffold.util.exceljar.annotations;

import com.lzhenxing.javascaffold.util.exceljar.enums.ParseType;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * ClassName: ExcelObjectMappingByPosition <br/>
 * Function: <br/>
 *
 * @author gary.liu
 * @date 2017/6/30
 */

@Retention(RetentionPolicy.RUNTIME)
@Target({java.lang.annotation.ElementType.TYPE})
public @interface ExcelObjectMappingByPosition
{
    String sheetName() default "";

    int sheetIndex() default 0;

    ParseType parseType();

    int dataStart();

    int dataEnd() default 0;

    boolean zeroIfNull() default false;
}

