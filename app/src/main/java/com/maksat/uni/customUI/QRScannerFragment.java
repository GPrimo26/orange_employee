package com.maksat.uni.customUI;

import android.Manifest;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.hardware.camera2.CameraManager;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.maksat.uni.MainActivity;
import com.maksat.uni.R;
import com.maksat.uni.fragments.BaseFragment;
import com.maksat.uni.fragments.home.HomeViewModel;

import java.io.IOException;
import java.util.Objects;

public class QRScannerFragment extends BaseFragment {
    public QRScannerFragment(MainActivity mainClass, HomeViewModel homeViewModel) {
        this.mainClass = mainClass;
        this.homeViewModel=homeViewModel;
    }

    private MainActivity mainClass;
    private SurfaceView surfaceView;
    private CameraSource cameraSource;
    private BarcodeDetector barcodeDetector;
    private Button exit_btn;
    private FloatingActionButton flashlight_fab;
    private int fl_flag = 0;
    private CameraManager mCameraManager;
    private String mCameraId;
    public android.hardware.Camera camera;
    private Camera.Parameters params;
    private HomeViewModel homeViewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requireActivity().getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                mainClass.fragmentManager.beginTransaction().hide(Objects.requireNonNull(mainClass.fragmentManager.findFragmentByTag("qrScanner"))).commit();
                mainClass.fragmentManager.beginTransaction().remove(Objects.requireNonNull(mainClass.fragmentManager.findFragmentByTag("qrScanner"))).commit();
            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.lo_qrscanner, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        DisplayMetrics metrics = new DisplayMetrics();
        requireActivity().getWindowManager().getDefaultDisplay().getMetrics(metrics);

        int height = metrics.heightPixels;
        int width = metrics.widthPixels;

        surfaceView = view.findViewById(R.id.camerapreview);

        /*ConstraintLayout.LayoutParams lp=(ConstraintLayout.LayoutParams) surfaceView.getLayoutParams();
        lp.bottomMargin=height-width;
        surfaceView.setLayoutParams(lp);*/
        exit_btn = view.findViewById(R.id.back_btn);
        flashlight_fab = view.findViewById(R.id.flashlight_fab);
        exit_btn.setOnClickListener(v -> {
            cameraSource.stop();
            mainClass.fragmentManager.beginTransaction().hide(Objects.requireNonNull(mainClass.fragmentManager.findFragmentByTag("qrScanner"))).commit();
            mainClass.fragmentManager.beginTransaction().remove(Objects.requireNonNull(mainClass.fragmentManager.findFragmentByTag("qrScanner"))).commit();
        });
        boolean isFlashAvailable = mainClass.getPackageManager()
                .hasSystemFeature(PackageManager.FEATURE_CAMERA_FRONT);

        barcodeDetector = new BarcodeDetector.Builder(requireContext())
                .setBarcodeFormats(Barcode.QR_CODE).build();
        com.maksat.uni.customUI.CameraSource.Builder builder = new com.maksat.uni.customUI.CameraSource.Builder(getContext(), barcodeDetector)
                .setFlashMode(Camera.Parameters.FLASH_MODE_OFF)
                .setFocusMode(Camera.Parameters.FOCUS_MODE_INFINITY)
                .setRequestedPreviewSize(width, height)
                .setFacing(CameraSource.CAMERA_FACING_BACK);
        cameraSource = builder.build();
        surfaceView.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                try {
                    if (ActivityCompat.checkSelfPermission(Objects.requireNonNull(getContext()), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                        // TODO: Consider calling
                        //    ActivityCompat#requestPermissions
                        // here to request the missing permissions, and then overriding
                        //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                        //                                          int[] grantResults)
                        // to handle the case where the user grants the permission. See the documentation
                        // for ActivityCompat#requestPermissions for more details.
                        return;
                    }
                    cameraSource.start(holder);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
                cameraSource.stop();
            }
        });
        barcodeDetector.setProcessor(new Detector.Processor<Barcode>() {
            @Override
            public void release() {

            }

            @Override
            public void receiveDetections(@NonNull Detector.Detections<Barcode> detections) {
                SparseArray<Barcode> qrCodes = detections.getDetectedItems();
                if (qrCodes.size() != 0) {
                    try {
                        homeViewModel.setParticipantId(Integer.parseInt(qrCodes.valueAt(0).displayValue));
                        cameraSource.stop();
                        mainClass.fragmentManager.beginTransaction().hide(Objects.requireNonNull(mainClass.fragmentManager.findFragmentByTag("rqScanner"))).commit();
                        mainClass.fragmentManager.beginTransaction().remove(Objects.requireNonNull(mainClass.fragmentManager.findFragmentByTag("rqScanner"))).commit();
                    }catch (Exception e){
                        Toast.makeText(requireContext(), "QR-код не содержит информацию о спортсмене", Toast.LENGTH_SHORT).show();
                    }
                    /*Vibrator vibrator = (Vibrator) getContext().getSystemService(Context.VIBRATOR_SERVICE);
                    vibrator.vibrate(100);*/
                }
            }
        });
        if (!isFlashAvailable) {
            Toast.makeText(getContext(), "На вашем устройстве не доступен фонарик", Toast.LENGTH_SHORT).show();
            flashlight_fab.setVisibility(View.GONE);
        }
        else {
            flashlight_fab.setOnClickListener(v -> {
                switch (fl_flag) {
                    case 1:
                        cameraSource.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
                        flashlight_fab.setImageResource(R.drawable.ic_flash_off);
                        fl_flag = 0;
                        break;
                    case 0:
                        cameraSource.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
                        flashlight_fab.setImageResource(R.drawable.ic_flash_on);
                        fl_flag = 1;
                        break;
                }
            });
        }

    }
}