package cs310.trojancheckinout;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;


import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
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
import cs310.trojancheckinout.models.User;

public class HistoryActivity extends AppCompatActivity {

    private HistoryAdapter adapter;
    private ArrayList<History> histories = new ArrayList<History>();

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        final RecyclerView list = findViewById(R.id.recycler_view_histories);
        final FirebaseFirestore db = FirebaseFirestore.getInstance();
        final ArrayList<String> historyList = new ArrayList<>();
        //final ArrayList<History> histories = new ArrayList<History>();

        Intent historyIntent = getIntent();
        User currUser = (User)historyIntent.getSerializableExtra("currUser");
        final String email = currUser.getEmail();

        adapter = new HistoryAdapter(histories);
        list.setAdapter(adapter);


        db.collection("history").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>(){
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        String entry = document.getId();
                        String entry_temp = entry.substring(1);
                        Log.d("document", entry_temp);
                        if (email.equals(entry_temp)){
                            historyList.add(entry);
                        }
                    }

                    getPersonData(list, db, historyList, histories);
                    adapter.notifyDataSetChanged();
                    System.out.println("HISTORY: " + histories.toString());
                    Log.d("size after inserting all", "SIZE: " + histories.size());
                    Log.d("document", "history list: " + historyList.toString());

                } else {
                    Log.d("document", "Failed with: ", task.getException());
                }
            }
        });


    }

    void getPersonData(RecyclerView list, FirebaseFirestore db, ArrayList<String> historyList, final ArrayList<History> histories){

        for (int i = 0; i < historyList.size(); i++){
            DocumentReference docIdRef = db.collection("history").document(historyList.get(i));
            docIdRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if (task.isSuccessful()) {

                        DocumentSnapshot document = task.getResult();
                        String buildingName = document.getString("buildingName");
                        double time_elapsed = document.getDouble("totalTime");
                        String last_timeIn = document.getString("timeInTime");
                        String last_timeOut = document.getString("timeOutTime");
                        String last_DateIn = document.getString("timeInDate");
                        String last_DateOut = document.getString("timeOutDate");
                        Log.d("time in", "last_timeIn is " + last_timeIn);
                        Log.d("time out", "last_timeOut is " + last_timeOut);
                        Log.d("time in", "last_timeIn is " + last_DateIn);
                        Log.d("time out", "last_timeOut is " + last_DateOut);

                        History new_history = new History(last_DateIn, last_timeIn, last_DateOut, last_timeOut, time_elapsed, buildingName);
                        histories.add(new_history);
                        Log.d("size", "SIZE: " + histories.size());
                        adapter.notifyDataSetChanged();


                        if (!document.exists()) {
                            Log.d("document", "Document does not exist!");
                        } else {
                            Log.d("Document", "Document exists!");
                        }
                    } else {
                        Log.d("document", "Failed with: ", task.getException());
                    }
                }
            });
        }//end
        adapter.notifyDataSetChanged();
    }




} //end class