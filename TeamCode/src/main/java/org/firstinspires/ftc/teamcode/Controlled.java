package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp(name = "Controlled", group = "Performance")
public class Controlled extends LinearOpMode {
    Hardware robot = new Hardware();
    Drive drive = new Drive(robot);
    Claw claw = new Claw(robot);

    public void runOpMode() {

        robot.init(hardwareMap);

        claw.init();

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

            claw.run(gamepad2.b);

            if (gamepad2.dpad_down) {
                robot.baseL.setPosition(1);
                robot.baseR.setPosition(1);
            } else if (gamepad2.dpad_up) {
                robot.baseL.setPosition(0);
                robot.baseR.setPosition(0);
            }

            telemetry.update();

            sleep(25);
        }
    }
}
