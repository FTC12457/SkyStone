package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.Gamepad;
import java.lang.Math;
import org.firstinspires.ftc.robotcore.external.Telemetry;

/* Class for drive functionality. Called in other classes.
 */

public class Drive {
    Hardware robot = new Hardware();

    float MASTER_DRIVE_MULTIPLIER = 0.6f; // Multipliers
    float FORWARDNESS_MULTIPLIER = 0.5f;
    float STRAFENESS_MULTIPLIER = 1f;
    float TURNYNESS_MULTIPLIER = 0.8f;

    Boolean a_pressed = false;

    public Drive(Hardware hardware) {

        robot = hardware; // Constructor

    }

    public void drive(Gamepad gamepad, Telemetry telemetryInstance){ // The called method.

        if (gamepad.a) {
            if (!a_pressed) {
                if (MASTER_DRIVE_MULTIPLIER == 0.4f) {
                    MASTER_DRIVE_MULTIPLIER = 0.7f;
                    FORWARDNESS_MULTIPLIER  = 0.8f;
                } else {
                    MASTER_DRIVE_MULTIPLIER = 0.4f;
                    FORWARDNESS_MULTIPLIER  = 0.6f;
                }
            }
            a_pressed = true;
        } else {
            a_pressed = false;
        }

        /* Three components of robot movement: Forwards/Backwards, Left/Right, and Turning.
         */

        double FORWARDNESS = gamepad.left_stick_y * FORWARDNESS_MULTIPLIER;
        double STRAFENESS = gamepad.left_stick_x * STRAFENESS_MULTIPLIER;
        double TURNYNESS = gamepad.right_stick_x * TURNYNESS_MULTIPLIER;


        /* All three components of robot movement are combined into smooth motion in the motors.
         */

        double BL = -FORWARDNESS - STRAFENESS - TURNYNESS;
        double BR = -FORWARDNESS + STRAFENESS + TURNYNESS;
        double FL = -FORWARDNESS + STRAFENESS - TURNYNESS;
        double FR = -FORWARDNESS - STRAFENESS + TURNYNESS;

        /* MAX value ensures the motors are not told to run beyond their peaks while maintaining
        proportions between three components of movement. */

        double MAX = Math.abs(Math.max(Math.max(BL, BR), Math.max(FL, FR)));

        if (MAX > 1) {
            robot.backLeftDrive.setPower(MASTER_DRIVE_MULTIPLIER * BL / MAX);
            robot.backRightDrive.setPower(MASTER_DRIVE_MULTIPLIER * BR / MAX);
            robot.frontLeftDrive.setPower(MASTER_DRIVE_MULTIPLIER * FL / MAX);
            robot.frontRightDrive.setPower(MASTER_DRIVE_MULTIPLIER * FR / MAX);
        } else {
            robot.backLeftDrive.setPower(MASTER_DRIVE_MULTIPLIER * BL);
            robot.backRightDrive.setPower(MASTER_DRIVE_MULTIPLIER * BR);
            robot.frontLeftDrive.setPower(MASTER_DRIVE_MULTIPLIER * FL);
            robot.frontRightDrive.setPower(MASTER_DRIVE_MULTIPLIER * FR);
        }

        /* Telemetry for reference. (Mostly debugging.) */

        telemetryInstance.addData("Forwardness%3A", FORWARDNESS);
        telemetryInstance.addData("Strafeness%3A", STRAFENESS);
        telemetryInstance.addData("Turnyness%3A", TURNYNESS);
        telemetryInstance.addData("LEFT REAR", robot.backLeftDrive.getPower());
        telemetryInstance.addData("RIGHT REAR", robot.backRightDrive.getPower());
        telemetryInstance.addData("LEFT FRONT", robot.frontLeftDrive.getPower());
        telemetryInstance.addData("RIGHT FRONT", robot.frontRightDrive.getPower());
    }
}
