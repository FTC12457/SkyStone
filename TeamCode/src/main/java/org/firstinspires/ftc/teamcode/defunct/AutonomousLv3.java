//package org.firstinspires.ftc.teamcode;
//
//import android.app.Activity;
//import android.content.Context;
//import android.widget.Switch;
//
//import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
//import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
//
//@Autonomous(name = "LVL 3", group = "Performance")
//public class AutonomousLv3 extends LinearOpMode {
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
//        This class attempts to place a skystone, drag the base plate, and park.
//         */
//
//        if (color.isChecked()) {
//            /* Condition: Red Far */
//            encoderDrive.encoderDrive(0.4, "Strafe", -9, 10);
//            encoderDrive.encoderDrive(0.4, "Forward", 26, 10);
//                sleep(300);
//                float alpha0 = robot.color.alpha();
//                sleep(300);
//                encoderDrive.encoderDrive(0.4, "Strafe", 8.5, 10);
//                sleep(300);
//                float alpha1 = robot.color.alpha();
//                sleep(300);
//                int skystone = 2;
//                if (alpha0 >= 1.5 * alpha1) {
//                    skystone = 1;
//                }
//                if (alpha1 >= 1.5 * alpha0) {
//                    skystone = 0;
//                }
//                if (skystone == 0) {
//                    encoderDrive.encoderDrive(0.4, "Strafe", -5.5, 10);
//                    encoderDrive.encoderDrive(0.3, "Forward", 6, 10);
//                    claw.close();
//                    sleep(1000);
//                    robot.rise.setPower(1);
//                    sleep(2500);
//                    encoderDrive.encoderDrive(0.4, "Forward", -12, 10);
//                    robot.rise.setPower(0);
//                    encoderDrive.encoderDrive(0.4, "Strafe", 96, 10);
//                }
//                if (skystone == 1) {
//                    encoderDrive.encoderDrive(0.4, "Strafe", 5, 10);
//                    encoderDrive.encoderDrive(0.3, "Forward", 6, 10);
//                    claw.close();
//                    sleep(1000);
//                    robot.rise.setPower(1);
//                    sleep(2500);
//                    encoderDrive.encoderDrive(0.4, "Forward", -12, 10);
//                    robot.rise.setPower(0);
//                    encoderDrive.encoderDrive(0.4, "Strafe", 90, 10);
//                }
//                if (skystone == 2) {
//                    encoderDrive.encoderDrive(0.4, "Strafe", 13.5, 10);
//                    encoderDrive.encoderDrive(0.3, "Forward", 6, 10);
//                    claw.close();
//                    sleep(1000);
//                    robot.rise.setPower(1);
//                    sleep(2500);
//                    encoderDrive.encoderDrive(0.4, "Forward", -12, 10);
//                    robot.rise.setPower(0);
//                    encoderDrive.encoderDrive(0.4, "Strafe", 84, 10);
//                }
////                robot.arm.setPower(0.8);
////                sleep(500);
////                robot.arm.setPower(0);
//                encoderDrive.encoderDrive(0.4, "Forward", 16, 10);
//                robot.claw.setPosition(0);
//                sleep(500);
//                base.close();
//                robot.rise.setPower(-1);
//                sleep(1000);
//                robot.rise.setPower(0);
//                //encoderDrive.encoderDrive(0.4, "Strafe", 12, 10);
//                encoderDrive.encoderDrive(0.4, "Forward", -42, 10);
//                base.open();
//                encoderDrive.encoderDrive(0.4, "Strafe", -32, 10);
////                robot.arm.setPower(-0.7);
////                sleep(500);
////                robot.arm.setPower(0);
//                encoderDrive.encoderDrive(0.6, "Forward", 24, 10);
//                //encoderDrive.encoderDrive(0.4, "Strafe", -24, 10);
//            encoderDrive.encoderDrive(0.4, "Strafe", -16, 10);
//        } else {
//            /* Condition: Blue Far */
//            //encoderDrive.encoderDrive(0.4, "Strafe", 4, 10);
//                encoderDrive.encoderDrive(0.4, "Forward", 27, 10);
//                sleep(300);
//                float alpha0 = robot.color.alpha();
//                sleep(300);
//                encoderDrive.encoderDrive(0.4, "Strafe", -8, 10);
//                sleep(300);
//                float alpha1 = robot.color.alpha();
//                sleep(300);
//                int skystone = 2;
//                if (alpha0 >= 1.5 * alpha1) {
//                    skystone = 1;
//                }
//                if (alpha1 >= 1.5 * alpha0) {
//                    skystone = 0;
//                }
//                if (skystone == 0) {
//                    encoderDrive.encoderDrive(0.4, "Strafe", 12.5, 10);
//                    encoderDrive.encoderDrive(0.4, "Forward", 6, 10);
//                    claw.close();
//                    sleep(1000);
//                    robot.rise.setPower(1);
//                    sleep(2500);
//                    encoderDrive.encoderDrive(0.4, "Forward", -12, 10);
//                    robot.rise.setPower(0);
//                    encoderDrive.encoderDrive(0.4, "Strafe", -96, 10);
//                }
//                if (skystone == 1) {
//                    encoderDrive.encoderDrive(0.4, "Strafe", 5, 10);
//                    encoderDrive.encoderDrive(0.4, "Forward", 6, 10);
//                    claw.close();
//                    sleep(1000);
//                    robot.rise.setPower(1);
//                    sleep(2500);
//                    encoderDrive.encoderDrive(0.4, "Forward", -12, 10);
//                    robot.rise.setPower(0);
//                    encoderDrive.encoderDrive(0.4, "Strafe", -90, 10);
//                }
//                if (skystone == 2) {
//                    encoderDrive.encoderDrive(0.4, "Strafe", -6.5, 10);
//                    encoderDrive.encoderDrive(0.4, "Forward", 6, 10);
//                    claw.close();
//                    sleep(1000);
//                    robot.rise.setPower(1);
//                    sleep(2500);
//                    encoderDrive.encoderDrive(0.4, "Forward", -12, 10);
//                    robot.rise.setPower(0);
//                    encoderDrive.encoderDrive(0.4, "Strafe", -84, 10);
//                }
////                robot.arm.setPower(0.8);
////                sleep(500);
////                robot.arm.setPower(0);
//                encoderDrive.encoderDrive(0.4, "Forward", 23, 10);
//                robot.claw.setPosition(0);
//                sleep(500);
//                base.close();
//                robot.rise.setPower(-1);
//                sleep(1000);
//                robot.rise.setPower(0);
//                //encoderDrive.encoderDrive(0.4, "Strafe", -12, 10);
//                encoderDrive.encoderDrive(0.4, "Forward", -48, 10);
//                base.open();
//                encoderDrive.encoderDrive(0.4, "Strafe", 36, 10);
////                robot.arm.setPower(-0.7);
////                sleep(500);
////                robot.arm.setPower(0);
//                encoderDrive.encoderDrive(0.4, "Forward", 24, 10);
//                //encoderDrive.encoderDrive(0.4, "Strafe", 24, 10);
//            encoderDrive.encoderDrive(0.4, "Strafe", 16, 10);
//        }
//
//    }
//}