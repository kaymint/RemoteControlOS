package com.os.remoteDesktop;

import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageDecoder;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/*/**
 * @authors : Kenneth Mintah Mensah
 *            Frederick Abayie
 *            Dorothy Sarpong-Kumankoma
 *            George Assan
 *            Samuel Agyeman
 * @version : 1.0.0
 * @date : 29/10/2015
 */
public class ServerUI extends JFrame {

    private final ObjectInputStream in;
    private final ObjectOutputStream out;
    private final String clientName;
    private final JLabel iconLabel = new JLabel();
    private final BlockingQueue queue = new LinkedBlockingQueue();
    private final ActionReader reader;
    private final ActionWriter writer;
    private boolean isRunning = true;
    private Timer timer = new Timer(ServerConstants.SCREEN_SHOT_INTERVAL, new ScreenShotTimer());

    public ServerUI(Socket socket) throws IOException, ClassNotFoundException{
        out = new ObjectOutputStream(socket.getOutputStream());
        in = new ObjectInputStream(socket.getInputStream());
        clientName = (String) in.readObject();
        setUpUI();
        reader = new ActionReader(in, this);
        reader.start();
        timer.start();
        writer = new ActionWriter(queue, out);
        writer.start();
        addWindowListener(new WindowClosingListener());
    }

    public void showImage(byte[] byteImage ) throws IOException{
        ByteArrayInputStream bin = new ByteArrayInputStream(byteImage);
        JPEGImageDecoder decoder = JPEGCodec.createJPEGDecoder(bin);
        final BufferedImage img = decoder.decodeAsBufferedImage();
        SwingUtilities.invokeLater(new Runnable(){
            public void run(){
                iconLabel.setIcon(new ImageIcon(img));
            }
        });
    }

    public void setUpUI(){
        setTitle(clientName + "Connected");
        getContentPane().add(new JScrollPane(iconLabel));
        iconLabel.addMouseListener( new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                if(isRunning){
                    try {
                        queue.put(new MouseMovement(e));
                        queue.put(new MouseClick(e));
                        queue.put(new ScreenShotCapture());
                    } catch (InterruptedException e1) {
                        e1.printStackTrace();
                    }
                }else {
                    Toolkit.getDefaultToolkit().beep();
                }
            }
        });
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                super.keyPressed(e);
                if(isRunning){
                    try {
                        queue.put(new KeyPress(e));
                        queue.put(new ScreenShotCapture());
                    } catch (InterruptedException e1) {
                        e1.printStackTrace();
                    }
                }else{
                    Toolkit.getDefaultToolkit().beep();
                }
            }
        });
        setSize(ServerConstants.SERVER_WINDOW_WIDTH,ServerConstants.SERVER_WINDOW_HEIGHT);
        setVisible(true);
    }

    class ScreenShotTimer implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                queue.put(new ScreenShotCapture());
                //System.out.println("Captured");
            } catch (InterruptedException e1) {
                e1.printStackTrace();
            }
        }
    }

    class WindowClosingListener implements WindowListener{

        @Override
        public void windowOpened(WindowEvent e) {

        }

        @Override
        public void windowClosing(WindowEvent e) {
            timer.stop();

            try {
                out.close();
            } catch (IOException e1) {

            }

            try {
                in.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }

        @Override
        public void windowClosed(WindowEvent e) {

        }

        @Override
        public void windowIconified(WindowEvent e) {

        }

        @Override
        public void windowDeiconified(WindowEvent e) {

        }

        @Override
        public void windowActivated(WindowEvent e) {

        }

        @Override
        public void windowDeactivated(WindowEvent e) {

        }
    }

    public static void main(String[] args) throws Exception{
        ServerSocket ss = new ServerSocket(ServerConstants.PORT);
        while(true){
            Socket socket = ss.accept();
            System.out.println("Connection From " + socket);
            new ServerUI(socket);
        }
    }

}
