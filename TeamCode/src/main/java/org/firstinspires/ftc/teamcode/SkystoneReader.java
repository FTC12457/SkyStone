package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcore.external.Telemetry;

import java.util.HashMap;

import detectors.FoundationPipeline.Pipeline;

public class SkystoneReader{

    LinearOpMode2 opMode;
    Telemetry telemetryInstance;

    HashMap<String, Integer> votes = new HashMap<>();

    public SkystoneReader(LinearOpMode2 opMode2, Telemetry telemetry) {
        opMode = opMode2;
        telemetryInstance = telemetry;

        votes.put("Null", 0);
        votes.put("Left", 0);
        votes.put("Center", 0);
        votes.put("Right", 0);
    }

    public void run() {

        double time_init = opMode.time;

        while (opMode.time - time_init < 5) {
            votes.put(skystoneCategory(), votes.get(skystoneCategory()) + 1);
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        telemetryInstance.addData("Null: ", votes.get("Null"));
        telemetryInstance.addData("Left: ", votes.get("Left"));
        telemetryInstance.addData("Center: ", votes.get("Center"));
        telemetryInstance.addData("Right: ", votes.get("Right"));
        telemetryInstance.update();
    }

    // Notice how the placement defaults on Right. This is important, since Right is the most
    // difficult to recognize, due to lack of contrasting yellow.

    public int placement() {
        if (votes.get("Left") > votes.get("Center") && votes.get("Left") > votes.get("Right")) {
            return 0;
        } else if (votes.get("Center") > votes.get("Right")) {
            return 1;
        } else {
            return 2;
        }
    }

    public static void clean(){
        System.gc();
    }

    public static String skystoneCategory() {
        if (!Pipeline.skyStones.isEmpty()) {
            if (Pipeline.skyStones.get(0).y < 160) return "Left";
            else if (Pipeline.skyStones.get(0).y < 320) return "Center";
            else return "Right";
        } else {
            return "Null";
        }
    }
}
