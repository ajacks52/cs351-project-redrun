package redrun.model.gameobject.world;

import redrun.model.gameobject.GameObject;
import redrun.model.noninteractable.Noninteractable;

/**
 * This class represents a static world object.
 * 
 * @author Troy Squillaci, Jake Nichol
 * @version 1.0
 * @since 2014-11-07
 */
public abstract class WorldObject extends GameObject implements Noninteractable
{
  /**
   * Creates a new static world object at the specified position.
   * 
   * @param x the x position of the world object
   * @param y the y position of the world object
   * @param z the z position of the world object
   */
  public WorldObject(float x, float y, float z)
  {
    super(x, y, z);
    // TODO Auto-generated constructor stub
  }

}
