package com.lzhenxing.javascaffold.util.exceljar.exception;

/**
 * ClassName: ExcelParsingException <br/>
 * Function: <br/>
 *
 * @author gary.liu
 * @date 2017/6/30
 */
public class ExcelParsingException
        extends Exception
{
    public ExcelParsingException(String message)
    {
        super(message);
    }

    public ExcelParsingException(String message, Exception exception)
    {
        super(message, exception);
    }
}