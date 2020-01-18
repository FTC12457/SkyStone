package org.firstinspires.ftc.teamcode.robot;

public class Claw {
    Hardware robot;
    Boolean control_pressed;
    Boolean override_pressed;

    public Claw(Hardware hardware) {
        robot = hardware;
    }

    public void open() {
        robot.claw.setPosition(0.45);
    }

    public void close() {
        robot.claw.setPosition(0.75);
    }

    public boolean isOpen = true;

    public void init() {
        control_pressed = false;
    }

    public void run(Boolean control, Boolean override) {
        if (control) {
            if (!control_pressed) {
                if (isOpen) {
                    close();
                    isOpen = false;
                } else {
                    open();
                    isOpen = true;
                }
            }
            control_pressed = true;
        } else {
            control_pressed = false;
        }
        if (override) {
            if (!override_pressed) {
                robot.claw.setPosition(0);
            }
            override_pressed = true;
        } else {
            override_pressed = false;
        }
    }
}
