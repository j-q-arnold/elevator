

/**
 * Classes in this package represent a building, its elevators, and the
 * elevator control system.  The top-level class, {@link jqa.elevator.ElevatorControlSystem}
 * manages the operation.
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
 * The objects making up the system include the following:
 *
 * <dl>
 *     <dt>{@link jqa.elevator.ElevatorControlSystem}</dt>
 *     <dd>
 *         The top-level control object.  It "owns" the overall
 *         responsibility for managing status and coordinating the
 *         components of the system.
 *     </dd>
 *     <dt>{@link jqa.elevator.Building}</dt>
 *     <dd>
 *         A "convenience" class to construct all the components going
 *         into the system.
 *     </dd>
 *     <dt>{@link jqa.elevator.Elevator}</dt>
 *     <dd>
 *         The moving car that that carries people (or freight or ...).
 *         The car has its own internal controls and status that are
 *         reported to the control system.
 *     </dd>
 *     <dt>{@link jqa.elevator.FloorCallButton}</dt>
 *     <dd>
 *         On each floor of the building, one or more call buttons request
 *         service at the floor.  The button sends an up/down signal to
 *         the controller, giving the desired direction of travel.
 *     </dd>
 *     <dt>{@link jqa.elevator.Driver}</dt>
 *     <dd>
 *         For simulation and control, the driver class simulates the real
 *         world, giving a way to "press buttons", give commands, record
 *         actions, etc.  In a real system, the driver class would be
 *         replaced with electro-mechanical components.
 *     </dd>
 *     <dt>{@link jqa.elevator.Scheduler}</dt>
 *     <dd>
 *         The control and scheduling of real elevator systems is complex,
 *         giving relative priorities to human convenience, energy consumption,
 *         time of day, traffic patterns, etc.  In a real system, the
 *         control system might choose to employ different scheduling
 *         priorities to account for morning arrivals, midday leave-and-return
 *         traffic for lunch, and end of day departures.
 *     </dd>
 * </dl>
 */

package jqa.elevator;
