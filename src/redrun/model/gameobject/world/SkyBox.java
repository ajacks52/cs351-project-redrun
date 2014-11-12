package redrun.model.gameobject.world;

import static org.lwjgl.opengl.GL11.*;

import org.newdawn.slick.opengl.Texture;

import redrun.model.toolkit.Tools;

public class SkyBox extends WorldObject
{
	Texture front = Tools.loadJPGTexture("blood_sport/blood_sport512_front");
	Texture back = Tools.loadJPGTexture("blood_sport/blood_sport512_back");
	Texture left = Tools.loadJPGTexture("blood_sport/blood_sport512_left");
	Texture right = Tools.loadJPGTexture("blood_sport/blood_sport512_right");
	Texture top = Tools.loadJPGTexture("blood_sport/blood_sport512_top");

	public SkyBox(float x, float y, float z)
	{
		super(x, y, z);
		
    displayListId = glGenLists(1);
    
    glNewList(displayListId, GL_COMPILE);
    {
    	glPushAttrib(GL_ENABLE_BIT);
      glEnable(GL_TEXTURE_2D);
      glDisable(GL_DEPTH_TEST);
      glDisable(GL_LIGHTING);
      glDisable(GL_BLEND);
    	
      glBegin(GL_QUADS);
      {  
      	// Set the color to white to indicate an error...
      	glColor4f(1,1,1,1);
      	
        // Render the front quad...
        front.bind();
        glBegin(GL_QUADS);
            glTexCoord2f(0, 0); glVertex3f(  0.5f, -0.5f, -0.5f );
            glTexCoord2f(1, 0); glVertex3f( -0.5f, -0.5f, -0.5f );
            glTexCoord2f(1, 1); glVertex3f( -0.5f,  0.5f, -0.5f );
            glTexCoord2f(0, 1); glVertex3f(  0.5f,  0.5f, -0.5f );
        glEnd();
        
        // Render the left quad...
        left.bind();
        glBegin(GL_QUADS);
            glTexCoord2f(0, 0); glVertex3f(  0.5f, -0.5f,  0.5f );
            glTexCoord2f(1, 0); glVertex3f(  0.5f, -0.5f, -0.5f );
            glTexCoord2f(1, 1); glVertex3f(  0.5f,  0.5f, -0.5f );
            glTexCoord2f(0, 1); glVertex3f(  0.5f,  0.5f,  0.5f );
        glEnd();
        
        // Render the back quad...
        right.bind();
        glBegin(GL_QUADS);
            glTexCoord2f(0, 0); glVertex3f( -0.5f, -0.5f,  0.5f );
            glTexCoord2f(1, 0); glVertex3f(  0.5f, -0.5f,  0.5f );
            glTexCoord2f(1, 1); glVertex3f(  0.5f,  0.5f,  0.5f );
            glTexCoord2f(0, 1); glVertex3f( -0.5f,  0.5f,  0.5f );
        glEnd();
   
        // Render the right quad
        right.bind();
        glBegin(GL_QUADS);
            glTexCoord2f(0, 0); glVertex3f( -0.5f, -0.5f, -0.5f );
            glTexCoord2f(1, 0); glVertex3f( -0.5f, -0.5f,  0.5f );
            glTexCoord2f(1, 1); glVertex3f( -0.5f,  0.5f,  0.5f );
            glTexCoord2f(0, 1); glVertex3f( -0.5f,  0.5f, -0.5f );
        glEnd();

        // Render the top quad
        top.bind();
        glBegin(GL_QUADS);
            glTexCoord2f(0, 1); glVertex3f( -0.5f,  0.5f, -0.5f );
            glTexCoord2f(0, 0); glVertex3f( -0.5f,  0.5f,  0.5f );
            glTexCoord2f(1, 0); glVertex3f(  0.5f,  0.5f,  0.5f );
            glTexCoord2f(1, 1); glVertex3f(  0.5f,  0.5f, -0.5f );
        glEnd();
        
        // Right face...
        glNormal3f(1.0f, 0.0f, 0.0f);
        glColor3f(1.0f, 0.0f, 1.0f);
        glVertex3f(1.0f, 1.0f, -1.0f);
        glTexCoord2f(0,0);
        glVertex3f(1.0f, 1.0f, 1.0f);
        glTexCoord2f(0,1);
        glVertex3f(1.0f, -1.0f, 1.0f);
        glTexCoord2f(1,1);
        glVertex3f(1.0f, -1.0f, -1.0f);
        glTexCoord2f(1,0);
      }
      glEnd();

      glPopAttrib();
      glPopMatrix();
    }
    glEndList();
	}

	@Override
	public void interact()
	{
		// TODO Auto-generated method stub
		
	}
}
