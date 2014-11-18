package redrun.model.gameobject.player;

import org.lwjgl.util.vector.Vector3f;

import redrun.model.gameobject.GameObject;
import redrun.model.interactable.Interactable;
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
  public Player(float x, float y, float z, String textureName)
  {
    super(x, y, z, textureName);
    // all the physics you'll  need :-D
//    rigidBody = new BoxRigidBody(this.position, new Vector3f(1,2,1), new Quat4f(0,0,0,1), 120.0f);
    // TODO Auto-generated constructor stub
  }
  
  /**
   * makes the player jump about 2 meters into the air
   */
  public void jump()
  {
    body.jump();
  }
  
  /**
   * walk in the specified direction.
   * x and z will be normalized
   * @param x
   * @param z
   */
  public void walk(float x, float z)
  {
    Vector3f vec = new Vector3f(x,0,z);
    body.push(vec);
  }
}
