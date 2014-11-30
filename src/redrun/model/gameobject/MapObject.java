package redrun.model.gameobject;

import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;

import redrun.model.constants.Constants;
import redrun.model.constants.Direction;
import redrun.model.constants.TrapType;
import redrun.model.gameobject.trap.DeathPillar;
import redrun.model.gameobject.trap.JailDoor;
import redrun.model.gameobject.trap.PoleDance;
import redrun.model.gameobject.trap.PoleWall;
import redrun.model.gameobject.trap.RockSmash;
import redrun.model.gameobject.trap.SpikeField;
import redrun.model.gameobject.trap.SpikeTrapDoor;
import redrun.model.gameobject.trap.Trap;
import redrun.test.GraphicsTestTroy;

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
  
  public MapObject(float x, float y, float z, String textureName, Direction orientation, TrapType type)
  {
    this.x = x;
    this.y = y;
    this.z = z;
    this.orientation = orientation;
    
    switch (type)
    {
      case EMPTY:
      {
      	break;
      }
      case JAIL_DOOR:
      {
      	this.trap = new JailDoor(x, y, z, orientation, null);
      	break;
      }
      case SPIKE_FIELD:
      {
      	this.trap = new SpikeField(x, y, z, "s11", new Dimension(10, 15), true);
      	break;
      }
      case SPIKE_TRAP_DOOR:
      {
      	this.trap = new SpikeTrapDoor(x, y, z, textureName);
      	break;
      }
      case POLE_WALL:
      {
      	this.trap = new PoleWall(x, y, z, textureName);
      	break;
      }
      case POLE_DANCE:
      {
      	this.trap = new PoleDance(x, y, z, textureName);
      	break;
      }
      case DEATH_PILLAR:
      {
      	this.trap = new DeathPillar(x, y, z, textureName);
      	break;
      }
      case ROCK_SMASH:
      {
      	this.trap = new RockSmash(x, y, z, "rock" + Constants.random.nextInt(3));
      	break;
      }
      default:
      {
      	try
      	{
      		throw new IllegalArgumentException();
      	}
      	catch (IllegalArgumentException ex)
      	{
      		ex.printStackTrace();
      	}
      }
    }
    
    if (type != TrapType.EMPTY)
    {
      GraphicsTestTroy.data.addGameObject(trap);
    }
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
