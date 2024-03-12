package jqa.elevator;


import java.util.ArrayList;
import java.util.Collection;


/**
 * Constructs all the objects for a proper building.
 *
 * <p>
 *     Normally, this could be driven by a configuration file,
 *     giving the number of floors, elevators, etc.  To simplify
 *     things for now, this is done with code and would be
 *     replaced later.
 * </p>
 */
public class Building
{
	private static final int FLOOR_COUNT = 52;
	private static final int ELEVATOR_COUNT = 10;

	ElevatorControlSystem controlSystem;

	public Building()
	{
		this.controlSystem = new ElevatorControlSystem(FLOOR_COUNT);

		for (int j = 0; j < ELEVATOR_COUNT; ++j) {
			Elevator elevator = new Elevator(this.controlSystem);
			this.controlSystem.addElevator(elevator);
		}

		/* Put a call button on every floor.  We could provide multiple
		 * buttons, without affecting much.  But that's unnecessary for now.
		 */
		for (int floor = 1; floor <= FLOOR_COUNT; ++floor) {
			FloorCallButton floorCallButton = new FloorCallButton(this.controlSystem, floor);
			floorCallButton.setDeviceIdentity(String.format("FCB-%d", floor));
			this.controlSystem.addFloorCallButton(floorCallButton);
		}
	}


	void printElevatorStatus()
	{
		Collection<Elevator> elevators;

		elevators = this.controlSystem.getElevators();
		for (Elevator elevator: elevators) {
			System.out.printf("%-10s", elevator.getDeviceIdentity());
			System.out.printf(
					"  %2d:%s",
					elevator.getCurrentFloor(),
					elevator.currentDirection.status
			);
			ArrayList<Integer> schedule = elevator.getFloorSchedule();
			System.out.printf("  => [");
			String separator = "";
			for (int floor: schedule) {
				System.out.printf("%s%d", separator, floor);
				separator = ", ";
			}
			System.out.printf("]%n");
		}
	}


	void printFloorCallStatus()
	{
		Collection<FloorCallButton> buttons;
		int index = 0;
		String separator = "";

		buttons = this.controlSystem.getFloorCallButtons();
		for (FloorCallButton button: buttons) {
			if (index % 10 == 0) {
				System.out.printf("%s%-10s", separator, "Calls");
				separator = "\n";
			}
			String status = "";
			if (button.isDown()) {
				status += "D";
			}
			if (button.isUp()) {
				status += "U";
			}
			System.out.printf("  %2d:%2s", button.getFloor(), status);
			++index;
		}
		System.out.printf("%n");
	}
}
