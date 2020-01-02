package org.firstinspires.ftc.teamcode;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.roadrunner.mecanum.SampleMecanumDriveBase;
import org.firstinspires.ftc.teamcode.roadrunner.mecanum.SampleMecanumDriveREV;

import com.acmerobotics.dashboard.FtcDashboard;

@Autonomous(name = "Red All", group = "Performance")
public class RedAll extends LinearOpMode{
    Hardware robot = new Hardware();
    EncoderDrive encoderDrive = new EncoderDrive(robot, this, telemetry);
    Base base = new Base(robot);

    @Override
    public void runOpMode() throws InterruptedException {
        //FtcDashboard.start(); // -PROPOSITION- Integrate this method into the hardware init function.
        robot.init(hardwareMap);

        SampleMecanumDriveBase drive = new SampleMecanumDriveREV(hardwareMap);

        Trajectory toFirstSkystone = drive.trajectoryBuilder()
                .strafeTo(new Vector2d(26, -34))
                .build();

        Trajectory toBase = drive.trajectoryBuilder()
                .splineTo(new Pose2d(-20, 4, 0))
                .build();

        waitForStart();

        /*
        This class works to avoid interfering with the alliance partner's robot, in the possibility
        that their autonomous far outclasses ours.
         */

        drive.followTrajectorySync(toFirstSkystone);
//        sleep(1000);
//        robot.autoredArm.setPosition(0.65);
//        sleep(500);
//        robot.autoredClaw.setPosition(0.8);
//        sleep(500);
//        robot.autoredArm.setPosition(0.38);
//        sleep(500);

        //robot.autoredClaw.setPosition(0.8); //temporary so it fits under the bridgeS
        drive.followTrajectorySync(toBase);

//        sleep(500);
//        robot.autoredArm.setPosition(0.6);
//        sleep(500);
//        robot.autoredClaw.setPosition(0.3);
//        sleep(500);
//        robot.autoredArm.setPosition(0.25);
//        robot.autoredClaw.setPosition(0.8);
//        sleep(500);
    }
}