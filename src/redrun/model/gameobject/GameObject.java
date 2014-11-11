package redrun.model.gameobject;

import static org.lwjgl.opengl.GL11.glCallList;

import org.lwjgl.util.vector.Vector3f;

import com.bulletphysics.dynamics.RigidBody;

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
  protected int displayListId = -1;

  /** The position of the game object in 3D space. */
  private Vector3f position = null;
  
  // Physics related fields...
  /** The variable that holds all of the information needed for the physics calculations */
  protected RigidBody rigidBody = null;
  
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
  
  
  
  // OpenGL related methods...
  /**
   * Draws the game object to the OpenGL scene.
   */
  public void draw()
  {
    glCallList(displayListId);
  }
  
  
  
  // Getter methods...
  /**
   * Gets the X position of the game object.
   * 
   * @return the X position of the game object
   */
  public float getX()
  {
    return position.x;
  }
  
  /**
   * Gets the Y position of the game object.
   * 
   * @return the Y position of the game object
   */
  public float getY()
  {
    return position.y;
  }
  
  /**
   * Gets the Z position of the game object.
   * 
   * @return the Z position of the game object
   */
  public float getZ()
  {
    return position.z;
  }
  
  /**
   * Gets the physics rigid body
   * 
   * @return the rigidBody
   */
  public RigidBody getRigidBody()
  {
    return rigidBody;
  }
  
  
  
  // Setter methods...
  /**
   * Sets the X position of the game object.
   * 
   * @param x the X position of the game object
   */
  public void setX(float x)
  {
    position.x = x;
  }
  
  /**
   * Sets the Y position of the game object.
   * 
   * @param y the Y position of the game object
   */
  public void setY(float y)
  {
    position.y = y;
  }
  
  /**
   * Sets the Z position of the game object.
   * 
   * @param z the Z position of the game object
   */
  public void setZ(float z)
  {
    position.z = z;
  }
  
  
  
  // Overridden methods from Object...
  @Override
  public String toString()
  {
    //@formatter:off
    return 
      "=== Game Object ===\n" +
      "ID: " + id + "\n" +
      "Position: (" + position.getX() + ", " + position.getY() + ", " + position.getZ() + ")\n" +
      "Physics: " + rigidBody.toString() + "\n" +
      "===================\n";
    //@formatter:on
  }
}
