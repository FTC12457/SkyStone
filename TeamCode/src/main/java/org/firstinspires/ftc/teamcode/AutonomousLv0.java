package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous(name = "LVL 0", group = "Performance")
public class AutonomousLv0 extends LinearOpMode{
    Hardware robot = new Hardware();
    EncoderDrive encoderDrive = new EncoderDrive(robot);
    Claw claw = new Claw(robot);

    @Override
    public void runOpMode() {
        robot.init(hardwareMap);

        telemetry.addData(">", "Press Play to start");
        telemetry.update();
        waitForStart();

        encoderDrive.encoderDrive(0.3, "Forward", 54, 10, this, telemetry);
        sleep(20000);
        encoderDrive.encoderDrive(0.3, "Forward", -30, 10, this, telemetry);
        sleep(100);
        encoderDrive.encoderDrive(0.3, "Strafe", 18, 10, this, telemetry);
    }
}