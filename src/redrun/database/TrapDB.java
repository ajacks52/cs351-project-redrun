package redrun.database;

/**
 * Represent a Trap from the RedRun DB
 * 
 * @author Jayson Grace
 * @version 1.0
 * @since 2014-11-28
 */
public class TrapDB
{
  private int id;
  private String trapType;
  private int kioskId;
  private int mapId;

  /**
   * Trap instantiation
   * 
   * @param id database id
   * @param trapType type of trap
   * @param kioskId kiosk associated with the trap
   * @param mapId map associated with the trap
   */
  public TrapDB(int id, String trapType, int kioskId, int mapId)
  {
    this.id = id;
    this.trapType = trapType;
    this.kioskId = kioskId;
    this.mapId = mapId;
  }

  /**
   * Convert object to string for network transmission
   */
  public String toString()
  {
    return "=== Trap === " + "id:" + this.id + " Trap Type:" + this.trapType + " Kiosk Id:" + this.kioskId + " Map Id:"
        + this.mapId + " ===";
  }

  /**
   * Get id of trap
   * 
   * @return trap id
   */
  public int getId()
  {
    return id;
  }
}