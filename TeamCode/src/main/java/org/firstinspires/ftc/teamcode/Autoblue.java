package org.firstinspires.ftc.teamcode;

public class Autoblue {
    Hardware robot;

    public Autoblue(Hardware hardware) {
        robot = hardware;
    }

    public void lift() {
        robot.autoblueArm.setPosition(0.55);
    }

    public void lower() {
        robot.autoblueArm.setPosition(0.7);
    }

    public void open() {
        robot.autoblueClaw.setPosition(0.6);
    }

    public void close() {
        robot.autoblueClaw.setPosition(0.11);
    }
}
