package com.abhinav.myhealthkeeper;

import java.util.List;

public interface ITimeSlotLoadListener {


        void onTimeSlotLoadSuccess(List<TimeSlotModel> timeSlotList);
        void onTimeSlotLoadFailed(String message);
        void onTimeSlotLoadEmpty();

}
