package com.lzhenxing.javascaffold.util.excel;


import com.csvreader.CsvReader;
import com.csvreader.CsvWriter;
import com.lzhenxing.javascaffold.util.ListUtils;
import com.lzhenxing.javascaffold.util.exceljar.enums.ExcelFileType;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * ClassName: ExcelToolsUtil <br/>
 * Function: 从页面导入导出,对基本导入导出封装<br/>
 *
 * @author gary.liu
 * @date 2017/4/17
 */
public class ExcelToolsUtil {

    private static Logger logger = LoggerFactory.getLogger(ExcelToolsUtil.class);

    /**
     * 把Excel文件转成List<T>对象
     *
     * @param t
     * @param file
     * @return
     * @throws Exception
     */
    public static <T> List<T> parseImport(Class<T> t, MultipartFile file) throws Exception {
        ExcelUtil excelUtil = new ExcelUtil();
        List<T> list = null;
        String filename = "";
        InputStream stream = null;
        try {
            filename = file.getOriginalFilename();
            stream = file.getInputStream();
            if (filename.endsWith(".xls")) {
                list = excelUtil.importExcel(stream, t, ExcelFileType.XLS);
            } else if (filename.endsWith(".xlsx")) {
                list = excelUtil.importExcel(stream, t, ExcelFileType.XLSX);
            } else {
                throw new Exception("请上传正确的导入模板，格式为xls或xlsx");
            }
            if (ListUtils.isEmpty(list)) {
                throw new Exception("excel解析没数据，请按要求填写数据");
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new Exception("批量导入数据，excel格式不正确");
        } finally {
            if (stream != null) {
                stream.close();
            }
        }
        return list;
    }

    /**
     * 导出excel/csv
     *
     * @param response
     * @param filename
     * @param sheetname
     * @param titles
     * @param datas
     * @return
     * @throws Exception
     */
    public static void export(HttpServletResponse response, String filename, String sheetname, String[] titles,
                              List<String[]> datas) throws Exception {
        if (filename.toLowerCase().endsWith(".csv")) {
            exportCsv(response, filename, titles, datas);
        } else {
            exportExcel(response, filename, sheetname, titles, datas);
        }
    }

    /**
     * 导出excel
     *
     * @param response
     * @param filename
     * @param sheetname
     * @param titles
     * @param datas
     * @throws Exception
     */
    public static void exportExcel(HttpServletResponse response, String filename, String sheetname, String[] titles,
                                   List<String[]> datas) throws Exception {
        filename = new String(filename.getBytes(), "ISO8859_1");

        response.setContentType("application/binary;charset=UTF-8");
        ServletOutputStream outputStream = response.getOutputStream();
        response.setHeader("Content-disposition", "attachment; filename=" + filename);

        // 创建一个workbook 对应一个excel应用文件
        // XSSFWorkbook for *.xlsx, HHSFWorkbook for *.xls
        Workbook workBook = new XSSFWorkbook();

        // 在workbook中添加一个sheet
        Sheet sheet = workBook.createSheet(sheetname);
        // 第一行放表头
        Row headRow = sheet.createRow(0);
        Cell cell = null;
        for (int i = 0; i < titles.length; i++) {
            cell = headRow.createCell(i);
            cell.setCellValue(titles[i]);
        }

        // 从第二行开始放数据
        for (int i = 0; i < datas.size(); i++) {
            Row row = sheet.createRow(i + 1);
            String[] data = datas.get(i);
            for (int j = 0; j < data.length; j++) {
                cell = row.createCell(j);
                cell.setCellValue(data[j]);
            }
        }

        try {
            workBook.write(outputStream);
            outputStream.flush();
            outputStream.close();
        } finally {
            try {
                outputStream.close();
            } catch (IOException e) {
                logger.error(e.getMessage());
            }
        }
    }

    /**
     * 导出csv
     *
     * @param response
     * @param filename
     * @param titles
     * @param datas
     * @throws Exception
     */
    public static void exportCsv(HttpServletResponse response, String filename, String[] titles, List<String[]> datas)
            throws Exception {
        filename = new String(filename.getBytes(), "ISO8859_1");

        response.setContentType("application/binary;charset=UTF-8");
        ServletOutputStream outputStream = response.getOutputStream();
        response.setHeader("Content-disposition", "attachment; filename=" + filename);

        CsvWriter csvOutput = new CsvWriter(outputStream, ',', Charset.forName("GBK"));
        try {
            // 第一行放表头
            for (String title : titles) {
                csvOutput.write(title);
            }
            csvOutput.endRecord();

            // 从第二行开始放数据
            for (String[] data : datas) {
                for (String s : data) {
                    csvOutput.write(StringUtils.trim(s));
                }
                csvOutput.endRecord();
            }
        } finally {
            if (csvOutput != null) {
                csvOutput.close();
            }
        }
    }

    /**
     * 解析csv/xls/xlsx
     *
     * @param file
     * @param ignoreFirstRow
     * @param columns
     *            若为-1则表示动态获取
     * @return
     * @throws Exception
     */
    public static List<String[]> parse(MultipartFile file, boolean ignoreFirstRow, int columns) throws Exception {
        if (file.getOriginalFilename().toLowerCase().endsWith(".csv")) {
            return parseCsv(file, ignoreFirstRow, columns);
        } else {
            return parseExcel(file, ignoreFirstRow, columns);
        }
    }

    /**
     * 解析excel
     *
     * @param file
     * @param ignoreFirstRow
     *            是否忽略第一行
     * @param columns
     *            列数
     * @return
     * @throws Exception
     */
    public static List<String[]> parseExcel(MultipartFile file, boolean ignoreFirstRow, int columns) throws Exception {
        List<String[]> datas = new ArrayList<String[]>();

        InputStream input = null;
        try {
            input = file.getInputStream();
            Workbook workbook = null;
            if (file.getOriginalFilename().toLowerCase().endsWith(".xlsx")) {
                workbook = new XSSFWorkbook(input);
            } else {
                workbook = new HSSFWorkbook(input);
            }
            Sheet sheet = workbook.getSheetAt(0);
            if (sheet != null) {
                // 若不指定列数，则自动从表头获取
                if (columns == -1 && sheet.getPhysicalNumberOfRows() > 0) {
                    Row row = sheet.getRow(0);
                    columns = row.getPhysicalNumberOfCells();
                }

                int i = ignoreFirstRow ? 1 : 0;// 是否忽略第一行（可能为title）
                for (; i < sheet.getPhysicalNumberOfRows(); i++) {
                    Row row = sheet.getRow(i);
                    if (row == null) {
                        break;
                    }
                    String[] data = new String[columns];
                    for (int j = 0; j < columns; j++) {
                        Cell cell = row.getCell(j);
                        if (cell != null) {
                            cell.setCellType(Cell.CELL_TYPE_STRING);
                            data[j] = cell.toString().trim();
                            if (StringUtils.isEmpty(data[j])) {
                                data[j] = null;
                            }
                        } else {
                            data[j] = null;
                        }
                    }
                    datas.add(data);
                }
            }
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    logger.error(e.getMessage());
                }
            }
        }
        return datas;
    }

    /**
     * 解析csv
     *
     * @param file
     * @param ignoreFirstRow
     * @param columns
     * @return
     * @throws Exception
     */
    public static List<String[]> parseCsv(MultipartFile file, boolean ignoreFirstRow, int columns) throws Exception {
        List<String[]> datas = new ArrayList<String[]>();

        InputStream input = null;
        try {
            input = file.getInputStream();
            CsvReader csvReader = new CsvReader(input, Charset.forName("GBK"));

            // 忽略第一行
            if (ignoreFirstRow) {
                csvReader.readHeaders();
            }
            while (csvReader.readRecord()) {
                // 若不指定列数，则自动获取
                if (columns == -1) {
                    columns = csvReader.getColumnCount();
                }

                String[] data = new String[columns];
                for (int i = 0; i < columns; i++) {
                    data[i] = StringUtils.trim(csvReader.get(i));
                    if (StringUtils.isEmpty(data[i])) {
                        data[i] = null;
                    }
                }
                datas.add(data);
            }
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    logger.error(e.getMessage());
                }
            }
        }
        return datas;
    }

    /**
     * 解析csv/xls/xlsx  与旧的不同在于columns=-1时候会根据整个Excel去拿最大的列数
     *
     * @param file
     * @param ignoreFirstRow
     * @param columns
     *            若为-1则表示动态获取
     * @return
     * @throws Exception
     */
    public static List<String[]> parseNew(MultipartFile file, boolean ignoreFirstRow, int columns) throws Exception {
        if (file.getOriginalFilename().toLowerCase().endsWith(".csv")) {
            return parseNewCsv(file, ignoreFirstRow, columns);
        } else {
            return parseNewExcel(file, ignoreFirstRow, columns);
        }
    }

    /**
     * 获取CSV的内容 与旧的不同在于会根据整个csv去拿最大的列数
     * @param file
     * @param ignoreFirstRow
     * @param columns
     * @return
     * @throws Exception
     */
    public static List<String[]> parseNewCsv(MultipartFile file, boolean ignoreFirstRow, int columns) throws Exception {
        List<String[]> datas = new ArrayList<String[]>();

        InputStream input = null;
        try {
            input = file.getInputStream();
            CsvReader csvReader = new CsvReader(input, Charset.forName("GBK"));

            // 忽略第一行
            if (ignoreFirstRow) {
                csvReader.readHeaders();
            }
            int _columns = 0;
            if (columns == -1) {//自动获取列数
                while (csvReader.readRecord()) {
                    _columns = csvReader.getColumnCount();
                    if (_columns > columns) {
                        columns = _columns;
                    }
                }
            }
            while (csvReader.readRecord()) {
                String[] data = new String[columns];
                for (int i = 0; i < columns; i++) {
                    data[i] = StringUtils.trim(csvReader.get(i));
                    if (StringUtils.isEmpty(data[i])) {
                        data[i] = null;
                    }
                }
                datas.add(data);
            }
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    logger.error(e.getMessage());
                }
            }
        }
        return datas;
    }

    /**
     * 获取Excel的内容 与旧的不同在于会根据整个Excel去拿最大的列数
     *
     * @param file
     * @param ignoreFirstRow
     *            是否忽略第一行
     * @param columns
     *            列数
     * @return
     * @throws Exception
     */
    public static List<String[]> parseNewExcel(MultipartFile file, boolean ignoreFirstRow, int columns) throws Exception {
        List<String[]> datas = new ArrayList<String[]>();

        InputStream input = null;
        try {
            input = file.getInputStream();
            Workbook workbook = null;
            if (file.getOriginalFilename().toLowerCase().endsWith(".xlsx")) {
                workbook = new XSSFWorkbook(input);
            } else {
                workbook = new HSSFWorkbook(input);
            }
            Sheet sheet = workbook.getSheetAt(0);
            if (sheet != null) {
                // 若不指定列数，则自动从表头获取

                int _columns = 0;
                if (columns == -1 && sheet.getPhysicalNumberOfRows() > 0) {//自动获取列数
                    for (int i=0; i < sheet.getPhysicalNumberOfRows(); i++) {
                        Row row = sheet.getRow(i);
                        if (row != null) {
                            _columns = row.getPhysicalNumberOfCells();
                            if (_columns > columns) {
                                columns = _columns;
                            }
                        }
                    }
                }

                int i = ignoreFirstRow ? 1 : 0;// 是否忽略第一行（可能为title）
                for (; i < sheet.getPhysicalNumberOfRows(); i++) {
                    Row row = sheet.getRow(i);
                    if (row == null) {
                        break;
                    }
                    String[] data = new String[columns];
                    for (int j = 0; j < columns; j++) {
                        Cell cell = row.getCell(j);
                        if (cell != null) {
                            cell.setCellType(Cell.CELL_TYPE_STRING);
                            data[j] = cell.toString().trim();
                            if (StringUtils.isEmpty(data[j])) {
                                data[j] = null;
                            }
                        } else {
                            data[j] = null;
                        }
                    }
                    datas.add(data);
                }
            }
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    logger.error(e.getMessage());
                }
            }
        }
        return datas;
    }

    /**
     * 在excel某行某列写上内容  不带颜色
     * @param row
     * @param content
     */
    public static void setCellContent(HSSFRow row, int colum, String content) {
        HSSFCell cell = row.createCell(colum);// 创建一列
        cell.setCellType(HSSFCell.CELL_TYPE_STRING);
        cell.setCellValue(content);
    }


    /**
     * 在excel某行某列写上内容  带颜色
     * @param row
     * @param content
     * @param workbook
     * @param color
     */
    public static void setCellContentWithColor(HSSFRow row, int colum, String content, HSSFWorkbook workbook, Short color) {
        HSSFCell cell = row.createCell(colum);// 创建一列
        cell.setCellType(HSSFCell.CELL_TYPE_STRING);
        if (color != null) {
            cell.setCellStyle(getColorStyle(workbook, color));
        }
        cell.setCellValue(content);
    }


    private static HSSFCellStyle getColorStyle(HSSFWorkbook workbook, short color) {
        HSSFCellStyle ztStyle = workbook.createCellStyle();
        Font ztFont = workbook.createFont();
        ztFont.setColor(HSSFColor.WHITE.index); // 将字体设置为“白色”
        ztStyle.setFont(ztFont); // 将字\体应用到样式上面
        ztStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        ztStyle.setFillForegroundColor(color);
        return ztStyle;
    }

    /**
     * 打印错误信息
     * @param request
     * @param response
     * @param msg
     */
    public static void outMsg(HttpServletRequest request, HttpServletResponse response, String msg) {
        PrintWriter out;
        try {
            out = response.getWriter();
        } catch (IOException e) {
            logger.error(e.getMessage());
            return;
        }
        response.setContentType("text/html;charset=UTF-8");
        try {
            request.setCharacterEncoding("UTF-8");
        } catch (UnsupportedEncodingException e) {
            logger.error(e.getMessage());
        }
        out.print(msg);
    }

    /**
     * 更新excel文件并导出
     * @param response
     * @param excelFile
     * @param sheetNames
     * @param titles
     * @param data
     * @throws IOException
     */
    public static void updateExcelAndExport(HttpServletResponse response, File excelFile, List<String> sheetNames,
                                            List<String[]> titles, List<String[]> data, boolean isIgnoreFirstRow) throws IOException, InvalidFormatException {
        response.setContentType("application/binary;charset=UTF-8");
        response.setHeader("Content-disposition", "attachment; filename=" + excelFile.getName());
        try(OutputStream outputStream = response.getOutputStream()){
            Workbook workbook = updateExcel(excelFile,sheetNames,titles,data,isIgnoreFirstRow);
            if (workbook != null){
                workbook.write(outputStream);
                outputStream.flush();
            }
        }
    }

    /**
     * 保存excel到本地
     * @param destPath
     * @param excelFile
     * @param sheetNames
     * @param titles
     * @param data
     * @param isIgnoreFirstRow
     * @throws IOException
     * @throws InvalidFormatException
     */
    public static void saveExcel(String destPath,File excelFile,List<String> sheetNames, List<String[]> titles,
                                 List<String[]> data,boolean isIgnoreFirstRow) throws IOException, InvalidFormatException {
        File destFile = new File(destPath);
        //为了保证导出的目录不重复
        if (destFile.exists()){
            destFile.delete();
        }
        try(OutputStream outputStream = new FileOutputStream(destFile)){
            Workbook workbook = updateExcel(excelFile,sheetNames,titles,data,isIgnoreFirstRow);
            if (workbook != null){
                workbook.write(outputStream);
                outputStream.flush();
            }
        }
    }



    /**
     * 更新excel文件
     * @param excelFile
     * @param sheetNames
     * @param titles
     * @param data
     * @return
     * @throws IOException
     */
    public static Workbook updateExcel(File excelFile, List<String> sheetNames, List<String[]> titles,
                                       List<String[]> data, boolean isIgnoreFirstRow) throws IOException, InvalidFormatException {
        String fileName = excelFile.getName();
        InputStream fis = new FileInputStream(excelFile);
        Workbook workBook = null;
        if(fileName.toLowerCase().endsWith("xlsx")){
            workBook = new XSSFWorkbook(fis);
        }else if(fileName.toLowerCase().endsWith("xls")){
            workBook = new HSSFWorkbook(fis);
        }
        for (int i = 0,size = sheetNames.size();i < size;i++){
            String sheetName = sheetNames.get(i);
            Sheet sheet = workBook.getSheet(sheetName);
            int lastRowNum = isIgnoreFirstRow ? 0 : 1;
            if (!ListUtils.isEmpty(titles)){
                Row titleRow = sheet.createRow(lastRowNum);
                String[] titleArr = titles.get(i);
                Cell titleCell;
                for (int j = 0;j < titleArr.length;j++) {
                    titleCell = titleRow.createCell(j);
                    titleCell.setCellValue(titleArr[j]);
                }
            }else{
                String[] dataArr;
                Row dataRow;
                Cell dataCell;
                for (int k = 0,dataSize = data.size();k < dataSize;k++){
                    dataArr = data.get(k);
                    dataRow = sheet.createRow(lastRowNum + k);
                    for (int l = 0;l < dataArr.length;l++){
                        dataCell = dataRow.createCell(l);
                        dataCell.setCellValue(dataArr[l]);
                    }
                }
            }
        }
        return workBook;
    }

}
