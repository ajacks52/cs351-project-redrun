package redrun.database;

/**
 * Represent a Map from the RedRun DB
 * 
 * @author Jayson Grace
 * @version 1.0
 * @since 2014-11-25
 */
public class Map
{
  private int id;
  private String mapName;

  /**
   * Map instantiation
   * 
   * @param id database id
   * @param mapName name of the map
   */
  public Map(int id, String mapName)
  {
    this.id = id;
    this.mapName = mapName;
  }

  /**
   * Convert object to string for network transmission
   */
  public String toString()
  {
    return "=== Map === " + "ID:" + this.id + " Name:" + this.mapName + " ===";
  }
}
