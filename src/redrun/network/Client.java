package redrun.network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.LinkedList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import redrun.model.constants.Direction;
import redrun.model.gameobject.MapObject;
import redrun.model.gameobject.map.Corner;
import redrun.model.gameobject.map.Corridor;
import redrun.model.gameobject.map.End;
import redrun.model.gameobject.map.Field;
import redrun.model.gameobject.map.Pit;
import redrun.model.gameobject.map.Platform;
import redrun.model.gameobject.map.Staircase;
import redrun.model.gameobject.map.Start;
import redrun.model.gameobject.map.Tunnel;
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
  private Player player = null;
  private LinkedList<MapObject> mapObjects = null;

  /**
   * Client instantiation
   * 
   * @param host IP address or FQDN of machine to connect to
   * @param portNumber port number to connect to
   * @param mapObjects list of map objects that the player uses to render map
   */
  public Client(String host, int portNumber, LinkedList<MapObject> mapObjects)
  {
    startNanoSec = System.nanoTime();
    System.out.println("Starting Client: " + timeDiff());

    this.mapObjects = mapObjects;
    
    // Try to connect until a connection is established
    while (!openConnection(host, portNumber))
    {
    }

    // Start listening thread
    listener = new ClientListener();
    System.out.println("Client(): Starting listener = : " + listener);
    listener.start();

//    this.getInformationFromDB();
//    this.generateRunner();
    // this.generatePhysics();
//     listenToUserRequests();

    closeAll();
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
//  private void listenToUserRequests()
//  {
//    // Get player object
//    Pattern quit = Pattern.compile("quit$", Pattern.CASE_INSENSITIVE);
//
//    while (true)
//    {
//      String data;
//      data = (this.player.toNetworkString());
//
//      Matcher matchQuit = quit.matcher(data);
//
//      if (matchQuit.find())
//      {
//        write.println(data);
//        break;
//      }
//      else write.println(data);
//    }
//  }

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

  /**
   * Main statement for Clients
   * 
   * @param args
   */
  public static void main(String[] args)
  {
    new Client(Server.HOST, Server.PORT, new LinkedList<MapObject>());
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
    private volatile boolean destroy = false;

    public void destroyThread()
    {
      this.destroy = true;
    }

    /**
     * Run the client listener
     */
    public void run()
    {
      System.out.println("ClientListener.run()");
      while (!destroy)
      {
        read();
      }
    }

    /**
     * Receive information about the game such as other players and the state of
     * the map
     */
    private void read()
    {
      Pattern getMapObject = Pattern
          .compile("===\\sMap\\sObject\\s===\\sID:(\\d+)\\sName:(\\w+)\\sLocation:\\((\\d+\\.\\d+f),\\s(\\d+\\.\\d+f),\\s(\\d+\\.\\d+f)\\)\\sTexture:(\\w+)\\sDirection:(\\w+\\.\\w+)\\sMap\\sID:(\\d+)\\s===");

      Pattern getGameObject = Pattern
          .compile(".*Game\\sObject.*ID:(\\d+)\\sPosition:(\\d+\\.\\d+),\\s(\\d+\\.\\d+),\\s(\\d+\\.\\d+)\\sPhysics:\\sMass:(-?\\d+\\.\\d+)\\sName:\\s(\\w+)\\s===");
      try
      {
        System.out.println("Client: listening to socket");
        String msg = reader.readLine();

        Matcher matchGameObject = getGameObject.matcher(msg);
        Matcher matchMapObject = getMapObject.matcher(msg);

        if (matchMapObject.find())
        {
          float x = Float.parseFloat(matchMapObject.group(3));
          float y = Float.parseFloat(matchMapObject.group(4));
          float z = Float.parseFloat(matchMapObject.group(5));
          String textureName = matchMapObject.group(6);

          Direction orientation = null;
          switch (matchMapObject.group(7))
          {
            case "Direction.NORTH":
            {
              orientation = Direction.NORTH;
              break;
            }
            case "Direction.EAST":
            {
              orientation = Direction.EAST;
              break;
            }
            case "Direction.SOUTH":
            {
              orientation = Direction.SOUTH;
              break;
            }
            case "Direction.WEST":
            {
              orientation = Direction.WEST;
              break;
            }
            default:
            {
              try
              {
                throw new IllegalArgumentException();
              }
              catch (IllegalArgumentException e)
              {
                e.printStackTrace();
              }
            }
          }

          switch (matchMapObject.group(2))
          {
            case "Corner":
            {
              mapObjects.add(new Corner(x, y, z, textureName, orientation, null));
            }
            case "Corridor":
            {
              mapObjects.add(new Corridor(x, y, z, textureName, orientation, null));
            }
            case "End":
            {
              mapObjects.add(new End(x, y, z, textureName, orientation, null));
            }
            case "Field":
            {
              mapObjects.add(new Field(x, y, z, textureName, orientation, null));
            }
            case "Pit":
            {
              mapObjects.add(new Pit(x, y, z, textureName, orientation, null));
            }
            case "Platform":
            {
              mapObjects.add(new Platform(x, y, z, textureName, orientation, null));
            }
            case "Staircase":
            {
              mapObjects.add(new Staircase(x, y, z, textureName, orientation, null));
            }
            case "Start":
            {
              mapObjects.add(new Start(x, y, z, textureName, orientation, null));
            }
            case "Tunnel":
            {
              mapObjects.add(new Tunnel(x, y, z, textureName, orientation, null));
            }
            default:
            {
              try
              {
                throw new IllegalArgumentException();
              }
              catch (IllegalArgumentException e)
              {
                e.printStackTrace();
              }
            }
          }
        }    
        if (matchGameObject.find())
        {
          System.out.println("Current Server time: " + Server.getElapsedTime());
          for (int i = 1; i <= matchGameObject.groupCount(); i++)
            System.out.println("matched text: " + matchGameObject.group(i));
        }
        // otherwise if it's a physics item
      }
      catch (IOException e)
      {
        e.printStackTrace();
      }
    }
  }
}