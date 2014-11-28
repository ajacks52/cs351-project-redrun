package redrun.test;

import javax.vecmath.Matrix4f;
import javax.vecmath.Quat4f;

import org.lwjgl.util.vector.Vector3f;

import redrun.model.gameobject.player.Player;
import redrun.model.gameobject.player.Runner;
import redrun.model.physics.BoxPhysicsBody;
import redrun.model.physics.PhysicsBody;
import redrun.model.physics.PhysicsTools;
import redrun.model.physics.PhysicsWorld;
import redrun.model.physics.PlanePhysicsBody;

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
    Player p1 = new Runner(0, 4, 0.75f, null);
    Player p = new Runner(0, 2, 0, null);
//    PhysicsBody p = new BoxPhysicsBody(new Vector3f(0,0,0), new Vector3f(0.5f,1.0f,0.5f), new Quat4f(), 100.0f);

    PhysicsBody floor = new PlanePhysicsBody(new Vector3f(0,0,0), new Vector3f(0,1,0), 0);
    
    p.jump();
    for (int i = 0; i < 300; i++)
    {
      PhysicsWorld.stepSimulation(1.0f/60.0f);
      System.out.println(p.getY() + " " + p1.getY());
    }
  }

}
