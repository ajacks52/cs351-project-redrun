/**
 * 
 */
package redrun.model.physics;

import javax.vecmath.Matrix4f;
import javax.vecmath.Quat4f;

import org.lwjgl.util.vector.Vector3f;

import com.bulletphysics.collision.shapes.StaticPlaneShape;
import com.bulletphysics.dynamics.RigidBody;
import com.bulletphysics.linearmath.DefaultMotionState;
import com.bulletphysics.linearmath.Transform;

/**
 * @author jem
 *
 */
public class PlaneRigidBody extends PhysicsBody
{
  public PlaneRigidBody(Vector3f center, Vector3f normal, float mass)
  {
    super(mass, new Quat4f(0,0,0,1), center, 
        new StaticPlaneShape(PhysicsTools.openGLToBullet(normal), 1)); // im not sure what the "1" represents
  }
}
