package org.firstinspires.ftc.teamcode;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.roadrunner.mecanum.SampleMecanumDriveBase;
import org.firstinspires.ftc.teamcode.roadrunner.mecanum.SampleMecanumDriveREV;

@Autonomous(name = "test2blockautoforred", group = "Performance")
public class test2blockautoforred extends LinearOpMode{
    Hardware robot = new Hardware();
    EncoderDrive encoderDrive = new EncoderDrive(robot, this, telemetry);
    Base base = new Base(robot);

    @Override
    public void runOpMode() throws InterruptedException {
        FtcDashboard.start(); // -PROPOSITION- Integrate this method into the hardware init function.
        robot.init(hardwareMap);

        //telemetry = new MultipleTelemetry(telemetry, FtcDashboard.getInstance().getTelemetry());
        SampleMecanumDriveBase drive = new SampleMecanumDriveREV(hardwareMap);

        Trajectory trajectorya = drive.trajectoryBuilder()
                .strafeTo(new Vector2d(25, -34.5))
                .build();

        Trajectory trajectory0 = drive.trajectoryBuilder() //goes to plate
                .reverse() //reverse spline to under the bridge
                .splineTo(new Pose2d(-86, -10, 0))
                .build();

        Trajectory trajectorynegative1 = drive.trajectoryBuilder() // goes to second block

                .splineTo(new Pose2d(55, 11, 0)) //spline to under the bridge
                .splineTo(new Pose2d(89, 1, 0)) //spline to second block

                .build();

        Trajectory trajectorynegative2 = drive.trajectoryBuilder() //goes to plate

                .reverse() //spline to under the bridge
                .splineTo(new Pose2d(-34, 11, 0))
                .splineTo(new Pose2d(-78, 0, 0))

                .build();

        Trajectory trajectorynegative3 = drive.trajectoryBuilder()
                .back(12)
                .build();

        Trajectory trajectorynegative4 = drive.trajectoryBuilder() //parks

                .splineTo(new Pose2d(24, 24, Math.toRadians(90)))
                .build();

        waitForStart();

        /*
        This class works to avoid interfering with the alliance partner's robot, in the possibility
        that their autonomous far outclasses ours.
         */



        //drive.followTrajectorySync(trajectory1);

        drive.followTrajectorySync(trajectorya);
        sleep(1000);

//        encoderDrive.encoderDrive(0.5, "Strafe", 30, 3);
//        sleep(1000);
//        encoderDrive.encoderDrive(0.8, "Forward", 22, 4);
//        encoderDrive.encoderDrive(0.5, "Forward", -3, 1);


        //sleep(1000);
        robot.autoredArm.setPosition(0.65);
        sleep(500);
        robot.autoredClaw.setPosition(0.8);
        sleep(500);
        robot.autoredArm.setPosition(0.38);
        sleep(500);

        //robot.autoredClaw.setPosition(0.8); //temporary so it fits under the bridgeS
        drive.followTrajectorySync(trajectory0);

        sleep(500);
        robot.autoredArm.setPosition(0.6);
        sleep(500);
        robot.autoredClaw.setPosition(0.3);
        sleep(500);
        robot.autoredArm.setPosition(0.25);
        robot.autoredClaw.setPosition(0.8);
        sleep(500);

        drive.followTrajectorySync(trajectorynegative1);

        robot.autoredClaw.setPosition(0.3);
        sleep(500);
        robot.autoredArm.setPosition(0.65);
        sleep(500);
        robot.autoredClaw.setPosition(0.8);
        sleep(500);
        robot.autoredArm.setPosition(0.38);
        sleep(500);

        drive.followTrajectorySync(trajectorynegative2);

        sleep(500);
        robot.autoredArm.setPosition(0.6);
        sleep(500);
        robot.autoredClaw.setPosition(0.3);
        sleep(500);
        robot.autoredArm.setPosition(0.38);
        robot.autoredClaw.setPosition(0.8);
        sleep(500);

        //pull stuff hopefully
        drive.followTrajectorySync(trajectorynegative3);
        encoderDrive.encoderDrive(0.8, "Strafe", -4, 2);
        drive.turnSync(Math.toRadians(-110));
        encoderDrive.encoderDrive(0.7, "Forward", 12, 3);
        base.close();
        sleep(500);
        encoderDrive.encoderDrive(0.8, "Forward", -40, 8);
        base.open();
        encoderDrive.encoderDrive(0.5, "Strafe", -32, 5);


        drive.followTrajectorySync(trajectorynegative4);

/*
        //drive back
        encoderDrive.encoderDrive(0.3, "Forward", -6, 10);

        //arm stuff
        sleep(1000);
        robot.autoredArm.setPosition(0.71);
        sleep(1000);
        robot.autoredClaw.setPosition(0.8);
        sleep(1000);
        robot.autoredArm.setPosition(0.25);
        sleep(1000);

        //drive forward
        encoderDrive.encoderDrive(0.3, "Forward", 24, 10);

        //arm stuff
        sleep(1000);
        robot.autoredArm.setPosition(0.6);
        sleep(1000);
        robot.autoredClaw.setPosition(0.3);
        sleep(1000);
        robot.autoredArm.setPosition(0.25);
        sleep(1000);

        //drive back
        encoderDrive.encoderDrive(0.3, "Forward", -18, 10);
        */
    }
}