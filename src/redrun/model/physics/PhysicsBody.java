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
    PhysicsWorld.addPhysicsBody(this);
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
    return trans.origin.z;
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
  
  public float getPitch()
  {
    trans = this.getMotionState().getWorldTransform(trans);
    Quat4f q = PhysicsTools.quatFromMatrix(trans.basis);
    return PhysicsTools.pitchFromQuat(q);
  }
  
  public float getYaw()
  {
    trans = this.getMotionState().getWorldTransform(trans);
    Quat4f q = PhysicsTools.quatFromMatrix(trans.basis);
    return PhysicsTools.yawFromQuat(q);
  }
  
  public float getRoll()
  {
    trans = this.getMotionState().getWorldTransform(trans);
    Quat4f q = PhysicsTools.quatFromMatrix(trans.basis);
    return PhysicsTools.rollFromQuat(q);
  }
 
  public void pitch(float pitch)
  {
    this.applyTorque(PhysicsTools.openGLToBullet(new Vector3f(0,pitch,0)));
  }
  
  public void yaw(float yaw)
  {
    this.applyTorque(PhysicsTools.openGLToBullet(new Vector3f(0,0,yaw)));
  }

  public void moveForward(float speed)
  {
    trans = this.getMotionState().getWorldTransform(trans);
    Quat4f q = PhysicsTools.quatFromMatrix(trans.basis);
    float x = - (float) (Math.sin(PhysicsTools.yawFromQuat(q)) * speed);
    float y = (float) (Math.cos(PhysicsTools.yawFromQuat(q)) * speed);
    push(new Vector3f(x,y,0));
  }

  public void moveBackward(float speed)
  {
    trans = this.getMotionState().getWorldTransform(trans);
    Quat4f q = PhysicsTools.quatFromMatrix(trans.basis);
    float x = (float) (Math.sin(PhysicsTools.yawFromQuat(q)) * speed);
    float y = - (float) (Math.cos(PhysicsTools.yawFromQuat(q)) * speed);
    push(new Vector3f(x,y,0));
  }

  public void moveLeft(float speed)
  {
    trans = this.getMotionState().getWorldTransform(trans);
    Quat4f q = PhysicsTools.quatFromMatrix(trans.basis);
    float x = - (float) (Math.cos(PhysicsTools.yawFromQuat(q)) * speed);
    float y = (float) (Math.sin(PhysicsTools.yawFromQuat(q)) * speed);
    push(new Vector3f(x,y,0));
  }

  public void moveRight(float speed)
  {
    trans = this.getMotionState().getWorldTransform(trans);
    Quat4f q = PhysicsTools.quatFromMatrix(trans.basis);
    float x = (float) (Math.cos(PhysicsTools.yawFromQuat(q)) * speed);
    float y = - (float) (Math.sin(PhysicsTools.yawFromQuat(q)) * speed);
    push(new Vector3f(x,y,0));
  }
  
}
