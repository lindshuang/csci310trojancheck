package cs310.trojancheckinout;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

import java.time.LocalTime;
import java.util.List;

import cs310.trojancheckinout.models.User;

public class ProfileActivity extends AppCompatActivity {

    private ImageView profilePicView;
    private FirebaseFirestore db;
    private User currUser;
    ///private EditText picEditText;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        db = FirebaseFirestore.getInstance();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        Button editProfileButton = findViewById(R.id.button_edit_pic);
        Button logoutButton = findViewById(R.id.button_logout);

        TextView nameView = findViewById(R.id.text_view_name);
        TextView emailView = findViewById(R.id.text_view_email);
        TextView studentIDView = findViewById(R.id.text_view_id);
        TextView majorView = findViewById(R.id.text_view_major);
        TextView occupationView = findViewById(R.id.text_view_project_role);
        profilePicView = findViewById(R.id.image_view_pic);
        final EditText picEditText = findViewById(R.id.edit_link);

        //Sample User
        Intent profileIntent = getIntent();
        currUser = (User)profileIntent.getSerializableExtra("currUser");
        String fullName = currUser.getFirstName() + " " + currUser.getLastName();

        nameView.setText(fullName);
        emailView.setText(currUser.getEmail());
        studentIDView.setText(currUser.getStudentID());
        majorView.setText("Computer Science");
        occupationView.setText(currUser.getOccupation());
        //also need profile click

        //Edit Profile On CLick
        editProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("Profile", "button Clicked");
                editProfilePic(picEditText);
            }
        });
        
        //Log Out on Click
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               logout();
            }
        });

    }

    protected void logout(){
        Bundle bundle = getIntent().getExtras();
        String currEmail = bundle.getString("email");

        db.collection("users").document(currUser.getEmail())//temp, ask Ashna about this
                .delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("test", "DocumentSnapshot successfully deleted!");
                        //redirect to the home screen after logging out
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("test", "Error deleting document", e);
                    }
                });
    }

    protected void editProfilePic(EditText picEditText){

        String picLink = picEditText.getText().toString();
        Picasso.get().load(picLink).into(profilePicView);
        //shorturl.at/nqC57
    }
}