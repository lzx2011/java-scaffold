package com.lzhenxing.javascaffold.util.exceljar.model;

import java.lang.reflect.Field;

/**
 * ClassName: ExcelFieldMappingInfo <br/>
 * Function: <br/>
 *
 * @author gary.liu
 * @date 2017/6/30
 */
public class ExcelFieldMappingInfo
{
    Field field;
    private String headName;
    private int position;
    private String importDataFormat;
    private String exportDataFormat;
    private int exportFieldWidth;
    boolean notExport;

    public Field getField()
    {
        return this.field;
    }

    public void setField(Field field)
    {
        this.field = field;
    }

    public String getHeadName()
    {
        return this.headName;
    }

    public void setHeadName(String headName)
    {
        this.headName = headName;
    }

    public int getPosition()
    {
        return this.position;
    }

    public void setPosition(int position)
    {
        this.position = position;
    }

    public String getImportDataFormat()
    {
        return this.importDataFormat;
    }

    public void setImportDataFormat(String importDataFormat)
    {
        this.importDataFormat = importDataFormat;
    }

    public String getExportDataFormat()
    {
        return this.exportDataFormat;
    }

    public void setExportDataFormat(String exportDataFormat)
    {
        this.exportDataFormat = exportDataFormat;
    }

    public int getExportFieldWidth()
    {
        return this.exportFieldWidth;
    }

    public void setExportFieldWidth(int exportFieldWidth)
    {
        this.exportFieldWidth = exportFieldWidth;
    }

    public boolean isNotExport()
    {
        return this.notExport;
    }

    public void setNotExport(boolean notExport)
    {
        this.notExport = notExport;
    }
}
