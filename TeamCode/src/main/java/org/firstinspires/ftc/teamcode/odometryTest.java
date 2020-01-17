package org.firstinspires.ftc.teamcode;

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.roadrunner.localizer.BadLocalizer;
import org.firstinspires.ftc.teamcode.roadrunner.localizer.StandardTrackingWheelLocalizer;
import org.firstinspires.ftc.teamcode.roadrunner.localizer.TwoLocalizer;
import org.firstinspires.ftc.teamcode.roadrunner.mecanum.SampleMecanumDriveBase;
import org.firstinspires.ftc.teamcode.roadrunner.mecanum.SampleMecanumDriveREV;
import org.firstinspires.ftc.teamcode.robot.Autoblue;
import org.firstinspires.ftc.teamcode.robot.Hardware;

/*
This class is the autonomous for blue that does everything, and presumes the alliance partner robot
immediately heads to park, next to the wall.
 */
@Config
@Autonomous(name = "Constable Odo", group = "Performance")
public class odometryTest extends LinearOpMode2{
    Hardware robot = new Hardware();
    // EncoderDrive encoderDrive = new EncoderDrive(robot, this, telemetry);
    Autoblue autoblue = new Autoblue(robot);

    @Override
    public void runOpMode() throws InterruptedException {

        robot.init(hardwareMap);

        SampleMecanumDriveBase drive = new SampleMecanumDriveREV(hardwareMap);
        TwoLocalizer localizer = new TwoLocalizer(hardwareMap, robot.leftEncoder, robot.bean, robot.imu);

        drive.setLocalizer(localizer);

        waitForStart();

        sleep(1000);

        Trajectory toFirstSkystone = drive.trajectoryBuilder()
                .forward(60)
                .build();

        drive.followTrajectorySync(toFirstSkystone);
    }
}