package redrun.model.gameobject;

import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.lwjgl.util.Timer;
import org.lwjgl.util.vector.Vector3f;

import static org.lwjgl.opengl.GL11.glCallList;
import redrun.model.physics.PhysicsBody;


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
  // Identification related fields...
  /** The ID of the game object. */
  public final int id;
  
  /** All game objects in existence. */
  private static HashMap<Integer, GameObject> gameObjects = new HashMap<Integer, GameObject>();
  
  // OpenGL related fields...
  protected Timer timer = null;
  
  /** The display list for the game object.  */
  protected int displayListId = -1;

  /** The position of the game object in 3D space. */
  protected Vector3f position = null;
  
  // Physics related fields...
  /** The variable that holds all of the information needed for the physics calculations */
  protected PhysicsBody body = null;
  
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
    
    timer = new Timer();
    timer.pause();
    
    id = System.identityHashCode(this);
    
    if (gameObjects.containsKey(id))
    {
      try
      {
        throw new IllegalArgumentException();
      }
      catch (IllegalArgumentException ex)
      {
        Logger.getLogger(GameObject.class.getName()).log(Level.SEVERE, null, ex);
      }
    }
    
    gameObjects.put(id, this);
  }
  
  
  
  // OpenGL related methods...
  /**
   * Draws the game object to the OpenGL scene.
   */
  public void draw()
  {
    glCallList(displayListId);
  }
  
  /**
   * Interacts with the game object.
   */
  public abstract void interact();
  
  /**
   * Updates the game object to reflect the state of the timer.
   */
  public abstract void update();
  
  /**
   * Reset the game object.
   */
  public abstract void reset();
  
  
  
  // Getter methods...
  /**
   * Gets an active game object with the specified ID. Returns null if no such game
   * object is associated with the specified ID.
   * 
   * @param id the ID of the game object
   * @return the game object with the specified ID
   */
  public static GameObject getGameObject(int id)
  {
    return gameObjects.get(id);
  }
  
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
  public PhysicsBody getBody()
  {
    return body;
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
  public boolean equals(Object obj) 
  {
    GameObject other = (GameObject) obj;
     
    return id == other.id;
  }

  @Override
  public int hashCode() 
  {
    return id;
  }
  
  @Override
  public String toString()
  {
    //@formatter:off
    return 
      "=== Game Object ===\n" +
      "ID: " + id + "\n" +
      "Position: (" + position.getX() + ", " + position.getY() + ", " + position.getZ() + ")\n" +
      "Physics: " + body.toString() + "\n" +
      "===================\n";
    //@formatter:on
  }
}
