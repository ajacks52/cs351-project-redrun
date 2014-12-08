package redrun.model.gameobject.trap.full;

import redrun.main.Main;
import redrun.model.constants.Direction;
import redrun.model.game.GameData;
import redrun.model.gameobject.trap.Trap;
import redrun.model.gameobject.trap.piece.Rock;

/**
 * 
 * @author Adam Mitchell
 *
 *
 */
public class RockSmash extends Trap
{
  Rock rock1;
  Rock rock2;
  Rock rock3;
  Rock rock4;
  Rock rock5;
  Rock rock6;

  public RockSmash(float x, float y, float z, Direction orientation, String textureName)
  {
    super(x, y, z, orientation, null);

    rock1 = new Rock(x,     y + 25, z-10, orientation, null, 0f);
    rock2 = new Rock(x+5,   y + 25, z,    orientation, null, 1f);
    rock3 = new Rock(x+3,   y + 25, z+10, orientation, null, 1.5f);
    rock4 = new Rock(x+10,  y + 25, z+15, orientation, null, 1.7f);
    rock5 = new Rock(x,     y + 25, z+15, orientation, null, 1.7f);
    rock6 = new Rock(x+15,  y + 25, z+10, orientation, null, 1.7f);

    GameData.addGameObject(rock1);
    GameData.addGameObject(rock2);
    GameData.addGameObject(rock3);
    GameData.addGameObject(rock4);
    GameData.addGameObject(rock5);
    GameData.addGameObject(rock6);
  }

  @Override
  public void activate()
  {
    System.out.println("Interacting with the game object: " + this.id);
    Main.client.sendTrap(this);
    rock1.activate();
    rock2.activate();
    rock3.activate();
    rock4.activate();
    rock5.activate();
    rock6.activate();
  }

  @Override
  public void reset()
  {
  }

  @Override
  public void interact()
  {
  }

  @Override
  public void update()
  {
  }

}
