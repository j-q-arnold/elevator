package jqa.elevator;


import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.concurrent.PriorityBlockingQueue;


/**
 * Controls a set of elevators, including sending commands and
 * receiving status from the components.
 *
 * <p>
 *     This controller makes some assumptions about the elevators under
 *     control and the building.  Some are probably unrealistic for a real
 *     building, but it is best to state them up front.
 * </p>
 * <ul>
 *     <li>
 *         One controller can handle multiple elevators.  The number is
 *         not bounded, though real-world constraints would apply in an
 *         actual building.
 *     </li>
 *     <li>
 *         The building has a fixed number of floors.  For simplicity here,
 *         the lowest floor is 1, the top is N (as set during construction).
 *         For consistency of interal and external representation, all floors
 *         are referenced with a 1-based value, not a zero-based index.
 *     </li>
 *     <li>
 *         Some elevators might not service all floors.  This is very
 *         common in practice.  Individual elevators might have dynamic
 *         control over what floors it can reach.  For example, an
 *         elevator might enable or disable certain floors at certain
 *         times of the day.
 *     </li>
 *     <li>
 *         All elevators on a floor are in the same "bank."  In practice,
 *         this means a floor call button could summon any available
 *         elevator under the controller.
 *     </li>
 * </ul>
 *
 * @see jqa.elevator
 */
public class ElevatorControlSystem
{
	private int floorCount;
	ArrayList<Elevator>        elevators;
	ArrayList<FloorCallButton> floorCallButtons;

	/**
	 * The internal queue that gathers incoming {@link Stimulus}.
	 * The controller orders the items by time, distributes the items to the
	 * designated devices, and coordinates the actions triggered by the
	 * stimuli.
	 *
	 * <p>
	 *     Note that {@link Stimulus} objects are comparable, using the
	 *     timestamp as the primary key.  Moreover, the priority queue
	 *     orders its objects with increasing values, ensuring items with
	 *     lower timestamps are handled first.
	 * </p>
	 *
	 * @see #addStimulus
	 * @see #stepStimulus
	 */
	private PriorityBlockingQueue<Stimulus> stimuliQueue;

	/**
	 * Maps the names to the objects for all {@link StandaloneDevice}
	 * objects in the system.  The simulator uses the map to distribute
	 * {@link Stimulus} objects to the appropriate destinations.
	 */
	HashMap<String, StandaloneDevice> deviceMap;


	public ElevatorControlSystem(int floorCount)
	{
		this.floorCount = floorCount;
		this.elevators = new ArrayList<>();
		this.floorCallButtons = new ArrayList<>();
		this.deviceMap = new HashMap<>();
		this.stimuliQueue = new PriorityBlockingQueue<>();
	}


	public void addElevator(Elevator elevator)
	{
		int index = this.elevators.size();
		elevator.setDeviceIdentity(String.format("E-%d", index));
		elevator.setIndex(index);
		this.elevators.add(index, elevator);
		this.deviceMap.put(elevator.getDeviceIdentity(), elevator);
	}


	public void addFloorCallButton(FloorCallButton floorCallButton)
	{
		this.floorCallButtons.add(floorCallButton);
		this.deviceMap.put(floorCallButton.getDeviceIdentity(), floorCallButton);
	}


	/**
	 * Adds one stimulus item to the priority queue, ordered by
	 * timestamp in increasing order.
	 *
	 * @param stimulus
	 * The raw objectd added to the queue.
	 *
	 * @see #stepStimulus
	 */
	public void addStimulus(Stimulus stimulus)
	{
		this.stimuliQueue.put(stimulus);
	}


	public Collection<Elevator> getElevators()
	{
		return this.elevators;
	}


	/**
	 * Gives the ground floor number.
	 * For simplicity in this project, we don't consider basement
	 * floors, though that would be common in real buildings.
	 *
	 * @return The bottom floor number (a positive integer).
	 */
	public int getFloorBottom()
	{
		return 1;
	}


	public Collection<FloorCallButton> getFloorCallButtons()
	{
		return this.floorCallButtons;
	}


	/**
	 * Gives the top floor number.
	 *
	 * @return The top floor number (a positive integer).
	 */
	public int getFloorTop()
	{
		return this.floorCount;
	}


	public int getNextTimestamp()
	{
		Stimulus stimulus = this.stimuliQueue.peek();
		if (stimulus == null) {
			return -1;
		}
		return stimulus.timestamp;
	}


	public boolean hasStimuli()
	{
		return this.stimuliQueue.size() > 0;
	}

	/**
	 * Runs the control system forward one step.  The next
	 * stimulus item is removed from the priority queue, handed
	 * to the associated device object.  That object in turn will
	 * cause its own activity, stimulating other objects, and perhaps
	 * creating additional stimuli for the queue.
	 */
	public void stepStimulus()
	{
		Stimulus stimulus;
		StandaloneDevice device;

		stimulus = this.stimuliQueue.poll();
		if (stimulus == null) {
			return;
		}
		device = this.deviceMap.get(stimulus.deviceIdentity);
		if (device == null) {
			System.err.printf("*** Unknown ID: %s%n", stimulus);
			return;
		}
		device.triggerDeviceChange(stimulus);
	}
}
