package redrun.model.toolkit;

import org.lwjgl.opengl.Display;
import org.newdawn.slick.Color;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.UnicodeFont;
import org.newdawn.slick.font.effects.ColorEffect;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.util.glu.GLU.gluPerspective;

/**
 * This class is a utility class to draw fonts to the screen
 * 
 * @author Adam Mitchell
 * @version 1.0
 * @since 2014-11-10
 */
public class FontTools
{
  private static UnicodeFont font;

  /**
   * Loads the fonts to be used for drawing text. Default font is Helvetica and
   * size 18 feel free to change to any font supported by java.awt.Font
   * 
   * Takes a few seconds to load font so account for this when you call this
   * method. Should only be called once while initializing for the first time.
   */
  @SuppressWarnings("unchecked")
  public static void loadFonts()
  {
    java.awt.Font awtFont = new java.awt.Font("Helvetica", java.awt.Font.BOLD, 18);
    font = new UnicodeFont(awtFont);
    font.getEffects().add(new ColorEffect(java.awt.Color.white));
    font.addAsciiGlyphs();

    try
    {
      font.loadGlyphs();
    }
    catch (SlickException e)
    {
      e.printStackTrace();
    }
  }

  /**
   * Draws a line of text to the screen
   * 
   * @param the text to be drawn
   * @param x coordinate of screen where text is drawn
   * @param y coordinate of screen where text is drawn
   */
  public static void renderText(String text, int x, int y, Color color)
  {
    font.drawString(x, y, text, color);
  }

  /**
   * Switches the drawing mode from 3d to 2d
   */
  public static void draw2D()
  {
    glEnable(GL_TEXTURE_2D);
    glShadeModel(GL_SMOOTH);
    glDisable(GL_DEPTH_TEST);
    glDisable(GL_LIGHTING);
    glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
    glClearDepth(1);
    glEnable(GL_BLEND);
    glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
    glViewport(0, 0, Display.getWidth(), Display.getHeight());
    glMatrixMode(GL_MODELVIEW);
    glMatrixMode(GL_PROJECTION);
    glLoadIdentity();
    glOrtho(0, Display.getWidth(), Display.getHeight(), 0, 1, -1);
    glMatrixMode(GL_MODELVIEW);
    glLoadIdentity();
  }

  /**
   * Switches the drawing mode from 2d to 3d
   */
  public static void draw3D()
  {
    glDisable(GL_TEXTURE_2D);
    glEnable(GL_DEPTH_TEST);
    glDisable(GL_BLEND);
    glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
    glMatrixMode(GL_PROJECTION);
    glLoadIdentity();
    gluPerspective(70, (float) Display.getWidth() / (float) Display.getHeight(), 0.3f, 1000f);
    glMatrixMode(GL_MODELVIEW);
    glDepthFunc(GL_LEQUAL);
    glHint(GL_PERSPECTIVE_CORRECTION_HINT, GL_NICEST);
  }

  /**
   * Cleans up and destroys the fonts. Should be called when windows is closed
   * and game is exited.
   */
  public static void cleanUpFonts()
  {
    font.destroy();
  }

}
