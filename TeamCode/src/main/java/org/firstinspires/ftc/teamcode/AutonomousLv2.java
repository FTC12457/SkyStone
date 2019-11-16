package org.firstinspires.ftc.teamcode;

import android.app.Activity;
import android.content.Context;
import android.widget.Switch;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous(name = "LVL 2", group = "Performance")
public class AutonomousLv2 extends LinearOpMode {
    Hardware robot = new Hardware();
    EncoderDrive encoderDrive = new EncoderDrive(robot);
    Claw claw = new Claw(robot);

    @Override
    public void runOpMode() {
        robot.init(hardwareMap);

        Context context = hardwareMap.appContext;
        int id_p = context.getResources().getIdentifier("platform", "id", context.getPackageName());
        Switch platform = (((Activity) context).findViewById(id_p));

        int id_c = context.getResources().getIdentifier("color", "id", context.getPackageName());
        Switch color = (((Activity) context).findViewById(id_c));

        telemetry.addData(">", "Press Play to start");
        telemetry.update();
        waitForStart();

        if (color.isChecked()) {
            if (platform.isChecked()) {
                /* Condition: Red Near */
                claw.close();
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
                claw.open();
                sleep(200);
                encoderDrive.encoderDrive(0.3, "Forward", -8, 10, this, telemetry);
                sleep(100);
                encoderDrive.encoderDrive(0.3, "Strafe", 8, 10, this, telemetry);
                sleep(100);
                encoderDrive.encoderDrive(0.3, "Forward", -8, 10, this, telemetry);
            } else {
                // Insert
            }
        } else {
            if (platform.isChecked()) {
                // Insert
            } else {
                // Insert
            }
        }

    }
}