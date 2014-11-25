package redrun.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * DAO used to facilitate all database interactions in the RedRun Database
 * 
 * @author Jayson Grace
 * @version 1.0
 * @since 2014-11-24
 */
public class RedRunDAO
{
  private static final boolean DEBUG = false;
  private static Connection c;

  /**
   * RedRunDAO instantiation
   */
  public RedRunDAO()
  {
    c = RedRunDAO.connectToDB();
  }

  /**
   * Generate a connectino to the database
   * 
   * @return the established connection
   */
  public static Connection connectToDB()
  {
    Connection c = null;
    try
    {
      Class.forName("org.sqlite.JDBC");
      c = DriverManager.getConnection("jdbc:sqlite:src/redrun/database/redRun.db");
    }
    catch (Exception e)
    {
      System.err.println(e.getClass().getName() + ": " + e.getMessage());
      System.exit(0);
    }
    System.out.println("Opened database successfully");
    return c;
  }

  /**
   * Close connection to the database
   */
  public void closeConnection()
  {
    try
    {
      c.close();
    }
    catch (Exception e)
    {
      System.err.println(e.getClass().getName() + ": " + e.getMessage());
      System.exit(0);
    }
  }

  /**
   * Get a list of all characters in the Character table
   * 
   * @return list of all items in the Characters database
   */
  public static ArrayList<Character> getAllCharacters()
  {
    ArrayList<Character> characterList = new ArrayList<Character>();
    try
    {
      Statement sqlStatement = c.createStatement();

      ResultSet rs = sqlStatement.executeQuery("SELECT * FROM characters;");
      while (rs.next())
      {
        int id = rs.getInt("id");
        String characterName = rs.getString("character_name");
        String image = rs.getString("image");
        String team = rs.getString("team");
        String startLocation = rs.getString("start_loc");
        Character character = new Character(id, characterName, image, team, startLocation);

        characterList.add(character);
        if (DEBUG)
        {
          System.out.println(id);
          System.out.println(characterName);
          System.out.println(image);
          System.out.println(team);
          System.out.println(startLocation);
        }
      }
      rs.close();
      sqlStatement.close();
      return characterList;
    }
    catch (SQLException e)
    {
      System.err.println(e.getClass().getName() + ": " + e.getMessage());
      System.exit(0);
    }
    return null;
  }

  /**
   * Insert new character into the Character table
   * 
   * @param characterName name of new character
   * @param image image associated with new character
   * @param team team of new character
   * @param startLocation starting location of new character
   * @return true if character created, otherwise false
   */
  public static boolean insertCharacter(String characterName, String image, String team, String startLocation)
  {
    try
    {
      String sql = "INSERT INTO characters(character_name, image, team, start_loc) VALUES (?, ?, ?, ?)";

      /** F**k SQL Injections. */
      PreparedStatement pstmt = c.prepareStatement(sql);
      pstmt.setString(1, characterName);
      pstmt.setString(2, image);
      pstmt.setString(3, team);
      pstmt.setString(4, startLocation);
      pstmt.executeUpdate();
      pstmt.close();
      return true;
    }
    catch (SQLException e)
    {
      System.err.println(e.getClass().getName() + ": " + e.getMessage());
      System.exit(0);
    }
    return false;
  }

  /**
   * Delete a specified character out of the Character table
   * 
   * @param characterName name of character to delete
   * @return true if successfully deleted, false otherwise
   */
  public static boolean deleteCharacter(String characterName)
  {
    try
    {
      Statement sqlStatement = c.createStatement();
      String sql = "DELETE FROM characters WHERE character_name = " + "'" + characterName + "'";

      sqlStatement.execute(sql);
      sqlStatement.close();
      return true;
    }
    catch (SQLException e)
    {
      System.err.println(e.getClass().getName() + ": " + e.getMessage());
      System.exit(0);
    }
    return false;
  }
}
