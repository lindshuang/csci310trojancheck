package cs310.trojancheckinout;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Build;
import android.os.Bundle;


import java.time.LocalTime;

import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestoreException;
import androidx.annotation.NonNull;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.QueryDocumentSnapshot;

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

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        //CollectionReference collection = db.collection("cities");

        DocumentReference docRef1 = db.collection("history").document("0ashnacha@usc.edu");
        DocumentReference docRef2 = db.collection("history").document("0nutakki@usc.edu");
        DocumentReference docRef3 = db.collection("history").document("1huan981@usc.edu");
        DocumentReference docRef4 = db.collection("history").document("1nutakki@usc.edu");

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

        String str = "test_string";

        History new_history = new History("10:00", "12:00", "12:00", "12:00", 3.0, "Taper");
        //String history_id = Integer.toString(current_student.getHistories().size()) + current_student.getEmail();
        db.collection("history").document("1nutakki@usc.edu").set(new_history);


        //FirebaseFirestore db = FirebaseFirestore.getInstance();
        //db.collection("users").document("test_doc").set(str);

//        db.
//        db.collection("test").document("LA").set(str);
//        DatabaseReference myRef = database.getReference("message");
//        myRef.setValue("Hello, World!");
    }
}