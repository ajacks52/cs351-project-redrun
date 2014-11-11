package redrun.model.physics;

import javax.vecmath.Matrix4f;
import javax.vecmath.Quat4f;
import javax.vecmath.Vector3f;

import com.bulletphysics.collision.shapes.BoxShape;
import com.bulletphysics.dynamics.RigidBody;
import com.bulletphysics.linearmath.DefaultMotionState;
import com.bulletphysics.linearmath.Transform;

public class BoxRigidBody extends RigidBody
{
  public BoxRigidBody(Vector3f center, Vector3f radiuses, Quat4f direction, float mass)
  {
    super(mass, 
        new DefaultMotionState(
            new Transform(new Matrix4f(direction,center,1.0f))), 
        new BoxShape(radiuses));
  }
}
