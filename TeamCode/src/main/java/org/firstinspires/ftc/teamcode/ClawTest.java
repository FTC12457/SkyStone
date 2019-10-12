package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp(name = "Claw Test", group = "Experimental")
public class ClawTest extends LinearOpMode {
    Hardware robot = new Hardware();
    Drive drive = new Drive(robot);

    public void runOpMode() {

        robot.init(hardwareMap);

        waitForStart();

        while(opModeIsActive()) {

            drive.drive(gamepad1, telemetry);

            robot.arm.setPower(0);
            robot.rise.setPower(0);

            if (gamepad2.dpad_up) {
                robot.arm.setPower(-0.4); //moves away from robot
            } else if (gamepad2.dpad_down) {
                robot.arm.setPower(0.4); // Moves towards robot
            }

            if (gamepad2.dpad_left) {
                robot.rise.setPower(-0.4); // Moves down
            } else if (gamepad2.dpad_right) {
                robot.rise.setPower(0.4); // Moves up
            }

            if (gamepad2.y) {
                robot.clawLeft.setPosition(0.65); // Closes
                robot.clawRight.setPosition(0.35);
            } else {
                robot.clawLeft.setPosition(0.8); // Opens
                robot.clawRight.setPosition(0.2);
            }

            telemetry.update();

            sleep(25);
        }
    }
}
