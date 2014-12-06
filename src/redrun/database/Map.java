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
  private String skyBox;
  private String floor;
  private String lightPosition;

  /**
   * Map instantiation
   * 
   * @param id database id
   * @param mapName name of map
   * @param skyBox skybox file used
   * @param floor floor file used
   * @param lightPosition position of light
   */
  public Map(int id, String mapName, String skyBox, String floor, String lightPosition)
  {
    this.id = id;
    this.mapName = mapName;
    this.skyBox = skyBox;
    this.floor = floor;
    this.lightPosition = lightPosition;
  }

  /**
   * Convert object to string for network transmission
   */
  public String toString()
  {
    return "=== Map === " + "ID:" + this.id + " Name:" + this.mapName + " SkyBox:" + this.skyBox + " Floor:"
        + this.floor + " Light Position:" + this.lightPosition + " ===";
  }

  /**
   * Get id of a map object
   * 
   * @return id of map
   */
  public int getId()
  {
    return id;
  }

  /**
   * Get name of a map object
   * 
   * @return name of map
   */
  public String getMapName()
  {
    return mapName;
  }
}
