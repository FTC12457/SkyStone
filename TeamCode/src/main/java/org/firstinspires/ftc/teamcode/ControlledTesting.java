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
                robot.autoblueArm.setPosition(0.1); //uo
            } else if (gamepad2.dpad_down) {
                robot.autoblueArm.setPosition(0.8); //down
            }

            if (gamepad2.dpad_right) {
                robot.autoblueClaw.setPosition(0.9); //close
            } else if (gamepad2.dpad_left) {
                robot.autoblueClaw.setPosition(0.3); //open

            }

            robot.arm.setPower(gamepad2.left_stick_y);

            claw.run(gamepad2.b, gamepad2.y);
            base.run(gamepad2.x);

            telemetry.update();

            sleep(25);
        }
    }
}
