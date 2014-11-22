package redrun.model.constants;

public enum Scale
{
	MAP_SCALE
	{
		public int scale()
		{
			return 3;
		}
	};
	
	public abstract int scale();
}
