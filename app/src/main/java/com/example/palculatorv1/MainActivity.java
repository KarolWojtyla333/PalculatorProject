package com.example.palculatorv1;

import androidx.appcompat.app.AppCompatActivity;
import android.widget.TextView;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.button.MaterialButton;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.Scriptable;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    int parenthesisFlag = 0;
    int smileFlag = 0;
    TextView resultWindow,solutionWindow;
    MaterialButton buttonAC,buttonC,buttonParenthesis;
    MaterialButton buttonDivide,buttonMultiply,buttonPlus,
            buttonMinus,buttonEqual;
    MaterialButton button0,button1,button2,button3,button4,button5,button6,button7,button8,button9;
    MaterialButton buttonSmile,buttonDecimal;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        resultWindow = findViewById(R.id.result_window);
        solutionWindow = findViewById(R.id.solution_window);

        assignId(buttonAC,R.id.button_ac);
        assignId(buttonC,R.id.button_c);
        assignId(buttonParenthesis,R.id.button_parenthesis);
        assignId(buttonDivide,R.id.button_divide);
        assignId(buttonMultiply,R.id.button_multiply);
        assignId(buttonPlus,R.id.button_plus);
        assignId(buttonMinus,R.id.button_minus);
        assignId(buttonEqual,R.id.button_equal);
        assignId(button0,R.id.button_0);
        assignId(button1,R.id.button_1);
        assignId(button2,R.id.button_2);
        assignId(button3,R.id.button_3);
        assignId(button4,R.id.button_4);
        assignId(button5,R.id.button_5);
        assignId(button6,R.id.button_6);
        assignId(button7,R.id.button_7);
        assignId(button8,R.id.button_8);
        assignId(button9,R.id.button_9);
        assignId(buttonSmile,R.id.button_smile);
        assignId(buttonDecimal,R.id.button_decimal);
    }

    void assignId(MaterialButton btn, int id){
        btn = findViewById(id);
        btn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        MaterialButton button = (MaterialButton) view;
        String buttonText = button.getText().toString();
        String dataToCalculate = solutionWindow.getText().toString();

        if (buttonText.equals("AC")) {
            solutionWindow.setText("");
            resultWindow.setText("0");
            return;
        } if (buttonText.equals("C")){
            buttonText = "";
            dataToCalculate = dataToCalculate.substring(0,dataToCalculate.length()-1);
        }
        if (buttonText.equals("()")){
            if (parenthesisFlag == 0) {
                buttonText = ("(");
                parenthesisFlag = 1;
            } else {
                buttonText = (")");
                parenthesisFlag = 0;
            }
        }
        if (buttonText.equals("☻")){
            if (smileFlag == 0) {
                solutionWindow.setText("");
                resultWindow.setText("Have a nice day!");
            }
            if (smileFlag == 1) {
                solutionWindow.setText("");
                resultWindow.setText("You rock!");
            }
            if (smileFlag == 2) {
                solutionWindow.setText("");
                resultWindow.setText("Smile!");
            }
            if (smileFlag == 3) {
                solutionWindow.setText("");
                resultWindow.setText("Trust the process!");
                smileFlag = -1;
            }
            smileFlag += 1;
            return;
        }
        if (buttonText.equals("=")){
            solutionWindow.setText(resultWindow.getText());
            return;
        } else {
            dataToCalculate = dataToCalculate + buttonText;
        }

        solutionWindow.setText(dataToCalculate);
        String finalResult = getResult(dataToCalculate);

        if (!finalResult.equals("Err")){
            resultWindow.setText(finalResult);
        }
    }

    String getResult(String data){
        try{
            Context context = Context.enter();
            context.setOptimizationLevel(-1);
            Scriptable scriptable = context.initStandardObjects();
            String finalResult = context.evaluateString(scriptable,data,"Javascript",
                    1,null).toString();
            if (finalResult.endsWith(".0")){
                finalResult = finalResult.replace(".0","");
            }
            return finalResult;
        } catch (Exception e) {
            return "Err";
        }
    }
}