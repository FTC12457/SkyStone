package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.robot.Hardware;

@TeleOp (name = "Odometry Test", group = "Experimental")
public class Odometry extends LinearOpMode {
    Hardware robot = new Hardware();

    @Override
    public void runOpMode() {
        robot.init(hardwareMap);

        waitForStart();

        while (opModeIsActive()) {
            telemetry.addData("Position of Encoder Left: ", robot.leftEncoder.getCurrentPosition());
            telemetry.addData("Position of Encoder Arm: ", robot.arm.getCurrentPosition());
            telemetry.addData("Position of Encoder Bean: ", robot.bean.getCurrentPosition());
            //telemetry.addData("Position of Encoder Bean: ", robot.bean.getCurrentPosition());
            telemetry.update();
        }
    }
}
