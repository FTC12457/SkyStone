package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp(name = "Claw Test", group = "Experimental")
public class ClawTest extends LinearOpMode {
    Hardware robot = new Hardware();
    Drive drive = new Drive(robot);

    public void runOpMode() {

        robot.init(hardwareMap);

        Boolean b_pressed = false;

        waitForStart();

        while(opModeIsActive()) {

            drive.drive(gamepad1, telemetry);

            if (gamepad2.left_bumper) {
                robot.arm.setPower(0.5); // Moves up
            } else if (gamepad2.left_trigger > 0) {
                robot.arm.setPower(-0.5); // Moves down
            } else {
                robot.arm.setPower(0);
            }

            if (gamepad2.right_bumper) {
                robot.rise.setPower(-0.5); // Moves down
            } else if (gamepad2.right_trigger > 0) {
                robot.rise.setPower(0.5); // Moves up
            } else {
                robot.rise.setPower(0);
            }

            if (gamepad2.b) {
                if (!b_pressed) {
                    if (robot.claw.getPosition() == 0.3) {
                        robot.claw.setPosition(0.59); // Closes
                    } else {
                        robot.claw.setPosition(0.3); // Opens
                    }
                }
                b_pressed = true;
            } else {
                b_pressed = false;
            }

            if (gamepad2.dpad_down) {
                robot.base.setPower(0.2);
            } else if (gamepad2.dpad_up) {
                robot.base.setPower(-0.2);
            } else {
                robot.base.setPower(0);
            }

            telemetry.update();

            sleep(25);
        }
    }
}
