package jqa.elevator;


import java.io.*;


/**
 * Created by jarnold on 9/14/17.
 */
public class Driver
{
	private Building building;

	public static void main(String[] args)
	{
		Driver driver = new Driver();

		driver.appMain(args);
		System.exit(0);
	}

	private void appMain(String args[])
	{
		if (args.length != 1) {
			System.err.printf("usage: %s event-file%n", this.getClass().getSimpleName());
			System.exit(1);
		}
		this.createBuilding();
		this.readEventFile(args[0]);
		this.runSimulation();
	}


	private void createBuilding()
	{
		this.building = new Building();
		this.building.printElevatorStatus();
		this.building.printFloorCallStatus();
	}


	private void readEventFile(String path)
	{
		int index = 0;
		String line;
		try (BufferedReader br = new BufferedReader(new FileReader(path))) {
			while ((line = br.readLine()) != null) {
				++index;
				System.out.printf("line %d: %s%n", index, line);
				Stimulus stimulus = Stimulus.createFromString(line);
				this.building.controlSystem.addStimulus(stimulus);
			}
		}
		catch (FileNotFoundException e) {
			System.err.printf("*** File not found: %s%n", path);
			System.exit(1);
		}
		catch (IOException e) {
			System.err.printf("*** I/O error on file: %s%n", path);
			System.exit(1);
		}
	}


	private void runSimulation()
	{
		int timestamp;
		ElevatorControlSystem controlSystem = this.building.controlSystem;

		while (controlSystem.hasStimuli()) {
			timestamp = controlSystem.getNextTimestamp();
			System.out.printf("=== time %d%n", timestamp);
			this.building.controlSystem.stepStimulus();
			this.building.printElevatorStatus();
			this.building.printFloorCallStatus();
		}
	}
}
