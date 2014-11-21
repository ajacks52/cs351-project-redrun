package redrun.test;

import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_COLOR_MATERIAL;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_TEST;
import static org.lwjgl.opengl.GL11.GL_DIFFUSE;
import static org.lwjgl.opengl.GL11.GL_LIGHT0;
import static org.lwjgl.opengl.GL11.GL_LIGHT1;
import static org.lwjgl.opengl.GL11.GL_LIGHTING;
import static org.lwjgl.opengl.GL11.GL_LIGHT_MODEL_AMBIENT;
import static org.lwjgl.opengl.GL11.GL_LINE_LOOP;
import static org.lwjgl.opengl.GL11.GL_NORMALIZE;
import static org.lwjgl.opengl.GL11.GL_POSITION;
import static org.lwjgl.opengl.GL11.GL_SMOOTH;
import static org.lwjgl.opengl.GL11.GL_SPECULAR;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glColor3f;
import static org.lwjgl.opengl.GL11.glDisable;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glLight;
import static org.lwjgl.opengl.GL11.glLightModel;
import static org.lwjgl.opengl.GL11.glLoadIdentity;
import static org.lwjgl.opengl.GL11.glPopMatrix;
import static org.lwjgl.opengl.GL11.glPopName;
import static org.lwjgl.opengl.GL11.glPushMatrix;
import static org.lwjgl.opengl.GL11.glPushName;
import static org.lwjgl.opengl.GL11.glRotatef;
import static org.lwjgl.opengl.GL11.glShadeModel;
import static org.lwjgl.opengl.GL11.glTranslatef;
import static org.lwjgl.opengl.GL11.glVertex3f;

import java.awt.Dimension;
import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.lwjgl.LWJGLException;
import org.lwjgl.Sys;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.util.Timer;
import org.newdawn.slick.opengl.Texture;

import redrun.graphics.camera.Camera;
import redrun.graphics.selection.Picker;
import redrun.model.gameobject.world.Button;
import redrun.model.gameobject.world.CheckerBoard;
import redrun.model.gameobject.world.Cube;
import redrun.model.gameobject.world.Tetrahedron;
import redrun.model.toolkit.BufferConverter;
import redrun.model.toolkit.Tools;

public class GraphicsTestJake
{
  /** time at last frame */
  long lastFrame;

  /** The list of cubes. */
  private static ArrayList<Cube> cubes = new ArrayList<Cube>();
  /** The list of tetrahedrons. */
  private static ArrayList<Tetrahedron> tetrahedrons = new ArrayList<Tetrahedron>();
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
  private void createOpenGL()
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
      Logger.getLogger(GraphicsTestJake.class.getName()).log(Level.SEVERE, null, ex);
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
    Cube pedestal = new Cube(4, 0, 4, "wood");
    Button button = new Button(4, 0, 4, null);

    // Load in the textures...
    Texture wood = Tools.loadTexture("wood", "png");
    Texture pokadots = Tools.loadTexture("pokadots", "png");
    // Create the camera...
    Camera camera = new Camera(70, (float) Display.getWidth() / (float) Display.getHeight(), 0.3f, 1000, 0.0f, 0.0f,
        0.0f);
    // Create the checker-board floor...
    CheckerBoard board = new CheckerBoard(0, 0, 0, null, new Dimension(50, 50));
    // Create the skybox...
    // TODO SkyBox skybox = new SkyBox(0, 0, 0);
    // Create the cubes...
    cubes.add(new Cube(0.0f, 0.0f, 0.0f, null));
    cubes.add(new Cube(5.0f, 0.0f, 0.0f, null));
    cubes.add(new Cube(0.0f, 0.0f, 5.0f, null));
    cubes.add(new Cube(5.0f, 0.0f, 5.0f, null));
    // Create the tetrahedrons...
    tetrahedrons.add(new Tetrahedron(0.0f, 0.0f, 0.0f, null));
    tetrahedrons.add(new Tetrahedron(5.0f, 0.0f, 0.0f, null));
    tetrahedrons.add(new Tetrahedron(0.0f, 0.0f, 5.0f, null));
    tetrahedrons.add(new Tetrahedron(5.0f, 0.0f, 5.0f, null));
    // Used for controlling the camera with the keyboard and mouse...
    float dx = 0.0f;
    float dy = 0.0f;
    float dt = 0.0f;
    // Set the mouse sensitivity...
    float mouseSensitivity = 0.05f;
    float movementSpeed = 0.01f;
    // Hide the mouse cursor...
    Mouse.setGrabbed(true);
    float occilate = 0;
    float rotate = 0;
    boolean shininessToggle = false;
    boolean specularityToggle = false;
    boolean emissionToggle = false;
    while (!Display.isCloseRequested() && !Keyboard.isKeyDown(Keyboard.KEY_ESCAPE))
    {
      dt = getDelta();

      dx = Mouse.getDX();
      dy = Mouse.getDY();
      camera.yaw(dx * mouseSensitivity);
      camera.pitch(-dy * mouseSensitivity);
      if (Keyboard.isKeyDown(Keyboard.KEY_W)) camera.moveForward(movementSpeed * dt);
      if (Keyboard.isKeyDown(Keyboard.KEY_S)) camera.moveBackward(movementSpeed * dt);
      if (Keyboard.isKeyDown(Keyboard.KEY_A)) camera.moveLeft(movementSpeed * dt);
      if (Keyboard.isKeyDown(Keyboard.KEY_D)) camera.moveRight(movementSpeed * dt);
      if (Keyboard.isKeyDown(Keyboard.KEY_SPACE)) camera.moveUp(movementSpeed * dt);
      if (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)) camera.moveDown(movementSpeed * dt);
      if (Keyboard.isKeyDown(Keyboard.KEY_J)) shininessToggle = !shininessToggle;
      if (Keyboard.isKeyDown(Keyboard.KEY_K)) specularityToggle = !specularityToggle;
      if (Keyboard.isKeyDown(Keyboard.KEY_L)) emissionToggle = !emissionToggle;
//      if (Keyboard.isKeyDown(Keyboard.KEY_F)) Picker.mode = 2;
      if (shininessToggle) shininess = 25.0f;
      else shininess = 12.0f;
      if (specularityToggle) specularity = 1.0f;
      else specularity = 0.3f;
      if (emissionToggle) emission = 0.05f;
      else emission = 0.0f;
      glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
//      if (Picker.mode == 2) Picker.startPicking();
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
      // Draw the checker-board and shit...
      glPushName(board.id);
      {
        board.draw();
      }
      glPopName();
      glPushName(button.id);
      {
        button.draw();
      }
      glPopName();
      glPushMatrix();
      {
        glPushName(pedestal.id);
        {
          pedestal.draw();
        }
        glPopName();
      }
      glPopMatrix();
      // Draw the cubes...
      for (Cube cube : cubes)
      {
        glPushMatrix();
        {
          glPushName(cube.id);
          {
            glTranslatef(20.0f, 1.5f, 5.0f);
            glTranslatef(cube.getX(), (float) (cube.getY() + Math.sin(occilate)), cube.getZ());
            glRotatef(rotate, 0, 1, 0);
            glEnable(GL_TEXTURE_2D);
            wood.bind();
            cube.draw();
            glDisable(GL_TEXTURE_2D);
          }
          glPopName();
        }
        glPopMatrix();
      }
      // Draw the tetrahedrons...
      for (Tetrahedron tetrahedron : tetrahedrons)
      {
        glPushMatrix();
        {
          glPushName(tetrahedron.id);
          {
            glTranslatef(5.0f, 1.5f, 20.0f);
            glTranslatef(tetrahedron.getX(), (float) (tetrahedron.getY() + Math.sin(occilate)), tetrahedron.getZ());
            glRotatef(rotate, 0, 1, 0);
            glEnable(GL_TEXTURE_2D);
            pokadots.bind();
            tetrahedron.draw();
            glDisable(GL_TEXTURE_2D);
          }
          glPopName();
        }
        glPopMatrix();
      }
//      if (Picker.mode == 2) Picker.stopPicking();

      // Draw the button.
      button.draw();
      glPushMatrix();
      {
        pedestal.draw();
      }
      glPopMatrix();

      // TODO skybox.draw();

      // Draw the checker-board...
      board.draw();
      board.update();

      // Draw the cubes...
      for (Cube cube : cubes)
      {
        glPushMatrix();
        {
          glTranslatef(20.0f, 1.5f, 5.0f);
          glTranslatef(cube.getX(), (float) (cube.getY() + Math.sin(occilate)), cube.getZ());
          glRotatef(rotate, 0, 1, 0);
          glEnable(GL_TEXTURE_2D);
          wood.bind();
          cube.draw();
          glDisable(GL_TEXTURE_2D);
        }
        glPopMatrix();
      }
      // Draw the tetrahedrons...
      for (Tetrahedron tetrahedron : tetrahedrons)
      {
        glPushMatrix();
        {
          glTranslatef(5.0f, 1.5f, 20.0f);
          glTranslatef(tetrahedron.getX(), (float) (tetrahedron.getY() + Math.sin(occilate)), tetrahedron.getZ());
          glRotatef(rotate, 0, 1, 0);
          glEnable(GL_TEXTURE_2D);
          pokadots.bind();
          tetrahedron.draw();
          glDisable(GL_TEXTURE_2D);
        }
        glPopMatrix();
      }
      glEnable(GL_COLOR_MATERIAL);
      for (int x = 0; x < 3; x++)
      {
        for (int y = 0; y < 3; y++)
        {
          for (int z = 0; z < 3; z++)
          {
            glPushMatrix();
            {
              glTranslatef(20.0f, 0.0f, 20.0f);
              glBegin(GL_LINE_LOOP);
              {
                glColor3f(0.0f, 1.0f, 0.0f);
                glVertex3f(1.0f, 1.0f, -1.0f);
                glVertex3f(-1.0f, 1.0f, -1.0f);
                glVertex3f(-1.0f, 1.0f, 1.0f);
                glVertex3f(1.0f, 1.0f, 1.0f);
                glColor3f(1.0f, 0.5f, 0.0f);
                glVertex3f(1.0f, -1.0f, 1.0f);
                glVertex3f(-1.0f, -1.0f, 1.0f);
                glVertex3f(-1.0f, -1.0f, -1.0f);
                glVertex3f(1.0f, -1.0f, -1.0f);
                glColor3f(1.0f, 0.0f, 0.0f);
                glVertex3f(1.0f, 1.0f, 1.0f);
                glVertex3f(-1.0f, 1.0f, 1.0f);
                glVertex3f(-1.0f, -1.0f, 1.0f);
                glVertex3f(1.0f, -1.0f, 1.0f);
                glColor3f(1.0f, 1.0f, 0.0f);
                glVertex3f(1.0f, -1.0f, -1.0f);
                glVertex3f(-1.0f, -1.0f, -1.0f);
                glVertex3f(-1.0f, 1.0f, -1.0f);
                glVertex3f(1.0f, 1.0f, -1.0f);
                glColor3f(0.0f, 0.0f, 1.0f);
                glVertex3f(-1.0f, 1.0f, 1.0f);
                glVertex3f(-1.0f, 1.0f, -1.0f);
                glVertex3f(-1.0f, -1.0f, -1.0f);
                glVertex3f(-1.0f, -1.0f, 1.0f);
                glColor3f(1.0f, 0.0f, 1.0f);
                glVertex3f(1.0f, 1.0f, -1.0f);
                glVertex3f(1.0f, 1.0f, 1.0f);
                glVertex3f(1.0f, -1.0f, 1.0f);
                glVertex3f(1.0f, -1.0f, -1.0f);
              }
              glEnd();
            }
            glPopMatrix();
            glTranslatef(0f, 0.0f, 2f);
          }
          glTranslatef(0f, 2f, -6f);
        }
        glTranslatef(2f, -6f, 0);
      }
      glDisable(GL_COLOR_MATERIAL);
      occilate += 0.025f;
      rotate++;
      button.update();
      Display.update();
      Timer.tick();
      Display.sync(60);
    }
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

  public static void main(String[] args)
  {
    GraphicsTestJake test = new GraphicsTestJake();
    test.createOpenGL();
    test.gameLoop();
    test.destroyOpenGL();
  }
}
