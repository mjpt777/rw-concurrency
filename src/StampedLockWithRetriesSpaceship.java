import java.util.concurrent.locks.StampedLock;

public class StampedLockWithRetriesSpaceship implements Spaceship
{
    private static final int RETRIES = 5;
    private final StampedLock lock = new StampedLock();

    private int x;
    private int y;

    public int readPosition(final int[] coordinates)
    {
        int tries = 0; long stamp;
        for (int i = 0; i < RETRIES; i++) {
            ++tries;
            stamp = lock.tryOptimisticRead();

            coordinates[0] = x;
            coordinates[1] = y;
            if (lock.validate(stamp)) {
                return tries;
            }
        }
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

        return tries;
    }

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
