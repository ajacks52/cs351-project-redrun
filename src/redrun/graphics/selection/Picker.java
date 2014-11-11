package redrun.graphics.selection;

import java.nio.IntBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.Display;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.util.glu.GLU.*;

public class Picker
{
  public static int mode;

  public static IntBuffer selectBuf = BufferUtils.createIntBuffer(1024);

  public static int hits;

  public static void startPicking()
  {
    IntBuffer viewport = BufferUtils.createIntBuffer(16);

    glSelectBuffer(selectBuf);

    glGetInteger(GL_VIEWPORT, viewport);

    glRenderMode(GL_SELECT);

    glInitNames();

    glMatrixMode(GL_PROJECTION);
    glPushMatrix();
    glLoadIdentity();

    gluPickMatrix(Display.getWidth() / 2, viewport.get(3) - (Display.getHeight() / 2), 5, 5, viewport);
    gluPerspective(70.0f, (float) Display.getWidth() / (float) Display.getHeight(), 0.3f, 1000.0f);
    glMatrixMode(GL_MODELVIEW);
  }

  public static void processHits(int hits, IntBuffer buffer, int sw)
  {
    for (int i = 0; i < 10; i++)
    System.out.println("Buffer: " + buffer.get(i));
    
    int numberOfNames = 0;
    int names, minZ;
    int index = 0;
    int indexNames = 0;

    minZ = Integer.MAX_VALUE;
    for (int i = 0; i < hits; i++)
    {
      names = buffer.get(index);
      index++;
      if (buffer.get(index) < minZ)
      {
        numberOfNames = names;
        minZ = buffer.get(index);
        indexNames = index + 2;
      }

      index += names + 2;
    }

    if (numberOfNames > 0)
    {
      System.out.print("You picked snowman ");

      index = indexNames;

      for (int j = 0; j < numberOfNames; j++, index++)
      {
        System.out.println(buffer.get(index));
      }
    }
    else System.out.println("You didn't click a snowman!");
  }

  public static void stopPicking()
  {
    glMatrixMode(GL_PROJECTION);
    glPopMatrix();
    glMatrixMode(GL_MODELVIEW);
    glFlush();
    hits = glRenderMode(GL_RENDER);
    System.out.println("HITS: " + hits);
    if (hits != 0) processHits(hits, selectBuf, 0);
    // Set the mode to render...
    mode = 1;
  }
}
