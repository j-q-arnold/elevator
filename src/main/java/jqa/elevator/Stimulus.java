package jqa.elevator;


import java.io.IOException;
import java.util.HashMap;


/**
 * Contains a stimulus (or simulation event).  For simplicity, this class
 * is somewhat "overloaded," containing data for all the events for all the
 * objects in the system.  An individual object would not be expected to
 * understand all events.
 */
public class Stimulus
	implements Comparable<Stimulus>
{
	/**
	 * The time at which this stimulus occurs.  The timestamp
	 * is an artificial counter, with no necessary relationship
	 * to real time.  Useful only to establish order between
	 * two objects.
	 */
	public int timestamp;

	/**
	 * Gives the target device to which this stimulus
	 * should be given.
	 */
	public String deviceIdentity;

	/**
	 * This object's type: what the stimulus triggers.
	 */
	public StimulusType type;

	/**
	 * Indicates the floor associated with this object.  Relevant
	 * for {@link StimulusType#GoToFloor}.
	 */
	public int floor;


	public Stimulus()
	{
		this.timestamp = 0;
		this.deviceIdentity = "";
		this.type = StimulusType.Nop;
		this.floor = 0;
	}


	public Stimulus(int timestamp, StimulusType type)
	{
		this();
		this.timestamp = timestamp;
		this.type = type;
	}


	@Override
	public int compareTo(Stimulus other)
	{
		int j = this.timestamp - other.timestamp;
		if (j != 0) {
			return j;
		}
		return this.type.ordinal() - other.type.ordinal();
	}


	/**
	 * Converts a line of text to a {@link Stimulus} object.
	 * The input line is defined with a fixed format, with
	 * fields separated by white space.
	 *
	 * {@code
	 * time  device  type  floor
	 * }
	 *
	 * @param line The string to be converted.
	 *
	 * @return A new object.
	 *
	 * @throws IOException If the line is bad.
	 */
	public static Stimulus createFromString(String line)
			throws IOException
	{
		Stimulus s = new Stimulus();
		String[] fields = line.split("\\s+");
		if (fields.length != 4) {
			throw new IOException(
					String.format("Stimulus line had %d fields, not 4", fields.length)
			);
		}
		try {
			s.timestamp = 0;
			s.timestamp = Integer.decode(fields[0]);
		}
		catch (NumberFormatException e) {
			// ignore
		}
		if ((s.deviceIdentity = fields[1]).isEmpty()) {
			throw new IOException("Device-ID missing");
		}
		s.type = StimulusType.getValue(fields[2]);
		try {
			s.floor = 0;
			s.floor = Integer.decode(fields[3]);
		}
		catch (NumberFormatException e) {
			// ignore
		}
		return s;
	}


	@Override
	public boolean equals(Object other)
	{
		Stimulus stimulus;
		if (other == null || this.getClass() != other.getClass()) {
			return false;
		}
		stimulus = (Stimulus)other;
		return this.timestamp == stimulus.timestamp
				&& this.type == stimulus.type
				;
	}


	@Override
	public String toString()
	{
		return String.format("t %d, id %s, t %s, f %d",
				this.timestamp,
				this.deviceIdentity,
				this.type,
				this.floor
		);
	}


	enum StimulusType
	{
		ButtonDown,
		ButtonUp,
		GoToFloor,
		Nop,
		;

		private static HashMap<String, StimulusType> nameToValueMap;

		static {
			nameToValueMap = new HashMap<>();
			for (StimulusType type: StimulusType.values()) {
				nameToValueMap.put(type.name(), type);
			}
		}

		static StimulusType getValue(String name) {
			StimulusType type = nameToValueMap.get(name);
			if (type == null) {
				type = Nop;
			}
			return type;
		}
	};
}
