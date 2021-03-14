package cs310.trojancheckinout;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

public class TempNavActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_temp_nav);

        Button profileButton = findViewById(R.id.button_profile);
        Button historyButton = findViewById(R.id.button_history);

//        profileButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent profileActivityIntent = new Intent(MainActivity.this, ProfileActivity.class);
//                startActivity(profileActivityIntent);
//            }
//        });

        historyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent historyActivityIntent = new Intent(TempNavActivity.this, HistoryActivity.class);
                startActivity(historyActivityIntent);
            }
        });

    }

}