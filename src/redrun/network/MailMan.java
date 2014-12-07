package redrun.network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import redrun.database.Map;
import redrun.database.MapObjectDB;
import redrun.database.RedRunDAO;

/**
 * Directs the flow of information between a client and the server
 * 
 * @author Jayson Grace ( jaysong@unm.edu )
 * @version 1.0
 * @since 2014-11-17
 */
public class MailMan extends Thread
{
  @SuppressWarnings("unused")
  private Socket client;
  private PrintWriter clientWriter;
  private BufferedReader clientReader;
  private String playerData;
  private boolean playerReady = false;
  private boolean trapReady = false;

  protected boolean isReady()
  {
    return playerReady && trapReady;
  }

  protected void resetReady()
  {
    this.playerReady = false;
    this.trapReady = false;
  }

  /**
   * MailMan Instantiation
   * 
   * @param client the client socket for communication
   */
  public MailMan(Socket client)
  {
    this.client = client;

    try
    {
      clientWriter = new PrintWriter(client.getOutputStream(), true);
    }
    catch (IOException e)
    {
      System.err.println("Server Worker: Could not open output stream");
      e.printStackTrace();
    }
    try
    {
      clientReader = new BufferedReader(new InputStreamReader(client.getInputStream()));
    }
    catch (IOException e)
    {
      System.err.println("Server Worker: Could not open input stream");
      e.printStackTrace();
    }
  }

  /**
   * Send message from the server to a client
   * 
   * @param msg message to send to client
   */
  public void send(String msg)
  {
    // System.out.println("MailMan.send(" + msg + ")");
    clientWriter.println(msg);
  }

  /**
   * Either take input to kill a particular client thread or broadcast new game
   * information to all players
   */
  public void run()
  {
    Pattern playerData = Pattern
        .compile("===\\sPlayer\\s===\\sLocation:(.*?)\\sName:(.*?)\\sTexture:(.*?)\\sTeam\\sName:(\\w+)\\sHealth:(\\d+)\\sLives\\sleft:(\\d+)\\sAlive:(\\w+)\\s===");
    Pattern quitServer = Pattern.compile("quit$", Pattern.CASE_INSENSITIVE);
    Pattern requestPlayer = Pattern.compile("Player");
    Pattern requestMapData = Pattern.compile("Map");

    while (true)
    {
      try
      {
        String incomingMessage = clientReader.readLine();
        // System.out.println("DICKNUTS: " + incomingMessage);

        Matcher matchInboundPlayer = playerData.matcher(incomingMessage);
        Matcher matchQuitServer = quitServer.matcher(incomingMessage);
        Matcher matchRequestPlayer = requestPlayer.matcher(incomingMessage);
        Matcher matchRequestMapData = requestMapData.matcher(incomingMessage);

        if (matchQuitServer.find())
        {
          System.out.println("Quitting!");
          send("Disconnecting client...");
          Server.deleteClientFromList(this);
          break;
        }
        else if (matchInboundPlayer.find())
        {
          this.playerData = incomingMessage;
          playerReady = true;
        }
        else if (matchRequestPlayer.find())
        {
          send(Server.assignPlayer().toString());
        }
        else if (matchRequestMapData.find())
        {
          for (Map map : Server.mapData)
          {
            this.send(map.toString());
          }
          // send client map objects
          for (MapObjectDB mapObject : Server.mapObjectData)
          {
            this.send(mapObject.toString());
          }
        }
        System.out.println("PLAYA: " + incomingMessage);

        // Server.players;
        // System.out.println("MailMan:" + mapObject);
        // send(mapObject.toString());

        // else
        // {
        // send(incomingMessage);
        // }
        // else Server.broadcast(incomingMessage);
      }
      catch (IOException e)
      {
        System.err.println("Client Error: Could not open input stream");
        e.printStackTrace();
      }
    }
  }
}