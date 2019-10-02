package com.lzhenxing.javascaffold.util.jar;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.commons.io.IOUtils;

/**
 *
 * ClassName: ReadJarResourceFile <br/>
 * Function: <br/>
 *
 * @author zhenxing.liu
 * @date 2019/9/17
 */
public class ReadJarResourceFile {

    /**
     * ��fatjar��Դ�ļ����Ƴ���
     * �ο���http://codepub.cn/2015/04/22/How-to-load-resource-file-in-a-Jar-package-correctly/
     * @param fileName
     */
    public String testGetResourceFromFatjar(String fileName) {
        String outputFile = System.getProperty("user.dir") + File.separator + fileName;
        try {
            File templateFile = new File(outputFile);
            if (templateFile.exists()) {
                return outputFile;
            }
            //��λ��û���fatjar��ȡ
            InputStream inputStream = ReadJarResourceFile.class.getClassLoader().getResourceAsStream(fileName);
            OutputStream outputStream = new FileOutputStream(templateFile);
            IOUtils.copy(inputStream, outputStream);
        } catch (Exception e) {

        }
        return outputFile;
    }
}
