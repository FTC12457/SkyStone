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

import java.util.Vector;

/*
This class is the autonomous for red that does everything, and presumes the alliance partner robot
immediately heads to park, next to the wall.
 */

@Autonomous(name = "Red All Exp", group = "Performance")
public class RedAllExp extends LinearOpMode2 {
    Hardware robot = new Hardware();
    // EncoderDrive encoderDrive = new EncoderDrive(robot, this, telemetry);
    Base base = new Base(robot);
    Autored autored = new Autored(robot);
    SkystoneReader reader = new SkystoneReader("Red", this, telemetry);

    @Override
    public void runOpMode() throws InterruptedException {

        robot.init(hardwareMap);
        double skystoneX;
        int position; //0 = near wall, 1 = middle, 2 = far from wall
        double finalBlockX = -22;

        SampleMecanumDriveBase drive = new SampleMecanumDriveREV(hardwareMap);

        waitForStart();

        reader.run();

        if (reader.placement("Red") == 0) {
            skystoneX = -64;
            position = 0;
        } else if (reader.placement("Red") == 1) {
            skystoneX = -56;
            position = 1;
        } else {
            skystoneX = -48;
            finalBlockX = finalBlockX - 8;
            position = 2;
        }
        // Incorporate skystone detection here.

        int displacement1 = 1;
        int displacement2 = 2;

        drive.setPoseEstimate(new Pose2d(-33, -63, Math.PI));

        autored.open();
        autored.lowerplace();

        Trajectory toFirstSkystone = drive.trajectoryBuilder()
                .strafeTo(new Vector2d(skystoneX, -30))
                .build();

        drive.followTrajectorySync(toFirstSkystone);

        sleep(250);

        autored.lowergrab();
        autored.close();
        sleep(250);
        autored.lift();

        sleep(250);

        drive.update();

        Trajectory toBase = drive.trajectoryBuilder()
                .reverse()
                .splineTo(new Pose2d(0, -43, Math.PI))
                .splineTo(new Pose2d(64, -29, Math.PI))
                .build();

        drive.followTrajectorySync(toBase);

        sleep(250);

        autored.lowerplace();
        sleep(250);
        autored.open();
        autored.lift();
        sleep(250);
        autored.close();
        autored.retract();

        drive.update();

        Trajectory toSecondSkystone = drive.trajectoryBuilder()
                .splineTo(new Pose2d(0, -43, Math.PI))
                .addMarker(() -> {autored.lowerplace(); autored.open(); return null;})
                .strafeTo(new Vector2d(skystoneX + 24, -30 - displacement1 - 3))
                //.splineTo(new Pose2d(skystoneX + 26, -30 - displacement1, Math.PI))
                .build();

        drive.followTrajectorySync(toSecondSkystone);

        sleep(250);
        autored.lowergrab();
        autored.close();
        sleep(250);
        autored.lift();

        sleep(250);

        drive.update();

        Trajectory toBaseAgain = drive.trajectoryBuilder()
                .reverse()
                .strafeTo(new Vector2d(0, -43))
                //.splineTo(new Pose2d(0, -43, Math.PI))
                .splineTo(new Pose2d(56, -31 - displacement1, Math.PI))
                .build();

        drive.followTrajectorySync(toBaseAgain);

        sleep(250);
        autored.lowerplace();
        sleep(250);
        autored.open();
        autored.lift();
        sleep(250);
        autored.retract();

        drive.update();

        Trajectory toThirdSkystone;

        /*
        toThirdSkystone = drive.trajectoryBuilder()
                .splineTo(new Pose2d(0, -43, Math.PI))
                .addMarker(() -> {autored.deploy(); return null;})
                .splineTo(new Pose2d(-17, -30 - displacement2, Math.PI))
                .build();
        */
        toThirdSkystone = drive.trajectoryBuilder()
                .splineTo(new Pose2d(0, -43, Math.PI))
                .addMarker(() -> {autored.lowerplace(); autored.open(); return null;})
                .strafeTo(new Vector2d(finalBlockX, -30 - displacement2))
                .build();

        drive.followTrajectorySync(toThirdSkystone);

        autored.lowergrab();
        autored.close();
        sleep(250);
        autored.lift();

        sleep(500);

        drive.update();

        Trajectory toBaseAgainAgain = drive.trajectoryBuilder()
                .reverse()
                .strafeTo(new Vector2d(0, -43))
                //.splineTo(new Pose2d(0, -43, Math.PI))
                .splineTo(new Pose2d(48, -31 - displacement2, Math.PI))
                .build();

        drive.followTrajectorySync(toBaseAgainAgain);

        drive.update();

        autored.lowerplace();
        sleep(250);
        autored.open();
        autored.lift();
        sleep(250);
        autored.retract();

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
                .splineTo(new Pose2d(32, -50, 0))
                .build();

        drive.followTrajectorySync(pull);

        drive.update();

        Trajectory push = drive.trajectoryBuilder()
                .strafeLeft(6)
                .forward(20)
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
    }
}