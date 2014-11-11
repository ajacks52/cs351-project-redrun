/**
 * 
 */
package redrun.model.physics;

import javax.vecmath.Matrix4f;
import javax.vecmath.Quat4f;
import javax.vecmath.Vector3f;

import com.bulletphysics.collision.shapes.ConvexHullShape;
import com.bulletphysics.dynamics.RigidBody;
import com.bulletphysics.linearmath.DefaultMotionState;
import com.bulletphysics.linearmath.Transform;
import com.bulletphysics.util.ObjectArrayList;


/**
 * @author jem
 *
 */
public class ConvexRigidBody extends RigidBody
{
  public ConvexRigidBody(Vector3f[] points, Vector3f center, float mass)
  {
    super(mass, 
        new DefaultMotionState(
            new Transform(
                new Matrix4f(
                    new Quat4f(0,0,0,1), center, 1))), 
        new ConvexHullShape(new ObjectArrayList<Vector3f>(points.length)));
    for (Vector3f vec : points)
    {
      ((ConvexHullShape) this.getCollisionShape()).addPoint(vec);
    }
  }
}
