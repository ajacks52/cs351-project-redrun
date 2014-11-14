package redrun.test;

import javax.vecmath.Matrix4f;
import javax.vecmath.Quat4f;

import org.lwjgl.util.vector.Vector3f;

import redrun.model.physics.BoxPhysicsBody;
import redrun.model.physics.PhysicsTools;

import com.bulletphysics.collision.broadphase.BroadphaseInterface;
import com.bulletphysics.collision.broadphase.DbvtBroadphase;
import com.bulletphysics.collision.dispatch.CollisionDispatcher;
import com.bulletphysics.collision.dispatch.DefaultCollisionConfiguration;
import com.bulletphysics.collision.shapes.CollisionShape;
import com.bulletphysics.collision.shapes.StaticPlaneShape;
import com.bulletphysics.dynamics.DiscreteDynamicsWorld;
import com.bulletphysics.dynamics.RigidBody;
import com.bulletphysics.dynamics.RigidBodyConstructionInfo;
import com.bulletphysics.dynamics.constraintsolver.SequentialImpulseConstraintSolver;
import com.bulletphysics.linearmath.DefaultMotionState;
import com.bulletphysics.linearmath.Transform;

public class PhysicsTestJordan
{
  public static javax.vecmath.Vector3f vec(Vector3f vec)
  {
    return new javax.vecmath.Vector3f(vec.x, vec.y, vec.z);
  }

  public static void main(String[] args)
  {
    System.out.println("Hello World!");
    // Build the broadphase
    BroadphaseInterface broadphase = new DbvtBroadphase();

    // Set up the collision configuration and dispatcher
    DefaultCollisionConfiguration collisionConfiguration = new DefaultCollisionConfiguration();
    CollisionDispatcher dispatcher = new CollisionDispatcher(collisionConfiguration);

    // The actual physics solver
    SequentialImpulseConstraintSolver solver = new SequentialImpulseConstraintSolver();

    // The world.
    DiscreteDynamicsWorld dynamicsWorld = new DiscreteDynamicsWorld(dispatcher, broadphase, solver,
        collisionConfiguration);
    dynamicsWorld.setGravity(vec(new Vector3f(0, -10, 0)));// Vector3(0, -10,
                                                           // 0));

    // Do_everything_else_here
    CollisionShape groundShape = new StaticPlaneShape(vec(new Vector3f(0, 1, 0)), 1);

    DefaultMotionState groundMotionState = new DefaultMotionState(btTransform(new Quat4f(0, 0, 0, 1), new Vector3f(0,
        -1, 0)));

    RigidBodyConstructionInfo groundRigidBodyCI = new RigidBodyConstructionInfo(0, groundMotionState, groundShape,
        vec(new Vector3f(0, 0, 0)));
    RigidBody groundRigidBody = new RigidBody(groundRigidBodyCI);

    dynamicsWorld.addRigidBody(groundRigidBody);

    float mass = 60f;
    BoxPhysicsBody fallRigidBody = new BoxPhysicsBody(new Vector3f(0, 1, 0), new Vector3f(0.5f, 1.0f, 0.5f),
        new Quat4f(0, 0, 0, 1), mass);
    fallRigidBody.getCollisionShape().calculateLocalInertia(mass, vec(new Vector3f(0, 0, 0)));
    dynamicsWorld.addRigidBody(fallRigidBody);

    fallRigidBody.jump();

    for (int i = 0; i < 300; i++)
    {

      fallRigidBody.push(new Vector3f(1, 0, 0));

      dynamicsWorld.stepSimulation(1 / 60.0f, 10);

      Transform trans = new Transform();
      trans = fallRigidBody.getMotionState().getWorldTransform(trans);

      System.out.println("sphere x: " + trans.origin.x + " y: " + trans.origin.y);
    }
  }

  private static Transform btTransform(Quat4f quat4f, Vector3f vector3f)
  {
    return new Transform(new Matrix4f(quat4f, vec(vector3f), 1.0f));
  }
}
