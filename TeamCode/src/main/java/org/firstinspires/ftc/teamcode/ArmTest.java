package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

@TeleOp(name = "Arm Test", group = "Experimental")
public class ArmTest extends LinearOpMode {
    Hardware robot = new Hardware();
    Drive drive = new Drive();

    public void runOpMode() {

        robot.init(hardwareMap);

        robot.arm = hardwareMap.get(DcMotor.class, "MS_KOL");
        robot.arm.setDirection(DcMotor.Direction.FORWARD);

        waitForStart();

        while(opModeIsActive()) {

            drive.drive();

            robot.arm.setPower(gamepad1.right_trigger*0.2); //added a multiplier of 0.2
            telemetry.addData("MS_KOL", robot.arm.getPower());

            telemetry.update();
            sleep(25);
        }

    }
}
