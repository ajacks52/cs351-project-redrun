package redrun.model.gameobject.map;

import redrun.model.constants.Direction;
import redrun.model.gameobject.trap.Trap;

public class Platform extends Map
{
	public Platform(float x, float y, float z, String textureName, Direction orientation, Trap trap)
	{
		super(x, y, z, textureName, orientation, trap);
		
		
	}
}
