package com.store.reader;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import com.google.common.io.Files;
import com.store.customException.CustomExceptions;

public class ExcelReader {

	public static ArrayList<String> headers;

	private ArrayList<ArrayList<String>> list;

	public static ArrayList<ArrayList<String>> excelReader(String filePath, int sheetIndex)
			throws FileNotFoundException, IOException, CustomExceptions, InvalidFormatException {
		ExcelReader ex = new ExcelReader();
		String ext = Files.getFileExtension(filePath);

		try {

			if (ext.equals("xls") || ext.equals("xlsx")) {
				ex.list = ex.ReadXLSFile(filePath, sheetIndex);
			}
		} catch (FileNotFoundException error) {
			throw new CustomExceptions("Incorrect file Extension : " + ext);
		}
		return ex.list;
	}

	private ArrayList<ArrayList<String>> ReadXLSFile(String filePath, int sheetIndex)
			throws FileNotFoundException, IOException, InvalidFormatException {

		OPCPackage pkg = OPCPackage.open(new FileInputStream(new File(filePath)));

		// File file=new File(pathname)
		// Create Workbook instance holding reference to .xls file
		XSSFWorkbook oWb = new XSSFWorkbook(pkg);

		// Get first/desired sheet from the workbook
		XSSFSheet oWs = oWb.getSheetAt(sheetIndex);

		Iterator<Row> rowIterator = oWs.iterator();
		return getExcelData(rowIterator);

	}

	@SuppressWarnings("unused")
	private ArrayList<ArrayList<String>> ReadXLSXFile(String filePath, int sheetIndex)
			throws FileNotFoundException, IOException {

		// Create Workbook instance holding reference to .xls file
		XSSFWorkbook oWb = new XSSFWorkbook(new FileInputStream(new File(filePath)));

		// Get first/desired sheet from the workbook
		XSSFSheet oWs = oWb.getSheetAt(sheetIndex);
		// XSSFSheet oWs = oWb.getSheet("");

		// Iterate through each rows one by one
		Iterator<Row> rowIterator = oWs.iterator();
		return getExcelData(rowIterator);

	}

	private ArrayList<ArrayList<String>> getExcelData(Iterator<Row> rowIterator) {

		ArrayList<ArrayList<String>> list = null;
		Row rw = rowIterator.next();
		headers = getRowData(rw);
		list = new ArrayList<ArrayList<String>>();
		while (rowIterator.hasNext()) {

			Row row = rowIterator.next();
			ArrayList<String> rowData = getRowData(row);
			list.add(rowData);
			rowData = null;
		}
		return list;
	}

	private ArrayList<String> getRowData(Row row) {
		ArrayList<String> rowData = new ArrayList<String>();
		String temp = null;
		// For each row, iterate through all the columns
		Iterator<Cell> cellIterator = row.cellIterator();
		while (cellIterator.hasNext()) {
			Cell cell = cellIterator.next();
			// Check the cell type and format accordingly
			switch (cell.getCellType()) {
			case Cell.CELL_TYPE_NUMERIC:
				temp = cell.getNumericCellValue() + "";
				break;
			case Cell.CELL_TYPE_STRING:
				temp = cell.getStringCellValue();
				break;
			case Cell.CELL_TYPE_BOOLEAN:
				temp = cell.getBooleanCellValue() + "";
				break;
			case Cell.CELL_TYPE_BLANK:
				temp = "";
				break;
			case Cell.CELL_TYPE_FORMULA:
				temp = cell.getCellFormula();
				break;
			}
			rowData.add(temp);
		}
		temp = null;
		cellIterator = null;
		return rowData;
	}

}
