package redrun.database;

public class MapObjectDB
{
  private int id;
  private String objectName;
  private String location;
  private String texture;
  private String direction;
  private int mapId;
  private StartLocation parsedStart;

  public MapObjectDB(int id, String objectName, String location, String texture, String direction, int mapId)
  {
    this.id = id;
    this.objectName = objectName;
    this.location = location;
    this.texture = texture;
    this.direction = direction;
    this.mapId = mapId;
    this.parsedStart = new StartLocation(location);
  }

  /**
   * Convert object to string for network transmission
   */
  public String toString()
  {
    return "=== Map Object === " + "ID:" + this.id + " Name:" + this.objectName + " Location:" + this.location
      + " Texture:" + this.texture + " Direction:" + this.direction + " Map ID:" + this.mapId + " ===";
//    return "=== Map Object === " + "ID:" + this.id + " Name:" + this.objectName + " Location:" + parsedStart.getX() + ", " + parsedStart.getY() + ", "
//  + parsedStart.getZ() + " Texture:" + this.texture
//        + " Direction:" + this.direction + " Map ID:" + this.mapId + " ===";
  }
}
