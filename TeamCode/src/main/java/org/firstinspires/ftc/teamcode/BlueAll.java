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

@Autonomous(name = "Blue All", group = "Performance")
public class BlueAll extends LinearOpMode2{
    Hardware robot = new Hardware();
    // EncoderDrive encoderDrive = new EncoderDrive(robot, this, telemetry);
    Base base = new Base(robot);
    Autoblue autoblue = new Autoblue(robot);
    SkystoneReader reader = new SkystoneReader(this, telemetry);

    @Override
    public void runOpMode() throws InterruptedException {

        robot.init(hardwareMap);

        SampleMecanumDriveBase drive = new SampleMecanumDriveREV(hardwareMap);

        int skystoneX;

        waitForStart();

        reader.run();
        if (reader.placement("Blue") == 0) {
            skystoneX = -9;
        } else if (reader.placement("Blue") == 1) {
            skystoneX = -24;
        } else {
            skystoneX = -32;
        }

        autoblue.open();

        Trajectory toFirstSkystone = drive.trajectoryBuilder()
                .strafeTo(new Vector2d(skystoneX, -33))
                .build();

        drive.followTrajectorySync(toFirstSkystone);

        sleep(250);

        autoblue.lower();
        sleep(500);
        autoblue.close();
        sleep(250);
        autoblue.lift();

        sleep(500);

        drive.update();

        Trajectory toBase = drive.trajectoryBuilder()
                .splineTo(new Pose2d(20, -24, 0))
                .splineTo(new Pose2d(90, -30, 0))
                .build();

        drive.followTrajectorySync(toBase);

        sleep(250);

        autoblue.lower();
        sleep(250);
        autoblue.open();
        sleep(250);
        autoblue.lift();
        sleep(500);
        autoblue.close();

        drive.update();

        Trajectory toSecondSkystone = drive.trajectoryBuilder()
                .reverse()
                .splineTo(new Pose2d(36, -24, 0))
                .splineTo(new Pose2d(skystoneX + 28, -30, 0))
                .build();

        drive.followTrajectorySync(toSecondSkystone);

        autoblue.open();
        sleep(250);
        autoblue.lower();
        sleep(500);
        autoblue.close();
        sleep(250);
        autoblue.lift();

        sleep(500);

        drive.update();

        Trajectory toBaseAgain = drive.trajectoryBuilder()
                .splineTo(new Pose2d(36, -24, 0))
                .splineTo(new Pose2d(80, -30, 0))
                .build();

        drive.followTrajectorySync(toBaseAgain);

        // drive.followTrajectorySync(toBase); It MIGHT work? There will be an error spike,
        // but in the direction the robot wants to go in anyways. So, maybe replace?

        sleep(250);
        autoblue.lower();
        sleep(250);
        autoblue.open();
        sleep(250);
        autoblue.lift();
        sleep(250);
        autoblue.close();

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
                .splineTo(new Pose2d(60, -10, 0))
                .build();

        drive.followTrajectorySync(pull);

        drive.update();

        Trajectory push = drive.trajectoryBuilder()
                .strafeRight(11)
                .forward(24)
                .build();

        drive.followTrajectorySync(push);

        base.open();
        sleep(250);

        drive.update();

        Trajectory park = drive.trajectoryBuilder()
                .back(40)
                .build();

        drive.followTrajectorySync(park);
    }
}