/**
 * 
 */
package redrun.model.physics;

import javax.vecmath.Matrix4f;
import javax.vecmath.Quat4f;

import org.lwjgl.util.vector.Vector3f;

import com.bulletphysics.collision.shapes.SphereShape;
import com.bulletphysics.dynamics.RigidBody;
import com.bulletphysics.linearmath.DefaultMotionState;
import com.bulletphysics.linearmath.Transform;

/**
 * @author jem
 *
 */
public class SphereRigidBody extends PhysicsBody
{
  public SphereRigidBody(Vector3f center, float radius, float mass)
  {
    super(mass, new Quat4f(0,0,0,1), center, new SphereShape(radius));
  }
}
