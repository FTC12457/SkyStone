package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Gamepad;
import java.lang.Math;
import org.firstinspires.ftc.robotcore.external.Telemetry;

@TeleOp(name = "Drive", group = "Mechanisms")
public class Drive extends LinearOpMode {
    Hardware robot = new Hardware();

    float MASTER_DRIVE_MULTIPLIER = 0.6f;
    float FORWARDNESS_MULTIPLIER = 0.7f;
    float STRAFENESS_MULTIPLIER = 1f;
    float TURNYNESS_MULTIPLIER = 0.7f;

    public Drive (Hardware hardware) {
        robot = hardware;
    }

    @Override
    public void runOpMode() {

        robot.init(hardwareMap);

        waitForStart();

        while (opModeIsActive()) {

            drive(gamepad1);

            sleep(25);
        }
    }

    public void drive(Gamepad gamepad) {
        this.drive(gamepad, telemetry);
    }

    public void drive(Gamepad gamepad, Telemetry telemetryInstance){

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

        telemetryInstance.update();
    }
}
