package redrun.database;

/**
 * Represent a Button from the RedRun DB
 * 
 * @author Jayson Grace
 * @version 1.0
 * @since 2014-11-30
 */
public class ButtonDB
{
  private int id;
  private boolean state = false;
  private int kioskId;
  private int trapId;
  private int mapId;

  /**
   * Button instantiation
   * 
   * @param state state of the button
   * @param kioskId associated kiosk
   * @param trapId associated trap
   * @param mapId associated map
   */
  public ButtonDB(int id, boolean state, int kioskId, int trapId, int mapId)
  {
    this.id = id;
    this.state = state;
    this.kioskId = kioskId;
    this.trapId = trapId;
    this.mapId = mapId;
  }

  /**
   * Convert object to string for network transmission
   */
  public String toString()
  {
    return "=== Button === " + "id:" + this.id + " State:" + this.state + " Kiosk Id:" + this.kioskId + " Trap Id:"
        + this.trapId + " Map Id:" + this.mapId + " ===";
  }
}
