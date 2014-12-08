package redrun.model.gameobject.trap.full;

import redrun.model.constants.Direction;
import redrun.model.game.GameData;
import redrun.model.gameobject.trap.Trap;
import redrun.model.gameobject.trap.piece.Fire;
import redrun.model.gameobject.trap.piece.JailDoor;

/**
 * Creates a jail of two doors looks players inside for 10 seconds. 
 * 
 * @author Adam Mitchell
 *
 *
 */
public class Jail extends Trap
{
  JailDoor door1;
  JailDoor door2;
  Fire fire;

  /**
   * Constructor
   * 
   * @param x same as map object
   * @param y same as map object
   * @param z same as map object
   * @param same direction as the map object
   * @param just use null
   */
  public Jail(float x, float y, float z, Direction orientation, String textureName)
  {
    super(x, y, z, orientation, null);

    if (orientation == Direction.EAST || orientation == Direction.WEST)
    {
      door1 = new JailDoor(x, y+15, z - 6.5f, orientation, null);
      door2 = new JailDoor(x, y+15, z + 6.5f, orientation, null);
    }

    if (orientation == Direction.SOUTH || orientation == Direction.NORTH)
    {
      door1 = new JailDoor(x - 6.5f, y+15, z, orientation, null);
      door2 = new JailDoor(x + 6.5f, y+15, z, orientation, null);
    }
    
    fire = new Fire(x, y+10, z, orientation);
    
    //GameData.addGameObject(fire);
    GameData.addGameObject(door1);
    GameData.addGameObject(door2);
  }

  @Override
  public void activate()
  {
    System.out.println("Interacting with the game object: " + this.id);
    door1.activate();
    door2.activate();
    //fire.activate();
  }

  @Override
  public void reset()
  {
    // TODO Auto-generated method stub

  }

  @Override
  public void interact()
  {
    // TODO Auto-generated method stub

  }

  @Override
  public void update()
  {
    // TODO Auto-generated method stub
    fire.update();
  }

}
