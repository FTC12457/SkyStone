package org.firstinspires.ftc.teamcode.robot;

public class Base {
    Hardware robot;
    Boolean control_pressed;

    private boolean opened;

    public Base(Hardware hardware) {
        robot = hardware;
        opened = true;
    }

    public void open() {
        robot.baseL.setPosition(0.25);
        robot.baseR.setPosition(0.65);
        opened = true;
    }

    public void close() {
        robot.baseL.setPosition(0.02);
        robot.baseR.setPosition(0.94);
        opened = false;
    }

    public boolean isOpen() {
        return (opened);
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
