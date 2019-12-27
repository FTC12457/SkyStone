package org.firstinspires.ftc.teamcode;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.roadrunner.mecanum.SampleMecanumDriveBase;
import org.firstinspires.ftc.teamcode.roadrunner.mecanum.SampleMecanumDriveREV;

@Autonomous(name = "test2blockautoforred", group = "Performance")
public class test2blockautoforred extends LinearOpMode{
    Hardware robot = new Hardware();
    EncoderDrive encoderDrive = new EncoderDrive(robot, this, telemetry);
    Base base = new Base(robot);

    @Override
    public void runOpMode() throws InterruptedException {
        FtcDashboard.start(); // -PROPOSITION- Integrate this method into the hardware init function.
        robot.init(hardwareMap);

        //telemetry = new MultipleTelemetry(telemetry, FtcDashboard.getInstance().getTelemetry());
        SampleMecanumDriveBase drive = new SampleMecanumDriveREV(hardwareMap);

        Trajectory trajectory0 = drive.trajectoryBuilder() //goes to plate

                .back(30) //back 30

                .reverse() //reverse spline to under the bridge
                .splineTo(new Pose2d(-60, 10, 0))
                .splineTo(new Pose2d(-115, -1, 0))

                .build();

        Trajectory trajectorynegative1 = drive.trajectoryBuilder() // goes to second block

                .splineTo(new Pose2d(55, 11, 0)) //spline to under the bridge
                .splineTo(new Pose2d(89, 1, 0)) //spline to second block

                .build();

        Trajectory trajectorynegative2 = drive.trajectoryBuilder() //goes to plate

                .reverse() //spline to under the bridge
                .splineTo(new Pose2d(-34, 10, 0))
                .splineTo(new Pose2d(-78, 0, 0))

                .build();

        Trajectory trajectorynegative3 = drive.trajectoryBuilder() //parks

                .splineTo(new Pose2d(44, 11, 0))
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
                .splineTo(new Pose2d(-60, 15, 0))
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

        //encoderDrive.encoderDrive(0.4, "Forward", 27, 10);
        //encoderDrive.encoderDrive(0.4, "Strafe", 27, 10);

        //sleep(1000);
        robot.autoblueArm.setPosition(0.65);
        sleep(500);
        robot.autoblueClaw.setPosition(0.8);
        sleep(500);
        robot.autoblueArm.setPosition(0.38);
        sleep(500);


        //robot.autoblueClaw.setPosition(0.8); //temporary so it fits under the bridgeS

        drive.followTrajectorySync(trajectory0);


        sleep(500);
        robot.autoblueArm.setPosition(0.6);
        sleep(500);
        robot.autoblueClaw.setPosition(0.3);
        sleep(500);
        robot.autoblueArm.setPosition(0.25);
        robot.autoblueClaw.setPosition(0.8);
        sleep(500);


        drive.followTrajectorySync(trajectorynegative1);


        robot.autoblueClaw.setPosition(0.3);
        sleep(500);
        robot.autoblueArm.setPosition(0.65);
        sleep(500);
        robot.autoblueClaw.setPosition(0.8);
        sleep(500);
        robot.autoblueArm.setPosition(0.38);
        sleep(500);



        drive.followTrajectorySync(trajectorynegative2);


        sleep(500);
        robot.autoblueArm.setPosition(0.6);
        sleep(500);
        robot.autoblueClaw.setPosition(0.3);
        sleep(500);
        robot.autoblueArm.setPosition(0.38);
        robot.autoblueClaw.setPosition(0.8);
        sleep(500);


        drive.followTrajectorySync(trajectorynegative3);

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