package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.CRServo;

public class Hardware {
    public DcMotor frontLeftDrive = null;
    public DcMotor frontRightDrive = null;
    public DcMotor backLeftDrive = null;
    public DcMotor backRightDrive = null;

    public Servo claw = null;

    public DcMotor arm = null;
    public CRServo rise = null;

    public Servo baseL = null;
    public Servo baseR = null;

    public Servo dArm = null;
    public CRServo dClaw = null;


    HardwareMap hwMap = null;

    public Hardware()
    {
        // Constructor
    }

    public void init(HardwareMap ahwMap) {

        hwMap = ahwMap;

        // At convenience, change names and config of RL and RR to BL and BR, respectively.

        frontLeftDrive      = hwMap.get(DcMotor.class, "FL");
        frontRightDrive     = hwMap.get(DcMotor.class, "FR");
        backLeftDrive       = hwMap.get(DcMotor.class, "RL");
        backRightDrive      = hwMap.get(DcMotor.class, "RR");

        claw                = hwMap.get(Servo.class,   "CL");
        arm                 = hwMap.get(DcMotor.class, "AR");
        rise                = hwMap.get(CRServo.class, "RS");
        baseL               = hwMap.get(Servo.class,   "BL");
        baseR               = hwMap.get(Servo.class,   "BR");
        dArm                = hwMap.get(Servo.class,   "DA");
        dClaw               = hwMap.get(CRServo.class, "DC");

        frontLeftDrive. setDirection(DcMotor.Direction.FORWARD); // Set to REVERSE if using AndyMark motors
        frontRightDrive.setDirection(DcMotor.Direction.REVERSE); // Set to FORWARD if using AndyMark motors
        backLeftDrive.  setDirection(DcMotor.Direction.FORWARD); // Set to REVERSE if using AndyMark motors
        backRightDrive. setDirection(DcMotor.Direction.REVERSE); // Set to FORWARD if using AndyMark motors

        arm.setDirection(DcMotor.Direction.FORWARD);
        arm.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        rise.setDirection(CRServo.Direction.FORWARD);
        rise.setPower(0);

        claw.setPosition(0);

        baseL.setPosition(0);
        baseR.setPosition(0);

        dArm.setPosition(0);

        dClaw.setDirection(CRServo.Direction.FORWARD);
        dClaw.setPower(0);
    }
}
