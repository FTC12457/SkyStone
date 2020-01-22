//package org.firstinspires.ftc.teamcode.defunct;
//
//import com.qualcomm.robotcore.eventloop.opmode.Disabled;
//import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
//import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
//
//import org.firstinspires.ftc.teamcode.robot.Base;
//import org.firstinspires.ftc.teamcode.robot.Claw;
//import org.firstinspires.ftc.teamcode.robot.Drive;
//import org.firstinspires.ftc.teamcode.robot.Hardware;
//
//@Disabled
//@TeleOp(name = "Controlled", group = "Performance")
//public class Controlled extends LinearOpMode {
//    Hardware robot = new Hardware();
//    Drive drive = new Drive(robot);
//    Claw claw = new Claw(robot);
//    Base base = new Base(robot);
//
//    public void runOpMode() {
//
//        robot.init(hardwareMap);
//
//        waitForStart();
//
//        while(opModeIsActive()) {
//
//            drive.drive(gamepad1, telemetry);
//
//            robot.arm.setPower(gamepad2.left_stick_y);
//
//            claw.run(gamepad2.b, gamepad2.y);
//            base.run(gamepad2.x);
//
//            telemetry.update();
//
//            sleep(25);
//        }
//    }
//}
