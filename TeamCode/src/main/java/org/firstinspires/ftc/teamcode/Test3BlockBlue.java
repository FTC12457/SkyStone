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

@Autonomous(name = "test3blockblue", group = "Performance")
public class Test3BlockBlue extends LinearOpMode2{
    Hardware robot = new Hardware();
    // EncoderDrive encoderDrive = new EncoderDrive(robot, this, telemetry);
    Base base = new Base(robot);
    Autoblue autoblue = new Autoblue(robot);
    //SkystoneReader reader = new SkystoneReader("Blue", this, telemetry);
    SkystoneReaderInit initReader = new SkystoneReaderInit("Blue", this, telemetry);

    @Override
    public void runOpMode() throws InterruptedException {

        robot.init(hardwareMap);

        SampleMecanumDriveBase drive = new SampleMecanumDriveREV(hardwareMap);

        int skystoneX;

        initReader.run();
        //waitForStart();

        //reader.run();

        //int skystoneposition = reader.placement("Blue");
        int skystoneposition = initReader.placement("Blue");

        if (skystoneposition == 2) {
            skystoneX = -13;
        } else if (skystoneposition == 1) {
            skystoneX = -21;
        } else if (skystoneposition == 0) {
            skystoneX = -30;
        } else {
            skystoneX = -30;
        }

        Trajectory toFirstSkystone = drive.trajectoryBuilder()
                .strafeTo(new Vector2d(skystoneX, -31.7))
                .build();

        drive.followTrajectorySync(toFirstSkystone);

        //sleep(250);

        autoblue.open();
        autoblue.lowergrab();
        sleep(500);
        autoblue.close();
        sleep(250);
        autoblue.lift();

        //sleep(500);

        drive.update();

        Trajectory toBase = drive.trajectoryBuilder()
                .splineTo(new Pose2d(28, -27, 0))
                .splineTo(new Pose2d(91, -32.5, 0))
                .build();

        drive.followTrajectorySync(toBase);

        //sleep(250);

        autoblue.lowerplace();
        sleep(250);
        autoblue.open();
        //sleep(250);
        autoblue.lift();
        sleep(500);
        autoblue.close(); //ideally we should have the open position also fit inside of the sizing cube

        drive.update();

        Trajectory toSecondSkystone;

        if (skystoneposition == 2) {
            toSecondSkystone = drive.trajectoryBuilder()
                    .reverse()
                    .splineTo(new Pose2d(39, -27, 0))
                    .splineTo(new Pose2d(skystoneX + 28, -31.7, 0))
                    .build();
        } else if (skystoneposition == 1) {
            toSecondSkystone = drive.trajectoryBuilder()
                    .reverse()
                    .splineTo(new Pose2d(39, -27.5, 0))
                    .splineTo(new Pose2d(skystoneX + 28, -31.7, 0))
                    .build();
        } else {
            toSecondSkystone = drive.trajectoryBuilder()
                    .reverse()
                    .splineTo(new Pose2d(39, -27, 0))
                    .splineTo(new Pose2d(skystoneX + 28, -31.7, 0))
                    .build();
        }

        drive.followTrajectorySync(toSecondSkystone);

        autoblue.open();
        //sleep(250);
        autoblue.lowergrab();
        sleep(500);
        autoblue.close();
        sleep(250);
        autoblue.lift();

        //sleep(500);

        drive.update();

        Trajectory toBaseAgain;

        if (skystoneposition == 2) {
            toBaseAgain = drive.trajectoryBuilder()
                    .strafeTo(new Vector2d(23, -27))
                    .splineTo(new Pose2d(33, -27, 0))
                    .splineTo(new Pose2d(83, -32.5, 0))
                    .build();
        } else {
            toBaseAgain = drive.trajectoryBuilder()
                    .splineTo(new Pose2d(33, -27, 0))
                    .splineTo(new Pose2d(83, -32.5, 0))
                    .build();
        }

        drive.followTrajectorySync(toBaseAgain);

        // drive.followTrajectorySync(toBase); It MIGHT work? There will be an error spike,
        // but in the direction the robot wants to go in anyways. So, maybe replace?

        //sleep(250);
        autoblue.lowerplace();
        sleep(250);
        autoblue.open();
        //sleep(250);
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

        //sleep(250);
        //base.close();

        //sleep(250);

        drive.update();

        base.close();
        sleep(500);

        Trajectory pull = drive.trajectoryBuilder()
                .reverse()
                .splineTo(new Pose2d(63, -10, 0))
                .build();

        drive.followTrajectorySync(pull);

        drive.update();

        Trajectory push = drive.trajectoryBuilder()
                //.strafeTo(new Vector2d(73, -30))
                .strafeRight(18)
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