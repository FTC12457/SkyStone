package org.firstinspires.ftc.teamcode.robot;

public class Claw {
    Hardware robot;
    public Claw(Hardware hardware) {
        robot = hardware;
    }

    public void open() {
        robot.claw.setPosition(0.35
        );
    }

    public void someWhatOpen() { robot.claw.setPosition(0.7); }

    public void close() {
        robot.claw.setPosition(0.82);
    }

    public void run(Boolean control, Boolean someWhatOpen, Boolean open) {
        if (control) {
            close();
        } else if (open) {
            open();
        } else if (someWhatOpen) {
            someWhatOpen();
        }
    }
}
