package org.tmcindonesia.tmc_explorer.lessons;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.github.barteksc.pdfviewer.PDFView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.tmcindonesia.tmc_explorer.R;
import org.tmcindonesia.tmc_explorer.questions.LESSON12;
import org.tmcindonesia.tmc_explorer.questions.LESSON7;

public class Lesson12Read extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pdf_layout);

        // PDF View
        // pdf-view instance object
        PDFView pdfView = findViewById(R.id.pdfView);
        // pdf-view method
        pdfView.fromAsset("explorer1_published.pdf")
                .pages(90,91,92,93,94,95)
                .enableSwipe(true)
                .enableDoubletap(true)
                .defaultPage(0)
                .enableAnnotationRendering(false)
                .password(null)
                .scrollHandle(null)
                .enableAntialiasing(true)
                .autoSpacing(false)
                .fitEachPage(true)
                .spacing(0)
                .pageSnap(false)
                .pageFling(false)
                .nightMode(false)
                .load();

        FloatingActionButton fab_goToQuestion;
        fab_goToQuestion = findViewById(R.id.fab_question);
        fab_goToQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), LESSON12.class));
            }
        });
    }
}