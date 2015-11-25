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

public class MouseMovement implements DeviceAction{

    //represents the x coordinates of mouse position
    private final int xCoord;

    //represents the y coordinates of mouse position
    private final int yCoord;

    /*
    a constructor that takes in a mouse event
     */
    public MouseMovement(MouseEvent event){
        this(event.getPoint());
    }

    /*
    a constructor that takes in a point and get the
    mouse coordinates
    @param Point
     */
    public MouseMovement(Point point){
        this.xCoord = (int) point.getX();
        this.yCoord = (int) point.getY();
    }

    /*
    method handles mouse position
     */
    @Override
    public Object performAction(Robot robot) throws IOException {
        robot.mouseMove(xCoord,yCoord);
        return null;
    }
}
