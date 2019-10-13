package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous(name = "Autonomous Code", group = "Experimental")
public class AutonomousTest extends LinearOpMode{
    Hardware robot = new Hardware();
    EncoderDrive encoderDrive = new EncoderDrive(robot);

    @Override
    public void runOpMode() {
        robot.init(hardwareMap);

        telemetry.addData(">", "Press Play to start");
        telemetry.update();
        waitForStart();

        //encoderDrive.encoderDrive(0.5, "Forward", 24, 10, this, telemetry);
        sleep(1000);
        encoderDrive.encoderDrive(0.1, "Turn", 24, 10, this, telemetry);
    }
}
