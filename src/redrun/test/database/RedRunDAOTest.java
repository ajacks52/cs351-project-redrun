package redrun.test.database;

import redrun.database.Map;
import redrun.database.MapObjectDB;
import redrun.database.RedRunDAO;
import redrun.database.Character;

/**
 * Testing class for the RedRunDAO class
 * 
 * @author Jayson Grace ( jaysong@unm.edu )
 * @version 1.0
 * @since 2014-11-24
 *
 */
public class RedRunDAOTest
{
  private static final boolean DEBUG = true;
  private static RedRunDAO dao = new RedRunDAO();

  /**
   * Used to test the map INSERT functionality of the DAO
   * 
   * @return true if all tests pass
   */
  private boolean firstTest()
  {
    assert (RedRunDAO.insertMap("Ice World", "iceflats", "snow") == true) : "Unable to create new Map";
    boolean found = false;
    for (Map map : RedRunDAO.getAllMaps())
    {
      if (DEBUG) System.out.println(map.toString());
      if (map.toString().contains("Ice World")) found = true;
    }
    assert (found == true) : "Unable to find newly created map in Map table";
    return true;
  }

  /**
   * Used to test the map SELECT functionality of the DAO
   * 
   * @return true if all tests pass
   */
  private boolean secondTest()
  {
    if (DEBUG)
    {
      for (Map map : RedRunDAO.getAllMaps())
      {
        System.out.println(map.toString());
      }
    }
    assert (RedRunDAO.getAllMaps() != null) : "We were unable to retrieve any maps from the Map table";
    return true;
  }

  /**
   * Used to test the map DELETE functionality of the DAO
   * 
   * @return true if all tests pass
   */
  private boolean thirdTest()
  {
    assert (RedRunDAO.deleteMap("Ice World") == true) : "Something went horribly wrong with our deletion method";
    boolean found = false;
    for (Map map : RedRunDAO.getAllMaps())
    {
      if (DEBUG) System.out.println(map.toString());
      if (map.toString().contains("Ice World")) found = true;
    }
    assert (found == false) : "Ice World was not actually deleted from the database.";

    return true;
  }

  /**
   * Used to test the character INSERT functionality of the DAO
   * 
   * @return true if all tests pass
   */
  private boolean fourthTest()
  {
    assert (RedRunDAO.insertCharacter("Pally Jenkins", "Bdubz.png", "Doom Bringers", "50.0,12.2,23.2", 1)) == true : "Unable to create new Character";
    boolean found = false;
    for (Character c : RedRunDAO.getAllCharacters())
    {
      if (DEBUG) System.out.println(c.toString());
      if (c.toString().contains("Pally Jenkins")) found = true;
    }
    assert (found == true) : "Unable to find newly created character in Character table";
    return true;
  }

  /**
   * Used to test the character SELECT functionality of the DAO
   * 
   * @return true if all tests pass
   */
  private boolean fifthTest()
  {
    if (DEBUG)
    {
      for (Character c : RedRunDAO.getAllCharacters())
      {
        System.out.println(c.toString());
      }
    }
    assert (RedRunDAO.getAllCharacters() != null) : "We were unable to retrieve any characters from the Character table";
    return true;
  }

  /**
   * Used to test the character DELETE functionality of the DAO
   * 
   * @return true if all tests pass
   */
  private boolean sixthTest()
  {
    assert (RedRunDAO.deleteCharacter("Pally Jenkins") == true) : "Something went horribly wrong with our deletion method";
    boolean found = false;
    for (Character c : RedRunDAO.getAllCharacters())
    {
      if (DEBUG) System.out.println(c.toString());
      if (c.toString().contains("Pally Jenkins")) found = true;
    }
    assert (found == false) : "Pally was not actually deleted from the database.";

    return true;
  }

  /**
   * Used to test the map object INSERT functionality of the DAO
   * 
   * @return true if all tests pass
   */
  private boolean seventhTest()
  {
    assert (RedRunDAO.insertMapObject("Start", "(0.0f, 0.0f, 0.0f)", "brickwall5", "Direction.NORTH", 1)) == true : "Unable to create new MapObject";
    boolean found = false;
    for (MapObjectDB mo : RedRunDAO.getAllMapObjects())
    {
      if (DEBUG) System.out.println(mo.toString());
      if (mo.toString().contains("(0.0f, 0.0f, 0.0f)")) found = true;
    }
    assert (found == true) : "Unable to find newly created game object in MapObjects table";
    return true;
  }

  /**
   * Used to test the map object SELECT functionality of the DAO
   * 
   * @return true if all tests pass
   */
  private boolean eighthTest()
  {
    if (DEBUG)
    {
      for (MapObjectDB mo : RedRunDAO.getAllMapObjects())
      {
        System.out.println(mo.toString());
      }
    }
    assert (RedRunDAO.getAllMapObjects() != null) : "We were unable to retrieve any map objects from the MapObjects table";
    return true;
  }

  /**
   * Used to test the map object DELETE functionality of the DAO
   * 
   * @return true if all tests pass
   */
  private boolean ninthTest()
  {
    assert (RedRunDAO.deleteMapObject("(0.0f, 0.0f, 0.0f)") == true) : "Something went horribly wrong with our deletion method";
    boolean found = false;
    for (MapObjectDB mo : RedRunDAO.getAllMapObjects())
    {
      if (DEBUG) System.out.println(mo.toString());
      if (mo.toString().contains("(0.0f, 0.0f, 0.0f)")) found = true;
    }
    assert (found == false) : "The item with this location: (0.0f, 0.0f, 0.0f) was not actually deleted from the database.";

    return true;
  }

  /**
   * Main statement for test cases
   * 
   * @param args
   */
  public static void main(String[] args)
  {
    RedRunDAOTest redRunDaoTest = new RedRunDAOTest();
    // Map Tests
    if (redRunDaoTest.firstTest() == true) System.out.println("First test passed.");
    if (redRunDaoTest.secondTest() == true) System.out.println("Second test passed.");
    if (redRunDaoTest.thirdTest() == true) System.out.println("Third test passed.");
    // Character Tests
    if (redRunDaoTest.fourthTest() == true) System.out.println("Fourth test passed.");
    if (redRunDaoTest.fifthTest() == true) System.out.println("Five test passed.");
    if (redRunDaoTest.sixthTest() == true) System.out.println("Sixth test passed.");
    // MapObject Tests
    if (redRunDaoTest.seventhTest() == true) System.out.println("Seventh test passed.");
    if (redRunDaoTest.eighthTest() == true) System.out.println("Eighth test passed");
    if (redRunDaoTest.ninthTest() == true) System.out.println("Ninth test passed");
    dao.closeConnection();
  }
}
