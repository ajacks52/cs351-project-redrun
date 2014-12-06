package redrun.network.experimental;

import java.io.*;

public class SerializeDemo
{
  public static void main(String[] args)
  {
    Employee e = new Employee();
    e.name = "Jayson Grace";
    e.address = "1734 Solano Drive NorthEast, Albuquerque, NM 87110";
    e.ssn = 123123243;
    e.number = 1;
    try
    {
      FileOutputStream fileOut = new FileOutputStream("/tmp/employee.ser");
      ObjectOutputStream out = new ObjectOutputStream(fileOut);
      out.writeObject(e);
      out.close();
      fileOut.close();
      System.out.printf("Serialized data is saved in /tmp/employee.ser");
    }
    catch (IOException i)
    {
      i.printStackTrace();
    }
  }
}