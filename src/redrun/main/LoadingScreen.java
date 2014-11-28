package redrun.main;

import static org.lwjgl.opengl.GL11.*;
import redrun.model.gameobject.GameObject;

/**
 * @author Adam Mitchell
 * @version 1.0
 * @since 2014-19-10
 * 
 * Game object the draws loading screen text.
 */
public class LoadingScreen extends GameObject
{
  public LoadingScreen(String texture)
  {
    super(0, 0, 0, texture);
    displayListId = glGenLists(1);

    glNewList(displayListId, GL_COMPILE);
    {
      glBegin(GL_QUADS);
      glTexCoord2f(0, 0);
      glVertex2i(0, 0); // Upper-left
      glTexCoord2f(1, 0);
      glVertex2i(750, 0); // Upper-right
      glTexCoord2f(1, 1);
      glVertex2i(750, 500); // Bottom-right
      glTexCoord2f(0, 1);
      glVertex2i(0, 500); // Bottom-left
      glEnd();
    }
    glEndList();
  }

  @Override
  public void interact()
  {
  }

  @Override
  public void update()
  {
  }

  @Override
  public void reset()
  {
  }

}
