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
   * Insert new map into Maps table
   * 
   * @param mapName name of map
   * @return true if map created, false otherwise
   */
  public static boolean insertMap(String mapName)
  {
    try
    {
      String sql = "INSERT INTO maps(map_name) VALUES (?)";

      /** F**k SQL Injections. */
      PreparedStatement pstmt = c.prepareStatement(sql);
      pstmt.setString(1, mapName);
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
   * Insert new character into the Character table
   * 
   * @param characterName name of new character
   * @param image image associated with new character
   * @param team team of new character
   * @param startLocation starting location of new character
   * @return true if character created, otherwise false
   */
  public static boolean insertCharacter(String characterName, String image, String team, String startLocation, int mapId)
  {
    try
    {
      String sql = "INSERT INTO characters(character_name, image, team, start_loc, map_id) VALUES (?, ?, ?, ?, ?)";

      /** F**k SQL Injections. */
      PreparedStatement pstmt = c.prepareStatement(sql);
      pstmt.setString(1, characterName);
      pstmt.setString(2, image);
      pstmt.setString(3, team);
      pstmt.setString(4, startLocation);
      pstmt.setInt(5, mapId);
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
   * Get a list of all maps in the Map table
   * 
   * @return list of all items in the Map database
   */
  public static ArrayList<Map> getAllMaps()
  {
    ArrayList<Map> mapList = new ArrayList<Map>();
    try
    {
      Statement sqlStatement = c.createStatement();

      ResultSet rs = sqlStatement.executeQuery("SELECT * FROM maps;");
      while (rs.next())
      {
        int id = rs.getInt("id");
        String mapName = rs.getString("map_name");
        Map map = new Map(id, mapName);

        mapList.add(map);
        if (DEBUG)
        {
          System.out.println(id);
          System.out.println(mapName);
        }
      }
      rs.close();
      sqlStatement.close();
      return mapList;
    }
    catch (SQLException e)
    {
      System.err.println(e.getClass().getName() + ": " + e.getMessage());
      System.exit(0);
    }
    return null;
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
        int mapId = rs.getInt("map_id");
        Character character = new Character(id, characterName, image, team, startLocation, mapId);

        characterList.add(character);
        if (DEBUG)
        {
          System.out.println(id);
          System.out.println(characterName);
          System.out.println(image);
          System.out.println(team);
          System.out.println(startLocation);
          System.out.println(mapId);
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
   * Delete a specified map out of the Map table
   * 
   * @param mapName name of map to delete
   * @return true if successfully deleted, false otherwise
   */
  public static boolean deleteMap(String mapName)
  {
    try
    {
      Statement sqlStatement = c.createStatement();
      String sql = "DELETE FROM maps WHERE map_name = " + "'" + mapName + "'";

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