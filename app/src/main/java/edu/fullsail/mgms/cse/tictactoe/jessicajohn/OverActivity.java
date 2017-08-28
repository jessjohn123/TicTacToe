package edu.fullsail.mgms.cse.tictactoe.jessicajohn;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

public class OverActivity extends AppCompatActivity implements View.OnTouchListener {

    private TextView mFinalText;
    private GameActivty.Winner m_WhoWon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_over);

        Context appContext = getApplicationContext();

        mFinalText = (TextView)findViewById(R.id.whoWon);
        Bundle b = getIntent().getExtras();
        m_WhoWon = GameActivty.Winner.values()[b.getInt("Win")];

        if(m_WhoWon == GameActivty.Winner.X)
            mFinalText.setText(R.string.X);
        else if(m_WhoWon == GameActivty.Winner.O)
            mFinalText.setText(R.string.O);
        else
            mFinalText.setText(R.string.Draw);

        mFinalText.setOnTouchListener(this);
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        if(view.getId() == mFinalText.getId()){
            Intent i = new Intent(this, MenuActivity.class);
            startActivity(i);
            finish();
        }
        return false;
    }
}
