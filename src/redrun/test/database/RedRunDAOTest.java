package redrun.test.database;

import redrun.database.ButtonDB;
import redrun.database.Kiosk;
import redrun.database.Map;
import redrun.database.MapObjectDB;
import redrun.database.RedRunDAO;
import redrun.database.Character;
import redrun.database.TrapDB;

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
  private boolean insertMap()
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
  private boolean selectMap()
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
  private boolean deleteMap()
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
  private boolean insertCharacter()
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
  private boolean selectCharacter()
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
  private boolean deleteCharacter()
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
  private boolean insertMapObject()
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
  private boolean selectMapObject()
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
  private boolean deleteMapObject()
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
   * Used to test the kiosk INSERT functionality of the DAO
   * 
   * @return true if all tests pass
   */
  private boolean insertKiosk()
  {
    assert (RedRunDAO.insertKiosk("0.0f, 0.10f, 0.5f", "200.01", 1) == true) : "Unable to create new Kiosk";
    boolean found = false;
    for (Kiosk kiosk : RedRunDAO.getAllKiosks())
    {
      if (DEBUG) System.out.println(kiosk.toString());
      if (kiosk.toString().contains("200.01")) found = true;
    }
    assert (found == true) : "Unable to find newly created kiosk in Kiosk table";
    return true;
  }

  /**
   * Used to test the kiosk SELECT functionality of the DAO
   * 
   * @return true if all tests pass
   */
  private boolean selectKiosk()
  {
    if (DEBUG)
    {
      for (Kiosk kiosk : RedRunDAO.getAllKiosks())
      {
        System.out.println(kiosk.toString());
      }
    }
    assert (RedRunDAO.getAllKiosks() != null) : "We were unable to retrieve any kiosks from the Kiosk table";
    return true;
  }

  /**
   * Used to test the trap INSERT functionality of the DAO
   * 
   * @return true if all tests pass
   */
  private boolean insertTrap()
  {
    assert (RedRunDAO.insertTrap("spikey", 1, 1) == true) : "Unable to create new Trap";
    boolean found = false;
    for (TrapDB trap : RedRunDAO.getAllTraps())
    {
      if (DEBUG) System.out.println(trap.toString());
      if (trap.toString().contains("spikey")) found = true;
    }
    assert (found == true) : "Unable to find newly created trap in Trap table";
    return true;
  }

  /**
   * Used to test the trap SELECT functionality of the DAO
   * 
   * @return true if all tests pass
   */
  private boolean selectTrap()
  {
    if (DEBUG)
    {
      for (TrapDB trap : RedRunDAO.getAllTraps())
      {
        System.out.println(trap.toString());
      }
    }
    assert (RedRunDAO.getAllTraps() != null) : "We were unable to retrieve any traps from the Trap table";
    return true;
  }

  private boolean insertButton()
  {
    assert (RedRunDAO.insertButton(false, 1, 1, 1) == true) : "Unable to create new Button";
    boolean found = false;
    for (ButtonDB button : RedRunDAO.getAllButtons())
    {
      if (DEBUG) System.out.println(button.toString());
      if (button.toString().contains("false")) found = true;
    }
    assert (found == true) : "Unable to find newly created button in Button table";
    return true;
  }

  /**
   * Used to test the trap SELECT functionality of the DAO
   * 
   * @return true if all tests pass
   */
  private boolean selectButton()
  {
    if (DEBUG)
    {
      for (ButtonDB button : RedRunDAO.getAllButtons())
      {
        System.out.println(button.toString());
      }
    }
    assert (RedRunDAO.getAllButtons() != null) : "We were unable to retrieve any buttons from the Button table";
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
    if (redRunDaoTest.insertMap() == true) System.out.println("Insert map test passed.");
    if (redRunDaoTest.selectMap() == true) System.out.println("Select map test passed.");
    if (redRunDaoTest.deleteMap() == true) System.out.println("Delete map test passed.");
    // Character Tests
    if (redRunDaoTest.insertCharacter() == true) System.out.println("Insert character test passed.");
    if (redRunDaoTest.selectCharacter() == true) System.out.println("Select character test passed.");
    if (redRunDaoTest.deleteCharacter() == true) System.out.println("Delete character test passed.");
    // MapObject Tests
    if (redRunDaoTest.insertMapObject() == true) System.out.println("Seventh test passed.");
    if (redRunDaoTest.selectMapObject() == true) System.out.println("Eighth test passed.");
    if (redRunDaoTest.deleteMapObject() == true) System.out.println("Ninth test passed.");
    // Kiosk Tests
    if (redRunDaoTest.insertKiosk() == true) System.out.println("Insert kiosk test passed.");
    if (redRunDaoTest.selectKiosk() == true) System.out.println("Select kiosk test passed.");
    // Trap Tests
    if (redRunDaoTest.insertTrap() == true) System.out.println("Insert trap test passed.");
    if (redRunDaoTest.selectTrap() == true) System.out.println("Select trap test passed.");
    // Button Tests
    if (redRunDaoTest.insertButton() == true) System.out.println("Insert button test passed.");
    if (redRunDaoTest.selectButton() == true) System.out.println("Select button test passed.");

    dao.closeConnection();
  }
}
