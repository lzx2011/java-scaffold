package com.lzhenxing.javascaffold.util.excel.example;

import com.lzhenxing.javascaffold.util.ListUtils;
import com.lzhenxing.javascaffold.util.excel.ExcelToolsUtil;
import com.lzhenxing.javascaffold.util.excel.ExcelUtil;
import com.vip.common.excelmapping.enums.ExcelFileType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * ClassName: ExcelOperateExample <br/>
 * Function: 导入导出例子<br/>
 *
 * @author gary.liu
 * @date 2017/5/2
 */
public class ExcelOperateExample {

    private static final Logger LOGGER = LoggerFactory.getLogger(ExcelOperateExample.class);

    private static final String IMPORT_EXCEL_PATH = "/Users/gary/Documents/Job/PDC/ImageStatisReport";

    /**
     * 解析 excel 示例
     * @param fileName
     */
    public static void parseExcel(String fileName){

        String excelPath = IMPORT_EXCEL_PATH + File.separator + fileName;

        File file = new File(excelPath);
        List<ImportExcelVo> dataList;

        try(FileInputStream in = new FileInputStream(file)) {

            LOGGER.info("start reading");
            ExcelUtil excelUtil = new ExcelUtil();
            dataList = excelUtil.importExcel(in, ImportExcelVo.class, ExcelFileType.XLSX);
            if(ListUtils.isNotEmpty(dataList)){
                System.out.println(dataList.toString());

                for(ImportExcelVo importExcelVo : dataList){
                    System.out.println(importExcelVo.getName());

                }
            }

        }catch(FileNotFoundException e){
            LOGGER.info("file not found");
        }catch (Exception e){
            LOGGER.info("parse data exception" + e.getMessage(), e);
        }

    }

    public static void exportExcel(){

        //这个路径要存在才行
        String destPath = "/Users/gary/Documents/Job/PDC/ImageStatisReport/test";
        String fileName = "exportTest.xlsx";
        String fullPath = destPath + File.separator + fileName;
        File file = new File(fullPath);

        ImportExcelVo importExcelVo = new ImportExcelVo();
        importExcelVo.setId("1");
        importExcelVo.setName("liu");
        importExcelVo.setScore("60");
        importExcelVo.setTime("5");
        List<ImportExcelVo> voList = new ArrayList<>();
        voList.add(importExcelVo);

        try(FileOutputStream out = new FileOutputStream(file)){

            ExcelUtil excelUtil = new ExcelUtil();
            excelUtil.exportExcel(voList, ImportExcelVo.class, out, ExcelFileType.XLSX);

        }catch (Exception e){
            e.printStackTrace();
        }

        //List<String> sheetNames = new ArrayList<>();
        //sheetNames.add("导出练习sheet1");
        //List<String[]> titles = new ArrayList<>();
        //String[] title = new String[]{"id", "name", "age"};
        //titles.add(title);
        //
        //List<String[]> datas = new ArrayList<>();
        //String[] data1 = new String[]{"1", "liu", "20"};
        //String[] data2 = new String[]{"2", "feng", "25"};
        //datas.add(data1);
        //datas.add(data2);
        //
        //try{
        //    ExcelToolsUtil.saveExcel(destPath, file, sheetNames, titles, datas, false);
        //} catch (Exception e){
        //    e.printStackTrace();
        //}
    }

    public static void main(String[] args){
        //解析excel示例
        //String fileName = "parseTest.xlsx";
        //parseExcel(fileName);

        //导出 excel 示例
        exportExcel();

    }

}
