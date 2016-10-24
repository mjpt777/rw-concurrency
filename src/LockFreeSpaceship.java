import java.util.concurrent.atomic.AtomicReference;

public class LockFreeSpaceship implements Spaceship
{
    private final AtomicReference<Position> position = new AtomicReference<>(new Position(0, 0));

    public int readPosition(final int[] coordinates)
    {
        final Position currentPosition = position.get();
        coordinates[0] = currentPosition.getX();
        coordinates[1] = currentPosition.getY();

        return 1;
    }

    public int move(final int xDelta, final int yDelta)
    {
        int tries = 0;
        Position currentPosition;

        do
        {
            ++tries;
            currentPosition = position.get();
        }
        while (!position.compareAndSet(currentPosition, currentPosition.move(xDelta, yDelta)));

        return tries;
    }

    static class Position
    {
        private final int x;
        private final int y;

        Position(final int x, final int y)
        {
            this.x = x;
            this.y = y;
        }

        int getX()
        {
            return x;
        }

        int getY()
        {
            return y;
        }

        Position move(final int xDelta, final int yDelta)
        {
            return new Position(x + xDelta, y + yDelta);
        }
    }
}
