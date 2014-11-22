package redrun.model.physics;

import javax.vecmath.Vector3f;

import com.bulletphysics.collision.broadphase.BroadphaseInterface;
import com.bulletphysics.collision.broadphase.DbvtBroadphase;
import com.bulletphysics.collision.dispatch.CollisionDispatcher;
import com.bulletphysics.collision.dispatch.DefaultCollisionConfiguration;
import com.bulletphysics.dynamics.DiscreteDynamicsWorld;
import com.bulletphysics.dynamics.RigidBody;
import com.bulletphysics.dynamics.constraintsolver.SequentialImpulseConstraintSolver;

public class PhysicsWorld
{
  public static DiscreteDynamicsWorld world;
  
  static 
  {
    BroadphaseInterface broadphase = new DbvtBroadphase();
    
    // Set up the collision configuration and dispatcher
    DefaultCollisionConfiguration collisionConfiguration = new DefaultCollisionConfiguration();
    CollisionDispatcher dispatcher = new CollisionDispatcher(collisionConfiguration);

    // The actual physics solver
    SequentialImpulseConstraintSolver solver = new SequentialImpulseConstraintSolver();
    
    world = new DiscreteDynamicsWorld(dispatcher, broadphase, solver, collisionConfiguration);
    world.setGravity(new Vector3f(0, -10, 0));
    
  }
  
  public static void addPhysicsBody(RigidBody body)
  {
    world.addRigidBody(body);
  }
  
  public static void removePhysicsBody(RigidBody body)
  {
    world.removeRigidBody(body);
  }
  
  public static void stepSimulation(float timeStep)
  {
    world.stepSimulation(timeStep, 10);
  }
}
