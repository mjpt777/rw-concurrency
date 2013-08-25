import jsr166e.StampedLock;

public class StampedLockSpaceship implements Spaceship
{
    private final StampedLock lock = new StampedLock();

    private int x;
    private int y;

    @Override
    public int readPosition(final int[] coordinates)
    {
        int tries = 1;
        long stamp = lock.tryOptimisticRead();

        coordinates[0] = x;
        coordinates[1] = y;

        if (!lock.validate(stamp))
        {
            ++tries;

            stamp = lock.readLock();
            try
            {
                coordinates[0] = x;
                coordinates[1] = y;
            }
            finally
            {
                lock.unlockRead(stamp);
            }
        }

        return tries;
    }

    @Override
    public int move(final int xDelta, final int yDelta)
    {
        final long stamp = lock.writeLock();
        try
        {
            x += xDelta;
            y += yDelta;
        }
        finally
        {
            lock.unlockWrite(stamp);
        }

        return 1;
    }
}
