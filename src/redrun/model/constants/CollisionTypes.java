package redrun.model.constants;

public class CollisionTypes
{
  public static final int NO_COLLISION_TYPE             = 0;
  public static final int PLAYER_COLLISION_TYPE         = 1 << 6; 
  public static final int INSTANT_DEATH_COLLISION_TYPE  = 1 << 7;
  public static final int EXPLOSION_COLLISION_TYPE      = 1 << 8;
  public static final int MINIMAL_DAMAGE_COLLISION_TYPE = 1 << 9;
  public static final int WALL_COLLISION_TYPE           = 1 << 10;
  public static final int END_COLLISION_TYPE            = 1 << 11;
}
