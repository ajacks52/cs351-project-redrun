package redrun.model.physics;

import javax.vecmath.Quat4f;

import org.lwjgl.util.vector.Vector3f;

import com.bulletphysics.collision.shapes.BoxShape;

/**
 * A plane
 * @author jem
 * @date 141115
 */
public class PlanePhysicsBody extends PhysicsBody
{
  /**
   * Creates an plane 
   * The simplest and easiest physics body should be used wherever possible
   * @param center in meters
   * @param normal the vector that is perpendicular to the plane
   * @param mass in kg
   */
  public PlanePhysicsBody(Vector3f center, Vector3f radiuses, float mass, int collisionTypes)
  {
    super(mass, new Quat4f(0,0,0,1), center,  new BoxShape(PhysicsTools.openGLToBullet(radiuses)), collisionTypes);
  }
  
  /**
   * Creates a plane 
   * The simplest and easiest physics body should be used wherever possible
   * @param center in meters
   * @param normal the vector that is perpendicular to the plane
   * @param mass in kg
   */
  public PlanePhysicsBody(Vector3f center, Vector3f radiuses, float mass)
  {
    super(mass, new Quat4f(0,0,0,1), center,  new BoxShape(PhysicsTools.openGLToBullet(radiuses)), 0);
  }
}
