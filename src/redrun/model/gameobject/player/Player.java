package redrun.model.gameobject.player;

import javax.vecmath.Quat4f;

import org.lwjgl.util.vector.Vector3f;

import redrun.model.gameobject.GameObject;
import redrun.model.interactable.Interactable;
import redrun.model.physics.BoxRigidBody;
import redrun.model.physics.PhysicsTools;

/**
 * This class represents a player in the game world.
 * 
 * @author Troy Squillaci, Jake Nichol
 * @version 1.0
 * @since 2014-11-07
 */
public abstract class Player extends GameObject implements Interactable
{
  /**
   * Creates a new player at the specified position.
   * 
   * @param x the x position of the player
   * @param y the y position of the player
   * @param z the z position of the player
   */
  public Player(float x, float y, float z)
  {
    super(x, y, z);
    // all the physics you'll  need :-D
//    rigidBody = new BoxRigidBody(this.position, new Vector3f(1,2,1), new Quat4f(0,0,0,1), 120.0f);
    // TODO Auto-generated constructor stub
  }

}
