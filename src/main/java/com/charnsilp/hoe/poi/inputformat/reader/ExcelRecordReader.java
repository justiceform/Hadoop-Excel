package com.charnsilp.hoe.poi.inputformat.reader;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.*;
import org.apache.hadoop.mapreduce.InputSplit;
import org.apache.hadoop.mapreduce.RecordReader;
import org.apache.hadoop.mapreduce.TaskAttemptContext;
import org.apache.hadoop.mapreduce.lib.input.FileSplit;
import org.apache.poi.hssf.util.CellReference;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import com.charnsilp.hoe.poi.dao.ReceiverDAO;


/**
 * Reads excel spread sheet , where keys are offset in file and value is the row
 * containing all column as a string.
 */
public class ExcelRecordReader extends RecordReader<LongWritable, ReceiverDAO> {

	private InputStream is;
	private ArrayList<ReceiverDAO> dataList= new ArrayList<ReceiverDAO>();
	private int currentData;
	private Workbook workbook;
	private Sheet sheet;
	private long bytesRead = 0;



	@Override
	public void initialize(InputSplit genericSplit, TaskAttemptContext context)
			throws IOException, InterruptedException {

		FileSplit split = (FileSplit) genericSplit;
		Configuration job = context.getConfiguration();
		final Path file = split.getPath();

		FileSystem fs = file.getFileSystem(job);
		FSDataInputStream fileIn = fs.open(split.getPath());

		System.out.println("start ExcelParser class");

		workbook = new XSSFWorkbook(fileIn);
		sheet = workbook.getSheetAt(3);

		ReceiverDAO.currentDate = getCellValue("J18",sheet);

		getDataFromExcel();
	}

	private void getDataFromExcel(){
		dataList.add(createReceiverDAO("BBL","FMS","M18"));
		dataList.add(createReceiverDAO("BBL","HP","M19"));
		dataList.add(createReceiverDAO("BBL","LOAN","M20"));
		dataList.add(createReceiverDAO("BBL","TOTAL","M21"));
		dataList.add(createReceiverDAO("KBANK","FMS","M22"));
		dataList.add(createReceiverDAO("KBANK","HP","M23"));
		dataList.add(createReceiverDAO("KBANK","LOAN","M24"));
		dataList.add(createReceiverDAO("KBANK","TOTAL","M25"));
		dataList.add(createReceiverDAO("SCB","FMS","M26"));
		dataList.add(createReceiverDAO("SCB","HP","M27"));
		dataList.add(createReceiverDAO("SCB","LOAN","M28"));
		dataList.add(createReceiverDAO("SCB","TOTAL","M29"));
	}


	private ReceiverDAO createReceiverDAO(String bank, String bus, String cellID){
		return (new ReceiverDAO(bank,bus,(getCellValue(cellID,sheet))));
	}
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


	@Override
	public boolean nextKeyValue() throws IOException, InterruptedException {
		if(currentData < dataList.size()-1) {
			currentData++;
			return true;
		}
		return false;
	}

	@Override
	public LongWritable getCurrentKey() throws IOException,
			InterruptedException {
		
		return new LongWritable(currentData);
	}

	@Override
	public ReceiverDAO getCurrentValue() throws IOException, InterruptedException {
		if(dataList.get(currentData) == null){
			return null;
		}
		return dataList.get(currentData);

	}

	@Override
	public float getProgress() throws IOException, InterruptedException {
		return 0;
	}

	@Override
	public void close() throws IOException {
		if (is != null) {
			is.close();
		}

	}

}

