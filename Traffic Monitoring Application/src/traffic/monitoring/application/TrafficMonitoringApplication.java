/**
 * -------------------------------------------------------- 
 *
 * @author Kayne Ward
 *
 * Developed: 2017
 *
 * ----------------------------------------------------------
 */
package traffic.monitoring.application;

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

import java.lang.Integer;
import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.table.AbstractTableModel;

public class TrafficMonitoringApplication extends JFrame implements ActionListener, KeyListener
{
    
    private Socket socket = null;
    private DataInputStream console = null;
    private DataOutputStream streamOut = null;
    private ChatClientThread1 client = null;
    private String serverName = "localhost";
    private int serverPort = 4444;
    
    private JTextField txtPreOrder, txtInOrder, txtPostOrder;
    private JButton btnConnect, btnBinDisplay, btnLocation, btnVehicle, btnVelocity, btnPreDisplay, btnPreSave, btnInDisplay, btnInSave, btnPostDisplay, btnPostSave, btnExit;
    private JLabel lblMessage, lblPreOrder, lblInOrder, lblPostOrder, lblBinTree, lblLinkList, lblBoader, lblSort, lblNewDataLab, lblBoarder;
    private JTextArea txtaBinTree, txtaLinkList, txtaTMData, txtaNewData;
    
    static int numberOfAssociatedWords = 50;
    static int currentAssocWord = 0;
    static TrafficData wordList[] = new TrafficData[numberOfAssociatedWords];

    MyModel TrafficModel;
    JTable TrafficTable;
    SpringLayout springLayout;
    ArrayList<Object[]> dataValues;
    DList myDList;
    BTree myBTree;
    HashMap<Integer, String> hm ;
    /*
    time, monitor, av vehicles, av speed
    monitor + "-" + time, av vehicles , av speed
    
    monitor
    */
    
    public static void main(String[] args)
    {
        TrafficMonitoringApplication trafficMonitoringApplication = new TrafficMonitoringApplication();
        trafficMonitoringApplication.run();

    }

    private void run()
    {
        setBounds(10, 10, 1000, 650);
        setTitle("TrafficMonitoringApplication");
        displayGUI();
        // = new DList();
        addWindowListener(new WindowAdapter()
               
        {
            @Override
            public void windowClosing(WindowEvent e)
            {
                System.exit(0);
            }
//            public void windowOpened( WindowEvent e)
//            { 
//                 txtaNewData.requestFocusInWindow();
//             } 

        });
         

        
        setResizable(false);
        setVisible(true);
    }

    //<editor-fold defaultstate="collapsed" desc="Display GUI">    
    private void displayGUI()
    {
        springLayout = new SpringLayout();
        setLayout(springLayout);

        displayTextFields(springLayout);
        displayButtons(springLayout);
        displayLabels(springLayout);
        displayTextAreas(springLayout);
        setTitle("Sample JTable");
        setBackground(Color.yellow);

     
       TrafficTable = new JTable();
       displayTrafficTable(springLayout);
       createBTree();
       createDDL();
       getParameters(); 
       //myDList.displayInTextArea(txtaLinkList);
    }
    
    public void createDDL()
    {
        myDList = new DList(); 
        myDList.head.append(new Node("6:00 AM, 1, 10, 2, 5, 2 _"));
        myDList.head.append(new Node("7:00 AM, 1, 10, 2, 5, 2 _"));
        myDList.head.append(new Node("9:00 AM, 1, 10, 2, 5, 2 _"));
        myDList.head.append(new Node("10:00 AM, 1, 10, 2, 5, 2 _"));
       myDList.displayInTextArea(txtaLinkList);
    }
    
    public void createBTree()
    {
        myBTree = new BTree();
        // for every entry in dataValues
        //  myBTree.addBTNode(dataValues.get(i).gettotalnumberofvehivles, dataValues.get(i).getTime + dataValues.get(i).getLocation);
        for (int i = 0; i < dataValues.size(); i++)
        {
            myBTree.addBTNode(Integer.parseInt(dataValues.get(i)[2].toString()), dataValues.get(i)[0].toString() + "_" +  dataValues.get(i)[1].toString());
        }
       
       // myBTree.inOrderTraverseTree(myBTree.root, txtaBinTree);
    }
    
    
public void displayTrafficTable(SpringLayout myPanelLayout)
    {
        // Create a panel to hold all other components
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BorderLayout());
        add(topPanel);

        // Create column names
        String columnNames[] =
        {
            "Time", "Location", "Av.Vehicle#", "Av.Velocity"
        };

        // Create some data
        dataValues = new ArrayList();
        dataValues.add(new Object[]
        {
            "6:00:00 AM", "1","09","70"
        });
        dataValues.add(new Object[]
        {
            "6:00:00 AM", "2","08","80"
        });
        dataValues.add(new Object[]
        {
            "7:00:00 AM", "1","10","60"
        });
        dataValues.add(new Object[]
        {
            "8:00:00 AM", "2","10","60"
        });
 
// dataValues.get(1)[2].toString()
        // constructor of JTable model
        TrafficModel = new MyModel(dataValues, columnNames);

        // Create a new table instance
        TrafficTable = new JTable(TrafficModel);

        // Configure some of JTable's paramters
        TrafficTable.isForegroundSet();
        TrafficTable.setShowHorizontalLines(false);
        TrafficTable.setRowSelectionAllowed(true);
        TrafficTable.setColumnSelectionAllowed(true);
        add(TrafficTable);

        // Change the text and background colours
        TrafficTable.setSelectionForeground(Color.blue);
        TrafficTable.setSelectionBackground(Color.red);

        // Add the table to a scrolling pane, size and locate
        JScrollPane scrollPane = TrafficTable.createScrollPaneForTable(TrafficTable);
        topPanel.add(scrollPane, BorderLayout.CENTER);
        topPanel.setPreferredSize(new Dimension(425, 200));
        myPanelLayout.putConstraint(SpringLayout.WEST, topPanel, 50, SpringLayout.WEST, this);
        myPanelLayout.putConstraint(SpringLayout.NORTH, topPanel, 50, SpringLayout.NORTH, this);
        //bubbleSort(dataValues);
    }
    

    private void displayTextAreas(SpringLayout layout)
    {
        txtaBinTree = TMLibraryComponents.LocateAJTextArea(this, layout, txtaBinTree, 10, 450, 5, 88);
        txtaBinTree.setBackground(Color.white);
        txtaBinTree.setEditable(false);

        txtaLinkList = TMLibraryComponents.LocateAJTextArea(this, layout, txtaLinkList, 10, 325, 5, 88);
        txtaLinkList.setBackground(Color.white);
        txtaLinkList.setEditable(false);

        txtaTMData = TMLibraryComponents.LocateAJTextArea(this, layout, txtaTMData, 10, 40, 15, 45);
        txtaTMData.setVisible(false);
        txtaTMData.setEditable(false);
        
        txtaNewData = TMLibraryComponents.LocateAJTextArea(this, layout, txtaNewData, 600, 80, 10, 30);
        txtaNewData.setText("hello");
        
        //txtaNewData.setVisible(true);
       // txtaNewData.setBackground(Color.white);
    }
   
    private void displayLabels(SpringLayout layout)
    {
        lblBinTree = TMLibraryComponents.LocateAJLabel(this, layout, "Binary Tree:", 10, 425);
        lblSort = TMLibraryComponents.LocateAJLabel(this, layout, "Sort:", 110, 255);
        lblLinkList = TMLibraryComponents.LocateAJLabel(this, layout, "Linked List:", 10, 300);
        lblNewDataLab = TMLibraryComponents.LocateAJLabel(this, layout, "New data recieved from:", 600, 50);
        lblBoarder = TMLibraryComponents.LocateAJLabel(this, layout, "", 590, 50);
        lblBoarder.setBackground(Color.pink);
        lblBoarder.setOpaque(true);
        lblBoarder.setPreferredSize(new Dimension (350,225));
        lblMessage = TMLibraryComponents.LocateAJLabel(this, layout, "Test", 10, 10);    
        //lblPreOrder = TMLibraryComponents.LocateAJLabel(this, layout, "Pre-Order", 50, 550);
        // lblInOrder = TMLibraryComponents.LocateAJLabel(this, layout, "In-Order", 500, 550);
        //  lblPostOrder = TMLibraryComponents.LocateAJLabel(this, layout, "Post-Order", 875, 550);
    }
    private void displayTextFields(SpringLayout layout)
    {
        txtPreOrder = TMLibraryComponents.LocateAJTextField(this, this, layout, 10, 30, 550);
        txtPreOrder.setText("Pre-Order");
        txtPreOrder.setBackground(Color.green);
        txtPreOrder.setHorizontalAlignment(JTextField.CENTER);
        txtPreOrder.setEditable(false);

        txtInOrder = TMLibraryComponents.LocateAJTextField(this, this, layout, 10, 460, 550);
        txtInOrder.setText("In-Order");
        txtInOrder.setBackground(Color.green);
        txtInOrder.setHorizontalAlignment(JTextField.CENTER);
        txtInOrder.setEditable(false);

        txtPostOrder = TMLibraryComponents.LocateAJTextField(this, this, layout, 10, 850, 550);
        txtPostOrder.setText("Post-Order");
        txtPostOrder.setBackground(Color.green);
        txtPostOrder.setHorizontalAlignment(JTextField.CENTER);
        txtPostOrder.setEditable(false);

    }

    private void displayButtons(SpringLayout layout)
    {
        btnBinDisplay = TMLibraryComponents.LocateAJButton(this, this, layout, "Display", 850, 420, 75, 25);
        btnExit = TMLibraryComponents.LocateAJButton(this, this, layout, "Exit", 700, 250, 150, 25);
        btnPreDisplay = TMLibraryComponents.LocateAJButton(this, this, layout, "Display", 10, 575, 75, 25);
        btnPreSave = TMLibraryComponents.LocateAJButton(this, this, layout, "Save", 90, 575, 75, 25);
        btnInDisplay = TMLibraryComponents.LocateAJButton(this, this, layout, "Display", 440, 575, 75, 25);
        btnInSave = TMLibraryComponents.LocateAJButton(this, this, layout, "Save", 520, 575, 75, 25);
        btnPostDisplay = TMLibraryComponents.LocateAJButton(this, this, layout, "Display", 825, 575, 75, 25);
        btnPostSave = TMLibraryComponents.LocateAJButton(this, this, layout, "Save", 905, 575, 75, 25);
        btnLocation = TMLibraryComponents.LocateAJButton(this, this, springLayout, "Location", 150, 250, 100, 25);
        btnVehicle = TMLibraryComponents.LocateAJButton(this, this, springLayout, "Vehicle#", 250, 250, 100, 25);
        btnVelocity = TMLibraryComponents.LocateAJButton(this, this, springLayout, "Velocity", 350, 250, 100, 25);
        btnConnect = TMLibraryComponents.LocateAJButton(this, this, springLayout, "Connect", 450, 250, 100, 25);
    }

    

    //---------------------------------------------------------------------------------------------------
    // Source: http://www.dreamincode.net/forums/topic/231112-from-basic-jtable-to-a-jtable-with-a-tablemodel/
    // class that extends the AbstractTableModel
    //---------------------------------------------------------------------------------------------------
    class MyModel extends AbstractTableModel
    {

        ArrayList<Object[]> al;

        // the headers
        String[] header;

        // constructor 
        MyModel(ArrayList<Object[]> obj, String[] header)
        {
            // save the header
            this.header = header;
            // and the data
            al = obj;
        }

        // method that needs to be overload. The row count is the size of the ArrayList
        public int getRowCount()
        {
            return al.size();
        }

        // method that needs to be overload. The column count is the size of our header
        public int getColumnCount()
        {
            return header.length;
        }

        // method that needs to be overload. The object is in the arrayList at rowIndex
        public Object getValueAt(int rowIndex, int columnIndex)
        {
            return al.get(rowIndex)[columnIndex];
        }

        // a method to return the column name 
        public String getColumnName(int index)
        {
            return header[index];
        }

        // a method to add a new line to the table
        void add(String word1, String word2, String word3, String word4)
        {
            // make it an array[2] as this is the way it is stored in the ArrayList
            // (not best design but we want simplicity)
            String[] str = new String[4];
            str[0] = word1;
            str[1] = word2;
            str[2] = word3;
            str[3] = word4;
            al.add(str);
            // inform the GUI that I have change
            fireTableDataChanged();
        }
    }

    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="Action and Key Listeners">    
    @Override
    public void actionPerformed(ActionEvent e)
  //   btnBinDisplay, btnLocation, btnVehicle, btnVelocity, btnPreDisplay, btnPreSave, btnInDisplay, btnInSave, btnPostDisplay, btnPostSave, btnExit
    {
//        setupTable(springLayout);
        if (e.getSource() == btnConnect)
        {    
            connect(serverName, serverPort);
       
              TrafficTable.repaint();
              TrafficModel.fireTableDataChanged();
        }
      
        if (e.getSource() == btnBinDisplay)
        {
            myDList.displayInTextArea(txtaLinkList);
        }

        if (e.getSource() == btnLocation)
        {
            bubbleSort(dataValues);
            TrafficTable.repaint();
            
        }
        if (e.getSource() == btnVehicle)
        {
            insertionSort(dataValues);
            TrafficTable.repaint();
        }
       if (e.getSource() == btnVelocity)
        {
            selectionSort(dataValues);
            TrafficTable.repaint();
        }
       if (e.getSource() == btnPreDisplay)
       {
           txtaBinTree.setText(" ");
           myBTree.preorderTraverseTree(myBTree.root, txtaBinTree);
       }
       
        if (e.getSource() == btnPreSave)
       {
           
       }
        if (e.getSource() == btnInDisplay)
       {
           txtaBinTree.setText("");
           myBTree.inOrderTraverseTree(myBTree.root, txtaBinTree);
       }
        if (e.getSource() == btnInSave)
       {
           
       }
        if (e.getSource() == btnPostDisplay)
       {
           txtaBinTree.setText("");
           myBTree.postOrderTraverseTree(myBTree.root, txtaBinTree);
      }
        if (e.getSource() == btnPostSave)
       {
           saveHashMap();
       }
        if (e.getSource() == btnExit)
       {
           System.exit(0);
       }
    }

    @Override
    public void keyTyped(KeyEvent e)
    {
    }

    @Override
    public void keyPressed(KeyEvent e)
    {
    }

    @Override
    public void keyReleased(KeyEvent e)
    {
//        calculateStudentResults();
//        calculateStudentAverage();
        //       calculateQuestionModes();
    }

    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="Manage Screen Data">    
    public void ClearData()
    {

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
            streamOut.writeUTF(dataValues.toString());
            streamOut.flush();
           // txtaLinkList.setText("");
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
            println(msg);

        // NEW -----------------------------------
            String[] parts = msg.split(" ");
            String part1 = parts[0];
            String part2= parts[1];
            String temp[] = part2.split(",");
            dataValues.add(new Object[]
        {
            temp[0], temp[1], temp[4], temp[5]
        });
            TrafficTable.repaint();
            txtaNewData.setText(part2);
            // if lblmessage == 
            TrafficModel.fireTableDataChanged();
            myBTree.addBTNode(Integer.parseInt(temp[3]), temp[0] + "_" + temp[1]);
            String str = temp[0] + "," +  temp[1] + "," + temp[2] + "," + temp[3] + "," + temp[4] + "," + temp[5];
            myDList.head.append(new Node(str));
            myDList.displayInTextArea(txtaLinkList);
        //----------------------------------------
           
        }
    }

    public void open()
    {
        try
        {
            streamOut = new DataOutputStream(socket.getOutputStream());
            client = new ChatClientThread1(this, socket);
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
        client.close();
        client.stop();
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
    
    //</editor-fold>    
    //<editor-fold defaultstate="collapsed" desc="File Management">    
    private void readDataFile(String fileName)
    {
        
    }

    public void writeFile()
    {
        // Try to print out the data and if an exception occurs go to the Catch section 
        try
        {
            // After testing has been completed, replace the hard-coded filename: "IPAddresses_New.txt"
            //       with the parameter variable: fileName 
            // Set up a PrintWriter for printing the array content out to the data file.
            PrintWriter out = new PrintWriter(new FileWriter("HashingMap.txt"));
            
            // Print out each line of the array into your data file.
            // Each line is printed out in the format:  PCName,PCID,IPAddress
           
           
             out.print(hm);;
                                   // Close the printFile (and in so doing, empty the print buffer)
             out.close();
        }
        catch (Exception e)
        {
            // If an exception occurs, print an error message on the console.
            System.err.println("Error Writing File: " + e.getMessage());
        }
    }
    
    public void saveHashMap()
    {         
        hm = new HashMap<Integer, String>();

        for (int i = 0; i < dataValues.size(); i++)
        {
                hm.put(Integer.parseInt(dataValues.get(i)[2].toString()), dataValues.get(i)[0].toString() + "_" + dataValues.get(i)[1].toString() );
        }
        System.out.println(hm);
        writeFile();
    }
    //.... and print out to a hashfile
    
    
  public static void bubbleSort(ArrayList<Object[]> arr) 
    {
        
        for(int j=0; j<arr.size(); j++) 
        {  
            for(int i=j+1; i<arr.size(); i++)
            {  
                if((arr.get(i)[1].toString() + arr.get(i)[0]).toString().compareToIgnoreCase(arr.get(j)[1].toString()+arr.get(j)[0].toString())<0)
                {  
                   Object[] words = arr.get(j); 
                   arr.set(j, arr.get(i));
                   arr.set(i, words);
                }  
            }  
          //TrafficTable.repaint();
            System.out.println(arr.get(j)[0] + " - " + arr.get(j)[1] + " - " + arr.get(j)[2] + " - " + arr.get(j)[3]);  
        }  
    }  
  public static void insertionSort(ArrayList <Object[]> arr)
  {
      int j;
      Object[] key;
      int i;
      System.out.println();
      System.out.println();
      for( j=1; j < arr.size(); j++)
      {
          key = arr.get(j);
          for (i = j - 1; ( i >= 0 ) && ((arr.get(i)[2]).toString().compareToIgnoreCase(key[2].toString()) > 0); i--)
           {
              arr.set(i+1, arr.get(i));                                                                    
          }
            arr.set(i+1, key);
            System.out.println(arr.get(j)[0] + " - " + arr.get(j)[1] + " - " + arr.get(j)[2] + " - " + arr.get(j)[3]);
      }
     
  }
  
  public static void selectionSort(ArrayList<Object[]>arr)
  {
      
      int first,i, j; 
      
         
    for ( i = arr.size() - 1; i > 0; i--)
      {
          first = 0;
          for(j = 1; j <= i; j++ )
          {
              if ( arr.get(j)[3].toString().compareToIgnoreCase(arr.get(first)[3].toString())<0)
                  first = j;
          }
          Object[] temp = arr.get(i);
          arr.set(i, arr.get(first));
          arr.set(first, temp);
          //System.out.println(arr.get(j)[0] + " - " + arr.get(j)[1] + " - " + arr.get(j)[2] + " - " + arr.get(j)[3]);
      }
    
  }
    //</editor-fold>    
}
