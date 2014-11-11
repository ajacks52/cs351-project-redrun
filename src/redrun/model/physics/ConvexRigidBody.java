/**
 * 
 */
package redrun.model.physics;

import javax.vecmath.Quat4f;

import org.lwjgl.util.vector.Vector3f;

import com.bulletphysics.collision.shapes.ConvexHullShape;
import com.bulletphysics.util.ObjectArrayList;


/**
 * @author jem
 *
 */
public class ConvexRigidBody extends PhysicsBody
{
  public ConvexRigidBody(Vector3f[] points, Vector3f center, float mass)
  {
    super(mass, new Quat4f(0,0,0,1), center,
        new ConvexHullShape(new ObjectArrayList<javax.vecmath.Vector3f>(points.length)));
    for (Vector3f vec : points)
    {
      ((ConvexHullShape) this.getCollisionShape()).addPoint(PhysicsTools.openGLToBullet(vec));
    }
  }
}
