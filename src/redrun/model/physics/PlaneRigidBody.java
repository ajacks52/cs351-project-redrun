/**
 * 
 */
package redrun.model.physics;

import javax.vecmath.Matrix4f;
import javax.vecmath.Quat4f;
import javax.vecmath.Vector3f;

import com.bulletphysics.collision.shapes.StaticPlaneShape;
import com.bulletphysics.dynamics.RigidBody;
import com.bulletphysics.linearmath.DefaultMotionState;
import com.bulletphysics.linearmath.Transform;

/**
 * @author jem
 *
 */
public class PlaneRigidBody extends RigidBody
{
  public PlaneRigidBody(Vector3f center, Vector3f normal, float mass)
  {
    super(mass, 
        new DefaultMotionState(
            new Transform(
                new Matrix4f(
                    new Quat4f(0,0,0,1), center, 1))), 
        new StaticPlaneShape(normal, 1));
  }
}
