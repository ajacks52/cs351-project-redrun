package redrun.model.gameobject.trap;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL20.glGetAttribLocation;
import static org.lwjgl.opengl.GL20.glVertexAttrib3f;

import javax.vecmath.Quat4f;

import org.lwjgl.util.vector.Vector3f;

import redrun.model.physics.BoxPhysicsBody;
import redrun.model.physics.SpherePhysicsBody;
import redrun.model.toolkit.ShaderLoader;

public class Spike extends Trap
{
  ShaderLoader sl;
  TrapDoor td1;
  TrapDoor td2;
  float occilate = 0;
  float occilate2 = 0;
  float movementSpeed = 0.15f;

  public Spike(float x, float y, float z, String textureName)
  {
    super(x, y, z, null);

    // Physics body...
    this.body = new BoxPhysicsBody(new Vector3f(x, y, z), new Vector3f(1, 1, 1), new Quat4f(), 1);

    sl = new ShaderLoader();
    sl.loadShader("bloodf.fs");
    sl.loadShader("bloodv.vs");
    sl.deleteShaders();
    int program = glGetAttribLocation(sl.getShaderProgram(), "atr1");

    displayListId = glGenLists(1);
    glNewList(displayListId, GL_COMPILE);
    {
      glScalef(0.3f, 2.f, 0.2f);
      glColor3f(0.5f, 0.5f, 0.5f);

      glBegin(GL_TRIANGLES);
      {
        // Front triangle...
        glVertexAttrib3f(program, 0.6f, 0f, 0f);
        glVertex3f(0.0f, 1.0f, 0.0f);
        glNormal3f(0.0f, 1.0f, 0.0f);
        glVertexAttrib3f(program, 0.0f, 0.0f, 0.0f);
        glVertex3f(-1.0f, -1.0f, 0.0f);
        glNormal3f(-1.0f, -1.0f, 0.0f);
        glVertexAttrib3f(program, 0.0f, 0.0f, 0.0f);
        glVertex3f(1.0f, -1.0f, 0.0f);
        glNormal3f(1.0f, -1.0f, 0.0f);
        // Right triangle...
        glVertexAttrib3f(program, 0.0f, 0.0f, 0.0f);
        glVertex3f(1.0f, -1.0f, 0.0f);
        glNormal3f(1.0f, -1.0f, 0.0f);
        glVertexAttrib3f(program, 0.5f, 0f, 0f);
        glVertex3f(0.0f, 1.0f, 0.0f);
        glNormal3f(0.0f, 1.0f, 0.0f);
        glVertexAttrib3f(program, 0.0f, 0.0f, 0.0f);
        glVertex3f(0.0f, -1.0f, -1.0f);
        glNormal3f(0.0f, -1.0f, -1.0f);
        // Left triangle...
        glVertexAttrib3f(program, 0.0f, 0.0f, 0.0f);
        glVertex3f(-1.0f, -1.0f, 0.0f);
        glNormal3f(-1.0f, -1.0f, 0.0f);
        glVertexAttrib3f(program, 0.5f, 0f, 0f);
        glVertex3f(0.0f, 1.0f, 0.0f);
        glNormal3f(0.0f, 1.0f, 0.0f);
        glVertexAttrib3f(program, 0.0f, 0.0f, 0.0f);
        glVertex3f(0.0f, -1.0f, -1.0f);
        glNormal3f(0.0f, -1.0f, -1.0f);
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
    this.timer.reset();
  }

  @Override
  public void interact()
  {
    // TODO Auto-generated method stub
    System.out.println("Interacting with the game object: " + this.id);
    this.timer.resume();
  }

  @Override
  public void update()
  {
    
    
  }

}
