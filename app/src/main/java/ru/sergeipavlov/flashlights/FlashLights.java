package ru.sergeipavlov.flashlights;

import android.content.Context;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;

public class FlashLights {
    private Context context;

    public FlashLights(Context context) {
        this.context = context;
    }

    public void lampOn() {
        try {
            CameraManager cm = (CameraManager)context.getSystemService(Context.CAMERA_SERVICE);
            String cameraID = cm.getCameraIdList()[0];
            cm.setTorchMode(cameraID, true);
        } catch (CameraAccessException e) {
            throw new RuntimeException(e);
        }
    }

    public void lampOff() {
        try {
            CameraManager cm = (CameraManager)context.getSystemService(Context.CAMERA_SERVICE);
            String cameraID = cm.getCameraIdList()[0];
            cm.setTorchMode(cameraID, false);
        } catch (CameraAccessException e) {
            throw new RuntimeException(e);
        }
    }

}
