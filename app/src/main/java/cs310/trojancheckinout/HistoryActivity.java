package cs310.trojancheckinout;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Build;
import android.os.Bundle;

import java.time.LocalTime;

import cs310.trojancheckinout.models.History;

public class HistoryActivity extends AppCompatActivity {

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        RecyclerView list = findViewById(R.id.recycler_view_histories);

        //LocalTime time = LocalTime.of(7,00,12);
        String time = "10:00:12";
        double totalTime = 4.30;
        String buildingName = "Taper Hall";

        //String timeInDate, String timeInTime, String timeOutDate, String timeOutTime, double totalTime, String buildingName

        History[] Histories = {
                new History(time, time, time, time, totalTime, buildingName),
                new History(time, time, time, time, totalTime, buildingName),
                new History(time, time, time, time, totalTime, buildingName),
                new History(time, time, time, time, totalTime, buildingName),
                new History(time, time, time, time, totalTime, buildingName),
                new History(time, time, time, time, totalTime, buildingName),
                new History(time, time, time, time, totalTime, buildingName),
                new History(time, time, time, time, totalTime, buildingName),
                new History(time, time, time, time, totalTime, buildingName),
                new History(time, time, time, time, totalTime, buildingName),
                new History(time, time, time, time, totalTime, buildingName),
                new History(time, time, time, time, totalTime, buildingName)
        };

        HistoryAdapter adapter = new HistoryAdapter(Histories);

        list.setAdapter(adapter);
    }
}