package com.os.remoteDesktop;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.io.IOException;

/**
 * @authors : Kenneth Mintah Mensah
 *           Frederick Abayie
 *           Dorothy Sarpong-Kumankoma
 *           George Assan
 *           Samuel Agyeman
 * @version : 1.0.0
 * @date : 29/10/2015
 */

public class MouseClick implements DeviceAction{

    //represents the mask for the mouse button
    // that was clicked
    private final int mouseButtonMask;

    //represents the number of times the mouse button was clicked
    private final int numClicks;


    public MouseClick(MouseEvent event){
        this(event.getModifiers(), event.getClickCount());
    }

    /*
     a constructor that takes a mouse button and the number of
     clicks
     @param: mouseButtonMask represents the button mask of mouse clicked
     @param: numClicks represents the number of clicks associated with
            button mask
     */
    public MouseClick(int mouseButton, int numClicks){
        this.mouseButtonMask = mouseButton;
        this.numClicks = numClicks;
    }

    /*
    method handles mouse clicks
    @param robot performs mouse clicks
     */
    @Override
    public Object performAction(Robot robot) throws IOException {
        for(int i = 0; i < numClicks; i++){
            robot.mousePress(mouseButtonMask);
            robot.mouseRelease(mouseButtonMask);
        }
        return null;
    }
}
