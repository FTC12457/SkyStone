package org.firstinspires.ftc.teamcode;

public class Claw {
    Hardware robot;
    Boolean control_pressed;
    Boolean override_pressed;

    public Claw(Hardware hardware) {
        robot = hardware;
    }

    public void open() {
        robot.claw.setPosition(0.4);
    }

    public void close() {
        robot.claw.setPosition(0.7);
    }

    public boolean isOpen() {
        return (robot.claw.getPosition() == 0.4);
    }

    public void init() {
        control_pressed = false;
    }

    public void run(Boolean control, Boolean override) {
        if (control) {
            if (!control_pressed) {
                if (isOpen()) {
                    close();
                } else {
                    open();
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
