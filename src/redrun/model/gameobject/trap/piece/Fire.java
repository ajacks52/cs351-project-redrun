package redrun.model.gameobject.trap.piece;

import org.lwjgl.util.glu.GLU;
import org.lwjgl.util.vector.Vector3f;

import redrun.model.constants.Direction;
import redrun.model.gameobject.trap.Trap;
import static org.lwjgl.opengl.GL11.*;

/**
 * A 3D particle system. The system continually emits particles in a randomised
 * direction from the emitter location. Gravity affects particles. Particles
 * gradually fade away, the longer they live. The left and right arrow keys pan
 * the scene. The mouse wheel zooms in or out. Pressing the left mouse button
 * temporarily stops particle generation.
 */
public class Fire extends Trap
{

  public Fire(float x, float y, float z, Direction orientation)
  {
    super(x, y, z, orientation, null);

  }

  private static ParticleEmitter particleEmitter = new ParticleEmitterBuilder().setEnable3D(true)
      .setInitialVelocity(new Vector3f(0, 0, 0)).setGravity(new Vector3f(0, -0.0001f, 0)).setSpawningRate(50)
      .setParticleLifeTime(500).createParticleEmitter();
  private static float zoom = 1.0f;
  private static double step = 0;
  private static boolean rotateDirection = false;
  private static boolean rotate = false;

  private static void logic()
  {
    particleEmitter.update();
    if (rotate)
    {
      if (rotateDirection == /* left */false)
      {
        step -= 0.03f;
      }
      else if (rotateDirection == /* right */true)
      {
        step += 0.03f;
      }
    }
  }

  // @Override
  // public void draw()
  // {
  //
  // }

  @Override
  public void activate()
  {
  }

  @Override
  public void reset()
  {
  }

  @Override
  public void interact()
  {
  }


  @Override
  public void update()
  {
    logic();

    System.out.println("hhii");
    glPushMatrix();
    {
      glEnable(GL_TEXTURE_2D);
      // glColor3f(1f, 1f, 1f);
      glMultMatrix(body.getOpenGLTransformMatrix());
      glBindTexture(GL_TEXTURE_2D, 0);
      particleEmitter.draw();
      glDisable(GL_TEXTURE_2D);
    }
    glPopMatrix();

  }
}
