public class VoxelField
{
    private int xDim;
    private int yDim;
    private int zDim;

    private IVoxel[][][] field;

    public VoxelField(int xDim, int yDim, int zDim)
    {
        this.xDim = xDim;
        this.yDim = yDim;
        this.zDim = zDim;

        field = new IVoxel[xDim][yDim][zDim];
    }

    public IVoxel[][][] getField()
    {
        return field;
    }
	
	public void setBlankBlockField()
	{
	    for(int x = 0; x < field.length; x++)
            {
                for(int y = 0; y < field[0].length; y++)
                {
                    for(int z = 0; z < field[1].length; z++)
                    {
                        field[x][y][z] = new BlockVoxel(false);
                    }
                }
            }
	}
	
	public void setBlankCharField()
	{
	    for(int x = 0; x < xDim; x++)
            {
                for(int y = 0; y < yDim; y++)
                {
                    for(int z = 0; z < zDim; z++)
                    {
                        field[x][y][z] = new CharacterVoxel();
                    }
                }
            }
	}
}
