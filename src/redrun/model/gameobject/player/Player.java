package redrun.model.gameobject.player;

import static org.lwjgl.opengl.GL11.GL_COMPILE;
import static org.lwjgl.opengl.GL11.glGenLists;
import static org.lwjgl.opengl.GL11.glMultMatrix;
import static org.lwjgl.opengl.GL11.glNewList;

import java.io.File;

import javax.vecmath.Quat4f;

import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

import com.bulletphysics.collision.dispatch.CollisionFlags;
import com.bulletphysics.collision.dispatch.GhostPairCallback;
import com.bulletphysics.collision.dispatch.PairCachingGhostObject;
import com.bulletphysics.collision.shapes.CapsuleShape;
import com.bulletphysics.collision.shapes.CollisionShape;
import com.bulletphysics.dynamics.character.KinematicCharacterController;

import redrun.graphics.camera.Camera;
import redrun.model.constants.CameraType;
import redrun.model.constants.Constants;
import redrun.model.constants.Team;
import redrun.model.gameobject.GameObject;
import redrun.model.mesh.Face;
import redrun.model.mesh.Model;
import redrun.model.physics.BoxPhysicsBody;
import redrun.model.physics.CapsulePhysicsBody;
import redrun.model.physics.PhysicsBody;
import redrun.model.physics.PhysicsTools;
import redrun.model.physics.PhysicsWorld;
import redrun.model.toolkit.OBJLoader;

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
  
  /** The players model */
  private Model model = null;


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
    super(x, y+10, z, textureName);

    
    body = new CapsulePhysicsBody(new Vector3f(x, y, z), 2f, 100f, 0f);
    body.body.setCollisionFlags(body.body.getCollisionFlags() | CollisionFlags.CUSTOM_MATERIAL_CALLBACK);
    PhysicsWorld.addToWatchList(body);
    camera = new Camera(70, (float) Display.getWidth() / (float) Display.getHeight(), 0.3f, 1000f, x, y, z, CameraType.PLAYER);

    this.name = name;
    this.team = team;
    this.health = 100;
    this.lives = 5;
    this.alive = true;
    
    
    model = OBJLoader.loadModel(new File("res/models/" + "guy11" + ".obj"));

    displayListId = glGenLists(1);
    glNewList(displayListId, GL_COMPILE);
    {
      glMultMatrix(body.getOpenGLTransformMatrix());
      int currentTexture = -1;
      GL11.glEnable(GL11.GL_TEXTURE_2D);
      Face face = null;
      for (int i = 0; i < model.faces.size(); i++)
      {
        face = model.faces.get(i);
        if (face.texture != currentTexture)
        {
          currentTexture = face.texture;
          GL11.glBindTexture(GL11.GL_TEXTURE_2D, currentTexture);
        }
        GL11.glColor3f(1f, 1f, 1f);
        GL11.glBegin(GL11.GL_TRIANGLES);
        Vector3f n1 = model.normals.get((int) face.normal.x);
        GL11.glNormal3f(n1.x, n1.y, n1.z);
        Vector2f t1 = model.texVerticies.get((int) face.textures.x);
        GL11.glTexCoord2f(t1.x, t1.y);
        Vector3f v1 = model.verticies.get((int) face.vertex.x);
        GL11.glVertex3f(v1.x, v1.y, v1.z);
        Vector3f n2 = model.normals.get((int) face.normal.y);
        GL11.glNormal3f(n2.x, n2.y, n2.z);
        Vector2f t2 = model.texVerticies.get((int) face.textures.y);
        GL11.glTexCoord2f(t2.x, t2.y);
        Vector3f v2 = model.verticies.get((int) face.vertex.y);
        GL11.glVertex3f(v2.x, v2.y, v2.z);
        Vector3f n3 = model.normals.get((int) face.normal.z);
        GL11.glNormal3f(n3.x, n3.y, n3.z);
        Vector2f t3 = model.texVerticies.get((int) face.textures.z);
        GL11.glTexCoord2f(t3.x, t3.y);
        Vector3f v3 = model.verticies.get((int) face.vertex.z);
        GL11.glVertex3f(v3.x, v3.y, v3.z);
        GL11.glEnd();
      }
      GL11.glDisable(GL11.GL_TEXTURE_2D);
    }
    GL11.glEndList();

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

  public void pitch(float pitch)
  {
    camera.pitch(pitch);
  }

  public void yaw(float yaw)
  {
    camera.yaw(yaw);
  }

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
    camera.updatePosition(this.getX(), this.getY() + 5f, this.getZ() + .5f, body.getPitch(), body.getYaw());
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
