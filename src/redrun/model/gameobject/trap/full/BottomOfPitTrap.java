package redrun.model.gameobject.trap.full;

import java.awt.Dimension;

import javax.vecmath.Quat4f;

import org.lwjgl.util.vector.Vector3f;

import redrun.model.constants.CollisionTypes;
import redrun.model.constants.Direction;
import redrun.model.gameobject.trap.Trap;
import redrun.model.physics.BoxPhysicsBody;

public class BottomOfPitTrap extends Trap
{

  public BottomOfPitTrap(float x, float y, float z, Direction orientation, Dimension dim)
  {
    super(x, y, z, orientation, null);
    this.body = new BoxPhysicsBody(new Vector3f(x, y - 15, z), new Vector3f((float) (dim.width / 3), 3.0f,
        (float) (dim.height / 3)), new Quat4f(), 0.0f, CollisionTypes.INSTANT_DEATH_COLLISION_TYPE);
  }

}
