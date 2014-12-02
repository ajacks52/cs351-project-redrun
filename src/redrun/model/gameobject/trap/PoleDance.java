package redrun.model.gameobject.trap;

import static org.lwjgl.opengl.GL11.*;
import redrun.model.constants.Direction;
import redrun.model.constants.Scale;
import redrun.model.game.GameData;

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

    // Physics body...

    spear1 = new Spear(x, y, z, orientation, null);
    spear2 = new Spear(x - 3, y, z + 3, orientation, null);
    spear3 = new Spear(x + 3, y, z - 3, orientation, null);
    spear4 = new Spear(x + 3, y, z + 3, orientation, null);
    spear5 = new Spear(x - 3, y, z - 3, orientation, null);
    
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
//  sp1.getBody().push(direction);
//  sp2.getBody().push(direction);
//  sp3.getBody().push(direction);
  }

  @Override
  public void reset()
  {
    // TODO Auto-generated method stub

  }

  @Override
  public void draw()
  {

    if (texture != null)
    {
      glPushMatrix();
      {
        glEnable(GL_TEXTURE_2D);
        texture.bind();
        glRotatef(body.getYaw(), 1, 0, 0);
        glRotatef(body.getPitch(), 0, 1, 0);
        glRotatef(body.getRoll(), 0, 0, 1);
        glTranslatef(body.getX(), body.getY(), body.getZ());
        glCallList(displayListId);    
        glDisable(GL_TEXTURE_2D);
      }
      glPopMatrix();
    }
//    else
//    {
//      glPushMatrix();
//      {
//        glRotatef(body.getYaw(), 1, 0, 0);
//        glRotatef(body.getPitch(), 0, 1, 0);
//        glRotatef(body.getRoll(), 0, 0, 1);
//        glTranslatef(body.getX(), body.getY(), body.getZ());
//        glCallList(displayListId);
//        sp1.draw();
//        sp2.draw();
//        sp3.draw();
//      }
//      glPopMatrix();
//    }

    update();

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

  }

}
