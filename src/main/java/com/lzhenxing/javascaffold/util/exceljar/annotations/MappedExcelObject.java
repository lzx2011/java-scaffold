package com.lzhenxing.javascaffold.util.exceljar.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * ClassName: MappedExcelObject <br/>
 * Function: <br/>
 *
 * @author gary.liu
 * @date 2017/6/30
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({java.lang.annotation.ElementType.FIELD})
public @interface MappedExcelObject {}
