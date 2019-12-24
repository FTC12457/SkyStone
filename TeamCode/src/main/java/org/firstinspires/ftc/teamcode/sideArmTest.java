package org.firstinspires.ftc.teamcode;

import android.app.Activity;
import android.content.Context;
import android.widget.Switch;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

@Autonomous(name = "sideArmTest", group = "Performance")
public class sideArmTest extends LinearOpMode{
    Hardware robot = new Hardware();
    EncoderDrive encoderDrive = new EncoderDrive(robot, this, telemetry);
    Claw claw = new Claw(robot);

    @Override
    public void runOpMode() {
        robot.init(hardwareMap);

        Context context = hardwareMap.appContext;
        int id_p = context.getResources().getIdentifier("platform", "id", context.getPackageName());
        Switch platform = (((Activity)context).findViewById(id_p));

        int id_c = context.getResources().getIdentifier("color", "id", context.getPackageName());
        Switch color = (((Activity)context).findViewById(id_c));

        telemetry.addData(">", "Press Play to start");
        telemetry.update();
        waitForStart();

        /*
        This class works to avoid interfering with the alliance partner's robot, in the possibility
        that their autonomous far outclasses ours.
         */

        //drive back
        encoderDrive.encoderDrive(0.3, "Forward", -6, 10);

        //arm stuff
        sleep(1000);
        robot.autoblueArm.setPosition(0.71);
        sleep(1000);
        robot.autoblueClaw.setPosition(0.8);
        sleep(1000);
        robot.autoblueArm.setPosition(0.25);
        sleep(1000);

        //drive forward
        encoderDrive.encoderDrive(0.3, "Forward", 24, 10);

        //arm stuff
        sleep(1000);
        robot.autoblueArm.setPosition(0.6);
        sleep(1000);
        robot.autoblueClaw.setPosition(0.3);
        sleep(1000);
        robot.autoblueArm.setPosition(0.25);
        sleep(1000);

        //drive back
        encoderDrive.encoderDrive(0.3, "Forward", -18, 10);
    }
}