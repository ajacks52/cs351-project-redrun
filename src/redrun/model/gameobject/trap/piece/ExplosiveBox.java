package redrun.model.gameobject.trap.piece;

import static org.lwjgl.opengl.GL11.*;

import javax.vecmath.Quat4f;

import org.lwjgl.util.vector.Vector3f;

import redrun.model.constants.Direction;
import redrun.model.gameobject.trap.Trap;
import redrun.model.physics.BoxPhysicsBody;
import redrun.model.physics.CapsulePhysicsBody;

public class ExplosiveBox extends Trap
{
  public ExplosiveBox(float x, float y, float z, Direction orientation, String textureName)
  {
    super(x, y+20, z, orientation, textureName);

    this.body = new CapsulePhysicsBody(new Vector3f(x, y+20, z), 3, 7, 7);

    displayListId = glGenLists(1);

    glNewList(displayListId, GL_COMPILE);
    {
      glBegin(GL_QUADS);
      {
        glColor3f(1.0f, 1.0f, 1.0f);
        // Top face...
        glNormal3f(0.0f, 1.0f, 0.0f);
        glVertex3f(1.0f, 1.0f, 1.0f);
        glTexCoord2f(0, 0);
        glVertex3f(1.0f, 1.0f, -1.0f);
        glTexCoord2f(0, 1);
        glVertex3f(-1.0f, 1.0f, -1.0f);
        glTexCoord2f(1, 1);
        glVertex3f(-1.0f, 1.0f, 1.0f);
        glTexCoord2f(1, 0);

        // Bottom face...
        glNormal3f(0.0f, -1.0f, 0.0f);
        glVertex3f(1.0f, -1.0f, 1.0f);
        glTexCoord2f(0, 0);
        glVertex3f(-1.0f, -1.0f, 1.0f);
        glTexCoord2f(0, 1);
        glVertex3f(-1.0f, -1.0f, -1.0f);
        glTexCoord2f(1, 1);
        glVertex3f(1.0f, -1.0f, -1.0f);
        glTexCoord2f(1, 0);

        // Front face...
        glNormal3f(0.0f, 0.0f, -1.0f);
        glVertex3f(1.0f, -1.0f, -1.0f);
        glTexCoord2f(0, 0);
        glVertex3f(-1.0f, -1.0f, -1.0f);
        glTexCoord2f(0, 1);
        glVertex3f(-1.0f, 1.0f, -1.0f);
        glTexCoord2f(1, 1);
        glVertex3f(1.0f, 1.0f, -1.0f);
        glTexCoord2f(1, 0);

        // Back face...
        glNormal3f(0.0f, 0.0f, 1.0f);
        glVertex3f(1.0f, 1.0f, 1.0f);
        glTexCoord2f(0, 0);
        glVertex3f(-1.0f, 1.0f, 1.0f);
        glTexCoord2f(0, 1);
        glVertex3f(-1.0f, -1.0f, 1.0f);
        glTexCoord2f(1, 1);
        glVertex3f(1.0f, -1.0f, 1.0f);
        glTexCoord2f(1, 0);

        // Left face...
        glNormal3f(-1.0f, 0.0f, 0.0f);
        glVertex3f(-1.0f, 1.0f, 1.0f);
        glTexCoord2f(0, 0);
        glVertex3f(-1.0f, 1.0f, -1.0f);
        glTexCoord2f(0, 1);
        glVertex3f(-1.0f, -1.0f, -1.0f);
        glTexCoord2f(1, 1);
        glVertex3f(-1.0f, -1.0f, 1.0f);
        glTexCoord2f(1, 0);

        // Right face...
        glNormal3f(1.0f, 0.0f, 0.0f);
        glVertex3f(1.0f, 1.0f, -1.0f);
        glTexCoord2f(0, 0);
        glVertex3f(1.0f, 1.0f, 1.0f);
        glTexCoord2f(0, 1);
        glVertex3f(1.0f, -1.0f, 1.0f);
        glTexCoord2f(1, 1);
        glVertex3f(1.0f, -1.0f, -1.0f);
        glTexCoord2f(1, 0);
      }
      glEnd();
    }
    glEndList();
  }

  @Override
  public void activate()
  {
    // TODO Auto-generated method stub
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
//    System.out.println(this.body.getY());
  }
}