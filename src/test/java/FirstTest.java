import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;


public class FirstTest {
WebDriver driver;

@DataProvider(name="urls")
public static Object[][] urls() {

    return new Object[][]{
            {"http://newtours.demoaut.com/mercurywelcome.php", "homePageImpr"},
            {"http://newtours.demoaut.com/mercuryreservation.php", "reservationPageImpr"},
            {"http://newtours.demoaut.com/mercuryreservation2.php", "reservationPage2Impr"},
            {"http://newtours.demoaut.com/mercurypurchase.php", "purchasePageImpr"}
    };
}

@Test(dataProvider = "urls")
public void prepareBaseline(String url, String name)
{
    System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir")+"\\src\\drivers\\chromedriver.exe");
    driver = new ChromeDriver();
    driver.get("http://newtours.demoaut.com/");

    driver.findElement(By.name("userName")).sendKeys("tutorial");
    driver.findElement(By.name("password")).sendKeys("tutorial");
    driver.findElement(By.name("login")).click();

    driver.get(url);

    new ScreenCaptureUtility().prepareBaseline(driver, name);

}

@Test
public void compareImages()
{
    System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir")+"\\src\\drivers\\chromedriver.exe");
    driver = new ChromeDriver();
    driver.get("http://newtours.demoaut.com/");

    new ScreenCaptureUtility().takePageScreenshot(driver, "scrHomePage");

    Assert.assertTrue(new ScreenCaptureUtility().areImagesEqual("homePage", "scrHomePage"));
    driver.quit();
}


    @Test
    public void compareImagesToFail()
    {
        System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir")+"\\src\\drivers\\chromedriver.exe");
        driver = new ChromeDriver();
        driver.get("http://newtours.demoaut.com/");

        driver.findElement(By.name("userName")).sendKeys("tutorial");

        new ScreenCaptureUtility().takePageScreenshot(driver, "scrHomePage");

        Assert.assertTrue(new ScreenCaptureUtility().areImagesEqual("homePage", "scrHomePage"));
        driver.quit();
    }

@Test
public void test()
{
    System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir")+"\\src\\drivers\\chromedriver.exe");
    driver = new ChromeDriver();
    driver.get("http://newtours.demoaut.com/");

    new ScreenCaptureUtility().takePageScreenshot(driver, "myImage1");
}

@Test
public void test2(ITestContext ctx)
{
    System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir")+"\\src\\drivers\\chromedriver.exe");
    driver = new ChromeDriver();
    driver.get("http://newtours.demoaut.com/");

    WebElement logo = driver.findElement(By.xpath("//img[@alt='Mercury Tours']"));

    new ScreenCaptureUtility().takeElementScreenshot(driver, "logoImage", logo);
    ctx.getCurrentXmlTest().addParameter("hello", "hello again");
}



}
