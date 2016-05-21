public class MazeBuilder
{
    private int width;
    private int height;

    private MazeCell[][] maze;

    public MazeBuilder(int width, int height)
    {
        this.width = width;
        this.height = height;

        maze = new MazeCell[this.width][this.height];
        for(int x = 0; x < this.width; x++)
        {
            for(int y = 0; y < this.height; y++)
            {
                this.maze[x][y] = new MazeCell();
            }
        }
    }

    public void drawVerticalHallway(int startX, int startY, int length)
    {
        for(int i = 0; i < length; i++)
        {
            MazeCell c = this.maze[startX][startY + i];
            
            if(!c.occupied)
            {
                c.occupied = true;
                c.eastWall = true;
                c.westWall = true;
            }
            else
            {
                c.northWall = false;
                c.southWall = false;
                c.northArch = true;
                c.southArch = true;
            }
        }
    }

    public void drawHorizontalHallway(int startX, int startY, int length)
    {
        for(int i = 0; i < length; i++)
        {
            MazeCell c = this.maze[startX + i][startY];

            if(!c.occupied)
            {
                c.occupied = true;
                c.northWall = true;
                c.southWall = true;
            }
            else
            {
                c.eastWall = false;
                c.westWall = false;
                c.eastArch = true;
                c.westArch = true;
            }
        }
    }

    public MazeCell[][] getMaze()
    {
        return this.maze;
    }

    public CharacterVoxelView build3D()
    {
       int blockSize = 100;
       int blockHeight = 30;
       
       //TODO: Looks like these are OS-dependent.
       //Windows 7 needed unicode values, but
       //10 switched to ASCII, or something.
       char floorChar = (char)178;
       char ceilChar = (char)177;
       char wallChar = (char)219;
       char floorTrim = '=';

       int XDim = blockSize * this.width;
       int YDim = blockSize * this.height;
       int ZDim = blockHeight + 4;

       CharacterVoxelView view = new CharacterVoxelView(XDim, YDim, ZDim, 75, 24);

       //draw floor and ceiling
       view.drawBlock(0, 0, 0, XDim - 1, YDim - 1, 2, ceilChar);
       view.drawBlock(0, 0, ZDim - 3, XDim - 1, YDim - 1, ZDim - 1, floorChar);
       
       //draw walls
       for(int X = 0; X < this.width; X++)
       {
           for(int Y = 0; Y < this.height; Y++)
           {
               MazeCell c = this.maze[X][Y];
               
               if(c.northWall)
               {
                   view.drawBlock(blockSize * X, blockSize * Y, 0,
                                  (blockSize * (X+1))-1, blockSize * Y, ZDim-1,
                                  wallChar);
                   view.drawBlock(blockSize * X, blockSize * Y, ZDim - 6,
                                  (blockSize * (X+1))-1, blockSize * Y, ZDim-4,
                                  floorTrim);
               }

               if(c.southWall)
               {
                   view.drawBlock(blockSize * X, (blockSize*(Y+1))-1, 0, 
                                  (blockSize*(X+1))-1,(blockSize*(Y+1))-1,ZDim-1,
                                  wallChar);
               }

               if(c.westWall)
               {
                   view.drawBlock(blockSize * X, blockSize * Y, 0,
                                  blockSize * X, (blockSize*(Y+1))-1, ZDim-1,
                                  wallChar);
               }

               if(c.eastWall)
               {
                   view.drawBlock((blockSize*(X+1))-1, blockSize * Y, 0,
                                  (blockSize*(X+1))-1, (blockSize*(Y+1))-1, ZDim-1,
                                  wallChar);
               }
           }
       }

       return view; 
    }
}
