package uz.softex.securitystore.LearnExcelAndWordAndPdf;

import org.apache.poi.util.Units;
import org.apache.poi.xwpf.usermodel.*;

import java.io.*;

public class Word {
    public static void main(String[] args) throws Exception {
        XWPFDocument document = new XWPFDocument();// wordda dokument yaratish

        XWPFParagraph paragraph = document.createParagraph();//paragraf yaratish

        XWPFRun run = paragraph.createRun(); //bu parafda run yaratish yani bolimchalar bularni keyin ozgartirsa boladi shriftini

        run.setText("mana kirdi");
        run.setBold(true);
        run.setFontSize(19);
        paragraph.setAlignment(ParagraphAlignment.CENTER);//bu shu paragrafni qayerda bolishini aytadi

        //jadval
        XWPFTable table = document.createTable();
        XWPFTableRow row = table.getRow(0);//qator yaratish
        XWPFTableCell cell = row.getCell(0);//ustun yaratish
        cell.setText("Id");
        row.createCell().setText("Username");
        row.createCell().setText("Title");
        row = table.createRow();//yangi qator ochish

        FileInputStream fileInputStream = new FileInputStream("F:\\alone.jpg");
        paragraph = document.createParagraph();
        run = paragraph.createRun();
        run.setText("rasm Sarlavhasi");
        run.addBreak();
        run.addPicture(fileInputStream,XWPFDocument.PICTURE_TYPE_JPEG,"F:\\alone.jpg", Units.toEMU(200),Units.toEMU(200));//bu rasm qoshihs  bu yerda nits emular pikselllar soninini aytadi

        //faylga yozish

        File file = new File("F:\\/wordDocument.docx");
        FileOutputStream fileOutputStream = new FileOutputStream(file);
        document.write(fileOutputStream);
        fileOutputStream.close();


        BufferedReader bufferedReader = new BufferedReader(new FileReader(file));//bu o`qiydi
    }
}
