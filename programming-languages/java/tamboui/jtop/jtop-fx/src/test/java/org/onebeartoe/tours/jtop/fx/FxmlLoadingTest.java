package org.onebeartoe.tours.jtop.fx;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import org.testng.annotations.Test;
import static org.testng.Assert.*;

public class FxmlLoadingTest {

    private static boolean platformStarted = false;

    private static synchronized void initPlatform() {
        if (!platformStarted) {
            try {
                Platform.startup(() -> {});
                platformStarted = true;
            } catch (IllegalStateException e) {
                // Platform already started
                platformStarted = true;
            }
        }
    }

    @Test
    public void testFxmlLoadAndControllerInit() throws Exception {
        try {
            initPlatform();
        } catch (Exception e) 
        {
            // If display is unavailable in some headless environments, 
            // skip GUI rendering
//TODO: should this really just return?  maybe throw an exception so the user at 
//      least knows the test did not run
            return;
        }

        var latch = new CountDownLatch(1);
        var errorRef = new AtomicReference<Throwable>();
        var controllerRef = new AtomicReference<JTopController>();

        Platform.runLater(() -> {
            try {
                var loader = new FXMLLoader(getClass().getResource("jtop.fxml"));
                Parent root = loader.load();
                assertNotNull(root, "Root element should not be null");

                JTopController controller = loader.getController();
                assertNotNull(controller, "Controller should be injected and not null");
                controllerRef.set(controller);
            } catch (Throwable t) {
                errorRef.set(t);
            } finally {
                latch.countDown();
            }
        });

        boolean completed = latch.await(10, TimeUnit.SECONDS);
        assertTrue(completed, "FXML loading timed out");
        if (errorRef.get() != null) {
            fail("Failed to load FXML: " + errorRef.get().getMessage(), errorRef.get());
        }

        if (controllerRef.get() != null) {
            controllerRef.get().shutdown();
        }
    }
}
