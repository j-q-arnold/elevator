package jqa.elevator;


import java.util.ArrayList;
import java.util.Observable;


/**
 * An individual elevator car within the building.  This class
 * manages its own travel up and down, stopping at the floors
 * in its "schedule."
 */
public class Elevator
	extends Observable
	implements StandaloneDevice
{
	private ElevatorControlSystem controlSystem;
	private Floor[]               floors;
	Direction currentDirection;
	int       currentFloor;

	String identity;

	/**
	 * This elevator's index within the controller.
	 */
	int index;


	public Elevator(ElevatorControlSystem controlSystem)
	{
		this.controlSystem = controlSystem;

		this.floors = new Floor[this.controlSystem.getFloorTop() + 1];
		for (int index = 0; index < this.floors.length; ++index) {
			this.floors[index] = new Floor();
		}

		this.currentDirection = Direction.kIDLE;
		this.currentFloor = this.controlSystem.getFloorBottom();
		this.index = -1;
	}


	/**
	 * Computes the "cost" of moving this elevator from its current
	 * position to a designated floor.  If the elevator is not allowed
	 * to go to the given floor, the cost is "infinite."
	 *
	 * @param floor
	 * The desired destination floor.
	 *
	 * @return The cost of moving the elevator, as an integer greater
	 * than or equal to zero, where zero means no cost (such as the
	 * elevator is already idle at the floor).
	 */
	public int computeFloorCost(int floor)
	{
		return 0;
	}


	Direction getCurrentDirection()
	{
		return this.currentDirection;
	}


	int getCurrentFloor()
	{
		return this.currentFloor;
	}


	public String getDeviceIdentity()
	{
		return this.identity;
	}


	public ArrayList<Integer> getFloorSchedule()
	{
		ArrayList<Integer> schedule = new ArrayList<>();
		for (int index = this.controlSystem.getFloorBottom();
				index <= this.controlSystem.getFloorTop(); ++index) {
			if (this.floors[index].isScheduled) {
				schedule.add(index);
			}
		}
		return schedule;
	}


	int getIndex()
	{
		return this.index;
	}


	/**
	 * Sets this elevator's identity within the building.
	 * @param identity The device name.
	 */
	@Override
	public void setDeviceIdentity(String identity)
	{
		this.identity = identity;
	}


	public void setIsFloorSchedule(int floor, boolean isScheduled)
	{
		if (this.floors[floor].isScheduled != isScheduled) {
			this.floors[floor].isScheduled = isScheduled;
			this.setChanged();
			this.notifyObservers();
		}
	}


	public void setIndex(int index)
	{
		this.index = index;
	}


	@Override
	public void triggerDeviceChange(Stimulus stimulus)
	{
		System.out.printf("%s: trigger %s%n", this.identity, stimulus);
		switch (stimulus.type) {
		case GoToFloor:
			this.setIsFloorSchedule(stimulus.floor, true);
			break;

		default:
			break;
		}
	}


	private class Floor
	{
		boolean isEnabled;
		boolean isScheduled;

		Floor()
		{
			this.isEnabled = true;
			this.isScheduled = false;
		}
	}
}
