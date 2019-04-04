package com.autoserve.abc.service.biz.impl.exportpdf;

import java.io.IOException;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.util.List;
import java.util.Map;

import com.autoserve.abc.dao.dataobject.pdfBean.InvestInformationDO;
import com.autoserve.abc.service.util.SystemGetPropeties;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.Image;
import com.lowagie.text.PageSize;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

/**
 * 利用开源组件IText2.0.7动态导出PDF文档 转载时请保留以下信息，注明出处！ Creditor
 * 
 * @author leno
 * @version v1.0
 * @param <T> 应用泛型，代表任意一个符合javabean风格的类
 *            注意这里为了简单起见，boolean型的属性xxx的get器方式为getXxx(),而不是isXxx()
 *            byte[]表图片数据,注意合适的大小
 */
public class CreditorExportPdf {

    /**
     * 这是一个通用的方法，利用了JAVA的反射机制，可以将放置在JAVA集合中并且符号一定条件的数据以PDF 的形式输出到指定IO设备上
     *
     * @param title 表格标题名
     * @param headers 表格属性列名数组
     * @param dataset 需要显示的数据集合,集合中一定要放置符合javabean风格的类的对象。此方法支持的
     *            javabean属性的数据类型有基本数据类型及String,Date,byte[](图片数据)
     * @param out 与输出设备关联的流对象，可以将PDF文档导出到本地文件或者网络中
     * @param pattern 如果有时间数据，设定输出格式。默认为"yyy-MM-dd"
     */
    @SuppressWarnings("unchecked")
    public void exportPdf(String title, List<String> params, List<Map<String, PdfParagraph>> topList,
                          List<InvestInformationDO> dataset, OutputStream out) {
        // 作为报表的PDF文件，一定要适合打印机的输出打印
        Rectangle rectPageSize = new Rectangle(PageSize.A4);// 定义A4页面大小
        // rectPageSize = rectPageSize.rotate();// 加上这句可以实现A4页面的横置
        Document document = new Document(rectPageSize, 50, 50, 50, 50);// 其余4个参数，设置了页面的4个边距
        try {
            // 将PDF文档写出到out所关联IO设备上的书写对象
            PdfWriter.getInstance(document, out);
            // 添加文档元数据信息
            document.addTitle(StrHelp.getChinese(title));
            document.addSubject("export information");
            document.addAuthor("leno");
            document.addCreator("leno");
            document.addKeywords("pdf itext");
            // 打开PDF文档
            document.open();
            try {
                String xhTopLog = SystemGetPropeties.getStrString("xhTopLog");
                Image image = Image.getInstance(xhTopLog);
                image.scaleAbsolute(150, 50);
                PdfPTable table = new PdfPTable(2);
                table.setHorizontalAlignment(Element.ALIGN_LEFT);
                table.setWidthPercentage(40 * 2);
                PdfPCell cell = null;
                cell = new PdfPCell(image);
                cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                cell.setVerticalAlignment(Element.ALIGN_LEFT);
                cell.setBorder(0);
                table.addCell(cell);
                cell = new PdfPCell(new PdfParagraph("债权转让合同", 20, true));
                cell.setHorizontalAlignment(Element.ALIGN_UNDEFINED);
                cell.setVerticalAlignment(Element.ALIGN_UNDEFINED);
                cell.setBorder(0);
                table.addCell(cell);
                document.add(table);
            } catch (MalformedURLException e) {
            } catch (IOException e) {
                e.printStackTrace();
            }
            document.add(new PdfParagraph(
                    "___________________________________________________________________________________________________"));
            PdfPTable table = new PdfPTable(1);
            table.setHorizontalAlignment(Element.ALIGN_RIGHT);
            table.setWidthPercentage(100 * 1);
            PdfPCell cell = null;
            cell = new PdfPCell(new PdfParagraph("合同编号：" + StrHelp.getChinese(title)));
            cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
            cell.setVerticalAlignment(Element.ALIGN_RIGHT);
            cell.setBorder(0);
            table.addCell(cell);
            document.add(table);
            exportTopTablePdf(2, 50, topList, document);
            //已生成平台电子印章
            /*try {
                String xhseal = SystemGetPropeties.getStrString("xhseal");
                Image image = Image.getInstance(xhseal);
                image.scaleAbsolute(100, 100);
                image.setAbsolutePosition(300, 480);
                document.add(image);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }*/
            for (int j = 0; j < params.size(); j++) {
                if ("t".equals(params.get(j).substring(0, 1))) {
                    document.add(new PdfParagraph(params.get(j).substring(1), 14, true));

                } else {
                    document.add(new PdfParagraph(params.get(j)));
                }
            }
            document.add(new PdfParagraph("乙 方 名 单："));
            document.add(new PdfParagraph("    "));
//            if(dataset.size()==1){
            	exitTablePdf(6, 18, dataset, document);
//            }else{
//            	exitTablePdf(5, 20, dataset, document);
//            }
        } catch (DocumentException e) {
            e.printStackTrace();
        } finally {
        	try {
        		document.close();
            	out.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
        }
    }

    public void exitTablePdf(int columns, int widths, List<InvestInformationDO> dataset, Document document) {
        PdfPTable table = new PdfPTable(columns);
        table.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.setWidthPercentage(widths * columns);
        // 遍历集合数据，产生数据行
        PdfPCell cell = null;
        cell = new PdfPCell(new PdfParagraph("用户名"));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setVerticalAlignment(Element.ALIGN_CENTER);
        table.addCell(cell);
//        if(dataset.size()==1){
        	cell = new PdfPCell(new PdfParagraph("姓名"));
        	cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        	cell.setVerticalAlignment(Element.ALIGN_CENTER);
        	table.addCell(cell);
//        }
        cell = new PdfPCell(new PdfParagraph("身份证号"));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setVerticalAlignment(Element.ALIGN_CENTER);
        table.addCell(cell);
        cell = new PdfPCell(new PdfParagraph("出借金额"));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setVerticalAlignment(Element.ALIGN_CENTER);
        table.addCell(cell);
        cell = new PdfPCell(new PdfParagraph("借款期限(天)"));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setVerticalAlignment(Element.ALIGN_CENTER);
        table.addCell(cell);
        cell = new PdfPCell(new PdfParagraph("到期本息合计"));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setVerticalAlignment(Element.ALIGN_CENTER);
        table.addCell(cell);
        for (int i = 0; i < dataset.size(); i++) {
        	cell = new PdfPCell(new PdfParagraph(dataset.get(i).getUserId()));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setVerticalAlignment(Element.ALIGN_CENTER);
            table.addCell(cell);
//            if(dataset.size()==1){
            	cell = new PdfPCell(new PdfParagraph(dataset.get(i).getRealName()));
            	cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            	cell.setVerticalAlignment(Element.ALIGN_CENTER);
            	table.addCell(cell);
//            }
            cell = new PdfPCell(new PdfParagraph(dataset.get(i).getUserDocNo()));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setVerticalAlignment(Element.ALIGN_CENTER);
            table.addCell(cell);
            cell = new PdfPCell(new PdfParagraph(dataset.get(i).getPayCapital().toString()));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setVerticalAlignment(Element.ALIGN_CENTER);
            table.addCell(cell);
            cell = new PdfPCell(new PdfParagraph(dataset.get(i).getDate()));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setVerticalAlignment(Element.ALIGN_CENTER);
            table.addCell(cell);
            cell = new PdfPCell(new PdfParagraph(dataset.get(i).getPayTotalMoney().toString()));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setVerticalAlignment(Element.ALIGN_CENTER);
            table.addCell(cell);
        }
        
        try {
            document.add(table);
        } catch (DocumentException e) {
            e.printStackTrace();
        }

    }

    public void exportTopTablePdf(int columns, int widths, List<Map<String, PdfParagraph>> params, Document document)
            throws DocumentException {
        PdfPTable table = new PdfPTable(columns);
        table.setHorizontalAlignment(Element.ALIGN_LEFT);
        table.setWidthPercentage(widths * columns);
        PdfPCell cell = null;
        PdfPCell cell1 = null;
        PdfPCell cell2 = null;
        float height;
        float width;
        for (int i = 0; i < params.size(); i++) {
            Map<String, PdfParagraph> paramMap = params.get(i);
            cell = new PdfPCell(paramMap.get("name"));
            cell1 = new PdfPCell(paramMap.get("value"));
            cell.setHorizontalAlignment(Element.ALIGN_LEFT);
            cell.setVerticalAlignment(Element.ALIGN_LEFT);
            cell1.setHorizontalAlignment(Element.ALIGN_LEFT);
            cell1.setVerticalAlignment(Element.ALIGN_LEFT);
            cell.setBorder(0);
            cell1.setBorder(0);
            table.addCell(cell);
            table.addCell(cell1);
            cell2 = new PdfPCell(new PdfParagraph("   "));
            cell2.setHorizontalAlignment(Element.ALIGN_LEFT);
            cell2.setVerticalAlignment(Element.ALIGN_LEFT);
            cell2.setBorder(0);
            table.addCell(cell2);
            cell2 = new PdfPCell(new PdfParagraph("   "));
            cell2.setHorizontalAlignment(Element.ALIGN_LEFT);
            cell2.setVerticalAlignment(Element.ALIGN_LEFT);
            cell2.setBorder(0);
            table.addCell(cell2);
        }

        try {
            document.add(table);
        } catch (com.lowagie.text.DocumentException e) {
            e.printStackTrace();
        }
    }

}
