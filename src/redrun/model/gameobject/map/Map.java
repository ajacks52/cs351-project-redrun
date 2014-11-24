package redrun.model.gameobject.map;

import redrun.model.constants.Direction;
import redrun.model.gameobject.GameObject;
import redrun.model.gameobject.trap.Trap;

/**
 * This class represents a map object that is used to construct Redrun maps.
 * 
 * @author Troy Squillaci
 * @version 1.0
 * @since 2014-11-22
 */
public abstract class Map extends GameObject
{
	/** The orientation of the map object in 3D space. */
	private Direction orientation = null;
	
	/** The trap associated with the map object. */
	private Trap trap = null;
	
	/**
   * Creates a new map object at the specified location. If the texture name is not null, the specified
   * texture will be applied to the map object. In addition the orientation of the map object and the trap
   * associated with it are specified here. Trap may be null.
   * 
   * @param x the x position of the map object
   * @param y the y position of the map object
   * @param z the z position of the map object
   * @param textureName the name of the texture to apply to the map object
   * @param orientation the cardinal direction that map object should be aligned to
   * @param trap the trap to place on the map object
   */
	public Map(float x, float y, float z, String textureName, Direction orientation, Trap trap)
	{
		super(x, y, z, textureName);

		this.orientation = orientation;
	}

	@Override
	public void interact()
	{
		// TODO Auto-generated method stub
	}

	@Override
	public void update()
	{
		// TODO Auto-generated method stub
	}

	@Override
	public void reset()
	{
		// TODO Auto-generated method stub
	}
}
