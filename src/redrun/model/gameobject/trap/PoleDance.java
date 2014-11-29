package redrun.model.gameobject.trap;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL20.glGetAttribLocation;
import static org.lwjgl.opengl.GL20.glVertexAttrib3f;
import redrun.model.toolkit.ShaderLoader;

public class PoleDance extends Trap
{
  Spike sp1;
  Spike sp2;
  Spike sp3;

  public PoleDance(float x, float y, float z, String textureName)
  {

    super(x, y, z, textureName);

    // Physics body...

     sp1 = new Spike(x, y, z, null);
     sp2 = new Spike(x + 5, y, z, null);
     sp3 = new Spike(x, y, z + 5, null);

    displayListId = glGenLists(1);
    glNewList(displayListId, GL_COMPILE);
    {
      glBegin(GL_QUADS);
      {
        glNormal3f(0.0f, 1.0f, 0.0f);
        glVertex3f(1.0f, 1.0f, -1.0f);
        glTexCoord2f(0, 0);
        glVertex3f(-1.0f, 1.0f, -1.0f);
        glTexCoord2f(0, 1);
        glVertex3f(-1.0f, 1.0f, 1.0f);
        glTexCoord2f(1, 1);
        glVertex3f(1.0f, 1.0f, 1.0f);
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
        sp1.draw();
        sp2.draw();
        sp3.draw();
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

    System.out.println("Interacting with the game object: " + this.id);
//    sp1.getBody().push(direction);
//    sp2.getBody().push(direction);
//    sp3.getBody().push(direction);
  }

  @Override
  public void update()
  {
    // TODO Auto-generated method stub

  }

}
