package com.abhinav.myhealthkeeper;

import android.app.Application;
import android.content.Context;

import com.zegocloud.uikit.prebuilt.call.config.ZegoNotificationConfig;
import com.zegocloud.uikit.prebuilt.call.invite.ZegoUIKitPrebuiltCallInvitationConfig;
import com.zegocloud.uikit.prebuilt.call.invite.ZegoUIKitPrebuiltCallInvitationService;
import com.zegocloud.uikit.prebuilt.call.invite.widget.ZegoSendCallInvitationButton;
import com.zegocloud.uikit.service.defines.ZegoUIKitUser;

import java.util.Collections;

public class Common {

    public static final int TIME_SLOT_TOTAL = 20;

    //public static String CurreentDoctor = "testdoc@testdoc.com";

    public static String ROLE;

    public static String myId;



    public  static  String convertTimeSlotToString(int slot){

        switch (slot)
        {
            case 0:
                return "9:00-9:30";
            case 1:
                return "9:30-10:00";
            case 2:
                return "10:00-10:30";
            case 3:
                return "10:30-11:00";
            case 4:
                return "11:00-11:30";
            case 5:
                return "11:30-12:00";
            case 6:
                return "12:00-12:30";
            case 7:
                return "12:30-13:00";
            case 8:
                return "13:00-13:30";
            case 9:
                return "13:30-14:00";
            case 10:
                return "14:00-14:30";
            case 11:
                return "14:30-15:00";
            case 12:
                return "15:00-15:30";
            case 13:
                return "15:30-16:00";
            case 14:
                return "16:00-16:30";
            case 15:
                return "16:30-17:00";
            case 16:
                return "17:00-17:30";
            case 17:
                return "17:30-18:00";
            case 18:
                return "18:00-18:30";
            case 19:
                return "18:30-19:00";
            case 20:
                return "19:00-19:30";
            default:
                return "Closed";
        }
    }


//    public  static void  startMyService(String senderId, String senderName){
//
//        Application application = getApplication(); // Android's application context
//        long appID = 1248855423;   // yourAppID
//        String appSign = "c8131aa0d89b539ff722fa1b5bb52b942a1f735da19896574310a3f086b723b1";  // yourAppSign
//        String userID = senderId; // yourUserID, userID should only contain numbers, English characters, and '_'.
//        String userName = senderName;   // yourUserName
//
//        ZegoUIKitPrebuiltCallInvitationConfig callInvitationConfig = new ZegoUIKitPrebuiltCallInvitationConfig();
//        callInvitationConfig.notifyWhenAppRunningInBackgroundOrQuit = true;
//        ZegoNotificationConfig notificationConfig = new ZegoNotificationConfig();
//        notificationConfig.sound = "zego_uikit_sound_call";
//        notificationConfig.channelID = "CallInvitation";
//        notificationConfig.channelName = "CallInvitation";
//        ZegoUIKitPrebuiltCallInvitationService.init(getApplication(), appID, appSign, userID, userName,callInvitationConfig);
//    }



//    public static void  startVideoCall(String receiverId, String name){
//
//
//        ZegoSendCallInvitationButton button = new ZegoSendCallInvitationButton(getApplicationContext());
//        button.setIsVideoCall(true);
//        button.setResourceID("zego_uikit_call");
//        String targetUserID = receiverId; // The ID of the user you want to call.
//        String targetUserName = name; // The username of the user you want to call.
//        button.setInvitees(Collections.singletonList(new ZegoUIKitUser(targetUserID,targetUserName)));
//    }
//
////    public static Context getApplicationContext() {
////        Context context;
////
////        return thi
////    }
}
