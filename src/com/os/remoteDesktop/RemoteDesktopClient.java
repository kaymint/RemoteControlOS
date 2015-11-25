package com.os.remoteDesktop;

import javax.rmi.CORBA.StubDelegate;
import java.awt.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.*;

/**
 * @authors : Kenneth Mintah Mensah
 *           Frederick Abayie
 *           Dorothy Sarpong-Kumankoma
 *           George Assan
 *           Samuel Agyeman
 * @version : 1.0.0
 * @date : 29/10/2015
 */

public class RemoteDesktopClient {
    private final ObjectOutputStream out;
    private final ObjectInputStream in;
    private final Robot robot;

    /*
    setup client
     */
    public RemoteDesktopClient(String server, String clientName) throws IOException, AWTException{
        Socket socket = new Socket(server, ServerConstants.PORT);
        robot = new Robot();
        out = new ObjectOutputStream(socket.getOutputStream());
        in = new ObjectInputStream(socket.getInputStream());
        out.writeObject(clientName);
        out.flush();
    }

    /*
    handles the instructions received from the server
     */
    public void runInstructions() throws ClassNotFoundException{
        try{
            while(true){
                DeviceAction deviceAction = (DeviceAction) in.readObject();
                Object actionResult = deviceAction.performAction(robot);
                if( actionResult != null){
                    out.writeObject(actionResult);
                    out.flush();
                    out.reset();
                }
            }
        }catch (IOException ex){
            System.out.println("Connection to Server lost or closed");
        }
    }

    public static void main(String[] args) throws IOException, AWTException {
        RemoteDesktopClient client = new RemoteDesktopClient(args[0], args[1]);
        try {
            client.runInstructions();
        } catch (ClassNotFoundException e) {
            System.out.println("Class not found");
        }
    }


}
