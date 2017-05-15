package com.example.hao.timspeakerlisten;

import android.accessibilityservice.AccessibilityService;
import android.app.KeyguardManager;
import android.content.ComponentName;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.support.v4.view.accessibility.AccessibilityRecordCompat;
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
        int actionint=mySharedPreferences.getAction();
        String action="";
        switch (actionint)
        {
            case Global.ACCPET:
                action=getString(R.string.videoaccept);
                break;
            case Global.DEJECT:
                action=getString(R.string.videodeject);

                break;
            case Global.MESSAGE:
                action=getString(R.string.usemessagesend);

                break;
            case Global.CHANGETOAU:
                action=getString(R.string.videotoaudio);

                break;


        }
        if (isActivity)
            Log.d(TAG, componentName.getShortClassName());
        printEventLog(accessibilityEvent);
//        Log.d(TAG,"scrol:"+String.valueOf(getScrollPosition(accessibilityEvent)));

        switch (eventType) {
//                case TYPE_VIEW_CLICKED:
//                    Log.d(TAG, packagename+" is clicked");
//                break;
            case AccessibilityEvent.TYPE_WINDOWS_CHANGED:
                case AccessibilityEvent.TYPE_WINDOW_CONTENT_CHANGED:
            case AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED: {
//                recycle(accessibilityNodeInfo);


                if(componentName.getShortClassName().equals("com.tencent.av.ui.VideoInviteLock"))
                {
                    //QQ`s screen video requirement is the top layout, accessibilityserice cannot catch it at first time. have to use some methods to active the layout again. so push the power button and cancel.
                    if(Global.qqlockruntime==0) {
                        try {
                            Thread.sleep(500);
                            performGlobalAction(GLOBAL_ACTION_POWER_DIALOG);
                            Thread.sleep(500);
                            performGlobalAction(GLOBAL_ACTION_BACK);
                            Global.qqlockruntime=1;
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    recycle(accessibilityNodeInfo);

                    List<AccessibilityNodeInfo> list1 = accessibilityNodeInfo.findAccessibilityNodeInfosByText("hal");
                    accessibilityNodeInfo.recycle();
                    Log.d(TAG,String.valueOf(list1.size()));
//                    if(list1.size()==0)
//                    {
                    //have to use root to swipe,therefore the X and Y may be only avalible for google pixel  https://www.diycode.cc/topics/389
                      systemManager.runCommand(this,"input swipe 289 1609 913 1609");

//                    }
//                    if(list1.size()==1)
//
//                    {
//                        List<AccessibilityNodeInfo> list2 = accessibilityNodeInfo.findAccessibilityNodeInfosByText("挂断");
//                        accessibilityNodeInfo.recycle();
//                        for (AccessibilityNodeInfo i : list2) {
//                            i.performAction(AccessibilityNodeInfo.ACTION_CLICK);
//                        }
//                    }

                    //request root


//                    recycle(accessibilityNodeInfo);
//                    for (int i = 0; i < accessibilityNodeInfo.getChildCount(); i++) {
//
//                        if (accessibilityNodeInfo.getChild(i) != null) {
//
//                            Log.i(TAG, "childtext:" + accessibilityNodeInfo.getChild(i).getText());
//                            accessibilityNodeInfo.getChild(i).performAction(AccessibilityNodeInfo.ACTION_SCROLL_FORWARD);
//                        }
//                    }

//                    try {
//                        Thread.sleep(500);
//
//                        performGlobalAction(GLOBAL_ACTION_POWER_DIALOG);
//                        Thread.sleep(500);
//
//                        performGlobalAction(GLOBAL_ACTION_BACK);
//
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                    if (accessibilityNodeInfo!=null) {
//                        if (mySharedPreferences.getOpenAuto()) {
//                            List<AccessibilityNodeInfo> list = accessibilityNodeInfo.findAccessibilityNodeInfosByText("挂断");
//                            accessibilityNodeInfo.recycle();
//                            for (AccessibilityNodeInfo i : list) {
//                                i.performAction(AccessibilityNodeInfo.ACTION_CLICK);
//                            }
//                        }
//                    }
//                    recycle(accessibilityNodeInfo);
                    //                    if(mySharedPreferences.getOpenAuto()) {
//                        List<AccessibilityNodeInfo> list = accessibilityNodeInfo.findAccessibilityNodeInfosByText("挂断");
//                        accessibilityNodeInfo.recycle();
//                        for (AccessibilityNodeInfo i : list) {
//                            i.performAction(AccessibilityNodeInfo.ACTION_CLICK);
//                        }
//                    }
                }
                if(componentName.getShortClassName().equals("com.tencent.av.ui.VideoInviteFull")) {

                if (accessibilityNodeInfo!=null)
                {
                    if(mySharedPreferences.getOpenAuto()) {
                        List<AccessibilityNodeInfo> list = accessibilityNodeInfo.findAccessibilityNodeInfosByText(action);
                        accessibilityNodeInfo.recycle();
                        for (AccessibilityNodeInfo i : list) {
                            i.performAction(AccessibilityNodeInfo.ACTION_CLICK);
                        }
                    }
                }
            }

        }
                break;

            }

    }
    //from website:http://www.jianshu.com/p/5b4a4a5aca7e
    private void printEventLog(AccessibilityEvent event) {
        Log.i(TAG, "-------------------------------------------------------------");
        int eventType = event.getEventType(); //事件类型
        Log.i(TAG, "PackageName:" + event.getPackageName() + ""); // 响应事件的包名
        Log.i(TAG, "Source Class:" + event.getClassName() + ""); // 事件源的类名
        Log.i(TAG, "Description:" + event.getContentDescription()+ ""); // 事件源描述
        Log.i(TAG, "Event Type(int):" + eventType + "");

        switch (eventType) {
            case AccessibilityEvent.TYPE_NOTIFICATION_STATE_CHANGED:// 通知栏事件
                Log.i(TAG, "event type:TYPE_NOTIFICATION_STATE_CHANGED");
                break;
            case AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED://窗体状态改变
                Log.i(TAG, "event type:TYPE_WINDOW_STATE_CHANGED");
                break;
            case AccessibilityEvent.TYPE_VIEW_FOCUSED:
                Log.i(TAG, "event type:TYPE_VIEW_FOCUSED");
                break;
            case AccessibilityEvent.TYPE_VIEW_SELECTED:
                Log.i(TAG, "event type:TYPE_VIEW_SELECTED");
                break;
            case AccessibilityEvent.TYPE_VIEW_ACCESSIBILITY_FOCUSED://View获取到焦点
                Log.i(TAG, "event type:TYPE_VIEW_ACCESSIBILITY_FOCUSED");
                break;
            case AccessibilityEvent.TYPE_GESTURE_DETECTION_START:
                Log.i(TAG, "event type:TYPE_VIEW_ACCESSIBILITY_FOCUSED");
                break;
            case AccessibilityEvent.TYPE_GESTURE_DETECTION_END:
                Log.i(TAG, "event type:TYPE_GESTURE_DETECTION_END");
                break;
            case AccessibilityEvent.TYPE_WINDOW_CONTENT_CHANGED:
                Log.i(TAG, "event type:TYPE_WINDOW_CONTENT_CHANGED");
                break;
            case AccessibilityEvent.TYPE_VIEW_CLICKED:
                Log.i(TAG, "event type:TYPE_VIEW_CLICKED");
                break;
            case AccessibilityEvent.TYPE_VIEW_TEXT_CHANGED:
                Log.i(TAG, "event type:TYPE_VIEW_TEXT_CHANGED");
                break;
            case AccessibilityEvent.TYPE_VIEW_SCROLLED:
                Log.i(TAG, "event type:TYPE_VIEW_SCROLLED");
                break;
            case AccessibilityEvent.TYPE_VIEW_TEXT_SELECTION_CHANGED:
                Log.i(TAG, "event type:TYPE_VIEW_TEXT_SELECTION_CHANGED");
                break;
            default:
                Log.i(TAG, "no listen event");
        }

        for (CharSequence txt : event.getText()) {
            Log.i(TAG, "text:" + txt);
        }

        Log.i(TAG, "-------------------------------------------------------------");
    }


    private float getScrollPosition(AccessibilityEvent event) {
        final AccessibilityRecordCompat record = new AccessibilityRecordCompat(event);
        final int itemCount = event.getItemCount();
        final int fromIndex = event.getFromIndex();

        // First, attempt to use (fromIndex / itemCount).
        if ((fromIndex >= 0) && (itemCount > 0)) {
            return (fromIndex / (float) itemCount);
        }

        final int scrollY = record.getScrollY();
        final int maxScrollY = record.getMaxScrollY();

        // Next, attempt to use (scrollY / maxScrollY). This will fail if the
        // getMaxScrollX() method is not available.
        if ((scrollY >= 0) && (maxScrollY > 0)) {
            return (scrollY / (float) maxScrollY);
        }

        // Finally, attempt to use (scrollY / itemCount).
        // TODO(alanv): Hack from previous versions -- is it still needed?
        if ((scrollY >= 0) && (itemCount > 0) && (scrollY <= itemCount)) {
            return (scrollY / (float) itemCount);
        }

        return 0.5f;
    }

    public void recycle(AccessibilityNodeInfo info) {

        if (info.getChildCount() == 0) {

            Log.i(TAG, "child widget----------------------------" + info.getClassName());

            Log.i(TAG, "id:" + info.getViewIdResourceName());

            Log.i(TAG, "Text：" + info.getText());

            Log.i(TAG, "windowId:" + info.getWindowId());

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
