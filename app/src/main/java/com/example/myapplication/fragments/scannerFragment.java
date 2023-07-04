package com.example.myapplication.fragments;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import com.example.myapplication.R;

import java.io.IOException;

public class scannerFragment extends Fragment {

    TextView textView;
    TextView barcodeTextView;
    private static final int CAMERA_PERMISSION_REQUEST_CODE = 200;
    private SurfaceView surfaceView;
    private CameraSource cameraSource;
    String barcode;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View viewP = inflater.inflate(R.layout.fragment_scanner, container, false);

        Button btn = viewP.findViewById(R.id.btn);
        barcodeTextView = viewP.findViewById(R.id.barcodeTextView);
        textView = viewP.findViewById(R.id.text);
        surfaceView = viewP.findViewById(R.id.surfaceView);

        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.CAMERA)
                == PackageManager.PERMISSION_GRANTED) {
            startCameraSource();
        } else {
            requestCameraPermission();
        }
        btn.setOnClickListener(v -> {
            MyAsyncTask myAsyncTask = new MyAsyncTask();
            myAsyncTask.execute(barcodeTextView.getText().toString());
        });

        return viewP;
    }

    private void requestCameraPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), Manifest.permission.CAMERA)) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CAMERA},
                    CAMERA_PERMISSION_REQUEST_CODE);
        } else {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CAMERA},
                    CAMERA_PERMISSION_REQUEST_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == CAMERA_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                startCameraSource();
            } else {
                System.out.println("Требуется разрешение камеры");
            }
        }
    }
    private void startCameraSource() {
        BarcodeDetector barcodeDetector = new BarcodeDetector.Builder(getContext())
                .setBarcodeFormats(Barcode.EAN_13)
                .build();

        cameraSource = new CameraSource.Builder(getContext(), barcodeDetector)
                .setAutoFocusEnabled(true)
                .build();

        surfaceView.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder surfaceHolder) {
                try {
                    if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.CAMERA)
                            != PackageManager.PERMISSION_GRANTED) {
                        return;
                    }
                    cameraSource.start(surfaceView.getHolder());
                } catch (IOException e) {
                    Log.e("CameraSource", "Не удается запустить камеру.", e);
                }
            }

            @Override
            public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {
            }

            @Override
            public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
                cameraSource.stop();
            }
        });

        barcodeDetector.setProcessor(new Detector.Processor<Barcode>() {
            @Override
            public void release() {
            }

            @Override
            public void receiveDetections(Detector.Detections<Barcode> detections) {
                final SparseArray<Barcode> barcodes = detections.getDetectedItems();
                if (barcodes.size() > 0) {
                    barcode = barcodes.valueAt(0).displayValue;
                    barcodeTextView.setText(barcode);

                }
            }
        });
    }
    static String getItemName(String ean13){
        Document doc = null;
        try {
            doc = Jsoup.connect("https://barcode-list.ru/barcode/RU/%D0%9F%D0%BE%D0%B8%D1%81%D0%BA.htm?barcode=" + ean13).get();
            Element itemNameElement = doc.select("tr.even:nth-child(2) > td:nth-child(3)").get(0);
            String rawText = itemNameElement.text();
            return rawText;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public class MyAsyncTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... strings) {
            getItemName(strings[0]);

            return getItemName(strings[0]);
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            textView.setText(result);
        }
    }

}