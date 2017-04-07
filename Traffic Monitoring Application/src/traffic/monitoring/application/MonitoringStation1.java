/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package traffic.monitoring.application;

/**
 *
 * @author Laptop
 */
import javax.swing.*;
import java.net.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.*;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;


/**
 *
 * @author Laptop
 */
public class MonitoringStation1 extends JFrame implements ActionListener, KeyListener
{
private JTextField txtTime, txtLocation, txtLanes, txtTotalVehicles, txtAvgVehicles,txtAvgVelocity;
private JLabel lblMessage, lblTime, lblLocation, lblLanes, lblTotalVehicles, lblAvgVehicles, lblAvgVelocity;
private JButton btnConnect, btnSubmit, btnExit;

private Socket socket = null;
private DataInputStream console = null;
private DataOutputStream streamOut = null;
private ChatClientThread2 client2 = null;
private String serverName = "localhost";
private int serverPort = 4444;
    
SpringLayout springLayout;
    

    public static void main(String[] args)
    {
        MonitoringStation1 MonitoringStation1 = new MonitoringStation1();
        MonitoringStation1.run();
    }

    private void run() 
    {
      setBounds(10,10,300,400);
      setTitle("Monitoring Stn 1");
    addWindowListener(new WindowAdapter()
        {
            @Override
            public void windowClosing(WindowEvent e)
            {
                System.exit(0);
            }
        });
    displayGUI();
    setResizable(false);
    setVisible(true);
    }
    public void displayGUI()
    {
        springLayout = new SpringLayout();
        setLayout(springLayout);
        
        displayTextFields(springLayout);
        displayLabels(springLayout);
        displayButtons(springLayout);
    }
    private void displayTextFields(SpringLayout layout)
    {
        txtTime = TMLibraryComponents.LocateAJTextField(this, this, springLayout, 10, 150, 100);
        txtLocation = TMLibraryComponents.LocateAJTextField(this, this, springLayout, 10, 150, 130);
        txtLanes = TMLibraryComponents.LocateAJTextField(this, this, springLayout, 10, 150, 160);
        txtTotalVehicles = TMLibraryComponents.LocateAJTextField(this, this, springLayout, 10, 150, 190);
        txtAvgVehicles = TMLibraryComponents.LocateAJTextField(this, this, springLayout, 10, 150, 220);
        txtAvgVelocity = TMLibraryComponents.LocateAJTextField(this, this, springLayout, 10, 150, 250);
    }
    private void displayLabels(SpringLayout layout)
    {
        lblTime = TMLibraryComponents.LocateAJLabel(this, layout, "TIme:", 30, 100);
        lblLocation = TMLibraryComponents.LocateAJLabel(this, layout, "Location:", 30, 130);
        lblLanes = TMLibraryComponents.LocateAJLabel(this, layout, "#Lanes:", 30, 160);
        lblTotalVehicles = TMLibraryComponents.LocateAJLabel(this, layout, "Total # Vehicles:", 30, 190);
        lblAvgVehicles = TMLibraryComponents.LocateAJLabel(this, layout, "Avg # Vehicles:", 30, 220);
        lblAvgVelocity = TMLibraryComponents.LocateAJLabel(this, layout, "Avg Velocity:", 30, 250);
        lblMessage = TMLibraryComponents.LocateAJLabel(this,layout, "Message", 30, 50);
    }
    private void displayButtons(SpringLayout layout)
    {
        btnConnect = TMLibraryComponents.LocateAJButton(this, this, springLayout, "Connect", 30, 320, 100, 25);
        btnSubmit = TMLibraryComponents.LocateAJButton(this, this, springLayout, "Submit", 30, 280, 100, 25);
        btnExit = TMLibraryComponents.LocateAJButton(this, this, springLayout, "Exit", 160, 280, 100, 25);
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnSubmit)
        {
            send();
            txtTime.requestFocus();
        }

        if (e.getSource() == btnExit)
        {
            txtLanes.setText(".bye");
            txtTotalVehicles.setText("");
            send();
            System.exit(0);
        }

        if (e.getSource() == btnConnect)
        {
            connect(serverName, serverPort);
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
        
    }

    @Override
    public void keyPressed(KeyEvent e) {
       
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    public void connect(String serverName, int serverPort)
    {
        println("Establishing connection. Please wait ...");
        try
        {
            socket = new Socket(serverName, serverPort);
            println("Connected: " + socket);
            open();
        }
        catch (UnknownHostException uhe)
        {
            println("Host unknown: " + uhe.getMessage());
        }
        catch (IOException ioe)
        {
            println("Unexpected exception: " + ioe.getMessage());
        }
    }

    private void send()
    {
        try
        {
            streamOut.writeUTF(txtTime.getText() + "," + txtLocation.getText() + "," + txtLanes.getText() + "," +
                    txtTotalVehicles.getText() + "," + txtAvgVehicles.getText() + "," + txtAvgVelocity.getText() );
            streamOut.flush();
           // txtLocation.setText("");
        }
        catch (IOException ioe)
        {
            println("Sending error: " + ioe.getMessage());
            close();
        }
    }

    public void handle(String msg)
    {
        if (msg.equals(".bye"))
        {
            println("Good bye. Press EXIT button to exit ...");
            close();
        }
        else
        {
            System.out.println("Handle: " + msg);
            println(msg);
        }
    }

    public void open()
    {
        try
        {
            streamOut = new DataOutputStream(socket.getOutputStream());
            client2 = new ChatClientThread2(this, socket);
        }
        catch (IOException ioe)
        {
            println("Error opening output stream: " + ioe);
        }
    }

    public void close()
    {
        try
        {
            if (streamOut != null)
            {
                streamOut.close();
            }
            if (socket != null)
            {
                socket.close();
            }
        }
        catch (IOException ioe)
        {
            println("Error closing ...");
        }
        client2.close();
        client2.stop();
    }

    void println(String msg)
    {
        //display.appendText(msg + "\n");
          lblMessage.setText(msg);
    }

    public void getParameters()
    {
//        serverName = getParameter("host");
//        serverPort = Integer.parseInt(getParameter("port"));
        
        serverName = "localhost";
        serverPort = 4444;        
    }


}




