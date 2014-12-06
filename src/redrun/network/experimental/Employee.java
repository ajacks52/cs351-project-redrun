package redrun.network.experimental;

public class Employee implements java.io.Serializable
{
  public String name;
  public String address;
  public int ssn;
  public int number;

  public void mailCheck()
  {
    System.out.println("Mailing a check to " + name + " " + address);
  }
}