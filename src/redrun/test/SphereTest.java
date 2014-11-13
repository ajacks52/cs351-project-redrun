package redrun.test;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.lwjgl.LWJGLException;
import org.lwjgl.Sys;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.util.glu.Sphere;

import redrun.graphics.camera.Camera;
import static org.lwjgl.opengl.GL11.*;

public class SphereTest
{
  /** Time at last frame */
  long lastFrame;

  public static void main(String[] args)
  {
    SphereTest test = new SphereTest();
    test.gameLoop();
    test.destroyOpenGL();
  }

  private void gameLoop()
  {
    initOpenGL();
    // Create the camera.
    Camera camera = new Camera(70, (float) Display.getWidth() / (float) Display.getHeight(), 0.3f, 1000, 0.0f, 0.0f,
        0.0f);

    // Used for controlling the camera with the keyboard and mouse...
    float dx = 0.0f;
    float dy = 0.0f;
    float dt = 0.0f;

    // Set the mouse sensitivity...
    float mouseSensitivity = 0.08f;
    float movementSpeed = 0.05f;

    // Hide the mouse cursor...
    Mouse.setGrabbed(true);

    while (!Display.isCloseRequested() && !Keyboard.isKeyDown(Keyboard.KEY_ESCAPE))
    {
      dt = this.getDelta();
      dx = Mouse.getDX();
      dy = Mouse.getDY();

      camera.yaw(dx * mouseSensitivity);
      camera.pitch(-dy * mouseSensitivity);

      if (Keyboard.isKeyDown(Keyboard.KEY_W)) camera.moveForward(movementSpeed * dt);

      if (Keyboard.isKeyDown(Keyboard.KEY_S)) camera.moveBackward(movementSpeed * dt);

      if (Keyboard.isKeyDown(Keyboard.KEY_A)) camera.moveLeft(movementSpeed * dt);

      if (Keyboard.isKeyDown(Keyboard.KEY_D)) camera.moveRight(movementSpeed * dt);

      if (Keyboard.isKeyDown(Keyboard.KEY_UP)) camera.moveUp(movementSpeed * dt);

      if (Keyboard.isKeyDown(Keyboard.KEY_DOWN)) camera.moveDown(movementSpeed * dt);

      glLoadIdentity();
      camera.lookThrough();

      render(); // render graphics
      Display.update();
      Display.sync(60);
    }
  }

  private void render()
  {
    glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
    glTranslatef(0.0f, 0.0f, -5);
    renderSphere(-2f, -0.5f, -1f);
    renderSphere(-1f, -0.5f, -2f);
    renderSphere(-0f, -0.5f, -3f);
    renderSphere(1f, -0.5f, -4f);
    renderSphere(2f, -0.5f, -5f);
  }

  private void renderSphere(float x, float y, float z)
  {
    glPushMatrix();
    glColor3f(1.0f, 0.0f, 0.0f);
    glTranslatef(x, y, z);
    Sphere s = new Sphere();
    s.draw(1.4f, 40, 40);
    glPopMatrix();
  }

  private void initOpenGL()
  {
    try
    {
      Display.setDisplayMode(new DisplayMode(800, 600));
      Display.setTitle("An Awesome OpenGL Scene");
      Display.create();
      Display.setVSyncEnabled(true);
    }
    catch (LWJGLException ex)
    {
      Logger.getLogger(GraphicsTestTroy.class.getName()).log(Level.SEVERE, null, ex);
    }

    glEnable(GL_DEPTH_TEST);
    glDisable(GL_COLOR_MATERIAL);
    glEnable(GL_LIGHTING);
    glEnable(GL_LIGHT0);
    glEnable(GL_LIGHT1);
    glEnable(GL_NORMALIZE);
    glShadeModel(GL_SMOOTH);
  }

  /**
   * Calculate how many milliseconds have passed since last frame.
   * 
   * @return milliseconds passed since last frame
   */
  public int getDelta()
  {
    long time = getTime();
    int delta = (int) (time - lastFrame);
    lastFrame = time;

    return delta;
  }

  /**
   * Get the accurate system time
   * 
   * @return The system time in milliseconds
   */
  public long getTime()
  {
    return (Sys.getTime() * 1000) / Sys.getTimerResolution();
  }

  /**
   * Cleans up OpenGL.
   */
  private void destroyOpenGL()
  {
    Display.destroy();
  }
}
