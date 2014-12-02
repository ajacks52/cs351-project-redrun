package redrun.model.mesh;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;
import org.newdawn.slick.opengl.Texture;

/**
 * 
 * @author Adam Mitchell
 *
 *
 */
public class Model
{
  public List<Vector3f> verticies = new ArrayList<Vector3f>();
  public List<Vector3f> normals = new ArrayList<Vector3f>();
  public List<Face> faces = new ArrayList<Face>();
  public List<Vector2f> texVerticies = new ArrayList<Vector2f>();
  public List<Texture> textures = new ArrayList<Texture>();
}
