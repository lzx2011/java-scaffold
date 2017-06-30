package com.lzhenxing.javascaffold.util.exceljar.model;

import com.lzhenxing.javascaffold.util.exceljar.enums.ExcelMappingType;
import com.lzhenxing.javascaffold.util.exceljar.enums.ParseType;

import java.util.List;
import java.util.Map;

/**
 * ClassName: ExcelMappingInfo <br/>
 * Function: <br/>
 *
 * @author gary.liu
 * @date 2017/6/30
 */
public class ExcelMappingInfo
{
    private ExcelMappingType excelMappingType;
    private String sheetName;
    private int sheetIndex;
    private ParseType parseType;
    private int headPosition;
    private int headStart;
    private int headEnd;
    private int dataStart;
    private int dataEnd;
    private boolean zeroIfNull;
    private Map<String, Integer> headNamePositionMap;
    private List<ExcelFieldMappingInfo> fieldMappingInfoListForImport;
    private List<ExcelFieldMappingInfo> fieldMappingInfoListForExport;

    public ExcelMappingType getExcelMappingType()
    {
        return this.excelMappingType;
    }

    public void setExcelMappingType(ExcelMappingType excelMappingType)
    {
        this.excelMappingType = excelMappingType;
    }

    public String getSheetName()
    {
        return this.sheetName;
    }

    public void setSheetName(String sheetName)
    {
        this.sheetName = sheetName;
    }

    public int getSheetIndex()
    {
        return this.sheetIndex;
    }

    public void setSheetIndex(int sheetIndex)
    {
        this.sheetIndex = sheetIndex;
    }

    public ParseType getParseType()
    {
        return this.parseType;
    }

    public void setParseType(ParseType parseType)
    {
        this.parseType = parseType;
    }

    public int getHeadPosition()
    {
        return this.headPosition;
    }

    public void setHeadPosition(int headPosition)
    {
        this.headPosition = headPosition;
    }

    public int getHeadStart()
    {
        return this.headStart;
    }

    public void setHeadStart(int headStart)
    {
        this.headStart = headStart;
    }

    public int getHeadEnd()
    {
        return this.headEnd;
    }

    public void setHeadEnd(int headEnd)
    {
        this.headEnd = headEnd;
    }

    public int getDataStart()
    {
        return this.dataStart;
    }

    public void setDataStart(int dataStart)
    {
        this.dataStart = dataStart;
    }

    public int getDataEnd()
    {
        return this.dataEnd;
    }

    public void setDataEnd(int dataEnd)
    {
        this.dataEnd = dataEnd;
    }

    public boolean isZeroIfNull()
    {
        return this.zeroIfNull;
    }

    public void setZeroIfNull(boolean zeroIfNull)
    {
        this.zeroIfNull = zeroIfNull;
    }

    public Map<String, Integer> getHeadNamePositionMap()
    {
        return this.headNamePositionMap;
    }

    public void setHeadNamePositionMap(Map<String, Integer> headNamePositionMap)
    {
        this.headNamePositionMap = headNamePositionMap;
    }

    public List<ExcelFieldMappingInfo> getFieldMappingInfoListForImport()
    {
        return this.fieldMappingInfoListForImport;
    }

    public void setFieldMappingInfoListForImport(List<ExcelFieldMappingInfo> fieldMappingInfoListForImport)
    {
        this.fieldMappingInfoListForImport = fieldMappingInfoListForImport;
    }

    public List<ExcelFieldMappingInfo> getFieldMappingInfoListForExport()
    {
        return this.fieldMappingInfoListForExport;
    }

    public void setFieldMappingInfoListForExport(List<ExcelFieldMappingInfo> fieldMappingInfoListForExport)
    {
        this.fieldMappingInfoListForExport = fieldMappingInfoListForExport;
    }

    public Integer getHeadNamePosition(String headName)
    {
        return (Integer)this.headNamePositionMap.get(headName);
    }
}
