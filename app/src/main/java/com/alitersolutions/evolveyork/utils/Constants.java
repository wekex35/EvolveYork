package com.alitersolutions.evolveyork.utils;

import com.alitersolutions.evolveyork.activity.BluetoothChatService;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public interface Constants {
    String  USERLOGINDETAILS = "userlogindetails";
    String  USERID = "userId";
    String  EVOLVEUSRID = "EvolveUsr_ID";
    String  TOKEN = "userlogindetails";
    String  NOTAVAILABLE = "notavailable";
    String  FAILURE = "failure";
    String  INDTENTDATA = "indtentdata";
    String  TAG = "evolveyork";
    String  BASEURL = "baseurl";
    String  LOCATIONINFO = "locationinfo";
    String  ITEMINFO = "iteminfo";
    String  APIROUTE = "/dev/";//dev
//    String  APIROUTE = "/evolveyei/";//dev
    String str = "\r\n";
    String EVOLVEUSRNAME = "EvolveUsr_Name";
    // Message types sent from the BluetoothChatService Handler
     int MESSAGE_STATE_CHANGE = 1;
     int MESSAGE_READ = 2;
     int MESSAGE_WRITE = 3;
     int MESSAGE_DEVICE_NAME = 4;
     int MESSAGE_TOAST = 5;




    // Key names received from the BluetoothChatService Handler
     String DEVICE_NAME = "device_name";
     String TOAST = "toast";


     String EMPTY = "empty";
     String KEY_ID = "id";
     String KEY_NAME = "name";
     String KEY_LAYOUT_NAME = "layout_name";
     String KEY_JSONVIEW = "jsonview";

    //View Info
    String TOP_MARGIN = "topmargin";
    String LEFT_MARGIN = "leftmargin";
    String VIEW_HEIGHT = "height";
    String VIEW_WIDTH = "width";
    String VIEW_ROTATION = "rotation";


    //Date and Time
    DateFormat TIME_FORMAT = new SimpleDateFormat("HH:mm:ss");
    DateFormat FOR_TAG_FORMAT = new SimpleDateFormat("ddMMyyyyHHmmss");

    String TIME = TIME_FORMAT.format(Calendar.getInstance().getTime());
    String FOR_SET_TAG = TIME_FORMAT.format(Calendar.getInstance().getTime());


}
