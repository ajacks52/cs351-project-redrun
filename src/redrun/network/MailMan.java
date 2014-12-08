package redrun.network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import redrun.database.Map;
import redrun.database.MapObjectDB;

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
  private boolean trapReady = true;

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
    clientWriter.println(msg);
  }

  /**
   * Facilitate responding to client requests from the server
   */
  public void run()
  {
    Pattern playerData = Pattern
        .compile("===\\sPlayer\\s===\\sLocation:\\[(.*?),\\s(.*?),\\s(.*?),\\s(.*?),\\s(.*?),\\s(.*?),\\s(.*?),\\s(.*?),\\s(.*?),\\s(.*?),\\s(.*?),\\s(.*?),\\s(.*?),\\s(.*?),\\s(.*?),\\s(.*?)\\]\\sName:(.*?)\\sTeam\\sName:(\\w+)\\sHealth:(\\d+)\\sLives\\sleft:(\\d+)\\sAlive:(\\w+)\\s===");
    Pattern requestDisconnect = Pattern.compile("Disconnect");
    Pattern requestPlayer = Pattern.compile("Player");
    Pattern requestMapData = Pattern.compile("Map");

    while (true)
    {
      try
      {
        String incomingMessage = clientReader.readLine();

        Matcher matchInboundPlayer = playerData.matcher(incomingMessage);
        Matcher matchRequestDisconnect = requestDisconnect.matcher(incomingMessage);
        Matcher matchRequestPlayer = requestPlayer.matcher(incomingMessage);
        Matcher matchRequestMapData = requestMapData.matcher(incomingMessage);

        // TODO: Add trap parser
        if (matchRequestDisconnect.find())
        {
          System.out.println("Quitting!");
          send("Disconnecting client...");
          Server.deleteClientFromList(this);
          break;
        }
        else if (matchInboundPlayer.find())
        {
          this.setPlayerData(incomingMessage);
          playerReady = true;
          Server.checkBroadcast();
        }
        else if (matchRequestPlayer.find())
        {
          send(Server.assignPlayer());
        }
        else if (matchRequestMapData.find())
        {
          System.out.println("Client Requested Map Data");

          for (Map map : Server.mapData)
          {
            this.send(map.toString());
          }
          for (MapObjectDB mapObject : Server.mapObjectData)
          {
            this.send(mapObject.toString());
          }
        }
      }
      catch (IOException e)
      {
        System.err.println("Client Error: Could not open input stream");
        e.printStackTrace();
      }
    }
  }

  /**
   * Check to see if both player and trap information are prepared for
   * transmission
   * 
   * @return true if both are ready, false otherwise
   */
  protected boolean isReady()
  {
    return playerReady && trapReady;
  }

  /**
   * Reset the ready state of player and trap information
   */
  protected void resetReady()
  {
    this.playerReady = false;
    //TODO
    this.trapReady = true;
  }

  /**
   * @return the playerData
   */
  public String getPlayerData()
  {
    return playerData;
  }

  /**
   * @param playerData the playerData to set
   */
  public void setPlayerData(String playerData)
  {
    this.playerData = playerData;
  }
}