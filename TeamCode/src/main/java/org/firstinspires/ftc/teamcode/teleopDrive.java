package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
//Test
//Hi
@TeleOp(name = "teleopDrive", group = "Experimental")
public class teleopDrive extends LinearOpMode {
    Hardware robot = new Hardware();
    driveBase drive = new driveBase(robot);

    public void runOpMode() {

        robot.init(hardwareMap);

        waitForStart();

        while(opModeIsActive()) {

            drive.drive(gamepad1, telemetry);

            sleep(25);
        }
    }
}