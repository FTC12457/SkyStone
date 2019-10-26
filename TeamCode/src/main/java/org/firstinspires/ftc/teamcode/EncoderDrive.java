package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;
import org.firstinspires.ftc.robotcore.external.Telemetry;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

public class EncoderDrive {
    Hardware robot = new Hardware();

    public EncoderDrive(Hardware hardware) {

        robot = hardware;

    }

    /*
     *  Method to perform a relative move, based on encoder counts.
     *  Encoders are not reset as the move is based on the current position.
     *  Move will stop if any of three conditions occur:
     *  1) Move gets to the desired position
     *  2) Move runs out of time
     *  3) Driver stops the opmode running.
     */

    void encoderDrive(double speed, String type, double Inches, double timeoutS, LinearOpMode opMode,
                      Telemetry telemetryInstance) {

        double COUNTS_PER_MOTOR_REV    = 1120; // If using Tetrix motors, set number to 1440 eg: TETRIX Motor Encoder
        double DRIVE_GEAR_REDUCTION    = 0.5;  // This is < 1.0 if geared UP
        double WHEEL_DIAMETER_INCHES   = 4.0;  // For figuring circumference
        double COUNTS_PER_INCH_WHEELS
                = (COUNTS_PER_MOTOR_REV * DRIVE_GEAR_REDUCTION) / (WHEEL_DIAMETER_INCHES * 3.14159265358979);

        int newLeftTarget;
        int newRightTarget;
        int newLeftBackTarget;
        int newRightBackTarget;

        ElapsedTime runtime = new ElapsedTime();

        /* Ensures that the opmode is still active. */

        if (opMode.opModeIsActive()) {

            /* First, the method determines the new target position of the wheel from the old one.
             */

            if (type.equals("Forward")) {

                /* Forward/Backwards type of movement. Negative Inches parameter goes backwards,
                positive Inches parameter goes forwards. */

                newLeftTarget      = robot.frontLeftDrive.getCurrentPosition()
                        + (int) (Inches * COUNTS_PER_INCH_WHEELS);
                newRightTarget     = robot.frontRightDrive.getCurrentPosition()
                        + (int) (Inches * COUNTS_PER_INCH_WHEELS);
                newLeftBackTarget  = robot.backRightDrive.getCurrentPosition()
                        + (int) (Inches * COUNTS_PER_INCH_WHEELS);
                newRightBackTarget = robot.backRightDrive.getCurrentPosition()
                        + (int) (Inches * COUNTS_PER_INCH_WHEELS);

            } else if (type.equals("Strafe")) {

                /* Left/Right type of movement. Negative Inches parameter goes left, positive Inches
                parameter goes right. */

                newLeftTarget      = robot.frontLeftDrive.getCurrentPosition()
                        + (int) (Inches * COUNTS_PER_INCH_WHEELS);
                newRightTarget     = robot.frontRightDrive.getCurrentPosition()
                        + (int) (-Inches * COUNTS_PER_INCH_WHEELS);
                newLeftBackTarget  = robot.backRightDrive.getCurrentPosition()
                        + (int) (-Inches * COUNTS_PER_INCH_WHEELS);
                newRightBackTarget = robot.backRightDrive.getCurrentPosition()
                        + (int) (Inches * COUNTS_PER_INCH_WHEELS);

            } else if (type.equals("Turn")) {

                /* Turning type of movement. Negative Inches parameter turns left, positive Inches
                parameter turns right. */

                newLeftTarget      = robot.frontLeftDrive.getCurrentPosition()
                        + (int) (Inches * COUNTS_PER_INCH_WHEELS);
                newRightTarget     = robot.frontRightDrive.getCurrentPosition()
                        + (int) (-Inches * COUNTS_PER_INCH_WHEELS);
                newLeftBackTarget  = robot.backRightDrive.getCurrentPosition()
                        + (int) (Inches * COUNTS_PER_INCH_WHEELS);
                newRightBackTarget = robot.backRightDrive.getCurrentPosition()
                        + (int) (-Inches * COUNTS_PER_INCH_WHEELS);

            } else {

                /* Error message in case of wrong implementation of method. */

                telemetryInstance.addData("ERROR", "WRONG TYPE FOR ENCODERDRIVER");
                newLeftTarget = robot.frontLeftDrive.getCurrentPosition();
                newRightTarget = robot.frontRightDrive.getCurrentPosition();
                newLeftBackTarget = robot.backRightDrive.getCurrentPosition();
                newRightBackTarget = robot.backRightDrive.getCurrentPosition();
            }

            robot.frontLeftDrive.setTargetPosition(newLeftTarget);
            robot.frontRightDrive.setTargetPosition(newRightTarget);
            robot.backRightDrive.setTargetPosition(newLeftBackTarget);
            robot.backRightDrive.setTargetPosition(newRightBackTarget);

            /* Turns on RUN_TO_POSITION, allowing it to use the target position determined
            previously. */

            robot.frontLeftDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            robot.frontRightDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            robot.backRightDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            robot.backRightDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            /* Having determined the position target, each wheel motor starts rotating until one of
            the following occurs:
                1) the op mode terminates.
                2) the safety runtime parameter is exceeded.
                3) all wheels have reached the target position specified. */

            runtime.reset();
            robot.frontLeftDrive.setPower(Math.abs(speed));
            robot.frontRightDrive.setPower(Math.abs(speed));
            robot.backRightDrive.setPower(Math.abs(speed));
            robot.backRightDrive.setPower(Math.abs(speed));

            while (opMode.opModeIsActive() &&
                    (runtime.seconds() < timeoutS) &&
                    (robot.frontLeftDrive.isBusy() || robot.frontRightDrive.isBusy()
                            || robot.backLeftDrive.isBusy() || robot.backRightDrive.isBusy())) {

                // NOTE: We use (isBusy() && isBusy()) in the loop test, which means that when
                // ANY of the motors hits its target position, the motion will stop.  This is "safer" in
                // the event that the robot will always end the motion as soon as possible. However,
                // if you require that BOTH motors have finished their moves before the robot
                // continues onto the next step, use (isBusy() || isBusy()) in the loop test.

                telemetryInstance.addData("BL Target Position:", robot.backLeftDrive.getTargetPosition());
                telemetryInstance.addData("BL Current Position:", robot.backLeftDrive.getCurrentPosition());
                telemetryInstance.addData("BR Target Position:", robot.backRightDrive.getTargetPosition());
                telemetryInstance.addData("BL Current Position:", robot.backRightDrive.getCurrentPosition());
                telemetryInstance.addData("FL Target Position:", robot.frontLeftDrive.getTargetPosition());
                telemetryInstance.addData("FL Current Position:", robot.frontLeftDrive.getCurrentPosition());
                telemetryInstance.addData("FR Target Position:", robot.frontRightDrive.getTargetPosition());
                telemetryInstance.addData("FR Current Position:", robot.frontRightDrive.getCurrentPosition());
                telemetryInstance.update();

            }

            /* Stops all motion once one of the above three conditions is exceeded. */

            robot.frontLeftDrive.setPower(0);
            robot.frontRightDrive.setPower(0);
            robot.backRightDrive.setPower(0);
            robot.backRightDrive.setPower(0);

            /* Telemetry for debugging. */

            telemetryInstance.addData("Path2", "Running at %7d :%7d",
                    robot.frontLeftDrive.getCurrentPosition(),
                    robot.frontRightDrive.getCurrentPosition(),
                    robot.backRightDrive.getCurrentPosition(),
                    robot.backRightDrive.getCurrentPosition());
            telemetryInstance.update();

            /* Turns of RUN_TO_POSITION. */
            robot.frontLeftDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            robot.frontRightDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            robot.backRightDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            robot.backRightDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        }
    }
}
