package org.firstinspires.ftc.teamcode.performance;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.LinearOpMode2;
import org.firstinspires.ftc.teamcode.SkystoneReader;
import org.firstinspires.ftc.teamcode.roadrunner.mecanum.SampleMecanumDriveBase;
import org.firstinspires.ftc.teamcode.roadrunner.mecanum.SampleMecanumDriveREV;
import org.firstinspires.ftc.teamcode.robot.Autored;
import org.firstinspires.ftc.teamcode.robot.Base;
import org.firstinspires.ftc.teamcode.robot.Hardware;

/*
This class is the autonomous for blue that does everything, and presumes the alliance partner robot
immediately heads to park, next to the wall.
 */

@Autonomous(name = "Red Two Skystones", group = "Performance")
public class Red2Sky extends LinearOpMode2 {
    Hardware robot = new Hardware();
    // EncoderDrive encoderDrive = new EncoderDrive(robot, this, telemetry);
    Base base = new Base(robot);
    Autored autored = new Autored(robot);
    SkystoneReader reader = new SkystoneReader("Red", this, telemetry);

    @Override
    public void runOpMode() throws InterruptedException {

        robot.init(hardwareMap);

        // The SampleMecanumDriveBase has been altered to use odometry.
        // To revert changes, delete the two additional parameters.

        SampleMecanumDriveBase drive = new SampleMecanumDriveREV(hardwareMap, robot.leftEncoder, robot.bean);

        int skystoneX;

        waitForStart();


        reader.run();

        if (reader.placement("Red") == 0) {
            skystoneX = 28;
        } else if (reader.placement("Red") == 1) {
            skystoneX = 20;
        } else {
            skystoneX = 12;
        }

        autored.open();

        Trajectory toFirstSkystone = drive.trajectoryBuilder()
                .strafeTo(new Vector2d(skystoneX, -32.25))
                .build();

        drive.followTrajectorySync(toFirstSkystone);

        sleep(250);

        autored.lowergrab();
        sleep(500);
        autored.close();
        sleep(250);
        autored.lift();

        sleep(500);

        drive.update();

        Trajectory toBase = drive.trajectoryBuilder()
                .reverse()
                .splineTo(new Pose2d(-20, -27, 0))
                .splineTo(new Pose2d(-90, -33, 0))
                .build();

        drive.followTrajectorySync(toBase);

        sleep(250);

        autored.lowerplace();
        sleep(250);
        autored.open();
        sleep(250);
        autored.lift();
        sleep(500);
        autored.close();

        drive.update();

        Trajectory toSecondSkystone;

        if (reader.placement("Red") == 2) {
            toSecondSkystone = drive.trajectoryBuilder()
//                .forward(80 + skystoneX - 26)
                    .splineTo(new Pose2d(-36, -27, 0))
                    //.splineTo(new Pose2d(-30, -25, 0))
                    //.strafeTo(new Vector2d(skystoneX - 26, -32))
                    .splineTo(new Pose2d(skystoneX - 26, -30.5))
                    .build();
        } else if (reader.placement("Red") == 1) {
            toSecondSkystone = drive.trajectoryBuilder()
//                .forward(80 + skystoneX - 26)
                    .splineTo(new Pose2d(-36, -27, 0))
                    .splineTo(new Pose2d(skystoneX - 27, -30.5, 0))
                    .build();
        } else {
            toSecondSkystone = drive.trajectoryBuilder()
//                .forward(80 + skystoneX - 26)
                    .splineTo(new Pose2d(-36, -27, 0))
                    .splineTo(new Pose2d(skystoneX - 26, -30.25 , 0))
                    .build();
        }

        drive.followTrajectorySync(toSecondSkystone);

        autored.open();
        sleep(250);
        autored.lowergrab();
        sleep(500);
        autored.close();
        sleep(250);
        autored.lift();

        sleep(500);

        drive.update();

        Trajectory toBaseAgain;

        if (skystoneX == 12) {
            toBaseAgain = drive.trajectoryBuilder()
                    .strafeTo(new Vector2d(-20, -25))
                    .reverse()
                    .splineTo(new Pose2d(-36, -26, 0))
                    .splineTo(new Pose2d(-80, -33, 0))
                    .build();
        } else {
            toBaseAgain = drive.trajectoryBuilder()
                    .reverse()
                    .splineTo(new Pose2d(-36, -27, 0))
                    .splineTo(new Pose2d(-80, -33, 0))
                    .build();
        }

        drive.followTrajectorySync(toBaseAgain);

        // drive.followTrajectorySync(toBase); It MIGHT work? There will be an error spike,
        // but in the direction the robot wants to go in anyways. So, maybe replace?

        sleep(250);
        autored.lowerplace();
        sleep(250);
        autored.open();
        sleep(250);
        autored.lift();
        sleep(250);
        autored.retract();

        drive.update();

        drive.turnSync(-0.5 * Math.PI);

        drive.update();

        Trajectory ram = drive.trajectoryBuilder()
                .forward(8)
                .build();

        drive.followTrajectorySync(ram);

        sleep(250);
        base.close();

        sleep(250);

        drive.update();

        sleep(250);
        base.close();

        sleep(250);

        Trajectory pull = drive.trajectoryBuilder()
                .reverse()
                .splineTo(new Pose2d(-60, -10, Math.PI))
                .build();

        drive.followTrajectorySync(pull);

        drive.update();

        Trajectory push = drive.trajectoryBuilder()
                .strafeLeft(6)
                .forward(20)
                //.strafeTo(new Vector2d(-80, -24))
                .build();

        base.open();
        drive.followTrajectorySync(push);

        drive.update();

        Trajectory park = drive.trajectoryBuilder()
                .back(15)
                .build();

        robot.bean.setPower(-1);
        drive.followTrajectorySync(park);
        robot.bean.setPower(0);

//        Trajectory pull = drive.trajectoryBuilder()
//                .back(40)
//                .build();
//
//        drive.followTrajectorySync(pull);
//
//        base.open();
//        sleep(250);
//
//        drive.update();
//
//        Trajectory park = drive.trajectoryBuilder()
//                .strafeLeft(36)
//                .forward(24)
//                .strafeLeft(12)
//                .build();
//
//        drive.followTrajectorySync(park);
    }
}