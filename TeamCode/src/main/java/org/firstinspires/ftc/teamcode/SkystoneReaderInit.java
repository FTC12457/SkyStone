package org.firstinspires.ftc.teamcode;

import org.firstinspires.ftc.robotcore.external.Telemetry;

import java.util.HashMap;

import detectors.FoundationPipeline.Pipeline;

/*
HOW TO IMPLEMENT: Declare an instance of the class, then call method run() on initialization. The
method should terminate on start (you might actually be able -- or need -- to get rid of the
waitForStart(), since both have the same while loop). After starting, use placement() as the
function returning skystone positions, as in the SkystoneReader class.
 */

public class SkystoneReaderInit {

    LinearOpMode2 opMode;
    Telemetry telemetryInstance;
    String teamColor;

    HashMap<Integer, String> votes = new HashMap<>();

    public SkystoneReaderInit(String color, LinearOpMode2 opMode2, Telemetry telemetry) {

        teamColor = color;
        opMode = opMode2;
        telemetryInstance = telemetry;

    }

    public void run() {

        int cycle = 0;
        double time_init = opMode.time;

        while (!opMode.isStarted()) {
            votes.put(cycle, skystoneCategory());
            if (opMode.time >= time_init + 1) {
                cycle = 0;
                time_init = opMode.time;
            } else cycle += 1;
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    // Notice how the placement defaults on Right. This is important, since Right is the most
    // difficult to recognize, due to lack of contrasting yellow.

    public int placement() {
        int left = 0;
        int center = 0;
        int right = 0;

        int i = 0;
        while (i <= votes.keySet().size()) {
            if (votes.get(i).equals("Left")) left += 1;
            if (votes.get(i).equals("Center")) center += 1;
            if (votes.get(i).equals("Right")) right += 1;
            i += 1;
        }

        if (teamColor.equals("Red")) {
            if (left > center && left > right) {
                return 0;
            } else if (center > right) {
                return 1;
            } else {
                return 2;
            }
        } else {
            if (left > center && left > right) {
                return 2;
            } else if (center > right) {
                return 1;
            } else {
                return 0;
            }
        }
    }

    public static void clean(){
        System.gc();
    }

    public String skystoneCategory() {
        if ("Blue".equals(teamColor)) {
            if (!Pipeline.skyStones.isEmpty()) {
                if (Pipeline.skyStones.get(0).y < 260) return "Left";
                else if (Pipeline.skyStones.get(0).y < 380) return "Center";
                else return "Right";
            } else {
                return "Null";
            }
        } else {
            if (!Pipeline.skyStones.isEmpty()) {
                if (Pipeline.skyStones.get(0).y < 160) return "Left";
                else if (Pipeline.skyStones.get(0).y < 320) return "Center";
                else return "Right";
            } else {
                return "Null";
            }
        }
    }
}
