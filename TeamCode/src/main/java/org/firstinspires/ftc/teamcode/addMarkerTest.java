package org.firstinspires.ftc.teamcode;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.roadrunner.localizer.StandardTrackingWheelLocalizer;
import org.firstinspires.ftc.teamcode.roadrunner.mecanum.SampleMecanumDriveBase;
import org.firstinspires.ftc.teamcode.roadrunner.mecanum.SampleMecanumDriveREV;
import org.firstinspires.ftc.teamcode.robot.Autoblue;
import org.firstinspires.ftc.teamcode.robot.Base;
import org.firstinspires.ftc.teamcode.robot.Claw;
import org.firstinspires.ftc.teamcode.robot.Hardware;
import org.slf4j.Marker;

/*
This class is the autonomous for blue that does everything, and presumes the alliance partner robot
immediately heads to park, next to the wall.
 */

@Autonomous(name = "Add Marker", group = "Performance")
public class addMarkerTest extends LinearOpMode2{
    Hardware robot = new Hardware();
    // EncoderDrive encoderDrive = new EncoderDrive(robot, this, telemetry);
    Autoblue autoblue = new Autoblue(robot);

    @Override
    public void runOpMode() throws InterruptedException {

        robot.init(hardwareMap);

        SampleMecanumDriveBase drive = new SampleMecanumDriveREV(hardwareMap);

        waitForStart();

        sleep(1000);

        Trajectory toFirstSkystone = drive.trajectoryBuilder()
                .addMarker(new Vector2d(20, -20), () -> {autoblue.open(); return null;})
                .strafeTo(new Vector2d(30, -30))
                .strafeTo(new Vector2d(60, -30))
                .build();

        drive.followTrajectorySync(toFirstSkystone);
    }
}