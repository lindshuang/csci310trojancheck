package cs310.trojancheckinout;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

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

public class HistoryActivity extends AppCompatActivity {

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        final RecyclerView list = findViewById(R.id.recycler_view_histories);
        final FirebaseFirestore db = FirebaseFirestore.getInstance();
        final ArrayList<String> historyList = new ArrayList<>();
        final ArrayList<History> histories = new ArrayList<History>();

        final String email = "nutakki@usc.edu";

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

                    getPersonData(db, historyList, histories);
                    System.out.println("HISTORY: " + histories.toString());

                    HistoryAdapter adapter = new HistoryAdapter(histories);
                    list.setAdapter(adapter);

                    Log.d("document", "history list: " + historyList.toString());

                } else {
                    Log.d("document", "Failed with: ", task.getException());
                }
            }
        });


    }

    void getPersonData(FirebaseFirestore db, ArrayList<String> historyList, final ArrayList<History> histories){

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

//                        HistoryAdapter adapter = new HistoryAdapter(histories);
//                        list.setAdapter(adapter);

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
        }//end for
    }




} //end class













//        db.collection("history").get()
//                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
//                    @Override
//                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
//                        if(!queryDocumentSnapshots.isEmpty()){
//
//                            List<DocumentSnapshot> docList = queryDocumentSnapshots.getDocuments();
//
//                            for(DocumentSnapshot d : docList){
//                                String entry = d.getId();
//                                entry = entry.substring(1);
//                                if (email.equals(entry)){
//                                    String timeInDate = d.getString("timeInDate");
//                                    String timeInTime = d.getString("timeInTime");
//                                    String timeOutDate = d.getString("timeOutDate");
//                                    String timeOutTime = d.getString("timeOutTime");
////                                    double totalTime = (double) d.get("totalTime");
//                                    String buildingName = d.getString("buildingName");
//                                    History new_history = new History(timeInDate, timeInTime, timeOutDate, timeOutTime, 5, buildingName);
//                                    histories.add(new_history);
//                                }
//                            }
//
//                            HistoryAdapter adapter = new HistoryAdapter(histories);
//                            list.setAdapter(adapter);
//
//                        }
//                    }
//                });

//        db.collection("history").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//
//            @Override
//            public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                if (task.isSuccessful()) {
//                    for (QueryDocumentSnapshot document : task.getResult()) {
//                        String entry = document.getId();
//                        entry = entry.substring(1);
//                        Log.d("document", entry);
//                        if (email.equals(entry)){
//                            historyList.add(entry);
//                        }
//                    }
//                    Log.d("document", "history list: " + historyList.toString());
//
//                } else {
//                    Log.d("document", "Failed with: ", task.getException());
//                }
//            }
//        });




//        History[] Histories = {
//                new History(time, time, time, time, totalTime, buildingName),
//                new History(time, time, time, time, totalTime, buildingName),
//                new History(time, time, time, time, totalTime, buildingName),
//                new History(time, time, time, time, totalTime, buildingName),
//                new History(time, time, time, time, totalTime, buildingName),
//                new History(time, time, time, time, totalTime, buildingName),
//                new History(time, time, time, time, totalTime, buildingName),
//                new History(time, time, time, time, totalTime, buildingName),
//                new History(time, time, time, time, totalTime, buildingName),
//                new History(time, time, time, time, totalTime, buildingName),
//                new History(time, time, time, time, totalTime, buildingName),
//                new History(time, time, time, time, totalTime, buildingName)
//        };


//        History new_history = new History("10:00", "12:00", "12:00", "12:00", 3.0, "Taper");
//        //String history_id = Integer.toString(current_student.getHistories().size()) + current_student.getEmail();
//        db.collection("history").document("1nutakki@usc.edu").set(new_history);


        //FirebaseFirestore db = FirebaseFirestore.getInstance();
        //db.collection("users").document("test_doc").set(str);

//        db.
//        db.collection("test").document("LA").set(str);
//        DatabaseReference myRef = database.getReference("message");
//        myRef.setValue("Hello, World!");
//    }
//}