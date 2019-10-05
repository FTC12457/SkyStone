package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

//Test
//Hi
@TeleOp(name = "Claw Test", group = "Experimental")
public class clawTest extends LinearOpMode {
    Hardware robot = new Hardware();
    driveBase drive = new driveBase(robot);

    public void runOpMode() {

        robot.init(hardwareMap);

        waitForStart();

        while(opModeIsActive()) {

            drive.drive(gamepad1, telemetry);

            //robot.arm.setPower(gamepad1.right_stick_y*0.2); //added a multiplier of 0.2

            if (gamepad1.y) {
                robot.clawLeft.setPosition(0.65);
                robot.clawRight.setPosition(0.35);
            } else {
                robot.clawLeft.setPosition(0.8);
                robot.clawRight.setPosition(0.2);
            }

            sleep(25);
        }
    }
}
