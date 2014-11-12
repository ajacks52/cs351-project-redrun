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
  private static UnicodeFont fontBig;
  private static UnicodeFont fontBigger;
  private static UnicodeFont fontCustom;
  private static int customSize = 0;


  /**
   * Loads the fonts to be used for drawing text. Default fonts all Helvetica and
   * size 18, 22, and 44 feel free to come in here and change to any font supported by java.awt.Font
   * To load a different size font call this method with a parameter greater than 0
   * 
   * Takes a few seconds to load font so account for this when you call this
   * method. Should only be called once while initializing for the first time.
   * 
   */
  @SuppressWarnings("unchecked")
  public static void loadFonts(int size)
  {
    customSize = size;
    java.awt.Font awtFont = new java.awt.Font("Helvetica", java.awt.Font.BOLD, 18);
    font = new UnicodeFont(awtFont);
    font.getEffects().add(new ColorEffect(java.awt.Color.white));
    font.addAsciiGlyphs();

    java.awt.Font awtFontBig = new java.awt.Font("Helvetica", java.awt.Font.BOLD, 22);
    fontBig = new UnicodeFont(awtFontBig);
    fontBig.getEffects().add(new ColorEffect(java.awt.Color.white));
    fontBig.addAsciiGlyphs();

    java.awt.Font awtFontBigger = new java.awt.Font("Helvetica", java.awt.Font.BOLD, 44);
    fontBigger = new UnicodeFont(awtFontBigger);
    fontBigger.getEffects().add(new ColorEffect(java.awt.Color.white));
    fontBigger.addAsciiGlyphs();
    
    if(size > 0)
    {
      java.awt.Font awtCustom = new java.awt.Font("Helvetica", java.awt.Font.BOLD, size);
      fontCustom = new UnicodeFont(awtCustom);
      fontCustom.getEffects().add(new ColorEffect(java.awt.Color.white));
      fontCustom.addAsciiGlyphs();
    }

    try
    {
      font.loadGlyphs();
      fontBig.loadGlyphs();
      fontBigger.loadGlyphs();
      if(size > 0)  fontCustom.loadGlyphs();
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
   * @param font size 0 = 18pt, 1 = 22pt, 2 = 44pt, 3 = custom size
   *
   */
  public static void renderText(String text, int x, int y, Color color, int size)
  {
    if (size == 0) font.drawString(x, y, text, color);
    if (size == 1) fontBig.drawString(x, y, text, color);
    if (size == 2) fontBigger.drawString(x, y, text, color);
    if (size == 3 && customSize > 0) fontCustom.drawString(x, y, text, color);
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
