package org.firstinspires.ftc.teamcode;

import com.disnodeteam.dogecv.DogeCV;
import com.disnodeteam.dogecv.detectors.skystone.SkystoneDetector;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraRotation;
import org.openftc.easyopencv.OpenCvInternalCamera;

import java.util.HashMap;
import java.util.Map;


@Autonomous(name="BlankCV Test", group="Experimental")
public class BlankCVTest extends LinearOpMode {
    Hardware robot = new Hardware();
    private OpenCvCamera phoneCam;
    BlankDetector blankDetector = new BlankDetector();

    @Override
    public void runOpMode() {
        robot.init(hardwareMap);

        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        phoneCam = new OpenCvInternalCamera(OpenCvInternalCamera.CameraDirection.BACK, cameraMonitorViewId);

        /*
         * Open the connection to the camera device
         */
        phoneCam.openCameraDevice();

        /*
         * Specify the image processing pipeline we wish to invoke upon receipt
         * of a frame from the camera. Note that switching pipelines on-the-fly
         * (while a streaming session is in flight) *IS* supported.
         */
        phoneCam.setPipeline(blankDetector);

        /*
         * Tell the camera to start streaming images to us! Note that you must make sure
         * the resolution you specify is supported by the camera. If it is not, an exception
         * will be thrown.
         *
         * Also, we specify the rotation that the camera is used in. This is so that the image
         * from the camera sensor can be rotated such that it is always displayed with the image upright.
         * For a front facing camera, rotation is defined assuming the user is looking at the screen.
         * For a rear facing camera or a webcam, rotation is defined assuming the camera is facing
         * away from the user.
         */
        //phoneCam.startStreaming(320, 240, OpenCvCameraRotation.SIDEWAYS_RIGHT);
        phoneCam.startStreaming(100, 100, OpenCvCameraRotation.UPRIGHT);

        waitForStart();

        double time_init = time;

        blankDetector.areaScoringMethod = DogeCV.AreaScoringMethod.PERFECT_AREA;

        double placeholderSkystoneX;
        double placeholderSkystoneBoxWidth;
        double placeholderSkystoneBoxHeight;
        double placeholderSkystoneBoxRatio;

        Map<String, Integer> xCoordinateCount = new HashMap();
        xCoordinateCount.put("Left", 0);
        xCoordinateCount.put("Center", 0);
        xCoordinateCount.put("Right", 0);

        //skyStoneDetector.

        while (time - time_init < 1) {

            placeholderSkystoneX = blankDetector.getScreenPosition().x;
            placeholderSkystoneBoxWidth = blankDetector.foundRectangle().width;
            placeholderSkystoneBoxHeight = blankDetector.foundRectangle().height;
            placeholderSkystoneBoxRatio = placeholderSkystoneBoxWidth / placeholderSkystoneBoxHeight;

            if ((placeholderSkystoneX > 0) /*&& (placeholderSkystoneBoxRatio > 1.5)*/ && (placeholderSkystoneBoxRatio < 3)) {
                if (placeholderSkystoneX < 60) {
                    xCoordinateCount.put("Left", xCoordinateCount.get("Left") + 1);
                } else if (placeholderSkystoneX < 180) {
                    xCoordinateCount.put("Center", xCoordinateCount.get("Center") + 1);
                } else {
                    xCoordinateCount.put("Right", xCoordinateCount.get("Right") + 1);
                }
            }
            sleep(40);

            /*
            //old stuff for this section
            x_sum += skyStoneDetector.getScreenPosition().x;
            occurrences += 1;
            sleep(40);
            */
        }

        // Maximum x is 320. Biased towards right because x value is always top left.

        if (xCoordinateCount.get("Left") >= xCoordinateCount.get("Center") &&
                xCoordinateCount.get("Left") >= xCoordinateCount.get("Right")) {
            telemetry.addData("Skystone: ", "Left");
        } else if (xCoordinateCount.get("Center") >= xCoordinateCount.get("Left") &&
                xCoordinateCount.get("Center") >= xCoordinateCount.get("Right")) {
            telemetry.addData("Skystone: ", "Center");
        } else {
            telemetry.addData("Skystone: ", "Right");
        }

        telemetry.addData("Skystone: ", xCoordinateCount.get("Left"));
        telemetry.addData("Skystone: ", xCoordinateCount.get("Center"));
        telemetry.addData("Skystone: ", xCoordinateCount.get("Right"));

        telemetry.update();
        sleep(10000);
    }
}
