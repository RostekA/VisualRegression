import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.*;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;

public class Regression {
    WebDriver driver;
    ExtentReports report;
    ExtentTest test;

    @BeforeClass
    public void beforeClass()
    {
        report = new ExtentReports(System.getProperty("user.dir") + "\\report\\Report.html");

        try {
            FileUtils.cleanDirectory(new File(System.getProperty("user.dir") + "\\src\\images\\diffImages"));
            FileUtils.cleanDirectory(new File(System.getProperty("user.dir") + "\\src\\images\\screenshots"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir")+"\\src\\drivers\\chromedriver.exe");
        driver = new ChromeDriver();
        driver.get("http://newtours.demoaut.com/");

        driver.findElement(By.name("userName")).sendKeys("tutorial");
        driver.findElement(By.name("password")).sendKeys("tutorial");
        driver.findElement(By.name("login")).click();
    }

    @BeforeMethod
    public void beforeMethod(Method method)
    {
        //test = report.startTest(method.getName());
    }

    @DataProvider(name="urls")
    public static Object[][] urls() {

        return new Object[][]{
                {"http://newtours.demoaut.com/mercurywelcome.php", "homePage"},
                {"http://newtours.demoaut.com/mercuryreservation.php", "reservationPage"},
                {"http://newtours.demoaut.com/mercuryreservation2.php", "reservationPage2"},
                {"http://newtours.demoaut.com/mercurypurchase.php", "homePage"}
        };
    }

    @Test(dataProvider = "urls")
    public void regression(String url, String name, Method method, ITestContext context)
    {
        context.getCurrentXmlTest().addParameter("image", name);
        test = report.startTest(method.getName() + " || " + url);
        driver.get(url);

        new ScreenCaptureUtility().takePageScreenshot(driver, name);
        Assert.assertTrue(new ScreenCaptureUtility().areImagesEqual(name, name));
    }

    @AfterMethod
    public void afterMethod(ITestResult result, ITestContext context)
    {
        String image = context.getCurrentXmlTest().getParameter("image");

        if(result.getStatus()==ITestResult.SUCCESS)
        {
            test.log(LogStatus.PASS, "Test passed");
        }

        if(result.getStatus()==ITestResult.FAILURE)
        {
            String diff = test.addScreenCapture("../src/images/diffImages/" + image + ".png");
            test.log(LogStatus.FAIL, "Test failed", "screenshot: \n" + diff);
            test.log(LogStatus.FAIL, result.getThrowable());
        }

        if(result.getStatus()==ITestResult.SKIP)
        {
            test.log(LogStatus.SKIP, "Test skipped");
        }
    }

    @AfterClass
    public void afterClass()
    {
        driver.quit();

        report.endTest(test);
        report.flush();
    }
}
