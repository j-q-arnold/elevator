package jqa.elevator;


/**
 * Specifies the interface to elevator scheduling.  In buildings
 * of considerable size, the behavior of "idle" elevators significantly
 * affects convenience, energy consumption, etc.  The controller might
 * want to apply different scheduling conventions at different times of
 * the day.  This interface gives the contract between a specific
 * scheduler and the overall elevator controller.
 */
public interface Scheduler
{
	public int getPreferredFloor(int elevator);
}
