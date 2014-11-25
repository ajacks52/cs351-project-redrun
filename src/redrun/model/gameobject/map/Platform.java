package redrun.model.gameobject.map;

import static org.lwjgl.opengl.GL11.GL_COMPILE;
import static org.lwjgl.opengl.GL11.glEndList;
import static org.lwjgl.opengl.GL11.glGenLists;
import static org.lwjgl.opengl.GL11.glNewList;
import redrun.model.constants.Direction;
import redrun.model.constants.Scale;
import redrun.model.gameobject.trap.Trap;
import redrun.model.gameobject.world.RectangularPrism;

public class Platform extends Map
{
	private RectangularPrism platform;
	
	public Platform(float x, float y, float z, String textureName, Direction orientation, Trap trap)
	{
		super(x, y, z, textureName, orientation, trap);
		
		int size = Scale.MAP_SCALE.scale();
		
		platform = new RectangularPrism(x, y, z, textureName, size - 3.0f, 1.0f, size - 3.0f);
		
		displayListId = glGenLists(1);

    glNewList(displayListId, GL_COMPILE);
    {
      platform.draw();
    }
    glEndList();
  }
}
