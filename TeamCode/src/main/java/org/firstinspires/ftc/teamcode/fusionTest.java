package org.firstinspires.ftc.teamcode;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.EncoderDrive;
import org.firstinspires.ftc.teamcode.Hardware;
import org.firstinspires.ftc.teamcode.roadrunner.mecanum.SampleMecanumDriveBase;
import org.firstinspires.ftc.teamcode.roadrunner.mecanum.SampleMecanumDriveREV;

@Autonomous(name = "Fusion Test", group = "Performance")
public class fusionTest extends LinearOpMode{
    Hardware robot = new Hardware();
    EncoderDrive encoderDrive = new EncoderDrive(robot, this, telemetry);

    @Override
    public void runOpMode() throws InterruptedException {
        FtcDashboard.start(); // -PROPOSITION- Integrate this method into the hardware init function.
        robot.init(hardwareMap);

        //telemetry = new MultipleTelemetry(telemetry, FtcDashboard.getInstance().getTelemetry());
        SampleMecanumDriveBase drive = new SampleMecanumDriveREV(hardwareMap);

        Trajectory trajectory0 = drive.trajectoryBuilder()
                .splineTo(new Pose2d(30, 10, 0))
                .forward(30)
                .back(30)
                .reverse()
                .splineTo(new Pose2d(-30, -10, 0))
                .build();

        waitForStart();

        //robot.autoredClaw.setPosition(0.8); //temporary so it fits under the bridgeS

        drive.followTrajectorySync(trajectory0);

        //drive.followTrajectorySync(trajectory3);

/*
        //drive back
        encoderDrive.encoderDrive(0.3, "Forward", -6, 10);

        //arm stuff
        sleep(1000);
        robot.autoredArm.setPosition(0.71);
        sleep(1000);
        robot.autoredClaw.setPosition(0.8);
        sleep(1000);
        robot.autoredArm.setPosition(0.25);
        sleep(1000);

        //drive forward
        encoderDrive.encoderDrive(0.3, "Forward", 24, 10);

        //arm stuff
        sleep(1000);
        robot.autoredArm.setPosition(0.6);
        sleep(1000);
        robot.autoredClaw.setPosition(0.3);
        sleep(1000);
        robot.autoredArm.setPosition(0.25);
        sleep(1000);

        //drive back
        encoderDrive.encoderDrive(0.3, "Forward", -18, 10);
        */
    }
}