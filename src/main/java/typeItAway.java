import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;

import com.asprise.ocr.Ocr;

import javax.imageio.ImageIO;

public class typeItAway {


    /**
     * Golden Rule. KISS
     *
     * Move mouse pointer to the right pixel
     *      -Left Click
     *
     * Read the Screen
     *      - ScreenShot??? Replace with live reading
     *      - Get chars from OCR
     *
     *  Use Robot to type
     *      - Use random
     *
     **/

    /***
     * Global
     ***/

    static int TYPE_AREA_COORDINATE_X = 200;
    static int TYPE_AREA_COORDINATE_Y = 390;

    static int READ_AREA_COORDINATE_X = 75;
    static int READ_AREA_COORDINATE_Y = 250;
    static int READ_AREA_COORDINATE_WIDTH = 725;
    static int READ_AREA_COORDINATE_HEIGHT = 95;

    static int KEY_PRESS_FAKE_DELAY_RANDOM_MIN = 30;
    static int KEY_PRESS_FAKE_DELAY_RANDOM_MAX = 75;


    Robot robot;
    Ocr ocr;


    public typeItAway()
    {
        try {
            this.robot = new Robot();
        } catch (AWTException e) {
            e.printStackTrace();
        }
        Ocr.setUp();
        ocr = new Ocr();
        ocr.startEngine("eng", Ocr.SPEED_FASTEST);
    }

    public static int getKeyPressFakeDelayRandomMax() {
        return KEY_PRESS_FAKE_DELAY_RANDOM_MAX;
    }

    public void doIt()
    {
        mouseAction(this.robot);
        loopImageAndType();
        this.ocr.stopEngine();
    }

    private void loopImageAndType() {
        long start = System.currentTimeMillis();
        long end = start + 58*1000;
        while(System.currentTimeMillis() < end)
        {
            BufferedImage image = screenShot(this.robot);
            String text = ocr.recognize(image, Ocr.RECOGNIZE_TYPE_TEXT, Ocr.OUTPUT_FORMAT_PLAINTEXT);
            System.out.println(text);
            keystrokes(this.robot, text.replaceAll("\\r\\n|\\r|\\n", " "));
        }
    }

    private void keystrokes(Robot lRobot, String text) {
        for (char c : text.toCharArray()) {
            int keyCode = KeyEvent.getExtendedKeyCodeForChar(c);
            if (KeyEvent.CHAR_UNDEFINED != keyCode) {
                //Fake wait to read the text appeared
                lRobot.delay((new Random()
                        .nextInt(
                                (KEY_PRESS_FAKE_DELAY_RANDOM_MAX -
                                        KEY_PRESS_FAKE_DELAY_RANDOM_MIN)
                                        + 1)
                        + KEY_PRESS_FAKE_DELAY_RANDOM_MIN));
                lRobot.keyPress(keyCode);
                lRobot.delay((new Random()
                        .nextInt(
                                (KEY_PRESS_FAKE_DELAY_RANDOM_MAX -
                                        KEY_PRESS_FAKE_DELAY_RANDOM_MIN)
                                        + 1)
                        + KEY_PRESS_FAKE_DELAY_RANDOM_MIN));
                lRobot.keyRelease(keyCode);

            }
            else {
                lRobot.keyPress(39);
                lRobot.delay((new Random()
                        .nextInt(
                                (KEY_PRESS_FAKE_DELAY_RANDOM_MAX -
                                        KEY_PRESS_FAKE_DELAY_RANDOM_MIN)
                                        + 1)
                        + KEY_PRESS_FAKE_DELAY_RANDOM_MIN));
                lRobot.keyRelease((new Random()
                        .nextInt(
                                (KEY_PRESS_FAKE_DELAY_RANDOM_MAX -
                                        KEY_PRESS_FAKE_DELAY_RANDOM_MIN)
                                        + 1)
                        + KEY_PRESS_FAKE_DELAY_RANDOM_MIN));
            }
            lRobot.delay((new Random()
                    .nextInt(
                            (KEY_PRESS_FAKE_DELAY_RANDOM_MAX -
                                    KEY_PRESS_FAKE_DELAY_RANDOM_MIN)
                                    + 1)
                    + KEY_PRESS_FAKE_DELAY_RANDOM_MIN));

        }
    }

    public BufferedImage screenShot(Robot lRobot)
    {
        Rectangle rectangle = new Rectangle(READ_AREA_COORDINATE_X,
                READ_AREA_COORDINATE_Y,
                READ_AREA_COORDINATE_WIDTH,
                READ_AREA_COORDINATE_HEIGHT);

        //Testing if image is created with the right area

        try {
            ImageIO.write(lRobot.createScreenCapture(rectangle), "bmp",
                    new File("C:\\Users\\Gokul\\IdeaProjects\\screenTypeFingersHack\\src\\main\\resources\\test1.bmp"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Testing end

        return lRobot.createScreenCapture(rectangle);
    }

    public void mouseAction(Robot lRobot)
    {
        lRobot.mouseMove(TYPE_AREA_COORDINATE_X,TYPE_AREA_COORDINATE_Y);
        lRobot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
        lRobot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
    }

    public static void main(String[] args)
    {
        typeItAway fakeIt = new typeItAway();
        fakeIt.doIt();
    }



}
