package org.firstinspires.ftc.teamcode;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.roadrunner.mecanum.SampleMecanumDriveBase;
import org.firstinspires.ftc.teamcode.roadrunner.mecanum.SampleMecanumDriveREV;

@Autonomous(name = "sideArmTest", group = "Performance")
public class test2blockautoforred extends LinearOpMode{
    Hardware robot = new Hardware();

    @Override
    public void runOpMode() throws InterruptedException {
        FtcDashboard.start(); // -PROPOSITION- Integrate this method into the hardware init function.
        robot.init(hardwareMap);

        //telemetry = new MultipleTelemetry(telemetry, FtcDashboard.getInstance().getTelemetry());
        SampleMecanumDriveBase drive = new SampleMecanumDriveREV(hardwareMap);

        Trajectory trajectory0 = drive.trajectoryBuilder()

                .back(30) //back 30

                .reverse() //reverse spline to under the bridge
                .splineTo(new Pose2d(-40, 10, 0))

                .reverse() //reverse spline to the plate
                .splineTo(new Pose2d(-50, -15, 0))

                .build();

        Trajectory trajectorynegative1 = drive.trajectoryBuilder()

                .splineTo(new Pose2d(50, 15, 0)) //spline to under the bridge

                .splineTo(new Pose2d(40, -5, 0)) //spline to second block

                .build();

        Trajectory trajectorynegative2 = drive.trajectoryBuilder()

                .reverse() //spline to under the bridge
                .splineTo(new Pose2d(-40, 10, 0))

                .reverse() //reverse spline to the plate
                .splineTo(new Pose2d(-50, -15, 0))

                .build();

        // not being used anymore, but keep these as references
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

        Trajectory trajectory5 = drive.trajectoryBuilder()
                .back(30)
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

        sleep(1000);
        robot.autoblueArm.setPosition(0.6);
        sleep(1000);
        robot.autoblueClaw.setPosition(0.3);
        sleep(1000);
        robot.autoblueArm.setPosition(0.25);
        robot.autoblueClaw.setPosition(0.8);
        sleep(1000);

        drive.followTrajectorySync(trajectorynegative1);

        robot.autoblueClaw.setPosition(0.3);
        sleep(1000);
        robot.autoblueArm.setPosition(0.71);
        sleep(1000);
        robot.autoblueClaw.setPosition(0.8);
        sleep(1000);
        robot.autoblueArm.setPosition(0.4);
        sleep(1000);

        drive.followTrajectorySync(trajectorynegative2);

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