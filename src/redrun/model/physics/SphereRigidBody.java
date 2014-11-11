/**
 * 
 */
package redrun.model.physics;

import javax.vecmath.Matrix4f;
import javax.vecmath.Quat4f;
import javax.vecmath.Vector3f;

import com.bulletphysics.collision.shapes.SphereShape;
import com.bulletphysics.dynamics.RigidBody;
import com.bulletphysics.linearmath.DefaultMotionState;
import com.bulletphysics.linearmath.Transform;

/**
 * @author jem
 *
 */
public class SphereRigidBody extends RigidBody
{
  public SphereRigidBody(Vector3f center, float radius, float mass)
  {
    super(mass, 
        new DefaultMotionState(
            new Transform(
                new Matrix4f(new Quat4f(0,0,0,1),center,1.0f))), 
        new SphereShape(radius));
  }
}
