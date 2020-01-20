package org.firstinspires.ftc.teamcode.robot;

import org.firstinspires.ftc.teamcode.LinearOpMode2;
import org.firstinspires.ftc.teamcode.roadrunner.localizer.ThreeLocalizer;
import org.firstinspires.ftc.teamcode.roadrunner.mecanum.SampleMecanumDriveBase;
import org.firstinspires.ftc.teamcode.roadrunner.mecanum.SampleMecanumDriveREV;

public class AutoDrive {
    Hardware robot = new Hardware();

    ThreeLocalizer localizer = new ThreeLocalizer(robot.leftEncoder, robot.arm, robot.bean);

    public SampleMecanumDriveBase drive;

    public AutoDrive(LinearOpMode2 opMode2) {
        drive = new SampleMecanumDriveREV(opMode2.hardwareMap);
        drive.setLocalizer(localizer);
    }
}
