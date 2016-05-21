public class ProgressBar
{
    private int length;

    public ProgressBar(int length)
    {
        this.length = length;
    }

    public ProgressBar()
    {
        this(50);
    }

    public void updateProgress(int progress)
    {
        int doneSegments = (progress * this.length)/100;

        String bar = "[";
        for(int i = 0; i < doneSegments; i++)
        {
            bar += "=";
        }

        for(int i = 0; i < this.length - doneSegments; i++)
        {
            bar += " ";
        }

        bar += "]";

        System.out.print("\r");
        System.out.print(bar);
    }
}
