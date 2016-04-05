package com.example.android.sunshine.app;

import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.google.android.gms.wearable.DataEvent;
import com.google.android.gms.wearable.DataEventBuffer;
import com.google.android.gms.wearable.DataMap;
import com.google.android.gms.wearable.DataMapItem;
import com.google.android.gms.wearable.WearableListenerService;

public class ForecastDataService extends WearableListenerService {

    @Override
    public void onDataChanged(DataEventBuffer dataEvents) {
        Log.d("!!!!", "ON DATA CHANGED");
        String date;
        double high, low;
        int weatherId;

        for (DataEvent dataEvent : dataEvents) {
            if (dataEvent.getType() == DataEvent.TYPE_CHANGED) {
                DataMap dataMap = DataMapItem.fromDataItem(dataEvent.getDataItem()).getDataMap();
                String path = dataEvent.getDataItem().getUri().getPath();
                if (path.equals("/today_forecast")) {
                    date = dataMap.getString("date");
                    high = dataMap.getDouble("hi_temp");
                    low = dataMap.getDouble("low_temp");
                    weatherId = dataMap.getInt("weather_id");

                    Log.d("!!!!", ": date is " + date);
                    Log.d("!!!!", ": high is " + high);
                    Log.d("!!!!", ": low is " + low);
                    Log.d("!!!!", ": icon asset is " + weatherId);

                    Intent intent = new Intent("WatchForecastData");
                    intent.putExtra("date", date);
                    intent.putExtra("hi_temp", high);
                    intent.putExtra("low_temp", low);
                    intent.putExtra("weather_id", weatherId);

                    LocalBroadcastManager.getInstance(getApplicationContext())
                            .sendBroadcast(intent);
                }
            }
        }

    }
}