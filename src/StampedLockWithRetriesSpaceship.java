import java.util.concurrent.locks.StampedLock;

public class StampedLockWithRetriesSpaceship implements Spaceship
{
    public static final int RETRIES = 5;
    private final StampedLock lock = new StampedLock();

    private int x;
    private int y;

    @Override
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
