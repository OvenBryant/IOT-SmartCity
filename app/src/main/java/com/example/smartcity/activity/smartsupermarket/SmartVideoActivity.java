//package com.example.smartcity.activity.smartsupermarket;
//
//
//import android.content.Intent;
//import android.content.SharedPreferences;
//import android.os.Bundle;
//import android.os.Environment;
//import android.util.Log;
//import android.view.Menu;
//import android.view.MenuItem;
//import android.view.MotionEvent;
//import android.view.TextureView;
//import android.view.View;
//
//import androidx.annotation.NonNull;
//import androidx.appcompat.app.AppCompatActivity;
//
//import com.example.smartcity.R;
//
//import nledu.com.ipcamera.CameraManager;
//import nledu.com.ipcamera.PTZ;
//
//public class SmartVideoActivity extends AppCompatActivity implements View.OnTouchListener {
//
//    private View layer;
//    private TextureView textureView;
//    private CameraManager cameraManager;
//    private String user="";
//    private String pwd="";
//    private String ip="";
//    private String channel="";
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.smart_video_layout);
//
//
//        SharedPreferences camera = getSharedPreferences("Camera", MODE_PRIVATE);
//        if(camera.getBoolean("flag",false))
//        {
//            user = camera.getString("user", "admin");
//            pwd = camera.getString("pwd", "admin");
//            ip = camera.getString("id", "id");
//            channel = camera.getString("channel", "1");
//        }
//
//
//        layer = findViewById(R.id.temp);
//
//        //register event
//        layer = findViewById(R.id.temp);
//        textureView = findViewById(R.id.svCamera);
//        findViewById(R.id.up).setOnTouchListener(this);
//        findViewById(R.id.left).setOnTouchListener(this);
//        findViewById(R.id.right).setOnTouchListener(this);
//        findViewById(R.id.down).setOnTouchListener(this);
//
//        initCameraManager(user,pwd,ip,channel);
//    }
//
//    private void initCameraManager(String _user,String _pwd,String _ip,String _channel) {
//        cameraManager = CameraManager.getInstance();
//        cameraManager.setupInfo(textureView, _user, _pwd, _ip, _channel);
//    }
//
//    /**
//     * 打开摄像头
//     * @param view
//     */
//    public void open(View view)  {
//        layer.setVisibility(View.GONE);
//        initCameraManager(user,pwd,ip,channel);
//        cameraManager.openCamera();
//    }
//
//    /**
//     * 关闭摄像头
//     * @param view
//     */
//    public void release(View view){
//        if (cameraManager != null){
//            cameraManager.releaseCamera();
//        }
//        layer.setVisibility(View.VISIBLE);
//    }
//
//    @Override
//    public boolean onTouch(View arg0, MotionEvent arg1) {
//        int action = arg1.getAction();
//        PTZ ptz = null;
//        if (action == MotionEvent.ACTION_CANCEL || action == MotionEvent.ACTION_UP) {
//            ptz = PTZ.Stop;
//        } else if (action == MotionEvent.ACTION_DOWN) {
//            int viewId = arg0.getId();
//            switch (viewId) {
//                case R.id.up:
//                    ptz = PTZ.Up;
//                    break;
//                case R.id.down:
//                    ptz = PTZ.Down;
//                    break;
//                case R.id.left:
//                    ptz = PTZ.Left;
//                    break;
//                case R.id.right:
//                    ptz = PTZ.Right;
//                    break;
//            }
//        }
//        cameraManager.controlDir(ptz);
//        return false;
//    }
//
//    /**
//     * 截图
//     * @param view
//     */
//    public void capture(View view) {
//        Log.i("path----->", Environment.getExternalStorageDirectory().getPath());
//        //cameraManager.capture(Environment.getExternalStorageDirectory().getPath(), "abc.png");
//    }
//
//    /**
//     * 生成上下文菜单 系统提供
//     *
//     * @param menu
//     * @return
//     */
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.menu_vidio_configure, menu);
//        return true;
//    }
//
//    /**
//     * 响应上下文菜单项
//     *
//     * @param item
//     * @return
//     */
//    @Override
//    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
//        switch (item.getItemId()) {
//            case R.id.video_configure:
//                Intent intent = new Intent(this,SmartVideoConfigureActivity.class);
//                startActivity(intent);
//                break;
//        }
//        return true;
//    }
//}