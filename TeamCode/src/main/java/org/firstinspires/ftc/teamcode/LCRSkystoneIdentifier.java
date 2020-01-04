package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import java.util.HashMap;

import detectors.FoundationPipeline.Pipeline;

@TeleOp(name = "LCR Identifier", group = "Auto")
public class LCRSkystoneIdentifier extends LinearOpMode2 {

//    private DogeCVDetector detector = new DogeCVDetector() {
//        @Override
//        public Mat process(Mat rgba) {
//
//        	/*
//        	    Here you can specify which elements are being detected.
//        	    At the moment, SkyStones are super-reliable, Individual stones can be detected if you set up
//        	    the camera right, and Foundations...need work.
//
//        	 */
//            Pipeline.doFoundations=false;
//            Pipeline.doStones=false;
//            Pipeline.doSkyStones=true;
//
//
//            Mat m = Pipeline.process(rgba);
//
//            telemetry.update();
//
//            Imgproc.resize(m, m, new Size(640*1.3, 480*1.3 ));
//            return m;
//        }
//        @Override
//        public void useDefaults() {}
//    };

    @Override
    public void runOpMode() {
        HashMap<String, Integer> votes = new HashMap<>();
        votes.put("Null", 0);
        votes.put("Left", 0);
        votes.put("Center", 0);
        votes.put("Right", 0);

        waitForStart();

        double time_init = time;

        while (time - time_init < 5) {
            votes.put(skystoneCategory(), votes.get(skystoneCategory()) + 1);
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        telemetry.addData("Null: ", votes.get("Null"));
        telemetry.addData("Left: ", votes.get("Left"));
        telemetry.addData("Center: ", votes.get("Center"));
        telemetry.addData("Right: ", votes.get("Right"));
        telemetry.update();

        sleep(10000);
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
