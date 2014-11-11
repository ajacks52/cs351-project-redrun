/**
 * 
 */
package redrun.model.physics;

import javax.vecmath.Quat4f;

import org.lwjgl.util.vector.Vector3f;

/**
 * @author jem
 *
 */
public class CubeRigidBody extends BoxRigidBody
{

  /**
   * @param center
   * @param radiuses
   * @param direction
   * @param mass
   */
  public CubeRigidBody(Vector3f center, float radius, Quat4f direction, float mass)
  {
    super(center, new Vector3f(radius,radius,radius), direction, mass);
  }

}
