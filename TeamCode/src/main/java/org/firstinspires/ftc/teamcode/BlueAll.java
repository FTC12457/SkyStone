package org.firstinspires.ftc.teamcode;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.roadrunner.mecanum.SampleMecanumDriveBase;
import org.firstinspires.ftc.teamcode.roadrunner.mecanum.SampleMecanumDriveREV;

/*
This class works to avoid interfering with the alliance partner's robot, in the possibility
that their autonomous far outclasses ours.
 */

@Autonomous(name = "Red All", group = "Performance")
public class BlueAll extends LinearOpMode{
    Hardware robot = new Hardware();
    EncoderDrive encoderDrive = new EncoderDrive(robot, this, telemetry);
    Base base = new Base(robot);
    Autoblue autoblue = new Autoblue(robot);

    @Override
    public void runOpMode() throws InterruptedException {
        //FtcDashboard.start(); // -PROPOSITION- Integrate this method into the hardware init function.
        robot.init(hardwareMap);

        SampleMecanumDriveBase drive = new SampleMecanumDriveREV(hardwareMap);

        Trajectory toFirstSkystone = drive.trajectoryBuilder()
                .strafeTo(new Vector2d(-26, -34))
                .build();

        waitForStart();
        drive.followTrajectorySync(toFirstSkystone);

        sleep(250);

        autoblue.lower();
        sleep(250);
        autoblue.close();
        sleep(250);
        autoblue.lift();

        sleep(500);

        drive.update();

        Trajectory toBase = drive.trajectoryBuilder()
                .reverse()
                .splineTo(new Pose2d(20, -26, 0))
                .splineTo(new Pose2d(80, -34, 0))
                .build();

        drive.followTrajectorySync(toBase);

        sleep(250);

        autoblue.lower();
        sleep(250);
        autoblue.open();
        sleep(250);
        autoblue.lift();
        autoblue.close();

        sleep(500);

        drive.update();

        Trajectory toSecondSkystone = drive.trajectoryBuilder()
                .splineTo(new Pose2d(20, -26, 0))
                .splineTo(new Pose2d(0, -34, 0))
                .build();

        drive.followTrajectorySync(toSecondSkystone);

        sleep(250);

        autoblue.lower();
        sleep(250);
        autoblue.close();
        sleep(250);
        autoblue.lift();

        sleep(500);

        drive.update();

        Trajectory toBaseAgain = drive.trajectoryBuilder()
                .reverse()
                .splineTo(new Pose2d(20, -26, 0))
                .splineTo(new Pose2d(80, -34, 0))
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

        sleep(500);

        drive.turnSync(0.5 * Math.PI);

        drive.update();

        Trajectory pull = drive.trajectoryBuilder()
                .back(40)
                .build();

        drive.followTrajectorySync(pull);
    }
}