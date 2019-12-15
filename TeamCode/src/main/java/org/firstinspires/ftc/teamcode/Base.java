package org.firstinspires.ftc.teamcode;

public class Base {
    Hardware robot;
    Boolean control_pressed;

    public Base(Hardware hardware) {
        robot = hardware;
    }

    public void open() {
        robot.baseR.setPosition(1);
        robot.baseL.setPosition(0);
    }

    public void close() {
        robot.baseR.setPosition(0.1);
        robot.baseL.setPosition(0.9);
    }

    public boolean isOpen() {
        return (robot.baseR.getPosition() == 1 && robot.baseL.getPosition() == 0);
    }

    public void init() {
        control_pressed = false;
    }

    public void run(Boolean control) {
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
    }
}
