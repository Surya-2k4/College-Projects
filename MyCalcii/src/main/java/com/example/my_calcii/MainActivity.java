package com.example.my_calcii;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    public TextView screen;
    private Button AC,power,back,div,mul,add,sub,one,two,three,four,five,six,seven,eight,nine,zero,dot,ans,eqaul;
    private String input,answer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        screen=findViewById(R.id.screen);
        AC=findViewById(R.id.AC);
        power=findViewById(R.id.power);
        back=findViewById(R.id.back);
        div=findViewById(R.id.div);
        mul=findViewById(R.id.mul);
        add=findViewById(R.id.add);
        sub=findViewById(R.id.sub);
        one=findViewById(R.id.one);
        two=findViewById(R.id.two);
        three=findViewById(R.id.three);
        four=findViewById(R.id.four);
        five=findViewById(R.id.five);
        six=findViewById(R.id.six);
        seven=findViewById(R.id.seven);
        eight=findViewById(R.id.eight);
        nine=findViewById(R.id.nine);
        zero=findViewById(R.id.zero);
        dot=findViewById(R.id.dot);
        ans=findViewById(R.id.ans);
        eqaul=findViewById(R.id.equal);

    }
    public  void ButtonClick(View view){
        Button button=(Button) view;
        String data=button.getText().toString();
        switch (data){
            case "AC":
                Solve();
                input="";
                break;
            case "ans":
                Solve();
                input+=answer;
                break;
            case  "X":
                Solve();
                input+="*";
                break;
            case  "^":
                Solve();
                input+="^";
                break;
            case  "=":
                Solve();
                answer=input;
                break;
            case  "~":
                String newTex=input.substring(0,input.length()-1);
                input=newTex;
                break;
            default:
                if(input==null){
                    input="";
                }
                if(data.equals("+")|| data.equals("-") || data.equals("/")){
                    Solve();
                }
                input+=data;
        }
        screen.setText(input);
    }
    private void Solve(){
        if(input.split("\\*").length==2){
            String number[]=input.split("\\*");
            try {
                double mul=Double.parseDouble(number[0]) * Double.parseDouble(number[1]);
                input =mul+"";
            }
           catch (Exception e){

           }
        }
        else if(input.split("/").length==2){
            String number[]=input.split("/");
            try {
                double div=Double.parseDouble(number[0]) / Double.parseDouble(number[1]);
                input =div+"";
            }
            catch (Exception e){

            }
        }
        else if(input.split("\\^").length==2){
            String number[]=input.split("\\^");
            try {
                double pow=Math.pow(Double.parseDouble(number[0]) , Double.parseDouble(number[1]));
                input =pow+"";
            }
            catch (Exception e){

            }
        }
        else if(input.split("\\+").length==2){
            String number[]=input.split("\\+");
            try {
                double add=Double.parseDouble(number[0]) + Double.parseDouble(number[1]);
                input =add+"";
            }
            catch (Exception e){

            }
        }
        else if(input.split("-").length>1){//this is only for substraction that may have  more than 2 spit char in case of -4-8
            String number[]=input.split("\\-");
            //to substract from the negative like -5-8
            if(number[0]=="" && number.length==2){
                number[0]=0+"";
            }
            try {
                double sub=0;
                if(number.length==2) {
                    sub = Double.parseDouble(number[0]) - Double.parseDouble(number[1]);
                }
                else if(number.length==3){
                    sub = -Double.parseDouble(number[1]) - Double.parseDouble(number[2]);
                }
                input =sub+"";
            }
            catch (Exception e){

            }
        }
        //to remove the lst digit .0 from the integer result like 9.0 instead of 9.0
        String n[]=input.split("\\.");
        if(n.length>1){
            if(n[1].equals("0")){
                input=n[0];
            }
        }
        screen.setText(input);
    }
}