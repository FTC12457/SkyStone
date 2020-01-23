package org.firstinspires.ftc.teamcode.robot;

import org.firstinspires.ftc.teamcode.LinearOpMode2;

public class Autored {
    Hardware robot;

    public Autored(Hardware hardware) {
        robot = hardware;
    }

    public void lift() {
        robot.autoredArm.setPosition(0.4);
    }

    public void lowergrab() {
        robot.autoredArm.setPosition(0.76);
    }

    public void lowerplace() {
        robot.autoredArm.setPosition(0.68);
    }

    public void open() {
        robot.autoredClaw.setPosition(0.08);
    } //lower is open
    public void semiopen() {
        robot.autoredClaw.setPosition(0.18);
    }

    public void close() {
        robot.autoredClaw.setPosition(0.35);
    }

    public void retract() {
        robot.autoredArm.setPosition(0.32);
        close();
    }

    public void deploy() {
        semiopen();
        lowerplace();
    }
}
