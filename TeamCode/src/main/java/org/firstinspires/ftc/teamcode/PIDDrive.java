package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class PIDDrive implements IEncoderDriveTask {
    Hardware robot;
    LinearOpMode opMode;
    Telemetry telemetryInstance;
    PIDLoop pidFL;
    PIDLoop pidFR;
    PIDLoop pidBL;
    PIDLoop pidBR;

    double speed;
    double accel;

    public PIDDrive(double speed, Hardware hardware, LinearOpMode linearOpMode, Telemetry telemetry) {
        robot = hardware;
        opMode = linearOpMode;
        telemetryInstance = telemetry;
        this.speed = speed;

        pidFL = new PIDLoop(100, 0, 0, opMode, robot.frontLeftDrive, telemetryInstance);
        pidFR = new PIDLoop(100, 0, 0, opMode, robot.frontRightDrive, telemetryInstance);
        pidBL = new PIDLoop(100, 0, 0, opMode, robot.backLeftDrive, telemetryInstance);
        pidBR = new PIDLoop(100, 0, 0, opMode, robot.backRightDrive, telemetryInstance);
    }

    public void run() {
        accel = robot.imu.getAcceleration().xAccel;

        pidFL.pidRun(0, accel, 10, speed);
        pidFR.pidRun(0, accel, 10, speed);
        pidBL.pidRun(0, accel, 10, speed);
        pidBR.pidRun(0, accel, 10, speed);
    }
}
