package cs310.trojancheckinout;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
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
    private AlertDialog.Builder builder;
    ///private EditText picEditText;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        db = FirebaseFirestore.getInstance();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        Button editProfileButton = findViewById(R.id.button_edit_pic);
        Button logoutButton = findViewById(R.id.button_logout);
        Button deleteAccount = findViewById(R.id.button_delete_account);

        TextView nameView = findViewById(R.id.text_view_name);
        TextView emailView = findViewById(R.id.text_view_email);
        TextView studentIDView = findViewById(R.id.text_view_id);
        TextView majorView = findViewById(R.id.text_view_major);
        TextView occupationView = findViewById(R.id.text_view_project_role);
        profilePicView = findViewById(R.id.image_view_pic);


        //Sample User
        Intent profileIntent = getIntent();
        currUser = (User)profileIntent.getSerializableExtra("currUser");
        String fullName = currUser.getFirstName() + " " + currUser.getLastName();
        String profilePic = currUser.getProfilePicture(); //shorturl.at/oAFNV

        nameView.setText(fullName);
        emailView.setText("Email: " + currUser.getEmail());
        studentIDView.setText("Student ID: " + currUser.getStudentID());
        majorView.setText("Major: " + "Computer Science");
        occupationView.setText(currUser.getOccupation());
        Picasso.get().load(profilePic).into(profilePicView);

        //Edit Profile On CLick
        editProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("Profile", "button Clicked");
                //editProfilePic(picEditText)
                Dialog editProfile = onCreateDialog();
                editProfile.show();
                //EditText picEditText = editProfile.findViewById(R.id.img_link);

            }
        });

        //Log Out on Click
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               //
            }
        });

        //delete Account
        deleteAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteAccount();
            }
        });

    }

    protected void deleteAccount(){
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

    protected void editProfilePic(String picLink){

        //String picLink = picEditText.getText().toString();
        Log.d("test", picLink);
        Picasso.get().load(picLink).into(profilePicView);
        currUser.setProfilePicture(picLink);
        //https://ctcusc.com/images/headshots/ctc-lindsay.jpg
    }

    public Dialog onCreateDialog() {
        builder = new AlertDialog.Builder(this);
        // Get the layout inflater
        builder.setCancelable(true);
        LayoutInflater inflater = getLayoutInflater();
        final View view = inflater.inflate(R.layout.dialog_edit_pic, null);
//        final EditText picEditText = findViewById(R.id.img_link);
//        final String picLink = "";

        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        builder.setView(view)
                // Add action buttons
                .setPositiveButton("Submit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        EditText picEditText = view.findViewById(R.id.img_link);
                        String picLink = picEditText.getText().toString();
                        editProfilePic(picLink);
//                        dialog.cancel();
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        return builder.create();
    }
}