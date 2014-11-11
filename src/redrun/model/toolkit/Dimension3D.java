package redrun.model.toolkit;

/**
 * A class for storing a 3 dimensional position.
 * 
 * @author J. Jake Nichol
 * @since 11-8-14
 * @version 1.0
 */
public class Dimension3D
{
  /** The X dimension. */
  public float x;

  /** The Y dimension. */
  public float y;

  /** The Z dimension. */
  public float z;

  /**
   * Creates a new Dimension3D.
   * 
   * @param x the X dimension
   * @param y the Y dimension
   * @param z the Z dimension
   */
  public Dimension3D(float x, float y, float z)
  {
    this.x = x;
    this.y = y;
    this.z = z;
  }
}
