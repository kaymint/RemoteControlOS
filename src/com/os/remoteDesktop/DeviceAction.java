package com.os.remoteDesktop;

import java.io.IOException;
import java.io.Serializable;
import java.awt.Robot;

/**
 * @authors : Kenneth Mintah Mensah
 *           Frederick Abayie
 *           Dorothy Sarpong-Kumankoma
 *           George Assan
 *           Samuel Agyeman
 * @version : 1.0.0
 * @date : 29/10/2015
 */

public interface DeviceAction extends Serializable{

    /*
    a method header for implementing
    keyboard and mouse operations
     */
    public Object performAction(Robot robot) throws IOException;
}
