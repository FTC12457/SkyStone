package org.firstinspires.ftc.teamcode.roadrunner.localizer;

import android.support.annotation.NonNull;

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.localization.TwoTrackingWheelLocalizer;
import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

import java.util.Arrays;
import java.util.List;

/*
 * Sample tracking wheel localizer implementation assuming the standard configuration:
 *
 *    /--------------\
 *    |     ____     |
 *    |     ----     |
 *    | ||        || |
 *    | ||        || |
 *    |              |
 *    |              |
 *    \--------------/
 *
 * Note: this could be optimized significantly with REV bulk reads
 */
@Config
public class TwoLocalizer extends TwoTrackingWheelLocalizer {
    public static double TICKS_PER_REV = 1440;
    public static double WHEEL_RADIUS = (1.5)/2.0; // in   (1.0+7.0/16.0)/2.0
    public static double GEAR_RATIO = 1; // output (wheel) speed / input (encoder) speed

    public static double LATERAL_DISTANCE = 11; // in; distance between the left and right wheels
    public static double FORWARD_OFFSET = 0; // in; offset of the lateral wheel

    private BNO055IMU inertial;

    private DcMotor leftEncoder, frontEncoder;

    public TwoLocalizer(HardwareMap hardwareMap, DcMotor left, DcMotor front, BNO055IMU imu) {
        super(Arrays.asList(
                new Pose2d(FORWARD_OFFSET, 0, Math.toRadians(90)), // front
                new Pose2d(0, LATERAL_DISTANCE / 2, 0) // left
        ));

        inertial = imu;

        leftEncoder = left;
        frontEncoder = front;
    }

    public static double encoderTicksToInches(int ticks) {
        return WHEEL_RADIUS * 2 * Math.PI * GEAR_RATIO * ticks / TICKS_PER_REV;
    }

    @NonNull
    @Override
    public List<Double> getWheelPositions() {
        return Arrays.asList(
                encoderTicksToInches(frontEncoder.getCurrentPosition()),
                encoderTicksToInches(leftEncoder.getCurrentPosition())
        );
    }

    @Override
    public double getHeading() {
        return inertial.getAngularOrientation().firstAngle;
    }
}
