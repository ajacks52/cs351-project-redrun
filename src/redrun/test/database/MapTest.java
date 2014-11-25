package redrun.test.database;

import redrun.database.Map;

/**
 * Testing class for the Map class
 * 
 * @author Jayson Grace ( jaysong@unm.edu )
 * @version 1.0
 * @since 2014-11-24
 *
 */
public class MapTest
{
  private static final boolean DEBUG = true;

  /**
   * First test for our Map DB class
   * 
   * @return true if all tests pass
   */
  private boolean test()
  {
    Map map = new Map(1, "Ice World");
    assert (map.toString() != null) : "Did not genereate Map properly, fail sauce";
    assert (map.toString().contains("Ice World")) : "Map name not generated properly.";
    if (DEBUG) System.out.println(map.toString());
    return true;
  }

  /**
   * Main statement for test cases
   * 
   * @param args
   */
  public static void main(String[] args)
  {
    MapTest MapTest = new MapTest();
    if (MapTest.test() == true) System.out.println("Test passed.");
  }
}