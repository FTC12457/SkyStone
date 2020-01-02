package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp(name = "ControlledTesting", group = "Performance")
public class ControlledTesting extends LinearOpMode {
    Hardware robot = new Hardware();
    Drive drive = new Drive(robot);
    Claw claw = new Claw(robot);
    Base base = new Base(robot);

    public void runOpMode() {

        robot.init(hardwareMap);

        claw.init();

        waitForStart();

        while(opModeIsActive()) {

            drive.drive(gamepad1, telemetry);

            if (gamepad2.dpad_up) {
                robot.autoredArm.setPosition(0.25); //up
            } else if (gamepad2.dpad_down) {
                robot.autoredArm.setPosition(0.71); //down
            }

            if (gamepad2.dpad_right) {
                robot.autoredClaw.setPosition(0.8); //close
            } else if (gamepad2.dpad_left) {
                robot.autoredClaw.setPosition(0.3); //open
            }

            if (gamepad1.dpad_up) {
                robot.capper.setPower(1);
            } else if (gamepad1.dpad_down) {
                robot.capper.setPower(-1);
            } else {
                robot.capper.setPower(0);
            }

            robot.arm.setPower(gamepad2.left_stick_y);

            claw.run(gamepad2.b, gamepad2.y);
            base.run(gamepad2.x);

            telemetry.update();

            sleep(25);
        }
    }
}
