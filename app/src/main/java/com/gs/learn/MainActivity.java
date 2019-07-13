package com.gs.learn;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Process;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.gs.learn.animation.AnimationMainActivity;
import com.gs.learn.custom.CustomMainActivity;
import com.gs.learn.device.DeviceMainActivity;
import com.gs.learn.event.EventMainActivity;
import com.gs.learn.group.GroupMainActivity;
import com.gs.learn.junior.JuniorMainActivity;
import com.gs.learn.media.MediaMainActivity;
import com.gs.learn.middle.MiddleMainActivity;
import com.gs.learn.mixture.MixtureMainActivity;
import com.gs.learn.network.NetworkMainActivity;
import com.gs.learn.performance.PerformanceMainActivity;
import com.gs.learn.senior.SeniorMainActivity;
import com.gs.learn.storage.StorageMainActivity;
import com.gs.learn.test.TestMainActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class MainActivity extends AppCompatActivity {
    private List<ChapterItem> ChapterList = new ArrayList<>();
    private static final String TAG = "MainActivity";
    private String[] mPermission = new String[] {
            Manifest.permission.INTERNET,
            Manifest.permission.CAMERA,
            Manifest.permission.RECORD_AUDIO,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.READ_CONTACTS,
            Manifest.permission.LOCATION_HARDWARE,
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.CALL_PHONE,
            Manifest.permission.READ_CALL_LOG,
            Manifest.permission.ADD_VOICEMAIL,
            Manifest.permission.USE_SIP,
            Manifest.permission.BODY_SENSORS,
            Manifest.permission.READ_SMS,
            Manifest.permission.RECEIVE_SMS,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
    };
    private List<String> mRequestPermission = new ArrayList<String>();
    public static int PERMISSION_REQ = 0x666;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //初始化数据
        initChapter();
        ChapterAdapter adapter = new ChapterAdapter(MainActivity.this, R.layout.chapter_item, ChapterList);
        ListView listView = (ListView) findViewById(R.id.list_view);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                ChapterItem chapterItem = ChapterList.get(position);
                Log.d(TAG,"chapter:" + chapterItem.getName());
                Intent intent = new Intent(MainActivity.this, chapterItem.getCls());
                startActivity(intent);
            }
        });

        //权限设置
        setPermission();
    }

    void initChapter(){
        ChapterItem chapterItem = new ChapterItem("初级控件", JuniorMainActivity.class);
        ChapterList.add(chapterItem);
        chapterItem = new ChapterItem("中级控件",MiddleMainActivity.class);
        ChapterList.add(chapterItem);
        chapterItem = new ChapterItem("高级控件",SeniorMainActivity.class);
        ChapterList.add(chapterItem);
        chapterItem = new ChapterItem("数据存储",StorageMainActivity.class);
        ChapterList.add(chapterItem);
        chapterItem = new ChapterItem("自定义控件",CustomMainActivity.class);
        ChapterList.add(chapterItem);
        chapterItem = new ChapterItem("组合控件",GroupMainActivity.class);
        ChapterList.add(chapterItem);
        chapterItem = new ChapterItem("调试与上线",TestMainActivity.class);
        ChapterList.add(chapterItem);
        chapterItem = new ChapterItem("设备操作",DeviceMainActivity.class);
        ChapterList.add(chapterItem);
        chapterItem = new ChapterItem("网络通信",NetworkMainActivity.class);
        ChapterList.add(chapterItem);
        chapterItem = new ChapterItem("事件",EventMainActivity.class);
        ChapterList.add(chapterItem);
        chapterItem = new ChapterItem("动画",AnimationMainActivity.class);
        ChapterList.add(chapterItem);
        chapterItem = new ChapterItem("多媒体",MediaMainActivity.class);
        ChapterList.add(chapterItem);
        chapterItem = new ChapterItem("融合技术",MixtureMainActivity.class);
        ChapterList.add(chapterItem);
        chapterItem = new ChapterItem("性能优化",PerformanceMainActivity.class);
        ChapterList.add(chapterItem);
    }

    private void setPermission(){
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            for (String one : mPermission) {
                if (PackageManager.PERMISSION_GRANTED != this.checkPermission(one, Process.myPid(), Process.myUid())) {
                    mRequestPermission.add(one);
                }
            }
            if (!mRequestPermission.isEmpty()) {
                this.requestPermissions(mRequestPermission.toArray(new String[mRequestPermission.size()]), PERMISSION_REQ);
                return ;
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions,  int[] grantResults) {
        // 版本兼容
        if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.M) {
            return;
        }
        if (requestCode == PERMISSION_REQ) {
            for (int i = 0; i < grantResults.length; i++) {
                for (String one : mPermission) {
                    if (permissions[i].equals(one) && grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                        mRequestPermission.remove(one);
                    }
                }
            }
        }
    }
}
