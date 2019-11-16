package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.Gamepad;

public class Claw {
    Hardware robot = new Hardware();
    Boolean control_pressed;

    public Claw(Hardware hardware) {
        robot = hardware;
    }

    public void open() {
        robot.claw.setPosition(0.5);
    }

    public void close() {
        robot.claw.setPosition(0.7);
    }

    public boolean isOpen() {
        return (robot.claw.getPosition() == 0.5);
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
