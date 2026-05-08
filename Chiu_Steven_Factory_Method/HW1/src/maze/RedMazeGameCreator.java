package maze;

public class RedMazeGameCreator extends MazeGameCreator
{
    @Override
    public Wall makeWall()
    {
        return new RedWall();
    }

    @Override
    public Room makeRoom(int roomNum)
    {
        return new PinkRoom(roomNum);
    }
}
