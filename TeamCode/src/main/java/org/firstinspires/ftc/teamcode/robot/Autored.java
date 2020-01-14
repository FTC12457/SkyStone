package org.firstinspires.ftc.teamcode.robot;

public class Autored {
    Hardware robot;

    public Autored(Hardware hardware) {
        robot = hardware;
    }

    public void lift() {
        robot.autoredArm.setPosition(0.30);
    }

    public void lowergrab() {
        robot.autoredArm.setPosition(0.68);
    }

    public void lowerplace() {
        robot.autoredArm.setPosition(0.52);
    }

    public void open() {
        robot.autoredClaw.setPosition(0.48);
    }

    public void close() {
        robot.autoredClaw.setPosition(0.73);
    }

    public void retract() {
        robot.autoredArm.setPosition(0.21);
        close();
    }

    public void deploy() {
        open();
        lowergrab();
    }
}
