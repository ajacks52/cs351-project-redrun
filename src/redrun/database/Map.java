package redrun.database;

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
