package org.firstinspires.ftc.teamcode;

public class Claw {
    Hardware robot = new Hardware();

    public Claw(Hardware hardware) {
        robot = hardware;
    }

    public void open() {
        robot.claw.setPosition(0.5);
    }

    public void close() {
        robot.claw.setPosition(0.7);
    }
}
