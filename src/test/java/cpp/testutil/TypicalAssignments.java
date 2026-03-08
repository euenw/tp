package cpp.testutil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import cpp.model.assignment.Assignment;

/**
 * A utility class containing a list of {@code Assignment} objects to be used in
 * tests.
 */
public class TypicalAssignments {
    public static final Assignment ASSIGNMENT_ONE = new AssignmentBuilder()
            .withId("793f92b5-9e96-47bb-94cd-e8ede5523d7a").withName("Assignment 1")
            .withDeadline("13-12-2020 10:00").build();

    public static final Assignment ASSIGNMENT_TWO = new AssignmentBuilder()
            .withId("793f92b5-9e96-47bb-94cd-e8ede5523d7b").withName("Assignment 2")
            .withDeadline("14-12-2020 10:00").build();

    public static final Assignment ASSIGNMENT_THREE = new AssignmentBuilder()
            .withId("793f92b5-9e96-47bb-94cd-e8ede5523d7c").withName("Assignment 3")
            .withDeadline("15-12-2020 10:00").build();

    public static List<Assignment> getTypicalAssignments() {
        return new ArrayList<>(Arrays.asList(TypicalAssignments.ASSIGNMENT_ONE));
    }
}
