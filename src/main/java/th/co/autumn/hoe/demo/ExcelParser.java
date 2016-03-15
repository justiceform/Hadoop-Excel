package th.co.autumn.hoe.demo;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.CellReference;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelParser {

	private static final Log LOG = LogFactory.getLog(ExcelParser.class);
	private StringBuilder currentString = null;
	private long bytesRead = 0;
	private Workbook workbook;

	private String getCellValue(String cellId,Sheet sheet){
		String value = "";
		CellReference cellReference = new CellReference(cellId);
		Row row = sheet.getRow(cellReference.getRow());
		Cell cell = row.getCell(cellReference.getCol());
		switch (cell.getCellType()) {
			case Cell.CELL_TYPE_BOOLEAN:
				bytesRead++;
				value = cell.getBooleanCellValue() + "\t";
				System.out.print(cell.getBooleanCellValue());
				break;

			case Cell.CELL_TYPE_NUMERIC:
				bytesRead++;
				value = cell.getNumericCellValue() + "\t";
				System.out.print(cell.getNumericCellValue());
				break;

			case Cell.CELL_TYPE_STRING:
				bytesRead++;
				value = cell.getStringCellValue() + "\t";
				System.out.print(cell.getStringCellValue());
				break;
		}
		return value.trim();
	}

	public ArrayList<ReceiverDAO> parseExcelData(InputStream is) {
		ArrayList<ReceiverDAO> dataList= new ArrayList<ReceiverDAO>();

		try {
			System.out.println("--> open workbook");
			
			
			workbook = new XSSFWorkbook(is);
			System.out.println("start get sheet");

			// Taking first sheet from the workbook
			// XSSFSheet sheet = workbook.getSheetAt(0);
			Sheet sheet = workbook.getSheetAt(3);
			currentString = new StringBuilder();

//
//			String bbl_fms = getCellValue("M5",sheet);
//			String bbl_hp = getCellValue("M6",sheet);
//			String bbl_loan = getCellValue("M7",sheet);
//			String bbl_total = getCellValue("M8",sheet);
//			String kbank_fms = getCellValue("M9",sheet);
//			String kbank_hp = getCellValue("M10",sheet);
//			String kbank_loan = getCellValue("M11",sheet);
//			String kbank_total = getCellValue("M13",sheet);
//			String scb_fms = getCellValue("M14",sheet);
//			String scb_hp = getCellValue("M15",sheet);
//			String scb_loan = getCellValue("M16",sheet);
//			String scb_tatal = getCellValue("M17",sheet);

			ReceiverDAO.currentDate = getCellValue("J5",sheet);
			dataList.add(new ReceiverDAO("BBL","FMS",getCellValue("M5",sheet)));
			dataList.add(new ReceiverDAO("BBL","HP",getCellValue("M6",sheet)));
			dataList.add(new ReceiverDAO("BBL","LOAN",getCellValue("M7",sheet)));
			dataList.add(new ReceiverDAO("BBL","TOTAL",getCellValue("M8",sheet)));
			dataList.add(new ReceiverDAO("KBANK","FMS",getCellValue("M9",sheet)));
			dataList.add(new ReceiverDAO("KBANK","HP",getCellValue("M10",sheet)));
			dataList.add(new ReceiverDAO("KBANK","LOAN",getCellValue("M11",sheet)));
			dataList.add(new ReceiverDAO("KBANK","TOTAL",getCellValue("M13",sheet)));
			dataList.add(new ReceiverDAO("SCB","FMS",getCellValue("M14",sheet)));
			dataList.add(new ReceiverDAO("SCB","HP",getCellValue("M15",sheet)));
			dataList.add(new ReceiverDAO("SCB","LOAN",getCellValue("M16",sheet)));
			dataList.add(new ReceiverDAO("SCB","TOTAL",getCellValue("M17",sheet)));

			// suppose your formula is in B3

//			}
			is.close();
		} catch (IOException e) {
			LOG.error("IO Exception : File not found " + e);
		}
//		System.out.print(currentString.toString());
		return dataList;

	}

	public long getBytesRead() {
		return bytesRead;
	}

}

