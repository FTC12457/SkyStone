package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp(name = "Color Sensor Test", group = "Experimental")
public class ColorSensorTest extends LinearOpMode{

    Hardware robot = new Hardware();

    public void runOpMode() {

        robot.init(hardwareMap);

        waitForStart();

        while (opModeIsActive()) {
            telemetry.addData("Light: ", robot.color.alpha());
            telemetry.addData("Red: ", robot.color.red());
            telemetry.addData("Green: ", robot.color.green());
            telemetry.addData("Blue: ", robot.color.blue());
            telemetry.update();
        }
    }
}
