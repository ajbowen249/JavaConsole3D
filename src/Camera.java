import java.lang.Math.*;

public class Camera
{
    private int fov;
    private int width;
    private int height;

    private int x;
    private int y;
    private int z;

    private int[][][] clipPlane;
    private int distToPlane;

    private CardinalEnum facing;

    public Camera(int fov, int width, int height)
    {
        this.fov = fov;
        this.width = width;
        this.height = height;

        initClipPlane();
    }

    public int getX()
    {
        return this.x;
    }

    public void setX(int x)
    {
        this.x = x;
    }

    public int getY()
    {
        return this.y;
    }

    public void setY(int y)
    {
        this.y = y;
    }

    public int getZ()
    {
        return this.z;
    }

    public void setZ(int z)
    {
        this.z = z;
    }

    public int[][][] getClipPlane()
    {
        return this.clipPlane;
    }

    public void faceCardinal(CardinalEnum dir)
    {
        int xInc = 0;
        int yInc = 0;
        int xInit = 0;
        int yInit = 0;
        
        switch(dir)
        {
            case NORTH:
                xInc = 1;
                xInit = this.x - (this.width/2);
                yInit = this.y - this.distToPlane;
                break;
            case EAST:
                yInc = 1;
                yInit = this.y - (this.width/2);
                xInit = this.x + this.distToPlane;
                break;
            case SOUTH:
                xInc = -1;
                xInit = this.x + (this.width/2);
                yInit = this.y + this.distToPlane;
                break;
            case WEST:
                yInc = -1;
                yInit = this.y + (this.width/2);
                xInit = this.x - this.distToPlane;
                break;
        }
        
        for(int Y = 0; Y < this.height; Y++)
        {
            int currentX = xInit;
            int currentY = yInit;

            for(int X = 0; X < this.width; X++)
            {
                this.clipPlane[X][Y][0] = currentX;
                this.clipPlane[X][Y][1] = currentY;
               
                currentX += xInc;
                currentY += yInc;

                this.clipPlane[X][Y][2] = (this.z - (this.height/2)) + Y;
            }
        }

        this.facing = dir;
    }
    
    public void setPosition(int newX, int newY, int newZ, CardinalEnum dir)
    {
        this.x = newX;
        this.y = newY;
        this.z = newZ;

        faceCardinal(dir);
    }

    public void move(int xDelta, int yDelta, int zDelta)
    {
        this.x += xDelta;
        this.y += yDelta;
        this.z += zDelta;
        
        faceCardinal(this.facing);
    }
    
    public int[] rayVector(int clipX, int clipY)
    {
        int xDelta = this.clipPlane[clipX][clipY][0] - this.x;
        int yDelta = this.clipPlane[clipX][clipY][1] - this.y;
        int zDelta = this.clipPlane[clipX][clipY][2] - this.z;

        int[] vector = new int[3];
        vector[0] = xDelta;
        vector[1] = yDelta;
        vector[2] = zDelta;

        return vector;
    }
    
    public static int[][] castRay(int[] slope, int startX, int startY, 
                                  int startZ, int maxLength)
    {
        int maxComponent = 0;
        if(Math.abs(slope[1]) > Math.abs(slope[maxComponent])) maxComponent = 1;
        if(Math.abs(slope[2]) > Math.abs(slope[maxComponent])) maxComponent = 2;

        float[] point = new float[3];
        point[0] = (float)startX;
        point[1] = (float)startY;
        point[2] = (float)startZ;

        float[] normSlope = new float[3];
        normSlope[maxComponent] = (slope[maxComponent] > 0)? 1.0f : -1.0f;
        for(int i = 0; i < 3; i++)
        {
            if(i != maxComponent)
            {
                normSlope[i] = (float)(slope[i])/Math.abs((float)slope[maxComponent]);
            }
        }

        int[][] cells = new int[maxLength][3];
        
        for(int i = 0; i < maxLength; i++)
        {
            cells[i][0] = Math.round(point[0]);
            cells[i][1] = Math.round(point[1]);
            cells[i][2] = Math.round(point[2]);

            point[0] += normSlope[0];
            point[1] += normSlope[1];
            point[2] += normSlope[2];
        } 

        return cells;
    }

    private void initClipPlane()
    {
        this.clipPlane = new int[this.width][this.height][3];
        
        double halfWidth = (double)this.width/2.0;
        distToPlane = (int)Math.round(
            halfWidth / Math.tan(Math.toRadians((double)this.fov/2.0)));

        this.x = (int)Math.round(halfWidth);
        this.y = distToPlane;
        this.z = this.height/2;

        faceCardinal(CardinalEnum.NORTH);
    }
}
