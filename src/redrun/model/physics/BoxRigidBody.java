package redrun.model.physics;

import javax.vecmath.Quat4f;

import org.lwjgl.util.vector.Vector3f;

import com.bulletphysics.collision.shapes.BoxShape;
public class BoxRigidBody extends PhysicsBody
{
  public BoxRigidBody(Vector3f center, Vector3f radiuses, Quat4f direction, float mass)
  {
    super(mass, direction, center, new BoxShape(PhysicsTools.openGLToBullet(radiuses)));
  }
}
