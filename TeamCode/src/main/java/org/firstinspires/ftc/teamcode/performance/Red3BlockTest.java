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

@Autonomous(name = "Red3BlockTest", group = "Performance")
@SuppressWarnings({"WeakerAccess", "SpellCheckingInspection"})
public class Red3BlockTest extends LinearOpMode2 {
    String teamColor = "Red";

    //                                              position==0         position==2
    //                                                          position==1
    // First skystone
    double[] xFirstSkystone                 = new double[] {-64,  -56,    -48};
    double[] xFirstSkystoneAdjustment       = new double[] {0,      0,      2};
    double   ySkystone                      = -29.5;                            // y value between initial position and the line of stones
    double[] yFirstSkystoneAdjustment       = new double[] {0,      0,      0};

    double[] xFirstPlateBridgeAdjustment    = new double[] {0,      0,      0};
    double   yBridge                        = -36;                              // y value between initial position and the arc peak of the spline
    double[] yFirstplateBridgeAdjustment    = new double[] {0,      0,      0};

    double   xFirstPlate                    = 64;                               // x (on the plate) where the first skystone is placed.
    double[] xFirstPlateAdjustment          = new double[] {0,      0,      0};
    double   yInitToPlate                   = -29;                              // y value between initial position and the plate
    double[] yFirstplateAdjustment          = new double[] {0,      0,      0};

    //
    // Second skystone
    //
    double[] xSecondSkystoneBridgeAdjustment= new double[] {0,      0,      0};
    double[] ySecondSkystoneBridgeAdjustment= new double[] {0,      0,      0};

    //       xSecondSkystone is set as xFirstSkystone + 24
    double[] xSecondSkystoneAdjustment      = new double[] {0,      0,      0};
    //       ySecondSkystone is set as ySkystone
    double[] ySecondSkystoneAdjustment      = new double[] {-2,     -2,     -2};

    double[] xSecondPlateBridgeAdjustment   = new double[] {0,      0,      0};
    double[] ySecondPlateBridgeAdjustment   = new double[] {-1,     -1,     -4};

    double   xSecondPlate                   = 60;                               // x (on the plate) where the second skystone is placed.
    double[] xSecondPlateAdjustment         = new double[] {0,      0,      0};
    //       ySecondPlate is yInitToPlate
    double[] ySecondPlateAdjustment         = new double[] {-3.5,   -3.5,   -4};

    //
    // Third skystone
    //
    double[] xThirdSkystoneBridgeAdjustment = new double[] {0,      0,      0};
    double[] yThirdSkystoneBridgeAdjustment = new double[] {-2,     -2,     -2};

    double   xThirdSkystone                 = -24;                              // default x value of the third stone
    double[] xThirdSkystoneAdjustment       = new double[] {4,      4,      4};
    //       yThirdSkystone is set as ySkystone
    double[] yThirdSkystoneAdjustment       = new double[] {-4,     -4,     -6};

    double[] xThirdPlateBridgeAdjustment    = new double[] {0,      0,      0};
    double[] yThirdPlateBridgeAdjustment    = new double[] {-7,     -7,     -5};

    double   xThirdPlate                    = 56;                               // x (on the plate) where the third skystone is placed.
    double[] xThirdPlateAdjustment          = new double[] {0,      0,      0};
    //       yThirdPlate is yInitToPlate
    double[] yThirdPlateAdjustment          = new double[] {-8,     -8,     -6};

    // default sleep time in ms
    final int DEFAULT_SLEEP_200_MS = 200;

    Hardware robot = new Hardware();
    Base base = new Base(robot);
    Autored autored = new Autored(robot);
    SkystoneReaderInit initReader = new SkystoneReaderInit(teamColor, this, telemetry);
    @Override
    public void runOpMode() throws InterruptedException {

        robot.init(hardwareMap);
        int position; //0 = near wall, 1 = middle, 2 = far from wall

        SampleMecanumDriveBase drive = new SampleMecanumDriveREV(hardwareMap);

        initReader.run();
        waitForStart();
        position = initReader.placement();

        // set the position of the initial place
        drive.setPoseEstimate(new Pose2d(-33, -63, Math.PI));

        // 0. Open red claw, lower claw
        autored.open();
        autored.lowerplace();

        // 1. Move to the first stone.
        Trajectory toFirstSkystone;
        toFirstSkystone = drive.trajectoryBuilder()
                .strafeTo(new Vector2d(xFirstSkystone[position] + xFirstSkystoneAdjustment[position],
                        ySkystone + yFirstSkystoneAdjustment[position]))
                .build();
        drive.followTrajectorySync(toFirstSkystone);
        drive.update();

        // 2. Grab the first stone
        autored.lowergrab();
        autored.close();
        sleep(DEFAULT_SLEEP_200_MS);
        autored.lift();
        sleep(DEFAULT_SLEEP_200_MS);

        // 3. Move the first stone to the plate.
        Trajectory toPlate = drive.trajectoryBuilder()
                .reverse()
                .splineTo(new Pose2d(0 + xFirstPlateBridgeAdjustment[position],
                        yBridge + yFirstplateBridgeAdjustment[position], Math.PI))
                .splineTo(new Pose2d(xFirstPlate + xFirstPlateAdjustment[position],
                        yInitToPlate + yFirstplateAdjustment[position], Math.PI))
                .build();
        drive.followTrajectorySync(toPlate);
        drive.update();

        // 4. Drop the first skyStone on the plate
        autored.lowerplace();
        autored.open();
        sleep(DEFAULT_SLEEP_200_MS);
        autored.retract();

        // 5. Move from the plate to the second skyStone, while lower the Red claw and open it up
        Trajectory toSecondSkystone = drive.trajectoryBuilder()
                .splineTo(new Pose2d(0 + xSecondSkystoneBridgeAdjustment[position],
                        yBridge + ySecondSkystoneBridgeAdjustment[position], Math.PI))
                .addMarker(() -> {autored.lowerplace(); autored.open(); return null;})
                .splineTo(new Pose2d(xFirstSkystone[position] + 24 + xSecondSkystoneAdjustment[position],
                        ySkystone + ySecondSkystoneAdjustment[position], Math.PI))
                .build();
        drive.followTrajectorySync(toSecondSkystone);
        drive.update();

        // 6. Grab the second stone
        autored.lowergrab();
        autored.close();
        sleep(DEFAULT_SLEEP_200_MS);
        autored.lift();
        sleep(DEFAULT_SLEEP_200_MS);

        // 7. Move the 2nd skyStone to the plate.
        // If the position is 2, actual block is 2+3=5, then apply a sharp spline to avoid a clision with the bridge.
        if (position == 2) {
            toPlate = drive.trajectoryBuilder()
                    .lineTo(new Vector2d(xFirstSkystone[position] + 16, ySkystone - 3))
                    .reverse()
                    .splineTo(new Pose2d(-10 + xSecondPlateBridgeAdjustment[position],
                            yBridge + ySecondPlateBridgeAdjustment[position], Math.PI))
                    .splineTo(new Pose2d(0 + xSecondPlateAdjustment[position],
                            yBridge + ySecondPlateBridgeAdjustment[position], Math.PI))
                    //.strafeTo(new Vector2d(0, yBridge))
                    .splineTo(new Pose2d(xSecondPlate + xSecondPlateAdjustment[position],
                            yInitToPlate + ySecondPlateAdjustment[position], Math.PI))
                    .build();
        } else {
            toPlate = drive.trajectoryBuilder()
                    .reverse()
                    .splineTo(new Pose2d(0 + xSecondPlateBridgeAdjustment[position],
                            yBridge + ySecondPlateBridgeAdjustment[position], Math.PI))
                    //.strafeTo(new Vector2d(0, yBridge))
                    .splineTo(new Pose2d(xSecondPlate + xSecondPlateAdjustment[position],
                            yInitToPlate + ySecondPlateAdjustment[position], Math.PI))
                    .build();
        }
        drive.followTrajectorySync(toPlate);
        drive.update();

        // 8. Drop the 2nd skyStone on the plate
        autored.lowerplace();
        autored.open();
        sleep(DEFAULT_SLEEP_200_MS);
        autored.retract();

        // 9. Move to the 3rd skyStone.

        // If the skystone is at position 2, then the second skystone is at position 5.
        // In this case, the third skystone has to be the one at position 4.
        if (position == 2) {
            xThirdSkystone = xThirdSkystone - 8;
        }

        Trajectory toThirdSkystone = drive.trajectoryBuilder()
                .splineTo(new Pose2d(0 + xThirdSkystoneBridgeAdjustment[position],
                        yBridge + yThirdSkystoneBridgeAdjustment[position], Math.PI))
                //.addMarker(() -> {autored.lowerplace(); autored.open(); return null;})
                .splineTo(new Pose2d(xThirdSkystone + xThirdSkystoneAdjustment[position],
                        ySkystone + yThirdSkystoneAdjustment[position], Math.PI))
                .build();
        drive.followTrajectorySync(toThirdSkystone);
        drive.update();

        // 10. Grab the 3rd skyStone
        autored.lowergrab();
        autored.close();
        sleep(DEFAULT_SLEEP_200_MS);
        autored.lift();
        sleep(DEFAULT_SLEEP_200_MS);

        // 11. Move the 3rd skyStone to the plate
        if (position == 2) {
            toPlate = drive.trajectoryBuilder()
                    .lineTo(new Vector2d(xThirdSkystone - 8 + xThirdSkystoneAdjustment[position],
                            ySkystone + yThirdSkystoneAdjustment[position]))
                    .reverse()
                    .splineTo(new Pose2d(-10 + xThirdPlateBridgeAdjustment[position],
                            yBridge + yThirdPlateBridgeAdjustment[position], Math.PI))
                    .splineTo(new Pose2d(0 + xThirdPlateBridgeAdjustment[position],
                            yBridge + yThirdPlateBridgeAdjustment[position], Math.PI))
                    .splineTo(new Pose2d(xThirdPlate + xThirdPlateAdjustment[position],
                            yInitToPlate + yThirdPlateAdjustment[position], Math.PI))
                    .build();
        } else {
            toPlate = drive.trajectoryBuilder()
                    .reverse()
                    .splineTo(new Pose2d(0 + xThirdPlateBridgeAdjustment[position],
                            yBridge + yThirdPlateBridgeAdjustment[position], Math.PI))
                    .splineTo(new Pose2d(xThirdPlate + xThirdPlateAdjustment[position],
                            yInitToPlate + yThirdPlateAdjustment[position], Math.PI))
                    .build();
        }

        drive.followTrajectorySync(toPlate);
        drive.update();

        // 12. Drop the 3rd skyStone on the plate
        autored.lowerplace();
        autored.open();
        sleep(DEFAULT_SLEEP_200_MS);
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

        // 15. Close the plate hooks
        base.close();
        sleep(DEFAULT_SLEEP_200_MS);

        // 16. Pull the plate
        Trajectory pull = drive.trajectoryBuilder()
                .reverse()
                .splineTo(new Pose2d(32, -55, 0))
                .build();
        drive.followTrajectorySync(pull);
        drive.update();

        // 17. Push the plate while open the plate hooks.
        base.open(); // Todo: Should we use a marker here?

        Trajectory push = drive.trajectoryBuilder()
                .strafeLeft(6)
                .addMarker(() -> {robot.bean.setPower(-1); return null;})
                .forward(20)
                .build();
        drive.followTrajectorySync(push);
        drive.update();

        // 18. Park. Move back 15 inches.
        Trajectory park = drive.trajectoryBuilder()
                .back(15)
                .build();

        robot.bean.setPower(0); // Stop rolling the measuring tape.
        drive.followTrajectorySync(park);
        drive.update();
    }
}
