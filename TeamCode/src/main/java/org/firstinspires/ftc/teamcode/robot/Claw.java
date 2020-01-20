package org.firstinspires.ftc.teamcode.robot;

public class Claw {
    Hardware robot;
    public Claw(Hardware hardware) {
        robot = hardware;
    }

    public void open() {
        robot.claw.setPosition(0);
    }

    public void close() {
        robot.claw.setPosition(0.75);
    }

    public void run(Boolean control, Boolean open) {
        if (control) {
            close();
        } else if (open) {
            open();
        }
    }
}
