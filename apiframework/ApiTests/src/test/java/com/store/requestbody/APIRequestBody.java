package com.store.requestbody;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.testng.annotations.DataProvider;

import com.store.customException.CustomExceptions;
import com.store.reader.Config;
import com.store.reader.ExcelReader;

public class APIRequestBody {

	@DataProvider(name = "Excel Data Provider")
	static Object[][] addListsPayload()
			throws FileNotFoundException, IOException, CustomExceptions, InvalidFormatException {
		Object[][] obj = excelDataProvider(System.getProperty(Config.CURRENT_USER_DIRECTORY) + Config.TEST_DATA_EXCEL, 0);
		return obj;
	}

	private static Object[][] excelDataProvider(String filePath, int sheetIndex)
			throws FileNotFoundException, IOException, CustomExceptions, InvalidFormatException {

		ArrayList<ArrayList<String>> excelList = ExcelReader.excelReader(filePath, sheetIndex);
		Object[][] obj = new Object[excelList.size()][2];
		int i = 0;
		for (ArrayList<String> excelRow : excelList) {
			for (int j = 0; j < excelRow.size(); j++) {
				obj[i][0] = ExcelReader.headers;
				obj[i][1] = excelRow;

			}
			i++;
		}
		return obj;
	}

}
