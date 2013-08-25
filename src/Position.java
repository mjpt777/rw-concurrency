public class Position
{
    private final int x;
    private final int y;

    public Position(final int x, final int y)
    {
        this.x = x;
        this.y = y;
    }

    public int getX()
    {
        return x;
    }

    public int getY()
    {
        return y;
    }

    public Position move(final int xDelta, final int yDelta)
    {
        return new Position(x + xDelta, y + yDelta);
    }
}
