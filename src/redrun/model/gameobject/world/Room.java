package redrun.model.gameobject.world;

import static org.lwjgl.opengl.GL11.*;
import redrun.model.toolkit.Dimension3D;

public class Room extends WorldObject
{
  Dimension3D dimensions;

  public Room(float x, float y, float z, Dimension3D dimensions)
  {
    super(x, y, z);
    this.dimensions = dimensions;

    displayListId = glGenLists(1);

    glNewList(displayListId, GL_COMPILE);
    {
      drawFloor();
      drawCeiling();
      drawLeftWall();
      drawRightWall();
      drawFrontWall();
      drawBackWall();
    }
    glEndList();
  }

  private void drawFloor()
  {
    glBegin(GL_QUADS);
    {
      glNormal3f(0.0f, 1.0f, 0.0f);

      glVertex3f(-dimensions.width / 2, -1, 0);
      glVertex3f(-dimensions.width / 2, -1, dimensions.depth);
      glVertex3f(dimensions.width / 2, -1, dimensions.depth);
      glVertex3f(dimensions.width / 2, -1, 0);
    }
    glEnd();
  }

  private void drawCeiling()
  {
    glBegin(GL_QUADS);
    {
      glNormal3f(0.0f, -1.0f, 0.0f);

      glVertex3f(-dimensions.width / 2, dimensions.height - 1, 0);
      glVertex3f(-dimensions.width / 2, dimensions.height - 1, dimensions.depth);
      glVertex3f(dimensions.width / 2, dimensions.height - 1, dimensions.depth);
      glVertex3f(dimensions.width / 2, dimensions.height - 1, 0);
    }
    glEnd();
  }

  private void drawLeftWall()
  {
    glBegin(GL_QUADS);
    {
      glNormal3f(-1.0f, 0.0f, 0.0f);

      glVertex3f(dimensions.width / 2, -1, 0);
      glVertex3f(dimensions.width / 2, -1, dimensions.depth);
      glVertex3f(dimensions.width / 2, dimensions.height - 1, dimensions.depth);
      glVertex3f(dimensions.width / 2, dimensions.height - 1, 0);
    }
    glEnd();
  }

  private void drawRightWall()
  {
    glBegin(GL_QUADS);
    {
      glNormal3f(1.0f, 0.0f, 0.0f);

      glVertex3f(-dimensions.width / 2, -1, 0);
      glVertex3f(-dimensions.width / 2, -1, dimensions.depth);
      glVertex3f(-dimensions.width / 2, dimensions.height - 1, dimensions.depth);
      glVertex3f(-dimensions.width / 2, dimensions.height - 1, 0);
    }
    glEnd();
  }

  private void drawFrontWall()
  {
    glBegin(GL_QUADS);
    {
      glNormal3f(0.0f, 0.0f, -1.0f);

      glVertex3f(-dimensions.width / 2, -1, dimensions.depth);
      glVertex3f(-dimensions.width / 2, dimensions.height - 1, dimensions.depth);
      glVertex3f(dimensions.width / 2, dimensions.height - 1, dimensions.depth);
      glVertex3f(dimensions.width / 2, -1, dimensions.depth);
    }
    glEnd();
  }

  private void drawBackWall()
  {
    glBegin(GL_QUADS);
    {
      glNormal3f(0.0f, 0.0f, 1.0f);

      glVertex3f(-dimensions.width / 2, -1, 0);
      glVertex3f(-dimensions.width / 2, dimensions.height - 1, 0);
      glVertex3f(dimensions.width / 2, dimensions.height - 1, 0);
      glVertex3f(dimensions.width / 2, -1, 0);
    }
    glEnd();
  }
}
