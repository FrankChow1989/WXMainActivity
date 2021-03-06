package com.hb.qx;

import android.accessibilityservice.AccessibilityServiceInfo;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.provider.Settings;
import android.text.Html;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.view.accessibility.AccessibilityManager;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.util.List;

import in.srain.cube.request.FailData;
import in.srain.cube.request.JsonData;
import in.srain.cube.request.RequestJsonHandler;

public class SeniorActivity extends Activity
{
    private LinearLayout ly_check, ly_nokick, mTB_open, mOpenLock, mChatpage, mSreenLight, mVoise;
    private ToggleButton openService, openLock, chatPage, sreenLight, voise;
    private ImageView mBack;
    private TextView v_code;
    private String code_name;

    private SharedPreferences sp;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.senior_activity);
        init();
        initEvent();
    }

    private void initEvent()
    {
        // TODO Auto-generated method stub
        mBack.setOnClickListener(new OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                // TODO Auto-generated method stub
                SeniorActivity.this.finish();
            }
        });

        mTB_open.setOnClickListener(new OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if (openService.isChecked() == true)
                {
                    openService.setChecked(false);
                } else
                {
                    openService.setChecked(true);
                    Intent intent = new Intent(
                            Settings.ACTION_ACCESSIBILITY_SETTINGS);
                    startActivity(intent);
                }
            }
        });

        mOpenLock.setOnClickListener(new OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if (sp.getInt("share", 0) == 0)
                {
                    share();
                } else
                {
                    if (openLock.isChecked() == true)
                    {
                        openLock.setChecked(false);
                        editor.putInt("lock", 0);
                    } else
                    {
                        openLock.setChecked(true);
                        editor.putInt("lock", 1);
                    }
                    editor.commit();
                }
            }
        });

        mChatpage.setOnClickListener(new OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                if (sp.getInt("share", 0) == 0)
                {
                    share();
                } else
                {
                    if (chatPage.isChecked() == true)
                    {
                        chatPage.setChecked(false);
                        editor.putInt("chatpage", 0);
                    } else
                    {
                        chatPage.setChecked(true);
                        editor.putInt("chatpage", 1);
                    }
                    editor.commit();
                }
            }
        });

        mSreenLight.setOnClickListener(new OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                if (sp.getInt("share", 0) == 0)
                {
                    share();
                } else
                {
                    if (sreenLight.isChecked() == true)
                    {
                        sreenLight.setChecked(false);
                        editor.putInt("sreen", 0);
                    } else
                    {
                        chatPage.setChecked(true);
                        editor.putInt("sreen", 1);
                    }
                    editor.commit();
                }
            }
        });

        mVoise.setOnClickListener(new OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                if (sp.getInt("share", 0) == 0)
                {
                    share();
                } else
                {
                    if (voise.isChecked() == true)
                    {
                        voise.setChecked(false);
                        editor.putInt("voise", 0);
                    } else
                    {
                        voise.setChecked(true);
                        editor.putInt("voise", 1);
                    }
                    editor.commit();
                }
            }
        });

        ly_nokick.setOnClickListener(new OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(SeniorActivity.this, NoKickActivity.class);
                startActivity(intent);
            }
        });
    }

    private void share()
    {
        YqhyDialog1 yyDialog = new YqhyDialog1(SeniorActivity.this, 0);
        try
        {
            yyDialog.showAtLocation(SeniorActivity.this.getWindow()
                    .getDecorView(), Gravity.CENTER, 0, 0);
        } catch (Exception e)
        {
            if (yyDialog != null)
            {
                if (yyDialog.isShowing())
                {
                    yyDialog.dismiss();
                }
            }
        }
    }

    @Override
    protected void onStart()
    {
        // TODO Auto-generated method stub
        super.onStart();
        updateServiceStatus();
        System.out.println("cccccccccc" + sp.getInt("sreen", 0));
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        if (sp.getInt("chatpage", 0) == 1)
        {
            chatPage.setChecked(true);
        } else if (sp.getInt("chatpage", 0) == 0)
        {
            chatPage.setChecked(false);
        }

        if (sp.getInt("chatpage", 0) == 1)
        {
            chatPage.setChecked(true);
        } else if (sp.getInt("chatpage", 0) == 0)
        {
            chatPage.setChecked(false);
        }

        if (sp.getInt("sreen", 0) == 1)
        {
            sreenLight.setChecked(true);
        } else if (sp.getInt("sreen", 0) == 0)
        {
            sreenLight.setChecked(false);
        }

        if (sp.getInt("voise", 0) == 1)
        {
            voise.setChecked(true);
        } else if (sp.getInt("voise", 0) == 0)
        {
            voise.setChecked(false);
        }
    }

    private void init()
    {
        // TODO Auto-generated method stub
        sp = getSharedPreferences("chatpage", MODE_PRIVATE);
        editor = sp.edit();

        openService = (ToggleButton) findViewById(R.id.accessibility_open_tbutton);
        mBack = (ImageView) findViewById(R.id.img_senior_back);
        openLock = (ToggleButton) findViewById(R.id.ly_open_lock);
        chatPage = (ToggleButton) findViewById(R.id.accessibility_chatpage_tbutton);
        sreenLight = (ToggleButton) findViewById(R.id.accessibility_sceen_tbutton);
        voise = (ToggleButton) findViewById(R.id.accessibility_voicenotice_tbutton);
        ly_nokick = (LinearLayout) findViewById(R.id.id_nokick);
        ly_check = (LinearLayout) findViewById(R.id.id_check);

        mTB_open = (LinearLayout) findViewById(R.id.id_senior_openservice);
        mOpenLock = (LinearLayout) findViewById(R.id.id_senior_lock);
        mChatpage = (LinearLayout) findViewById(R.id.id_senior_chatpage);
        mSreenLight = (LinearLayout) findViewById(R.id.id_senior_sreenlight);
        mVoise = (LinearLayout) findViewById(R.id.id_senior_voice);


        v_code = (TextView) findViewById(R.id.id_v_code);
        code_name = HbApplication.instance.vsername;
        v_code.setText("版本：v" + code_name);

        ly_check = (LinearLayout) findViewById(R.id.id_check);
        ly_check.setOnClickListener(new OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                request();
            }
        });
    }

    public void request()
    {
        RequestUtil.reverseget(URL.UP_URL, new RequestJsonHandler()
        {
            @Override
            public void onRequestFinish(final JsonData data)
            {

                if (data != null)
                {
                    int vsercode = data.optInt("vsercode");
                    System.out.println("-----vsercode-----:" + vsercode);

                    if (vsercode > HbApplication.instance.vsercode)
                    {
                        View view = getLayoutInflater().inflate(
                                R.layout.dialog_update, null);
                        AlertDialogContainer container = new AlertDialogContainer(
                                SeniorActivity.this, view);
                        container.setNoText("否");
                        container.setOkText("是");
                        container.setTitle("更新通知");
                        TextView textView = (TextView) view
                                .findViewById(R.id.msg);
                        textView.setText(Html.fromHtml(data.optString("msg")));
                        container.setCallBack(new AlertDialogCallBack()
                        {
                            @Override
                            public boolean ok()
                            {
                                Intent intent = new Intent(SeniorActivity.this,
                                        UpdateService.class);
                                intent.putExtra("dowurl",
                                        data.optString("dowurl"));
                                startService(intent);
                                return true;
                            }

                            @Override
                            public boolean no()
                            {
                                return true;
                            }
                        });
                    } else
                    {

                        System.out.println("-----vsercode-----:" + vsercode);
                        Toast.makeText(getApplicationContext(),
                                "已是最新版本，当版本" + HbApplication.instance.vsername,
                                Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onRequestFail(FailData failData)
            {
            }
        });
    }

    @SuppressLint("NewApi")
    private void updateServiceStatus()
    {
        try
        {
            boolean serviceEnabled = false;
            AccessibilityManager accessibilityManager = (AccessibilityManager) getSystemService(Context.ACCESSIBILITY_SERVICE);
            System.out.println("accessibilityManager============================" + accessibilityManager);
            List<AccessibilityServiceInfo> accessibilityServices = accessibilityManager
                    .getEnabledAccessibilityServiceList(AccessibilityServiceInfo.FEEDBACK_GENERIC);
            for (AccessibilityServiceInfo info : accessibilityServices)
            {
                if (info.getId() != "")
                {
                    if (info.getId().equals(
                            getPackageName() + "/.QQHongbaoService"))
                    {
                        serviceEnabled = true;
                    }
                }
            }
            if (serviceEnabled)
            {
                // Prevent screen from dimming
                openService.setChecked(true);
                getWindow().addFlags(
                        WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
            } else
            {
                openService.setChecked(false);
                getWindow().clearFlags(
                        WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
            }
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
