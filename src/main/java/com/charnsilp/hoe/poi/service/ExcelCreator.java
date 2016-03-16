package com.charnsilp.hoe.poi.service;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.*;
import org.apache.hadoop.util.Progressable;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by tek on 3/16/2016 AD.
 */
public class ExcelCreator {

    public void create(ArrayList<ArrayList<String>> result,OutputStream fileOutputStram) {
        Workbook workbook = new XSSFWorkbook();

        Sheet sheet = workbook.createSheet("Result");
        Iterator<ArrayList<String>> resultIterator = result.iterator();
        int rowCount = 0;
        while(resultIterator.hasNext()){

            ArrayList<String> dataRow = resultIterator.next();
            Iterator<String> rowIterator = dataRow.iterator();
            Row row = sheet.createRow(rowCount);

            int cellCount =0;
            while(rowIterator.hasNext()){
                String dataCell = rowIterator.next();
                Cell cell = row.createCell(cellCount);
                cell.setCellValue(dataCell);

                cellCount++;
            }
            rowCount++;
        }

        try {

            workbook.write(fileOutputStram);
            fileOutputStram.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
