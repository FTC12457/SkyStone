package org.firstinspires.ftc.teamcode;

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.roadrunner.mecanum.SampleMecanumDriveBase;
import org.firstinspires.ftc.teamcode.roadrunner.mecanum.SampleMecanumDriveREV;
import org.firstinspires.ftc.teamcode.robot.Autoblue;
import org.firstinspires.ftc.teamcode.robot.Hardware;

/*
This class is the autonomous for blue that does everything, and presumes the alliance partner robot
immediately heads to park, next to the wall.
 */
@Config
@Autonomous(name = "Constable Odo", group = "Performance")
public class odometryTest extends LinearOpMode2{
    Hardware robot = new Hardware();
    // EncoderDrive encoderDrive = new EncoderDrive(robot, this, telemetry);
    Autoblue autoblue = new Autoblue(robot);

    @Override
    public void runOpMode() throws InterruptedException {

        robot.init(hardwareMap);

        SampleMecanumDriveBase drive = new SampleMecanumDriveREV(hardwareMap, robot.leftEncoder, robot.bean);

        waitForStart();

        sleep(1000);
//
//        drive.turnSync(Math.PI * 2);

        Trajectory trajectory0 = drive.trajectoryBuilder()
                .splineTo(new Pose2d(24, 12, 0))
                .build();

        drive.followTrajectorySync(trajectory0);

        drive.update();

        Trajectory trajectory1 = drive.trajectoryBuilder()
                .reverse()
                .splineTo(new Pose2d(0, 0, 0))
                .build();

        drive.followTrajectorySync(trajectory1);
    }
}