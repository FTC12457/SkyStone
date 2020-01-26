package org.firstinspires.ftc.teamcode;

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.roadrunner.mecanum.SampleMecanumDriveBase;
import org.firstinspires.ftc.teamcode.roadrunner.mecanum.SampleMecanumDriveREV;
import org.firstinspires.ftc.teamcode.robot.Autoblue;
import org.firstinspires.ftc.teamcode.robot.Hardware;

import java.util.Vector;

/*
This class is the autonomous for blue that does everything, and presumes the alliance partner robot
immediately heads to park, next to the wall.
 */
@Config
@Autonomous(name = "testyOdomtoeyr", group = "Performance")
public class testyOdeomtrry extends LinearOpMode2{
    Hardware robot = new Hardware();
    // EncoderDrive encoderDrive = new EncoderDrive(robot, this, telemetry);
    Autoblue autoblue = new Autoblue(robot);

    @Override
    public void runOpMode() throws InterruptedException {

        robot.init(hardwareMap);

        SampleMecanumDriveBase drive = new SampleMecanumDriveREV(hardwareMap, robot.leftEncoder, robot.bean, robot.arm);

        waitForStart();

        drive.setPoseEstimate(new Pose2d(0, 0, 0));

        sleep(1000);

        Trajectory trajectory1 = drive.trajectoryBuilder()
                .lineTo(new Vector2d(24, 0))
                .build();

        drive.followTrajectorySync(trajectory1);

        sleep(5000);

        drive.update();
        //drive.updatePoseEstimate();

        Trajectory trajectyr2 = drive.trajectoryBuilder()
                .reverse()
                .lineTo(new Vector2d(0, 0))
                .build();

        drive.followTrajectorySync(trajectyr2);

    }
}