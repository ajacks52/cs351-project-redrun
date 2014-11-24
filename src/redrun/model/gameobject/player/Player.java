package redrun.model.gameobject.player;

import javax.vecmath.Quat4f;

import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector3f;

import redrun.graphics.camera.PlayerCamera;
import redrun.model.gameobject.GameObject;
import redrun.model.interactable.Interactable;
import redrun.model.physics.BoxPhysicsBody;

/**
 * This class represents a player in the game world.
 * 
 * @author Troy Squillaci, Jake Nichol
 * @version 1.0
 * @since 2014-11-07
 */
public abstract class Player extends GameObject implements Interactable
{
  /**
   * Creates a new player at the specified position.
   * 
   * @param x the x position of the player
   * @param y the y position of the player
   * @param z the z position of the player
   */

  private PlayerCamera camera;

  public Player(float x, float y, float z, String name, String textureName, String team)
  {
    super(x, y, z, textureName);

    body = new BoxPhysicsBody(new Vector3f(x, y, z), new Vector3f(0.5f, 1.0f, 0.5f), new Quat4f(), 100.0f);
    camera = new PlayerCamera(70, (float) Display.getWidth() / (float) Display.getHeight(), 0.3f, 1000f, x, y, z);
    // all the physics you'll need :-D
    // rigidBody = new BoxRigidBody(this.position, new Vector3f(1,2,1), new
    // Quat4f(0,0,0,1), 120.0f);
    // TODO Auto-generated constructor stub
  }

  /**
   * makes the player jump about 2 meters into the air
   */
  public void jump()
  {
    body.jump();
  }

  /**
   * walk in the specified direction. x and z will be normalized
   * 
   * @param x
   * @param z
   */
  public void walk(float x, float z)
  {
    Vector3f vec = new Vector3f(x, 0, z);
    body.push(vec);
  }

  public void pitch(float pitch)
  {
    body.pitch(pitch);
  }

  public void yaw(float yaw)
  {
    body.yaw(yaw);
  }

  public void walkForward(float speed)
  {
    body.moveForward(speed);
  }

  public void walkBackward(float speed)
  {
    body.moveBackward(speed);
  }

  public void walkLeft(float speed)
  {
    body.moveLeft(speed);
  }

  public void walkRight(float speed)
  {
    body.moveRight(speed);
  }

  public void lookThrough()
  {
    camera.position = new Vector3f(getX(), getY(), getZ());
    camera.pitch = body.getPitch();
    camera.yaw = body.getYaw();
    camera.lookThrough();
  }
}
