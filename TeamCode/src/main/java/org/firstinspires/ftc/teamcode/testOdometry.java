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

/*
This class is the autonomous for blue that does everything, and presumes the alliance partner robot
immediately heads to park, next to the wall.
 */
@Config
@Autonomous(name = "testThing (don't run)", group = "Performance")
public class testOdometry extends LinearOpMode2{
    Hardware robot = new Hardware();
    // EncoderDrive encoderDrive = new EncoderDrive(robot, this, telemetry);
    Autoblue autoblue = new Autoblue(robot);

    @Override
    public void runOpMode() throws InterruptedException {

        robot.init(hardwareMap);

        SampleMecanumDriveBase drive = new SampleMecanumDriveREV(hardwareMap, robot.leftEncoder, robot.bean, robot.arm);

        waitForStart();

        sleep(1000);

        int bob = robot.bean.getCurrentPosition();

        while (Math.abs(robot.bean.getCurrentPosition()-bob) < 1440) {

            robot.backLeftDrive.setPower(0.4);
            robot.backRightDrive.setPower(-0.4);
            robot.frontLeftDrive.setPower(-0.4);
            robot.frontRightDrive.setPower(0.4);

        }

        robot.backRightDrive.setPower(0);
        robot.backLeftDrive.setPower(0);
        robot.frontRightDrive.setPower(0);
        robot.frontLeftDrive.setPower(0);

    }
}