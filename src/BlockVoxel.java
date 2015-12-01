public class BlockVoxel implements IVoxel
{
    private boolean visible;

    public BlockVoxel()
    {
        this(true);
    }

    public BlockVoxel(boolean visible)
    {
        setVisible(visible);
    }

    public boolean isVisible()
    {
        return this.visible;
    }

    public void setVisible(boolean visible)
    {
        this.visible = visible;
    }
}
