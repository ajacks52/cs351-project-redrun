package redrun.test;

import static org.lwjgl.opengl.GL11.*;

import java.awt.Dimension;
import java.nio.FloatBuffer;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.lwjgl.LWJGLException;
import org.lwjgl.Sys;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;

import redrun.graphics.camera.Camera;
import redrun.graphics.selection.Picker;
import redrun.model.gameobject.world.CheckerBoard;
import redrun.model.toolkit.BufferConverter;

public class GraphicsTestJake
{
  /** time at last frame */
  long lastFrame;
  
  // Settings for how materials react to lighting...
  /** Shininess level. */
  public static float shininess = 0.0f;

  /** Specularity level. */
  public static float specularity = 0.0f;

  /** Emmision level. */
  public static float emission = 0.0f;

  /**
   * Performs OpenGL initialization.
   */
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
   * The main loop where the logic occurs. Stopped when the escape key is
   * pressed or the window is closed.
   */
  private void gameLoop()
  {
    // Create the camera...
    Camera camera = new Camera(70, (float) Display.getWidth() / (float) Display.getHeight(), 0.3f, 1000, 0.0f, 0.0f,
        0.0f);

    // Create the checker-board floor...
    CheckerBoard board = new CheckerBoard(0, 0, 0, new Dimension(50, 50));

    // Used for controlling the camera with the keyboard and mouse...
    float dx = 0.0f;
    float dy = 0.0f;
    float dt = 0.0f;

    // Set the mouse sensitivity...
    float mouseSensitivity = 0.08f;
    float movementSpeed = 0.05f;

    // Hide the mouse cursor...
    Mouse.setGrabbed(true);
    
    boolean shininessToggle = false;
    boolean specularityToggle = false;
    boolean emissionToggle = false;

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

      if (Keyboard.isKeyDown(Keyboard.KEY_J)) shininessToggle = !shininessToggle;

      if (Keyboard.isKeyDown(Keyboard.KEY_K)) specularityToggle = !specularityToggle;

      if (Keyboard.isKeyDown(Keyboard.KEY_L)) emissionToggle = !emissionToggle;

      if (Keyboard.isKeyDown(Keyboard.KEY_F)) Picker.mode = 2;

      if (shininessToggle) shininess = 25.0f;
      else shininess = 12.0f;

      if (specularityToggle) specularity = 1.0f;
      else specularity = 0.3f;

      if (emissionToggle) emission = 0.05f;
      else emission = 0.0f;

      glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

      if (Picker.mode == 2) Picker.startPicking();

      glLoadIdentity();
      camera.lookThrough();

      // Rotate to look at the scene...
      glRotatef(135, 0, 1, 0);

      // Add ambient light...
      FloatBuffer ambientColor = BufferConverter.asFloatBuffer(new float[] { 0.2f, 0.2f, 0.2f, 1.0f });
      glLightModel(GL_LIGHT_MODEL_AMBIENT, ambientColor);

      // Add positional light...
      FloatBuffer lightColor0 = BufferConverter.asFloatBuffer(new float[] { 1.5f, 1.5f, 1.5f, 1.0f });
      FloatBuffer lightPosition0 = BufferConverter.asFloatBuffer(new float[] { 15.0f, 0.0f, 5.0f, 1.0f });
      glLight(GL_LIGHT0, GL_DIFFUSE, lightColor0);
      glLight(GL_LIGHT0, GL_SPECULAR, lightColor0);
      glLight(GL_LIGHT0, GL_POSITION, lightPosition0);

      // Add directional light...
      FloatBuffer lightColor1 = BufferConverter.asFloatBuffer(new float[] { 0.5f, 0.5f, 0.5f, 1.0f });
      FloatBuffer lightPosition1 = BufferConverter.asFloatBuffer(new float[] { 0.0f, 15.0f, 0.0f, 0.0f });
      glLight(GL_LIGHT1, GL_DIFFUSE, lightColor1);
      glLight(GL_LIGHT1, GL_POSITION, lightPosition1);

      // Draw the checker-board...
      board.draw();
      
      Display.update();
      Display.sync(60);
    }
  }
  
  /** 
   * Calculate how many milliseconds have passed 
   * since last frame.
   * 
   * @return milliseconds passed since last frame 
   */
  public int getDelta() {
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

  public static void main(String[] args)
  {
    GraphicsTestJake test = new GraphicsTestJake();
    test.initOpenGL();
    test.gameLoop();
    test.destroyOpenGL();
  }
}
