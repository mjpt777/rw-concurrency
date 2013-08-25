import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ReentrantLockSpaceship implements Spaceship
{
    private final Lock lock = new ReentrantLock();

    private int x;
    private int y;

    @Override
    public int readPosition(final int[] coordinates)
    {
        lock.lock();
        try
        {
            coordinates[0] = x;
            coordinates[1] = y;
        }
        finally
        {
            lock.unlock();
        }

        return 1;
    }

    @Override
    public int move(final int xDelta, final int yDelta)
    {
        lock.lock();
        try
        {
            x += xDelta;
            y += yDelta;
        }
        finally
        {
            lock.unlock();
        }

        return 1;
    }
}
