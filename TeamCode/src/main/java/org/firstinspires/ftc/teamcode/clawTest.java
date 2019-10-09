package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

//Test
//Hi
@TeleOp(name = "Claw Test", group = "Experimental")
public class clawTest extends LinearOpMode {
    Hardware robot = new Hardware();
    driveBase drive = new driveBase(robot);

    public void runOpMode() {

        robot.init(hardwareMap);

        waitForStart();

        while(opModeIsActive()) {

            drive.drive(gamepad1, telemetry);

            if (gamepad1.dpad_up) {
                robot.arm.setPower(-0.4); //moves away from robot
            } else if (gamepad1.dpad_down) {
                robot.arm.setPower(0.4); //moves towards robot
            } else {
                robot.arm.setPower(0);
            }

            if (gamepad1.dpad_left) {
                robot.rise.setPower(-0.2); //moves away from robot
            } else if (gamepad1.dpad_right) {
                robot.rise.setPower(0.2); //moves towards robot
            } else {
                robot.rise.setPower(0);
            }

            if (gamepad1.y) {
                robot.clawLeft.setPosition(0.65); //closes
                robot.clawRight.setPosition(0.35);
            } else {
                robot.clawLeft.setPosition(0.8); //opens
                robot.clawRight.setPosition(0.2);
            }

            sleep(25);
        }
    }
}
