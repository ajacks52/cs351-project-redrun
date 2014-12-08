/**
 * 
 */
package redrun.model.physics;

import javax.vecmath.Quat4f;

import org.lwjgl.util.vector.Vector3f;

import com.bulletphysics.collision.shapes.BoxShape;
import com.bulletphysics.collision.shapes.StaticPlaneShape;

/**
 * @author jem
 *
 */
public class PlanePhysicsBody extends PhysicsBody
{
  /**
   * Creates an infinite plane in all directions
   * The simplest and easiest physics body should be used wherever possible
   * @param center in meters
   * @param normal the vector that is perpendicular to the plane
   * @param mass in kg
   */
  public PlanePhysicsBody(Vector3f center, Vector3f radiuses, float mass, int collisionTypes)
  {
    super(mass, new Quat4f(0,0,0,1), center,  new BoxShape(PhysicsTools.openGLToBullet(radiuses)), collisionTypes); // im not sure what the "1" represents
  }
  
  /**
   * Creates an infinite plane in all directions
   * The simplest and easiest physics body should be used wherever possible
   * @param center in meters
   * @param normal the vector that is perpendicular to the plane
   * @param mass in kg
   */
  public PlanePhysicsBody(Vector3f center, Vector3f radiuses, float mass)
  {
    super(mass, new Quat4f(0,0,0,1), center,  new BoxShape(PhysicsTools.openGLToBullet(radiuses)), 0); // im not sure what the "1" represents
  }
}
