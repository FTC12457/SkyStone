package org.firstinspires.ftc.teamcode;

import android.app.Activity;
import android.content.Context;
import android.widget.Switch;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous(name = "Autonomous Code", group = "Experimental")
public class AutonomousTest extends LinearOpMode{
    Hardware robot = new Hardware();
    EncoderDrive encoderDrive = new EncoderDrive(robot);

    @Override
    public void runOpMode() {
        robot.init(hardwareMap);

        Context context = hardwareMap.appContext;
        int id = context.getResources().getIdentifier("platform", "id", context.getPackageName());
        Switch platform = (((Activity)context).findViewById(id));

        telemetry.addData(">", "Press Play to start");
        telemetry.update();
        waitForStart();

        if (platform.isChecked()) {
            robot.claw.setPosition(0.59);
            sleep(200);
            robot.rise.setPower(0.5);
            sleep(200);
            robot.rise.setPower(0);
            encoderDrive.encoderDrive(0.3, "Forward", 24, 10, this, telemetry);
            sleep(100);
            encoderDrive.encoderDrive(0.3, "Turn", 22, 10, this, telemetry);
            sleep(100);
            encoderDrive.encoderDrive(0.3, "Forward", 8, 10, this, telemetry);
            sleep(100);
            robot.claw.setPosition(0.3);
            sleep(200);
            encoderDrive.encoderDrive(0.3, "Forward", -8, 10, this, telemetry);
            sleep(100);
            encoderDrive.encoderDrive(0.3, "Strafe", 8, 10, this, telemetry);
            sleep(100);
            encoderDrive.encoderDrive(0.3, "Forward", -8, 10, this, telemetry);
        } else {
            encoderDrive.encoderDrive(0.3, "Forward", 12, 10, this, telemetry);
            sleep(100);
            encoderDrive.encoderDrive(0.3, "Strafe", -8, 10, this, telemetry);
        }
    }
}
