package org.firstinspires.ftc.teamcode;

public class Autoblue {
    Hardware robot;

    public Autoblue(Hardware hardware) {
        robot = hardware;
    }

    public void lift() {
        robot.autoblueArm.setPosition(-0.8);
    }

    public void lower() {
        robot.autoblueArm.setPosition(-0.5);
    }

    public void open() {
        robot.autoblueClaw.setPosition(0.3);
    }

    public void close() {
        robot.autoblueClaw.setPosition(0.8);
    }
}
