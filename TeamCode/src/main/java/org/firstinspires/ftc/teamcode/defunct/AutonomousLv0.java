//package org.firstinspires.ftc.teamcode.defunct;
//
//import android.app.Activity;
//import android.content.Context;
//import android.widget.Switch;
//
//import com.qualcomm.robotcore.eventloop.opmode.Disabled;
//import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
//import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
//
//import org.firstinspires.ftc.teamcode.robot.Claw;
//import org.firstinspires.ftc.teamcode.robot.Hardware;
//
//@Disabled
//@Autonomous(name = "LVL 0", group = "Performance")
//public class AutonomousLv0 extends LinearOpMode{
//    Hardware robot = new Hardware();
//    EncoderDrive encoderDrive = new EncoderDrive(robot, this, telemetry);
//    Claw claw = new Claw(robot);
//
//    @Override
//    public void runOpMode() {
//        robot.init(hardwareMap);
//
//        Context context = hardwareMap.appContext;
//        int id_p = context.getResources().getIdentifier("platform", "id", context.getPackageName());
//        Switch platform = (((Activity)context).findViewById(id_p));
//
//        int id_c = context.getResources().getIdentifier("color", "id", context.getPackageName());
//        Switch color = (((Activity)context).findViewById(id_c));
//
//        telemetry.addData(">", "Press Play to start");
//        telemetry.update();
//        waitForStart();
//
//        /*
//        This class works to avoid interfering with the alliance partner's robot, in the possibility
//        that their autonomous far outclasses ours.
//         */
//
//        encoderDrive.encoderDrive(0.3, "Forward", 54, 10);
//        sleep(20000);
//        encoderDrive.encoderDrive(0.3, "Forward", -30, 10);
//        sleep(100);
//
//        if (color.isChecked()) {
//            /* Condition: Red Near */
//            encoderDrive.encoderDrive(0.3, "Strafe", -18, 10);
//        } else {
//            /* Condition: Blue Near */
//            encoderDrive.encoderDrive(0.3, "Strafe", 18, 10);
//        }
//    }
//}