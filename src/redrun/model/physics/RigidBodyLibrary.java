package redrun.model.physics;
import javax.vecmath.Matrix4f;
import javax.vecmath.Quat4f;
import javax.vecmath.Vector3f;

import com.bulletphysics.collision.shapes.BoxShape;
import com.bulletphysics.collision.shapes.CollisionShape;
import com.bulletphysics.collision.shapes.ConvexHullShape;
import com.bulletphysics.collision.shapes.SphereShape;
import com.bulletphysics.collision.shapes.StaticPlaneShape;
import com.bulletphysics.dynamics.RigidBody;
import com.bulletphysics.linearmath.DefaultMotionState;
import com.bulletphysics.linearmath.MotionState;
import com.bulletphysics.linearmath.Transform;
import com.bulletphysics.util.ObjectArrayList;


public class RigidBodyLibrary
{
  /**
   * btTransform
   * I was tired of not being able to make my transforms the way that the c++ library did them
   * @param q the direction of the transform
   * @param v the offset of the tranform
   * @return
   */
  public static Transform btTransform(Quat4f q, Vector3f v) 
  { 
    return new Transform(new Matrix4f(q,v,1.0f)); 
  }

  /**
   * Box
   * creates a Box RigidBody with the specified properties
   * @param center of the box
   * @param radiuses called half-extents in the library, x,y,z dimensions/2
   * @param direction a Quaternion representing the direction of the box
   * @param mass the mass of the box in kg
   * @return RigidBody for the physics engine
   */
  public static RigidBody Box(Vector3f center, Vector3f radiuses, Quat4f direction, float mass)
  {
    MotionState motionState = new DefaultMotionState(btTransform(direction, center));
    CollisionShape shape = new BoxShape(radiuses);
    RigidBody body = new RigidBody(mass, motionState, shape);
    return body;
  }
  
  /**
   * Cube
   * a utility that makes a Box with equal half-extents
   * @param center
   * @param direction
   * @param radius
   * @param mass in kg
   * @return
   */
  public static RigidBody Cube(Vector3f center, Quat4f direction, float radius, float mass)
  {
    MotionState motionState = new DefaultMotionState(btTransform(direction, center));
    CollisionShape shape = new BoxShape(new Vector3f(radius,radius,radius));
    RigidBody body = new RigidBody(mass, motionState, shape);
    return body;
  }
  
  /**
   * Sphere
   * creates a sphere with center at center
   * @param center
   * @param radius
   * @param mass in kg
   * @return
   */
  public static RigidBody Sphere(Vector3f center, float radius, float mass)
  {
    MotionState motionState = new DefaultMotionState(btTransform(new Quat4f(0,0,0,1), center));
    CollisionShape shape = new SphereShape(radius);
    RigidBody body = new RigidBody(mass, motionState, shape);
    return body;
  }
  
  /**
   * Plane
   * used for floors and walls, infinite in all directions
   * @param center
   * @param normal
   * @param mass in kg
   * @return
   */
  public static RigidBody Plane(Vector3f center, Vector3f normal, float mass)
  {
    MotionState motionState = new DefaultMotionState(btTransform(new Quat4f(0,0,0,1), center));
    CollisionShape shape = new StaticPlaneShape(normal, 1);
    RigidBody body = new RigidBody(mass, motionState, shape);
    return body;
  }

  /**
   * Convex
   * makes a custom convex object by filling in the points
   * @param points
   * @param center
   * @param mass in kg
   * @return
   */
  public static RigidBody Convex(Vector3f[] points, Vector3f center, float mass)
  {
    MotionState motionState = new DefaultMotionState(btTransform(new Quat4f(0,0,0,1), center));
    ObjectArrayList<Vector3f> list = new ObjectArrayList<Vector3f>(points.length);
    for (Vector3f vec : points)
    {
      list.add(vec);
    }
    CollisionShape shape = new ConvexHullShape(list);
    RigidBody body = new RigidBody(mass, motionState, shape);
    return body;
  }
  
}
