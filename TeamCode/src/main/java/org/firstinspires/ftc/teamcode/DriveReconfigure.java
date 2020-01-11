package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@Disabled
@TeleOp(name = "Reconfig", group = "Experimental")
public class DriveReconfigure extends LinearOpMode{
    Hardware robot = new Hardware();

    public void runOpMode() {
        robot.init(hardwareMap);

        waitForStart();

        while(opModeIsActive()) {
            if (gamepad1.dpad_up) {
                robot.frontLeftDrive.setPower(1);
            } else {
                robot.frontLeftDrive.setPower(0);
            }
            if (gamepad1.dpad_right) {
                robot.frontRightDrive.setPower(1);
            } else {
                robot.frontRightDrive.setPower(0);
            }
            if (gamepad1.dpad_down) {
                robot.backRightDrive.setPower(1);
            } else {
                robot.backRightDrive.setPower(0);
            }
            if (gamepad1.dpad_left) {
                robot.backLeftDrive.setPower(1);
            } else {
                robot.backLeftDrive.setPower(0);
            }
            sleep(25);
        }
    }
}
