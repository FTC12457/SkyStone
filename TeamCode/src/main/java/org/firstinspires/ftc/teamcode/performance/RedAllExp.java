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

        SampleMecanumDriveBase drive = new SampleMecanumDriveREV(hardwareMap);

        waitForStart();

        reader.run();

        // Incorporate skystone detection here.

        int skystoneX = -64;

        drive.setPoseEstimate(new Pose2d(-36, -64, Math.PI));

        autored.open();
        autored.lowerplace();

        Trajectory toFirstSkystone = drive.trajectoryBuilder()
                .strafeTo(new Vector2d(skystoneX, -32))
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
                .splineTo(new Pose2d(0, -38, Math.PI))
                .splineTo(new Pose2d(64, -32, Math.PI))
                .build();

        drive.followTrajectorySync(toBase);

        sleep(250);

        autored.lowerplace();
        sleep(250);
        autored.open();
        autored.lift();
        sleep(250);
        autored.close();

        drive.update();

        Trajectory toSecondSkystone = drive.trajectoryBuilder()
                .splineTo(new Pose2d(0, -38, Math.PI))
                .addMarker(() -> {autored.lowerplace(); autored.open(); return null;})
                .splineTo(new Pose2d(skystoneX + 26, -32, Math.PI))
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
                .splineTo(new Pose2d(0, -38, Math.PI))
                .splineTo(new Pose2d(48, -32, Math.PI))
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

        toThirdSkystone = drive.trajectoryBuilder()
                .splineTo(new Pose2d(0, -38, Math.PI))
                .addMarker(() -> {autored.deploy(); return null;})
                .splineTo(new Pose2d(-56, -32, Math.PI))
                .build();

        drive.followTrajectorySync(toThirdSkystone);

        autored.close();
        sleep(250);
        autored.lift();

        sleep(500);

        drive.update();

        Trajectory toBaseAgainAgain = drive.trajectoryBuilder()
                .reverse()
                .splineTo(new Pose2d(0, -38, Math.PI))
                .splineTo(new Pose2d(40, -32, Math.PI))
                .build();

        drive.followTrajectorySync(toBaseAgainAgain);

        drive.update();

        drive.turnSync(0.5 * Math.PI);

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
                .splineTo(new Pose2d(32, -64, 0))
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