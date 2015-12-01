public class CharacterVoxel implements IVoxel
{
    private char character;

    public CharacterVoxel()
    {
        this(' ');
    }

    public CharacterVoxel(char character)
    {
        setChar(character);
    }

    public char getChar()
    {
        return this.character;
    }

    public void setChar(char character)
    {
        this.character = character;
    }
}
