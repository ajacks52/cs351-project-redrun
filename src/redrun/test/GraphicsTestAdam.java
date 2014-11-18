package redrun.test;

import java.awt.Dimension;
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
import redrun.model.gameobject.trap.*;
import redrun.model.gameobject.world.CheckerBoard;
import redrun.model.toolkit.*;
import static org.lwjgl.opengl.GL11.*;

/**
 * 
 * Renders a scene so show how the traps work. To activate a trap press the r or
 * f keys. Loads and uses fonts, and applies textures to the walls
 * 
 * @author Adam Mitchell
 * @version 1.0
 * @since 2014-11-10
 * 
 */
public class GraphicsTestAdam
{
  // the camera object
  private Camera cam;
  @SuppressWarnings("unused")
  private Random rand;

  /** time at last frame */
  private long lastFrame;
  /** frames per second */
  private int fps;
  /** last fps time */
  private long lastFPS;

  // the traps in display
  private Spikes spikes;
  private TrapDoor trapDoor;
  private Hammer hammer;
  private SpikeField spikeField;
  private SpikeTrench spikeTrench;
  // the walls
  private CheckerBoard board;
  private CheckerBoard wallT;
  private CheckerBoard wallR;

  // Used for controlling the camera with the keyboard and mouse...
  private float dx, dy, dt;
  private float previousTime, currentTime;
  // Set the mouse sensitivity...
  private float mouseSensitivity = 0.05f;
  private float movementSpeed = 10.0f;

  private GraphicsTestAdam()
  {
    this.initDisplay();
    // load font takes a about 2 seconds
    redrun.model.toolkit.FontTools.loadFonts(15);

    cam = new Camera(70, (float) Display.getWidth() / (float) Display.getHeight(), 0.3f, 1000, -10f, -3f, -10f);

    spikes = new Spikes(1, 1, 1, "wood");
    trapDoor = new TrapDoor(1, 1, 1, "wood");
    hammer = new Hammer(50, 0, 50, null);
    spikeField = new SpikeField(0, 0, 0, "s11", new Dimension(10, 15));
    spikeTrench = new SpikeTrench(0, 0, 0, "s11", new Dimension(10, 5));

    board = new CheckerBoard(0, 0, 0, "x17", new Dimension(50, 50));
    wallT = new CheckerBoard(0, 10, 0, "x11", new Dimension(50, 50));
    wallR = new CheckerBoard(0, 0, 0, "brickwall6", new Dimension(50, 11));

    // Hide the mouse cursor...
    Mouse.setGrabbed(true);
    getDelta(); // call once before loop to initialize lastFrame
    lastFPS = getTime(); // call before loop to initialize fps timer

    this.mainLoop();
    this.exit();
  }

  /**
   * Scene rendering loop
   * 
   */
  private void mainLoop()
  {
    glEnable(GL_DEPTH_TEST);
    while (!Display.isCloseRequested() && !Keyboard.isKeyDown(Keyboard.KEY_ESCAPE))
    {

      keyBoard();
      glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

      glLoadIdentity();
      // if (Picker.mode == 2) Picker.startPicking();
      cam.lookThrough();

      // draw the trap objects
      hammer.render();
      spikes.render(10, 0, 10);
      trapDoor.render(30, 0, 10);
      spikeField.render(30, 0, 20);
      spikeTrench.render(0, 0, 20);

      // if (Picker.mode == 2) Picker.stopPicking();

      // draw the floor
      board.draw();
      // draw a left wall
      glPushMatrix();
      glTranslatef(0, -1, -1);
      glRotatef(-90.0f, 1.0f, 0.0f, 0.0f);
      wallR.draw();
      glPopMatrix();
      // draw the ceiling
      wallT.draw();

      // drawing 2d text
      FontTools.draw2D();
      FontTools.renderText("x: " + cam.getX() + " y: " + cam.getY() + " z: " + cam.getZ(), 10, 10, Color.white, 0);
      FontTools.draw3D();
      Timer.tick();
      updateFPS();
      Display.sync(65);
      Display.update();
    }
  }

  private void keyBoard()
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
    if (Keyboard.isKeyDown(Keyboard.KEY_SPACE) || Keyboard.isKeyDown(Keyboard.KEY_UP)) cam.moveUp(movementSpeed * dt);
    if (Keyboard.isKeyDown(Keyboard.KEY_X) || Keyboard.isKeyDown(Keyboard.KEY_DOWN)) cam.moveDown(movementSpeed * dt);
    if (Keyboard.isKeyDown(Keyboard.KEY_F)) spikes.interact();
    if (Keyboard.isKeyDown(Keyboard.KEY_R)) trapDoor.interact();
  }

  /**
   * Sets up the screen 800x600
   */
  private void initDisplay()
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
   * Calculate how many milliseconds have passed since last frame.
   * 
   * @return milliseconds passed since last frame
   */
  private int getDelta()
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
  private long getTime()
  {
    return (Sys.getTime() * 1000) / Sys.getTimerResolution();
  }

  /**
   * Calculate the FPS and set it in the title bar
   */
  private void updateFPS()
  {
    if (getTime() - lastFPS > 1000)
    {
      Display.setTitle("My test level FPS: " + fps);
      fps = 0;
      lastFPS += 1000;
    }
    fps++;
  }

  /**
   * Cleans everything up, destroys the fonts and the display
   */
  private void exit()
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
    new GraphicsTestAdam();

  }
}