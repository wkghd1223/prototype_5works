package com.example.ex.ui.camera;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.ex.NavActivity;
import com.example.ex.R;

import org.opencv.android.BaseLoaderCallback;
import org.opencv.android.CameraBridgeViewBase;
import org.opencv.android.LoaderCallbackInterface;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.Semaphore;

public class CameraFragment extends Fragment
        implements CameraBridgeViewBase.CvCameraViewListener2 , View.OnClickListener{

    private CameraViewModel cameraviewModel;
    private Context context = getContext();
    public static CameraFragment newInstance() {
        return new CameraFragment();
    }

    static {
        System.loadLibrary("opencv_java4");
        System.loadLibrary("native-lib");
    }

    private static final String TAG = "opencv";

    private static final String FACE ="haarcascade_frontalface_alt2.xml";
    private static final String EYE ="haarcascade_eye_tree_eyeglasses.xml";
    private String lenze = "default_lenze";

    public long cascadeClassifier_face = 0;
    public long cascadeClassifier_eye = 0;

    final String folder = "5works";

    static final int PERMISSIONS_REQUEST_CODE = 1000;
    String[]  PERMISSIONS = {"android.permission.CAMERA",
            "android.permission.WRITE_EXTERNAL_STORAGE"};

    private Mat matInput;
    private Mat matResult;
    private int mMode = 1;

    public native long loadCascade(String cascadeFileName);
    public native void detect(long cascadeClassifier_face, long cascadeClassifier_eye, long matAddrInput , long matAddrResult ,String lenzeName);

    private void copyFile(String filename) {
        String baseDir = Environment.getExternalStorageDirectory().getPath();
        String pathDir = baseDir + File.separator + filename;

        AssetManager assetManager = getContext().getAssets();

        InputStream inputStream = null;
        OutputStream outputStream = null;

        try {
            Log.d( TAG, "copyFile :: 다음 경로로 파일복사 "+ pathDir);
            inputStream = assetManager.open(filename);
            outputStream = new FileOutputStream(pathDir);

            byte[] buffer = new byte[1024];
            int read;
            while ((read = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, read);
            }
            inputStream.close();
            inputStream = null;
            outputStream.flush();
            outputStream.close();
            outputStream = null;
        } catch (Exception e) {
            Log.d(TAG, "copyFile :: 파일 복사 중 예외 발생 "+e.toString() );
        }

    }
    private void read_cascade_file(){
        //copyFile 메소드는 Assets에서 해당 파일을 가져와
        //외부 저장소 특정위치에 저장하도록 구현된 메소드입니다.
        copyFile(FACE);
        copyFile(EYE);
        copyFile(lenze);

        //loadCascade 메소드는 외부 저장소의 특정 위치에서 해당 파일을 읽어와서
        //CascadeClassifier 객체로 로드합니다.
        cascadeClassifier_face = loadCascade(FACE);
        cascadeClassifier_eye = loadCascade(EYE);

    }

    private CameraBridgeViewBase mOpenCvCameraView;
    private BaseLoaderCallback mLoaderCallback = new BaseLoaderCallback(context) {
        @Override
        public void onManagerConnected(int status) {
            switch (status) {
                case LoaderCallbackInterface.SUCCESS:
                {
                    mOpenCvCameraView.enableView();
                } break;
                default:
                {
                    super.onManagerConnected(status);
                } break;
            }
        }
    };

    private final Semaphore writeLock = new Semaphore(1);

    // 세마포어를 aquire, release 하는 함수
    public void getWriteLock() throws InterruptedException {
        writeLock.acquire();
    }
    public void releaseWriteLock() {
        writeLock.release();
    }
    // opencv와 native 라이브러리를 사용한다는 선언
    static {
        System.loadLibrary("opencv_java4");
        System.loadLibrary("native-lib");
    }

    // 초기 설정 : 퍼미션 상태 및 렌즈 이미지 다운로드 ( res/asset -> 내부 저장소 )
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            //퍼미션 상태 확인
            if (!hasPermissions(PERMISSIONS)) {

                //퍼미션 허가 안되어있다면 사용자에게 요청
                requestPermissions(PERMISSIONS, PERMISSIONS_REQUEST_CODE);
            }
            else  read_cascade_file();
        }
        else  read_cascade_file();
    }

    // 레이아웃 컨텐츠들을 변수와 매핑
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        cameraviewModel = ViewModelProviders.of(this).get(CameraViewModel.class);
        View root = inflater.inflate(R.layout.camera_fragment, container, false);

        mOpenCvCameraView = root.findViewById(R.id.activity_surface_view);

        mOpenCvCameraView.setVisibility(SurfaceView.VISIBLE);
        mOpenCvCameraView.setCvCameraViewListener(this);
        mOpenCvCameraView.setCameraIndex(mMode); // front-camera(1),  back-camera(0)
        mLoaderCallback.onManagerConnected(LoaderCallbackInterface.SUCCESS);

        ImageButton capture = root.findViewById(R.id.imageButton);
        capture.setOnClickListener(this);

        Button defult_btn = root.findViewById(R.id.default_lenze);
        Button lenze1 = root.findViewById(R.id.lenze1_jpg);

        defult_btn.setOnClickListener(cameraviewModel);
        lenze1.setOnClickListener(cameraviewModel);

        // lenze변수의 값을 버튼의 눌림에따라 바뀌게한다.
        cameraviewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                lenze = s;
            }
        });
        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        cameraviewModel = ViewModelProviders.of(this).get(CameraViewModel.class);
        // TODO: Use the ViewModel
    }

    @Override
    public void onCameraViewStarted(int width, int height) {

    }

    @Override
    public void onCameraViewStopped() {

    }

    @Override
    public Mat onCameraFrame(CameraBridgeViewBase.CvCameraViewFrame inputFrame) {
        try {
            getWriteLock(); //세마포어
            matInput = inputFrame.rgba(); // 화면의 이미지를 담는다.

            if (matResult == null)
                matResult = new Mat(matInput.rows(), matInput.cols(), matInput.type()); // matResult에 matInput의 이미지를 담는다

            if(mMode == 1) Core.flip(matInput, matInput, 1); // 전면카메라 시 뒤집힘 현상 때문에 뒤집어 준다.

            Core.rotate(matInput, matInput, Core.ROTATE_90_CLOCKWISE); // 전면카메라 회전현상
            detect(cascadeClassifier_face,cascadeClassifier_eye, matInput.getNativeObjAddr(), matResult.getNativeObjAddr(), lenze); // 얼굴 및 얼굴 검출 코드
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        releaseWriteLock();

        return matResult;
    }

    //캡쳐버튼의 리스너 구현
    @Override
    public void onClick(View view) {
        try {
            getWriteLock();

            File path = new File(Environment.getExternalStorageDirectory(),folder);
            if(!path.exists()){
                path.mkdirs();
            }

            SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
            // 년월일시분초
            Date currentTime_1 = new Date();
            String dateString = formatter.format(currentTime_1);

            File file = new File(path, dateString + ".jpg");

            String filename = file.toString();

            Imgproc.cvtColor(matResult, matResult, Imgproc.COLOR_BGR2RGBA);
            boolean ret  = Imgcodecs.imwrite( filename, matResult);

            if ( ret )
                Log.d(TAG, "SUCESS");
            else
                Log.d(TAG, "FAIL");

            Intent mediaScanIntent = new Intent( Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
            mediaScanIntent.setData(Uri.fromFile(file));
            context = getContext();
            context.sendBroadcast(mediaScanIntent);
            Toast.makeText(getActivity(),"사진을 캡쳐했습니다.",Toast.LENGTH_LONG).show();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        releaseWriteLock();
    }
    // 퍼미션 함수들
    private boolean hasPermissions(String[] permissions) {
        int result;

        //스트링 배열에 있는 퍼미션들의 허가 상태 여부 확인
        for (String perms : permissions){

            result = ContextCompat.checkSelfPermission(getContext(), perms);

            if (result == PackageManager.PERMISSION_DENIED){
                //허가 안된 퍼미션 발견
                return false;
            }
        }
        //모든 퍼미션이 허가되었음
        return true;
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch(requestCode){
            case PERMISSIONS_REQUEST_CODE:
                if (grantResults.length > 0) {
                    boolean cameraPermissionAccepted = grantResults[0]
                            == PackageManager.PERMISSION_GRANTED;

//                    if (!cameraPermissionAccepted)
//                        showDialogForPermission("앱을 실행하려면 퍼미션을 허가하셔야합니다.");

                    boolean writePermissionAccepted = grantResults[1]
                            == PackageManager.PERMISSION_GRANTED;
                    if (!cameraPermissionAccepted || !writePermissionAccepted) {
                        showDialogForPermission("앱을 실행하려면 퍼미션을 허가하셔야합니다.");
                        return;
                    }else
                    {
                        read_cascade_file();
                    }
                }
                break;
        }
    }
    @TargetApi(Build.VERSION_CODES.M)
    private void showDialogForPermission(String msg) {

        AlertDialog.Builder builder = new AlertDialog.Builder( getContext());
        builder.setTitle("알림");
        builder.setMessage(msg);
        builder.setCancelable(false);
        builder.setPositiveButton("예", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id){
                requestPermissions(PERMISSIONS, PERMISSIONS_REQUEST_CODE);
            }
        });
        builder.setNegativeButton("아니오", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface arg0, int arg1) {
                onDestroy();
            }
        });
        builder.create().show();
    }
}
