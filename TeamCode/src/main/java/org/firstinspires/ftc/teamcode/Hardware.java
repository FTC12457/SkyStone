package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo; //added by brian

public class Hardware {
    public DcMotor frontLeftDrive = null;
    public DcMotor frontRightDrive = null;
    public DcMotor backLeftDrive = null;
    public DcMotor backRightDrive = null;

    public Servo clawLeft = null;
    public Servo clawRight = null;

    public DcMotor arm = null; // Experimental for ArmTest class
    public DcMotor rise = null; // Experimental for ArmTest class


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

        clawLeft            = hwMap.get(Servo.class,   "CL");
        clawRight           = hwMap.get(Servo.class,   "CR");

        frontLeftDrive. setDirection(DcMotor.Direction.FORWARD); // Set to REVERSE if using AndyMark motors
        frontRightDrive.setDirection(DcMotor.Direction.REVERSE); // Set to FORWARD if using AndyMark motors
        backLeftDrive.  setDirection(DcMotor.Direction.FORWARD); // Set to REVERSE if using AndyMark motors
        backRightDrive. setDirection(DcMotor.Direction.REVERSE); // Set to FORWARD if using AndyMark motors

        // Initialize the arm motor.
        arm = hwMap.get(DcMotor.class, "MS_KOL");
        arm.setDirection(DcMotor.Direction.FORWARD);
        arm.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        rise = hwMap.get(DcMotor.class, "RRISE");
        rise.setDirection(DcMotor.Direction.FORWARD);
        rise.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        clawLeft.setPosition(0.8);
        clawRight.setPosition(0.2);
    }
}
