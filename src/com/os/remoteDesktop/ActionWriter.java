package com.os.remoteDesktop;

import java.io.ObjectOutputStream;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.BlockingQueue;

/**
 * Created by StreetHustling on 11/2/15.
 */
public class ActionWriter extends Thread {
    BlockingQueue queue;
    ObjectOutputStream out;

    public ActionWriter(BlockingQueue queue, ObjectOutputStream out){
        this.queue = queue;
        this.out = out;
    }

    @Override
    public void run() {
        try{
            while(true){
                DeviceAction action = (DeviceAction) queue.take();
                out.writeObject(action);
                out.flush();
            }
        }catch (Exception ex){
            System.out.println("Connection to client closed");
        }
    }
}
