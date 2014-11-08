package redrun.model.gameobject;

import static org.lwjgl.opengl.GL11.glCallList;

import org.lwjgl.util.vector.Vector3f;

/**
 * This abstract class represents a game object. Every object in the 3D scene will extend
 * this class.
 * 
 * @author Troy Squillaci, Jake Nichol
 * @version 1.0
 * @since 2014-11-07
 */
public abstract class GameObject
{
  /** The ID of the game object. */
  private int id = -1;
  
  // OpenGL related fields...
  /** The display list for the game object.  */
  private int displayListId = -1;

  /** The position of the game object in 3D space. */
  private Vector3f position = null;
  
  // Physics related fields...
  /** The mass of the game object. */
  private float mass = -1;
  
  /**
   * Creates a new game object at the specified position.
   * 
   * @param x the x position of the game object
   * @param y the y position of the game object
   * @param z the z position of the game object
   */
  public GameObject(float x, float y, float z)
  {
    position = new Vector3f(x, y, z);
  }
  
  /**
   * Draws the game object to the OpenGL scene.
   */
  public void draw()
  {
    glCallList(displayListId);
  }

  @Override
  public String toString()
  {
    //@formatter:off
    return 
      "=== Game Object ===\n" +
      "ID: " + id + "\n" +
      "Position: (" + position.getX() + ", " + position.getY() + ", " + position.getZ() + ")\n" +
      "Physics: Mass: " + mass + "\n" +
      "===================\n";
    //@formatter:on
  }
}
