package traffic.monitoring.application;
//Source:
//  Creating a simple Chat Client1/Server Solution 
//  http://pirate.shu.edu/~wachsmut/Teaching/CSAS2214/Virtual/Lectures/chat-client-server.html


import java.net.*;
import java.io.*;

public class ChatClientThread1 extends Thread
{

    private Socket socket = null;
    private TrafficMonitoringApplication TMApp = null;
    private DataInputStream streamIn = null;

    public ChatClientThread1(TrafficMonitoringApplication _TMApp, Socket _socket)
    {
        TMApp = _TMApp;
        socket = _socket;
        open();
        start();
    }

    public void open()
    {
        try
        {
            streamIn = new DataInputStream(socket.getInputStream());
        }
        catch (IOException ioe)
        {
            System.out.println("Error getting input stream: " + ioe);
            //client1.stop();
            TMApp.close();
        }
    }

    public void close()
    {
        try
        {
            if (streamIn != null)
            {
                streamIn.close();
            }
        }
        catch (IOException ioe)
        {
            System.out.println("Error closing input stream: " + ioe);
        }
    }

    public void run()
    {
        while (true)
        {
            try
            {
                TMApp.handle(streamIn.readUTF());
            }
            catch (IOException ioe)
            {
                System.out.println("Listening error: " + ioe.getMessage());
                //client1.stop();
                TMApp.close();
            }
        }
    }
}
