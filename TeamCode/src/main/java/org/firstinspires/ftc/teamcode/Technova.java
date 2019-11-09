package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous(name = "Technova", group = "Experimental")
public class Technova extends LinearOpMode{
    Hardware robot = new Hardware();
    EncoderDrive encoderDrive = new EncoderDrive(robot);
    Claw claw = new Claw(robot);

    @Override
    public void runOpMode() {
        robot.init(hardwareMap);

        telemetry.addData(">", "Press Play to start");
        telemetry.update();
        waitForStart();

//        claw.close();
//        sleep(200);
//        robot.rise.setPower(0.5);
//        sleep(200);
//        robot.rise.setPower(0);
//        encoderDrive.encoderDrive(0.3, "Forward", 24, 10, this, telemetry);
//        sleep(100);
//        encoderDrive.encoderDrive(0.3, "Turn", -22, 10, this, telemetry);
//        sleep(100);
//        encoderDrive.encoderDrive(0.3, "Forward", 8, 10, this, telemetry);
//        sleep(100);
//        claw.open();
//        sleep(200);
//        encoderDrive.encoderDrive(0.3, "Forward", -8, 10, this, telemetry);
//        sleep(100);
//        encoderDrive.encoderDrive(0.3, "Strafe", -8, 10, this, telemetry);
//        sleep(100);
//        encoderDrive.encoderDrive(0.3, "Forward", -8, 10, this, telemetry);

        encoderDrive.encoderDrive(0.3, "Forward", 54, 10, this, telemetry);
        sleep(20000);
        encoderDrive.encoderDrive(0.3, "Forward", -30, 10, this, telemetry);
        sleep(100);
        encoderDrive.encoderDrive(0.3, "Strafe", 18, 10, this, telemetry);
    }
}