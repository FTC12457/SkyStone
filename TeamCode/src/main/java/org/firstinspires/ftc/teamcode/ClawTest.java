package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp(name = "Claw Test", group = "Experimental")
public class ClawTest extends LinearOpMode {
    Hardware robot = new Hardware();
    Drive drive = new Drive(robot);

    public void runOpMode() {

        robot.init(hardwareMap);

        Boolean y_pressed = false;

        waitForStart();

        while(opModeIsActive()) {

            drive.drive(gamepad1, telemetry);

            if (gamepad2.dpad_up) {
                robot.arm.setPower(0.3); // Moves up
            } else if (gamepad2.dpad_down) {
                robot.arm.setPower(-0.3); // Moves down
            } else {
                robot.arm.setPower(0);
            }

            if (gamepad2.right_stick_y < 0) {
                robot.rise.setPower(-0.5); // Moves down
            } else if (gamepad2.right_stick_y > 0) {
                robot.rise.setPower(0.5); // Moves up
            } else {
                robot.rise.setPower(0);
            }

            if (gamepad2.y) {
                if (!y_pressed) {
                    if (robot.claw.getPosition() == 0.3) {
                        robot.claw.setPosition(0.59); // Closes
                    } else {
                        robot.claw.setPosition(0.3); // Opens
                    }
                }
                y_pressed = true;
            } else {
                y_pressed = false;
            }

            telemetry.update();

            sleep(25);
        }
    }
}
