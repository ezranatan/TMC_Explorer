package org.tmcindonesia.tmc_explorer.questions;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import org.tmcindonesia.tmc_explorer.Home;
import org.tmcindonesia.tmc_explorer.R;
import org.tmcindonesia.tmc_explorer.UserAnswer;

import java.util.HashMap;
import java.util.Map;

public class LESSON9 extends AppCompatActivity {
    FirebaseFirestore firebaseFirestore;
    FirebaseAuth firebaseAuth;
    String userID;
    public static final String TAG = "TAG";
    // variable QUESTIONS PAGE
    private int correctAnswerQuestionsPage[] = {1, 0, 0, 1, 1};
    private RadioGroup rgqp_question1, rgqp_question2, rgqp_question3, rgqp_question4, rgqp_question5;
    private RadioButton rb_question1, rb_question2, rb_question3, rb_question4, rb_question5;
    private Button getCheckAnswerQuestionsPage;
    private int numberOfCorrectAnswer = 0;
    // variable MY JOURNEY WITH JESUS
    private EditText mjwj_answer1, mjwj_answer2, mjwj_answer3, mjwj_answer4;
    private String AnswersMJWJ[] = {};
    private Button getAnswerMJWJ;
    private String userAnswers;
    private static final String key_mjwj_answer1 = "key_mjwj_answer1";
    private static final String key_mjwj_answer2 = "key_mjwj_answer2";
    private static final String key_mjwj_answer3 = "key_mjwj_answer3";
    private static final String key_mjwj_answer4 = "key_mjwj_answer4";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question9);

        // *******************************QUESTIONS PAGE*********************************
        // get layout ID radio group
        rgqp_question1 = (RadioGroup) findViewById(R.id.radioGroup_QuestionPage9_MultipleChoiceQuestion1);
        rgqp_question2 = (RadioGroup) findViewById(R.id.radioGroup_QuestionPage9_MultipleChoiceQuestion2);
        rgqp_question3 = (RadioGroup) findViewById(R.id.radioGroup_QuestionPage9_MultipleChoiceQuestion3);
        rgqp_question4 = (RadioGroup) findViewById(R.id.radioGroup_QuestionPage9_MultipleChoiceQuestion4);
        rgqp_question5 = (RadioGroup) findViewById(R.id.radioGroup_QuestionPage9_MultipleChoiceQuestion5);
        // OK button clicked QUESTION PAGE
        getCheckAnswerQuestionsPage = findViewById(R.id.button_CheckAnswer_QuestionsPage);
        getCheckAnswerQuestionsPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // get which radio button is choose
                int rb_id_question1 = rgqp_question1.getCheckedRadioButtonId();
                int rb_id_question2 = rgqp_question2.getCheckedRadioButtonId();
                int rb_id_question3 = rgqp_question3.getCheckedRadioButtonId();
                int rb_id_question4 = rgqp_question4.getCheckedRadioButtonId();
                int rb_id_question5 = rgqp_question5.getCheckedRadioButtonId();
                int rb_id_array[] = {rb_id_question1, rb_id_question2, rb_id_question3, rb_id_question4, rb_id_question5};
                // get layout ID radio button
                rb_question1 = (RadioButton) findViewById(rb_id_question1);
                rb_question2 = (RadioButton) findViewById(rb_id_question2);
                rb_question3 = (RadioButton) findViewById(rb_id_question3);
                rb_question4 = (RadioButton) findViewById(rb_id_question4);
                rb_question5 = (RadioButton) findViewById(rb_id_question5);
                //get radio button index
                int rb_index_question1 = rgqp_question1.indexOfChild(rb_question1);
                int rb_index_question2 = rgqp_question2.indexOfChild(rb_question2);
                int rb_index_question3 = rgqp_question3.indexOfChild(rb_question3);
                int rb_index_question4 = rgqp_question4.indexOfChild(rb_question4);
                int rb_index_question5 = rgqp_question5.indexOfChild(rb_question5);
                int rb_index_array[] = {rb_index_question1, rb_index_question2, rb_index_question3, rb_index_question4, rb_index_question5};
                //toast
                checkAnswerQuestionsPage(correctAnswerQuestionsPage, rb_index_array);
            }

            // getNumberOfCorrectAnswer
            public void checkAnswerQuestionsPage(int[] listOfCorrectAnswer, int[] listOfUserAnswer) {
                for (int index = 0; index < listOfCorrectAnswer.length; index++) {
                    if (listOfCorrectAnswer[index] == listOfUserAnswer[index]) {
                        numberOfCorrectAnswer++;
                    }
                }
                Toast.makeText(LESSON9.this,
                        String.valueOf(numberOfCorrectAnswer) + " soal kamu jawab dengan benar",
                        Toast.LENGTH_SHORT).show();
            }
        });

        // ***************************MY JOURNEY WITH JESUS*******************************
        // get layout ID
        mjwj_answer1 = (EditText) findViewById(R.id.editText_QuestionPage9_MJWJAnswer1);
        mjwj_answer2 = (EditText) findViewById(R.id.editText_QuestionPage9_MJWJAnswer2);
        mjwj_answer3 = (EditText) findViewById(R.id.editText_QuestionPage9_MJWJAnswer3);
        mjwj_answer4 = (EditText) findViewById(R.id.editText_QuestionPage9_MJWJAnswer4);
        getAnswerMJWJ = (Button) findViewById(R.id.button_CheckAnswer_MJWJ);
        // Ok button clicked MY JOURNEY WITH JESUS
        getAnswerMJWJ.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // create instance
                UserAnswer userAnswers = new UserAnswer(
                        numberOfCorrectAnswer,
                        mjwj_answer1.getText().toString().trim(),
                        mjwj_answer2.getText().toString().trim(),
                        mjwj_answer3.getText().toString().trim(),
                        mjwj_answer4.getText().toString().trim()
                );
                // write data base method
                writeUserAnswerToDataBase(userAnswers);
                // save preferences
                SavePreferences();
                // toast
                Toast.makeText(LESSON9.this,
                        "Terimakasih, ayo lanjutkan pelajaran mu",
                        Toast.LENGTH_SHORT).show();
                // move to home page
                startActivity(new Intent(getApplicationContext(), Home.class));
            }
        });
        LoadPreferences();
    }


    // WRITE DATA TO FIRE STORE DATA BASE
    public void writeUserAnswerToDataBase(UserAnswer userAnswers) {
        // get the content
        String className = this.getLocalClassName().toString();
        Map<String, Object> answers = new HashMap<>();
        answers.put("Correct answer", userAnswers.getNumberOfCorrectAnswer());
        answers.put(getResources().getString(R.string.MJWJ1_question1), userAnswers.getUserAnswerMJWJ1());
        answers.put(getResources().getString(R.string.MJWJ1_question2), userAnswers.getUserAnswerMJWJ2());
        answers.put(getResources().getString(R.string.MJWJ1_question3), userAnswers.getUserAnswerMJWJ3());
        answers.put(getResources().getString(R.string.MJWJ1_question4), userAnswers.getUserAnswerMJWJ4());
        // create fire base instance
        firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        String userID = firebaseAuth.getCurrentUser().getUid();
        // actually write on cloud
        firebaseFirestore.collection("TMC EXPLORER ONE USER").document(userID).collection("User Answer").document(className)
                .set(answers)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "successfully written!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error writing document", e);
                    }
                });
    }

    // SAVE PREFERENCE WHEN BACK BACK PRESSED and ACTIVITY GET DESTROYED
    private void SavePreferences() {
        SharedPreferences sharedPreferences = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key_mjwj_answer1, mjwj_answer1.getText().toString());
        editor.putString(key_mjwj_answer2, mjwj_answer2.getText().toString());
        editor.putString(key_mjwj_answer3, mjwj_answer3.getText().toString());
        editor.putString(key_mjwj_answer4, mjwj_answer4.getText().toString());
        editor.commit();
    }

    private void LoadPreferences() {
        SharedPreferences sharedPreferences = getPreferences(MODE_PRIVATE);
        // set text just like when the user leave it (back pressed)
        mjwj_answer1.setText(sharedPreferences.getString(key_mjwj_answer1, mjwj_answer1.getText().toString()));
        mjwj_answer2.setText(sharedPreferences.getString(key_mjwj_answer2, mjwj_answer2.getText().toString()));
        mjwj_answer3.setText(sharedPreferences.getString(key_mjwj_answer3, mjwj_answer3.getText().toString()));
        mjwj_answer4.setText(sharedPreferences.getString(key_mjwj_answer4, mjwj_answer4.getText().toString()));
    }

    @Override
    public void onBackPressed() {
        SavePreferences();
        super.onBackPressed();
    }
}