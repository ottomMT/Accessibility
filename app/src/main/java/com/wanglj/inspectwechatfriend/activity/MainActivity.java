package com.wanglj.inspectwechatfriend.activity;

import android.accessibilityservice.AccessibilityService;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.accessibility.AccessibilityManager;
import android.widget.Button;
import android.widget.Toast;

import com.wanglj.inspectwechatfriend.R;
import com.wanglj.inspectwechatfriend.utils.Utils;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

public class MainActivity extends AppCompatActivity {
    public static final String LauncherUI = "com.tencent.mm.ui.LauncherUI";
    public static final String MM = "com.tencent.mm";  //微信的包名

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Log.d("MainActivity", Utils.getVersion(this));

        //打开辅助功能
        findViewById(R.id.openSetting).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        //开始检查
        findViewById(R.id.start).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                  //实例化accessibilityManager
                AccessibilityManager accessibilityManager = (AccessibilityManager) getSystemService(ACCESSIBILITY_SERVICE);

                accessibilityManager.addAccessibilityStateChangeListener(new AccessibilityManager.AccessibilityStateChangeListener() {
                    @Override
                    public void onAccessibilityStateChanged(boolean b) {
                        if(b){
                            Intent intent = new Intent();
                            intent.setFlags(FLAG_ACTIVITY_NEW_TASK);
                            intent.setClassName(MM, LauncherUI);
                            startActivity(intent);
                        }else{
                            try {
                                //打开系统设置中辅助功能
                                Intent intent = new Intent(android.provider.Settings.ACTION_ACCESSIBILITY_SETTINGS);
                                startActivity(intent);
                                Toast.makeText(MainActivity.this, "找到检测被删好友辅助，然后开启服务即可", Toast.LENGTH_LONG).show();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                });

                if(!accessibilityManager.isEnabled()){
                    try {
                        //打开系统设置中辅助功能
                        Intent intent = new Intent(android.provider.Settings.ACTION_ACCESSIBILITY_SETTINGS);
                        startActivity(intent);
                        Toast.makeText(MainActivity.this, "找到检测被删好友辅助，然后开启服务即可", Toast.LENGTH_LONG).show();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }else{
                    Intent intent = new Intent();
                    // 设置此状态，记住以下原则，首先会查找是否存在和被启动的Activity具有相同的亲和性的任务栈
                    // （即taskAffinity，注意同一个应用程序中的activity的亲和性一样，所以下面的a情况会在同一个栈中，前面这句话有点拗口，请多读几遍），
                    // 如果有，刚直接把这个栈整体移动到前台，并保持栈中的状态不变，即栈中的activity顺序不变，如果没有，则新建一个栈来存放被启动的activity
                    intent.setFlags(FLAG_ACTIVITY_NEW_TASK);
                    intent.setClassName(MM, LauncherUI);
                    startActivity(intent);
                }



            }
        });

        final Button button = (Button) findViewById(R.id.getCount);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, DeleteFriendListActivity.class));
            }
        });
    }
}
