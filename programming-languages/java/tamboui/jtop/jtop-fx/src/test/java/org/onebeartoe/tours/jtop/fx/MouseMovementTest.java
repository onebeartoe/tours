package org.onebeartoe.tours.jtop.fx;

import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import org.testng.annotations.Test;
import static org.testng.Assert.*;

public class MouseMovementTest {

    @Test
    public void testToggleMouseMovementOnAndOff() {
        JTopController controller = new JTopController();

        assertFalse(controller.isMouseMovementScheduled(), "Mouse movement should initially be unscheduled");

        // Toggle on
        controller.handleToggleMouse();
        assertTrue(controller.isMouseMovementScheduled(), "Mouse movement should be scheduled after first toggle");
        assertNotNull(controller.getMouseTask(), "Mouse task should be scheduled and not null");

        // Toggle off
        controller.handleToggleMouse();
        assertFalse(controller.isMouseMovementScheduled(), "Mouse movement should be unscheduled after second toggle");
        assertNotNull(controller.getCancelTask(), "Cancel task should have executed and not be null");

        controller.shutdown();
    }

    @Test
    public void testKeyPressedToggle() {
        JTopController controller = new JTopController();

        // Simulate pressing 'm'
        KeyEvent mEvent = new KeyEvent(KeyEvent.KEY_PRESSED, "m", "m", KeyCode.M, false, false, false, false);
        controller.handleKeyPressed(mEvent);
        assertTrue(controller.isMouseMovementScheduled(), "Pressing 'm' should schedule mouse movement");

        // Simulate pressing 'm' again to turn off
        controller.handleKeyPressed(mEvent);
        assertFalse(controller.isMouseMovementScheduled(), "Pressing 'm' again should cancel mouse movement");

        controller.shutdown();
    }

    @Test
    public void testTaskExecutionSafeguards() {
        JTopController controller = new JTopController();

        // Ensure MouseMoveTask runs safely without throwing exceptions even if robot is null
        controller.setRobot(null);
        var moveTask = controller.new MouseMoveTask();
        moveTask.run();

        // Ensure CancelTask runs safely
        var cancelTask = controller.new CancelTask();
        cancelTask.run();

        controller.shutdown();
    }
}
