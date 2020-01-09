package org.firstinspires.ftc.teamcode;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.roadrunner.mecanum.SampleMecanumDriveBase;
import org.firstinspires.ftc.teamcode.roadrunner.mecanum.SampleMecanumDriveREV;

/*
This class is the autonomous for blue that does everything, and presumes the alliance partner robot
immediately heads to park, next to the wall.
 */

@Autonomous(name = "Red All", group = "Performance")
public class RedAll extends LinearOpMode2{
    Hardware robot = new Hardware();
    // EncoderDrive encoderDrive = new EncoderDrive(robot, this, telemetry);
    Base base = new Base(robot);
    Autored autored = new Autored(robot);
    SkystoneReader reader = new SkystoneReader(this, telemetry);

    @Override
    public void runOpMode() throws InterruptedException {

        robot.init(hardwareMap);

        SampleMecanumDriveBase drive = new SampleMecanumDriveREV(hardwareMap);

        int skystoneX;

        waitForStart();

        reader.run();

        if (reader.placement("Red") == 0) {
            skystoneX = 26;
        } else if (reader.placement("Red") == 1) {
            skystoneX = 18;
        } else {
            skystoneX = 10;
        }

        autored.open();

        Trajectory toFirstSkystone = drive.trajectoryBuilder()
                .strafeTo(new Vector2d(skystoneX, -33.5))
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
                .splineTo(new Pose2d(-20, -23, 0))
                .splineTo(new Pose2d(-90, -31, 0))
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

        Trajectory toSecondSkystone = drive.trajectoryBuilder()
//                .forward(80 + skystoneX - 26)
                .splineTo(new Pose2d(-36, -22, 0))
                .splineTo(new Pose2d(skystoneX - 26, -31.5, 0))
                .build();

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

        Trajectory toBaseAgain = drive.trajectoryBuilder()
                .reverse()
                .splineTo(new Pose2d(-36, -23, 0))
                .splineTo(new Pose2d(-80, -31, 0))
                .build();

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
        autored.initialize();

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
                .strafeLeft(10)
                .forward(24)
                .build();

        drive.followTrajectorySync(push);

        base.open();
        sleep(250);

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