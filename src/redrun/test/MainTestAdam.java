package redrun.test;

import java.awt.Dimension;

import org.lwjgl.*;
import org.lwjgl.input.*;
import org.lwjgl.opengl.*;
import org.lwjgl.util.Timer;
import org.newdawn.slick.Color;

import redrun.graphics.camera.Camera;
import redrun.main.LoadingScreen;
import redrun.main.Menu;
import redrun.model.gameobject.trap.*;
import redrun.model.gameobject.world.*;
import redrun.model.gameobject.player.*;
import redrun.model.toolkit.*;
import static org.lwjgl.opengl.GL11.*;

/**
 * 
 * @author Adam Mitchell
 * @version 1.0
 * @since 2014-19-10
 *
 */
public class MainTestAdam
{

  // the camera object
  private Camera cam;
  /** time at last frame */
  private long lastFrame;
  /** frames per second */
  private int fps;
  /** last fps time */
  private long lastFPS;

  // Game objects...
  // the traps in display
  private SpikeTrapDoor spikes;
  private TrapDoor trapDoor;
  private Hammer hammer;
  private SpikeField spikeField;
  // the runners
  Runner runner1;
  Runner runner2;
  // the walls
  private CheckerBoard board;
  private CheckerBoard wallT;
  private CheckerBoard wallR;

  // Used for controlling the camera with the keyboard and mouse...
  private float dx, dy, dt;
  // Set the mouse sensitivity...
  private float mouseSensitivity = 0.05f;
  private float movementSpeed = 0.005f;

  private static BackgroundLoader backgroundLoader;

  private MainTestAdam()
  {
    this.initDisplay();
    Display.setVSyncEnabled(true);

    cam = new Camera(70, (float) Display.getWidth() / (float) Display.getHeight(), 0.3f, 1000, -10f, -3f, -10f);

    Menu menu = new Menu();

    //////// Initialize game objects 
    spikes = new SpikeTrapDoor(10, 0, 30, "wood");
    trapDoor = new TrapDoor(30, 0, 10, "wood");
    hammer = new Hammer(50, 0, 50, null);
    spikeField = new SpikeField(30, 0, 20, "s11", new Dimension(10, 15));
    runner1 = new Runner(10, 0, 10, null);
    runner2 = new Runner(15, 0, 10, null);
    board = new CheckerBoard(0, 0, 0, "x17", new Dimension(50, 50));
    wallT = new CheckerBoard(0, 10, 0, "x11", new Dimension(50, 50));
    wallR = new CheckerBoard(0, 0, 0, "24", new Dimension(50, 11));

    // Hide the mouse cursor...
    Mouse.setGrabbed(true);
    // call once before loop to initialize lastFrame
    getDelta();
    // call before loop to initialize fps timer
    lastFPS = getTime();

    while (!Display.isCloseRequested() && !Keyboard.isKeyDown(Keyboard.KEY_ESCAPE))
    {
      mainLoop();
      menu.stateControl();

      Timer.tick();
      updateFPS();
      Display.sync(65);
      Display.update();
    }
    exit();
  }
  
  /**
   * Scene rendering loop
   */
  private void mainLoop()
  {
    glEnable(GL_DEPTH_TEST);
    // while (!Display.isCloseRequested() &&
    // !Keyboard.isKeyDown(Keyboard.KEY_ESCAPE))
    {
      keyBoardControls();
      glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
      glLoadIdentity();
      
      // if (Picker.mode == 2) Picker.startPicking();
      cam.lookThrough();

      // draw the trap objects
      hammer.render();
      spikes.draw();
      trapDoor.draw();
      spikeField.draw();
      runner1.render();
      runner2.render();

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
      FontTools.renderText("x: " + cam.getX() + " y: " + cam.getY() + " z: " + cam.getZ(), 10, 10, Color.white, 1);
      FontTools.draw3D();
    }
  }

  private void keyBoardControls()
  {
    dt = getDelta();
    dx = Mouse.getDX();
    dy = Mouse.getDY();

    cam.yaw(dx * mouseSensitivity);
    cam.pitch(-dy * mouseSensitivity);

    if (Keyboard.isKeyDown(Keyboard.KEY_W)) cam.moveForward(movementSpeed * dt);
    if (Keyboard.isKeyDown(Keyboard.KEY_S)) cam.moveBackward(movementSpeed * dt);
    if (Keyboard.isKeyDown(Keyboard.KEY_A)) cam.moveLeft(movementSpeed * dt);
    if (Keyboard.isKeyDown(Keyboard.KEY_D)) cam.moveRight(movementSpeed * dt);
    if (Keyboard.isKeyDown(Keyboard.KEY_SPACE)) cam.moveUp(movementSpeed * dt);
    if (Keyboard.isKeyDown(Keyboard.KEY_X)) cam.moveDown(movementSpeed * dt);
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

    backgroundLoader = new BackgroundLoader()
    {
      protected Drawable getDrawable() throws LWJGLException
      {
        return new SharedDrawable(Display.getDrawable());
      }
    };
    try
    {
      backgroundLoader.start();
    }
    catch (LWJGLException e)
    {
    }
    // set up the loading screen
    LoadingScreen spashScreen = new LoadingScreen("loading_screen");
    FontTools.draw2D();
    while (backgroundLoader.isRunning())
    {
      glClear(GL_COLOR_BUFFER_BIT);
      spashScreen.draw();
      Display.sync(65);
      Display.update();
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
    Display.destroy();
  }

  /**
   * Gets things started
   * 
   * @param args
   */
  public static void main(String[] args)
  {
    new MainTestAdam();

  }
}