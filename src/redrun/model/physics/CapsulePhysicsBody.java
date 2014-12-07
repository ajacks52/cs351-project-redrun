package redrun.model.physics;

import javax.vecmath.Quat4f;

import org.lwjgl.util.vector.Vector3f;

import com.bulletphysics.collision.shapes.CapsuleShape;
import com.bulletphysics.collision.shapes.SphereShape;

public class CapsulePhysicsBody extends PhysicsBody
{
  /**
   * Creates a capsule physics body
   * 
   * @param radius in meters
   * @param height
   */
  public CapsulePhysicsBody(Vector3f center, float radius, float mass, float height)
  {
    super(mass, new Quat4f(0, 0, 0, 1), center, new CapsuleShape(radius, height));

    body.setSleepingThresholds(0, 0);
    body.setAngularFactor(0);
  }
}
