package redrun.test.database;

import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

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

  /**
   * Used to test the character SELECT functionality of the DAO
   * 
   * @return true if all tests pass
   */
  private boolean firstTest()
  {
    RedRunDAO dao = new RedRunDAO();
    if (DEBUG)
    {
      for (Character c : RedRunDAO.getAllCharacters())
      {
        System.out.println(c.toString());
      }
    }
    assert (RedRunDAO.getAllCharacters() != null) : "We were unable to retrieve any characters from the Character table";
    dao.closeConnection();
    return true;
  }

  /**
   * Used to test the character INSERT functionality of the DAO
   * 
   * @return true if all tests pass
   */
  private boolean secondTest()
  {

    return true;
  }

  /**
   * Test specified Triangles for input
   * 
   * @return true if all tests pass
   */
  private boolean thirdTest()
  {

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
    if (redRunDaoTest.firstTest() == true) System.out.println("First test passed.");
    if (redRunDaoTest.secondTest() == true) System.out.println("Second test passed.");
    if (redRunDaoTest.thirdTest() == true) System.out.println("Third test passed.");

  }
}
