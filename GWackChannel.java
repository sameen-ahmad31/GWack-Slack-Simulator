import java.net.*;
import java.util.LinkedList;
import java.io.*;

public class GWackChannel
{
    private String username = "";
    ServerSocket serversocket;
    private BufferedReader streamIN;
    public LinkedList<Server> clientList;

       
    public LinkedList<Server> getClientList()
    {
        return this.clientList;
    }

    public void disconnectUser(Server user)
    {
        clientList.remove(user);
    }

    public String updateMembers()
    {
        String members = "START_CLIENT_LIST\n";
        for(Server u : clientList)
        {
            members = members + u.getUserName()+"\n";
        }
        members = members + "END_CLIENT_LIST";
        return members;
    }

   // public static void main(String args[]) 
   // {
        
        //Client:
        //Socket(IP Address, Port Number)
        //Button: addActionListener(ActionButton())
            //toggle the connect/disconnet label
            //create the socket by calling the Socket() constructor
            //get the output stream and write the secret key and name (does the handshake): write to the strean
        //Send messages: send button had addActionListener

        //Server
        //connects to the server
        //infinite while loop
            //uses it to keep connecting

        //Multithreading:
            //multiple different functions working at the same time
   // }
   
    public GWackChannel(int portNum)
    {
        clientList = new LinkedList<Server>();
        try
        {
            serversocket = new ServerSocket(portNum);
        }
        catch(Exception e)
        {
            System.err.println("Unable to connect");
            //JOptionPane.showMessageDialog(this,"Cannot connect to server","Error",JOptionPane.ERROR_MESSAGE); 
            System.exit(1);
        }
    }
   
       public void server()
       {
           
           while(true)
           {
               Socket clientSocket;
           try 
           {
               clientSocket = serversocket.accept();
               System.out.println("Connection "+clientSocket.getRemoteSocketAddress());
               streamIN = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
               String c = streamIN.readLine();
               if(c.substring(0,6).equals("NAME: ")){
                   username = c.substring(6);
               }
               Server clientserve = new Server(clientSocket, clientList, this, username);
               clientList.add(clientserve);
               clientserve.start();
               
           } 
           catch (IOException e) 
           {
               e.printStackTrace();
               break;
           }
           
       }
           
    }
    
   
       public static void main(String args[]){
           if(args.length <1){
                //JOptionPane.showMessageDialog(this,"Cannot connect to server","Error",JOptionPane.ERROR_MESSAGE); 
               System.out.println("Error: Not a Valid Port Number");
               System.exit(1);
           }
           int portnum;
          // Integer.parseInt(enterPort.getText());
          try
          {
            portnum = Integer.parseInt(args[0]);
            GWackChannel chan = new GWackChannel(portnum);
            chan.server();
   
          }
          catch (NumberFormatException e) 
           {
               e.printStackTrace();
              // break;
           }

       }
       
   
   
   
   }


