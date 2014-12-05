package redrun.main;


import org.lwjgl.input.Keyboard;
import org.newdawn.slick.Color;

import redrun.model.toolkit.FontTools;

/**
 * 
 * @author Adam Mitchell
 * @version 1.0
 * @since 2014-19-10
 *
 * The main menu. Controls the keyboard input, state control and other aspects of the main menu
 *
 */
public class Menu
{

  Color textColor = Color.white;
  Color textSelectedColor = Color.darkGray;
  Color[] options = new Color[5];
  private static GameState state = GameState.MAIN_MENU;
  int selection = 0;
  int clients = 0;

  /**
   * @author Adam Mitchell
   * The game states 
   * PLAYGOUND(1), TWO_PLAYER(2), THREE_PLAYER(3), FOUR_PLAYER(4), 
   * CONTROLS(-1), MAIN_MENU(-1), ERROR(-1);
   */
  public static enum GameState
  {
    PLAYGOUND(1), TWO_PLAYER(2), THREE_PLAYER(3), FOUR_PLAYER(4), CONTROLS(-1), MAIN_MENU(-1), ERROR(-1);
    
    int value;
    private GameState(int num)
    {
      this.value = num;
    }
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
          if (selection < 4)
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
          state = GameState.values()[selection];
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
          state = GameState.valueOf("MAIN_MENU");
          System.out.println(state);
        }
      }
    }
  }

  /**
   * Controls the overall game state
   * 
   * Use static field state and set it to a game state 
   * in the following way 
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
      case PLAYGOUND:
        // mainLoop();
        break;
      case TWO_PLAYER:
        // add two player code
        if(clients != 2) {
          state = GameState.ERROR;
          state.value = 2;
        }
        
        break;
      case THREE_PLAYER:
        // add three player code
        if(clients != 3) {
          state = GameState.ERROR;
          state.value = 3;
        }
        
        break;
      case FOUR_PLAYER:
        // add four player code
        if(clients != 4) {
          state = GameState.ERROR;
          state.value = 4;
        }
        
        break;
      case CONTROLS:
        backToMenuControls();
        controlsText();
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
   * Prints error text
   */
  private void errorText()
  {
    FontTools.renderText("Connected Clients 0", 650, 7, textColor, 0);

    FontTools.renderText("I'm sorry there are only " + clients + " clients connected.", 20, 110, textColor, 2);
    FontTools.renderText("You need " + (state.value-clients) +" more users connect to play a", 20, 150, textColor, 2);
    FontTools.renderText( state.value + " player round.", 20, 190, textColor, 2);
    FontTools.renderText("Feel free to test out the controls in the", 20, 240, textColor, 2);
    FontTools.renderText("Play Ground", 20, 280, textColor, 2);

    FontTools.renderText("Press enter to return the main menu", 20, 540, textColor, 1);
  }

  /**
   * Prints how to play text
   */
  private void controlsText()
  {
    FontTools.renderText("Connected Clients 0", 650, 7, textColor, 0);

    FontTools.renderText("How to play...", 70, 110, textColor, 3);

    FontTools.renderText("Press enter to return the main menu", 20, 540, textColor, 1);
  }

  /**
   * Prints the main menu text
   */
  private void menuText()
  {
    FontTools.renderText("Connected Clients 0", 650, 7, textColor, 0);

    FontTools.renderText("Welcome to Red Run", 70, 110, textColor, 3);

    FontTools.renderText("Practice in the Playground", 70, 180, options[0], 2);
    FontTools.renderText("Play Two Player Round", 70, 220, options[1], 2);
    FontTools.renderText("Play Three Player Round", 70, 260, options[2], 2);
    FontTools.renderText("Play Four Player Round", 70, 300, options[3], 2);
    FontTools.renderText("Show Game Controls", 70, 340, options[4], 2);

    FontTools.renderText("Use the arrow keys to selcet an option then press enter", 20, 540, textColor, 1);
    FontTools.renderText("Number of clinets must match selection", 20, 560, textColor, 1);
  }
}
