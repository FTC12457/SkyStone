package org.firstinspires.ftc.teamcode.defunct;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.robot.Drive;
import org.firstinspires.ftc.teamcode.robot.Hardware;

//Test
//Hi
@Disabled
@TeleOp(name = "Arm Test", group = "Experimental")
public class ArmTest extends LinearOpMode {
    Hardware robot = new Hardware();
    Drive drive = new Drive(robot);

    public void runOpMode() {

        robot.init(hardwareMap);

        waitForStart();

        while(opModeIsActive()) {

            drive.drive(gamepad1, telemetry);

            robot.arm.setPower(gamepad1.right_stick_y * 0.2); // Multiplier of 0.2

            telemetry.addData("Angular Velocity: ", robot.arm.getPower());

            telemetry.update();

            sleep(25);
        }
    }
}
