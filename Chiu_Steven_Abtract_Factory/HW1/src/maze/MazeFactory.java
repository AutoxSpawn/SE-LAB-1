package maze;

public abstract class MazeFactory
{
    public Maze makeMaze()
    {
        return new Maze();
    }

    public abstract Wall makeWall();

    public abstract Door makeDoor(Room r1, Room r2);

    public abstract Room makeRoom(int roomNum);
}
