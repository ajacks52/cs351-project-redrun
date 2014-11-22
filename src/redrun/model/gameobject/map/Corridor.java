package redrun.model.gameobject.map;

import static org.lwjgl.opengl.GL11.*;
import redrun.model.constants.Direction;
import redrun.model.gameobject.trap.Trap;

public class Corridor extends Map
{
	public Corridor(float x, float y, float z, String textureName, Direction orientation, Trap trap)
	{
		super(x, y, z, textureName, orientation, trap);
				
		displayListId = glGenLists(1);
		
    glNewList(displayListId, GL_COMPILE);
    {    	
      glBegin(GL_QUADS);
      {
        glColor3f(1.0f, 1.0f, 1.0f);  
        
    		switch(orientation)
    		{
    		  case NORTH:
    		  {
    		    // Draw the platform...
            glVertex3f(x + 10, y, z + 10);
            glTexCoord2f(1, 1);
            glVertex3f(x - 10, y, z + 10);
            glTexCoord2f(0, 1);
            glVertex3f(x - 10, y, z - 10);
            glTexCoord2f(0, 0);
            glVertex3f(x + 10, y, z - 10);
            glTexCoord2f(1, 0);
            break;
    		  }
    		  case EAST:
    		  {
    		    // Draw the platform...
            glVertex3f(x + 10, y, z + 10);
            glTexCoord2f(1, 1);
            glVertex3f(x - 10, y, z + 10);
            glTexCoord2f(0, 1);
            glVertex3f(x - 10, y, z - 10);
            glTexCoord2f(0, 0);
            glVertex3f(x + 10, y, z - 10);
            glTexCoord2f(1, 0);
            break;
    		  }
    		  case SOUTH:
    		  {
    		    // Draw the platform...
            glVertex3f(x + 10, y, z + 10);
            glTexCoord2f(1, 1);
            glVertex3f(x - 10, y, z + 10);
            glTexCoord2f(0, 1);
            glVertex3f(x - 10, y, z - 10);
            glTexCoord2f(0, 0);
            glVertex3f(x + 10, y, z - 10);
            glTexCoord2f(1, 0);
            break;
    		  }
    		  case WEST:
    		  {
    		    // Draw the platform...
            glVertex3f(x + 10, y, z + 10);
            glTexCoord2f(1, 1);
            glVertex3f(x - 10, y, z + 10);
            glTexCoord2f(0, 1);
            glVertex3f(x - 10, y, z - 10);
            glTexCoord2f(0, 0);
            glVertex3f(x + 10, y, z - 10);
            glTexCoord2f(1, 0);
            break;
    		  }
          default:
          {
          	try
          	{
          		throw new IllegalArgumentException();
          	}
          	catch (IllegalArgumentException ex)
          	{
          		ex.printStackTrace();
          	}
          }
    		}       
      }  
      glEnd();
    }
    glEndList();
	}
}
