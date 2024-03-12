package jqa.elevator;


/**
 * Represents a standalone device, such as a pushbutton or an
 * electromechanical sensor that can generate signals on its own
 * or in response to a human action.  For simulation purposes,
 * this standlone object can itself be triggered by a driver,
 * directing the device object to change state and thus possibly to
 * notify its observers.
 */
public interface StandaloneDevice
{
	String getDeviceIdentity();


	/**
	 * Sets this device's identity.  For a driver to distribute
	 * stimuli to devices in the system, the identity serves as
	 * the "handle" to associate a particular object with a given
	 * stimulus.
	 *
	 * @param identity
	 * The name by which the device is known.
	 */
	void setDeviceIdentity(String identity);

	/**
	 * Triggers an internal state change by the object, as directed
	 * by the command.  The internal behavior that actually occurs as
	 * a result of the trigger depend entirely on the actual object
	 * and the stimulus provided.
	 *
	 * @param stimulus
	 * The stimulus to be acted on.
	 */
	void triggerDeviceChange(Stimulus stimulus);
}
