package redrun.model.gameobject.player;

import static org.lwjgl.opengl.GL11.glCallList;
import static org.lwjgl.opengl.GL11.glPopMatrix;
import static org.lwjgl.opengl.GL11.glPushMatrix;

import org.newdawn.slick.Color;

import redrun.model.constants.Constants;
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
    
    
    FontTools.renderText("Wasted", Constants.DISPLAY_WIDTH/2, Constants.DISPLAY_HEIGHT/2, Color.red, 3);
  }
}
