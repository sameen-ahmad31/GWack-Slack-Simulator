import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import javax.swing.border.Border;
import java.awt.Color;
import javax.swing.JFrame;
import java.net.*;
import java.io.*;
import javax.swing.BorderFactory;


public class GClient extends JFrame implements KeyListener {

    private  String userName = "";
    private  String IPaddress = "";
    private  int portNum;
    public  boolean isConnected = false;
    private  BufferedReader streamIn; 
    private  Socket clientsocket;
    private  PrintWriter printwrite;
    private Thread clientThread;

    public GClient() {
        super();

        Border border = BorderFactory.createLineBorder(Color.black);

        //create the frame
        this.setTitle("GWack -- GW Slack Simulator"); 
        this.setSize(1200, 400);          // sets the size (in pixels) of the frame
        this.setLocation(100, 100); 


        //for the button
        JButton disconnect = new JButton("Disconnect");
        disconnect.setVisible(false);
     
        JButton connect = new JButton("Connect");
     
        JButton sendM = new JButton("Send");
    

        //text field to enter the name
        JTextField enterName = new JTextField("", 10);
        enterName.setBorder(border);

        //text field to enter the IP address
        JTextField enterIP = new JTextField("", 10);
        enterIP.setBorder(border);

        //text field to enter the port number
        JTextField enterPort = new JTextField("", 10);
        enterPort.setBorder(border);

        JPanel blankPanel = new JPanel();
        blankPanel.setLayout(new BorderLayout());
        blankPanel.add(new JLabel("   "));
        blankPanel.setBackground(Color.PINK);



        //create the panel itself
        JPanel northpanel = new JPanel(new FlowLayout(FlowLayout.TRAILING)); 
        northpanel.add(new JLabel("Name: "));
        northpanel.add(enterName);
        northpanel.add(new JLabel("IP Address: "));
        northpanel.add(enterIP);
        northpanel.add(new JLabel("Port: "));
        northpanel.add(enterPort);
        northpanel.add(connect); //and the button now
        northpanel.add(disconnect);
        northpanel.add(blankPanel, BorderLayout.SOUTH);
        northpanel.setBackground(Color.PINK);


        //text field to display the messages
        JTextArea displayMess = new JTextArea("", 10,50);
        displayMess.setAutoscrolls(true);
        displayMess.setBorder(border);

        //text field to display the members
        JTextArea displayMembers = new JTextArea("", 10,20);
        displayMembers.setAutoscrolls(true);
        displayMembers.setBorder(border);

        //text field to compose a message
        JTextArea enterMess = new JTextArea("", 10,50);
        enterMess.setBorder(border);

        JScrollPane centerPanelz = new JScrollPane(displayMembers);
        centerPanelz.setBackground(Color.PINK);

        JPanel westPanel = new JPanel();
        westPanel.setLayout(new BorderLayout());
        westPanel.add(new JLabel("Members Online"),BorderLayout.NORTH);
        westPanel.add(centerPanelz, BorderLayout.WEST);
        westPanel.add(blankPanel, BorderLayout.EAST);
        westPanel.setBackground(Color.PINK);


        JScrollPane centerPanely = new JScrollPane(displayMess);
        centerPanely.setBackground(Color.PINK);


        //center Panel 1 for the messages
        JPanel centerPanel1 = new JPanel();
        centerPanel1.setLayout(new BorderLayout());
        centerPanel1.add(new JLabel("Messages"),BorderLayout.NORTH);
        centerPanel1.add(centerPanely, BorderLayout.SOUTH);
        centerPanel1.setBackground(Color.PINK); 


        //center Panel 2 for the compose message
        JPanel centerPanel2 = new JPanel();
        centerPanel2.setLayout(new BorderLayout());
        centerPanel2.add(new JLabel("Compose"),BorderLayout.NORTH);
        centerPanel2.add(enterMess,BorderLayout.SOUTH);
        centerPanel2.setBackground(Color.PINK);

        
        //a panel that combines both of the panel
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BorderLayout());
        centerPanel.add(centerPanel1, BorderLayout.NORTH);
        centerPanel.add(centerPanel2, BorderLayout.SOUTH);
        centerPanel.setBackground(Color.PINK);


        JPanel southPanel = new JPanel();
        southPanel.setLayout(new BorderLayout());
        southPanel.add(sendM, BorderLayout.EAST);
        southPanel.setBackground(Color.PINK);

        
        this.add(northpanel, BorderLayout.NORTH); //add the panel to the frame
        this.add(westPanel,BorderLayout.WEST);
        this.add(centerPanel, BorderLayout.CENTER);
        this.add(southPanel, BorderLayout.SOUTH); //add the send button


        this.pack();
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
       // f.setVisible(true);

        
        connect.addActionListener((e)->{ 
            //if(connect.getText().equals("Connect"))
           // {
                /* connect.setText("Disconnect");
                enterName.setEditable(false);
                enterIP.setEditable(false);
                enterPort.setEditable(false);
                displayMess.setEditable(false); //the displayed message can not be edited
                displayMembers.setEditable(false); //the displayed members can't be edited
                Socket cs = server_socket.accept();
                DataOutputStream d = new DataOutputStream(cs.getOutputStream()); */
                disconnect.setVisible(true);
                connect.setVisible(false);
                isConnected = true;
                userName = enterName.getText();
                enterName.setEditable(false);
                IPaddress = enterIP.getText();
                enterIP.setEditable(false);
                portNum = Integer.parseInt(enterPort.getText());
                enterPort.setEditable(false);
                
                if(enterPort.getText() == null || enterPort.getText().length()< 4)
                {
                    connect.setVisible(true);
                    disconnect.setVisible(false);
                    isConnected = false;
                    JOptionPane.showMessageDialog(this,"Invalid port","Error",JOptionPane.ERROR_MESSAGE);
                                                
                }

                if(!(IPaddress.equals("ssh-cs2113.adamaviv.com") || IPaddress.equals("localhost")))
                {
                    connect.setVisible(true);
                    disconnect.setVisible(false);
                    isConnected = false;
                    JOptionPane.showMessageDialog(this,"Invalid host","Error",JOptionPane.ERROR_MESSAGE);

                }
                try
                {
                    clientsocket = new Socket(IPaddress,portNum);
                    printwrite = new PrintWriter(clientsocket.getOutputStream());
                    streamIn = new BufferedReader(new InputStreamReader(clientsocket.getInputStream()));
                }
                catch(Exception ex)
                {
                    disconnect.setVisible(false);
                    connect.setVisible(true);
                    isConnected = false;
                    JOptionPane.showMessageDialog(this,"Cannot connect to server","Error",JOptionPane.ERROR_MESSAGE);                  
                }

                if(IPaddress.equals("ssh-cs2113.adamaviv.com"))
                {
                    //clientsocket = new Socket(); 
                   // printwrite = new PrintWriter(clientsocket.getOutputStream());
                    printwrite.println("SECRET");
                    printwrite.println("3c3c4ac618656ae32b7f3431e75f7b26b1a14a87");
                    printwrite.println("NAME");
                    printwrite.println(userName);
                    printwrite.flush();
                }
                else if(IPaddress.equals("localhost"))
                {
                    printwrite.println("NAME: " + userName);
                    printwrite.flush();
                }                                       
            
                clientThread = new Thread(() ->{
                try
                {
                    String mess = "";                                                   
                    while(isConnected)
                    {
                        if(streamIn.ready())
                        {
                            mess = streamIn.readLine();
                        }
                        else
                        {
                            continue;
                        }
                        if(mess.equals("START_CLIENT_LIST"))
                        {
                            String clientList ="";
                            mess = streamIn.readLine();
                            while(!mess.equals("END_CLIENT_LIST"))
                            {
                                clientList += mess +"\n";
                                mess = streamIn.readLine();
                            }
                            displayMembers.setText(clientList);
                        }
                        else
                        {
                            displayMess.append(mess + "\n");
                        }
                    }

                }
                catch(Exception ex)
                {
                                                    
                }
            });
            clientThread.start();
        });
        
        disconnect.addActionListener((e)->{   
            disconnect.setVisible(false);
            connect.setVisible(true);
            isConnected = false;   
            displayMembers.setText("");
            displayMess.setText("");
            userName = "";
            enterName.setText("");
            enterName.setEditable(true);
            IPaddress ="";
            enterIP.setText("");
            enterIP.setEditable(true);
            portNum = 0;
            enterPort.setText("");
            enterPort.setEditable(true);
           // printwrite.println("Server disconnected");
            try
            {
                if(IPaddress.equals("localhost"))
                {
                    printwrite.println("Server disconnected");
                    printwrite.flush();
                }
                printwrite.close();
                streamIn.close();
                clientsocket.close();
            }
            catch(IOException ex)
            {
                JOptionPane.showMessageDialog(this,"Unable to Disconnect","Error",JOptionPane.ERROR_MESSAGE);
            }

               /*  connect.setText("Connect");
                enterName.setEditable(true);
                enterIP.setEditable(true);
                enterPort.setEditable(true);
                displayMess.setEditable(false); //the displayed message can not be edited
                displayMembers.setEditable(false); //the displayed members can't be edited */
        });

        enterMess.addKeyListener(new KeyAdapter() 
        {
            public void keyPressed(KeyEvent e)
            {
                if(e.getKeyCode() == KeyEvent.VK_ENTER)
                {
                    String mess = enterMess.getText();
                    printwrite.println(mess);
                    printwrite.flush();
                }
            }

            public void keyReleased(KeyEvent e)
            {
                if(e.getKeyCode() == KeyEvent.VK_ENTER)
                {
                    enterMess.setText("");
                }
            }

          /*   public void keyTyped(KeyEvent e) 
            {
                if(e.getKeyChar() == '\n')
                {
                    String mess = enterMess.getText();
                    printwrite.println(mess);
                    printwrite.flush();
                    enterMess.setText("");
                } 
            } */
   
        }); 

        sendM.addActionListener((e)->{ 
            String mess = enterMess.getText();
            printwrite.println(mess);
            printwrite.flush();
            enterMess.setText("");  

        });

    

    }


    @Override
    public void keyPressed(KeyEvent e) { }

    @Override
    public void keyReleased(KeyEvent e) { }

    @Override
    public void keyTyped(KeyEvent e) { }


}