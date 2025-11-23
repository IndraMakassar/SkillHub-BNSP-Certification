package com.indramakassar.view;

import org.fest.swing.edt.FailOnThreadViolationRepaintManager;
import org.fest.swing.edt.GuiActionRunner;
import org.fest.swing.edt.GuiQuery;
import org.fest.swing.fixture.FrameFixture;
import org.junit.*;

import javax.swing.*;
import java.awt.*;

import static org.junit.Assert.*;
import static org.junit.Assume.assumeFalse;

public class EnrollmentViewFestTest {

    private FrameFixture window;

    @BeforeClass
    public static void setupOnce() {
        // Install FEST to check EDT rule
        FailOnThreadViolationRepaintManager.install();
    }

    @Before
    public void setUp() {
        assumeFalse(GraphicsEnvironment.isHeadless());
        JFrame frame = GuiActionRunner.execute(new GuiQuery<JFrame>() {
            @Override
            protected JFrame executeInEDT() {
                JFrame f = new JFrame("EnrollmentView Test");
                f.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
                f.setContentPane(new EnrollmentView());
                f.pack();
                return f;
            }
        });
        window = new FrameFixture(frame);
        window.show();
    }

    @After
    public void tearDown() {
        if (window != null) {
            window.cleanUp();
        }
    }

    @Test
    public void components_are_present_and_named() {
        // Comboboxes
        window.comboBox("enrollment.cmbStudent").requireVisible();
        window.comboBox("enrollment.cmbClass").requireVisible();

        // Buttons
        window.button("enrollment.btnEnroll").requireVisible();
        window.button("enrollment.btnRefresh").requireVisible();
        // Filter panel controls
        window.comboBox("enrollment.cmbFilterType").requireVisible();
        window.comboBox("enrollment.cmbFilterValue").requireVisible();
        window.button("enrollment.btnApplyFilter").requireVisible();
        window.button("enrollment.btnDrop").requireVisible();

        // Table and status label
        window.table("enrollment.table").requireVisible();
        window.label("enrollment.lblStatus").requireVisible().requireText("Ready.");
    }
}
