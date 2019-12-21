//package org.firstinspires.ftc.teamcode;
//
//import android.app.Activity;
//import android.content.Context;
//import android.widget.Switch;
//
//import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
//import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
//
//@Autonomous(name = "LVL 2", group = "Performance")
//public class AutonomousLv2 extends LinearOpMode {
//    Hardware robot = new Hardware();
//    EncoderDrive encoderDrive = new EncoderDrive(robot, this, telemetry);
//    Claw claw = new Claw(robot);
//    Base base = new Base(robot);
//
//    @Override
//    public void runOpMode() {
//        robot.init(hardwareMap);
//
//        Context context = hardwareMap.appContext;
//        int id_p = context.getResources().getIdentifier("platform", "id", context.getPackageName());
//        Switch platform = (((Activity) context).findViewById(id_p));
//
//        int id_c = context.getResources().getIdentifier("color", "id", context.getPackageName());
//        Switch color = (((Activity) context).findViewById(id_c));
//
//        telemetry.addData(">", "Press Play to start");
//        telemetry.update();
//        waitForStart();
//
//        /*
//        This class places a single block, without an attempt to identify the skystone. Only
//        functions for the far-from-platform position.
//         */
//
//        if (color.isChecked()) {
//            if (platform.isChecked()) {
//                /* Condition: Red Near */
//                encoderDrive.encoderDrive(0.3, "Forward", 30, 10);
//                claw.close();
//                sleep(2000);
//                robot.rise.setPower(0.8);
//                sleep(4000);
//                robot.rise.setPower(0);
//                encoderDrive.encoderDrive(0.3, "Forward", -6, 10);
//                encoderDrive.encoderDrive(0.3, "Strafe", 84, 10);
//                encoderDrive.encoderDrive(0.3, "Forward", 15, 10);
//                claw.open();
//                sleep(500);
//                base.close();
//                sleep(500);
//                encoderDrive.encoderDrive(0.3, "Strafe", 18, 10);
//                encoderDrive.encoderDrive(0.3, "Forward", -30, 10);
//                base.open();
//            } else {
//                // Insert
//            }
//        } else {
//            if (platform.isChecked()) {
//                // Insert
//            } else {
//                // Insert
//            }
//        }
//
//    }
//}