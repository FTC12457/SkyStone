package org.firstinspires.ftc.teamcode.performance;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.robot.Autored;
import org.firstinspires.ftc.teamcode.robot.Base;
import org.firstinspires.ftc.teamcode.robot.Claw;
import org.firstinspires.ftc.teamcode.robot.Drive;
import org.firstinspires.ftc.teamcode.robot.Hardware;

@TeleOp(name = "Controlled", group = "Performance")
public class Controlled extends LinearOpMode {
    Hardware robot = new Hardware();
    Drive drive = new Drive(robot);
    Claw claw = new Claw(robot);
    Base base = new Base(robot);

    Autored autored = new Autored(robot);

    public void runOpMode() {

        robot.init(hardwareMap);

        claw.init();

        waitForStart();

        while(opModeIsActive()) {

            drive.drive(gamepad1, telemetry);

            if (gamepad1.dpad_up) {
                robot.capper.setPower(1);
            } else if (gamepad1.dpad_down) {
                robot.capper.setPower(-1);
            } else {
                robot.capper.setPower(0);
            }

            /*
            if (gamepad2.dpad_up) {
                autored.lift();
            }
            if (gamepad2.dpad_down) {
                autored.lowergrab();
            }

            if (gamepad2.dpad_left) {
                autored.open();
            }
            if (gamepad2.dpad_right) {
                autored.close();
            }
            if (gamepad2.left_bumper) {
                autored.retract();
            }
            if (gamepad2.right_bumper) {
                autored.lowerplace();
            }
            */  
            robot.bean.setPower(gamepad1.left_trigger - gamepad1.right_trigger);

            robot.arm.setPower(gamepad2.left_stick_y);

            claw.run(gamepad2.b, gamepad2.y);
            base.run(gamepad2.x);

            telemetry.update();

            sleep(25);
        }
    }
}
