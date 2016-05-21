import java.util.Scanner;

public class MazeTest
{
    private static CharacterVoxelView view;
    private static Scanner kb;
    
    public static void main(String args[])
    {
        System.out.println("Constructing maze builder...");
        MazeBuilder mb = new MazeBuilder(20, 20);

        System.out.println("Creating hallways...");
        mb.drawHorizontalHallway(0, 1, 4);
        mb.drawVerticalHallway(3, 0, 4);

        System.out.println("Generating 3D Scene...");
        view = mb.build3D();

        System.out.println("Pre-placing camera...");
        view.getCamera().setPosition(50, 150, 17, CardinalEnum.EAST);

        String cmd = "";
        kb = new Scanner(System.in);

        while(!cmd.equals("quit"))
        {
            refreshScreen();
            
            System.out.print("CMD>");
            cmd = kb.nextLine();
            
            if(cmd.equals("n"))
            {
                view.getCamera().move(0, -10, 0);
            }
            else if(cmd.equals("s"))
            {
                view.getCamera().move(0, 10, 0);
            }
            else if(cmd.equals("e"))
            {
                view.getCamera().move(10, 0, 0);
            }
            else if(cmd.equals("w"))
            {
                view.getCamera().move(-10, 0, 0);
            }
            else if(cmd.equals("fn"))
            {
                view.getCamera().faceCardinal(CardinalEnum.NORTH);
            }
            else if(cmd.equals("fe"))
            {
                view.getCamera().faceCardinal(CardinalEnum.EAST);
            }
            else if(cmd.equals("fs"))
            {
                view.getCamera().faceCardinal(CardinalEnum.SOUTH);
            }
            else if(cmd.equals("fw"))
            {
                view.getCamera().faceCardinal(CardinalEnum.WEST);
            }
        }
    }
    
    private static void refreshScreen()
    {
        char[][] renderResult = view.render();

        for(int Y = 0; Y < renderResult[0].length; Y++)
        {
            for(int X = 0; X < renderResult.length; X++)
            {
                System.out.print(renderResult[X][Y]);
            }
            
            System.out.println();
        }
    }
}
