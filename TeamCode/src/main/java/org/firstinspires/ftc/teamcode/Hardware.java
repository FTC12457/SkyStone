package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.CRServo;

public class Hardware {
    public DcMotor frontLeftDrive = null;
    public DcMotor frontRightDrive = null;
    public DcMotor backLeftDrive = null;
    public DcMotor backRightDrive = null;

    public Servo claw = null;

    public DcMotor arm = null; // Experimental for ArmTest class

    public CRServo rise = null; // Experimental for ArmTest class


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

        frontLeftDrive. setDirection(DcMotor.Direction.FORWARD); // Set to REVERSE if using AndyMark motors
        frontRightDrive.setDirection(DcMotor.Direction.REVERSE); // Set to FORWARD if using AndyMark motors
        backLeftDrive.  setDirection(DcMotor.Direction.FORWARD); // Set to REVERSE if using AndyMark motors
        backRightDrive. setDirection(DcMotor.Direction.REVERSE); // Set to FORWARD if using AndyMark motors

        // Initialize the arm motor.
        arm = hwMap.get(DcMotor.class, "ARM");
        arm.setDirection(DcMotor.Direction.FORWARD);
        arm.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        rise = hwMap.get(CRServo.class, "RISE");

        rise.setPower(0);

        claw.setPosition(0.3);
    }
}
