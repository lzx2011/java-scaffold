package com.lzhenxing.javascaffold.util.pdf;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Objects;

import com.google.common.collect.Maps;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.AcroFields;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfCopy;
import com.itextpdf.text.pdf.PdfImportedPage;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;
import com.itextpdf.text.pdf.PdfWriter;
import org.apache.commons.lang3.StringUtils;
import sun.misc.BASE64Decoder;

/**
 *
 * ClassName: ItextPdfOperate <br/>
 * Function: itext 操作pdf<br/>
 *
 * @author zhenxing.liu
 * @date 2019/8/20
 */
public class ItextPdfOperate {

    public static String filePath = "/Users/gary.liu/my_doc_itext.pdf";

    public static String testFilePath = "/Users/gary.liu/Documents/pdfTest/test.pdf";
    public static String saveFilePath = "/Users/gary.liu/Documents/pdfTest";
    public static String imageFilePath = "/Users/gary.liu/Documents/pdfTest/signature.jpeg";
    public static String formTemplate = "/Users/gary.liu/Documents/pdfTest/birthOneThingTemplateForm1.pdf";
    //public static String testTemplate = "/Users/gary.liu/Documents/pdfTest/testNew.pdf";
    //public static String testTemplate = "/Users/gary.liu/Documents/pdfTest/birthOneThingTemplateForm.pdf";
    public static String testTemplate = "/Users/gary.liu/Documents/Job/Code/gov-trade/birthOneThingTemplate.pdf";


    public static String imageString = "/9j/4AAQSkZJRgABAQAAAQABAAD/2wCEAAkGBwgHBgkIBwgWChULDRYPDQwWDRsgIRAWIB0iIiAd\n"
        +"Hx8kKDQsJCYxJx8fLUctMTMuODhDIys1QD9ANzQ5QzcBCgoKDQwNGBAOFSslFSUwKy0rLS0tKyst\n"
        +"KzcrKy0rKysrKystKysrKysrKysrKysrKysrKysrKysrKysrKysrK//AABEIADIAMgMBIgACEQED\n"
        +"EQH/xAAcAAACAgMBAQAAAAAAAAAAAAAFBgAEAQMHCAL/xAA4EAABAgUBBAYIBQUAAAAAAAABAgMA\n"
        +"BBESIQUGEzFBFCIyUWFxQoGRobHR8PEVI0OSwQcWM1Ph/8QAGQEBAQADAQAAAAAAAAAAAAAAAwIA\n"
        +"AQQF/8QAHxEAAgICAgMBAAAAAAAAAAAAAAECERIhMTIiQWED/9oADAMBAAIRAxEAPwDoqbRgY8o+\n"
        +"wuBXS20C5CwQnC88u+NidQQneEmo9GhrdgZja/VMxwYn7X7STk3rCtF0ub6Ghk2vzA7S3ONiTyoO\n"
        +"J+ivaXrmv6FtA3LTGoKmWybrFqrejnnkYmizz9Jl95gPKMw+XkkCqFlRzxwcj2Qt7T6hLy2pyvQF\n"
        +"k7jKklVePKIc25aEwSjZ6IlphD7DbzZqHEhSfIxsLlKU5mkJ+w2vMTWzUo4+uwiqaU5A0g2vWZK4\n"
        +"AOk0PJB+ULkgqDN8SBf4xJ/7D+wxiMyRlMCNLQ246hIupzKR1hxBjCm7W02mgSKAUyD9o1PrcRce\n"
        +"yRgK7xzijqU8JYTD1SQ2jq+Z4D+fXHmxTbpHY9bYKb2KZ1HaWfc6YJZD6gspSmpup1uOBmp58YXP\n"
        +"6jbCN7OOykzKTSnkTVySV0qhYpzAGKH3Q+7IVU6p5xVST1j3mGXaiTlZ3QlqmZdL+4UlxsKTWiqg\n"
        +"fCsdK0wXtClsdOaRK7Pycm8m1bbXWBRW5RNcQUE/p9QpLIN1p/x9/wBoXNVkkOp6bK4xVaBz8R4x\n"
        +"VlZ5aCpVxeCQSoE5FEg/yYyTa4JWxs6dKn9EfsESBzc5IKbSpRpUAkdaMRNyLpF11Mw8KN1uCeqL\n"
        +"aVqMK8PEciKQC2hcNzMutFbDcSFD8zOM+uCji3ZRLcqyL3HG1kpJ86Z5HNIX9UuXMsS7a95uxuwR\n"
        +"mpuPt7vVEw07ZUvLSDuj6jpyGkXTqpYoINjqOB8FDBhqZWuZ2becWK7wLcA8LiR7oSpqSRMTUtKs\n"
        +"oKQAEmqu0vmeA76e2OiNs26a4wB2WrPdFe7J+HPa9FfUmpAUbh9VgZqEq2lbk3KO0IbVcmvh/wAg\n"
        +"pPtkA09E4HvEUyRMN4RSmez7RDONoK6KbeoTO7TRkHAzU/KMRusc9FtJ7vyzGYLEvILqJ/udOfQV\n"
        +"8IWdGUrfJVdkIWQa8O1EiQb6jQ7DlooH4rJ45H4w7s9hzzMSJFEMQJ79byHxgVI4fIGOt8okSOmP\n"
        +"AL5L60pvVjn3RIkSCNn/2Q==";

    public static void createPdf() throws Exception{
        // 生成File所在文件夹
        String filename = filePath;
        File file = new File(filename);
        file.getParentFile().mkdirs();

        // 生成PDF文档
        //BaseFont fontChinese = BaseFont.createFont("C:/Windows/Fonts/STSONG.TTF", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);

        Document document = new Document();
        PdfWriter.getInstance(document, new FileOutputStream(filename));
        document.open();
        document.add(new Paragraph("Hello World!"));
        document.close();
    }

    public static void addFormNormal(){
        //String templatePath = "pdfFolder/template_demo.pdf";
        //生成的新文件路径
        String newPDFPath = saveFilePath + "/addFromItext.pdf";
        PdfReader reader;
        FileOutputStream out;
        ByteArrayOutputStream bos;
        PdfStamper stamper;
        try {
            out = new FileOutputStream(newPDFPath);//输出流
            reader = new PdfReader(testTemplate);//读取pdf模板
            bos = new ByteArrayOutputStream();
            stamper = new PdfStamper(reader, bos);
            AcroFields form = stamper.getAcroFields();

            //BaseFont bfChinese = BaseFont.createFont("STSong-Light",
            //    "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);

            form.addSubstitutionFont(BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED));

            //form.set()
            //String[] str = {"12垫付","小鲁","男","1994-00-00",
            //    "130222111133338888"
            //    ,"河北省唐山市"};
            String[] str = {"112垫付"};
            int i = 0;
            java.util.Iterator<String> it = form.getFields().keySet().iterator();
            while(it.hasNext()){
                String name = it.next().toString();
                System.out.println(name);
                if(i >= 1){
                    break;
                }
                //System.out.println(i);
                System.out.println(name + " value：" + form.getField(name));
                //form.setFieldProperty(name, "textfont", bfChinese, null);
                form.setField(name, str[i++]);
                //form.setField(name, new String(str[i++].getBytes("UTF-8"), "UTF-8"));
                System.out.println(form.getField(name));
            }
            stamper.setFormFlattening(true);//如果为false那么生成的PDF文件还能编辑，一定要设为true
            stamper.close();

            Document doc = new Document();
            PdfCopy copy = new PdfCopy(doc, out);
            doc.open();
            PdfImportedPage importPage = copy.getImportedPage(
                new PdfReader(bos.toByteArray()), 1);
            copy.addPage(importPage);
            doc.close();

        } catch (IOException e) {
            System.out.println(1);
        } catch (DocumentException e) {
            System.out.println(2);
            e.printStackTrace();
        }
    }

    public static void insertImage() throws Exception{
        // 读取模板文件
        InputStream input = new FileInputStream(new File(formTemplate));
        PdfReader reader = new PdfReader(input);
        PdfStamper stamper = new PdfStamper(reader, new FileOutputStream(saveFilePath + "/addImage.pdf"));


        // 提取pdf中的表单
        AcroFields form = stamper.getAcroFields();
        form.addSubstitutionFont(BaseFont.createFont("STSong-Light","UniGB-UCS2-H", BaseFont.NOT_EMBEDDED));

        // 通过域名获取所在页和坐标，左下角为起点
        int pageNo = form.getFieldPositions("fill_7").get(0).page;
        Rectangle signRect = form.getFieldPositions("fill_7").get(0).position;
        float x = signRect.getLeft();
        //float y = signRect.getBottom() - 100;
        float y = signRect.getBottom();

        // 读图片
        Image image = Image.getInstance(imageFilePath);
        // 获取操作的页面
        PdfContentByte under = stamper.getOverContent(pageNo);
        // 根据域的大小缩放图片
        image.scaleToFit(signRect.getWidth(), signRect.getHeight());
        // 添加图片
        image.setAbsolutePosition(x, y);
        under.addImage(image);

        stamper.close();
        reader.close();
    }

    public static void insertImageBase64(String imageString) {
        try{
            // 读取模板文件
            InputStream input = new FileInputStream(new File(formTemplate));
            PdfReader reader = new PdfReader(input);
            //PdfStamper stamper = new PdfStamper(reader, new FileOutputStream(saveFilePath + "/addImage.pdf"));
            PdfStamper stamper = new PdfStamper(reader, new FileOutputStream(formTemplate));


            // 提取pdf中的表单
            AcroFields form = stamper.getAcroFields();
            form.addSubstitutionFont(BaseFont.createFont("STSong-Light","UniGB-UCS2-H", BaseFont.NOT_EMBEDDED));

            // 通过域名获取所在页和坐标，左下角为起点
            int pageNo = form.getFieldPositions("fill_7").get(0).page;
            Rectangle signRect = form.getFieldPositions("fill_7").get(0).position;
            float x = signRect.getLeft();
            //float y = signRect.getBottom() - 100;
            float y = signRect.getBottom();

            // 读图片
            //Image image = Image.getInstance(imageFilePath);
            BASE64Decoder decoder = new BASE64Decoder();
            System.out.println(decoder.decodeBuffer(imageString));
            Image image = Image.getInstance(decoder.decodeBuffer(imageString));
            // 获取操作的页面
            PdfContentByte under = stamper.getOverContent(pageNo);
            // 根据域的大小缩放图片
            image.scaleToFit(signRect.getWidth(), signRect.getHeight());
            // 添加图片
            image.setAbsolutePosition(x, y);
            under.addImage(image);

            stamper.close();
            reader.close();

        } catch (Exception e){
            e.printStackTrace();
        }

    }

    public static void addForm(Map<String, Object> formMap, String templatePath, String outputFilePath){
        //生成的新文件路径
        PdfReader reader;
        FileOutputStream out;
        ByteArrayOutputStream bos;
        PdfStamper stamper;
        Document doc = new Document();
        try {
            //createDirectory(outputFilePath);
            //输出流
            out = new FileOutputStream(outputFilePath);
            //读取pdf模板
            reader = new PdfReader(templatePath);
            bos = new ByteArrayOutputStream();
            stamper = new PdfStamper(reader, bos);
            AcroFields form = stamper.getAcroFields();

            form.addSubstitutionFont(BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED));

            java.util.Iterator<String> it = form.getFields().keySet().iterator();
            while (it.hasNext()) {
                String name = it.next().toString();
                if (Objects.nonNull(formMap.get(name))) {
                    form.setField(name, formMap.get(name).toString());
                }
            }
            //如果为false那么生成的PDF文件还能编辑，一定要设为true
            stamper.close();
            stamper.setFormFlattening(true);

            PdfCopy copy = new PdfCopy(doc, out);
            doc.open();
            PdfImportedPage importPage = copy.getImportedPage(new PdfReader(bos.toByteArray()), 1);
            copy.addPage(importPage);

        } catch (Exception e) {
            //throw new TradeBizException(true, ErrorCodeEnum.PDF_DEAL_ERROR, e);
        } finally {
            doc.close();
        }

    }


    public static void commonFillPdfForm(Map<String, String> formMap, String templatePath, String outputFilePath,
                                         String signatureString) {
        //生成的新文件路径
        PdfReader reader;
        FileOutputStream out;
        ByteArrayOutputStream bos;
        PdfStamper stamper;
        Document doc = new Document();
        try {
            createDirectory(outputFilePath);
            //输出流
            out = new FileOutputStream(outputFilePath);
            //读取pdf模板
            reader = new PdfReader(templatePath);
            bos = new ByteArrayOutputStream();
            stamper = new PdfStamper(reader, bos);
            AcroFields form = stamper.getAcroFields();

            form.addSubstitutionFont(BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED));

            java.util.Iterator<String> it = form.getFields().keySet().iterator();
            while (it.hasNext()) {
                String name = it.next().toString();
                if(StringUtils.isBlank(formMap.get(name))){
                    continue;
                }
                form.setField(name, formMap.get(name));

                //处理 checkbox
                int type = form.getFieldType(name);
                System.out.println("key:" + name + ", type:" + type);
                if(type == 2){
                    //注意版本问题，这个true必填，否则会变成x; 或者使用较低版本
                    //form.setField(name, "On", true);
                    //form.setField(name, formMap.get(name), true);
                    form.setField(name, formMap.get(name));
                }
            }

            //处理签名
            if (StringUtils.isNotBlank(signatureString)) {
                // 通过域名获取所在页和坐标，左下角为起点
                int pageNo = form.getFieldPositions("sign").get(0).page;
                Rectangle signRect = form.getFieldPositions("sign").get(0).position;

                // 读图片
                BASE64Decoder decoder = new BASE64Decoder();
                Image image = Image.getInstance(decoder.decodeBuffer(signatureString));
                // 获取操作的页面
                PdfContentByte under = stamper.getOverContent(pageNo);
                // 根据域的大小缩放图片
                image.scaleToFit(signRect.getWidth(), signRect.getHeight());
                // 添加图片
                image.setAbsolutePosition(signRect.getLeft(), signRect.getBottom());

                under.addImage(image);
            }
            //如果为false那么生成的PDF文件还能编辑，一定要设为true
            stamper.setFormFlattening(true);
            stamper.close();

            PdfCopy copy = new PdfCopy(doc, out);
            doc.open();
            PdfImportedPage importPage;
            for(int i = 1; i <= reader.getNumberOfPages(); i++) {
                importPage = copy
                    .getImportedPage(new PdfReader(bos.toByteArray()), i);
                copy.addPage(importPage);
            }
            doc.close();
        } catch (Exception e) {
            //logger.error("error",e);
        }
    }

    public static void createDirectory(String filePath) {
        int point = filePath.lastIndexOf(File.separator);
        String path = filePath.substring(0, point + 1);

        int index = path.indexOf(File.separator);
        while(index>-1){
            String tempPath = path.substring(0, index);
            File file = new File(tempPath);
            if (!file.exists()) {
                file.mkdirs();
            }
            index = path.indexOf(File.separator, index + 1);
        }
    }


    public static void main(String[] args) throws Exception{
        //Map<String, Object> map = Maps.newHashMap();
        //map.put("fill_13", "1244是佛挡");
        //String outputFilePath = "/Users/gary.liu/Documents/Job/Code/gov-trade/temp.pdf";
        //String templatePath = "/Users/gary.liu/Documents/Job/Code/gov-trade/birthOneThingTemplate.pdf";
        //addForm(map, templatePath, outputFilePath);
        //System.out.println(File.separator);
        //insertImage();

        insertImageBase64(imageString);
    }
}
