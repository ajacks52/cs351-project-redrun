/**
 * 
 */
package redrun.model.physics;

import java.nio.FloatBuffer;

import javax.vecmath.Matrix4f;
import javax.vecmath.Quat4f;

import org.lwjgl.BufferUtils;
import org.lwjgl.util.vector.Vector3f;

import com.bulletphysics.collision.shapes.CollisionShape;
import com.bulletphysics.dynamics.RigidBody;
import com.bulletphysics.linearmath.DefaultMotionState;
import com.bulletphysics.linearmath.Transform;

/**
 * Super class for all other physics bodies. creates convenience methods that
 * can be used in the model objects to perform operations on the physics bodies.
 * 
 * @author jem
 *
 */
public class PhysicsBody
{
  private Transform trans = new Transform(); // used only to make getting faster
  public RigidBody body;

  /**
   * Creates a simple physics body
   * 
   * @param mass in kg if mass is 0 it is static
   * @param direction
   * @param center in meters
   * @param collisionShape
   */
  public PhysicsBody(float mass, Quat4f direction, Vector3f center, CollisionShape collisionShape)
  {
    javax.vecmath.Vector3f fallInertia = PhysicsTools.openGLToBullet(new Vector3f(0, 0, 0));
    if (collisionShape != null && mass != 0.0f)
    {
      collisionShape.calculateLocalInertia(mass, fallInertia);
    }
    body = new RigidBody(mass, new DefaultMotionState(new Transform(new Matrix4f(direction,
        PhysicsTools.openGLToBullet(center), 1))), collisionShape, fallInertia);
    PhysicsWorld.addPhysicsBody(body);
  }

  /**
   * Gets the X value
   * 
   * @return x value
   */
  public float getX()
  {
    trans = body.getMotionState().getWorldTransform(trans);
    return trans.origin.x;
  }

  /**
   * Gets the Y value
   * 
   * @return y value
   */
  public float getY()
  {
    trans = body.getMotionState().getWorldTransform(trans);
    return trans.origin.y;
  }

  /**
   * Gets the Z value
   * 
   * @return z value
   */
  public float getZ()
  {
    trans = body.getMotionState().getWorldTransform(trans);
    return trans.origin.z;
  }

  /**
   * Gets the mass
   * 
   * @return mass
   */
  public float getMass()
  {
    return 1f / body.getInvMass();
  }

  /**
   * applies a force in the normalised direction this isn't a collision this is
   * like the hand of god pushing the object
   * 
   * @param direction
   */
  public void push(Vector3f direction)
  {
    // direction.normalise();
    float mass = getMass();
    System.out.println("push: " + direction);
    // body.applyCentralForce(PhysicsTools.openGLToBullet(direction));
    body.setLinearVelocity(PhysicsTools.openGLToBullet(direction));
  }

  /**
   * jump in the y direcion
   */
  public void jump()
  {
    body.applyCentralImpulse(PhysicsTools.openGLToBullet(new Vector3f(0, getMass() * 7, 0))); // about
                                                                                              // 7
                                                                                              // times
                                                                                              // their
                                                                                              // mass
                                                                                              // allows
                                                                                              // them
                                                                                              // to
                                                                                              // jump
                                                                                              // 2
                                                                                              // meters
  }

  public float getPitch()
  {
    trans = body.getMotionState().getWorldTransform(trans);
    Quat4f q = PhysicsTools.quatFromMatrix(trans.basis);
    return PhysicsTools.pitchFromQuat(q);
  }

  public float getYaw()
  {
    trans = body.getMotionState().getWorldTransform(trans);
    Quat4f q = PhysicsTools.quatFromMatrix(trans.basis);
    return PhysicsTools.yawFromQuat(q);
  }

  public float getRoll()
  {
    trans = body.getMotionState().getWorldTransform(trans);
    Quat4f q = PhysicsTools.quatFromMatrix(trans.basis);
    return PhysicsTools.rollFromQuat(q);
  }

  // public void pitch(float pitch)
  // {
  // body.setAngularVelocity(PhysicsTools.openGLToBullet(new Vector3f(0, pitch *
  // 10, 0)));
  // }
  //
  // public void yaw(float yaw)
  // {
  // body.setAngularVelocity(PhysicsTools.openGLToBullet(new Vector3f(0, 0, yaw
  // * 10)));
  // }

  public void moveForward(float speed, float yaw)
  {
    trans = body.getMotionState().getWorldTransform(trans);
    Quat4f q = PhysicsTools.quatFromMatrix(trans.basis);
    // float x = -(float) (Math.sin(PhysicsTools.yawFromQuat(q)) * speed);
    // float z = (float) (Math.cos(PhysicsTools.yawFromQuat(q)) * speed);
    float x = -(float) (Math.sin(yaw) * speed);
    float z = (float) (Math.cos(yaw) * speed);
    push(new Vector3f(x, 0, z));
  }

  public void moveBackward(float speed, float yaw)
  {
    trans = body.getMotionState().getWorldTransform(trans);
    Quat4f q = PhysicsTools.quatFromMatrix(trans.basis);
    // float x = (float) (Math.sin(PhysicsTools.yawFromQuat(q)) * speed);
    // float z = -(float) (Math.cos(PhysicsTools.yawFromQuat(q)) * speed);
    float x = (float) (Math.sin(yaw) * speed);
    float z = -(float) (Math.cos(yaw) * speed);

    push(new Vector3f(x, 0, z));
  }

  public void moveLeft(float speed, float yaw)
  {
    trans = body.getMotionState().getWorldTransform(trans);
    Quat4f q = PhysicsTools.quatFromMatrix(trans.basis);

    // float x = (float) (Math.cos(PhysicsTools.yawFromQuat(q) + Math.PI / 4) *
    // speed);
    // float z = (float) (Math.sin(PhysicsTools.yawFromQuat(q) + Math.PI / 4) *
    // speed);
    float x = (float) (Math.sin(yaw) * speed);
    float z = (float) (Math.cos(yaw) * speed);
    push(new Vector3f(x, 0, z));
  }

  public void moveRight(float speed, float yaw)
  {
    trans = body.getMotionState().getWorldTransform(trans);
    Quat4f q = PhysicsTools.quatFromMatrix(trans.basis);
//    float x = -(float) (Math.cos(PhysicsTools.yawFromQuat(q) - Math.PI / 4) * speed);
//    float y = (float) (Math.sin(PhysicsTools.yawFromQuat(q) - Math.PI / 4) * speed);
    float x = -(float) (Math.sin(yaw) * speed);
    float y = (float) (Math.cos(yaw) * speed);
    push(new Vector3f(x, y, 0));
  }

  public FloatBuffer getOpenGLTransformMatrix()
  {
    float[] m = new float[16];
    trans = body.getWorldTransform(trans);
    trans.getOpenGLMatrix(m);
    FloatBuffer buffer = BufferUtils.createFloatBuffer(16);
    buffer.clear();
    buffer.put(m);
    buffer.flip();
    return buffer;
  }

  public void translate(float x, float y, float z)
  {
    body.translate(PhysicsTools.openGLToBullet(new Vector3f(x, y, z)));
    body.activate(true);
  }

  public CollisionShape getCollisionShape()
  {
    return body.getCollisionShape();
  }
}
