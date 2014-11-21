package redrun.model.physics;

import javax.vecmath.Matrix3f;
import javax.vecmath.Matrix4f;
import javax.vecmath.Quat4f;
import javax.vecmath.Vector3f;

import com.bulletphysics.linearmath.QuaternionUtil;
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
  
  public static Quat4f quatFromRollPitchYaw(float roll, float pitch, float yaw)
  {
    Quat4f q = new Quat4f();
    QuaternionUtil.setEuler(q, yaw, pitch, roll);
    return q;
  }
  
  public static float rollFromQuat(Quat4f q)
  {
    return (float) Math.toDegrees(Math.atan2(2*q.y*q.w - 2*q.x*q.z, 1-2*q.y*q.y - 2*q.z*q.z));
  }
  
  public static float pitchFromQuat(Quat4f q)
  {
    return (float) Math.toDegrees(Math.atan2(2*q.x*q.w - 2*q.y*q.z, 1-2*q.x*q.x - 2*q.z*q.z));
  }
  
  public static float yawFromQuat(Quat4f q)
  {
    return (float) Math.toDegrees(Math.asin(2*q.x*q.y + 2*q.z*q.w));
  }
  
  public static Quat4f quatFromMatrix(Matrix3f m1)
  {
    float x; 
    float y;
    float z;
    float w;
    w = (float) (Math.sqrt(1.0 + m1.m00 + m1.m11 + m1.m22) / 2.0);
    float w4 = (float) (4.0 * w);
    x = (m1.m21 - m1.m12) / w4 ;
    y = (m1.m02 - m1.m20) / w4 ;
    z = (m1.m10 - m1.m01) / w4 ;
    return new Quat4f(x,y,z,w);
  }
}
