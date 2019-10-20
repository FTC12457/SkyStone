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
                robot.arm.setPower(-0.2); // Moves away from robot
            } else if (gamepad2.dpad_down) {
                robot.arm.setPower(0.3); // Moves towards robot
            } else {
                robot.arm.setPower(0);
            }

            //if (gamepad2.dpad_left) {
            //    robot.rise.setPower(-0.4); // Moves down
            //} else if (gamepad2.dpad_right) {
            //    robot.rise.setPower(0.4); // Moves up
            //} else {
            //    robot.rise.setPower(0);
            //}

            if (gamepad2.y) {
                if (!y_pressed) {
                    if (robot.claw.getPosition() == 0.3) {
                        robot.claw.setPosition(0.58); // Closes
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
