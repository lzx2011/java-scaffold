package com.lzhenxing.javascaffold.util.exceljar.helper;

import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.lzhenxing.javascaffold.util.exceljar.exception.ExcelParsingException;
import com.lzhenxing.javascaffold.util.exceljar.model.ExcelFieldMappingInfo;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.RichTextString;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
/**
 * ClassName: SSFHelper <br/>
 * Function: <br/>
 *
 * @author gary.liu
 * @date 2017/6/30
 */

public class SSFHelper
{
    private static final String DATA_TYPE_NOT_SUPPORTED = "{0} Data type not supported for parsing";
    private static final String INVALID_NUMBER_FORMAT = "Invalid number found in sheet {0} at row {1}, column {2}";
    private static final String INVALID_DATE_FORMAT = "Invalid date found in sheet {0} at row {1}, column {2}";

    public <T> T getCellValue(Sheet sheet, Class<T> type, String dataFormat, Integer row, Integer col)
            throws ExcelParsingException
    {
        Cell cell = getCell(sheet, row.intValue(), col.intValue());
        if (type.equals(String.class)) {
            return cell == null ? null : (T)getStringCell(cell, dataFormat);
        }
        if (type.equals(Date.class)) {
            return cell == null ? null : (T)getDateCell(cell, new Object[] { sheet.getSheetName(), row, col });
        }
        if (type.equals(Integer.class)) {
            return (T)getIntegerCell(cell, new Object[] { sheet.getSheetName(), row, col });
        }
        if (type.equals(Float.class)) {
            return (T)getFloatCell(cell, new Object[] { sheet.getSheetName(), row, col });
        }
        if (type.equals(Double.class)) {
            return (T)getDoubleCell(cell, new Object[] { sheet.getSheetName(), row, col });
        }
        if (type.equals(Long.class)) {
            return (T)getLongCell(cell, new Object[] { sheet.getSheetName(), row, col });
        }
        if (type.equals(Boolean.class)) {
            return (T)getBooleanCell(cell, sheet.getSheetName(), row, col);
        }
        throw new ExcelParsingException(getErrorMessage("{0} Data type not supported for parsing", new Object[] { type.getName() }));
    }

    public void setCellValue(Cell cell, Object value, ExcelFieldMappingInfo excelFieldMappingInfo)
            throws ExcelParsingException
    {
        if (value == null) {
            return;
        }
        if ((value instanceof Integer))
        {
            int intValue = ((Integer)value).intValue();
            cell.setCellValue(intValue);
        }
        else if ((value instanceof Float))
        {
            float fValue = ((Float)value).floatValue();
            cell.setCellValue(fValue);
        }
        else if ((value instanceof Double))
        {
            double dValue = ((Double)value).doubleValue();
            cell.setCellValue(dValue);
        }
        else if ((value instanceof Long))
        {
            long longValue = ((Long)value).longValue();
            cell.setCellValue(longValue);
        }
        else if ((value instanceof Boolean))
        {
            boolean bValue = ((Boolean)value).booleanValue();
            cell.setCellValue(bValue);
        }
        else if ((value instanceof Date))
        {
            Date date = (Date)value;
            SimpleDateFormat sdf = new SimpleDateFormat(excelFieldMappingInfo.getExportDataFormat());
            String textValue = sdf.format(date);
            cell.setCellValue(textValue);
        }
        else if ((value instanceof String))
        {
            String textValue = value.toString();
            Pattern p = Pattern.compile("^//d+(//.//d+)?{1}");
            Matcher matcher = p.matcher(textValue);
            if (matcher.matches()) {
                cell.setCellValue(Double.parseDouble(textValue));
            } else {
                cell.setCellValue(textValue);
            }
        }
        else
        {
            throw new ExcelParsingException(getErrorMessage("{0} Data type not supported for parsing", new Object[] { value.getClass().getName() }));
        }
    }

    Cell getCell(Sheet sheet, int rowNumber, int columnNumber)
    {
        Row row = sheet.getRow(rowNumber - 1);
        return row == null ? null : row.getCell(columnNumber - 1);
    }

    String getStringCell(Cell cell, String dataFormat)
    {
        String value = null;
        if (cell.getCellType() == 2)
        {
            int type = cell.getCachedFormulaResultType();
            switch (type)
            {
                case 1:
                    RichTextString richTextString = cell.getRichStringCellValue();
                    if (richTextString != null) {
                        value = richTextString.getString();
                    }
                    break;
                case 0:
                    if (DateUtil.isCellDateFormatted(cell))
                    {
                        Date date = DateUtil.getJavaDate(cell.getNumericCellValue());
                        if (date != null)
                        {
                            if (dataFormat == null) {
                                dataFormat = "yyyy-MM-dd HH:mm:ss";
                            }
                            SimpleDateFormat formatter = new SimpleDateFormat(dataFormat);
                            value = formatter.format(date).toString();
                        }
                        else
                        {
                            double doubleNum = cell.getNumericCellValue();
                            long longNum = new Double(doubleNum).longValue();
                            if (doubleNum == longNum) {
                                value = "" + longNum;
                            } else {
                                value = "" + doubleNum;
                            }
                        }
                    }
                    else
                    {
                        double doubleNum = cell.getNumericCellValue();
                        long longNum = new Double(doubleNum).longValue();
                        if (doubleNum == longNum) {
                            value = "" + longNum;
                        } else {
                            value = "" + doubleNum;
                        }
                    }
                    break;
                case 4:
                    value = cell.getBooleanCellValue() ? "TRUE" : "FALSE";
                    break;
            }
        }
        else if (cell.getCellType() == 1)
        {
            RichTextString richTextString = cell.getRichStringCellValue();
            if (richTextString != null) {
                value = richTextString.getString();
            }
        }
        else if (cell.getCellType() == 0)
        {
            if (DateUtil.isCellDateFormatted(cell))
            {
                Date date = DateUtil.getJavaDate(cell.getNumericCellValue());
                if (date != null)
                {
                    if (dataFormat == null) {
                        dataFormat = "yyyy-MM-dd HH:mm:ss";
                    }
                    SimpleDateFormat formatter = new SimpleDateFormat(dataFormat);
                    value = formatter.format(date).toString();
                }
                else
                {
                    double doubleNum = cell.getNumericCellValue();
                    long longNum = new Double(doubleNum).longValue();
                    if (doubleNum == longNum) {
                        value = "" + longNum;
                    } else {
                        value = "" + doubleNum;
                    }
                }
            }
            else
            {
                double doubleNum = cell.getNumericCellValue();
                long longNum = new Double(doubleNum).longValue();
                if (doubleNum == longNum) {
                    value = "" + longNum;
                } else {
                    value = "" + doubleNum;
                }
            }
        }
        else if (cell.getCellType() == 4)
        {
            value = cell.getBooleanCellValue() ? "TRUE" : "FALSE";
        }
        if (value != null)
        {
            value = value.trim();
            if (value.length() <= 0) {
                value = null;
            }
        }
        return value;
    }

    Date getDateCell(Cell cell, Object... errorMessageArgs)
            throws ExcelParsingException
    {
        try
        {
            if (!DateUtil.isCellDateFormatted(cell)) {
                throw new ExcelParsingException(getErrorMessage("Invalid date found in sheet {0} at row {1}, column {2}", errorMessageArgs));
            }
        }
        catch (IllegalStateException illegalStateException)
        {
            throw new ExcelParsingException(getErrorMessage("Invalid date found in sheet {0} at row {1}, column {2}", errorMessageArgs));
        }
        return DateUtil.getJavaDate(cell.getNumericCellValue());
    }

    private String getErrorMessage(String errorMessage, Object... errorMessageArgs)
    {
        return MessageFormat.format(errorMessage, errorMessageArgs);
    }

    Double getDoubleCell(Cell cell, Object... errorMessageArgs)
            throws ExcelParsingException
    {
        if (cell == null) {
            return null;
        }
        Double value = null;
        switch (cell.getCellType())
        {
            case 0:
                value = Double.valueOf(cell.getNumericCellValue());
                break;
            case 2:
                int type = cell.getCachedFormulaResultType();
                if (type == 0) {
                    value = Double.valueOf(cell.getNumericCellValue());
                } else if (type == 1) {
                    try
                    {
                        String s = cell.getRichStringCellValue().getString();
                        if (s != null) {
                            s = s.trim();
                        }
                        value = Double.valueOf(Double.parseDouble(s));
                    }
                    catch (Exception e)
                    {
                        throw new ExcelParsingException(getErrorMessage("Invalid number found in sheet {0} at row {1}, column {2}", errorMessageArgs));
                    }
                }
                break;
            case 1:
                try
                {
                    String s = cell.getRichStringCellValue().getString();
                    if (s != null) {
                        s = s.trim();
                    }
                    value = Double.valueOf(Double.parseDouble(s));
                }
                catch (Exception e)
                {
                    throw new ExcelParsingException(getErrorMessage("Invalid number found in sheet {0} at row {1}, column {2}", errorMessageArgs));
                }
            case 3:
                break;
            default:
                throw new ExcelParsingException(getErrorMessage("Invalid number found in sheet {0} at row {1}, column {2}", errorMessageArgs));
        }
        return value;
    }

    Float getFloatCell(Cell cell, Object... errorMessageArgs)
            throws ExcelParsingException
    {
        Double doubleValue = getDoubleCell(cell, errorMessageArgs);
        return doubleValue == null ? null : Float.valueOf(doubleValue.floatValue());
    }

    Long getLongCell(Cell cell, Object... errorMessageArgs)
            throws ExcelParsingException
    {
        Double doubleValue = getNumberWithoutDecimals(cell, errorMessageArgs);
        return doubleValue == null ? null : Long.valueOf(doubleValue.longValue());
    }

    Integer getIntegerCell(Cell cell, Object... errorMessageArgs)
            throws ExcelParsingException
    {
        Double doubleValue = getNumberWithoutDecimals(cell, errorMessageArgs);
        return doubleValue == null ? null : Integer.valueOf(doubleValue.intValue());
    }

    private Double getNumberWithoutDecimals(Cell cell, Object... errorMessageArgs)
            throws ExcelParsingException
    {
        Double doubleValue = getDoubleCell(cell, errorMessageArgs);
        if ((doubleValue != null) && (doubleValue.doubleValue() % 1.0D != 0.0D)) {
            throw new ExcelParsingException(getErrorMessage("Invalid number found in sheet {0} at row {1}, column {2}", errorMessageArgs));
        }
        return doubleValue;
    }

    Boolean getBooleanCell(Cell cell, String sheetName, Integer row, Integer col)
    {
        Boolean value = null;
        int type = cell.getCachedFormulaResultType();
        if ((type == 4) || (type == 2)) {
            value = Boolean.valueOf(cell.getBooleanCellValue());
        }
        return value;
    }
}
