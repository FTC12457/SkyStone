package org.firstinspires.ftc.teamcode;

import com.disnodeteam.dogecv.DogeCV;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import com.disnodeteam.dogecv.DigitalCamera;
import com.disnodeteam.dogecv.detectors.skystone.SkystoneDetector;

import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraRotation;
import org.openftc.easyopencv.OpenCvInternalCamera;

import java.util.HashMap;
import java.util.Map;


@Autonomous(name="Doge CV Test", group="Experimental")
public class DogeCVTest extends LinearOpMode {
    Hardware robot = new Hardware();
    private OpenCvCamera phoneCam;
    SkystoneDetector skyStoneDetector = new SkystoneDetector();

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
        phoneCam.setPipeline(skyStoneDetector);

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
        phoneCam.startStreaming(320, 240, OpenCvCameraRotation.SIDEWAYS_RIGHT);

        waitForStart();

        double x_sum = 0;
        double time_init = time;
        int occurrences = 0;

        skyStoneDetector.areaScoringMethod = DogeCV.AreaScoringMethod.PERFECT_AREA;

        double placeholderSkystoneX;
        double placeholderSkystoneBoxWidth;
        double placeholderSkystoneBoxHeight;
        double placeholderSkystoneBoxRatio;

        Map<Integer, Integer> xCoordinateCount = new HashMap();

        while (time - time_init < 5) {

            placeholderSkystoneX = skyStoneDetector.getScreenPosition().x;
            placeholderSkystoneBoxWidth = skyStoneDetector.foundRectangle().width;
            placeholderSkystoneBoxHeight = skyStoneDetector.foundRectangle().height;
            placeholderSkystoneBoxRatio = placeholderSkystoneBoxWidth / placeholderSkystoneBoxHeight;

            if ((placeholderSkystoneX > 0) && (placeholderSkystoneBoxRatio > 1.5) && (placeholderSkystoneBoxRatio < 3)) {
                x_sum += placeholderSkystoneX;
                occurrences += 1;
            }
            sleep(40);

            Integer k = (int)2.5;
            if (xCoordinateCount.containsKey(k)) {
                xCoordinateCount.put(k, xCoordinateCount.get(k) + 1);
            } else{
                xCoordinateCount.put(k, 1);
            }

            /*
            //old stuff for this section
            x_sum += skyStoneDetector.getScreenPosition().x;
            occurrences += 1;
            sleep(40);
            */
        }

        double avg_x = x_sum / occurrences;

        // Maximum x is 320. Biased towards right because x value is always top left.

        if (avg_x < 100) {
            telemetry.addData("Skystone: ", "Left");
        } else if (avg_x < 170) {
            telemetry.addData("Skystone: ", "Center");
        } else {
            telemetry.addData("Skystone: ", "Right");
        }
        telemetry.addData("Average X: ", avg_x);

        telemetry.update();
        sleep(10000);
    }
}
