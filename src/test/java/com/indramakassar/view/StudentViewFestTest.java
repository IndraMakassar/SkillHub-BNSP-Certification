package com.indramakassar.view;

import com.indramakassar.persistence.HibernateUtil;
import org.fest.swing.edt.FailOnThreadViolationRepaintManager;
import org.fest.swing.edt.GuiActionRunner;
import org.fest.swing.edt.GuiQuery;
import org.fest.swing.fixture.FrameFixture;
import org.junit.*;

import javax.swing.*;
import java.awt.*;

import static org.junit.Assert.*;
import static org.junit.Assume.assumeFalse;

public class StudentViewFestTest {

    private FrameFixture window;

    @BeforeClass
    public static void setupOnce() {
        HibernateUtil.resetSessionFactory();

        // Install FEST to check EDT rule
        FailOnThreadViolationRepaintManager.install();
    }

    @Before
    public void setUp() {
        assumeFalse(GraphicsEnvironment.isHeadless());
        JFrame frame = GuiActionRunner.execute(new GuiQuery<JFrame>() {
            @Override
            protected JFrame executeInEDT() {
                JFrame f = new JFrame("StudentView Test");
                f.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
                f.setContentPane(new StudentView());
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
        window.textBox("student.txtName").requireVisible();
        window.textBox("student.txtEmail").requireVisible();
        window.textBox("student.txtPhone").requireVisible();
        // ID is not editable
        window.textBox("student.txtId").requireDisabled();

        // Buttons
        window.button("student.btnAdd").requireVisible();
        window.button("student.btnUpdate").requireVisible();
        window.button("student.btnDelete").requireVisible();
        window.button("student.btnClear").requireVisible();

        // Table and status label
        window.table("student.table").requireVisible();
        window.label("student.lblStatus").requireVisible().requireText("Ready.");
    }
}
