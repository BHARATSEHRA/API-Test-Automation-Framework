package com.store.testcasebase;

import java.io.PrintStream;
import java.io.StringWriter;
import java.lang.reflect.Method;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.store.reader.Config;
import com.store.reporterLogger.ReportLogger;

import org.apache.commons.io.output.WriterOutputStream;
import org.testng.ITestResult;
import org.testng.annotations.*;

import io.restassured.RestAssured;
import io.restassured.specification.RequestSpecification;

public class TestBase {
	protected static RequestSpecification httpRequest;
	ExtentHtmlReporter htmlReporter;
	ExtentReports extent;
	ExtentTest extentTest;
	private StringWriter requestWriter, responseWriter;
	private PrintStream requestCapture, responseCapture;

	@BeforeSuite
	public void setUpBeforeSuite() throws Exception {
		ReportLogger.generateReport();
	}

	@BeforeClass
	public void setupBeforeClass() throws Exception {
		Config.readConfig();
		httpRequest = RestAssured.given();
	}

	@BeforeMethod
	public void setUpBeforeMethod(Method method) throws Exception {
		ReportLogger.newTest(method.getName());
		requestWriter = new StringWriter();
		requestCapture = new PrintStream(new WriterOutputStream(requestWriter), true);
		responseWriter = new StringWriter();
		responseCapture = new PrintStream(new WriterOutputStream(responseWriter), true);
	}

	@AfterMethod
	public void getResult(ITestResult result) {
		if (result.getStatus() == ITestResult.FAILURE) {
			if (!(responseWriter.toString().equals(""))) {
				ReportLogger.error("Testcase fails. Below is the API Request sent");
				ReportLogger.error(requestWriter.toString());
				ReportLogger.error("API Response received : ");
				ReportLogger.error(responseWriter.toString());
			}
			ReportLogger.fail(result);

		} else if (result.getStatus() == ITestResult.SUCCESS) {
			ReportLogger.pass(result);
		} else {
			ReportLogger.skipped(result);
		}
	}

	@AfterSuite
	public void tearDownAfterSuite() {
		// to write or update test information to reporter
		ReportLogger.printReport();
	}

}
