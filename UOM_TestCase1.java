package Test_Cases.UnitOfMeasure_Testcases;

import Page_Objects.*;
import Test_Cases.Common_TestCases;
import Utilities.*;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.awt.*;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.io.FileInputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
import java.util.concurrent.TimeUnit;


public class UOM_TestCase1 {



    WebDriver driver;
        private static final Logger LOG = LogManager.getLogger(UOM_TestCase1.class);
        public Properties SeleniumProperties = new Properties();
        public Utilities.ExcelUtils ExcelUtils = new ExcelUtils();
        public Test_Cases.Common_TestCases Common_TestCases;
        public InputStream input = null;
        public Page_Objects.Login Login;
        public Page_Objects.unitOfMeasure unitOfMeasur;
        public String[][] Login_Data;
        public String[][] Business_Data;
        public String[][] Category_Data;
        public String [][] Capital_Allocations;
        public String[][] Scenario_Analysis;
        public Utilities.WebdriverUtil WebdriverUtil;
        public Utilities.Utils Utils;
        public Utilities.CommonUtil CommonUtil;
        public String[] headers;
        static ExtentTest test;
        static String UOM_NAME;
        static ExtentReports report;
        static unitOfMeasure unitOfMeasure;
        PageElements pg = new PageElements(driver);
        CommonFunctions cf = new CommonFunctions(driver);
        UOMUtil pg2=new UOMUtil(driver);
        private String Status;


    @BeforeClass
        public void testSetUp() throws Exception {
        //ExcelUtils=new ExcelUtils();
        input = new FileInputStream("src/test/resources/Selenium.properties");
        SeleniumProperties.load(input);
        Login_Data = ExcelUtils.readExcelDataFileToArray(SeleniumProperties.getProperty("UOM_testDataSheet"), "Sheet1");
        Business_Data = ExcelUtils.readExcelDataFileToArray(SeleniumProperties.getProperty("UOM_testDataSheet"), "Business Entity");
        Category_Data = ExcelUtils.readExcelDataFileToArray(SeleniumProperties.getProperty("UOM_testDataSheet"), "Risk Category");
        Capital_Allocations=ExcelUtils.readExcelDataFileToArray(SeleniumProperties.getProperty("UOM_testDataSheet"), "Capital Allocations");
        Scenario_Analysis = ExcelUtils.readExcelDataFileToArray(SeleniumProperties.getProperty("UOM_testDataSheet"), "Scenario Analysis");
        //excel_test_data_Login
        String baseUrl = Login_Data[1][2];
        WebdriverUtil = new WebdriverUtil(baseUrl);
        driver = WebdriverUtil.setWebdriver();
        driver.manage().deleteAllCookies();
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        driver.get(baseUrl);
        Common_TestCases = new Common_TestCases(driver);
        cf = new CommonFunctions(driver);
        unitOfMeasure = new unitOfMeasure();
        Login = new Login();
        Common_TestCases.UOM_Login();
        Utils = new Utils(driver);
        CommonUtil = new CommonUtil(driver);
        String timeStamp = new SimpleDateFormat("yyyy.MM.dd.hh.mm.ss").format(new Date());
        report = new ExtentReports(System.getProperty("user.dir") + "\\Reports\\UOM_Report_TestCase1" + timeStamp + ".html", false);


    }

        @Test
        public void VerifyUOMWorkflowTestCase1() throws Exception {
            pg = new PageElements(driver);
            //pg2 =new UOMUtil(driver);
            cf = new CommonFunctions(driver);


            try {
                driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
                test = report.startTest("Verify the risk event workflow for  status(Open->Draft->Cancelled)");
                cf.javaScriptClick(cf.waitForElement(pg.Primary_Menu));
                LOG.info("Clicked on Primary menu");
                cf.javaScriptClick(cf.waitForElement(pg.Risk_Measure));
                LOG.info("Clicked on Risk Measurement");
                cf.javaScriptClick(cf.waitForElement(pg.UnitOfMeasure_menu));
                LOG.info("Clicked on Unit Of Measure");
                cf.javaScriptClick(cf.waitForElement(pg.Add_New_Button));
                Common_TestCases.Business_Entity();
                CommonUtil.clickOnElement(unitOfMeasure.Search_Bus_Entity);
                CommonUtil.typeOnElement(unitOfMeasure.Search_Bus_Entity, Business_Data[1][0]);
                CommonUtil.typeOnElement(unitOfMeasure.Search_Bus_Entity,String.valueOf(Keys.ENTER));
                //CommonUtil.secondsDelay(2);
                CommonUtil.clickOnElement(unitOfMeasure.Entity_check_box);
                LOG.info("Select Business Entity");
                CommonUtil.secondsDelay(2);
                CommonUtil.clickOnElement(unitOfMeasure.Done_Button);
                CommonUtil.clickOnElement(unitOfMeasure.Unit_Of_Measure_Name);
                CommonUtil.typeOnElement(unitOfMeasure.Unit_Of_Measure_Input_Box,Category_Data[15][0]+String.valueOf(Keys.ENTER));
                CommonUtil.secondsDelay(3);
                test.log(LogStatus.PASS,"Business Entity Selected", test.addBase64ScreenShot(Utils.captureScreenshot("UOM_TestCase1")));
                CommonUtil.clickOnElement(unitOfMeasure.UOM_Owner);
                CommonUtil.typeOnElement(unitOfMeasure.UOM_Owner, Business_Data[1][1]);
                cf.clickAndTryMulti(cf.waitForElement(pg.select_owner),2);
                CommonUtil.clickOnElement(unitOfMeasure.UOM_Description);
                CommonUtil.typeOnElement(unitOfMeasure.UOM_Description, Business_Data[1][2]);
                LOG.info("Name and Owner option captured");
                CommonUtil.clickOnElement(unitOfMeasure.RC_principal_risk_DropDown);
                CommonUtil.typeOnElement(unitOfMeasure.Risk_category_Input_box,Category_Data[1][1]+String.valueOf(Keys.ENTER));
                CommonUtil.clickOnElement(unitOfMeasure.RC_Risk_Special_DropDown);
                CommonUtil.typeOnElement(unitOfMeasure.Specialist_Risk_Input_Box,Category_Data[1][2]);
                CommonUtil.clickOnElement(unitOfMeasure.SP_Business_Change_Risk);
                LOG.info("Specialist Risk dropdown selected");
                CommonUtil.clickOnElement(unitOfMeasure.Basel_dropdown);
                CommonUtil.typeOnElement(unitOfMeasure.Basel_input_box,Category_Data[1][2]);
                CommonUtil.clickOnElement(unitOfMeasure.Basel_Internal_fraud);
                LOG.info("BASEL L1 Selected");
                CommonUtil.clickOnElement(unitOfMeasure.Year_Completion_Input_box);
                CommonUtil.clickOnElement(unitOfMeasure.Year_Completion_select);
                LOG.info("Completion Year Selected");
                CommonUtil.clickOnElement(unitOfMeasure.Quarter_input_Box);
                CommonUtil.clickOnElement(unitOfMeasure.Quarter_Completion_Select);
                LOG.info("Completion Quarter Selected");
                CommonUtil.clickOnElement(unitOfMeasure.Scenarion_Analysis_input_box);
                CommonUtil.clickOnElement(unitOfMeasure.Scenario_Analysis_Select);
                LOG.info("Scenario Analysis input Selected");
                String UOM_NAME=CommonUtil.Get_Text(unitOfMeasure.Capture_UOM_Name);//capture unique name generated
                System.out.println(UOM_NAME);
                LOG.info("Completion Quarter Selected");
                CommonUtil.clickOnElement(unitOfMeasure.Save_Button);
                Common_TestCases.Scrollup();
                CommonUtil.secondsDelay(2);
                String Draft_Status= CommonUtil.Get_Text(unitOfMeasure.Status);
                System.out.println(Draft_Status);
                LOG.info("Capital Allocation Captured");
                if (Draft_Status.equalsIgnoreCase(Business_Data[1][14]))
                {
                    test.log(LogStatus.PASS, "Status: Draft", test.addBase64ScreenShot(Utils.captureScreenshot("UOM_TestCase1")));
                } else
                {
                    test.log(LogStatus.FAIL, "Status: not Draft", test.addBase64ScreenShot(Utils.captureScreenshot("UOM_TestCase1")));
                }
                //Capture The Capital allocation
                cf.javaScriptClick(pg.Edit_Button);
                CommonUtil.waitForElement(unitOfMeasure.Capital_Allocations_RBB);
                CommonUtil.clickOnElement(unitOfMeasure.Capital_Allocations_RBB);
                CommonUtil.waitForElement(unitOfMeasure.Capital_Allocations_RBB);
                CommonUtil.typeOnElement_Control_All(unitOfMeasure.Capital_Allocations_RBB, Business_Data[1][5]);
                CommonUtil.clickOnElement(unitOfMeasure.Capital_Allocations_CIB);
                CommonUtil.typeOnElement_Control_All(unitOfMeasure.Capital_Allocations_CIB, Business_Data[1][4]);
                CommonUtil.typeOnElement(unitOfMeasure.Capital_Allocation_Rotation_input,Capital_Allocations[1][3]);
                CommonUtil.Explicitwait_Clickable(unitOfMeasure.Save4);
                cf.javaScriptClick(cf.waitForElement(pg.save_Button));
                CommonUtil.secondsDelay(2);
                cf.javaScriptClick(cf.waitForElement(pg.Action_Button2));
                cf.javaScriptClick(cf.waitForElement(pg.Continue_Complete_Assessment));
                CommonUtil.clickOnElement(unitOfMeasure.Assessement_Complete_Cntinue_Button);
                Common_TestCases.Scrollup();
                CommonUtil.secondsDelay(4);
                String Status2=CommonUtil.Get_Text(unitOfMeasure.Status);
                System.out.println(Status2);
                if (Status2.equalsIgnoreCase(Business_Data[2][14]))
                {
                    test.log(LogStatus.PASS, "Awaiting Assessment", test.addBase64ScreenShot(Utils.captureScreenshot("UOM_TestCase1")));
                }
                else
                {
                    test.log(LogStatus.FAIL, "Status is not Awaiting Assessment", test.addBase64ScreenShot(Utils.captureScreenshot("UOM_TestCase1")));
                }
                CommonUtil.Explicitwait_Clickable(unitOfMeasure.Open_Work_details);
                CommonUtil.clickOnElement(unitOfMeasure.Open_Work_details);
                cf.javaScriptClick(cf.waitForElement(pg.Work_Participation_input));
                CommonUtil.typeOnElement(unitOfMeasure.Work_Participation_input, Business_Data[1][8]);
                //Attached file
                Robot robot = new Robot();
                CommonUtil.clickOnElement(unitOfMeasure.Add_Attached);
                Thread.sleep(2000);
                StringSelection str = new StringSelection(Business_Data[1][6]);
                Toolkit.getDefaultToolkit().getSystemClipboard().setContents(str, null);
                Thread.sleep(1000);
                robot.keyPress(KeyEvent.VK_CONTROL);
                robot.keyPress(KeyEvent.VK_V);
                robot.keyRelease(KeyEvent.VK_CONTROL);
                robot.keyRelease(KeyEvent.VK_V);
                robot.keyPress(KeyEvent.VK_ENTER);
                Thread.sleep(1000);
                driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
                CommonUtil.Explicitwait_Clickable(unitOfMeasure.Button_Upload);
                CommonUtil.clickOnElement(unitOfMeasure.Button_Upload);
                Thread.sleep(2000);
                Common_TestCases.Scrolldown();
                CommonUtil.secondsDelay(3);
                CommonUtil.clickOnElement(unitOfMeasure.Attachment_Status);
                String Attached_Status=CommonUtil.Get_Text(unitOfMeasure.Attachment_Status);
                System.out.println(Attached_Status);
                if (Attached_Status.contains("100"))
                {
                    test.log(LogStatus.PASS, "Attachment added Successfully", test.addBase64ScreenShot(Utils.captureScreenshot("UOM_TestCase1")));
                }
                else
                {
                    test.log(LogStatus.FAIL, "Attachment not added Successfully", test.addBase64ScreenShot(Utils.captureScreenshot("UOM_TestCase1")));
                }
                LOG.info("File Uploaded");
                CommonUtil.Explicitwait_Clickable(unitOfMeasure.Open_Financial_loss);
                CommonUtil.clickOnElement(unitOfMeasure.Open_Financial_loss);
                CommonUtil.typeOnElement(unitOfMeasure.Typical_loss_input, Business_Data[1][4]);
                CommonUtil.typeOnElement(unitOfMeasure.Typical_loss_Rational_input, Business_Data[1][9]);
                CommonUtil.typeOnElement(unitOfMeasure.Expected_Annually, Business_Data[1][4]);
                CommonUtil.typeOnElement(unitOfMeasure.Expected_Annually_Rational_input, Business_Data[1][9]);
                //Copy Scenario
                /*CommonUtil.clickOnElement(unitOfMeasure.Copy_Existing);
                CommonUtil.secondsDelay(3);
                CommonUtil.typeOnElement(unitOfMeasure.Search_copy,Category_Data[1][7]+String.valueOf(Keys.ENTER));
                CommonUtil.clickOnElement(unitOfMeasure.Click_Selected);
                CommonUtil.clickOnElement((unitOfMeasure.Done_Copy_button));*/
                CommonUtil.secondsDelay(3);
                CommonUtil.clickOnElement(unitOfMeasure.Open_Control_Environment_Assesssment_Menu);//the WebElement keeps changing
                //CommonUtil.clickOnElement(unitOfMeasure.Open_Control_Environment_Assesssment_Menu);
                CommonUtil.typeOnElement(unitOfMeasure.Environment_Details_input, Business_Data[1][9]);
                CommonUtil.secondsDelay(2);
                //Create a Scenario
                CommonUtil.clickOnElement(unitOfMeasure.Create_Scenario);
                CommonUtil.Explicitwait_Clickable(unitOfMeasure.Scenario_Name);
                CommonUtil.clickOnElement(unitOfMeasure.Scenario_Name);
                CommonUtil.typeOnElement(unitOfMeasure.Scenario_Name,Scenario_Analysis[1][0]);
                CommonUtil.Explicitwait_Clickable(unitOfMeasure.Description1);
                CommonUtil.clickOnElement(unitOfMeasure.Description1);
                CommonUtil.typeOnElement(unitOfMeasure.Description1,Scenario_Analysis[1][3]);                ;
                CommonUtil.Explicitwait_Clickable(unitOfMeasure.Assessment_Type_dropdown);
                CommonUtil.clickOnElement(unitOfMeasure.Assessment_Type_dropdown);
                CommonUtil.Explicitwait_Clickable(unitOfMeasure.Scenario_Analysis);
                CommonUtil.clickOnElement(unitOfMeasure.Scenario_Analysis);
                CommonUtil.clickOnElement(unitOfMeasure.Severity);
                CommonUtil.Explicitwait_Clickable(unitOfMeasure.Severy_1);
                CommonUtil.clickOnElement(unitOfMeasure.Severy_1);
                CommonUtil.clickOnElement(unitOfMeasure.Financial_Impact);
                CommonUtil.typeOnElement(unitOfMeasure.Financial_Impact,Scenario_Analysis[1][4]);
                CommonUtil.clickOnElement(unitOfMeasure.Risk_Drivers);
                CommonUtil.typeOnElement(unitOfMeasure.Risk_Drivers,Scenario_Analysis[1][4]);
                CommonUtil.typeOnElement(unitOfMeasure.Assumptions,Scenario_Analysis[1][2]+String.valueOf(Keys.ENTER));
                CommonUtil.secondsDelay(2);
                CommonUtil.clickOnElement(unitOfMeasure.Save3);
                CommonUtil.secondsDelay(2);
                test.log(LogStatus.PASS, "Create scenario", test.addBase64ScreenShot(Utils.captureScreenshot("UOM_TestCase1")));
                CommonUtil.clickOnElement(unitOfMeasure.Close_Navigator);
                cf.javaScriptClick(cf.waitForElement(pg.Allocation_Save2));
                CommonUtil.secondsDelay(2);
                cf.javaScriptClick(cf.waitForElement(pg.Action_Button));
                CommonUtil.secondsDelay(3);
                cf.javaScriptClick(cf.waitForElement(pg.Start_Assessment));
                CommonUtil.clickOnElement(unitOfMeasure.Continue);
                String UOM_reference=CommonUtil.getText(unitOfMeasure.UOM_reference);
                //CommonUtil.secondsDelay(4);
                Common_TestCases.Scrollup();
                CommonUtil.secondsDelay(4);
                CommonUtil.clickOnElement(unitOfMeasure.Continue);
                String Assessment_Completed_Status=CommonUtil.getText(unitOfMeasure.Status);
                System.out.println(Assessment_Completed_Status);
                LOG.info("Assessment Completed");
                if (Assessment_Completed_Status.equalsIgnoreCase(Business_Data[3][14]))
                {
                    test.log(LogStatus.PASS, "Assessment Completed", test.addBase64ScreenShot(Utils.captureScreenshot("UOM_TestCase1")));
                }
                else
                {
                    test.log(LogStatus.FAIL, "Assessment not Completed", test.addBase64ScreenShot(Utils.captureScreenshot("UOM_TestCase1")));
                }

                cf.javaScriptClick(cf.waitForElement(pg.User_Menu));
                CommonUtil.secondsDelay(2);
                cf.javaScriptClick(cf.waitForElement(pg.Log_Out));
                LOG.info("Capture User logout");
                CommonUtil.secondsDelay(2);
                Common_TestCases.UOM_Login_Approver();
                cf.javaScriptClick(cf.waitForElement(pg.Primary_Menu));
                cf.javaScriptClick(cf.waitForElement(pg.Risk_Measure));
                cf.javaScriptClick(cf.waitForElement(pg.UnitOfMeasure_menu));
                CommonUtil.secondsDelay(3);
                CommonUtil.typeOnElement(unitOfMeasure.Search_Uni_Of_Measure,UOM_reference+String.valueOf(Keys.ENTER));
                CommonUtil.Explicitwait_Clickable(unitOfMeasure.Select_For_Approval);
                CommonUtil.clickOnElement(unitOfMeasure.Select_For_Approval);
                CommonUtil.secondsDelay(3);
                CommonUtil.Explicitwait_Clickable(unitOfMeasure.Action_Button);
                CommonUtil.clickOnElement(unitOfMeasure.Action_Button);
                CommonUtil.Explicitwait_Clickable(unitOfMeasure.Approve);
                CommonUtil.clickOnElement(unitOfMeasure.Approve);
                CommonUtil.clickOnElement(unitOfMeasure.Continue);
                CommonUtil.secondsDelay(2);
                Common_TestCases.Scrollup();
                CommonUtil.secondsDelay(2);
                String Approved_Status_Completed=CommonUtil.getText(unitOfMeasure.Status);
                System.out.println(Approved_Status_Completed);
                LOG.info("Assessment Approved");
                if (Approved_Status_Completed.equalsIgnoreCase(Business_Data[4][14]))
                {
                    test.log(LogStatus.PASS, "Assessment Approved", test.addBase64ScreenShot(Utils.captureScreenshot("UOM_TestCase1")));
                }
                else
                {
                    test.log(LogStatus.FAIL, "Assessment not Approved", test.addBase64ScreenShot(Utils.captureScreenshot("UOM_TestCase1")));
                }

            } catch (Exception e) {
                test.log(LogStatus.FAIL, "TestCase:Failed ", test.addBase64ScreenShot(Utils.captureScreenshot("UOM_TestCase1")));
                LOG.info("step failed " + e.getMessage());
                throw e;
            }
        }


        @AfterClass
        public void afterTest() {
            report.endTest(test);
            report.flush();
            driver.quit();
        }
    }
