package redrun.network.serialized;

import java.net.*;
import java.io.*;

import redrun.model.gameobject.player.Runner;

public class Server
{

  public static void main(String args[])
  {
    int port = 7777;
    try
    {
      ServerSocket ss = new ServerSocket(port);
      Socket s = ss.accept();
      InputStream is = s.getInputStream();
      ObjectInputStream ois = new ObjectInputStream(is);
      ois.readObject();
//      TestObject to = (TestObject) ois.readObject();
//      System.out.println("Vector size : " + to.vectorX.size() + " and object.id : " + to.id);
      is.close();
      s.close();
      ss.close();
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
  }
}