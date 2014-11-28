package redrun.network.sendfile;

import java.io.*;
import java.net.*;

class TCPServer
{
  public static final int PORT = 4444;
  public static final String json = "/tmp/student.json";

  public static void main(String args[])
  {

    while (true)
    {
      ServerSocket welcomeSocket = null;
      Socket connectionSocket = null;
      BufferedOutputStream outToClient = null;

      try
      {
        welcomeSocket = new ServerSocket(PORT);
        connectionSocket = welcomeSocket.accept();
        outToClient = new BufferedOutputStream(connectionSocket.getOutputStream());
      }
      catch (IOException ex)
      {
        // Do exception handling
      }

      if (outToClient != null)
      {
        File myFile = new File(json);
        byte[] mybytearray = new byte[(int) myFile.length()];

        FileInputStream fis = null;

        try
        {
          fis = new FileInputStream(myFile);
        }
        catch (FileNotFoundException ex)
        {
          // Do exception handling
        }
        BufferedInputStream bis = new BufferedInputStream(fis);

        try
        {
          bis.read(mybytearray, 0, mybytearray.length);
          outToClient.write(mybytearray, 0, mybytearray.length);
          outToClient.flush();
          outToClient.close();
          connectionSocket.close();

          // File sent, exit the main method
          return;
        }
        catch (IOException ex)
        {
          // Do exception handling
        }
      }
    }
  }
}