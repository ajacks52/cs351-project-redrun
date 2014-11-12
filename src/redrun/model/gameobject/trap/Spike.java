package redrun.model.gameobject.trap;

import redrun.model.gameobject.world.Tetrahedron;

public class Spike extends Trap
{

  Tetrahedron spike = null;
  
  public Spike(float x, float y, float z)
  {
    super(x, y, z);
    spike = new Tetrahedron((x+1f),1,(z+1f));
  }
  
  public Tetrahedron getSpikes()
  {
    return spike;
  }

  @Override
  public void activate()
  {
    // TODO Auto-generated method stub
    
  }

  @Override
  public void reset()
  {
    // TODO Auto-generated method stub
    
  }

  @Override
  public void interact()
  {
    // TODO Auto-generated method stub
    
  }

  @Override
  public void update()
  {
    // TODO Auto-generated method stub
    
  }

}
