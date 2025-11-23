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

public class ClassViewFestTest {

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
                JFrame f = new JFrame("ClassView Test");
                f.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
                f.setContentPane(new ClassView());
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
        // Text fields
        window.textBox("class.txtClassName").requireVisible();
        window.textBox("class.txtInstructor").requireVisible();
        // ID is not editable (disabled)
        window.textBox("class.txtId").requireDisabled();
        // Description as text area
        window.textBox("class.txtDescription").requireVisible();

        // Buttons
        window.button("class.btnAdd").requireVisible();
        window.button("class.btnUpdate").requireVisible();
        window.button("class.btnDelete").requireVisible();
        window.button("class.btnClear").requireVisible();

        // Table and status label
        window.table("class.table").requireVisible();
        window.label("class.lblStatus").requireVisible().requireText("Ready.");
    }
}
