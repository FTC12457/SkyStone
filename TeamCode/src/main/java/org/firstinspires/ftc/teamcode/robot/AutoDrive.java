package org.firstinspires.ftc.teamcode.robot;

import com.acmerobotics.roadrunner.control.PIDCoefficients;
import com.acmerobotics.roadrunner.localization.Localizer;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.LinearOpMode2;
import org.firstinspires.ftc.teamcode.roadrunner.localizer.BadLocalizer;
import org.firstinspires.ftc.teamcode.roadrunner.mecanum.SampleMecanumDriveBase;
import org.firstinspires.ftc.teamcode.roadrunner.mecanum.SampleMecanumDriveREV;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class AutoDrive {
    Hardware robot = new Hardware();

    BadLocalizer localizer = new BadLocalizer(robot.leftEncoder, robot.arm, robot.bean);

    public SampleMecanumDriveBase drive;

    public AutoDrive(LinearOpMode2 opMode2) {
        drive = new SampleMecanumDriveREV(opMode2.hardwareMap);
        drive.setLocalizer(localizer);
    }
}
