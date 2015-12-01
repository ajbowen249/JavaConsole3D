public class MazeCell
{
    public boolean northWall;
    public boolean eastWall;
    public boolean southWall;
    public boolean westWall;
    
    public boolean northArch;
    public boolean eastArch;
    public boolean southArch;
    public boolean westArch;

    public boolean occupied;

    public MazeCell() 
    {
        northWall = false;
        eastWall = false;
        southWall = false;
        westWall = false;

        northArch = false;
        eastArch = false;
        southArch = false;
        westArch = false;

        occupied = false;
    }
}
