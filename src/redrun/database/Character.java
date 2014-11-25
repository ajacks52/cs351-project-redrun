package redrun.database;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Represent a Character from the RedRun DB
 * 
 * @author Jayson Grace
 * @version 1.0
 * @since 2014-11-24
 */
public class Character
{
  private int id;
  private String characterName;
  private String image;
  private String team;
  private StartLocation parsedStart;

  /**
   * Character instantiation
   * 
   * @param id database id
   * @param characterName default database character name
   * @param image path to image associated with this character
   * @param team team this character is associated with
   * @param startLocation starting location of this player
   */
  public Character(int id, String characterName, String image, String team, String startLocation)
  {
    this.id = id;
    this.characterName = characterName;
    this.image = image;
    this.team = team;
    this.parsedStart = new StartLocation(startLocation);
  }

  /**
   * Convert object to string for network transmission
   */
  public String toString()
  {
    return "=== Character === " + "ID:" + this.id + " Name:" + this.characterName + " Image:" + this.image + " Team:"
        + this.team + " Start Location:" + parsedStart.getX() + ", " + parsedStart.getY() + ", " + parsedStart.getZ()
        + " ===";
  }

  /**
   * Convert start location from string to floats that can be used as
   * coordinates
   * 
   * @author Jayson Grace
   * @version 1.0
   * @since 2014-11-24
   */
  private class StartLocation
  {
    private float x, y, z;

    private StartLocation(String startLocation)
    {
      Pattern parsed = Pattern.compile("(\\d+\\.\\d+),(\\d+\\.\\d+),(\\d+\\.\\d+)");
      Matcher matchParsed = parsed.matcher(startLocation);
      if (matchParsed.find())
      {
        this.x = Float.parseFloat(matchParsed.group(1));
        this.y = Float.parseFloat(matchParsed.group(2));
        this.z = Float.parseFloat(matchParsed.group(3));
      }
    }

    /**
     * Getter for x
     * 
     * @return x value
     */
    private float getX()
    {
      return x;
    }

    /**
     * Getter for y
     * 
     * @return y value
     */
    private float getY()
    {
      return y;
    }

    /**
     * Getter for z
     * 
     * @return z value
     */
    private float getZ()
    {
      return z;
    }
  }
}
