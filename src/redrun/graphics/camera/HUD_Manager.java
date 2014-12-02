package redrun.graphics.camera;

import java.util.ArrayList;

import org.newdawn.slick.Color;

import redrun.model.constants.CameraType;
import redrun.model.constants.Constants;
import redrun.model.gameobject.player.Player;
import redrun.model.toolkit.FontTools;

public class HUD_Manager
{

  public static void huds(Camera cam, Player player)
  {
    FontTools.draw2D();
    if (cam.getType() == CameraType.SPECTATOR)
    {
      FontTools.renderText("Spectator Camera: (" + cam.getX() + ", " + cam.getY() + ", " + cam.getZ() + ")", 10, 10,
          Color.black, 1);
    }
    else
    {
       FontTools.renderText("Player: " + player.getName(), 10, 10,
       Color.black, 1);
       FontTools.renderText("Team: " + player.getTeam(), 10, 30,
       Color.black, 1);
       FontTools.renderText("Lives: " + player.getLives(), 10, 50,
       Color.black, 1);
      FontTools.renderText("Player Camera: (" + cam.getX() + ", " + cam.getY() + ", " + cam.getZ() + ")", 10, 70,
          Color.black, 1);

      FontTools.renderText("+", (Constants.DISPLAY_WIDTH / 2), (Constants.DISPLAY_HEIGHT / 2), Color.white, 2);
    }
    FontTools.draw3D();
  }
}
