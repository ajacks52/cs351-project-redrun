package redrun.database;

/**
 * Represent a Kiosk from the RedRun DB
 * 
 * @author Jayson Grace
 * @version 1.0
 * @since 2014-11-28
 */
public class Kiosk
{
  private int id;
  // private String location;
  private String cooldown;
  private int mapId;
  private StartLocation parsedLocation;

  /**
   * Kiosk instantiation
   * 
   * @param id database id
   * @param location location of kiosk on map
   * @param cooldown cooldown time for kiosk
   * @param mapId
   */
  public Kiosk(int id, String location, String cooldown, int mapId)
  {
    this.id = id;
    // this.location = location;
    this.cooldown = cooldown;
    this.mapId = mapId;
    this.parsedLocation = new StartLocation(location);
  }

  /**
   * Convert object to string for network transmission
   */
  public String toString()
  {
    return "=== Kiosk === " + "id:" + this.id + " Location:" + parsedLocation.getX() + ", " + parsedLocation.getY()
        + ", " + parsedLocation.getZ() + " Cooldown:" + this.cooldown + " Map Id:" + this.mapId + " ===";
  }

  /**
   * Get id of kiosk
   * 
   * @return kiosk id
   */
  public int getId()
  {
    return id;
  }
}