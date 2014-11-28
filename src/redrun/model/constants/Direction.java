package redrun.model.constants;

public enum Direction
{
	NORTH
	{
		public int deltaX()
		{
			return 0;
		}

		public int deltaY()
		{
			return -1;
		}
	},
	EAST
	{
		public int deltaX()
		{
			return 1;
		}

		public int deltaY()
		{
			return 0;
		}
	},
	SOUTH
	{
		public int deltaX()
		{
			return 0;
		}

		public int deltaY()
		{
			return 1;
		}
	},
	WEST
	{
		public int deltaX()
		{
			return -1;
		}

		public int deltaY()
		{
			return 0;
		}
	};
	
	public abstract int deltaX();

	public abstract int deltaY();

	public static final int SIZE = values().length;

	public static final Direction getLeftDir(	Direction dir)
	{
		return values()[(dir.ordinal() + SIZE - 1) % SIZE];
	}

	public static final Direction getRightDir(Direction dir)
	{
		return values()[(dir.ordinal() + 1) % SIZE];
	}
}