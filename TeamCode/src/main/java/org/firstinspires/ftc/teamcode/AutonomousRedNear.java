package org.firstinspires.ftc.teamcode;

import android.app.Activity;
import android.content.Context;
import android.widget.Switch;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous(name = "Red Near", group = "Experimental")
public class AutonomousRedNear extends LinearOpMode{
    Hardware robot = new Hardware();
    EncoderDrive encoderDrive = new EncoderDrive(robot);
    Claw claw = new Claw(robot);

    @Override
    public void runOpMode() {
        robot.init(hardwareMap);

        Context context = hardwareMap.appContext;
        int id_p = context.getResources().getIdentifier("platform", "id", context.getPackageName());
        Switch platform = (((Activity)context).findViewById(id_p));

        int id_c = context.getResources().getIdentifier("color", "id", context.getPackageName());
        Switch color = (((Activity)context).findViewById(id_c));

        int id_t = context.getResources().getIdentifier("team", "id", context.getPackageName());
        Switch team = (((Activity)context).findViewById(id_t));

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
//        encoderDrive.encoderDrive(0.3, "Turn", 22, 10, this, telemetry);
//        sleep(100);
//        encoderDrive.encoderDrive(0.3, "Forward", 8, 10, this, telemetry);
//        sleep(100);
//        claw.open();
//        sleep(200);
//        encoderDrive.encoderDrive(0.3, "Forward", -8, 10, this, telemetry);
//        sleep(100);
//        encoderDrive.encoderDrive(0.3, "Strafe", 8, 10, this, telemetry);
//        sleep(100);
//        encoderDrive.encoderDrive(0.3, "Forward", -8, 10, this, telemetry);

        if (team.isChecked()) {
            if (color.isChecked()) {
                if (platform.isChecked()) {
                    encoderDrive.encoderDrive(0.3, "Forward", 27, 10, this, telemetry);
                    sleep(100);
                    encoderDrive.encoderDrive(0.3, "Strafe", -27, 10, this, telemetry);
                } else {
                    encoderDrive.encoderDrive(0.3, "Forward", 27, 10, this, telemetry);
                    sleep(100);
                    encoderDrive.encoderDrive(0.3, "Strafe", 27, 10, this, telemetry);
                }
            } else {
                if (platform.isChecked()) {
                    encoderDrive.encoderDrive(0.3, "Forward", 27, 10, this, telemetry);
                    sleep(100);
                    encoderDrive.encoderDrive(0.3, "Strafe", 27, 10, this, telemetry);
                } else {
                    encoderDrive.encoderDrive(0.3, "Forward", 27, 10, this, telemetry);
                    sleep(100);
                    encoderDrive.encoderDrive(0.3, "Strafe", -27, 10, this, telemetry);
                }
            }
        } else {
            encoderDrive.encoderDrive(0.3, "Forward", 54, 10, this, telemetry);
            sleep(20000);
            encoderDrive.encoderDrive(0.3, "Forward", -30, 10, this, telemetry);
            sleep(100);
            encoderDrive.encoderDrive(0.3, "Strafe", 18, 10, this, telemetry);
        }
    }
}
