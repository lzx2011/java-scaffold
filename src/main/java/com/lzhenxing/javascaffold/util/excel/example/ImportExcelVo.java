package com.lzhenxing.javascaffold.util.excel.example;

import com.vip.common.excelmapping.annotations.ExcelFieldMappingByHeadName;
import com.vip.common.excelmapping.annotations.ExcelObjectMappingByTableHead;
import com.vip.common.excelmapping.enums.ParseType;

/**
 * ClassName: ImportExcelVo <br/>
 * Function: 注意 sheet,sheetName 不填默认为第一个sheet <br/>
 *
 * @author gary.liu
 * @date 2017/5/2
 */
@ExcelObjectMappingByTableHead(sheetName = "", parseType = ParseType.ROW, headPosition = 1, headStart = 1, dataStart = 2)
public class ImportExcelVo {

    @ExcelFieldMappingByHeadName(headName = "id")
    private String id;

    @ExcelFieldMappingByHeadName(headName = "name")
    private String name;

    @ExcelFieldMappingByHeadName(headName = "score")
    private String score;

    @ExcelFieldMappingByHeadName(headName = "time")
    private String time;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}


