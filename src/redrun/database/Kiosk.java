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
  private String location;
  private String cooldown;
  private int mapId;

  /**
   * Kiosk instantiation
   * 
   * @param id database id
   * @param KioskName default database Kiosk name
   * @param image path to image associated with this Kiosk
   * @param team team this Kiosk is associated with
   * @param startLocation starting location of this player
   */
  public Kiosk(int id, String location, String cooldown, int mapId)
  {
    this.id = id;
    this.location = location;
    this.cooldown = cooldown;
    this.mapId = mapId;
  }

  /**
   * Convert object to string for network transmission
   */
  public String toString()
  {
    return "=== Kiosk === " + "ID:" + this.id + " Location:" + this.location + " Cooldown:" + this.cooldown +" Map ID:" + this.mapId + " ===";
  }
}