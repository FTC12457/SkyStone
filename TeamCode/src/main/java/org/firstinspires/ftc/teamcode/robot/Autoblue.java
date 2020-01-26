package org.firstinspires.ftc.teamcode.robot;

public class Autoblue {
    Hardware robot;

    public Autoblue(Hardware hardware) {
        robot = hardware;
    }

    public void lift() {
        robot.autoblueArm.setPosition(0.27);
    } //lower number lifts

    public void lowergrab() {
        robot.autoblueArm.setPosition(0.58);
    }

    public void lowerplace() {
        robot.autoblueArm.setPosition(0.45);
    }

    public void open() {
        robot.autoblueClaw.setPosition(0.65);
    } //lower number closes

    public void semiopen() {
        robot.autoblueClaw.setPosition(0.5);
    }

    public void close() {
        robot.autoblueClaw.setPosition(0.37);
    }

    public void retract() {
        robot.autoblueArm.setPosition(0.2);
        close();
    }

    public void deploy() {
        open();
        lowergrab();
    }
}
