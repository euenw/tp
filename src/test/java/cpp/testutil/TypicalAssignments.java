package cpp.testutil;

import cpp.model.assignment.Assignment;

/**
 * A utility class containing a list of {@code Assignment} objects to be used in
 * tests.
 */
public class TypicalAssignments {
    public static final Assignment ASSIGNMENT_ONE = new AssignmentBuilder()
            .withId("793f92b5-9e96-47bb-94cd-e8ede5523d7a").withName("Assignment 1")
            .withDeadline("13-12-2020 10:00").build();
}
