package redrun.network.experimental;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.JSONObject;

/**
 * Facilitate client interaction with the server
 * 
 * @author Jayson Grace ( jaysong@unm.edu )
 * @author Joel Castellanos ( joel@unm.edu )
 * @version 1.0
 * @since 2014-11-17
 */
public class Client
{
  private Socket clientSocket;
  private PrintWriter write;
  private BufferedReader reader;
  private long startNanoSec;
  private Scanner keyboard;
  private ClientListener listener;

  private volatile int sneedsInStore;

  /**
   * Client instantiation
   * 
   * @param host ip address or FQDN of machine to connect to
   * @param portNumber port number to connect to
   */
  public Client(String host, int portNumber)
  {
    startNanoSec = System.nanoTime();
    System.out.println("Starting Client: " + timeDiff());

    keyboard = new Scanner(System.in);

    // Try to connect until a connection is established
    while (!openConnection(host, portNumber))
    {
    }

    // Start listening thread
    listener = new ClientListener();
    System.out.println("Client(): Starting listener = : " + listener);
    listener.start();

    // listenToUserRequests();

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

    JSONObject json = new JSONObject();
    json.put("type", "CONNECT");

    try (OutputStreamWriter out = new OutputStreamWriter(clientSocket.getOutputStream(), StandardCharsets.UTF_8))
    {
      out.write(json.toString());
    }
    catch (IOException e)
    {
      e.printStackTrace();
      return false;
    }

    // try
    // {
    // write = new PrintWriter(clientSocket.getOutputStream(), true);
    // }
    // catch (IOException e)
    // {
    // System.err.println("Client Error: Could not open output stream");
    // e.printStackTrace();
    // return false;
    // }
    // try
    // {
    // reader = new BufferedReader(new
    // InputStreamReader(clientSocket.getInputStream()));
    // }
    // catch (IOException e)
    // {
    // System.err.println("Client Error: Could not open input stream");
    // e.printStackTrace();
    // return false;
    // }
    return true;
  }

  /**
   * Parse input commands to send out to server
   */
  private void listenToUserRequests()
  {
    Pattern buy = Pattern.compile("buy:\\s\\d*\\s\\d*\\.\\d{1,2}$", Pattern.CASE_INSENSITIVE);
    Pattern sell = Pattern.compile("sell:\\s\\d*\\s\\d*\\.\\d{1,2}$", Pattern.CASE_INSENSITIVE);
    Pattern inventory = Pattern.compile("inventory$", Pattern.CASE_INSENSITIVE);
    Pattern quit = Pattern.compile("quit$", Pattern.CASE_INSENSITIVE);

    while (true)
    {
      System.out.println("Enter Command (Buy: #amount #price | Sell: #amount #price | Inventory | Quit):");
      String cmd = keyboard.nextLine();

      if (cmd == null) continue;
      if (cmd.length() < 1) continue;
      // Matchers for checking input values
      Matcher matchBuy = buy.matcher(cmd);
      Matcher matchSell = sell.matcher(cmd);
      Matcher matchInventory = inventory.matcher(cmd);
      Matcher matchQuit = quit.matcher(cmd);

      // Check input if specified for buying
      if (matchBuy.find()) write.println(cmd);
      else if (matchSell.find()) write.println(cmd);
      else if (matchInventory.find()) System.out.println("Sneeds in Inventory = " + sneedsInStore);
      else if (matchQuit.find())
      {
        write.println(cmd);
        break;
      }
      else System.out.println("Invalid input, please stop that!");
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

  /**
   * Main statement for Clients
   * 
   * @param args
   */
  public static void main(String[] args)
  {

    String host = "127.0.0.1";
    int port = 7777;
    new Client(host, port);
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

    /**
     * Destroy thread for client
     */
    public void destroyThread()
    {
      this.destroy = true;
    }

    /**
     * Run the client listener
     */
    public void run()
    {
      while (!destroy)
      {
        read();
      }
    }

    /**
     * Parse messages that the client receives from the server
     */
    private void read()
    {
      // Pattern getTransaction =
      // Pattern.compile("(\\d+\\.\\d+):\\s\\w+=(\\d*)\\s:\\s\\w+=(\\d+\\.\\d*)");
      try
      {
        // System.out.println("Client: listening to socket");
        String msg = reader.readLine();

        // Matcher matchTransaction = getTransaction.matcher(msg);

        // Get the updated inventory
        // if (matchTransaction.find())
        // {
        // sneedsInStore = Integer.parseInt(matchTransaction.group(2));
        // System.out.println(matchTransaction.group(1) + " : " + "inventory=" +
        // matchTransaction.group(2) + " : "
        // + "treasury=" + matchTransaction.group(3));
        // }
        if (msg.startsWith("Success") || msg.startsWith("Error")) System.out.println(msg);
        else
        {
          System.out.println("Message from Server(" + timeDiff() + ") = " + msg);
        }
      }
      catch (IOException e)
      {
        e.printStackTrace();
      }
    }
  }
}