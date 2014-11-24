package redrun.model.gameobject.map;

import redrun.model.constants.Direction;
import redrun.model.gameobject.trap.Trap;

/**
 * This class represents a map object that is used to construct Redrun maps.
 * This particular class is a field which has a larger surface area than other
 * map objects.
 * 
 * @author Troy Squillaci
 * @version 1.0
 * @since 2014-11-22
 */
public class Field extends Map
{
	/**
   * Creates a new field at the specified location. If the texture name is not null, the specified
   * texture will be applied to the field. In addition the orientation of the field and the trap
   * associated with it are specified here. Trap may be null.
   * 
   * @param x the x position of the field
   * @param y the y position of the field
   * @param z the z position of the field
   * @param textureName the name of the texture to apply to the field
   * @param orientation the cardinal direction that field should be aligned to
   * @param trap the trap to place on the field
   */
	public Field(float x, float y, float z, String textureName, Direction orientation, Trap trap)
	{
		super(x, y, z, textureName, orientation, trap);
		// TODO Auto-generated constructor stub
	}
}
