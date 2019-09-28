package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
//Test
//Hi
@TeleOp(name = "Arm Test", group = "Experimental")
public class ArmTest extends LinearOpMode {
    Hardware robot = new Hardware();
    Drive drive = new Drive(robot);

    public void runOpMode() {

        robot.init(hardwareMap);

        waitForStart();

        while(opModeIsActive()) {

            drive.drive(gamepad1, telemetry);

            robot.arm.setPower(gamepad1.right_stick_y*0.2); //added a multiplier of 0.2

            telemetry.addData("MS_KOL", robot.arm.getPower());

            telemetry.update();
            sleep(25);
        }
    }
}
