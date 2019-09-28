package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class Hardware {
    public DcMotor frontLeftDrive = null;
    public DcMotor frontRightDrive = null;
    public DcMotor backLeftDrive = null;
    public DcMotor backRightDrive = null;

    public Servo clawLeft = null; //added by brian
    public Servo clawRight = null; //added by brian

    public DcMotor arm = null; // Experimental for ArmTest class

    HardwareMap hwMap = null;

    public Hardware()
    {
        // Constructor
    }

    public void init(HardwareMap ahwMap) {

        hwMap = ahwMap;

        // At convenience, change names and config of RL and RR to BL and BR, respectively.

        frontLeftDrive      = hwMap.get(DcMotor.class,    "FL");
        frontRightDrive     = hwMap.get(DcMotor.class,    "FR");
        backLeftDrive       = hwMap.get(DcMotor.class,    "RL");
        backRightDrive      = hwMap.get(DcMotor.class,    "RR");

        clawLeft = hwMap.get(Servo.class, "CL"); //added by brian
        clawRight = hwMap.get(Servo.class, "CR"); //added by brian

        frontLeftDrive. setDirection(DcMotor.Direction.FORWARD); // Set to REVERSE if using AndyMark motors
        frontRightDrive.setDirection(DcMotor.Direction.REVERSE); // Set to FORWARD if using AndyMark motors
        backLeftDrive.  setDirection(DcMotor.Direction.FORWARD); // Set to REVERSE if using AndyMark motors
        backRightDrive. setDirection(DcMotor.Direction.REVERSE); // Set to FORWARD if using AndyMark motors

        // Initialize the arm motor.
        arm = hwMap.get(DcMotor.class, "MS_KOL");
        arm.setDirection(DcMotor.Direction.FORWARD);

        clawLeft.setPower(0); //added by brian
        clawRight.setPower(0); //added by brian
    }
}
