package traffic.monitoring.application;
//Source:
//  Creating a simple Chat Client/Server Solution 
//  http://pirate.shu.edu/~wachsmut/Teaching/CSAS2214/Virtual/Lectures/chat-client-server.html


import java.net.*;
import java.io.*;

public class ChatClientThread2 extends Thread
{

    private Socket socket = null;
    private MonitoringStation1 MS1 = null;
    private DataInputStream streamIn = null;

    public ChatClientThread2(MonitoringStation1 _MS1, Socket _socket)
    {
        MS1 = _MS1;
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
            //client2.stop();
            MS1.close();
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
                MS1.handle(streamIn.readUTF());
            }
            catch (IOException ioe)
            {
                System.out.println("Listening error: " + ioe.getMessage());
                //client2.stop();
                MS1.close();
            }
        }
    }
}
