package org.firstinspires.ftc.teamcode.robot;

public class Autoblue {
    Hardware robot;

    public Autoblue(Hardware hardware) {
        robot = hardware;
    }

    public void lift() {
        robot.autoblueArm.setPosition(0.27);
    }

    public void lowergrab() {
        robot.autoblueArm.setPosition(0.69);
    }

    public void lowerplace() {
        robot.autoblueArm.setPosition(0.58);
    }

    public void open() {
        robot.autoblueClaw.setPosition(0.5);
    }

    public void close() {
        robot.autoblueClaw.setPosition(0.05);
    }

    public void initialize() {
        robot.autoblueArm.setPosition(0.2);
        robot.autoblueClaw.setPosition(0.05);
    }

}
