package redrun.model.physics;

import java.util.HashSet;

import javax.vecmath.Vector3f;

import com.bulletphysics.BulletGlobals;
import com.bulletphysics.ContactAddedCallback;
import com.bulletphysics.ContactProcessedCallback;
import com.bulletphysics.collision.broadphase.BroadphaseInterface;
import com.bulletphysics.collision.broadphase.DbvtBroadphase;
import com.bulletphysics.collision.dispatch.CollisionDispatcher;
import com.bulletphysics.collision.dispatch.CollisionFlags;
import com.bulletphysics.collision.dispatch.CollisionObject;
import com.bulletphysics.collision.dispatch.DefaultCollisionConfiguration;
import com.bulletphysics.collision.narrowphase.ManifoldPoint;
import com.bulletphysics.dynamics.DiscreteDynamicsWorld;
import com.bulletphysics.dynamics.RigidBody;
import com.bulletphysics.dynamics.constraintsolver.SequentialImpulseConstraintSolver;

public class PhysicsWorld
{
  public static DiscreteDynamicsWorld world;
  public static HashSet<PhysicsBody> bodies;
  public static HashSet<PhysicsBody> watchList;
  
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
    bodies = new HashSet<PhysicsBody>();
    watchList = new HashSet<PhysicsBody>();
    ContactProcessedCallback callback = new ContactProcessedCallback()
    {

      @Override
      public boolean contactProcessed(ManifoldPoint arg0, Object obj1, Object obj2)
      {
        CollisionObject o1 = (CollisionObject)obj1;
        CollisionObject o2 = (CollisionObject)obj2;
        

        for (PhysicsBody body : watchList)
        {
          if (body.body == o1)
          {

            body.collidedWith(o2);
          } 
          else if (body.body == o2)
          {

            body.collidedWith(o1);
          }
        }
        return false;
      }
    };
    BulletGlobals.setContactProcessedCallback(callback);
  }
  
  public static void addPhysicsBody(PhysicsBody body)
  {
    world.addRigidBody(body.body);
    bodies.add(body);
  }
  
  public static void removePhysicsBody(PhysicsBody body)
  {
    world.removeRigidBody(body.body);
    bodies.remove(body);
  }
  
  public static void stepSimulation(float timeStep)
  {
    world.stepSimulation(timeStep, 10);
    for (PhysicsBody body : bodies)
    {
      body.callback();
    }
    
  }
  public static void addToWatchList(PhysicsBody body)
  {
    watchList.add(body);
  }
}
