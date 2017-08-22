package edu.fullsail.mgms.cse.tictactoe.jessicajohn;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

public class MenuActivity extends AppCompatActivity implements View.OnTouchListener {

    Button diffButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        diffButton = (Button) findViewById(R.id.diffButton);

        diffButton.setOnTouchListener(this);
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        if(motionEvent.getAction() == motionEvent.ACTION_DOWN && view.getId() == R.id.diffButton){
            if(diffButton.getText().equals("Difficulty: Easy")){
                diffButton.setText(R.string.medium);
            }
            else if(diffButton.getText().equals("Difficulty: Medium")){
                diffButton.setText(R.string.hard);
            }
            else{
                diffButton.setText(R.string.easy);
            }
        }
        return false;
    }
}
