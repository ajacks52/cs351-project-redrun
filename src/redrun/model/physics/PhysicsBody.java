/**
 * 
 */
package redrun.model.physics;

import javax.vecmath.Matrix4f;
import javax.vecmath.Quat4f;

import org.lwjgl.util.vector.Vector3f;

import com.bulletphysics.collision.shapes.CollisionShape;
import com.bulletphysics.dynamics.RigidBody;
import com.bulletphysics.linearmath.DefaultMotionState;
import com.bulletphysics.linearmath.MotionState;
import com.bulletphysics.linearmath.Transform;

/**
 * @author jem
 *
 */
public class PhysicsBody extends RigidBody
{

  public PhysicsBody(float mass, Quat4f direction, Vector3f center,
      CollisionShape collisionShape)
  {
    super(mass, 
        new DefaultMotionState(
            new Transform(
                new Matrix4f(direction,PhysicsTools.openGLToBullet(center), 1))), 
        collisionShape);
    // TODO Auto-generated constructor stub
  }
  
}
