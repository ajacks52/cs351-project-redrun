package redrun.model.gameobject.world;

import static org.lwjgl.opengl.GL11.*;

import javax.vecmath.Quat4f;

import org.lwjgl.util.vector.Vector3f;

import redrun.model.constants.CollisionTypes;
import redrun.model.physics.BoxPhysicsBody;

/**
 * This class represents a rectangular prism that can be drawn in an OpenGL scene.
 * 
 * @author Troy Squillaci
 * @version 1.0
 * @since 2014-11-03
 */
public class InvisibleWall extends WorldObject
{
  /**
   * Creates a rectangular prism at the specified location with the specified dimensions.
   * 
   * @param x the x position of the rectangular prism
   * @param y the y position of the rectangular prism
   * @param z the z position of the rectangular prism
   * @param textureName the texture to apply to the rectangular prism
   * @param width the width position of the rectangular prism
   * @param height the height position of the rectangular prism
   * @param depth the depth position of the rectangular prism
   */
  public InvisibleWall(float x, float y, float z, String textureName, float width, float height, float depth)
  {
    super(x, y, z, textureName);

    this.body = new BoxPhysicsBody(new Vector3f(x, y, z), new Vector3f(width / 2, height / 2, depth / 2), new Quat4f(), 0, CollisionTypes.WALL_COLLISION_TYPE);
    this.body.body.setFriction(0);
  }

  /**
   * Creates a rectangular prism at the specified location with the specified dimensions.
   * 
   * @param x the x position of the rectangular prism
   * @param y the y position of the rectangular prism
   * @param z the z position of the rectangular prism
   * @param textureName the texture to apply to the rectangular prism
   * @param width the width position of the rectangular prism
   * @param height the height position of the rectangular prism
   * @param depth the depth position of the rectangular prism
   * @param mass the mass of the rectangular prism
   */
  public InvisibleWall(float x, float y, float z, String textureName, float width, float height, float depth,
      float mass)
  {
    this(x, y, z, textureName, width, height, depth);

    this.body = new BoxPhysicsBody(new Vector3f(x, y, z), new Vector3f(width / 2, height / 2, depth / 2), new Quat4f(), mass, CollisionTypes.WALL_COLLISION_TYPE);
    this.body.body.setFriction(0);
  }

  @Override
  public void draw()
  {
    // passthrough
  }
  
  @Override
  public void interact()
  {
    System.out.println("Interacting with the game object: " + this.id);
  }

  @Override
  public void update()
  {

  }

  @Override
  public void reset()
  {

  }
}