package org.firstinspires.ftc.teamcode;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.roadrunner.mecanum.SampleMecanumDriveBase;
import org.firstinspires.ftc.teamcode.roadrunner.mecanum.SampleMecanumDriveREV;
import org.firstinspires.ftc.teamcode.robot.Autoblue;
import org.firstinspires.ftc.teamcode.robot.Base;
import org.firstinspires.ftc.teamcode.robot.Hardware;

/*
This class is the autonomous for blue that does everything, and presumes the alliance partner robot
immediately heads to park, next to the wall.
 */

@Autonomous(name = "test3blockblue", group = "Performance")
public class Test3BlockBlue extends LinearOpMode2{
    // drive constants
    String teamColor = "Blue";

    final double yInitPosToBase = -32.5;
    final double yInitPosToSplineArc = -27;
    final double yInitPosToStoneLine = -31.7;

    Hardware robot = new Hardware();
    Base base = new Base(robot);
    Autoblue autoblue = new Autoblue(robot);
    SkystoneReader reader = new SkystoneReader(teamColor, this, telemetry);
    SkystoneReaderInit initReader = new SkystoneReaderInit(teamColor, this, telemetry);

    @Override
    public void runOpMode() throws InterruptedException {

        robot.init(hardwareMap);

        SampleMecanumDriveBase drive = new SampleMecanumDriveREV(hardwareMap);

        int skystoneX;

        initReader.run();
        waitForStart();

        int skystoneposition = initReader.placement();

        if (skystoneposition == 2) {
            skystoneX = -13;
        } else if (skystoneposition == 1) {
            skystoneX = -21;
        } else if (skystoneposition == 0) {
            skystoneX = -30;
        } else {
            skystoneX = -30;
        }

        // 1. Move to the first skyStone.
        Trajectory toFirstSkystone = drive.trajectoryBuilder()
                .strafeTo(new Vector2d(skystoneX, yInitPosToStoneLine))
                .build();

        drive.followTrajectorySync(toFirstSkystone);
        //sleep(250);

        // 2. Grab the first skyStone.
        autoblue.open();
        autoblue.lowergrab();
        sleep(250);
        autoblue.close();
        sleep(250);
        autoblue.lift();
        //sleep(500);
        drive.update();

        // 3. Move the first stone to the base.
        Trajectory toBase = drive.trajectoryBuilder()
                .splineTo(new Pose2d(28, yInitPosToSplineArc, 0))
                .splineTo(new Pose2d(91, yInitPosToBase, 0))
                .build();
        drive.followTrajectorySync(toBase);

        // 4. Drop the first skyStone on the base
        //sleep(250);
        // should you used the Marker to move arm while you drive?
        autoblue.lowerplace();
        sleep(250);
        autoblue.open();
        //sleep(250);
        autoblue.lift();
        sleep(250);
        autoblue.close(); //ideally we should have the open position also fit inside of the sizing cube
        drive.update();

        // 5. Move to the 2nd skyStone
        Trajectory toSecondSkystone = drive.trajectoryBuilder()
                .reverse()
                .splineTo(new Pose2d(39, yInitPosToSplineArc, 0))
                .splineTo(new Pose2d(skystoneX + 28, yInitPosToStoneLine, 0)) // why 28 instead of 24? Each stone is 8 inches long.
                .build();
        drive.followTrajectorySync(toSecondSkystone);

        // 6. Grab the 2nd skyStone
        autoblue.open();
        autoblue.lowergrab();
        sleep(250); // Originally sleep 500ms, change to 250 to be quick
        autoblue.close();
        sleep(250);
        autoblue.lift();
        //sleep(500);
        drive.update();

        // 7. Move the 2nd skyStone to the base
        Trajectory toBaseAgain = drive.trajectoryBuilder()
                .splineTo(new Pose2d(33, yInitPosToSplineArc, 0))
                .splineTo(new Pose2d(83, yInitPosToBase, 0)) // this time 8=91-83 (1 skyStone length) closer
                .build();
        drive.followTrajectorySync(toBaseAgain);

        // drive.followTrajectorySync(toBase); It MIGHT work? There will be an error spike,
        // but in the direction the robot wants to go in anyways. So, maybe replace?

        // 8. Drop the 2nd skyStone on the base
        //sleep(250);
        autoblue.lowerplace();
        sleep(250);
        autoblue.open();
        //sleep(250);
        autoblue.lift();
        sleep(250);
        autoblue.retract();
        drive.update();

        // 9. Move to the 3rd skyStone.
        Trajectory toThirdSkystone = drive.trajectoryBuilder()
                .reverse()
                .splineTo(new Pose2d(39, yInitPosToSplineArc, 0))
                .splineTo(new Pose2d(skystoneX + 20, yInitPosToStoneLine, 0)) // this should be skystoneX + 16, right?
                .build();
        drive.followTrajectorySync(toThirdSkystone);
        drive.update();

        // 10. Grab the 2nd skyStone
                autoblue.open();
                autoblue.lowergrab();
                sleep(250); // Originally sleep 500ms, change to 250 to be quick
                autoblue.close();
                sleep(250);
                autoblue.lift();
                //sleep(500);
                drive.update();

        // 11. Move the 3rd skyStone to the base
        Trajectory toBaseAgainAgain = drive.trajectoryBuilder()
                .splineTo(new Pose2d(33, yInitPosToSplineArc, 0))
                .splineTo(new Pose2d(83, yInitPosToBase, 0)) // this will bump with the 2nd stone on the  base.
                .build();
        drive.followTrajectorySync(toBaseAgainAgain);
        drive.update();

        // 12. Drop the 3rd skyStone on the base
        //sleep(250);
        autoblue.lowerplace();
        sleep(250);
        autoblue.open();
        //sleep(250);
        autoblue.lift();
        sleep(250);
        autoblue.retract();
        drive.update();

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

        // 16. Move and pull the base
        Trajectory pull = drive.trajectoryBuilder()
                .reverse()
                .splineTo(new Pose2d(63, -10, 0))
                .build();
        drive.followTrajectorySync(pull);
        drive.update();

        // 17. Open the base hook
        base.open();

        // 18. move the robot only
        Trajectory push = drive.trajectoryBuilder()
                //.strafeTo(new Vector2d(73, -30))
                .strafeRight(18)
                .forward(20)
                .build();
        drive.followTrajectorySync(push);
        drive.update();

        // 19. Back 15 inches. Seems overlapping with step 18.
        Trajectory park = drive.trajectoryBuilder()
                .back(15)
                .build();

        // 20. Roll out the measure
        robot.bean.setPower(-1);
        // Why drive in the middle of step 20?
        drive.followTrajectorySync(park);
        robot.bean.setPower(0);
    }
}