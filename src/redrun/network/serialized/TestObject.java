package redrun.network.serialized;

import java.io.*;
import java.util.Vector;

public class TestObject implements Serializable
{
  int value;
  String id;
  Vector<String> vectorX;

  public TestObject(int v, String s, Vector<String> vector)
  {
    this.value = v;
    this.id = s;
    this.vectorX = vector;
  }
}