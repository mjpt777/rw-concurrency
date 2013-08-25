import java.util.concurrent.atomic.AtomicReference;

public class AtomicRefSpaceship implements Spaceship
{
    private AtomicReference<Position> position = new AtomicReference<Position>(new Position(0, 0));

    @Override
    public int readPosition(final int[] coordinates)
    {
        final Position currentPosition = position.get();
        coordinates[0] = currentPosition.getX();
        coordinates[1] = currentPosition.getY();

        return 1;
    }

    @Override
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
}
