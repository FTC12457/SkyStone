//package org.firstinspires.ftc.teamcode.defunct;
//
//import com.qualcomm.robotcore.eventloop.opmode.Disabled;
//import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
//import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
//
//import org.firstinspires.ftc.teamcode.robot.Drive;
//import org.firstinspires.ftc.teamcode.robot.Hardware;
//
///*
//Basic class calls only the drive functionality.
// */
//
//@Disabled
//@TeleOp(name = "DriveTest", group = "Experimental")
//public class DriveTest extends LinearOpMode {
//    Hardware robot = new Hardware();
//    Drive drive = new Drive(robot);
//
//    public void runOpMode() {
//
//        robot.init(hardwareMap);
//
//        waitForStart();
//
//        while(opModeIsActive()) {
//
//            drive.drive(gamepad1, telemetry);
//
//            telemetry.update();
//
//            sleep(25);
//        }
//    }
//}