package redrun.test;

import javax.vecmath.Matrix4f;
import javax.vecmath.Quat4f;
import javax.vecmath.Vector3f;

import com.bulletphysics.collision.broadphase.BroadphaseInterface;
import com.bulletphysics.collision.broadphase.DbvtBroadphase;
import com.bulletphysics.collision.dispatch.CollisionDispatcher;
import com.bulletphysics.collision.dispatch.DefaultCollisionConfiguration;
import com.bulletphysics.collision.shapes.CollisionShape;
import com.bulletphysics.collision.shapes.SphereShape;
import com.bulletphysics.collision.shapes.StaticPlaneShape;
import com.bulletphysics.dynamics.DiscreteDynamicsWorld;
import com.bulletphysics.dynamics.RigidBody;
import com.bulletphysics.dynamics.RigidBodyConstructionInfo;
import com.bulletphysics.dynamics.constraintsolver.SequentialImpulseConstraintSolver;
import com.bulletphysics.linearmath.DefaultMotionState;
import com.bulletphysics.linearmath.Transform;

public class PhysicsTestJordan
{
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
    DiscreteDynamicsWorld dynamicsWorld = new DiscreteDynamicsWorld(dispatcher, broadphase, solver, collisionConfiguration);
    dynamicsWorld.setGravity(new Vector3f(0,-10,0));// Vector3(0, -10, 0));

    // Do_everything_else_here
    CollisionShape groundShape = new StaticPlaneShape(new Vector3f(0, 1, 0), 1);
    CollisionShape fallShape = new SphereShape(1);
    
    
    DefaultMotionState groundMotionState = new DefaultMotionState(btTransform(new Quat4f(0,0,0,1), new Vector3f(0,-1,0)));

    RigidBodyConstructionInfo groundRigidBodyCI = new RigidBodyConstructionInfo(0,groundMotionState,groundShape,new Vector3f(0,0,0));
    RigidBody groundRigidBody  = new RigidBody(groundRigidBodyCI);
    
    dynamicsWorld.addRigidBody(groundRigidBody);
    
    
    DefaultMotionState fallMotionState = new DefaultMotionState(btTransform(new Quat4f(0,0,0,1), new Vector3f(0,50,0)));
    
    float mass = 1.0f;
    Vector3f fallInertia = new Vector3f(0,0,0);
    fallShape.calculateLocalInertia(mass, fallInertia);
    
    RigidBodyConstructionInfo fallRigidBodyCI = new RigidBodyConstructionInfo(mass,fallMotionState,fallShape,fallInertia);
    RigidBody fallRigidBody = new RigidBody(fallRigidBodyCI);
    
    dynamicsWorld.addRigidBody(fallRigidBody);
    
    for (int i=0; i < 300; i++)
    {
      dynamicsWorld.stepSimulation(1/60.0f, 10);
      
      Transform trans = new Transform();
      trans = fallRigidBody.getMotionState().getWorldTransform(trans);
      
      System.out.println("sphere height:" + trans.origin.y);
    }
  }

  private static Transform btTransform(Quat4f quat4f, Vector3f vector3f)
  {
    return new Transform(new Matrix4f(quat4f,vector3f,1.0f));
  }
}
