package redrun.model.gameobject.map;

import redrun.model.constants.Direction;
import redrun.model.gameobject.GameObject;
import redrun.model.gameobject.trap.Trap;

public abstract class Map extends GameObject
{
	/** The orientation of the map object in 3D space. */
	private Direction orientation = null;
	
	/** The trap associated with the map object. */
	private Trap trap = null;
	
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
