import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import ru.yandex.qatools.ashot.AShot;
import ru.yandex.qatools.ashot.Screenshot;
import ru.yandex.qatools.ashot.comparison.ImageDiff;
import ru.yandex.qatools.ashot.comparison.ImageDiffer;
import ru.yandex.qatools.ashot.coordinates.WebDriverCoordsProvider;
import ru.yandex.qatools.ashot.shooting.ShootingStrategies;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;


public class ScreenCaptureUtility {

    public boolean areImagesEqual(String baseline, String screenshot)
    {
        BufferedImage imgBaseline = null;
        BufferedImage imgScreenshot = null;

        try {
            imgBaseline = ImageIO.read(new File(System.getProperty("user.dir") + "\\src\\images\\baseline\\" + baseline + ".png"));
            imgScreenshot = ImageIO.read(new File(System.getProperty("user.dir") + "\\src\\images\\screenshots\\" + screenshot + ".png"));
        } catch (IOException e) { }

        ImageDiff diff = new ImageDiffer().makeDiff(imgBaseline, imgScreenshot);
        boolean isDifferent = diff.hasDiff();

        if(isDifferent)
        {
            BufferedImage diffImage = diff.getMarkedImage();

            try {
                ImageIO.write(diffImage,
                        "png",
                        new File(System.getProperty("user.dir") + "\\src\\images\\diffImages\\" + baseline + ".png"));
            } catch (IOException e) { }
        }

        return !isDifferent;
    }



    public void takePageScreenshot(WebDriver driver, String name)
    {
        Screenshot screen = new AShot().takeScreenshot(driver);
        BufferedImage bi = screen.getImage();

        File file = new File(System.getProperty("user.dir")+"\\src\\images\\screenshots\\" + name + ".png");

        try {
            ImageIO.write(bi, "png", file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void takePageScreenshotImproved(WebDriver driver, String name)
    {
        Screenshot screen = new AShot().shootingStrategy(ShootingStrategies.viewportPasting(100)).takeScreenshot(driver);
        BufferedImage bi = screen.getImage();

        File file = new File(System.getProperty("user.dir")+"\\src\\images\\screenshots\\" + name + ".png");

        try {
            ImageIO.write(bi, "png", file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void prepareBaseline(WebDriver driver, String name)
    {
        Screenshot screen = new AShot().shootingStrategy(ShootingStrategies.viewportPasting(100)).takeScreenshot(driver);
        BufferedImage bi = screen.getImage();

        File file = new File(System.getProperty("user.dir")+"\\src\\images\\baseline\\" + name + ".png");

        try {
            ImageIO.write(bi, "png", file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void takeElementScreenshot(WebDriver driver, String name, WebElement element)
    {
        Screenshot screen = new AShot().coordsProvider(new WebDriverCoordsProvider()).takeScreenshot(driver, element);

        BufferedImage bi = screen.getImage();

        File file = new File(System.getProperty("user.dir")+"\\src\\images\\screenshots\\" + name + ".png");

        try {
            ImageIO.write(bi, "png", file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
