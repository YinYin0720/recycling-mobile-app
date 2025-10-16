package com.example.e_cynic.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.e_cynic.R;
import com.example.e_cynic.constants.RequestCode;
import com.example.e_cynic.utils.userInteraction.AlertDialogCreator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class PlayQuizActivity extends AppCompatActivity
{
    // views
    private TextView questionNo,question,score;
    private RadioButton ans1,ans2,ans3,ans4;
    private ImageView backBtn;

    private String correctAns, explain;
    private int correctAnsCount = 0, qCount = 1;
    static final private int qc = 5;

    private ArrayList<ArrayList<String>> quizArray = new ArrayList<>();

    private DialogInterface.OnClickListener ad_positive_listener = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialogInterface, int i) {
            if (qCount == qc)
            {
                Intent intent = new Intent(getApplicationContext(), QuizResultActivity.class);
                intent.putExtra("CorrectAns", correctAnsCount);
                startActivityForResult(intent, RequestCode.VIEW_QUIZ_RESULT_ACTIVITY);
            }

            else
            {
                qCount++;
                nextQuestion();
            }
        }
    };

    String quizData[][] = {
            // {"question","correct ans","ans","ans","ans","explain"}
            {"Which item is not a kitchen appliance?","Television","Microwave","Refrigerator","Electronic Cooker","Television is a mass medium for entertainment. It is not placed at the kitchen."},
            {"What goes in the green recycle bin?","Meat","Paper","Metal","Plastic","Green recycle bin used for biodegradable waste or compostable materials."},
            {"What goes in the orange recycle bin?","Plastic","Cardboard","Glass Bottle","Carrot","Orange recycle bin is usually for plastic and aluminum."},
            {"What goes in the blue recycle bin?","Paper","Metal","Glass","Battery","Blue recycle bin is for paper."},
            {"What color of recycle bin should glass bottle be put into? ","Orange","Brown","Green","Blue","Orange recycle bin is used for plastics and metals."},
            {"What are the 3Rs of recycling?","Reduce, Reuse, Recycle","Rewrite, Respond, Rewind","Redirect, Round, Rationale","Respectful, Responsible, Right","The 3Rs stand for Reduce, Reuse, Recycle."},
            {"Instead of using plastic for shopping, we can bring","a canvas bag","a cup","a tent","a table","Bag is suitable tool to carry stuff."},
            {"What does recycle mean?","Convert waste into reusable material","Use something over and over again","Use less of something to reduce the waste","Make something ugly into something beautiful","Recycle means convert waste into reusable material."},
            {"Every ton of new glass produced generates about how many pounds of mining waste?","385","159","287","450","As much as 385 pounds of mining waste is created when new glass is made."},
            {"One primary AAA battery pollutes up to _ liters of water.","400","300","200","100","One primary AAA battery pollutes up to 400 liters of water."},
            {"If you recycle one glass bottle, it saves enough energy to light a 100-watt bulb for _ hours","4","3","2","1","It saves enough energy to light a 100-watt bulb for 4 hours with every glass bottle be recycled."},
            {"Recycled cartons can be used to make","Paper","Clothes","Eraser","Glove","Recycled cartons can be used to make paper."},
            {"Which material below is never decomposed?","Styrofoam","Leaves","Tin Can","Diapers","Styrofoam is never decomposed."},
            {"Each ton of recycled paper can save about how many mature trees?","17","3","7","25","Every ton of recycled paper needs about 17 mature trees to be produced."},
            {"Which material requires longest time to decompose in landfill?","Aluminum Can","Cigarette","Cotton","Nylon Clothes","Aluminum can is required the longest time to decompose in landfill among those. (about 200 years)"},
            {"Which materials requires shortest time to decompose in landfill?","Cotton","Cigarette","Nylon Clothes","Aluminum Can","Cotton is required the shortest time to decompose in landfill among those. (about 1-5 months)"},
            {"How long needed for plastic bottle decompose in landfill?","450","900","100","50","Plastic bottles take about 450 years to decompose."},
            {"The most waste in offices is","Paper Waste","Food Waste","Medical Waste","Combustion Waste","70 percent of the total waste in offices is paper waste."},
            {"Aluminum cans can actually be recycled and put back onto the shelf in just about","2 months","1 month","3 months","4 months","Aluminum cans can be recycled and back on shelf as a new can in as little as 60 days."},
            {"The average person generates _ pounds of solid waste every day.","4.4","2.7","6.8","8.3","The average person generates 4.4 pounds of solid waste every day."}};

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.play_quiz);

        backBtn = findViewById(R.id.backBtn);

        backBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent i = new Intent(PlayQuizActivity.this,
                        QuizActivity.class);
                startActivity(i);
            }
        });

        questionNo = findViewById(R.id.questionNo);
        question = findViewById(R.id.question);
        score = findViewById(R.id.score);
        ans1 = findViewById(R.id.ans1);
        ans2 = findViewById(R.id.ans2);
        ans3 = findViewById(R.id.ans3);
        ans4 = findViewById(R.id.ans4);

        int l = quizData.length;

        for (int i = 0; i < l; i++)
        {
            ArrayList<String> temp = new ArrayList<>();
            temp.add(quizData[i][0]);
            temp.add(quizData[i][1]);
            temp.add(quizData[i][2]);
            temp.add(quizData[i][3]);
            temp.add(quizData[i][4]);
            temp.add(quizData[i][5]);

            quizArray.add(temp);
        }

        nextQuestion();
    }

    public void nextQuestion()
    {
        questionNo.setText("Q" + qCount);
        Random r = new Random();

        // Generate random number between quiz size
        int randomNo = r.nextInt(quizArray.size());

        // Pick question
        ArrayList<String> quiz = quizArray.get(randomNo);

        // Set question and right answer
        // quizData: {"question","rightAns","ans","ans","ans,"explain"}
        question.setText(quiz.get(0));
        correctAns = quiz.get(1);
        explain = quiz.get(5);

        // Remove question from array list & shuffle the choices
        quiz.remove(0);
        quiz.remove(4);
        Collections.shuffle(quiz);

        // Set choices
        ans1.setText(quiz.get(0));
        ans2.setText(quiz.get(1));
        ans3.setText(quiz.get(2));
        ans4.setText(quiz.get(3));

        // Remove the displayed question from array
        quizArray.remove(randomNo);
    }

    public void checkAns(View view)
    {
        // Get clicked button
        Button ansBtn = findViewById(view.getId());
        String textBtn = ansBtn.getText().toString();
        String alertTitle;

        if (textBtn.equals(correctAns))
        {
            alertTitle = "Correct answer!";
            correctAnsCount++;
            score.setText("Score: " + String.valueOf(correctAnsCount));
        }

        else
        {
            alertTitle = "Incorrect answer!";
        }

        AlertDialogCreator.createAlertDialog(this, alertTitle, "Answer: " + correctAns + ".\n\n" + explain, "NEXT QUESTION",
                ad_positive_listener, null, null).show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case RequestCode.VIEW_QUIZ_RESULT_ACTIVITY:
                finish();
                break;
        }
    }
}