package redrun.model.gameobject.trap.full;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL20.glGetAttribLocation;
import static org.lwjgl.opengl.GL20.glUseProgram;
import static org.lwjgl.opengl.GL20.glVertexAttrib3f;

import javax.vecmath.Quat4f;

import org.lwjgl.util.vector.Vector3f;

import com.bulletphysics.collision.dispatch.CollisionObject;

import redrun.model.constants.Direction;
import redrun.model.game.GameData;
import redrun.model.gameobject.trap.Trap;
import redrun.model.gameobject.trap.piece.Spear;
import redrun.model.physics.BoxPhysicsBody;
import redrun.model.toolkit.ShaderLoader;

public class PoleWall extends Trap
{
  Spear spear1;
  Spear spear2;
  Spear spear3;
  Spear spear4;
  Spear spear5;

  public PoleWall(float x, float y, float z, Direction orientation, String textureName)
  {
    super(x, y, z, orientation, textureName);

    if (orientation == Direction.EAST || orientation == Direction.WEST)
    {
      spear1 = new Spear(x - 37, y + 3, z - 5, orientation, null, 0f, "x");
      spear2 = new Spear(x - 37, y + 3, z + 0, orientation, null, 0f, "x");
      spear3 = new Spear(x - 37, y + 3, z + 5, orientation, null, 0f, "x");
      spear4 = new Spear(x - 37, y + 5, z + 3, orientation, null, 0f, "x");
      spear5 = new Spear(x - 37, y + 5, z - 3, orientation, null, 0f, "x");
    }
    else if (orientation == Direction.SOUTH || orientation == Direction.NORTH)
    {
      spear1 = new Spear(x - 5, y + 3, z - 37, orientation, null, 0f, "z");
      spear2 = new Spear(x + 0, y + 3, z - 37, orientation, null, 0f, "z");
      spear3 = new Spear(x + 5, y + 3, z - 37, orientation, null, 0f, "z");
      spear4 = new Spear(x - 3, y + 5, z - 37, orientation, null, 0f, "z");
      spear5 = new Spear(x + 3, y + 5, z - 37, orientation, null, 0f, "z");
    }

    ///   *-5     *0     *+5  +5
    ///            +
    ///       *-3     *+3     +3
    GameData.addGameObject(spear1);
    GameData.addGameObject(spear2);
    GameData.addGameObject(spear3);
    GameData.addGameObject(spear4);
    GameData.addGameObject(spear5);
  }

  @Override
  public void activate()
  {
    System.out.println("Interacting with the game object: " + this.id);
    spear1.activate();
    spear2.activate();
    spear3.activate();
    spear4.activate();
    spear5.activate();
  }

  @Override
  public void reset()
  {
  }

  @Override
  public void interact()
  {
  }

  @Override
  public void update()
  {
  }

}
