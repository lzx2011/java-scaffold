package com.lzhenxing.javascaffold.util.pdf;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
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
import org.apache.pdfbox.cos.COSName;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.PDResources;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType0Font;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.apache.pdfbox.pdmodel.interactive.form.PDAcroForm;
import org.apache.pdfbox.pdmodel.interactive.form.PDTextField;
import org.apache.pdfbox.text.PDFTextStripper;

/**
 *
 * ClassName: DocumentCreation <br/>
 * Function: <br/>
 *
 * @author zhenxing.liu
 * @date 2019/8/17
 */
public class DocumentCreation {


    public static String filePath = "/Users/gary.liu/my_doc.pdf";

    public static String testFilePath = "/Users/gary.liu/Documents/pdfTest/test.pdf";
    public static String saveFilePath = "/Users/gary.liu/Documents/pdfTest";
    public static String imageFilePath = "/Users/gary.liu/Documents/pdfTest/icon.jpeg";
    public static String formTemplate = "/Users/gary.liu/Documents/pdfTest/birthOneThingTemple.pdf";
    public static String testTemplate = "/Users/gary.liu/Documents/pdfTest/testNew.pdf";

    public static void createFile() throws IOException {

        //Creating PDF document object
        PDDocument document = new PDDocument();

        //Saving the document
        document.save("/Users/gary.liu/my_doc.pdf");

        System.out.println("PDF created");

        //Closing the document
        document.close();


        //Loading an existing document
        //File file = new File("/Users/gary.liu/my_doc.pdf");
        //PDDocument doc = PDDocument.load(file);
        //
        ////Creating a PDF Document
        //PDPage page = doc.getPage(1);
        //
        //PDPageContentStream contentStream = new PDPageContentStream(doc, page);
        //
        ////Begin the Content stream
        //contentStream.beginText();
        //
        ////Setting the font to the Content stream
        //contentStream.setFont(PDType1Font.TIMES_ROMAN, 16 );
        //
        ////Setting the leading
        //contentStream.setLeading(14.5f);
        //
        ////Setting the position for the line
        //contentStream.newLineAtOffset(25, 725);
        //
        //String text1 = "This is an example of adding text to a page in the pdf document. we can add as many lines";
        //String text2 = "as we want like this using the ShowText()  method of the ContentStream class";
        //
        ////Adding text in the form of string
        //contentStream.showText(text1);
        //contentStream.newLine();
        //contentStream.showText(text2);
        ////Ending the content stream
        //contentStream.endText();
        //
        //System.out.println("Content added");
        //
        ////Closing the content stream
        //contentStream.close();
        //
        ////Saving the document
        //doc.save(new File("/Users/gary.liu/my_doc1.pdf"));
        //
        ////Closing the document
        //doc.close();

        }

        //有问题
    public static void readContent() throws IOException{
        File myFile = new File(filePath);

        try (PDDocument doc = PDDocument.load(myFile)) {

            PDFTextStripper stripper = new PDFTextStripper();
            String text = stripper.getText(doc);

            System.out.println("Text size: " + text.length() + " characters:");
            System.out.println(text);
        }
    }

    public static void addText() throws IOException{

        //Loading an existing document
        File file = new File(testFilePath);
        PDDocument document = PDDocument.load(file);

        //Retrieving the pages of the document
        //PDPage page = document.getPage(0);
        PDPage page = new PDPage();

        PDPageContentStream contentStream = new PDPageContentStream(document, page);

        //Begin the Content stream
        contentStream.beginText();
        // contentStream.set
        //Setting the font to the Content stream
        contentStream.setFont(PDType1Font.HELVETICA_BOLD, 14);

        //Setting the position for the line
        contentStream.newLineAtOffset(25, 500);

        String text = "This is the sample document and we are adding content to it. - By yiibai.com";

        //Adding text in the form of string
        contentStream.showText(text);

        //Ending the content stream
        contentStream.endText();

        System.out.println("Content added");

        //Closing the content stream
        contentStream.close();

        //Saving the document
        document.save(new File(saveFilePath + "/new-doc-text.pdf"));

        //Closing the document
        document.close();
    }


    public static void writeText() throws Exception{
        try (PDDocument doc = new PDDocument()) {

            PDPage myPage = new PDPage();
            doc.addPage(myPage);

            try (PDPageContentStream cont = new PDPageContentStream(doc, myPage)) {

                cont.beginText();

                cont.setFont(PDType1Font.TIMES_ROMAN, 12);
                //cont.setFont(PDType1Font.TIMES_ITALIC, 12);

                cont.setLeading(14.5f);

                cont.newLineAtOffset(25, 700);
                String line1 = "World War II (often abbreviated to WWII or WW2), "
                    + "also known as the Second World War,";
                line1 = line1.replace("\n", "").replace("\r", "");
                cont.showText(line1);

                cont.newLine();

                String line2 = "was a global war that lasted from 1939 to 1945, "
                    + "although related conflicts began earlier.";
                line2 = line2.replace("\n", "").replace("\r", "");
                cont.showText(line2);
                cont.newLine();

                //String line3 = "It involved the vast majority of the world's "
                //    + "countries—including all of the great powers—";
                //cont.showText(line3);
                //cont.newLine();
                //
                //String line4 = "eventually forming two opposing military "
                //    + "alliances: the Allies and the Axis.";
                //cont.showText(line4);
                cont.newLine();

                cont.endText();
            }

            doc.save(saveFilePath + "/writeText.pdf");
        }
    }

    public static void insertImage() throws Exception{
        //Loading an existing document
        File file = new File(testFilePath);
        PDDocument doc = PDDocument.load(file);

        //Retrieving the page
        //PDPage page = doc.getPage(0);
        PDPage page = new PDPage();

        //Creating PDImageXObject object
        PDImageXObject pdImage = PDImageXObject.createFromFile(imageFilePath,doc);

        //creating the PDPageContentStream object
        PDPageContentStream contents = new PDPageContentStream(doc, page);

        //Drawing the image in the PDF document
        contents.drawImage(pdImage, 70, 250);

        System.out.println("Image inserted");

        //Closing the PDPageContentStream object
        contents.close();

        //Saving the document
        doc.save(saveFilePath + "/sample-image.pdf");

        //Closing the document
        doc.close();
    }

    public static void addMultiLines() throws Exception{
        //Loading an existing document
        File file = new File(filePath);
        PDDocument doc = PDDocument.load(file);

        //Creating a PDF Document
        PDPage page = doc.getPage(1);
        PDFont font = PDType0Font.load(doc, new File("/Users/gary.liu/Downloads/font21015/bauer_bodoni_d.ttf"));

        PDPageContentStream contentStream = new PDPageContentStream(doc, page);

        //Begin the Content stream
        contentStream.beginText();

        //Setting the font to the Content stream
        contentStream.setFont( PDType1Font.TIMES_ROMAN, 16 );
        System.out.println(" getName => "+font.getName());
        // contentStream.setFont( font, 16 );

        //Setting the leading
        contentStream.setLeading(14.5f);

        //Setting the position for the line
        contentStream.newLineAtOffset(25, 725);

        String text1 = "This is an example of adding text to a page in the pdf document. we can add as many lines";
        String text2 = "as we want like this using the ShowText()  method of the ContentStream class";
        //Adding text in the form of string
        contentStream.showText(text1);
        contentStream.newLine();
        contentStream.newLine();
        contentStream.showText(text2);
        contentStream.newLine();
        //Ending the content stream
        contentStream.endText();

        System.out.println("Content added");

        //Closing the content stream
        contentStream.close();

        //Saving the document
        doc.save(new File("/new-mul-doc.pdf"));

        //Closing the document
        doc.close();

    }

    public static void readText() throws Exception{
        //Loading an existing document
        File file = new File(testFilePath);
        PDDocument document = PDDocument.load(file);

        //Instantiate PDFTextStripper class
        PDFTextStripper pdfStripper = new PDFTextStripper();

        //Retrieving text from PDF document
        String text = pdfStripper.getText(document);
        System.out.println(text);

        //Closing the document
        document.close();
    }

    public static void addForm() throws Exception{

        try (PDDocument pdfDocument = PDDocument.load(new File(formTemplate)))
        {
            // get the document catalog
            PDAcroForm acroForm = pdfDocument.getDocumentCatalog().getAcroForm();

            // as there might not be an AcroForm entry a null check is necessary
            if (acroForm != null)
            {

                //PDResources resources = new PDResources();
                //String font = "KozMinPr6N-Regular";
                //font.replace("\n", "").replace("\r", "");
                //resources.put(COSName.getPDFName(font), PDType1Font.HELVETICA_BOLD);
                //acroForm.setDefaultResources(resources);

                // Retrieve an individual field and set its value.
                //String appearance = ((PDTextField)acroForm.getFields().get(0)).getDefaultAppearance();
                //System.out.println(appearance);


                PDTextField field = ((PDTextField) acroForm.getField( "fill_13" ));

                COSName helvName = acroForm.getDefaultResources().add(PDType1Font.HELVETICA); // use different font if you want. Do not subset!
                field.setDefaultAppearance("/" + helvName.getName() + " 10 Tf 0 g");

                //field.setDefaultAppearance(appearance);

                String name = "baby水电费";
                name.replace("\n", "").replace("\r", "");
                field.setValue(name);


                // If a field is nested within the form tree a fully qualified name
                // might be provided to access the field.
                //PDTextField field1 = (PDTextField) acroForm.getField( "fill_14" );
                //field1.setDefaultAppearance(appearance);
                //field1.setValue("中国");
            }

            // Save and close the filled out form.
            pdfDocument.save(saveFilePath + "/addForm.pdf");
        }
    }


    public static void addForm1() throws Exception{

        try (PDDocument pdfDocument = PDDocument.load(new File(testTemplate)))
        {
            // get the document catalog
            PDAcroForm acroForm = pdfDocument.getDocumentCatalog().getAcroForm();

            // as there might not be an AcroForm entry a null check is necessary
            if (acroForm != null)
            {
                PDTextField field = ((PDTextField) acroForm.getField( "fill_1" ));

                //COSName helvName = acroForm.getDefaultResources().add(PDType1Font.HELVETICA); // use different font if you want. Do not subset!
                //field.setDefaultAppearance("/" + helvName.getName() + " 10 Tf 0 g");

                String name = "baby123第三方";
                name.replace("\n", "").replace("\r", "");
                field.setValue(name);
            }

            // Save and close the filled out form.
            pdfDocument.save(saveFilePath + "/addForm.pdf");
        }
    }


    public static void addFormByItext() throws Exception{

        PdfReader reader = new PdfReader(formTemplate);
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        PdfStamper ps = new PdfStamper(reader, bos);
        /**
         * 使用中文字体 使用 AcroFields填充值的不需要在程序中设置字体，在模板文件中设置字体为中文字体 Adobe 宋体 std L
         */

        AcroFields s = ps.getAcroFields();
        //设置key-value值
        //通过s.setFieldProperty("字段名", "textfont", BaseFont , null); 设置字段的格式或者在模板中修改样式
        s.setField("fill_13", "sf1223释放掉");
        //s.setField("info", "测试单位");
        //s.setField("projectName_1", "测试项目");
        //s.setField("num_1", "2");
        //s.setField("bw_1", "1");
        //s.setField("amount_1", "100000000");
        //s.setField("total", "壹佰万整");
        //s.setField("bz_1", "个");
        //s.setField("SKR", "测试人");
        //s.setField("SKDW", "测试单位");
        //s.setField("year", "2016");
        //s.setField("mouth", "11");
        //s.setField("day", "08");
        // 设为true
        ps.setFormFlattening(true);
        ps.close();
        FileOutputStream fos = new FileOutputStream(saveFilePath + "/addFormByItext.pdf");
        fos.write(bos.toByteArray());

    }


    public static void addFormByItextAsian() throws Exception{

        PdfReader reader = new PdfReader(formTemplate);
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        PdfStamper ps = new PdfStamper(reader, bos);

        //BaseFont bfChinese = BaseFont.createFont("Adobe-CNS1-0", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);

        /**
         * 使用中文字体 使用 AcroFields填充值的不需要在程序中设置字体，在模板文件中设置字体为中文字体 Adobe 宋体 std L
         */

        AcroFields s = ps.getAcroFields();
        //设置key-value值
        //s.setFieldProperty("fill_21", "textfont", bfChinese , null);
        // 设置字段的格式或者在模板中修改样式
        s.setField("fill_13", "sf1223释放掉");
        // 设为true
        ps.setFormFlattening(true);
        ps.close();
        FileOutputStream fos = new FileOutputStream(saveFilePath + "/addFormByItext.pdf");
        fos.write(bos.toByteArray());
    }

    public static void addImageByItext() throws Exception{

        // 读取模板文件
        InputStream input = new FileInputStream(new File(formTemplate));
        PdfReader reader = new PdfReader(input);
        PdfStamper stamper = new PdfStamper(reader, new FileOutputStream(saveFilePath + "/addImage.pdf"));


        // 提取pdf中的表单
        AcroFields form = stamper.getAcroFields();
        form.addSubstitutionFont(BaseFont.createFont("STSong-Light","UniGB-UCS2-H", BaseFont.NOT_EMBEDDED));

        // 通过域名获取所在页和坐标，左下角为起点
        int pageNo = form.getFieldPositions("fill_12").get(0).page;
        Rectangle signRect = form.getFieldPositions("fill_12").get(0).position;
        float x = signRect.getLeft();
        float y = signRect.getBottom() - 100;

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

    public static void addFromChinese() throws Exception{
        //String templatePath = "pdfFolder/template_demo.pdf";
        //生成的新文件路径
        String newPDFPath = saveFilePath + "/addFromChinese.pdf";
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
            form.addSubstitutionFont(BaseFont.createFont("KozMinPro-Regular", BaseFont.IDENTITY_H, BaseFont.EMBEDDED));

            //form.set()
            String[] str = {"12垫付","小鲁","男","1994-00-00",
                "130222111133338888"
                ,"河北省唐山市"};
            int i = 0;
            java.util.Iterator<String> it = form.getFields().keySet().iterator();
            while(it.hasNext()){
                String name = it.next().toString();
                System.out.println(name);
                if(i > 1){
                    break;
                }
                //System.out.println(i);
                System.out.println(name + " value：" + form.getField(name));
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

    public static void addTextByItext() throws Exception{
        //创建基础字体
        BaseFont bf = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
        //自定义字体属性
        Font font = new Font(bf,30);

        Document document = new Document(PageSize.A4.rotate());
        PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream("test_cn.pdf"));
        writer.setPdfVersion(PdfWriter.PDF_VERSION_1_7);
        //Make document tagged
        writer.setTagged();
        //===============
        writer.setViewerPreferences(PdfWriter.DisplayDocTitle);
        document.addTitle("中文测试");
        writer.createXmpMetadata();
        //=====================
        document.open();
        Paragraph p = new Paragraph();
        //Embed font
        p.setFont(font);
        //==================
        Chunk c = new Chunk("中文测试");
        p.add(c);
        document.add(p);
        //BaseFont cbf = BaseFont.createFont(cfontPath,BaseFont.IDENTITY_H,BaseFont.EMBEDDED);
        Font cfont = new Font(bf,64);
        Phrase ph = new Phrase("中文测试",cfont);
        document.add(ph);
        document.close();
    }

    public static void main(String[] args) throws Exception{


        //addFromChinese();

        //addFormByItext();

        //addFormByItextAsian();

        //addImageByItext();

        //addForm();
        addForm1();

        //createFile();

        //addText();

        //writeText();

        //OK
        //readText();

        //insertImage();

        //readContent();
    }

}
