package redrun.main;

/**
 * Lab 7 cs351 11/05/2014
 * @author Adam Mitcehll
 * 
 * Features 
 * - The triangles and boxes move around about their x,y,z axis'
 * - move around and with arrow keys 
 * - zoom in and out with + or - keys 
 * - rotate about the -z, +z axis with return key or shift key
 * - go back to the starting location with space bar
 */

import java.util.Random;

import org.lwjgl.LWJGLException;
import org.lwjgl.Sys;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;

import redrun.graphics.camera.Camera;
import static org.lwjgl.opengl.GL11.*;

public class Driver
{
  static long last_frame;

  public static void main(String[] args)
  {
    Driver drive = new Driver();
    drive.initDisplay();
    drive.mainLoop();
    drive.exit();
  }

  public void mainLoop()
  {
    Camera cam = new Camera(100, (float) Display.getWidth() / (float) Display.getHeight(), 0.3f, 1000, 0, 0, 0);
    float x = 0;
    @SuppressWarnings("unused")
    float y = 0;

    Random rand = new Random();
    int[] coordsX = new int[2000];
    int[] coordsY = new int[2000];
    int[] coordsZ = new int[2000];
    for (int i = 0; i < 50; i++)
    {
      coordsX[i] = rand.nextInt(70) - 50;
      coordsY[i] = rand.nextInt(70) - 50;
      coordsZ[i] = rand.nextInt(70) - 50;
    }

    while (!Display.isCloseRequested())
    {
      glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
      glLoadIdentity();
      cam.lookThrough();

      int delta = getDelta();

      if (Keyboard.isKeyDown(Keyboard.KEY_ADD) || Keyboard.isKeyDown(Keyboard.KEY_EQUALS))
      {
        float n = cam.getZ();
        n += (0.07f * delta);
        System.out.println(n);
        cam.pitch(n);
      }
      if (Keyboard.isKeyDown(Keyboard.KEY_SUBTRACT) || Keyboard.isKeyDown(Keyboard.KEY_MINUS))
      {
        float n = cam.getZ();
        n -= (0.07f * delta);
        System.out.println(n);
        cam.moveDown(n);
      }
      if (Keyboard.isKeyDown(Keyboard.KEY_LEFT))
      {
        float n = cam.getX();
        n += (0.07f * delta);
        // cam.setRY(n);
        System.out.println(n);
        cam.moveLeft(n);
      }
      if (Keyboard.isKeyDown(Keyboard.KEY_RIGHT))
      {
        float n = cam.getX();
        n -= (0.07f * delta);
        System.out.println(n);
        cam.moveRight(n);
      }
      if (Keyboard.isKeyDown(Keyboard.KEY_UP))
      {
        float n = cam.getY();
        n -= (0.07f * delta);
        System.out.println(n);
        cam.moveUp(n);
      }
      if (Keyboard.isKeyDown(Keyboard.KEY_DOWN))
      {
        float n = cam.getY();
        n += (0.07f * delta);
        System.out.println(n);
       // if (n < 0)
        {
          cam.moveDown(n);
        }
      }
      if (Keyboard.isKeyDown(Keyboard.KEY_RETURN))
      {
//        float n = cam.getRZ();
//        n += (0.01f * delta);
//        if (n < 0)
//        {
//          cam.moveDown(n);
//        }
      }
      if (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT) || Keyboard.isKeyDown(Keyboard.KEY_RSHIFT))
      {
//        float n = cam.getRZ();
//        n -= (0.01f * delta);
//        if (n < -1)
//        {
//          cam.setY(n);
//        }
      }
      if (Keyboard.isKeyDown(Keyboard.KEY_SPACE))
      {
        cam.moveUp(0);
        cam.moveRight(0);
        cam.moveDown(0);
//        cam.pitch(0);
//        cam.yaw(0);
       }

      drawFloor();
      // drawCoords();

      for (int i = 0; i < 50; i++)
      {
        buildBox(x, coordsZ[i], coordsX[i], coordsY[i], 1, 1, 1);
        buildTetaedron(x, coordsY[i], coordsZ[i], coordsX[i], -1, -1, -1);

      }
      x += 0.15f;

      Display.update();
    }
  }

  public void drawFloor()
  {

    int GridSizeX = 16;
    int GridSizeY = 16;
    int SizeX = 8;
    int SizeY = 8;

    // glMatrixMode(GL_MODELVIEW);
    // glLoadIdentity();

    // glMatrixMode(GL_PROJECTION);
    // glLoadIdentity();
    // glOrtho(0,GridSizeX*SizeX,0,GridSizeY*SizeY,-1.0,1.0);

    glPushMatrix();
    glTranslatef(0, 0, -100);
    glBegin(GL_QUADS);
    for (int x = 0; x < GridSizeX; ++x)
      for (int y = 0; y < GridSizeY; ++y)
      {
        if ((x + y) % 2 == 0)
          glColor3f(0.0f, 1.0f, 1.0f);
        else
          glColor3f(0.0f, 0.0f, 1.0f);

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
        if ((x + y) % 2 == 0)
          glColor3f(0.0f, 0.0f, 1.0f);
        else
          glColor3f(0.0f, 1.0f, 1.0f);

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
        if ((x + y) % 2 == 0)
          glColor3f(0.0f, 0.0f, 1.0f);
        else
          glColor3f(0.0f, 1.0f, 1.0f);

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
        if ((x + y) % 2 == 0)
          glColor3f(0.0f, 1.0f, 1.0f);
        else
          glColor3f(0.0f, 0.0f, 1.0f);

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
        if ((x + y) % 2 == 0)
          glColor3f(0.0f, 1.0f, 1.0f);
        else
          glColor3f(0.0f, 0.0f, 1.0f);

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
        if ((x + y) % 2 == 0)
          glColor3f(0.0f, 1.0f, 1.0f); // white
        else
          glColor3f(0.0f, 0.0f, 1.0f); // black

        glVertex3f(x * SizeX, y * SizeY, 128);
        glVertex3f((x + 1) * SizeX, y * SizeY, 128);
        glVertex3f((x + 1) * SizeX, (y + 1) * SizeY, 128);
        glVertex3f(x * SizeX, (y + 1) * SizeY, 128);

      }
    glEnd();

    glPopMatrix();
  }

  public void drawCoords()
  {

    glLineWidth(5);
    glColor3f(1f, 0f, 0f);
    glVertex3f(10, 10, -1);
    glVertex3f(50, 10, -1);

  }

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

  public int getDelta()
  {
    long time = getTime();
    int delta = (int) (time - last_frame);
    last_frame = time;

    return delta;
  }

  public long getTime()
  {
    return ((Sys.getTime() * 1000) / Sys.getTimerResolution());
  }

  public void exit()
  {
    Display.destroy();
  }

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
}