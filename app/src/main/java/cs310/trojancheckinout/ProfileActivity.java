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
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.model.Document;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.time.LocalTime;
import java.util.List;

import cs310.trojancheckinout.models.User;

public class ProfileActivity extends AppCompatActivity {

    private ImageView profilePicView;
    private FirebaseFirestore db;
    private User currUser;
    private AlertDialog.Builder builder;
    private Bundle bundle;
    private String currEmail;
    private String picLink;
    private DocumentSnapshot userDoc;
    ///private EditText picEditText;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //get db and bundle and current user
        db = FirebaseFirestore.getInstance();
        bundle = getIntent().getExtras();
        currEmail = bundle.getString("email"); //uncomment when you pass in bundle
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        //create user object since we need almost all the info
        //currEmail = "nutakki@usc.edu"; //temporary, just for testing

        DocumentReference docIdRef = db.collection("users").document(currEmail);
        docIdRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                userDoc = task.getResult();
                if (task.isSuccessful()) {
                    //User(String firstName, String lastName, String email, String password, boolean checked_in, String occupation, String studentID, String profilePicture
                    currUser = new User(
                            userDoc.getString("firstName"),
                            userDoc.getString("lastName"),
                            userDoc.getString("email"),
                            userDoc.getString("password"),
                            userDoc.getBoolean("checked_in"),
                            userDoc.getString("occupation"),
                            userDoc.getString("studentID"),
                            userDoc.getString("profilePicture"));

                    //Buttons
                    Button viewHistory = findViewById(R.id.button_view_history);
                    Button editProfileButton = findViewById(R.id.button_edit_pic);
                    Button logoutButton = findViewById(R.id.button_logout);
                    Button deleteAccount = findViewById(R.id.button_delete_account);

                    //Text Views
                    TextView nameView = findViewById(R.id.text_view_name);
                    TextView emailView = findViewById(R.id.text_view_email);
                    TextView studentIDView = findViewById(R.id.text_view_id);
                    TextView majorView = findViewById(R.id.text_view_major);
                    TextView occupationView = findViewById(R.id.text_view_project_role);
                    profilePicView = findViewById(R.id.image_view_pic);

                    //Display Data
                    String fullName = currUser.getFirstName() + " " + currUser.getLastName();
                    nameView.setText(fullName);
                    emailView.setText("Email: " + currUser.getEmail());
                    studentIDView.setText("Student ID: " + currUser.getStudentID());
                    majorView.setText("Major: " + "Computer Science");
                    occupationView.setText(currUser.getOccupation());
                    String profilePic = currUser.getProfilePicture();
                    Picasso.get().load(profilePic).into(profilePicView);

                    //Dialog for Picture Link
                    final AlertDialog.Builder builder = new AlertDialog.Builder(ProfileActivity.this)
                            .setPositiveButton("OK", null);
                    final EditText input = new EditText(ProfileActivity.this);
                    builder.setTitle("Enter Link to Profile Picture");
                    builder.setView(input);
                    builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });

                    final AlertDialog editLinkDialog = builder.create();

                    //Override so we don't submit on invalid input
                    editLinkDialog.setOnShowListener(new DialogInterface.OnShowListener() {
                        @Override
                        public void onShow(DialogInterface dialogInterface) {

                            Button button = ((AlertDialog) editLinkDialog).getButton(AlertDialog.BUTTON_POSITIVE);
                            button.setOnClickListener(new View.OnClickListener() {

                                @Override
                                public void onClick(View view) {
                                    picLink = input.getText().toString();
                                    picLink = picLink.replaceAll("\\s+","");
                                    Log.d("document", "Link:(" + picLink + ")");
                                    if(TextUtils.isEmpty(picLink)){
                                        input.setError("No Link Provided");
                                    }else{
                                        //editProfilePic();
                                        Log.d("test", picLink);
                                        Picasso.get().load(picLink).into(profilePicView, new Callback() {
                                            @Override
                                            public void onSuccess() {
                                                DocumentReference currDoc = db.collection("users").document(currEmail);
                                                currDoc.update("profilePicture", picLink)
                                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                            @Override
                                                            public void onSuccess(Void aVoid) {
                                                                Log.d("Doc", "DocumentSnapshot successfully written!");
                                                                editLinkDialog.dismiss();
                                                            }
                                                        })
                                                        .addOnFailureListener(new OnFailureListener() {
                                                            @Override
                                                            public void onFailure(@NonNull Exception e) {
                                                                Log.w("Doc", "Error writing document", e);
                                                            }
                                                        });
                                                //edit profile pic link in D
                                            }
                                            @Override
                                            public void onError(Exception e) {
                                                //Picasso.get().load("https://raw.githubusercontent.com/lindshuang/image-store/main/error_profile_pic.png").into(profilePicView);
                                                input.setError("Invalid Link");
                                            }
                                        });
                                        //editLinkDialog.dismiss();
                                    }
                                }
                            });
                        }
                    });



                    //Click Edit Profile Button
                    editProfileButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Log.d("Profile", "button Clicked");
                            //editProfilePic(picEditText)
                            editLinkDialog.show();
                        }
                    });

                    //Click Log Out Button
                    logoutButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            logout();
                        }
                    });

                    //Click Delete Account Button
                    deleteAccount.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            deleteAccount();
                        }
                    });

                    //Click View History Button
                    viewHistory.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent profileActivityIntent = new Intent(ProfileActivity.this, HistoryActivity.class);
                            profileActivityIntent.putExtra("email", currEmail);
                            startActivity(profileActivityIntent);
                        }
                    });
                }
            }
        });
    }


    //Log Out Button Function
    protected void logout(){
        Intent intent = new Intent(ProfileActivity.this, MainActivity.class);
        intent.putExtra("email", "");
    }

    //Delete Account Function
    protected void deleteAccount(){
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

    //Edit Profile Picture Function
    protected void editProfilePic(){

        Log.d("test", picLink);
        Picasso.get().load(picLink).into(profilePicView, new Callback() {
            @Override
            public void onSuccess() { DocumentReference currDoc = db.collection("users").document(currEmail);
                currDoc.update("profilePicture", picLink)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Log.d("Doc", "DocumentSnapshot successfully written!");
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.w("Doc", "Error writing document", e);
                            }
                        });
                //edit profile pic link in D
            }
            @Override
            public void onError(Exception e) {
                Picasso.get().load("https://raw.githubusercontent.com/lindshuang/image-store/main/error_profile_pic.png").into(profilePicView);
            }
        });

        //https://ctcusc.com/images/headshots/ctc-lindsay.jpg
    }

    //Edit Profile Pic Pop-up
//    public AlertDialog onCreateDialog() {
//        builder = new AlertDialog.Builder(this);
//        LayoutInflater inflater = getLayoutInflater(); // Get the layout inflater
//        final View view = inflater.inflate(R.layout.dialog_edit_pic, null);
//        final EditText picEditText = view.findViewById(R.id.img_link);
//        final TextView error_text = view.findViewById(R.id.error_text);
//
//                // Inflate and set the layout for the dialog, pass null as the parent view because its going in the dialog layout
//        builder.setView(view)
//                // Add action buttons
//                .setPositiveButton("Submit", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int id) {
//                        picLink = picEditText.getText().toString();
//                        if(picLink.length() <= 0){
//                            error_text.setVisibility(View.VISIBLE);
//                            error_text.setText("Email Address not found");
//                            Log.d("document", "no link!");
//                        }else{
//                            editProfilePic(picLink);
//                        }
//                    }
//                })
//                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int id) {
//                        dialog.cancel();
//                    }
//                });
//        return builder.create();
//    }
}