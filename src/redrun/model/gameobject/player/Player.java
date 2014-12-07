package redrun.model.gameobject.player;

import javax.vecmath.Quat4f;

import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector3f;

import com.bulletphysics.collision.dispatch.CollisionFlags;
import com.bulletphysics.collision.dispatch.GhostPairCallback;
import com.bulletphysics.collision.dispatch.PairCachingGhostObject;
import com.bulletphysics.collision.shapes.CapsuleShape;
import com.bulletphysics.collision.shapes.CollisionShape;
import com.bulletphysics.dynamics.character.KinematicCharacterController;

import redrun.graphics.camera.Camera;
import redrun.model.constants.CameraType;
import redrun.model.constants.Team;
import redrun.model.gameobject.GameObject;
import redrun.model.physics.BoxPhysicsBody;
import redrun.model.physics.CapsulePhysicsBody;
import redrun.model.physics.PhysicsBody;
import redrun.model.physics.PhysicsWorld;

/**
 * This class represents a player in the game world.
 * 
 * @author Troy Squillaci, J. Jake Nichol
 * @version 1.0
 * @since 2014-11-07
 */
public class Player extends GameObject
{
  /** This player's camera. */
  private Camera camera;

  /** This player's name. */
  private String name;

  /** This player's team. */
  private Team team;

  /** This player's health. */
  private int health;

  /** This player's lives. */
  private int lives;

  /** The state of this player's life. */
  private boolean alive;

  private KinematicCharacterController controller;

  /**
   * Creates a new player at the specified position.
   * 
   * @param x the x position of the player
   * @param y the y position of the player
   * @param z the z position of the player
   * @param name the name of the player
   * @param textureName the name of the player texture for this player
   * @param team the team this player is on
   */
  public Player(float x, float y, float z, String name, String textureName, Team team)
  {
    super(x, y, z, textureName);

    
    body = new CapsulePhysicsBody(new Vector3f(x, y, z), .5f, 10, 1);
    camera = new Camera(70, (float) Display.getWidth() / (float) Display.getHeight(), 0.3f, 1000f, -x, -y, -z, CameraType.PLAYER);

    this.name = name;
    this.team = team;
    this.health = 100;
    this.lives = 5;
    this.alive = true;

  }

  /**
   * Makes the player jump about 2 meters into the air.
   */
  public void jump()
  {
    body.jump();
  }

  /**
   * Walk in the specified direction, x and z will be normalized.
   * 
   * @param x
   * @param z
   */
  public void walk(float x, float z)
  {
    Vector3f vec = new Vector3f(x, 0, z);
    body.push(vec);
  }

//  public void pitch(float pitch)
//  {
//    body.pitch(pitch);
//  }
//
//  public void yaw(float yaw)
//  {
//    body.yaw(yaw);
//  }

  public void walkForward(float speed)
  {
    body.moveForward(speed, camera.getYaw());
  }

  public void walkBackward(float speed)
  {
    body.moveBackward(speed, camera.getYaw());
  }

  public void walkLeft(float speed)
  {
    body.moveLeft(speed, camera.getYaw());
  }

  public void walkRight(float speed)
  {
    body.moveRight(speed, camera.getYaw());
  }

  public void lookThrough()
  {
    camera.updatePosition(this.getX(), this.getY() + 2f, this.getZ(), body.getPitch(), body.getYaw());
    camera.lookThrough();
  }

  @Override
  public void interact()
  {
    // TODO Auto-generated method stub

  }

  @Override
  public void update()
  {
    camera.updatePosition(this.getX(), this.getY(), this.getZ(), body.getPitch(), body.getYaw());
  }

  @Override
  public void reset()
  {
    // TODO Auto-generated method stub

  }

  /**
   * Gets the state of this player's life.
   * 
   * @return the state of this player's life
   */
  public boolean isAlive()
  {
    return this.alive;
  }

  /**
   * Gets the name of this player.
   * 
   * @return this player's name
   */
  public String getName()
  {
    return this.name;
  }

  /**
   * Gets this player's health.
   * 
   * @return the health of this player
   */
  public int getHealth()
  {
    return this.health;
  }

  /**
   * Gets this player's team.
   * 
   * @return the team of this player
   */
  public Team getTeam()
  {
    return this.team;
  }

  /**
   * Gets this player's lives.
   * 
   * @return the number of lives this player has
   */
  public int getLives()
  {
    return this.lives;
  }

  /**
   * Gets the camera object on this player's head.
   * 
   * @return this player's camera
   */
  public Camera getCamera()
  {
    return this.camera;
  }

  public PhysicsBody getBody()
  {
    return body;
  }

  /**
   * Sets the state of this player's life.
   * 
   * @param alive whether or not this player is alive
   */
  public void setAlive(boolean alive)
  {
    this.alive = alive;
  }

  /**
   * Sets this player's health.
   * 
   * @param health this player's health
   */
  public void setHealth(int health)
  {
    this.health = health;
  }

  /**
   * Sets this player's team.
   * 
   * @param team this player's team
   */
  public void setTeam(Team team)
  {
    this.team = team;
  }

  /**
   * Sets this player's lives.
   * 
   * @param lives the number of lives to give this player
   */
  public void setLives(int lives)
  {
    this.lives = lives;
  }

  @Override
  public String toString()
  {
    //@formatter:off
    return "=== Player === " + "Location:" + body.getX() + ", " + body.getY() + ", " + body.getZ() + " Name:" + this.name
        + " Texture:" + this.texture + " Team Name:" + this.team + " Health:"
        + this.health + " Lives left:" + this.lives + " Alive:" + this.alive + " ===";
    //@formatter:on
  }
}
