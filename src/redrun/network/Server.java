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
  public static final String HOST = "127.0.0.1";
  public static final int PORT = 7777;
  private ServerSocket serverSocket;
  private static LinkedList<MailMan> allConnections = new LinkedList<MailMan>();
  private static final long time = System.currentTimeMillis();

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
        // send client map
        ArrayList<Map> mapInformation = RedRunDAO.getAllMaps();
        for (Map map : mapInformation)
        {
          worker.send(map.toString());
        }
        // send client map objects
        ArrayList<MapObjectDB> mapObjects = RedRunDAO.getAllMapObjects();
        for (MapObjectDB mapObject : mapObjects)
        {
          worker.send(mapObject.toString());
        }
      }
      catch (IOException e)
      {
        System.err.println("Server error: Failed to connect to client.");
        e.printStackTrace();
      }
    }
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

  /**
   * Broadcast a message to all connected clients
   * 
   * @param s Message to broadcast to connected clients
   */
  public static void broadcast(MailMan currentWorker, String s)
  {
    for (MailMan workers : allConnections)
    {
      // Send message to all workers except the sender
      if (!currentWorker.equals(workers)) workers.send(s);
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