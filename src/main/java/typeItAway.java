import java.awt.*;
import java.awt.event.InputEvent;

public class typeItAway {


    /**
     * Golden Rule. KISS
     *
     * Move mouse pointer to the right pixel
     *      -Left Click
     *
     * Read the Screen
     *      - Get chars from OCR
     *
     *  Use Robot to type
     *      - Use random
     *
     **/

    /***
     * Global
     ***/

    int TYPE_AREA_COORDINATE_X = 200;
    int TYPE_AREA_COORDINATE_Y = 390;

    Robot robot;


    public typeItAway()
    {
        try {
            this.robot = new Robot();
        } catch (AWTException e) {
            e.printStackTrace();
        }
    }

    public void doIt()
    {
        mouseAction(this.robot);
    }

    public void mouseAction(Robot lRobot)
    {
        lRobot.mouseMove(TYPE_AREA_COORDINATE_X,TYPE_AREA_COORDINATE_Y);
        lRobot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
        lRobot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
    }

    public static void main(String args[])
    {
        typeItAway fakeIt = new typeItAway();
        fakeIt.doIt();
    }



}
