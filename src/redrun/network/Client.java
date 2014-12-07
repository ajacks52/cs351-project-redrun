package redrun.network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import redrun.model.game.GameData;
import redrun.model.gameobject.player.Player;

/**
 * Facilitate client interaction with the server
 * 
 * @author Jayson Grace ( jaysong@unm.edu )
 * @version 1.0
 * @since 2014-11-22
 */
public class Client
{
  private Socket clientSocket;
  private PrintWriter write;
  private BufferedReader reader;
  private long startNanoSec;
  private ClientListener listener;

  /**
   * Client instantiation
   * 
   * @param host IP address or FQDN of machine to connect to
   * @param portNumber port number to connect to
   * @param mapObjects list of map objects that the player uses to render map
   */
  public Client(String host, int portNumber)
  {
    startNanoSec = System.nanoTime();
    System.out.println("Starting Client: " + timeDiff());

    // Try to connect until a connection is established...
    while (!openConnection(host, portNumber));

    // Start listening thread...
    listener = new ClientListener();
    System.out.println("Client(): Starting listener = : " + listener);
    listener.start();
  }

  /**
   * Close the reader and writer
   */
  public void closeAll()
  {
    System.out.println("Client.closeAll()");

    listener.destroyThread();

    try
    {
      listener.join();
    }
    catch (InterruptedException e)
    {
      e.printStackTrace();
    }

    if (write != null) write.close();
    if (reader != null)
    {
      try
      {
        reader.close();
        clientSocket.close();
      }
      catch (IOException e)
      {
        System.err.println("Client Error: Could not close");
        e.printStackTrace();
      }
    }
  }

  /**
   * Open connection for client
   * 
   * @param host server address to connect to
   * @param portNumber port number to connect to on the server
   * @return false if unable to open a connection to the server, true if
   *         successful
   */
  private boolean openConnection(String host, int portNumber)
  {
    try
    {
      clientSocket = new Socket(host, portNumber);
    }
    catch (UnknownHostException e)
    {
      System.err.println("Client Error: Unknown Host " + host);
      e.printStackTrace();
      return false;
    }
    catch (IOException e)
    {
      System.err.println("Client Error: Could not open connection to " + host + " on port " + portNumber);
      e.printStackTrace();
      return false;
    }

    try
    {
      write = new PrintWriter(clientSocket.getOutputStream(), true);
    }
    catch (IOException e)
    {
      System.err.println("Client Error: Could not open output stream");
      e.printStackTrace();
      return false;
    }
    try
    {
      reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
    }
    catch (IOException e)
    {
      System.err.println("Client Error: Could not open input stream");
      e.printStackTrace();
      return false;
    }
    return true;
  }

  /**
   * Parse input commands to send out to server
   */
  public void sendToServer(String information)
  {
    Pattern buy = Pattern.compile("buy:\\s\\d*\\s\\d*\\.\\d{1,2}$", Pattern.CASE_INSENSITIVE);
    Pattern sell = Pattern.compile("sell:\\s\\d*\\s\\d*\\.\\d{1,2}$", Pattern.CASE_INSENSITIVE);
    Pattern inventory = Pattern.compile("inventory$", Pattern.CASE_INSENSITIVE);
    Pattern quit = Pattern.compile("quit$", Pattern.CASE_INSENSITIVE);

    while (true)
    {
//      System.out.println("Enter Command (Buy: #amount #price | Sell: #amount #price | Inventory | Quit):");
//      String cmd = keyboard.nextLine();
      
      
      if (information == null) continue;
      if (information.length() < 1) continue;
      // Matchers for checking input values
//      Matcher matchBuy = buy.matcher(cmd);
//      Matcher matchSell = sell.matcher(cmd);
//      Matcher matchInventory = inventory.matcher(cmd);
//      Matcher matchQuit = quit.matcher(cmd);

      // Check input if specified for buying
//      if (matchBuy.find()) write.println(cmd);
//      else if (matchSell.find()) write.println(cmd);
//      else if (matchQuit.find())
//      {
        write.println(information);
        break;
//      }
//      else System.out.println("Invalid input, please stop that!");
    }
  }

  /**
   * Get difference in time between the start of the client's runtime and the
   * calling time of the method
   * 
   * @return the difference in time
   */
  private String timeDiff()
  {
    long nanoSecDiff = System.nanoTime() - startNanoSec;
    double secDiff = (double) nanoSecDiff / 1000000000.0;
    return String.format("%.6f", secDiff);
  }

  // public static void main(String[] args)
  // {
  // new Client(Server.HOST, Server.PORT, new LinkedList<MapObject>());
  // }

  /**
   * Request mapObjects from the server
   */
  public void requestMapObjects()
  {
    this.write.println("Send MapObjects");
  }
  
  /**
   * Requests to be disconnected from the server.
   */
  public void requestDisconnet()
  {
    this.write.println("Disconnect");
  }
  
  public void sendUserInput(UserInput input)
  {
    this.write.println(input.toString());
  }
  
  public void sendPlayer(Player player)
  {
    this.write.println(player.toString());
  }

  /**
   * Facilitate listening to inbound messages for the client and destroy the
   * thread used for listening for messages if necessary
   * 
   * @author Jayson Grace ( jaysong@unm.edu )
   * @version 1.0
   * @since 2014-11-17
   */
  class ClientListener extends Thread
  {
    /** For closing the thread gracefully. */
    private volatile boolean destroy = false;

    /**
     * Gracefully closes the thread.
     */
    public void destroyThread()
    {
      this.destroy = true;
    }

    /**
     * Listen for incoming data from the server.
     */
    @Override
    public void run()
    {
      // System.out.println("ClientListener.run()");

      while (!destroy)
      {
        Pattern getMap = Pattern
            .compile("(===\\sMap\\s===)\\sID:(\\d+)\\sName:(.*?)\\sSkyBox:(\\w+)\\sFloor:(\\w+)\\sLight Position:(.*?)\\s===");

        Pattern getMapObject = Pattern
            .compile("(===\\sMap\\sObject\\s===)\\sID:(\\d+)\\sName:(\\w+)\\sLocation:(\\d+\\.\\d+f),\\s(\\d+\\.\\d+f),\\s(\\d+\\.\\d+f)\\sGround Texture:(\\w+)\\sWall Texture:(\\w+)\\sDirection:(\\w+)\\sTrap Type:(.*?)\\sMap\\sID:(\\d+)\\s===");

        // Pattern getGameObject = Pattern
        // .compile(".*(Game\\sObject).*ID:(\\d+)\\sPosition:(\\d+\\.\\d+),\\s(\\d+\\.\\d+),\\s(\\d+\\.\\d+)\\sPhysics:\\sMass:(-?\\d+\\.\\d+)\\sName:\\s(\\w+)\\s(===)");
        try
        {
          // System.out.println("Client: Listening to socket...");

          // Wait for the next message from the server...
          String msg = reader.readLine();

          // System.out.println("???" + msg + "???");

          Matcher matchMap = getMap.matcher(msg);
          Matcher matchMapObject = getMapObject.matcher(msg);
          // Matcher matchGameObject = getGameObject.matcher(msg);

          if (matchMap.find())
          {
            GameData.networkData.add(msg);
          }

          else if (matchMapObject.find())
          {
            GameData.networkData.add(msg);
          }
          // else if (matchGameObject.find())
          // {
          // // gameObjects.add("GameObject Start");
          // for (int i = 1; i <= matchGameObject.groupCount(); i++)
          // {
          // gameObjects.add(matchGameObject.group(i));
          // }
          // // gameObjects.add("GameObject Finish");
          // GraphicsTestTroy.networkData.add(gameObjects);
          // }
          else
          {
            System.out.println("Unrecognized message " + msg + " sent, error!");
          }
          // System.out.println("Current Server time: " +
          // Server.getElapsedTime());
        }
        catch (IOException e)
        {
          e.printStackTrace();
        }
      }
    }
  }
}