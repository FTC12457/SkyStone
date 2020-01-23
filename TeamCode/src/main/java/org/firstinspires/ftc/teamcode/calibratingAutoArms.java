package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.robot.Autored;
import org.firstinspires.ftc.teamcode.robot.Autoblue;
import org.firstinspires.ftc.teamcode.robot.Base;
import org.firstinspires.ftc.teamcode.robot.Claw;
import org.firstinspires.ftc.teamcode.robot.Drive;
import org.firstinspires.ftc.teamcode.robot.Hardware;

@TeleOp(name = "calibratingAutoArms", group = "Performance")
public class calibratingAutoArms extends LinearOpMode {
    Hardware robot = new Hardware();
    Drive drive = new Drive(robot);
    Claw claw = new Claw(robot);
    Base base = new Base(robot);

    Autored autored = new Autored(robot);
    Autoblue autoblue = new Autoblue(robot);

    public void runOpMode() {

        robot.init(hardwareMap);

        waitForStart();

        while(opModeIsActive()) {


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
            if (gamepad2.a) {
                autored.semiopen();
            }

            /*
            if (gamepad2.dpad_up) {
                autoblue.lift();
            }
            if (gamepad2.dpad_down) {
                autoblue.lowergrab();
            }

            if (gamepad2.dpad_left) {
                autoblue.open();
            }
            if (gamepad2.dpad_right) {
                autoblue.close();
            }
            if (gamepad2.left_bumper) {
                autoblue.retract();
            }
            if (gamepad2.right_bumper) {
                autoblue.lowerplace();
            }
            if (gamepad2.a) {
                autoblue.semiopen();
            }
            */

            telemetry.update();

            sleep(25);
        }
    }
}
