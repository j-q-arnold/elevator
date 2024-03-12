package jqa.elevator;


import java.util.Observable;


public class FloorCallButton
	extends Observable
	implements StandaloneDevice
{
	private ElevatorControlSystem controlSystem;
	private int floor;
	private String identity;
	private boolean isUp;
	private boolean isDown;


	/**
	 * Creates a new floor call button for a specific floor.
	 *
	 * @param controlSystem
	 * The overall system that owns this object.
	 *
	 * @param floor
	 * The floor on which the button resides.
	 */
	public FloorCallButton(ElevatorControlSystem controlSystem, int floor)
	{
		this.controlSystem = controlSystem;
		this.floor = floor;
		this.isDown = false;
		this.isUp = false;
	}


	public String getDeviceIdentity()
	{
		return this.identity;
	}


	int getFloor()
	{
		return this.floor;
	}


	boolean isDown()
	{
		return this.isDown;
	}


	boolean isUp()
	{
		return this.isUp;
	}


	public void setDeviceIdentity(String identity)
	{
		this.identity = identity;
	}

	/**
	 * Sets the down direction indicator of this call button.
	 * If the button state changes, all observers are notified.
	 *
	 * @param isDown
	 * The new value of the button indicator, which can be on (true)
	 * or off (false).
	 */
	public void setDown(boolean isDown)
	{
		/* Ignore down requests on the bottom floor.
		 */
		if (isDown && this.floor <= this.controlSystem.getFloorBottom()) {
			return;
		}
		if (this.isDown != isDown) {
			this.isDown = isDown;
			this.setChanged();
			this.notifyObservers();
		}
	}


	/**
	 * Sets the up direction indicator of this call button.
	 * If the button state changes, all observers are notified.
	 *
	 * @param isUp
	 * The new value of the button indicator, which can be on (true)
	 * or off (false).
	 */
	public void setUp(boolean isUp)
	{
		/* Ignore up requests on the top floor.
		 */
		if (isUp && this.floor >= this.controlSystem.getFloorTop()) {
			return;
		}
		if (this.isUp != isUp) {
			this.isUp = isUp;
			this.setChanged();
			this.notifyObservers();
		}
	}


	/**
	 * {@inheritDoc}
	 * <p>
	 *     The floor call button responds only to button up and down
	 *     events.  The button handles stimuli through its "normal"
	 *     state change methods.
	 * </p>
	 */
	@Override
	public void triggerDeviceChange(Stimulus stimulus)
	{
		System.out.printf("%s: trigger %s%n", this.identity, stimulus);
		if (stimulus.type == Stimulus.StimulusType.ButtonDown) {
			this.setDown(true);
		}
		else if (stimulus.type == Stimulus.StimulusType.ButtonUp) {
			this.setUp(true);
		}
		else {
			return;
		}
	}
}
