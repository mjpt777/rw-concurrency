import sun.misc.Unsafe;

import java.lang.reflect.Field;

public class UnsafeSpaceship implements Spaceship
{
    private static final Unsafe UNSAFE;
    private static final long POSITION_OFFSET;

    static
    {
        try
        {
            final Field f = Unsafe.class.getDeclaredField("theUnsafe");
            f.setAccessible(true);
            UNSAFE = (Unsafe)f.get(null);
            POSITION_OFFSET = UNSAFE.objectFieldOffset(UnsafeSpaceship.class.getDeclaredField("position"));
        }
        catch (final Exception ex)
        {
            throw new RuntimeException(ex);
        }
    }

    private volatile Position position = new Position(0, 0);

    @Override
    public int readPosition(final int[] coordinates)
    {
        final Position currentPosition = position;
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
            currentPosition = position;
        }
        while (!UNSAFE.compareAndSwapObject(this, POSITION_OFFSET, currentPosition, currentPosition.move(xDelta, yDelta)));

        return tries;
    }
}
