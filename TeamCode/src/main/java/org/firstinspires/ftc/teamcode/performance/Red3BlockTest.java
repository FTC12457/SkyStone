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

import detectors.FoundationPipeline.Pipeline;

/*
This class is the autonomous for red that does everything, and presumes the alliance partner robot
immediately heads to park, next to the wall.
 */

@Autonomous(name = "Red3BlockTest", group = "Performance")
public class Red3BlockTest extends LinearOpMode2 {
    String teamColor = "Red";

    double[] firstSkystoneXAdjustment = new double[] {0, 0, 0};
    double[] firstSkystoneYAdjustment = new double[] {0, 0, 0};
    double[] firstPlateBridgeXAdjustment = new double[] {0, 0, 0};
    double[] firstplateBridgeYAdjustment = new double[] {0, 0, 0};
    double[] firstPlateXAdjustment = new double[] {0, 0, 0};
    double[] firstplateYAdjustment = new double[] {0, 0, 0};
    double[] secondSkystoneBridgeXAdjustment = new double[] {0, 0, 0};
    double[] secondSkystoneBridgeYAdjustment = new double[] {0, 0, 0};
    double[] secondSkystoneXAdjustment = new double[] {0, 0, 0};
    double[] secondSkystoneYAdjustment = new double[] {-2, -2, -2};
    double[] secondPlateBridgeXAdjustment = new double[] {0, 0, 0};
    double[] secondPlateBridgeYAdjustment = new double[] {-1, -1, -4};
    double[] secondPlateXAdjustment = new double[] {0, 0, 0};
    double[] secondPlateYAdjustment = new double[] {-3.5, -3.5, -4};
    double[] thirdSkystoneBridgeXAdjustment = new double[] {0, 0, 0};
    double[] thirdSkystoneBridgeYAdjustment = new double[] {-2, -2, -2};
    double[] thirdSkystoneXAdjustment = new double[] {0, 0, 0};
    double[] thirdSkystoneYAdjustment = new double[] {-4, -4, -6};
    double[] thirdPlateBridgeXAdjustment = new double[] {0, 0, 0};
    double[] thirdPlateBridgeYAdjustment = new double[] {-7, -7, -5};
    double[] thirdPlateXAdjustment = new double[] {0, 0, 0};
    double[] thirdPlateYAdjustment = new double[] {-8, -8, -6};

    // y value between initial position and the Base
    final double yInitToBase = -29;

    // y value between initial position and the arc peak of the spline
    final double yInitToSplineArc = -36;

    // y value between initial position and the line of stones
    final double yInitToStone = -29.5;

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

        //position = 0;

        if (position == 0) {
            skystoneX = -64;
        } else if (position == 1) {
            skystoneX = -56;
        } else {
            skystoneX = -46;
            xThirdBlock = xThirdBlock - 8;
        }
        // Incorporate skystone detection here.

        // set the position of the initial place
        drive.setPoseEstimate(new Pose2d(-33, -63, Math.PI));

        // 0. Open red claw, lower claw
        autored.open();
        autored.lowerplace();

        // 1. Move to the first stone.
        Trajectory toFirstSkystone;
        toFirstSkystone = drive.trajectoryBuilder()
                .strafeTo(new Vector2d(skystoneX + firstSkystoneXAdjustment[position], yInitToStone + firstSkystoneYAdjustment[position]))
                .build();
        drive.followTrajectorySync(toFirstSkystone);
        drive.update();

        // 2. Grab the first stone
        autored.lowergrab();
        autored.close();
        sleep(200);
        autored.lift();
        sleep(200);

        // 3. Move the first stone to the base.
        Trajectory toBase = drive.trajectoryBuilder()
                .reverse()
                .splineTo(new Pose2d(0 + firstPlateBridgeXAdjustment[position], yInitToSplineArc + firstplateBridgeYAdjustment[position], Math.PI))
                .splineTo(new Pose2d(64 + firstPlateXAdjustment[position], yInitToBase + firstplateYAdjustment[position], Math.PI))
                .build();
        drive.followTrajectorySync(toBase);
        drive.update();

        // 4. Drop the first skyStone on the base
        autored.lowerplace();
        autored.open();
        sleep(250);
        autored.retract();

        // 5. Move from the Base to the second skyStone, while lower the Red claw and open it up
        Trajectory  toSecondSkystone = drive.trajectoryBuilder()
                .splineTo(new Pose2d(0 + secondSkystoneBridgeXAdjustment[position], yInitToSplineArc + secondSkystoneBridgeYAdjustment[position], Math.PI))
                .addMarker(() -> {autored.lowerplace(); autored.open(); return null;})
                .splineTo(new Pose2d(skystoneX + 24 + secondSkystoneXAdjustment[position], yInitToStone + secondSkystoneYAdjustment[position], Math.PI))
                .build();
        drive.followTrajectorySync(toSecondSkystone);
        drive.update();

        // 6. Grab the second stone
        autored.lowergrab();
        autored.close();
        sleep(200);
        autored.lift();
        sleep(200);

        // 7. Move the 2nd skyStone to the Base
        Trajectory toBaseAgain = drive.trajectoryBuilder()
                .reverse()
                .splineTo(new Pose2d(0 + secondPlateBridgeXAdjustment[position], yInitToSplineArc +secondPlateBridgeYAdjustment[position], Math.PI))
                //.strafeTo(new Vector2d(0, yInitToSplineArc))
                .splineTo(new Pose2d(60 + secondPlateXAdjustment[position], yInitToBase +secondPlateYAdjustment[position], Math.PI))
                .build();
        if (position == 2) {
            toBaseAgain = drive.trajectoryBuilder()
                    .lineTo(new Vector2d(skystoneX + 16, yInitToStone - 3))
                    .reverse()
                    .splineTo(new Pose2d(-10 + secondPlateBridgeXAdjustment[position], yInitToSplineArc +secondPlateBridgeYAdjustment[position], Math.PI))
                    .splineTo(new Pose2d(0 + secondPlateXAdjustment[position], yInitToSplineArc + secondPlateBridgeYAdjustment[position], Math.PI))
                    //.strafeTo(new Vector2d(0, yInitToSplineArc))
                    .splineTo(new Pose2d(60 + secondPlateXAdjustment[position], yInitToBase +secondPlateYAdjustment[position], Math.PI))
                    .build();
        }
        drive.followTrajectorySync(toBaseAgain);
        drive.update();

        // 8. Drop the 2nd skyStone on the Base
        autored.lowerplace();
        autored.open();
        sleep(200);
        autored.retract();

        // 9. Move to the 3rd skyStone.
        Trajectory toThirdSkystone = drive.trajectoryBuilder()
                .splineTo(new Pose2d(0 + thirdSkystoneBridgeXAdjustment[position], yInitToSplineArc + thirdSkystoneBridgeYAdjustment[position], Math.PI))
                //.addMarker(() -> {autored.lowerplace(); autored.open(); return null;})
                .splineTo(new Pose2d(xThirdBlock + thirdSkystoneXAdjustment[position], yInitToStone + thirdSkystoneYAdjustment[position], Math.PI))
                .build();
        drive.followTrajectorySync(toThirdSkystone);
        drive.update();

        // 10. Grab the 3rd skyStone
        autored.lowergrab();
        autored.open();
        sleep(500);
        autored.close();
        sleep(200);
        autored.lift();
        sleep(200);

        // 11. Move the 3rd skyStone to the base
        Trajectory toBaseAgainAgain = drive.trajectoryBuilder()
                .lineTo(new Vector2d(xThirdBlock - 8 + thirdSkystoneXAdjustment[position], yInitToStone + thirdSkystoneYAdjustment[position]))
                .reverse()
                .splineTo(new Pose2d(-10 + thirdPlateBridgeXAdjustment[position], yInitToSplineArc + thirdPlateBridgeYAdjustment[position], Math.PI))
                .splineTo(new Pose2d(0 + thirdPlateBridgeXAdjustment[position], yInitToSplineArc +thirdPlateBridgeYAdjustment[position], Math.PI))
                .splineTo(new Pose2d(56 + thirdPlateXAdjustment[position], yInitToBase + thirdPlateYAdjustment[position], Math.PI))
                .build();
        if (position == 2) {
            toBaseAgainAgain = drive.trajectoryBuilder()
                    .reverse()
                    .splineTo(new Pose2d(0 + thirdPlateBridgeXAdjustment[position], yInitToSplineArc +thirdPlateBridgeYAdjustment[position], Math.PI))
                    .splineTo(new Pose2d(56 + thirdPlateXAdjustment[position], yInitToBase + thirdPlateYAdjustment[position], Math.PI))
                    .build();
        }
        drive.followTrajectorySync(toBaseAgainAgain);
        drive.update();

        // 12. Drop the 3rd skyStone on the base
        autored.lowerplace();
        autored.open();
        sleep(200);
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
        sleep(200);

        // 16. Pull the base
        Trajectory pull = drive.trajectoryBuilder()
                .reverse()
                .splineTo(new Pose2d(32, -55, 0))
                .build();
        drive.followTrajectorySync(pull);
        drive.update();

        // 17. Push the base while open the base hook.
        Trajectory push = drive.trajectoryBuilder()
                .strafeLeft(6)
                .addMarker(() -> {robot.bean.setPower(-1); return null;})
                .forward(20)
                .build();

        base.open(); // Should we use a marker here?

        drive.followTrajectorySync(push);
        drive.update();

        // 18. Park. Move back 15 inches while rolling out the measure tape.
        Trajectory park = drive.trajectoryBuilder()
                .back(15)
                .build();

        robot.bean.setPower(0);
        drive.followTrajectorySync(park);
    }
}