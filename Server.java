import java.net.*;
import java.util.LinkedList;
import java.io.*;

public class Server extends Thread
{
    public GWackChannel channel;
    private String username = "";
    private Socket clientSocket;
    private LinkedList<Server> list;
    private PrintWriter streamOut;
    private BufferedReader streamIN;

    public Server(Socket sock, LinkedList<Server> llist, GWackChannel chann, String username)
    {
        super();
        this.clientSocket = sock;
        this.list = llist;    
        this.channel = chann;
        this.username = username;
    }

    public PrintWriter getWriter()
    {
        return this.streamOut;
    }

    public String getUserName()
    {
        return this.username;
    }

    public Socket getClientSocket()
    {
        return this.clientSocket;
    }

    public void sendMessage(String message)
    {
        for(Server i : this.list)
        {
            if(!(i.getClientSocket().isClosed()))
            {
                i.getWriter().println(message);
                i.getWriter().flush();
            }
        }
    }

    public void run()
    {
        
        try
        {
            streamIN = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            streamOut = new PrintWriter(clientSocket.getOutputStream());
            String mess = "";
            this.sendMessage(channel.updateMembers());          
            while((mess = streamIN.readLine()) != null)
            {
                this.sendMessage(channel.updateMembers());
                String clientmessage = "["+username+"] "+mess;
                this.sendMessage(clientmessage);      
            }    
             
        }
        catch(Exception e)
        { }
        finally
        {
            try
            {
                if(streamOut != null)
                {
                    streamOut.close();
                }
                
                if(streamIN != null)
                {
                    streamIN.close();
                    clientSocket.close();
                    channel.disconnectUser(this);
                    this.sendMessage(channel.updateMembers());
                }

            }
            catch(Exception e){}
        }
                

    }

       

    
}