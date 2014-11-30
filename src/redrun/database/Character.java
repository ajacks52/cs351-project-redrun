package redrun.database;

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
  private int mapId;
  private StartLocation parsedStart;

  /**
   * Character instantiation
   * 
   * @param id database id
   * @param characterName default database character name
   * @param image path to image associated with this character
   * @param team team this character is associated with
   * @param startLocation starting location of this player
   * @param mapId the associated map
   */
  public Character(int id, String characterName, String image, String team, String startLocation, int mapId)
  {
    this.id = id;
    this.characterName = characterName;
    this.image = image;
    this.team = team;
    this.mapId = mapId;
    this.parsedStart = new StartLocation(startLocation);
  }

  /**
   * Convert object to string for network transmission
   */
  public String toString()
  {
    return "=== Character === " + "id:" + this.id + " Name:" + this.characterName + " Image:" + this.image + " Team:"
        + this.team + " Start Location:" + parsedStart.getX() + ", " + parsedStart.getY() + ", " + parsedStart.getZ()
        + " Map Id:" + this.mapId + " ===";
  }
}
