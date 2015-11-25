package com.os.remoteDesktop;

import java.io.ObjectInputStream;
import java.util.concurrent.BlockingQueue;

/**
 * Created by StreetHustling on 11/2/15.
 */
public class ActionReader extends Thread {
    private ObjectInputStream in;
    private ServerUI serverUI;

    public ActionReader(ObjectInputStream in, ServerUI serverUI){
        this.in = in;
        this.serverUI = serverUI;
    }

    @Override
    public void run() {
        while (true){
            try{
                byte[] image = (byte[]) in.readObject();
                System.out.println();
                serverUI.showImage(image);
            }catch (Exception ex){

            }
        }
    }


}
