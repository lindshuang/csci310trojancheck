package cs310.trojancheckinout;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import cs310.trojancheckinout.models.History;
import cs310.trojancheckinout.models.User;

public class TempNavActivity extends AppCompatActivity {

    private User currUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_temp_nav);

        Button profileButton = findViewById(R.id.button_profile);
        //Button historyButton = findViewById(R.id.button_history);

        //create a new dummy user
        //currUser = new User("Anya", "Nutakki", "nutakki@usc.edu",  "123", true, "Student", "123456789", "https://st.depositphotos.com/1779253/5140/v/600/depositphotos_51405259-stock-illustration-male-avatar-profile-picture-use.jpg");

        profileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent profileActivityIntent = new Intent(TempNavActivity.this, ProfileActivity.class);
                profileActivityIntent.putExtra("email", "nutakki@usc.edu");
                startActivity(profileActivityIntent);
            }
        });

//        historyButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent historyActivityIntent = new Intent(TempNavActivity.this, HistoryActivity.class);
//                //historyActivityIntent.putExtra("currUser", currUser);
//                startActivity(historyActivityIntent);
//            }
//        });

    }

}