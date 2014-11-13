package redrun.model.physics;

import javax.vecmath.Vector3f;
/**
 * 
 * @author jem
 *
 */
public class PhysicsTools
{
  /**
   * converts from openGL Vector3f to JBullet Vector3f
   * @param vec
   * @return
   */
  public static Vector3f openGLToBullet(org.lwjgl.util.vector.Vector3f vec)
  {
    return new Vector3f(vec.x,vec.y,vec.z);
  }
}
