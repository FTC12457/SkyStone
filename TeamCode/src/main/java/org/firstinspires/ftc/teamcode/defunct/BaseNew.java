//package org.firstinspires.ftc.teamcode.defunct;
//
//import com.acmerobotics.roadrunner.geometry.Pose2d;
//import com.acmerobotics.roadrunner.trajectory.Trajectory;
//import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
//import com.qualcomm.robotcore.eventloop.opmode.Disabled;
//
//import org.firstinspires.ftc.teamcode.robot.Autored;
//import org.firstinspires.ftc.teamcode.robot.Base;
//import org.firstinspires.ftc.teamcode.robot.Hardware;
//import org.firstinspires.ftc.teamcode.LinearOpMode2;
//import org.firstinspires.ftc.teamcode.roadrunner.mecanum.SampleMecanumDriveBase;
//import org.firstinspires.ftc.teamcode.roadrunner.mecanum.SampleMecanumDriveREV;
//
///*
//This class is the autonomous for blue that does everything, and presumes the alliance partner robot
//immediately heads to park, next to the wall.
// */
//
//@Disabled
//@Autonomous(name = "Baseness", group = "Performance")
//public class BaseNew extends LinearOpMode2 {
//    Hardware robot = new Hardware();
//    // EncoderDrive encoderDrive = new EncoderDrive(robot, this, telemetry);
//    Base base = new Base(robot);
//    Autored autored = new Autored(robot);
//
//    @Override
//    public void runOpMode() throws InterruptedException {
//
//        robot.init(hardwareMap);
//
//        SampleMecanumDriveBase drive = new SampleMecanumDriveREV(hardwareMap);
//
//        waitForStart();
//
//        sleep(250);
//        base.close();
//
//        sleep(250);
//
//        Trajectory pull = drive.trajectoryBuilder()
//                .reverse()
//                .splineTo(new Pose2d(-20, 20, -0.5 * Math.PI))
//                .build();
//
//        drive.followTrajectorySync(pull);
//
//        drive.update();
//
//        Trajectory push = drive.trajectoryBuilder()
//                .strafeLeft(4)
//                .forward(12)
//                .build();
//
//        drive.followTrajectorySync(push);
//
//        base.open();
//        sleep(250);
//
//        drive.update();
//
//        Trajectory park = drive.trajectoryBuilder()
//                .back(12)
//                .build();
//
//        drive.followTrajectorySync(park);
//    }
//}