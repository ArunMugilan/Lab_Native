package com.example.mindsharpener;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private EditText answerText;
    private Button button;
    private RadioGroup radioGroup;
    private int pointsValue = 0;
    private Random random = new Random();
    private TextView firstNo, Oprtr, secNo, points;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize views
        firstNo = findViewById(R.id.firstNumber);
        Oprtr = findViewById(R.id.oprtr);
        secNo = findViewById(R.id.secNumber);
        points = findViewById(R.id.Points);
        answerText = findViewById(R.id.answer);
        button = findViewById(R.id.button);
        radioGroup = findViewById(R.id.levelRG);

        // Set a listener for the Check button
        button.setOnClickListener(view -> checkAnswer());

        // Set a listener for the RadioGroup to handle level selection
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
                nextQuestion();
            }
        });

        // Initial question update
        nextQuestion();
    }

    // Helper method to convert operator string to corresponding integer
    private int getOperator(String bodmas) {
        switch (bodmas) {
            case "+":
                return 0;
            case "-":
                return 1;
            case "*":
                return 2;
            case "/":
                return 3;
            default:
                return 0;
        }
    }

    // Method to update the displayed question based on the selected level
    private void nextQuestion() {
        int selectedLevelId = radioGroup.getCheckedRadioButtonId();
        RadioButton selectedLevelRadioButton = findViewById(selectedLevelId);

        // Set the maximum digits based on the selected level
        int level;
        if (selectedLevelRadioButton != null) {
            String selectedLevel = selectedLevelRadioButton.getText().toString();
            switch (selectedLevel) {
                case "i3":
                    level = 1;
                    break;
                case "i5":
                    level = 2;
                    break;
                case "i7":
                    level = 3;
                    break;
                default:
                    level = 1;
            }

            // Generate random numbers
            int firstNumber = random.nextInt((int) Math.pow(10, level));
            int secondNumber = random.nextInt((int) Math.pow(10, level));

            // Display the numbers in TextViews
            firstNo.setText(String.valueOf(firstNumber));
            secNo.setText(String.valueOf(secondNumber));

            // Generate a random operator (0 to 3)
            int oprtr = random.nextInt(4);

            // Update the operator based on the random number
            switch (oprtr) {
                case 0:
                    Oprtr.setText("+");
                    break;
                case 1:
                    Oprtr.setText("-");
                    break;
                case 2:
                    Oprtr.setText("*");
                    break;
                case 3:
                    Oprtr.setText("/");
                    break;
            }

            // Clear the EditText
            answerText.getText().clear();
        }
    }

    // Method to check the answer and update points
    private void checkAnswer() {
        // Get the correct answer based on the displayed question
        int firstNumber = Integer.parseInt(firstNo.getText().toString());
        int secondNumber = Integer.parseInt(secNo.getText().toString());
        int operator = getOperator(Oprtr.getText().toString());
        int correctAnswer = calculateAnswer(firstNumber, secondNumber, operator);

        // Get the user's answer from the EditText
        String answer = answerText.getText().toString();

        // Check if the user's answer is correct
        if (!answer.isEmpty()) {
            int userAnswer = Integer.parseInt(answer);
            if (userAnswer == correctAnswer) {
                // Increment points for correct answer
                pointsValue++;
            } else {
                // Decrement points for wrong answer
                pointsValue--;
            }
        }

        // Update the points TextView
        points.setText(String.valueOf(pointsValue));

        // Update the question for the next round
        nextQuestion();
    }


    // Helper method to calculate the answer based on the operator
    private int calculateAnswer(int firstNumber, int secondNumber, int operator) {
        if (operator == 0) {
            return firstNumber + secondNumber;
        } else if (operator == 1) {
            return firstNumber - secondNumber;
        } else if (operator == 2) {
            return firstNumber * secondNumber;
        } else if (operator == 3) {
            // Ensure non-zero divisor for division
            return (secondNumber != 0) ? firstNumber / secondNumber : 0;
        } else {
            return 0;
        }
    }

}

