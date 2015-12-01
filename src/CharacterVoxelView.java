import java.lang.Math.*;

public class CharacterVoxelView
{
    private Camera cam;
    private char[][][] vField;

    private int viewWidth;
    private int viewHeight;

    private int worldDimX;
    private int worldDimY;
    private int worldDimZ;

    public CharacterVoxelView(int worldX, int worldY, int worldZ, 
        int width, int height)
    {
        this.viewWidth = width;
        this.viewHeight = height;
        this.worldDimX = worldX;
        this.worldDimY = worldY;
        this.worldDimZ = worldZ;
        System.out.println("Constructing camera");
        this.cam = new Camera(60, this.viewWidth, this.viewHeight);
        
        System.out.println("Allocating voxel field");
        this.vField = new char[worldX][worldY][worldZ];
        System.out.println("Setting blank voxels");
        for(int x = 0; x < worldX; x++)
        {
            for(int y = 0; y < worldY; y++)
            {
                for(int z = 0; z < worldZ; z++)
                {
                    this.vField[x][y][z] = ' ';
                }
            }
        }
    }

    public Camera getCamera()
    {
        return this.cam;
    }

    public char[][][]  getField()
    {
        return this.vField;
    }

    public char[][] render()
    {
        int[][][] plane = this.cam.getClipPlane();
        char[][] view = new char[this.viewWidth][this.viewHeight];
        int maxDist = 200;

        for(int X = 0; X < this.viewWidth; X++)
        {
            for(int Y = 0; Y < this.viewHeight; Y++)
            {
                char foundChar = ' ';
                int[] vector = cam.rayVector(X, Y);
                int[][] checkPoints = Camera.castRay(vector, 
                                              plane[X][Y][0],
                                              plane[X][Y][1],
                                              plane[X][Y][2],
                                              maxDist);

                for(int i = 0; i < checkPoints.length; i++)
                {
                    if(checkPoints[i][0] < 0 || checkPoints[i][0] >= worldDimX
                    || checkPoints[i][1] < 0 || checkPoints[i][1] >= worldDimY
                    || checkPoints[i][2] < 0 || checkPoints[i][2] >= worldDimZ)
                    {
                        break;
                    }
                    else
                    {
                        char ch = this.vField[checkPoints[i][0]]
                                             [checkPoints[i][1]]
                                             [checkPoints[i][2]];
                        if(ch != ' ')
			{
			    foundChar = ch;
			    break;
			}
                    }
                }
				
                view[X][Y] = foundChar;
            }
        }

        return view;
    }

    public void drawBlock(int startX, int startY, int startZ,
                          int endX, int endY, int endZ,
						  char ch)
    {
        for(int x = startX; x <= endX; x++)
        {
            for(int y = startY; y <= endY; y++)
            {
                for(int z = startZ; z <= endZ; z++)
                {
                   this.vField[x][y][z] = ch;
                }
            }
        }
    }
}
