package redrun.test.database;

import redrun.database.Character;

/**
 * Testing class for the Character class
 * 
 * @author Jayson Grace ( jaysong@unm.edu )
 * @version 1.0
 * @since 2014-11-24
 *
 */
public class CharacterTest
{
  private static final boolean DEBUG = false;

  /**
   * First test for our Character DB class
   * 
   * @return true if all tests pass
   */
  private boolean test()
  {
    Character character = new Character(1, "Steve Madden", "oilyBro.png", "Victims", "1.1,2.6,3.2");
    assert (character.toString() != null) : "Did not genereate character properly, fail sauce";
    assert (character.toString().contains("Steve Madden")) : "Character name not generated properly.";
    if (DEBUG)
      System.out.println(character.toString());
    return true;
  }

  /**
   * Main statement for test cases
   * 
   * @param args
   */
  public static void main(String[] args)
  {
    CharacterTest characterTest = new CharacterTest();
    if (characterTest.test() == true) System.out.println("Test passed.");
  }
}
