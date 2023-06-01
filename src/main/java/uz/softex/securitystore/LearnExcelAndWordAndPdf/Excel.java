package uz.softex.securitystore.LearnExcelAndWordAndPdf;

import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.xssf.usermodel.*;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;

import java.io.File;
import java.io.FileOutputStream;


public class Excel {
    public static void main(String[] args) throws Exception {

//        FileInputStream file = new FileInputStream(new File("src/main/java/WorkWithExcel/workbook.xlsx"));
//        XSSFWorkbook workbook = new XSSFWorkbook(file);
//
//
//        Sheet sheet = (Sheet) workbook.getSheetAt(0);
//
//        Map<Integer, List<String>> data = new HashMap<>();
//        int i = 0;
//        for (Row row : sheet) {
//            data.put(i, new ArrayList<String>());
//            for (Cell cell : row) {
//                switch (cell.getType()) {
//                    case STRING: ... break;
//                    case NUMERIC: ... break;
//                    case BOOLEAN: ... break;
//                    case FORMULA: ... break;
//                    default: data.get(new Integer(i)).add(" ");
//                }
//            }
//            i++;
//        }

//        XSSFWorkbook workbook = new XSSFWorkbook();
//        Sheet sheet = (Sheet) workbook.createSheet("inputs");
//        sheet.

        XSSFWorkbook workbook = new XSSFWorkbook();
        File file = new File("F:\\/excel.xlsx");
        FileOutputStream fileOutputStream = new FileOutputStream(file);

        XSSFSheet sheet = workbook.createSheet("Sheet№1");
        CellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setBorderTop(BorderStyle.SLANTED_DASH_DOT);
        XSSFRow row = sheet.createRow(1);
        XSSFCell cell = row.createCell(0);
        cell.setBlank();
        cell.setCellValue("Test");
        cell.setCellStyle(cellStyle);

        workbook.write(fileOutputStream);
        workbook.close();

    }
}
