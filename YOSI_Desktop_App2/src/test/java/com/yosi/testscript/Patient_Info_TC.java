package com.yosi.testscript;

import org.testng.annotations.Test;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.LogStatus;
import com.yosi.base.YosiBaseClass;
import com.yosi.pom.Patient_AddInfo_POM;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;

import java.io.File;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.yosi.util.*;

import io.appium.java_client.android.AndroidDriver;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.Select;
import org.testng.annotations.AfterClass;

public class Patient_Info_TC extends YosiBaseClass{
	


	
	 @BeforeClass
	  public void beforeClass() throws InterruptedException {
		 
		// startTest();
		 extent = new ExtentReports (System.getProperty("user.dir") +"/test-output/PatientExtentReport.html", true);
		 //extent.addSystemInfo("Environment","Environment Name")
		 extent
		                .addSystemInfo("Host Name", "SoftwareTestingMaterial")
		                .addSystemInfo("Environment", "Automation Testing");
		                
		                //loading the external xml file (i.e., extent-config.xml) which was placed under the base directory
		                //You could find the xml file below. Create xml file in your project and copy past the code mentioned below
		                extent.loadConfig(new File(System.getProperty("user.dir")+"D:\\Automation testing\\YOSI_Desktop_App\\extent-config.xml"));
		  desktop_init();
		  start_paperwork();
		  select_doctor();
		  verify_patient();		           
	  }
	
	  @DataProvider
	  public Object[][] dp() throws Exception{
		 
		 String file_location = "D:/Automation testing/YOSI_Desktop_App/src/main/java/com/yosi/testdata/TestData.xlsx";
		 String SheetName = "Patient_info";
		 
		 Object[][] testObjArray = ExcelUtils.getTableArray(file_location,SheetName);
		  
	     return (testObjArray);
	     
	  }
	  	
	    
  @Test (dataProvider = "dp")
  public void patient_info(String address1,String address2,String zipcode) 
 
  {		
	System.out.println("PATIENT INFORMATION");
	Log.info("Data prefilled in PAtient info page");
	String fname = driver.findElement(By.xpath("//*[@id=\"firstname\"]")).getAttribute("value");
	System.out.println(fname);
	Log.info("First Name of the patient " +fname);
	
	
	String regx = "[a-zA-Z]+\\.?";
    Pattern f_pattern = Pattern.compile(regx,Pattern.CASE_INSENSITIVE);
    Matcher f_matcher = f_pattern.matcher(fname);
    Boolean f_bol =f_matcher.find();
    System.out.println(f_matcher.find()); 
	logger = extent.startTest("First Name Validation In PATIENT INFO PAGE");
	Log.info(" First Name Validation In PATIENT INFO PAGE");

		
		if ( f_bol == true )
		{
			logger.log(LogStatus.PASS, "The name "+fname+"  is contains char only");
		}
		
		else
		{
			logger.log(LogStatus.FAIL, "The name "+fname+" is Invalid");
		}
		
		 extent.endTest(logger);
	String lname = driver.findElement(By.xpath("//*[@id=\"lastname\"]")).getAttribute("value");
	System.out.println(lname);
	logger = extent.startTest("Last Name Validation In PATIENT INFO PAGE");
	 Pattern l_pattern = Pattern.compile(regx,Pattern.CASE_INSENSITIVE);
	    Matcher l_matcher = l_pattern.matcher(fname);
	    Boolean l_bol =l_matcher.find();
	    System.out.println(l_matcher.find()); 
	Log.info(" Last Name Validation In PATIENT INFO PAGE");

		
		if ( l_bol == true )
		{
			logger.log(LogStatus.PASS, "The name "+lname+"  is contains char only");
		}
		
		else
		{
			logger.log(LogStatus.FAIL, "The name "+lname+" is Invalid");
		}
		
		 extent.endTest(logger);
	
	Log.info("Last Name of the patient " +lname);
	String gender = driver.findElement(By.xpath("//*[@id=\"select_gender_option1\"]")).getAttribute("value");
	Log.info("Gender of the patient " +gender);
	System.out.println(gender);
	String dob = driver.findElement(By.xpath("//*[@id=\"date\"]")).getAttribute("value");
	Log.info("DOB of the patient " +dob);
	System.out.println(dob);
	  	
		driver.findElement(By.id("address1")).click();
		driver.findElement(By.id("address1")).clear();
		driver.findElement(By.id("address1")).sendKeys(address1);
		Log.info("Enter Address1 " +address1);
		driver.findElement(By.id("address2")).click();
		driver.findElement(By.id("address2")).clear();	
		driver.findElement(By.id("address2")).sendKeys(address2);
		Log.info("Enter Address2 " +address2);
		driver.findElement(By.id("zipcode")).click();
		driver.findElement(By.id("zipcode")).clear();
		driver.findElement(By.id("zipcode")).sendKeys(zipcode);
		
		if(driver.findElement(By.xpath("//*[@id=\"statecity_Modal\"]")).isDisplayed())
		{
			Select city_select= new Select(driver.findElement(By.xpath("//*[@id=\"select_statecity\"]")));
			city_select.selectByIndex(1);
			
		}
		String add1 = driver.findElement(By.id("address1")).getAttribute("value");
		String add2 = driver.findElement(By.id("address2")).getAttribute("value");
		String zip =driver.findElement(By.id("zipcode")).getAttribute("value");
		Log.info("Address of the patient \n"+add1+ "," +add2+","+zip+".");
	
		String zipcode_ele =driver.findElement(By.xpath("//*[@id=\"zipcode\"]")).getAttribute("value");
		String regex = "^[0-9]{5}(?:-[0-9]{4})?$";
		Pattern pattern = Pattern.compile(regex);

		Matcher matcher = pattern.matcher(zipcode_ele);
		Boolean zip_match=matcher.matches();
		System.out.println(matcher.matches());
		System.out.println("Zipcode"+zipcode_ele);
		logger = extent.startTest("Zipcode Validation In PATIENT INFO PAGE");
		Log.info("Zipcode Validation In PATIENT INFO PAGE");
  
			
			if (zip_match == true )
			{
				logger.log(LogStatus.PASS, "The specified "+zipcode_ele +" US ZIP Code is valid");
			}
			
			else
			{
				logger.log(LogStatus.FAIL, "The specified "+zipcode_ele +" US ZIP Code is Invalid");
			}
			
			 extent.endTest(logger);
			
			 driver.manage().timeouts().implicitlyWait(15000,TimeUnit.SECONDS); 
			 
			 
  }

  
  
 

  @AfterClass
  public void afterClass() throws InterruptedException 
  {
		logger = extent.startTest("Patient Info page"); 
		 if(driver.findElement(By.xpath("//form[@id='form_mpersonal_info']/div/div[7]/div/input")).isDisplayed() == true)
		 {
				driver.findElement(By.xpath("//form[@id='form_mpersonal_info']/div/div[7]/div/input")).click();	
				logger.log(LogStatus.PASS, "Test Case is Passed");
		 }
		 else
		 {
			 logger.log(LogStatus.FAIL, "Test Case is Failed ");
		 }
		 extent.endTest(logger);
	
	  patient_guarantor();
	  insurance_info();
	  pat_add_info();
	  emergency_contact();
	  patient_lifestyle();
	  patient_past_medical();
	  patient_rx_info();
	  addition_page();
	  medical_history_page();
	  phq_page();
	  gad_page();
	  encounter_page();
	  ros_page();
	  policy1_page();
	  policy2_page();
	  signature_page();
	 driver.manage().timeouts().implicitlyWait(5000,TimeUnit.SECONDS); 
	
	 extent.flush();
	 extent.close();
	 driver.quit();
  }

}
