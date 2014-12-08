package redrun.network;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.LinkedList;

import redrun.database.Map;
import redrun.database.MapObjectDB;
import redrun.database.RedRunDAO;

/**
 * Facilitate server functionality
 * 
 * @author Jayson Grace ( jaysong@unm.edu )
 * @version 1.0
 * @since 2014-11-17
 */
public class Server
{
  /** Server ip address */
  public static final String HOST = "127.0.0.1";
  /** Server listening port */
  public static final int PORT = 7777;
  /** Used to listen for inbound client connections */
  private ServerSocket serverSocket;
  /** Used to assign unique players to each connected client */
  private static int counter = 0;
  /** Maintain list of all connections to server */
  private static LinkedList<MailMan> allConnections = new LinkedList<MailMan>();
  /** Server time */
  private static final long time = System.currentTimeMillis();
  /** Keep track of data associated with the map */
  public static ArrayList<Map> mapData = new ArrayList<Map>();
  /** Keep track of data associated with items in the map */
  public static ArrayList<MapObjectDB> mapObjectData = new ArrayList<MapObjectDB>();

  /**
   * Server instantiation
   * 
   * @param portNumber port number for clients to connect to
   */
  public Server(int portNumber)
  {
    try
    {
      serverSocket = new ServerSocket(portNumber);
    }
    catch (IOException e)
    {
      System.err.println("Server error: Opening socket failed.");
      e.printStackTrace();
      System.exit(-1);
    }

    mapData = RedRunDAO.getAllMaps();
    mapObjectData = RedRunDAO.getAllMapObjects();

    waitForConnection();
  }

  /**
   * Wait for client to connect
   */
  public void waitForConnection()
  {
    while (true)
    {
      System.out.println("Server: waiting for Connection");
      try
      {
        Socket client = serverSocket.accept();
        MailMan worker = new MailMan(client);
        worker.start();
        System.out.println("Server: *********** new Connection");
        allConnections.add(worker);
      }
      catch (IOException e)
      {
        System.err.println("Server error: Failed to connect to client.");
        e.printStackTrace();
      }
    }
  }

  /**
   * Assign a player to each connected client
   * 
   * @return player to assign
   */
  public static String assignPlayer()
  {
    String[] players = {
        "=== Player === Location:[1.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 4.0, 2.0, 4.0] Name:Balthazar Team Name:BLUE Health:100 Lives left:5 Alive:true ===",
        "=== Player === Location:[1.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 4.0, 2.0, -4.0] Name:Joel Team Name:RED Health:100 Lives left:5 Alive:true ===",
        "=== Player === Location:[1.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, -4.0, 2.0, 4.0] Name:Archimedes Team Name:BLUE Health:100 Lives left:5 Alive:true ===",
        "=== Player === Location:[1.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, -4.0, 2.0, -4.0] Name:Leeroy Jenkins Team Name:BLUE Health:100 Lives left:5 Alive:true ===" };
    return players[counter++];
  }

  /**
   * Clear all connections
   */
  public void cleanConnectionList()
  {
    allConnections.clear();
  }

  /**
   * Delete single client from our list of connections
   */
  public static void deleteClientFromList(MailMan worker)
  {
    allConnections.remove(worker);
  }

  public static void checkBroadcast()
  {
    // System.out.println("===================");
    // System.out.println("Checking broadcast status...");

    boolean isReady = true;

    for (MailMan workers : allConnections)
    {
      if (!workers.isReady())
      {
        isReady = false;
        break;
      }
    }

    if (isReady)
    {
      // System.out.println("Broadcasting...");

      for (MailMan workers : allConnections)
      {
        broadcast(workers.getPlayerData());
        workers.resetReady();
      }
    }
  }

  /**
   * Broadcast a message to all connected clients
   * 
   * @param s Message to broadcast to connected clients
   */
  public static void broadcast(String networkData)
  {
    // System.out.println("=======================");

    for (MailMan workers : allConnections)
    {
      // System.out.println(networkData);
      workers.send(networkData);
    }
  }

  /**
   * Get elapsed time from the start of running the Server class and when the
   * method is called
   * 
   * @return time elapsed time
   */
  public static String getElapsedTime()
  {
    long nanoSecDiff = System.currentTimeMillis() - time;
    double secDiff = (double) nanoSecDiff / 1000.0f;
    return String.format("%.3f", secDiff);
  }

  /**
   * Main statement for the Server
   * 
   * @param args
   */
  public static void main(String args[])
  {
    new Server(PORT);
  }
}