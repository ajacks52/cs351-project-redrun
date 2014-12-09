package redrun.model.gameobject.player;

import static org.lwjgl.opengl.GL11.glCallList;
import static org.lwjgl.opengl.GL11.glPopMatrix;
import static org.lwjgl.opengl.GL11.glPushMatrix;

import org.newdawn.slick.Color;

import redrun.model.toolkit.FontTools;
import redrun.model.toolkit.HUD_Manager;

public class DeathScreen
{

  public static void display(long deathTime)
  {
    HUD_Manager.make2D();
    glPushMatrix();
    {
      glCallList(HUD_Manager.transBackgroundUtil);
    }
    glPopMatrix();
    HUD_Manager.make3D();
    FontTools.renderText("Wasted", 10, 10, Color.white, 3);
  }
}
