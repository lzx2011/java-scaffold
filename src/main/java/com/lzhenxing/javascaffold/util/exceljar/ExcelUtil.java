//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.lzhenxing.javascaffold.util.exceljar;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.lzhenxing.javascaffold.util.exceljar.annotations.*;
import com.lzhenxing.javascaffold.util.exceljar.enums.ExcelFileType;
import com.lzhenxing.javascaffold.util.exceljar.enums.ExcelMappingType;
import com.lzhenxing.javascaffold.util.exceljar.enums.ParseType;
import com.lzhenxing.javascaffold.util.exceljar.exception.ExcelParsingException;
import com.lzhenxing.javascaffold.util.exceljar.helper.SSFHelper;
import com.lzhenxing.javascaffold.util.exceljar.model.ExcelFieldMappingInfo;
import com.lzhenxing.javascaffold.util.exceljar.model.ExcelMappingInfo;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ExcelUtil {
    private SSFHelper hssfHelper = new SSFHelper();
    private Map<String, ExcelMappingInfo> excelMappingInfoMapCache = new ConcurrentHashMap();
    private static final Logger LOGGER = LoggerFactory.getLogger(ExcelUtil.class);

    public ExcelUtil() {
    }

    public <T> List<T> importExcel(InputStream inputStream, Class<T> clazz, ExcelFileType excelFileType) throws IOException, ExcelParsingException {
        Object workbook = null;
        if(ExcelFileType.XLS == excelFileType) {
            workbook = new HSSFWorkbook(inputStream);
        } else {
            workbook = new XSSFWorkbook(inputStream);
        }

        return this.buildJaveBeans((Workbook)workbook, clazz);
    }

    public <T> void exportExcel(List<T> list, Class<T> clazz, OutputStream outputStream, ExcelFileType excelFileType) throws IOException, ExcelParsingException, IllegalArgumentException, IllegalAccessException {
        Object workbook = null;
        if(ExcelFileType.XLS == excelFileType) {
            workbook = new HSSFWorkbook();
        } else {
            workbook = new XSSFWorkbook();
        }

        ExcelMappingInfo excelMappingInfo = this.getExcelFieldMappingInfo(clazz);
        Sheet sheet;
        if(StringUtils.isNotBlank(excelMappingInfo.getSheetName())) {
            sheet = ((Workbook)workbook).createSheet(excelMappingInfo.getSheetName());
        } else {
            sheet = ((Workbook)workbook).createSheet();
        }

        sheet.setDefaultColumnWidth(10);
        CellStyle tableHeadStyle = ((Workbook)workbook).createCellStyle();
        tableHeadStyle.setFillForegroundColor((short)40);
        tableHeadStyle.setFillPattern((short)1);
        tableHeadStyle.setBorderBottom((short)1);
        tableHeadStyle.setBorderLeft((short)1);
        tableHeadStyle.setBorderRight((short)1);
        tableHeadStyle.setBorderTop((short)1);
        tableHeadStyle.setAlignment((short)2);
        Font tableHeadfont = ((Workbook)workbook).createFont();
        tableHeadfont.setColor((short)20);
        tableHeadfont.setFontHeightInPoints((short)12);
        tableHeadStyle.setFont(tableHeadfont);
        CellStyle dataStyle = ((Workbook)workbook).createCellStyle();
        dataStyle.setFillForegroundColor((short)43);
        dataStyle.setFillPattern((short)1);
        dataStyle.setBorderBottom((short)1);
        dataStyle.setBorderLeft((short)1);
        dataStyle.setBorderRight((short)1);
        dataStyle.setBorderTop((short)1);
        dataStyle.setAlignment((short)2);
        dataStyle.setVerticalAlignment((short)1);
        Font dataFont = ((Workbook)workbook).createFont();
        dataFont.setBoldweight((short)400);
        dataStyle.setFont(dataFont);
        this.buildExcel(list, clazz, excelMappingInfo, sheet, tableHeadStyle, dataStyle);
        ((Workbook)workbook).write(outputStream);
    }

    private <T> List<T> buildJaveBeans(Workbook workbook, Class<T> clazz) throws ExcelParsingException {
        ArrayList list = new ArrayList();
        ExcelMappingInfo excelMappingInfo = this.getExcelFieldMappingInfo(clazz);
        Sheet sheet = null;
        int currentLocation;
        if(StringUtils.isNotBlank(excelMappingInfo.getSheetName())) {
            sheet = workbook.getSheet(excelMappingInfo.getSheetName());
        } else {
            currentLocation = excelMappingInfo.getSheetIndex();
            sheet = workbook.getSheetAt(currentLocation);
        }

        this.loadHeadNamePositionMapCache(clazz, sheet, excelMappingInfo);
        this.loadFieldMappingInfoListForImport(clazz, excelMappingInfo);

        for(currentLocation = excelMappingInfo.getDataStart(); excelMappingInfo.getDataEnd() == 0 || currentLocation <= excelMappingInfo.getDataEnd(); ++currentLocation) {
            Object object = this.getNewInstance(sheet, clazz, excelMappingInfo, Integer.valueOf(currentLocation), excelMappingInfo.isZeroIfNull());
            if(object == null) {
                break;
            }

            List mappedExcelFields = this.getMappedExcelObjects(clazz);
            Iterator i$ = mappedExcelFields.iterator();

            while(i$.hasNext()) {
                Field mappedField = (Field)i$.next();
                Class fieldType = mappedField.getType();
                List fieldValue = this.buildJaveBeans(workbook, fieldType.equals(List.class)?this.getFieldType(mappedField):fieldType);
                if(fieldType.equals(List.class)) {
                    this.setFieldValue(mappedField, object, fieldValue);
                } else if(!fieldValue.isEmpty()) {
                    this.setFieldValue(mappedField, object, fieldValue.get(0));
                }
            }

            list.add(object);
        }

        return list;
    }

    private <T> T getNewInstance(Sheet sheet, Class<T> clazz, ExcelMappingInfo excelMappingInfo, Integer currentLocation, boolean zeroIfNull) throws ExcelParsingException {
        Object object = this.getInstance(clazz);
        boolean hasValidField = false;

        ExcelFieldMappingInfo excelFieldMappingInfo;
        Object cellValue;
        for(Iterator i$ = excelMappingInfo.getFieldMappingInfoListForImport().iterator(); i$.hasNext(); this.setFieldValue(excelFieldMappingInfo.getField(), object, cellValue)) {
            excelFieldMappingInfo = (ExcelFieldMappingInfo)i$.next();
            if(ParseType.ROW == excelMappingInfo.getParseType()) {
                cellValue = this.hssfHelper.getCellValue(sheet, excelFieldMappingInfo.getField().getType(), excelFieldMappingInfo.getImportDataFormat(), currentLocation, Integer.valueOf(excelFieldMappingInfo.getPosition()));
            } else {
                cellValue = this.hssfHelper.getCellValue(sheet, excelFieldMappingInfo.getField().getType(), excelFieldMappingInfo.getImportDataFormat(), Integer.valueOf(excelFieldMappingInfo.getPosition()), currentLocation);
            }

            if(cellValue != null) {
                hasValidField = true;
            } else if(zeroIfNull) {
                if(excelFieldMappingInfo.getField().getType().equals(Integer.class)) {
                    cellValue = Integer.valueOf(0);
                } else if(excelFieldMappingInfo.getField().getType().equals(Float.class)) {
                    cellValue = Float.valueOf(0.0F);
                } else if(excelFieldMappingInfo.getField().getType().equals(Double.class)) {
                    cellValue = Double.valueOf(0.0D);
                } else if(excelFieldMappingInfo.getField().getType().equals(Long.class)) {
                    cellValue = Long.valueOf(0L);
                }
            }
        }

        if(!hasValidField) {
            return null;
        } else {
            return (T)object;
        }
    }

    private <T> void buildExcel(List<?> list, Class<T> clazz, ExcelMappingInfo excelMappingInfo, Sheet sheet, CellStyle tableHeadStyle, CellStyle dataStyle) throws IllegalArgumentException, IllegalAccessException, ExcelParsingException {
        if(excelMappingInfo.getFieldMappingInfoListForExport() == null) {
            this.loadFieldMappingInfoListForExport(clazz, excelMappingInfo);
        }

        List fieldMappingInfoListForExport = excelMappingInfo.getFieldMappingInfoListForExport();
        int rowIndex = 0;
        int columnIndex = 0;
        if(excelMappingInfo.getParseType() == ParseType.ROW) {
            rowIndex = excelMappingInfo.getHeadPosition() - 1;
        } else {
            columnIndex = excelMappingInfo.getHeadPosition() - 1;
        }

        Cell i$1;
        Iterator var18;
        Row var21;
        if(excelMappingInfo.getParseType() == ParseType.ROW) {
            Row i$ = sheet.getRow(rowIndex);
            if(i$ == null) {
                i$ = sheet.createRow(rowIndex);
            }

            ++rowIndex;
            Iterator data = fieldMappingInfoListForExport.iterator();

            while(data.hasNext()) {
                ExcelFieldMappingInfo mappedExcelFields = (ExcelFieldMappingInfo)data.next();
                if(!mappedExcelFields.isNotExport()) {
                    i$1 = i$.createCell(mappedExcelFields.getPosition());
                    sheet.setColumnWidth(mappedExcelFields.getPosition(), mappedExcelFields.getExportFieldWidth() * 256);
                    i$1.setCellStyle(tableHeadStyle);
                    this.hssfHelper.setCellValue(i$1, mappedExcelFields.getHeadName(), mappedExcelFields);
                }
            }
        } else {
            var18 = fieldMappingInfoListForExport.iterator();

            while(var18.hasNext()) {
                ExcelFieldMappingInfo var19 = (ExcelFieldMappingInfo)var18.next();
                if(!var19.isNotExport()) {
                    var21 = sheet.getRow(var19.getPosition());
                    if(var21 == null) {
                        var21 = sheet.createRow(var19.getPosition());
                    }

                    i$1 = var21.createCell(columnIndex);
                    sheet.setColumnWidth(columnIndex, var19.getExportFieldWidth() * 256);
                    i$1.setCellStyle(tableHeadStyle);
                    this.hssfHelper.setCellValue(i$1, var19.getHeadName(), var19);
                }
            }

            ++columnIndex;
        }

        var18 = list.iterator();

        while(var18.hasNext()) {
            Object var20 = var18.next();
            Cell fieldType;
            Object fieldList;
            Iterator var24;
            if(excelMappingInfo.getParseType() == ParseType.ROW) {
                var21 = sheet.getRow(rowIndex);
                if(var21 == null) {
                    var21 = sheet.createRow(rowIndex);
                }

                ++rowIndex;
                var24 = fieldMappingInfoListForExport.iterator();

                while(var24.hasNext()) {
                    ExcelFieldMappingInfo var26 = (ExcelFieldMappingInfo)var24.next();
                    if(!var26.isNotExport()) {
                        fieldType = var21.createCell(var26.getPosition());
                        fieldList = var26.getField().get(var20);
                        if(fieldList != null) {
                            fieldType.setCellStyle(dataStyle);
                        }

                        this.hssfHelper.setCellValue(fieldType, fieldList, var26);
                    }
                }
            } else {
                Iterator var22 = fieldMappingInfoListForExport.iterator();

                while(var22.hasNext()) {
                    ExcelFieldMappingInfo var23 = (ExcelFieldMappingInfo)var22.next();
                    if(!var23.isNotExport()) {
                        Row mappedField = sheet.getRow(var23.getPosition());
                        if(mappedField == null) {
                            mappedField = sheet.createRow(var23.getPosition());
                        }

                        fieldType = mappedField.createCell(columnIndex);
                        fieldList = var23.getField().get(var20);
                        if(fieldList != null) {
                            fieldType.setCellStyle(dataStyle);
                        }

                        this.hssfHelper.setCellValue(fieldType, fieldList, var23);
                    }
                }

                ++columnIndex;
            }

            List var25 = this.getMappedExcelObjects(clazz);
            var24 = var25.iterator();

            while(var24.hasNext()) {
                Field var27 = (Field)var24.next();
                Class var28 = var27.getType();
                fieldList = null;
                if(var28.equals(List.class)) {
                    var28 = this.getFieldType(var27);
                    fieldList = (List)var27.get(var20);
                } else {
                    Object fieldExcelMappingInfo = var27.get(var20);
                    if(fieldExcelMappingInfo != null) {
                        fieldList = new ArrayList();
                        ((List)fieldList).add(fieldExcelMappingInfo);
                    }
                }

                if(fieldList != null) {
                    ExcelMappingInfo var29 = this.getExcelFieldMappingInfo(var28);
                    this.buildExcel((List)fieldList, var28, var29, sheet, tableHeadStyle, dataStyle);
                }
            }
        }

    }

    private Class<?> getFieldType(Field field) {
        Type type = field.getGenericType();
        if(type instanceof ParameterizedType) {
            ParameterizedType pt = (ParameterizedType)type;
            return (Class)pt.getActualTypeArguments()[0];
        } else {
            return null;
        }
    }

    private <T> List<Field> getMappedExcelObjects(Class<T> clazz) {
        ArrayList fieldList = new ArrayList();
        Field[] fields = clazz.getDeclaredFields();
        Field[] arr$ = fields;
        int len$ = fields.length;

        for(int i$ = 0; i$ < len$; ++i$) {
            Field field = arr$[i$];
            MappedExcelObject mappedExcelObject = (MappedExcelObject)field.getAnnotation(MappedExcelObject.class);
            if(mappedExcelObject != null) {
                field.setAccessible(true);
                fieldList.add(field);
            }
        }

        return fieldList;
    }

    private <T> T getInstance(Class<T> clazz) throws ExcelParsingException {
        Object object = null;

        try {
            object = clazz.newInstance();
            return (T)object;
        } catch (Exception var4) {
            LOGGER.error(var4.getMessage(), var4);
            throw new ExcelParsingException("Exception occured while instantiating the class " + clazz.getName(), var4);
        }
    }

    private <T> void setFieldValue(Field field, T object, Object cellValue) throws ExcelParsingException {
        try {
            field.set(object, cellValue);
        } catch (IllegalArgumentException var5) {
            LOGGER.error(var5.getMessage(), var5);
            throw new ExcelParsingException("Exception occured while setting field value ", var5);
        } catch (IllegalAccessException var6) {
            LOGGER.error(var6.getMessage(), var6);
            throw new ExcelParsingException("Exception occured while setting field value ", var6);
        }
    }

    private <T> ExcelMappingInfo getExcelFieldMappingInfo(Class<T> clazz) throws ExcelParsingException {
        ExcelMappingInfo excelMappingInfo = (ExcelMappingInfo)this.excelMappingInfoMapCache.get(clazz.getName());
        return excelMappingInfo == null?this.loadExcelFieldMappingInfo(clazz):excelMappingInfo;
    }

    private <T> ExcelMappingInfo loadExcelFieldMappingInfo(Class<T> clazz) throws ExcelParsingException {
        ExcelMappingInfo excelMappingInfo = null;
        ExcelObjectMappingByTableHead excelObjectMappingByTableHead = (ExcelObjectMappingByTableHead)clazz.getAnnotation(ExcelObjectMappingByTableHead.class);
        ExcelObjectMappingByPosition excelObjectMappingByPosition = (ExcelObjectMappingByPosition)clazz.getAnnotation(ExcelObjectMappingByPosition.class);
        if(excelObjectMappingByTableHead != null) {
            excelMappingInfo = new ExcelMappingInfo();
            excelMappingInfo.setExcelMappingType(ExcelMappingType.TableHeadMapping);
            excelMappingInfo.setSheetName(excelObjectMappingByTableHead.sheetName());
            excelMappingInfo.setSheetIndex(excelObjectMappingByTableHead.sheetIndex());
            excelMappingInfo.setParseType(excelObjectMappingByTableHead.parseType());
            excelMappingInfo.setHeadPosition(excelObjectMappingByTableHead.headPosition());
            excelMappingInfo.setHeadStart(excelObjectMappingByTableHead.headStart());
            excelMappingInfo.setHeadEnd(excelObjectMappingByTableHead.headEnd());
            excelMappingInfo.setDataStart(excelObjectMappingByTableHead.dataStart());
            excelMappingInfo.setDataEnd(excelObjectMappingByTableHead.dataEnd());
            excelMappingInfo.setZeroIfNull(excelObjectMappingByTableHead.zeroIfNull());
        } else {
            if(excelObjectMappingByPosition == null) {
                throw new ExcelParsingException("Invalid class configuration - ExcelObjectMappingByTableHead or ExcelObjectMappingByPosition annotation missing - " + clazz.getSimpleName());
            }

            excelMappingInfo = new ExcelMappingInfo();
            excelMappingInfo.setExcelMappingType(ExcelMappingType.PositionMapping);
            excelMappingInfo.setSheetName(excelObjectMappingByPosition.sheetName());
            excelMappingInfo.setSheetIndex(excelObjectMappingByPosition.sheetIndex());
            excelMappingInfo.setParseType(excelObjectMappingByPosition.parseType());
            excelMappingInfo.setDataStart(excelObjectMappingByPosition.dataStart());
            excelMappingInfo.setDataEnd(excelObjectMappingByPosition.dataEnd());
            excelMappingInfo.setZeroIfNull(excelObjectMappingByPosition.zeroIfNull());
        }

        this.excelMappingInfoMapCache.put(clazz.getName(), excelMappingInfo);
        return excelMappingInfo;
    }

    private <T> void loadHeadNamePositionMapCache(Class<T> clazz, Sheet sheet, ExcelMappingInfo excelMappingInfo) throws ExcelParsingException {
        HashMap headNamePositionMap = new HashMap();

        for(int currentLocation = excelMappingInfo.getHeadStart(); excelMappingInfo.getHeadEnd() == 0 || currentLocation <= excelMappingInfo.getHeadEnd(); ++currentLocation) {
            String headName;
            if(ParseType.ROW == excelMappingInfo.getParseType()) {
                headName = (String)this.hssfHelper.getCellValue(sheet, String.class, (String)null, Integer.valueOf(excelMappingInfo.getHeadPosition()), Integer.valueOf(currentLocation));
            } else {
                headName = (String)this.hssfHelper.getCellValue(sheet, String.class, (String)null, Integer.valueOf(currentLocation), Integer.valueOf(excelMappingInfo.getHeadPosition()));
            }

            if(headName == null) {
                break;
            }

            headNamePositionMap.put(headName, Integer.valueOf(currentLocation));
        }

        excelMappingInfo.setHeadNamePositionMap(headNamePositionMap);
    }

    private <T> void loadFieldMappingInfoListForImport(Class<T> clazz, ExcelMappingInfo excelMappingInfo) {
        ArrayList fieldMappingInfoListForImport = new ArrayList();
        Field[] fields = clazz.getDeclaredFields();
        Field[] arr$ = fields;
        int len$ = fields.length;

        for(int i$ = 0; i$ < len$; ++i$) {
            Field field = arr$[i$];
            ExcelFieldMappingByHeadName excelFieldMappingByHeadName = (ExcelFieldMappingByHeadName)field.getAnnotation(ExcelFieldMappingByHeadName.class);
            if(excelFieldMappingByHeadName != null) {
                if(excelMappingInfo.getHeadNamePosition(excelFieldMappingByHeadName.headName()) != null) {
                    field.setAccessible(true);
                    ExcelFieldMappingInfo excelFieldMappingByPosition = new ExcelFieldMappingInfo();
                    excelFieldMappingByPosition.setField(field);
                    excelFieldMappingByPosition.setHeadName(excelFieldMappingByHeadName.headName());
                    excelFieldMappingByPosition.setPosition(excelMappingInfo.getHeadNamePosition(excelFieldMappingByHeadName.headName()).intValue());
                    excelFieldMappingByPosition.setImportDataFormat(excelFieldMappingByHeadName.importDataFormat());
                    fieldMappingInfoListForImport.add(excelFieldMappingByPosition);
                }
            } else {
                ExcelFieldMappingByPosition var12 = (ExcelFieldMappingByPosition)field.getAnnotation(ExcelFieldMappingByPosition.class);
                if(var12 != null) {
                    field.setAccessible(true);
                    ExcelFieldMappingInfo excelFieldMappingInfo = new ExcelFieldMappingInfo();
                    excelFieldMappingInfo.setField(field);
                    excelFieldMappingInfo.setPosition(var12.position());
                    excelFieldMappingInfo.setImportDataFormat(var12.importDataFormat());
                    fieldMappingInfoListForImport.add(excelFieldMappingInfo);
                }
            }
        }

        excelMappingInfo.setFieldMappingInfoListForImport(fieldMappingInfoListForImport);
    }

    private <T> void loadFieldMappingInfoListForExport(Class<T> clazz, ExcelMappingInfo excelMappingInfo) {
        ArrayList fieldMappingInfoListForExport = new ArrayList();
        Field[] fields = clazz.getDeclaredFields();
        int position = excelMappingInfo.getHeadStart() - 1;
        Field[] arr$ = fields;
        int len$ = fields.length;

        for(int i$ = 0; i$ < len$; ++i$) {
            Field field = arr$[i$];
            ExcelFieldMappingByHeadName excelFieldMappingByHeadName = (ExcelFieldMappingByHeadName)field.getAnnotation(ExcelFieldMappingByHeadName.class);
            if(excelFieldMappingByHeadName != null && !excelFieldMappingByHeadName.notExport()) {
                field.setAccessible(true);
                ExcelFieldMappingInfo excelFieldMappingInfo = new ExcelFieldMappingInfo();
                excelFieldMappingInfo.setField(field);
                excelFieldMappingInfo.setHeadName(excelFieldMappingByHeadName.headName());
                excelFieldMappingInfo.setPosition(position);
                excelFieldMappingInfo.setExportDataFormat(excelFieldMappingByHeadName.exportDataFormat());
                excelFieldMappingInfo.setExportFieldWidth(excelFieldMappingByHeadName.exportFieldWidth());
                excelFieldMappingInfo.setNotExport(excelFieldMappingByHeadName.notExport());
                fieldMappingInfoListForExport.add(excelFieldMappingInfo);
                ++position;
            }
        }

        excelMappingInfo.setFieldMappingInfoListForExport(fieldMappingInfoListForExport);
    }
}
