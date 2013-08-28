/**
 * Interface to a concurrent representation of a ship that can move around
 * a 2 dimensional space with updates and reads performed concurrently.
 */
public interface Spaceship
{
    /**
     * Read the position of the spaceship into the array of coordinates provided.
     *
     * @param coordinates into which the x and y coordinates should be read.
     * @return the number of attempts made to read the current state.
     */
    int readPosition(final int[] coordinates);

    /**
     * Move the position of the spaceship by a delta to the x and y coordinates.
     *
     * @param xDelta delta by which the spaceship should be moved in the x-axis.
     * @param yDelta delta by which the spaceship should be moved in the y-axis.
     * @return the number of attempts made to write the new coordinates.
     */
    int move(final int xDelta, final int yDelta);
}
