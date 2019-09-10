package com.czy.pdf;

//import com.itextpdf.tool.xml.XMLWorkerHelper;
//import com.lowagie.text.Document;
//import com.lowagie.text.DocumentException;
//import com.lowagie.text.Paragraph;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import com.itextpdf.text.pdf.draw.LineSeparator;
import com.itextpdf.tool.xml.XMLWorkerFontProvider;
import com.itextpdf.tool.xml.XMLWorkerHelper;
//import fr.opensagres.xdocreport.converter.IURIResolver;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.converter.WordToHtmlConverter;
import org.apache.poi.ss.usermodel.Color;
import org.apache.poi.xwpf.converter.xhtml.XHTMLConverter;
import org.apache.poi.xwpf.converter.xhtml.XHTMLOptions;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.junit.Test;
//import org.xhtmlrenderer.pdf.ITextFontResolver;
//import org.xhtmlrenderer.pdf.ITextRenderer;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * Created by Administrator on 2019/5/26 0026.
 */
public class PdfTest {
    public static void main(String[] args) {
        // 获得本机所有编码格式
        Map<String, Charset> charsets = Charset.availableCharsets();
        // 迭代遍历出编码方式
        for (Map.Entry<String, Charset> entry : charsets.entrySet()) {
            System.out.println(entry.getKey() + " : " + entry.getValue().name());
        }
    }
    @Test
    public void createPdf(){
            Configuration cfg = new Configuration();
            try {
                cfg.setLocale(Locale.CHINA);
                cfg.setEncoding(Locale.CHINA, "UTF-8");
                cfg.setDefaultEncoding("UTF-8");//设置编码
                cfg.setDirectoryForTemplateLoading(new File("C:\\Users\\Administrator\\Desktop"));//设置模板路径

                //获取模板
                Template template = cfg.getTemplate("四川中电启明星信息技术有限公司录用信.xml");
                template.setEncoding("UTF-8");
                Writer writer = new StringWriter();
                //数据填充模板
                template.process(new HashMap<String, Object>(), writer);
                String str = writer.toString();
                //pdf生成
                DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();


//                ITextRenderer iTextRenderer = new ITextRenderer();
//                iTextRenderer.setDocumentFromString(str);


                //设置字体  其他字体需要添加字体库
//                ITextFontResolver fontResolver = iTextRenderer.getFontResolver();
//                fontResolver.addFont(basePath + "/WEB-INF/views/font/simsun.ttc", BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
//                iTextRenderer.setDocument(builder.parse(new ByteArrayInputStream(str.getBytes())),null);
//                iTextRenderer.layout();

                //生成PDF
//                ByteArrayOutputStream baos = new ByteArrayOutputStream();
//                iTextRenderer.createPDF(new FileOutputStream("C:\\Users\\Administrator\\Desktop\\四川中电启明星信息技术有限公司录用信.pdf"));
//                baos.close();

//                Document document = new Document();
//                PdfWriter.getInstance(document, new FileOutputStream("C:\\Users\\Administrator\\Desktop\\四川中电启明星信息技术有限公司录用信.pdf"));
//                document.open();
//                document.add(new Paragraph(str));
//                document.close();

//                File file = new File("C:\\Users\\Administrator\\Desktop\\四川中电启明星信息技术有限公司录用信.pdf");
//                if (!file.exists()) file.createNewFile();
//                FileWriter fw = new FileWriter(file);
//                fw.write(iTextRenderer.);
//                fw.flush();
//                fw.close();
            } catch(Exception e) {
                e.printStackTrace();
            }
        }

    @Test
    public void createPdf2(){
//        Configuration cfg = new Configuration();
//        cfg.setLocale(Locale.CHINA);
//        cfg.setEncoding(Locale.CHINA, "UTF-8");
//        cfg.setDefaultEncoding("UTF-8");//设置编码
        try {
//            cfg.setDirectoryForTemplateLoading(new File("C:\\Users\\Administrator\\Desktop"));//设置模板路径

            //获取模板
//            Template template = cfg.getTemplate("四川中电启明星信息技术有限公司录用信.docx");
//            template.setEncoding("UTF-8");
//            Writer writer = new StringWriter();
//            //数据填充模板
//            template.process(new HashMap<String, Object>(), writer);
//            String str = writer.toString();

            Document document = new Document(PageSize.A5, 50, 0, 0, 0);
            convert2Html(new File("C:\\Users\\Administrator\\Desktop\\四川中电启明星信息技术有限公司录用信.docx"));
            PdfWriter pdfWriter = PdfWriter.getInstance(document, new FileOutputStream("C:\\Users\\Administrator\\Desktop\\四川中电启明星信息技术有限公司录用信.pdf"));
            document.open();

            XMLWorkerHelper.getInstance().parseXHtml(
                    pdfWriter, document,
                    new FileInputStream("C:\\Users\\Administrator\\Desktop\\四川中电启明星信息技术有限公司录用信.pdf.tmp"),
                    Charset.forName("UTF-8"), new XMLWorkerFontProvider() {
                        public Font getFont(String s, String s1, boolean b, float v, int i, BaseColor baseColor) {
                            try {
                                return new Font(BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED), 12f);
                            } catch (DocumentException e) {
                                e.printStackTrace();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            String fntname = s;
                            if(fntname==null){
                                fntname="宋体";
                            }
                            return super.getFont(fntname, s1, v, i);
                        }
                    });

            document.close();
        }catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static void convert2Html(File file)
            throws TransformerException, IOException, ParserConfigurationException {
        String fileName = file.getName();
        String substring = fileName.substring(fileName.lastIndexOf(".")+1);
        FileOutputStream out = new FileOutputStream("C:\\Users\\Administrator\\Desktop\\四川中电启明星信息技术有限公司录用信.pdf.tmp");

        if("docx".equals(substring)){
            InputStream inputStream = new FileInputStream(file);
            XWPFDocument document = new XWPFDocument(inputStream);

            XHTMLOptions options = XHTMLOptions.create();
            options.setIgnoreStylesIfUnused(false);
            options.setFragment(true);

            XHTMLConverter.getInstance().convert(document, out, options);
        }else{
            HWPFDocument wordDocument = new HWPFDocument(new FileInputStream(file));
            WordToHtmlConverter wordToHtmlConverter = new WordToHtmlConverter(
                    DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument());
            wordToHtmlConverter.processDocument(wordDocument);
            org.w3c.dom.Document document = wordToHtmlConverter.getDocument();
            DOMSource domSource = new DOMSource(document);
            StreamResult streamResult = new StreamResult(out);

            TransformerFactory tf = TransformerFactory.newInstance();
            Transformer serializer = tf.newTransformer();
            serializer.setOutputProperty(OutputKeys.ENCODING, "utf-8");
            serializer.setOutputProperty(OutputKeys.INDENT, "yes");
            serializer.setOutputProperty(OutputKeys.METHOD, "html");
            serializer.transform(domSource, streamResult);
        }
        out.flush();
        out.close();
    }

    @Test
    public void createPdf3() throws IOException, DocumentException {
        File file = new File("C:\\Users\\Administrator\\Desktop\\信.pdf");
        if(file.exists()) file.delete();
        Document document = new Document(PageSize.A4, 50, 50, 50, 50);
        PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream("C:\\Users\\Administrator\\Desktop\\信.pdf"));
        writer.setPageEvent(new PdfExport());
        document.open();

        Font titleFont = new Font(BaseFont.createFont("/ttf/simhei.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED), 25f);
        titleFont.setColor(BaseColor.RED);
        Paragraph title = new Paragraph("中共国家电网有限公司党组会议通知", titleFont);
        title.setAlignment(Element.ALIGN_CENTER);
        document.add(title);
        document.add(new Paragraph("\n", new Font(BaseFont.createFont("/ttf/simhei.ttf", BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED), 10f)));

        Paragraph bianhao = new Paragraph("党组会通［2018］60号", new Font(BaseFont.createFont("/ttf/fangsong_GB2312.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED), 15f));
        bianhao.setPaddingTop(0);
        bianhao.setAlignment(Element.ALIGN_CENTER);
        document.add(bianhao);

        LineSeparator lineSeparator = new LineSeparator(0.5f, 90, BaseColor.RED, Element.ALIGN_CENTER, -5f);
        document.add(lineSeparator); //画横线

        document.add(new Paragraph("\n", new Font(BaseFont.createFont("/ttf/simhei.ttf", BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED), 10f)));
        PdfPTable table = new PdfPTable(new float[]{15, 150});

        Font cell1Font = new Font(BaseFont.createFont("/ttf/simhei.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED), 10f);
        Font cell2Font = new Font(BaseFont.createFont("/ttf/fangsong_GB2312.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED), 10f);
        for (int i = 0; i < 12; i++) {
            String value = "";
            if(i == 0) value = "时间：";
            else if ((i == 1)) value = "2018年12月25日（星期二）9:00";
            else if ((i == 2)) value = "地点：";
            else if ((i == 3)) value = "708会议室";
            else if ((i == 4)) value = "主持：";
            else if ((i == 5)) value = "党组书记寇伟";
            else if ((i == 6)) value = "出席：";
            else if ((i == 7)) value = "党组副书记、党组成员";
            else if ((i == 8)) value = "列席：";
            else if ((i == 9)) value = "刘泽洪、张智刚、王昕伟（第1-7议题），助理、总师、副总师、郭丁平、吕运强、葛兆军、张福轩、刘永奇、徐鹏、丁勇、杨新法、郑云安、张徐东、王海啸、郭炬、全生明（第1-2议题），赵庆波、苏胜新（第1-2、4议题），孙正运、李荣华、丁扬（第1-2、5议题），陈国平、冯凯、冯来法、谢永胜、史连军（第1-2、4-5议题），吕春泉、张玮、周安春（第1-5议题），朱光超、欧阳昌裕、李凯、吕海平、王彦亮（第1-3议题），刘克俭（第1-2、7议题），王国春、王继业（第1-3、5议题），张磊（第1-3、6-7议题），彭建国（第1-4议题），王政涛、林铭山、邬捷龙、李景中、张运洲（第2议题），江冰（第2、5议题），杨东伟、俞学豪、郭剑波、滕乐天（第5议题）";
            else if ((i == 10)) value = "议题：";
            else if ((i == 11)) value = "";

            PdfPCell cell = new PdfPCell();
            cell.addElement(new Paragraph(value, i%2==0 ? cell1Font : cell2Font));
            cell.setBorder(0);
            cell.setBorderWidth(0);
            table.addCell(cell);
        }
        document.add(table);

        BaseFont bfChinese = BaseFont.createFont("/ttf/fangsong_GB2312.ttf", BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
        Font nameFont = new Font(bfChinese, 12, Font.BOLD);
        Font detailFont = new Font(bfChinese, 12, Font.NORMAL);

        Paragraph p = new Paragraph();
        p.setIndentationLeft(50f);
        p.setIndentationRight(50f);
        p.setFirstLineIndent(20f);
        Chunk chunk1 = new Chunk("1.传达中央经济工作会议精神", nameFont);
        Chunk chunk2 = new Chunk("（助理、总师、副总师、总部各部门参加）", detailFont);
        p.add(chunk1);
        p.add(chunk2);
        document.add(p);
        p = new Paragraph();
        p.setIndentationLeft(50f);
        p.setIndentationRight(50f);
        p.setFirstLineIndent(20f);
        chunk1 = new Chunk("2.审议****有关安排", nameFont);
        chunk2 = new Chunk("（体改办汇报，助理、总师、副总师、总部各部门、信通产业集团、电动汽车公司、新源公司、通航公司、节能公司、能源院、英大集团参加）", detailFont);
        p.add(chunk1);
        p.add(chunk2);
        document.add(p);

        document.add(new Paragraph("\n", new Font(BaseFont.createFont("/ttf/simhei.ttf", BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED), 10f)));//换行
        p = new Paragraph("请各部门、单位于12月24日12:00前将报名回执发至sp-msyc@sgcc.com.cn（66598151）。有关会议材料请按照《关于加强党组会、总经理办公会等重要会议文件、材料保密管理的规定》，于12月24日16:00前送办公厅文印室。", detailFont);
        p.setIndentationLeft(50f);
        p.setIndentationRight(50f);
        p.setFirstLineIndent(20f);
        document.add(p);

        document.add(new Paragraph("\n", new Font(BaseFont.createFont("/ttf/simhei.ttf", BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED), 10f)));
        p = new Paragraph("办 公 厅", detailFont);
        p.setIndentationLeft(370f);
        document.add(p);

        p = new Paragraph("2018年12月24日", detailFont);
        p.setIndentationLeft(360f);
        document.add(p);

        document.newPage();
        //参会时间
        Font timeFont = new Font(BaseFont.createFont("/ttf/simhei.ttf", BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED), 18f);
        Paragraph time = new Paragraph("参 会 时 间", timeFont);
        time.setAlignment(Element.ALIGN_CENTER);
        document.add(time);
        document.add(new Paragraph("\n", new Font(BaseFont.createFont("/ttf/simhei.ttf", BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED), 10f)));//换行

        p = new Paragraph("请参会人员参照以下时间参加会议，并提前至707会议室等候。", detailFont);
        p.setIndentationLeft(50f);
        p.setIndentationRight(50f);
        document.add(p);

        for (int i = 0; i < 2; i++) {
            p = new Paragraph("第"+(i + 1)+"议题：9:00；", detailFont);
            p.setIndentationLeft(50f);
            p.setIndentationRight(50f);
            document.add(p);
        }

        document.newPage();
        Font huizhiFont = new Font(BaseFont.createFont("/ttf/simhei.ttf", BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED), 18f);
        Paragraph huizhi = new Paragraph("党组会议报名回执", huizhiFont);
        huizhi.setAlignment(Element.ALIGN_CENTER);
        document.add(huizhi);

        document.add(new Paragraph("\n", new Font(BaseFont.createFont("/ttf/simhei.ttf", BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED), 10f)));
        PdfPTable hzTable = new PdfPTable(new float[]{25, 140});
        Font hzTableFont = new Font(BaseFont.createFont("/ttf/fangsong_GB2312.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED), 10f);
        for (int i = 0; i < 12; i++) {
            String value = "";
            if(i == 0) value = "会议名称";
            else if ((i == 1)) value = "2018年第60次党组会议";
            else if ((i == 2)) value = "部     门\n（单位）";
            else if ((i == 3)) value = "";
            else if ((i == 4)) value = "参会人员";
            else if ((i == 5)) value = "";
            else if ((i == 6)) value = "职    务";
            else if ((i == 7)) value = "";
            else if ((i == 8)) value = "主要负责人不参会事由";
            else if ((i == 9)) value = "";
            else if ((i == 10)) value = "经 办 人";
            PdfPCell cell = new PdfPCell();
            cell.setUseAscender(true);
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);// 设置垂直居中
            cell.setFixedHeight(20);
            if(i==2 || i ==3) cell.setFixedHeight(50);
            if(i==8 || i ==9) cell.setFixedHeight(100);
            if(i == 11) {
                cell.setPadding(0);
                PdfPTable operatorTable = new PdfPTable(new float[]{90, 30, 70});
                operatorTable.setPaddingTop(0);
                Paragraph element = new Paragraph("  电话", hzTableFont);
                element.setAlignment(1);
                PdfPCell phoneCell = new PdfPCell(element);
                phoneCell.setFixedHeight(20);
                phoneCell.setUseAscender(true);
                phoneCell.setVerticalAlignment(Element.ALIGN_MIDDLE);// 设置垂直居中
                phoneCell.setHorizontalAlignment(Element.ALIGN_MIDDLE);// 设置水平居中

                PdfPCell cell1 = new PdfPCell();
                cell1.setBorderWidth(0);
                operatorTable.addCell(cell1);
                operatorTable.addCell(phoneCell);
                operatorTable.addCell(cell1);
                cell.addElement(operatorTable);
            }else {
                Paragraph element = new Paragraph(value, hzTableFont);
                element.setAlignment(1);
                cell.addElement(element);
                cell.setBorderWidth(0.5f);
                cell.setUseAscender(true);
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);// 设置垂直居中
                cell.setHorizontalAlignment(1);// 设置水平居中
            }
            hzTable.addCell(cell);
        }
        document.add(hzTable);

        document.close();
    }

    public class PdfExport extends PdfPageEventHelper {
        public PdfTemplate tpl = null;
        public BaseFont bf = null;

        @Override
        public void onEndPage(PdfWriter writer, Document document) {
            //在每页结束的时候把页数信息写道模版指定位置
            PdfContentByte cb =writer.getDirectContent();
            String text = writer.getPageNumber() + "";
            float textSize = bf.getWidthPoint(text, 9);
            float textBase = document.bottom();

            cb.beginText();
            cb.setFontAndSize(bf, 9);
            cb.setTextMatrix(300, textBase);
            cb.showText(text);
            cb.endText();
            cb.addTemplate(tpl, document.left()+textSize, textBase);
        }

        @Override
        public void onOpenDocument(PdfWriter writer, Document document) {
            tpl = writer.getDirectContent().createTemplate(100, 100);
            try {
                bf = BaseFont.createFont("/ttf/simhei.ttf", BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
            } catch (DocumentException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onCloseDocument(PdfWriter writer, Document document) {
        }
    }
}