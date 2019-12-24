package org.firstinspires.ftc.teamcode;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.roadrunner.mecanum.SampleMecanumDriveBase;
import org.firstinspires.ftc.teamcode.roadrunner.mecanum.SampleMecanumDriveREV;

import org.firstinspires.ftc.robotcore.external.Telemetry;

@Autonomous(name = "sideArmTest", group = "Performance")
public class sideArmTest extends LinearOpMode{
    Hardware robot = new Hardware();
    EncoderDrive encoderDrive = new EncoderDrive(robot, this, telemetry);

    @Override
    public void runOpMode() throws InterruptedException {
        FtcDashboard.start();
        robot.init(hardwareMap);

        //telemetry = new MultipleTelemetry(telemetry, FtcDashboard.getInstance().getTelemetry());
        SampleMecanumDriveBase drive = new SampleMecanumDriveREV(hardwareMap);

        Trajectory trajectory0 = drive.trajectoryBuilder()
                .back(30)
                .build();

        Trajectory trajectory1 = drive.trajectoryBuilder()
                .reverse()
                .splineTo(new Pose2d(-40, 10, 0))
                .build();

        Trajectory trajectory2 = drive.trajectoryBuilder()
                .reverse()
                .splineTo(new Pose2d(-50, -15, 0))
                .build();

        Trajectory trajectory3 = drive.trajectoryBuilder()
                .splineTo(new Pose2d(50, 15, 0))
                .build();

        Trajectory trajectory4 = drive.trajectoryBuilder()
                .splineTo(new Pose2d(40, -5, 0))
                .build();

        waitForStart();

        /*
        This class works to avoid interfering with the alliance partner's robot, in the possibility
        that their autonomous far outclasses ours.
         */

        sleep(1000);
        robot.autoblueArm.setPosition(0.71);
        sleep(1000);
        robot.autoblueClaw.setPosition(0.8);
        sleep(1000);
        robot.autoblueArm.setPosition(0.4);
        sleep(1000);


        //robot.autoblueClaw.setPosition(0.8); //temporary so it fits under the bridgeS

        drive.followTrajectorySync(trajectory0);

        drive.followTrajectorySync(trajectory1); //splines
        drive.followTrajectorySync(trajectory2);

        //drive.followTrajectorySync(trajectory3);

        sleep(1000);
        robot.autoblueArm.setPosition(0.6);
        sleep(1000);
        robot.autoblueClaw.setPosition(0.3);
        sleep(1000);
        robot.autoblueArm.setPosition(0.25);
        robot.autoblueClaw.setPosition(0.8);
        sleep(1000);

        drive.followTrajectorySync(trajectory3);
        drive.followTrajectorySync(trajectory4);

        robot.autoblueClaw.setPosition(0.3);
        sleep(1000);
        robot.autoblueArm.setPosition(0.71);
        sleep(1000);
        robot.autoblueClaw.setPosition(0.8);
        sleep(1000);
        robot.autoblueArm.setPosition(0.4);
        sleep(1000);

        drive.followTrajectorySync(trajectory1); //splines
        drive.followTrajectorySync(trajectory2);

        sleep(1000);
        robot.autoblueArm.setPosition(0.6);
        sleep(1000);
        robot.autoblueClaw.setPosition(0.3);
        sleep(1000);
        robot.autoblueArm.setPosition(0.4);
        robot.autoblueClaw.setPosition(0.8);
        sleep(1000);

        drive.followTrajectorySync(trajectory3);

/*
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
        */
    }
}