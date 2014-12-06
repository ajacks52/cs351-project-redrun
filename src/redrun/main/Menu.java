package redrun.main;

import org.lwjgl.input.Keyboard;
import org.newdawn.slick.Color;

import redrun.model.constants.Constants;
import redrun.model.toolkit.FontTools;

/**
 * The main menu. Controls the keyboard input, state control and other aspects
 * of the main menu.
 * 
 * @author Adam Mitchell, J. Jake Nichol
 * @version 1.0
 * @since 2014-19-10
 */
public class Menu
{
  Color textColor = Color.white;
  Color textSelectedColor = Color.gray;
  Color[] options = new Color[5];
  private static MenuState state = MenuState.MAIN_MENU;
  int selection = 0;
  int clients = 0;

  /**
   *
   * 
   * @author Adam Mitchell, J. Jake Nichol
   */
  public static enum MenuState
  {
    OFF, CONTROLS, HOW_TO, EXIT, MAIN_MENU, ERROR;
  }

  /**
   * Menu constructor
   */
  public Menu()
  {
    for (int i = 0; i < options.length; i++)
    {
      options[i] = textColor;
    }
    options[0] = textSelectedColor;
    selection = 0;
  }

  /**
   * 
   */
  private void checkMenuInput()
  {
    while (Keyboard.next())
    {
      if (Keyboard.getEventKey() == Keyboard.KEY_DOWN)
      {
        if (Keyboard.getEventKeyState())
        {
          if (selection < 3)
          {
            ++selection;
            options[selection - 1] = textColor;
            options[selection] = textSelectedColor;
          }
        }
      }
      if (Keyboard.getEventKey() == Keyboard.KEY_UP)
      {
        if (Keyboard.getEventKeyState())
        {
          if (selection > 0)
          {
            --selection;
            options[selection + 1] = textColor;
            options[selection] = textSelectedColor;
          }
        }
      }
      if (Keyboard.getEventKey() == Keyboard.KEY_RETURN)
      {
        if (Keyboard.getEventKeyState())
        {
          state = MenuState.values()[selection];
          System.out.println(state);
        }
      }
    }
  }

  /**
   * Allows users to press enter to return to the menu
   */
  private void backToMenuControls()
  {
    while (Keyboard.next())
    {
      if (Keyboard.getEventKey() == Keyboard.KEY_RETURN)
      {
        if (Keyboard.getEventKeyState())
        {
          state = MenuState.valueOf("MAIN_MENU");
          System.out.println(state);
        }
      }
    }
  }

  /**
   * Controls the overall game state
   * 
   * Use static field state and set it to a game state in the following way
   * 
   * state = GameState.NAME_OF_STATE
   */
  public void stateControl()
  {
    switch (state)
    {
      case MAIN_MENU:
        checkMenuInput();
        menuText();
        break;
      case OFF:
        // Do nothing
        break;
      case CONTROLS:
        backToMenuControls();
        controlsText();
        break;
      case HOW_TO:
        backToMenuControls();
        howToText();
        break;
      case EXIT:
        Main.requestClose();
        break;
      case ERROR:
        backToMenuControls();
        errorText();
        break;
      default:
        break;
    }
  }

  /**
   * Prints the main menu text
   */
  private void menuText()
  {
    FontTools.renderText("Welcome to Red Run", 70, 110, textColor, 3);

    FontTools.renderText("Back to Game", 70, 220, options[0], 2);
    FontTools.renderText("Controls", 70, 260, options[1], 2);
    FontTools.renderText("How to Play", 70, 300, options[2], 2);
    FontTools.renderText("Exit", 70, 340, options[3], 2);

    FontTools.renderText("Use the arrow keys to select an option then press enter", 70, 540, textColor, 1);
    FontTools.renderText("Number of clients must match selection", 70, 560, textColor, 1);
  }

  /**
   * Prints text about controlling the game.
   */
  private void controlsText()
  {
    FontTools.renderText("Controls", 70, 110, textColor, 3);
    FontTools.renderText("Use WASD controls to walk around the map", 70, 170, textColor, 2);
    FontTools.renderText("Press SPACE to jump", 70, 220, textColor, 2);
    FontTools.renderText("In Spectator Mode, use SPACE to move upward and SHIFT", 70, 270, textColor, 2);
    FontTools.renderText("to move downward", 70, 310, textColor, 2);
    FontTools.renderText("Press M to open the menu", 70, 360, textColor, 2);
    FontTools.renderText("Press ESC to close the game", 70, 410, textColor, 2);

    FontTools.renderText("Press enter to return the main menu", 70, 540, textColor, 1);
  }

  /**
   * Prints information about how to play the game.
   */
  private void howToText()
  {
    FontTools.renderText("How to Play", 70, 110, textColor, 3);
    FontTools.renderText(
        "Players on the BLUE team try to get from the beginning of the obstacle course to the end without dying.", 70,
        210, textColor, 1);
    FontTools.renderText("Players on the RED team try to spring traps which will kill the BLUE team players.", 70, 250,
        textColor, 1);
    FontTools.renderText("For the BLUE team to win, at least one BLUE player must reach the end of the obstacle course.", 70, 290, textColor, 1);
    FontTools.renderText("For the RED team to win, all of the BLUE players must be dead and without lives.", 70, 330, textColor, 1);

    FontTools.renderText("Press enter to return the main menu", 70, 540, textColor, 1);
  }

  /**
   * Prints error text.
   */
  private void errorText()
  {
    FontTools.renderText("I'm sorry there are only " + clients + " clients connected.", 20, 110, textColor, 2);
    FontTools.renderText("You need " + (Constants.MAX_PLAYERS - clients) + " more users connect to play a", 20, 150,
        textColor, 2);
    FontTools.renderText("Feel free to test out the controls in the", 20, 240, textColor, 2);
    FontTools.renderText("Play Ground", 20, 280, textColor, 2);

    FontTools.renderText("Press enter to return the main menu", 70, 540, textColor, 1);
  }
  
  /**
   * Gets the current state of the menu.
   * 
   * @return the menu's current state
   */
  public MenuState getState()
  {
    return state;
  }
  
  /**
   * Sets the menu to the MAIN_MENU state.
   */
  public void setState()
  {
    state = MenuState.MAIN_MENU;
  }
}
