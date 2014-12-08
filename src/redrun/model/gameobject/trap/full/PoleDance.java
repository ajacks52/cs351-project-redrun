package redrun.model.gameobject.trap.full;

import redrun.main.Main;
import redrun.model.constants.Direction;
import redrun.model.game.GameData;
import redrun.model.gameobject.trap.Trap;
import redrun.model.gameobject.trap.piece.Spear;

public class PoleDance extends Trap
{
  Spear spear1;
  Spear spear2;
  Spear spear3;
  Spear spear4;
  Spear spear5;

  public PoleDance(float x, float y, float z, Direction orientation, String textureName)
  {
    super(x, y, z, orientation, textureName);

    spear1 = new Spear(x, y - 13.5f, z, orientation, null, 0f, "y");
    spear2 = new Spear(x - 3, y - 13.5f, z + 3, orientation, null, 1f, "y");
    spear3 = new Spear(x + 3, y - 13.5f, z - 3, orientation, null, 1.5f, "y");
    spear4 = new Spear(x + 3, y - 13.5f, z + 3, orientation, null, 2.3f, "y");
    spear5 = new Spear(x - 3, y - 13.5f, z - 3, orientation, null, 3f, "y");

    GameData.addGameObject(spear1);
    GameData.addGameObject(spear2);
    GameData.addGameObject(spear3);
    GameData.addGameObject(spear4);
    GameData.addGameObject(spear5);
  }

  @Override
  public void activate()
  {
    System.out.println("Interacting with the game object: " + this.id);
    Main.client.sendTrap(this);
    spear1.activate();
    spear2.activate();
    spear3.activate();
    spear4.activate();
    spear5.activate();
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
