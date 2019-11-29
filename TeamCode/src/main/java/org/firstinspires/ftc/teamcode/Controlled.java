package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp(name = "Controlled", group = "Performance")
public class Controlled extends LinearOpMode {
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

            if (gamepad2.left_bumper) {
                robot.arm.setPower(0.5); // Moves up
            } else if (gamepad2.left_trigger > 0) {
                robot.arm.setPower(-0.5); // Moves down
            } else {
                robot.arm.setPower(0);
            }

            if (gamepad2.right_trigger > 0.5) {
                robot.rise.setPower(-0.5); // Moves down
            } else if (gamepad2.right_bumper) {
                robot.rise.setPower(0.5); // Moves up
            } else {
                robot.rise.setPower(0);
            }

            claw.run(gamepad2.b, gamepad1.b);
            base.run(gamepad2.x);

            if (gamepad1.right_trigger > 0.5) {
                robot.dArm.setPosition(robot.dArm.getPosition()+0.03); // Goes back
            } else if (gamepad1.right_bumper) {
                robot.dArm.setPosition(robot.dArm.getPosition()-0.03); // Goes forward
            }

            if (gamepad1.x) {
                robot.dClaw.setPower(1);
            } else {
                robot.dClaw.setPower(0);
            }

            telemetry.update();

            sleep(25);
        }
    }
}
