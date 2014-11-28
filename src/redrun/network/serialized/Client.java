package redrun.network.serialized;

//Client
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.io.*;
import java.util.Vector;

import org.json.JSONObject;

public class Client
{

  protected static Vector<String> vectorX = new Vector<String>();

  public Client()
  {
    vectorX.addElement("hello");
    vectorX.add("goodbye");
    vectorX.add("finally");
  }

  public static void main(String args[])
  {
    try
    {
      new Client();
      Socket s = new Socket("localhost", 7777);
      OutputStream os = s.getOutputStream();
      // ObjectOutputStream oos = new ObjectOutputStream(os);
      // TestObject to = new TestObject(1, "theID", vectorX);
      // System.out.println(vectorX.size());
      // oos.writeObject(to);
      
      JSONObject json = new JSONObject();
      json.put("type", "CONNECT");
      try (OutputStreamWriter out = new OutputStreamWriter(s.getOutputStream(), StandardCharsets.UTF_8))
      {
        out.write(json.toString());
      }
      
      // oos.close();
      os.close();
      s.close();
    }
    catch (Exception e)
    {
      System.out.println(e);
    }
  }
}