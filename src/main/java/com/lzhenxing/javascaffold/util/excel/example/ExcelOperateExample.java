package com.lzhenxing.javascaffold.util.excel.example;

import com.lzhenxing.javascaffold.util.ListUtils;
import com.lzhenxing.javascaffold.util.excel.ExcelUtil;
import com.vip.common.excelmapping.enums.ExcelFileType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ClassName: ExcelOperateExample <br/>
 * Function: <br/>
 *
 * @author gary.liu
 * @date 2017/5/2
 */
public class ExcelOperateExample {

    private static final Logger LOGGER = LoggerFactory.getLogger(ExcelOperateExample.class);

    private static final String IMPORT_EXCEL_PATH = "/Users/gary/Documents/Job/PDC/ImageStatisReport";


    public void parseExcel(String fileName){

        String excelPath = IMPORT_EXCEL_PATH + "/" + fileName;

        File file = new File(excelPath);
        List<ImportExcelVo> dataList = null;

        try(FileInputStream in = new FileInputStream(file)) {

            LOGGER.info("start reading");
            ExcelUtil excelUtil = new ExcelUtil();
            dataList = excelUtil.importExcel(in, ImportExcelVo.class, ExcelFileType.XLSX);
            if(ListUtils.isNotEmpty(dataList)){
                System.out.print(dataList.toString());
            }

        }catch(FileNotFoundException e){
            LOGGER.info("file not found");
        }catch (Exception e){
            LOGGER.info("parse data exception" + e.getMessage(), e);
        }

    }

    public void exportExcel(){

        //ExcelToolsUtil.saveExcel();


    }

}
