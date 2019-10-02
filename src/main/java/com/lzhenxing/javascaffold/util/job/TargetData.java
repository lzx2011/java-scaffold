package com.lzhenxing.javascaffold.util.job;

import java.util.List;

import com.alibaba.fastjson.annotation.JSONField;

import lombok.Data;

/**
 *
 * ClassName: TargetData <br/>
 * Function: <br/>
 *
 * @author zhenxing.liu
 * @date 2019/9/9
 */
@Data
public class TargetData {

    private String type;

    private List<TargetMapField> mapFields;

}
