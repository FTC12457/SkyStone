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
    SkystoneReader reader = new SkystoneReader("Blue", this, telemetry);

    @Override
    public void runOpMode() throws InterruptedException {

        robot.init(hardwareMap);

        SampleMecanumDriveBase drive = new SampleMecanumDriveREV(hardwareMap);

        int skystoneX;

        waitForStart();

        reader.run();
        if (reader.placement("Blue") == 0) {
            skystoneX = -31;
        } else if (reader.placement("Blue") == 1) {
            skystoneX = -23;
        } else {
            skystoneX = -15;
        }

        Trajectory toFirstSkystone = drive.trajectoryBuilder()
                .strafeTo(new Vector2d(skystoneX, -31))
                .build();

        drive.followTrajectorySync(toFirstSkystone);

        sleep(250);

        autoblue.open();
        autoblue.lowergrab();
        sleep(500);
        autoblue.close();
        sleep(250);
        autoblue.lift();

        sleep(500);

        drive.update();

        Trajectory toBase = drive.trajectoryBuilder()
                .splineTo(new Pose2d(25, -25, 0))
                .splineTo(new Pose2d(88, -32, 0))
                .build();

        drive.followTrajectorySync(toBase);

        sleep(250);

        autoblue.lowerplace();
        sleep(250);
        autoblue.open();
        sleep(250);
        autoblue.lift();
        sleep(500);
        autoblue.close();

        drive.update();

        Trajectory toSecondSkystone = drive.trajectoryBuilder()
                .reverse()
                .splineTo(new Pose2d(36, -25, 0))
                .splineTo(new Pose2d(skystoneX + 26, -31, 0))
                .build();

        drive.followTrajectorySync(toSecondSkystone);

        autoblue.open();
        sleep(250);
        autoblue.lowergrab();
        sleep(500);
        autoblue.close();
        sleep(500);
        autoblue.lift();

        sleep(500);

        drive.update();

        Trajectory toBaseAgain = drive.trajectoryBuilder()
                .splineTo(new Pose2d(30, -25, 0))
                .splineTo(new Pose2d(80, -32, 0))
                .build();

        drive.followTrajectorySync(toBaseAgain);

        // drive.followTrajectorySync(toBase); It MIGHT work? There will be an error spike,
        // but in the direction the robot wants to go in anyways. So, maybe replace?

        sleep(250);
        autoblue.lowerplace();
        sleep(250);
        autoblue.open();
        sleep(250);
        autoblue.lift();
        sleep(250);
        autoblue.initialize();

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
                .strafeRight(18)
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
    }
}