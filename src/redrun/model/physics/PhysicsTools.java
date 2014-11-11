package redrun.model.physics;

import javax.vecmath.Vector3f;

public class PhysicsTools
{
  public static Vector3f openGLToBullet(org.lwjgl.util.vector.Vector3f vec)
  {
    return new Vector3f(vec.x,vec.y,vec.z);
  }
}
