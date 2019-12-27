package org.firstinspires.ftc.teamcode;

public class Base {
    Hardware robot;
    Boolean control_pressed;

    private boolean opened;

    public Base(Hardware hardware) {
        robot = hardware;
    }

    public void open() {
        robot.baseR.setPosition(1);
        robot.baseL.setPosition(0);
        opened = true;
    }

    public void close() {
        robot.baseR.setPosition(0.1);
        robot.baseL.setPosition(0.9);
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
