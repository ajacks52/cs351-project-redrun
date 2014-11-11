package redrun.test;

import java.util.Random;

import org.lwjgl.LWJGLException;
import org.lwjgl.Sys;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;

import redrun.graphics.camera.Camera;
import redrun.graphics.selection.Picker;
import redrun.model.toolkit.*;
import static org.lwjgl.opengl.GL11.*;

/**
 * 
 * Class to show how to draw text, also does a few other camera adjustments to
 * mimic a person walking and jumping these are mainly just for testing purposes
 * 
 * @author Adam Mitchell
 * @version 1.0
 * @since 2014-11-10
 * 
 *        Features:
 */
public class GraphicsTestAdam
{

  // Settings for how materials react to lighting...
  /** Shininess level. */
  public static float shininess = 0.0f;

  /** Specularity level. */
  public static float specularity = 0.0f;

  /** Emmision level. */
  public static float emission = 0.0f;

  Camera cam;

  /**
   * Mostly copied Troy's mainLoop code to set the basic scene added walls and a
   * few small testing tweaks
   */
  public void mainLoop()
  {

    Random rand = new Random();
    int[] coordsX = new int[2000];
    int[] coordsY = new int[2000];
    int[] coordsZ = new int[2000];
    for (int i = 0; i < 500; i++)
    {
      coordsX[i] = rand.nextInt(550) - 300;
      coordsY[i] = rand.nextInt(550) - 300;
      coordsZ[i] = rand.nextInt(550) - 300;
    }

    cam = new Camera(70, (float) Display.getWidth() / (float) Display.getHeight(), 0.3f, 1000, 0f, 0f, 0f);

    // Used for controlling the camera with the keyboard and mouse...
    float dx = 0.0f;
    float dy = 0.0f;
    float dt = 0.0f;
    float previousTime = 0.0f;
    float currentTime = 0.0f;

    // Set the mouse sensitivity...
    float mouseSensitivity = 0.05f;
    float movementSpeed = 10.0f;

    // Hide the mouse cursor...
    Mouse.setGrabbed(true);

    float theta = 0;
    boolean shininessToggle = false;
    boolean specularityToggle = false;
    boolean emissionToggle = false;

    boolean fall = false;
    glEnable(GL_DEPTH_TEST);

    while (!Display.isCloseRequested() && !Keyboard.isKeyDown(Keyboard.KEY_ESCAPE))
    {

      currentTime = Sys.getTime();
      dt = (currentTime - previousTime) / 1000.0f;
      previousTime = currentTime;

      dx = Mouse.getDX();
      dy = Mouse.getDY();

      cam.yaw(dx * mouseSensitivity);
      cam.pitch(-dy * mouseSensitivity);

      if (Keyboard.isKeyDown(Keyboard.KEY_W)) cam.moveForward(movementSpeed * dt);

      if (Keyboard.isKeyDown(Keyboard.KEY_S)) cam.moveBackward(movementSpeed * dt);

      if (Keyboard.isKeyDown(Keyboard.KEY_A)) cam.moveLeft(movementSpeed * dt);

      if (Keyboard.isKeyDown(Keyboard.KEY_D)) cam.moveRight(movementSpeed * dt);

      if (Keyboard.isKeyDown(Keyboard.KEY_SPACE)) cam.moveUp(movementSpeed * dt);

      if (Keyboard.isKeyDown(Keyboard.KEY_DOWN)) cam.moveDown(movementSpeed * dt);

      if (Keyboard.isKeyDown(Keyboard.KEY_J)) shininessToggle = !shininessToggle;

      if (Keyboard.isKeyDown(Keyboard.KEY_K)) specularityToggle = !specularityToggle;

      if (Keyboard.isKeyDown(Keyboard.KEY_L)) emissionToggle = !emissionToggle;

      if (Keyboard.isKeyDown(Keyboard.KEY_F)) Picker.mode = 2;

      if (Keyboard.isKeyDown(Keyboard.KEY_SPACE))
      {

        System.out.println(cam.getY());

      }
      if (cam.getY() < -1 && !Keyboard.isKeyDown(Keyboard.KEY_SPACE) || cam.getY() < -5)
      {
        fall = true;
      }
      if (cam.getY() == 0)
      {
        fall = false;
      }

      if (fall)
      {
        cam.moveDown(movementSpeed * dt * 2);
      }

      if (shininessToggle) shininess = 25.0f;
      else shininess = 12.0f;

      if (specularityToggle) specularity = 1.0f;
      else specularity = 0.3f;

      if (emissionToggle) emission = 0.05f;
      else emission = 0.0f;

      glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

      glLoadIdentity();
      cam.lookThrough();

      drawBuilding();

      for (int i = 0; i < 500; i++)
      {
        buildBox(theta, coordsZ[i], coordsX[i], coordsY[i], 1, 1, 1);
        buildTetaedron(theta, coordsY[i], coordsZ[i], coordsX[i], -1, -1, -1);
      }
      theta += 0.15f;

      FontTools.draw2D();
      FontTools.renderText("Line of text one ", 10, 10);
      FontTools.renderText("Line of text two ", 10, 25);
      FontTools.renderText("Dynamic line of text, y " + cam.getY() + " x: " + cam.getX(), 10, 40);
      FontTools.draw3D();

      Display.update();
    }
  }

  /**
   * Draw the 6 walls of the building using glPush and glPop
   */
  public void drawBuilding()
  {

    int GridSizeX = 16;
    int GridSizeY = 16;
    int SizeX = 8;
    int SizeY = 8;

    glPushMatrix();
    glTranslatef(-1, -4, -100);
    glBegin(GL_QUADS);
    for (int x = 0; x < GridSizeX; ++x)
      for (int y = 0; y < GridSizeY; ++y)
      {
        if ((x + y) % 2 == 0) glColor3f(0.0f, 0.5f, 1.0f);
        else glColor3f(0.0f, 0.0f, 1.0f);

        glVertex3f(x * SizeX, 0, y * SizeY);
        glVertex3f((x + 1) * SizeX, 0, y * SizeY);
        glVertex3f((x + 1) * SizeX, 0, (y + 1) * SizeY);
        glVertex3f(x * SizeX, 0, (y + 1) * SizeY);

      }
    glEnd();

    glBegin(GL_QUADS);
    for (int x = 0; x < GridSizeX; ++x)
      for (int y = 0; y < GridSizeY; ++y)
      {
        if ((x + y) % 2 == 0) glColor3f(0.0f, 0.0f, 1.0f);
        else glColor3f(0.0f, 0.5f, 1.0f);

        glVertex3f(x * SizeX, 128, y * SizeY);
        glVertex3f((x + 1) * SizeX, 128, y * SizeY);
        glVertex3f((x + 1) * SizeX, 128, (y + 1) * SizeY);
        glVertex3f(x * SizeX, 128, (y + 1) * SizeY);

      }
    glEnd();

    glBegin(GL_QUADS);
    for (int x = 0; x < GridSizeX; ++x)
      for (int y = 0; y < GridSizeY; ++y)
      {
        if ((x + y) % 2 == 0) glColor3f(0.0f, 0.0f, 1.0f);
        else glColor3f(0.0f, 0.5f, 1.0f);

        glVertex3f(0, x * SizeX, y * SizeY);
        glVertex3f(0, (x + 1) * SizeX, y * SizeY);
        glVertex3f(0, (x + 1) * SizeX, (y + 1) * SizeY);
        glVertex3f(0, x * SizeX, (y + 1) * SizeY);

      }
    glEnd();

    glBegin(GL_QUADS);
    for (int x = 0; x < GridSizeX; ++x)
      for (int y = 0; y < GridSizeY; ++y)
      {
        if ((x + y) % 2 == 0) glColor3f(0.0f, 0.5f, 1.0f);
        else glColor3f(0.0f, 0.0f, 1.0f);

        glVertex3f(128, x * SizeX, y * SizeY);
        glVertex3f(128, (x + 1) * SizeX, y * SizeY);
        glVertex3f(128, (x + 1) * SizeX, (y + 1) * SizeY);
        glVertex3f(128, x * SizeX, (y + 1) * SizeY);

      }
    glEnd();

    glBegin(GL_QUADS);
    for (int x = 0; x < GridSizeX; ++x)
      for (int y = 0; y < GridSizeY; ++y)
      {
        if ((x + y) % 2 == 0) glColor3f(0.0f, 0.5f, 1.0f);
        else glColor3f(0.0f, 0.0f, 1.0f);

        glVertex3f(x * SizeX, y * SizeY, 0);
        glVertex3f((x + 1) * SizeX, y * SizeY, 0);
        glVertex3f((x + 1) * SizeX, (y + 1) * SizeY, 0);
        glVertex3f(x * SizeX, (y + 1) * SizeY, 0);

      }
    glEnd();

    glBegin(GL_QUADS);
    for (int x = 0; x < GridSizeX; ++x)
      for (int y = 0; y < GridSizeY; ++y)
      {
        if ((x + y) % 2 == 0) glColor3f(0.0f, 0.5f, 1.0f); // white
        else glColor3f(0.0f, 0.0f, 1.0f); // black

        glVertex3f(x * SizeX, y * SizeY, 128);
        glVertex3f((x + 1) * SizeX, y * SizeY, 128);
        glVertex3f((x + 1) * SizeX, (y + 1) * SizeY, 128);
        glVertex3f(x * SizeX, (y + 1) * SizeY, 128);

      }
    glEnd();

    glPopMatrix();
  }

  /**
   * Builds the small star like boxes seen throughout the scene
   * 
   * @param the rotation angle of the box
   * @param the translation amount in the x dir
   * @param the translation amount in the y dir
   * @param the translation amount in the z dir
   * @param x the axis of rotation
   * @param y the axis of rotation
   * @param z the axis of rotation
   */
  private void buildBox(float theta, float posX, float posZ, float posY, float x, float y, float z)
  {
    glPushMatrix();
    {
      glTranslatef(posX, posY, posZ);
      glRotatef(theta, x, y, z);
      glBegin(GL_QUADS);
      // FrontFace
      glColor3f(1f, 0f, 0f);
      glNormal3f(0.0f, 0.0f, 1.0f);
      glVertex3f(-1, -1, 1);
      glVertex3f(-1, 1, 1);
      glVertex3f(1, 1, 1);
      glVertex3f(1, -1, 1);
      // BackFace
      glNormal3f(0.0f, 0.0f, -1.0f);
      glColor3f(0.2f, 0.2f, 0.2f);
      glVertex3f(-1, -1, -1);
      glVertex3f(-1, 1, -1);
      glVertex3f(1, 1, -1);
      glVertex3f(1, -1, -1);
      // BottomFace
      glNormal3f(0.0f, -1.0f, 0.0f);
      glColor3f(0.4f, 0.5f, 0.9f);
      glVertex3f(-1, -1, -1);
      glVertex3f(-1, -1, 1);
      glVertex3f(-1, 1, 1);
      glVertex3f(-1, 1, -1);
      // TopFace
      glNormal3f(0.0f, 1.0f, 0.0f);
      glColor3f(0.0f, 0.5f, 0.5f);
      glVertex3f(1, -1, -1);
      glVertex3f(1, -1, 1);
      glVertex3f(1, 1, 1);
      glVertex3f(1, 1, -1);
      // LeftFace
      glNormal3f(-1.0f, 0.0f, 0.0f);
      glColor3f(0.0f, 0.5f, 0f);
      glVertex3f(-1, -1, -1);
      glVertex3f(1, -1, -1);
      glVertex3f(1, -1, 1);
      glVertex3f(-1, -1, 1);
      // Right Face
      glNormal3f(1.0f, 0.0f, 0.0f);
      glColor3f(1.0f, 0.5f, 0.4f);
      glVertex3f(-1, 1, -1);
      glVertex3f(1, 1, -1);
      glVertex3f(1, 1, 1);
      glVertex3f(-1, 1, 1);
      glEnd();
    }
    glPopMatrix();
  }

  /**
   * Builds the small star like tetaedrons seen throughout the scene
   * 
   * param the rotation angle of the triangle
   * 
   * @param the translation amount in the x dir
   * @param the translation amount in the y dir
   * @param the translation amount in the z dir
   * @param x the axis of rotation
   * @param y the axis of rotation
   * @param z the axis of rotation
   */
  private void buildTetaedron(float theta, float posX, float posZ, float posY, float x, float y, float z)
  {

    glPushMatrix();
    {
      glTranslatef(posX, posY, posZ);
      glRotatef(theta, x, y, z);
      glBegin(GL_TRIANGLE_STRIP);

      glNormal3f(1.0f, 0.0f, 1.0f);
      glColor3f(1.0f, 1.0f, 0.0f);
      glVertex3f(0.0f, 1.f, 0.0f);
      glVertex3f(-1.0f, -1.0f, 1.0f);
      glVertex3f(1.0f, -1.0f, 1.0f);

      glNormal3f(1.0f, 0.0f, 1.0f);
      glColor3f(1.0f, 0.0f, 0.0f);
      glVertex3f(0.0f, 1.0f, 0.0f);
      glVertex3f(-1.0f, -1.0f, 1.0f);
      glVertex3f(0.0f, -1.0f, -1.0f);

      glNormal3f(1.0f, 0.0f, 1.0f);
      glColor3f(0.0f, 1.0f, 0.0f);
      glVertex3f(0.0f, 1.0f, 0.0f);
      glVertex3f(0.0f, -1.0f, -1.0f);
      glVertex3f(1.0f, -1.0f, 1.0f);

      glNormal3f(1.0f, 0.0f, 1.0f);
      glColor3f(0.0f, 0.0f, 1.0f);
      glVertex3f(-1.0f, -1.0f, 1.0f);
      glVertex3f(0.0f, -1.0f, -1.0f);
      glVertex3f(1.0f, -1.0f, 1.0f);

      glEnd();
    }
    glPopMatrix();
  }

  /**
   * Sets up the screen 800x600 and loads the fonts takes a away to run cause the
   * fonts take about 2 seconds to load
   */
  public void initDisplay()
  {
    try
    {
      Display.setDisplayMode(new DisplayMode(800, 600));
      Display.create();
    }
    catch (LWJGLException ex)
    {
      ex.printStackTrace();
    }

    // load font takes a about 2 seconds
    FontTools.loadFonts();
  }

  /**
   * Cleans everything up, destroys the fonts and the display
   */
  public void exit()
  {
    FontTools.cleanUpFonts();
    Display.destroy();
  }

  /**
   * Gets things started
   * @param args
   */
  public static void main(String[] args)
  {
    GraphicsTestAdam driver = new GraphicsTestAdam();
    driver.initDisplay();
    driver.mainLoop();
    driver.exit();
  }
}