package com.os.remoteDesktop;

import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;
import java.awt.*;
import java.io.ByteArrayOutputStream;
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
public class ScreenShotCapture implements DeviceAction {

    Toolkit defaultToolkit;
    Rectangle screenShotArea;
    ByteArrayOutputStream bout;
    JPEGImageEncoder imageEncoder;

    /*
    captures screenshots and encodes the images as bytes
     */
    @Override
    public Object performAction(Robot robot) throws IOException {

        defaultToolkit = Toolkit.getDefaultToolkit();
        screenShotArea = new Rectangle(defaultToolkit.getScreenSize());
        bout = new ByteArrayOutputStream();
        imageEncoder = JPEGCodec.createJPEGEncoder(bout);
        imageEncoder.encode(robot.createScreenCapture(screenShotArea));

        return bout.toByteArray();
    }

    @Override
    public String toString(){
        return "Screenshot Captured and Encoded";
    }


}
