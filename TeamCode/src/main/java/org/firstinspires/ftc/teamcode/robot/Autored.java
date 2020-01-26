package org.firstinspires.ftc.teamcode.robot;

import org.firstinspires.ftc.teamcode.LinearOpMode2;

public class Autored {
    Hardware robot;

    public Autored(Hardware hardware) {
        robot = hardware;
    }

    // Lift a skystone, assuming that the claw just grabbed a skystone.
    public void lift() {
        robot.autoredArm.setPosition(0.4);
    }

    // Lower the arm to the position when grab a skystone
    public void lowergrab() {
        robot.autoredArm.setPosition(0.76);
    }

    // Lower the arm to the position when drop a skystone on the plate
    public void lowerplace() {
        robot.autoredArm.setPosition(0.68);
    }

    public void open() {
        robot.autoredClaw.setPosition(0.08);
    }

    public void close() {
        robot.autoredClaw.setPosition(0.35);
    }

    // Raise the arm and close the claw, assume skystone is just dropped on the plate.
    public void retract() {
        robot.autoredArm.setPosition(0.32);
        close();
    }



    // This method is not used in the 3 block autonomous mode.
    public void semiopen() {
        robot.autoredClaw.setPosition(0.18);
    }

    // This method is not used in the 3 block autonomous mode.
    public void deploy() {
        semiopen();
        lowerplace();
    }
}
