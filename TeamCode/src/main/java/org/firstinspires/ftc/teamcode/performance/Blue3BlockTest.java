package org.firstinspires.ftc.teamcode.performance;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.LinearOpMode2;
import org.firstinspires.ftc.teamcode.SkystoneReaderInit;
import org.firstinspires.ftc.teamcode.roadrunner.mecanum.SampleMecanumDriveBase;
import org.firstinspires.ftc.teamcode.roadrunner.mecanum.SampleMecanumDriveREV;
import org.firstinspires.ftc.teamcode.robot.Autored;
import org.firstinspires.ftc.teamcode.robot.Base;
import org.firstinspires.ftc.teamcode.robot.Hardware;

/*
This class is the autonomous for red that does everything, and presumes the alliance partner robot
immediately heads to park, next to the wall.
 */

@Autonomous(name = "Blue3BlockTest", group = "Performance")
public class Blue3BlockTest extends LinearOpMode2 {
    String teamColor = "Blue";

    // y value between initial position and the Base
    final double yInitToBase = 29;

    // y value between initial position and the arc peak of the spline
    final double yInitToSplineArc = 38;

    // y value between initial position and the line of stones
    final double yInitToStone = 29;

    // default x value of the third stone
    // Todo: According to math calculation, this should be -24
    double xThirdBlock = -20;

    Hardware robot = new Hardware();
    // EncoderDrive encoderDrive = new EncoderDrive(robot, this, telemetry);
    Base base = new Base(robot);
    Autored autored = new Autored(robot);
    SkystoneReaderInit initReader = new SkystoneReaderInit(teamColor, this, telemetry);
    @Override
    public void runOpMode() throws InterruptedException {

        robot.init(hardwareMap);
        double skystoneX;
        int position; //0 = near wall, 1 = middle, 2 = far from wall

        // should use SampleMecanumDriveREVOptimized or not?
        SampleMecanumDriveBase drive = new SampleMecanumDriveREV(hardwareMap);

        initReader.run();
        waitForStart();
        position = initReader.placement();

        position = 0;

        if (position == 0) {
            skystoneX = -64;
        } else if (position == 1) {
            skystoneX = -56;
        } else {
            skystoneX = -48;
            xThirdBlock = xThirdBlock - 8;
        }
        // Incorporate skystone detection here.

        // set the position of the initial place
        drive.setPoseEstimate(new Pose2d(-33, 63, Math.PI));

        // 0. Open red claw, lower claw
        autored.open();
        autored.lowerplace();

        // 1. Move to the first stone.
        Trajectory toFirstSkystone = drive.trajectoryBuilder()
                .strafeTo(new Vector2d(skystoneX, yInitToStone))
                .build();
        drive.followTrajectorySync(toFirstSkystone);
        drive.update();

        // 2. Grab the first stone
        autored.lowergrab();
        autored.close();
        sleep(250);
        autored.lift();
        sleep(250);

        // 3. Move the first stone to the base.
        Trajectory toBase = drive.trajectoryBuilder()
                .splineTo(new Pose2d(0, yInitToSplineArc, Math.PI))
                .splineTo(new Pose2d(64, yInitToBase, Math.PI))
                .build();
        drive.followTrajectorySync(toBase);
        drive.update();

        // 4. Drop the first skyStone on the base
        autored.lowerplace();
        autored.open();
        sleep(250);
        autored.retract();

        // 5. Move from the Base to the second skyStone, while lower the Red claw and open it up
        Trajectory toSecondSkystone = drive.trajectoryBuilder()
                .reverse()
                .splineTo(new Pose2d(0, yInitToSplineArc, Math.PI))
                .addMarker(() -> {autored.lowerplace(); autored.open(); return null;})
                .splineTo(new Pose2d(skystoneX + 24, yInitToStone + 2, Math.PI))
                .build();
        drive.followTrajectorySync(toSecondSkystone);
        drive.update();

        // 6. Grab the second stone
        autored.lowergrab();
        autored.close();
        sleep(250);
        autored.lift();
        sleep(250);

        // 7. Move the 2nd skyStone to the Base
        Trajectory toBaseAgain;
        if (position == 2) {
            toBaseAgain = drive.trajectoryBuilder()
                    .lineTo(new Vector2d(skystoneX + 16, yInitToStone + 3))
                    .splineTo(new Pose2d(0, yInitToSplineArc, Math.PI))
                    //.strafeTo(new Vector2d(0, yInitToSplineArc))
                    .splineTo(new Pose2d(56, yInitToBase + 3, Math.PI))
                    .build();
        } else {
            toBaseAgain = drive.trajectoryBuilder()
                    .splineTo(new Pose2d(0, yInitToSplineArc, Math.PI))
                    //.strafeTo(new Vector2d(0, yInitToSplineArc))
                    .splineTo(new Pose2d(56, yInitToBase - 3, Math.PI))
                    .build();
        }
        drive.followTrajectorySync(toBaseAgain);
        drive.update();

        // 8. Drop the 2nd skyStone on the Base
        autored.lowerplace();
        autored.open();
        sleep(250);
        autored.retract();

        // 9. Move to the 3rd skyStone.
        Trajectory toThirdSkystone;

        toThirdSkystone = drive.trajectoryBuilder()
                .reverse()
                .splineTo(new Pose2d(0, yInitToSplineArc, Math.PI))
                //.addMarker(() -> {autored.lowerplace(); autored.open(); return null;})
                .splineTo(new Pose2d(xThirdBlock, yInitToStone - 4, Math.PI))
                .build();
        drive.followTrajectorySync(toThirdSkystone);
        drive.update();

        // 10. Grab the 3rd skyStone
        autored.lowergrab();
        autored.open();
        sleep(500);
        autored.close();
        sleep(250);
        autored.lift();
        sleep(250);

        // 11. Move the 3rd skyStone to the base
        Trajectory toBaseAgainAgain;

        if (position == 2) {
            toBaseAgainAgain = drive.trajectoryBuilder()
                    .splineTo(new Pose2d(0, yInitToSplineArc-2, Math.PI))
                    .splineTo(new Pose2d(48, yInitToBase - 7, Math.PI))
                    .build();
        } else {
            toBaseAgainAgain = drive.trajectoryBuilder()
                    .lineTo(new Vector2d(xThirdBlock - 8, yInitToStone - 4))
                    .splineTo(new Pose2d(0, yInitToSplineArc-2, Math.PI))
                    .splineTo(new Pose2d(48, yInitToBase - 7, Math.PI))
                    .build();
        }
        drive.followTrajectorySync(toBaseAgainAgain);
        drive.update();

        // 12. Drop the 3rd skyStone on the base
        autored.lowerplace();
        autored.open();
        sleep(250);
        autored.retract();

        // 13. Turn left 180 degrees
        drive.turnSync(-0.5 * Math.PI);
        drive.update();

        // 14. Move forward 8 inches
        Trajectory ram = drive.trajectoryBuilder()
                .forward(8)
                .build();
        drive.followTrajectorySync(ram);
        drive.update();

        // 15. Close the base hook
        base.close();
        sleep(250);

        // 16. Pull the base
        Trajectory pull = drive.trajectoryBuilder()
                .reverse()
                .splineTo(new Pose2d(32, 50, 0))
                .build();
        drive.followTrajectorySync(pull);
        drive.update();

        // 17. Push the base while open the base hook.
        Trajectory push = drive.trajectoryBuilder()
                .strafeRight(6)
                .forward(20)
                .build();

        base.open(); // Should we use a marker here?

        drive.followTrajectorySync(push);
        drive.update();

        // 18. Park. Move back 15 inches while rolling out the measure tape.
        Trajectory park = drive.trajectoryBuilder()
                .back(15)
                .build();

        robot.bean.setPower(-1);
        drive.followTrajectorySync(park);
        robot.bean.setPower(0);
    }
}