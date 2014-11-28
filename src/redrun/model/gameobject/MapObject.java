package redrun.model.gameobject;

import java.util.ArrayList;
import java.util.List;

import redrun.model.constants.Direction;
import redrun.model.gameobject.trap.Trap;

/**
 * This class represents a map object that is used to construct Redrun maps.
 * 
 * @author Troy Squillaci
 * @version 1.0
 * @since 2014-11-22
 */
public abstract class MapObject implements Comparable<MapObject>
{  
  /** The X position of the map object. */
  protected final float x;
  
  /** The Y position of the map object. */
  protected final float y;
  
  /** The Z position of the map object. */
  protected final float z;
  
  /** The orientation of the map object in 3D space defined by cardinal directions. */
  protected final Direction orientation;

  // Optional parameters...
  /** The name of the texture to apply to the map object. */
  protected String textureName = null;
  
  /** The trap to associate with the map object. */
  protected Trap trap = null;
  
  /** The list of game object that define the map object. */
  protected List<GameObject> components = new ArrayList<GameObject>();
  
  public MapObject(float x, float y, float z, String textureName, Direction orientation, Trap trap)
  {
    this.x = x;
    this.y = y;
    this.z = z;
    this.orientation = orientation;
    this.trap = trap;
  }
  
  /**
   * Gets the X position of the map object.
   * 
   * @return the X position of the map object
   */
  public float getX()
  {
    return x;
  }
  
  /**
   * Gets the Y position of the map object.
   * 
   * @return the Y position of the map object
   */
  public float getY()
  {
    return y;
  }
  
  /**
   * Gets the Z position of the map object.
   * 
   * @return the Z position of the map object
   */
  public float getZ()
  {
    return z;
  }
  
  /**
   * Draws the map object to the OpenGL scene.
   */
  public void draw()
  {
    for (GameObject component : components)
    {
      component.draw();
    }
  }
}
