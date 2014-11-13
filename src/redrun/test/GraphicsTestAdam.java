package redrun.test;

import java.util.Random;

import org.lwjgl.LWJGLException;
import org.lwjgl.Sys;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.util.Timer;
import org.newdawn.slick.Color;

import redrun.graphics.camera.Camera;
import redrun.graphics.selection.Picker;
import redrun.model.gameobject.trap.*;
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
 */
public class GraphicsTestAdam
{
  Camera cam;
  Random rand;

  /**
   * Mostly copied Troy's mainLoop code to set the basic scene added walls and a
   * few small testing tweaks
   * 
   * Loads the fonts
   * 
   */
  public void mainLoop()
  {
    // load font takes a about 2 seconds
    redrun.model.toolkit.FontTools.loadFonts(15);

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

    boolean fall = false;
    glEnable(GL_DEPTH_TEST);
    Spike ss = new Spike(1, 1, 1);
    TrapDoor td = new TrapDoor(1,1,1);
    

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
      if (Keyboard.isKeyDown(Keyboard.KEY_F)) Picker.mode = 2;
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

      glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

      glLoadIdentity();
      if (Picker.mode == 2) Picker.startPicking();
      cam.lookThrough();

      ss.drawSpikes(10, 0);
      ss.update();
      ss.drawSpikes(30, 0);
      ss.update();
      td.drawTrapDoor(50, 0);
      td.update();
      
      
      if (Picker.mode == 2) Picker.stopPicking();

      drawBuilding();
      FontTools.draw2D();
      FontTools.renderText("y\t: " + cam.getY() + "\tx: " + cam.getX() + "\tz: " + cam.getZ(), 10, 10, Color.white, 0);
      FontTools.draw3D();
      Timer.tick();
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
        if ((x + y) % 2 == 0) glColor3f(0.0f, 0.5f, 1.0f);
        else glColor3f(0.0f, 0.0f, 1.0f);

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
   * Sets up the screen 800x600
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
   * 
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