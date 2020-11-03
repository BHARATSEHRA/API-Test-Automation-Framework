package com.store.reporterLogger;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.HashMap;
import org.apache.log4j.Logger;
import org.testng.ITestResult;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.ChartLocation;
import com.aventstack.extentreports.reporter.configuration.Theme;
import com.store.reader.PropertyReader;

public class ReportLogger {
	private static ExtentHtmlReporter htmlReporter;
	private static ExtentReports extent;
	private static ExtentTest extentTest;
	private static HashMap<String, String> reportPropertyMap;
	public static final Logger LOGGER = Logger.getLogger(ReportLogger.class);

	// Function will log info message on HTML report and logs

	public static void generateReport() throws IOException {
		reportPropertyMap = new PropertyReader().readProperties("\\src\\test\\resources\\extentReport.properties");
		htmlReporter = new ExtentHtmlReporter(System.getProperty("user.dir") + reportPropertyMap.get("htmlReportPath"));
		extent = new ExtentReports();
		extent.attachReporter(htmlReporter);
		htmlReporter.config().setChartVisibilityOnOpen(true);
		htmlReporter.config().setDocumentTitle(reportPropertyMap.get("htmlReportTitle"));
		htmlReporter.config().setReportName(reportPropertyMap.get("htmlReportName"));
		htmlReporter.config().setTestViewChartLocation(ChartLocation.TOP);
		htmlReporter.config().setTheme(Theme.STANDARD);
		htmlReporter.config().setTimeStampFormat(reportPropertyMap.get("TimeStampFormat"));
		LOGGER.info("HTML report created : " + reportPropertyMap.get("htmlReportName"));
	}

	// Function to log and report info

	public static void info(String msg) {
		extentTest.log(Status.INFO, msg);
		LOGGER.info(msg);
	}

	// Function to log and report info on error

	public static void error(String msg) {
		LOGGER.error(msg);
	}

	// Function to log and report info on test case pass

	public static void pass(ITestResult result) {
		extentTest.log(Status.PASS, MarkupHelper.createLabel(result.getName() + " PASSED ", ExtentColor.GREEN));
		LOGGER.info(result.getName() + " PASSED ");
	}

	// Function to log and report info on test case fail

	public static void fail(ITestResult result) {
		extentTest.log(Status.FAIL, MarkupHelper.createLabel(result.getName() + " FAILED ", ExtentColor.RED));
		extentTest.fail(result.getThrowable());
		LOGGER.error(result.getName() + " FAILED ");
		StringWriter sw = new StringWriter();
		result.getThrowable().printStackTrace(new PrintWriter(sw));
		LOGGER.error(sw.toString());
		sw = null;
	}

	// Function to log and report info on test case skip

	public static void skipped(ITestResult result) {
		extentTest.log(Status.SKIP, MarkupHelper.createLabel(result.getName() + " SKIPPED ", ExtentColor.ORANGE));
		extentTest.skip(result.getThrowable());
		LOGGER.error(result.getName() + " SKIPPED ");
	}

	// To publish report
	public static void printReport() {
		extent.flush();
		LOGGER.info("HTML report saved at " + System.getProperty("user.dir") + reportPropertyMap.get("htmlReportPath"));
	}

	// To create new extent report
	public static void newTest(String method) {
		extentTest = extent.createTest(method);
		LOGGER.info("New testcase :" + method);
	}

}
