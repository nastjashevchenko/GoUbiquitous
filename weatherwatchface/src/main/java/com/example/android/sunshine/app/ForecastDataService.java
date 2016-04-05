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
    private static final String LOG_TAG = ForecastDataService.class.getSimpleName();

    @Override
    public void onDataChanged(DataEventBuffer dataEvents) {
        String date;
        double high, low;
        int weatherId;

        for (DataEvent dataEvent : dataEvents) {
            if (dataEvent.getType() == DataEvent.TYPE_CHANGED) {
                DataMap dataMap = DataMapItem.fromDataItem(dataEvent.getDataItem()).getDataMap();
                String path = dataEvent.getDataItem().getUri().getPath();
                if (path.equals("/today-forecast")) {
                    date = dataMap.getString("date");
                    high = dataMap.getDouble("hi-temp");
                    low = dataMap.getDouble("low-temp");
                    weatherId = dataMap.getInt("weather-id");

                    Log.d(LOG_TAG, ": date is " + date);
                    Log.d(LOG_TAG, ": high is " + high);
                    Log.d(LOG_TAG, ": low is " + low);
                    Log.d(LOG_TAG, ": icon asset is " + weatherId);

                    Intent intent = new Intent("WatchForecastData");
                    intent.putExtra("date", date);
                    intent.putExtra("hi-temp", high);
                    intent.putExtra("low-temp", low);
                    intent.putExtra("weather-id", weatherId);

                    LocalBroadcastManager.getInstance(getApplicationContext())
                            .sendBroadcast(intent);
                }
            }
        }

    }
}