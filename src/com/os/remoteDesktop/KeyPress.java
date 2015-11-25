package com.os.remoteDesktop;

import java.awt.*;
import java.awt.event.KeyEvent;
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

public class KeyPress implements DeviceAction{

    //key code for key pressed
    private int keyCode;

    /*
    constructor that takes KeyEvent as parameters
     */
    public KeyPress(KeyEvent event){
        this(event.getKeyCode());
    }

    /*
    contructor that takes key code of key pressed
     */
    public KeyPress(int keyCode){
        this.keyCode = keyCode;
    }

    /*
    a method to handle
    actions performed when a key is pressed
     */
    @Override
    public Object performAction(Robot robot) throws IOException {
        robot.keyPress(keyCode);
        robot.keyRelease(keyCode);
        return null;
    }

}
