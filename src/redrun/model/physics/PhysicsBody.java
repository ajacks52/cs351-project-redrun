/**
 * 
 */
package redrun.model.physics;

import javax.vecmath.Matrix4f;
import javax.vecmath.Quat4f;

import org.lwjgl.util.vector.Vector3f;

import com.bulletphysics.collision.shapes.CollisionShape;
import com.bulletphysics.dynamics.RigidBody;
import com.bulletphysics.linearmath.DefaultMotionState;
import com.bulletphysics.linearmath.Transform;

/**
 * Super class for all other physics bodies.
 * creates convenience methods that can be used in the model objects to perform operations on the physics bodies.
 * @author jem
 *
 */
public class PhysicsBody extends RigidBody
{
  private Transform trans = new Transform(); // used only to make getting faster
  /**
   * Creates a simple physics body
   * @param mass in kg if mass is 0 it is static
   * @param direction 
   * @param center in meters
   * @param collisionShape
   */
  public PhysicsBody(float mass, Quat4f direction, Vector3f center,
      CollisionShape collisionShape)
  {
    super(mass, 
        new DefaultMotionState(
            new Transform(
                new Matrix4f(direction,PhysicsTools.openGLToBullet(center), 1))), 
        collisionShape);
  }
  
  /**
   * Gets the X value
   * @return x value
   */
  public float getX()
  {
    trans = this.getMotionState().getWorldTransform(trans);
    return trans.origin.x;
  }
  
  /**
   * Gets the Y value
   * @return y value
   */
  public float getY()
  {
    trans = this.getMotionState().getWorldTransform(trans);
    return trans.origin.y;
  }
  
  /**
   * Gets the Z value
   * @return z value
   */
  public float getZ()
  {
    trans = this.getMotionState().getWorldTransform(trans);
    return trans.origin.y;
  }
  
  /**
   * Gets the mass 
   * @return mass
   */
  public float getMass()
  {
    return 1f/this.getInvMass();
  }
  
  /**
   * applies a force in the normalised direction
   * this isn't a collision this is like the hand of god pushing the object
   * @param direction
   */
  public void push(Vector3f direction)
  {
    direction.normalise();
    float mass = getMass();
    direction.x *= mass;
    direction.y *= mass;
    direction.z *= mass;
    applyCentralForce(PhysicsTools.openGLToBullet(direction));
  }
  
  
  
  /**
   * jump in the y direcion 
   */
  public void jump()
  {
    applyCentralImpulse(PhysicsTools.openGLToBullet(new Vector3f(0,getMass()*7,0))); // about 7 times their mass allows them to jump 2 meters
  }
}
