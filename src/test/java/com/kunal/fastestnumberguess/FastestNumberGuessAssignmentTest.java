package com.kunal.fastestnumberguess;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * @author kunal
 * @project FastestNumberGuessAssignment
 */

class FastestNumberGuessAssignmentTest {

    private FastestNumberGuessAssignment fastestNumberGuessAssignmentUnderTest;

    @BeforeEach
    void setUp() {
        fastestNumberGuessAssignmentUnderTest = new FastestNumberGuessAssignment();
    }

    @Test
    void testMain_ThrowsInterruptedException() {
        assertThatThrownBy(() -> FastestNumberGuessAssignment.main(new String[]{"7"})).isInstanceOf(InterruptedException.class);
    }

    @Test
    void testRun() {
        fastestNumberGuessAssignmentUnderTest.run();
    }

    @Test
    void testMain() throws Exception {
        FastestNumberGuessAssignment.main(new String[]{"7"});
    }
}
