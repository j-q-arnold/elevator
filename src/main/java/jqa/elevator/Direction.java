package jqa.elevator;


/**
 * Indicates the direction of travel for an elevator.
 */
public enum Direction
{
	kDOWN("down", "D"),
	kIDLE("idle", "I"),
	kUP("up", "U"),
	;

	final String mnemonic;
	final String status;

	Direction(String mnemonic, String status)
	{
		this.mnemonic = mnemonic;
		this.status = status;
	}
}
