package redrun.model.gameobject.player;

import redrun.model.gameobject.GameObject;
import redrun.model.interactable.Interactable;

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
    
    // TODO Auto-generated constructor stub
  }

}
