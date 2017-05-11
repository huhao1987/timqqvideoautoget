package com.example.hao.timspeakerlisten;

import android.accessibilityservice.AccessibilityService;
import android.content.ComponentName;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;

import java.util.List;

import static android.view.accessibility.AccessibilityEvent.TYPE_VIEW_CLICKED;

/**
 * Created by Hao on 11/05/2017.
 */

public class Myaccessibiligtyservice extends AccessibilityService {
    private String TAG="accessibilityservice";
    @Override
    public void onAccessibilityEvent(AccessibilityEvent accessibilityEvent) {
        int eventType=accessibilityEvent.getEventType();
        String packagename=accessibilityEvent.getPackageName().toString();
        String classname=accessibilityEvent.getClassName().toString();
//        accessibilityEvent.
        AccessibilityNodeInfo accessibilityNodeInfo=getRootInActiveWindow();

        //get the current activity
        ComponentName componentName = new ComponentName(
                accessibilityEvent.getPackageName().toString(),
                accessibilityEvent.getClassName().toString()
        );
        ActivityInfo activityInfo=tryGetActivity(componentName);
        boolean isActivity = activityInfo != null;

        if (isActivity)
            Log.d(TAG, componentName.getShortClassName());


        switch (eventType) {
                case TYPE_VIEW_CLICKED:
                    Log.d(TAG, packagename+" is clicked");
                break;
//                case AccessibilityEvent.TYPE_WINDOW_CONTENT_CHANGED:
            case AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED: {

                if(componentName.getShortClassName().equals("com.tencent.av.ui.VideoInviteFull")) {
//                    Log.d(TAG, packagename + " windows change");
                    if (accessibilityNodeInfo!=null)
                    {
                      List<AccessibilityNodeInfo> list=accessibilityNodeInfo.findAccessibilityNodeInfosByText("接听");
                        accessibilityNodeInfo.recycle();
                        for(AccessibilityNodeInfo i:list)
                        {
                            i.performAction(AccessibilityNodeInfo.ACTION_CLICK);
                        }
//                        recycle(accessibilityNodeInfo);
                    }
                }
            }
                break;

            }

    }

    public void recycle(AccessibilityNodeInfo info) {

        if (info.getChildCount() == 0) {

//            Log.i(TAG, "child widget----------------------------" + info.getClassName());

            Log.i(TAG, "id:" + info.getViewIdResourceName());

//            Log.i(TAG, "Text：" + info.getText());
//
//            Log.i(TAG, "windowId:" + info.getWindowId());

        } else {

            for (int i = 0; i < info.getChildCount(); i++) {

                if(info.getChild(i)!=null){

                    recycle(info.getChild(i));

                }

            }

        }

    }
    private ActivityInfo tryGetActivity(ComponentName componentName) {
        try {
            return getPackageManager().getActivityInfo(componentName, 0);
        } catch (PackageManager.NameNotFoundException e) {
            return null;
        }
    }
    @Override
    public void onInterrupt() {

    }
}
