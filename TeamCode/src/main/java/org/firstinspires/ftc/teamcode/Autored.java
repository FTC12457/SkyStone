package org.firstinspires.ftc.teamcode;

public class Autored {
    Hardware robot;

    public Autored(Hardware hardware) {
        robot = hardware;
    }

    public void lift() {
        robot.autoredArm.setPosition(0.38);
    }

    public void lower() {
        robot.autoredArm.setPosition(0.65);
    }

    public void open() {
        robot.autoredClaw.setPosition(0.5);
    }

    public void close() {
        robot.autoredClaw.setPosition(0.8);
    }
}
