package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous(name = "Red Far", group = "Experimental")
public class AutonomousRedFar extends LinearOpMode{
    Hardware robot = new Hardware();
    EncoderDrive encoderDrive = new EncoderDrive(robot);

    @Override
    public void runOpMode() {
        robot.init(hardwareMap);

        telemetry.addData(">", "Press Play to start");
        telemetry.update();
        waitForStart();

        encoderDrive.encoderDrive(0.3, "Forward", 27, 10, this, telemetry);
        sleep(100);
        encoderDrive.encoderDrive(0.3, "Strafe", 27, 10, this, telemetry);
    }
}
