package edu.fullsail.mgms.cse.tictactoe.jessicajohn;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.View;
import android.widget.TextView;


import org.w3c.dom.Text;

import java.lang.reflect.Field;

public class MenuActivity extends AppCompatActivity implements View.OnTouchListener {

    private TextView mTxtViewPVP;
    private TextView mTxtViewCVC;
    private TextView mTxtViewPVC;

    AlertDialog.Builder mDialog;

    int mSelectDp;
    int mTitleDp;
    int mSubDp;

    GameActivty.GameMode mCurrMode = GameActivty.GameMode.PVP;
    GameActivty.GameDifficulty mCurrDiff = GameActivty.GameDifficulty.EASY;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        mTxtViewPVP = (TextView) findViewById(R.id.pvptext);
        mTxtViewCVC = (TextView) findViewById(R.id.cvctext);
        mTxtViewPVC = (TextView) findViewById(R.id.cvptext);
        mTxtViewPVP.setOnTouchListener(this);
        mTxtViewPVC.setOnTouchListener(this);
        mTxtViewCVC.setOnTouchListener(this);

        mSelectDp = (int)(getResources().getDimension((R.dimen.menu_option))/getResources().getDisplayMetrics().density);
        mTxtViewPVP.setTextSize(mSelectDp);
        mTitleDp = (int)(getResources().getDimension((R.dimen.menu_title))/getResources().getDisplayMetrics().density);
        mSubDp = (int)(getResources().getDimension((R.dimen.menu_subtitle))/getResources().getDisplayMetrics().density);
        findViewById(R.id.difficulty).setOnTouchListener(this);
        findViewById(R.id.Start).setOnTouchListener(this);
        findViewById(R.id.credit).setOnTouchListener(this);
        mDialog = new AlertDialog.Builder(this);
        mDialog.setTitle("Made By");
        mDialog.setMessage("Jessica J John\nMGMS | APM\n8/26/2017");
        mDialog.setPositiveButton(" OK ", new DialogInterface.OnClickListener()
        {
            public void onClick(DialogInterface _dialog, int id)
            {
                _dialog.dismiss();
            }
        });
    }


    @Override
    public boolean onTouch(View v, MotionEvent motionEvent) {

        if(v.getId() == R.id.Start && motionEvent.getAction() == MotionEvent.ACTION_DOWN){
            Intent i = new Intent(this, GameActivty.class);
            GameActivty.GameMode mode = mCurrMode;
            GameActivty.GameDifficulty diff = mCurrDiff;
            TextView mGameDiff = ((TextView) findViewById(R.id.difficulty));

            if(mGameDiff.getText().equals("Difficulty: Medium"))
                diff = GameActivty.GameDifficulty.MEDIUM;
            else if(mGameDiff.getText().equals("Difficulty: Hard"))
                diff = GameActivty.GameDifficulty.HARD;

            i.putExtra("mode", mode.ordinal());
            i.putExtra("diff", diff.ordinal());
            startActivity(i);
            finish();
        }

        else if(v.getId() == R.id.difficulty && motionEvent.getAction() == MotionEvent.ACTION_DOWN){

            TextView mGameDiff = ((TextView) (findViewById(R.id.difficulty)));

            if(mGameDiff.getText().equals("Difficulty: Medium"))
            {
                mGameDiff.setText(R.string.DHard);
                mCurrDiff = GameActivty.GameDifficulty.HARD;
            }
            else if(mGameDiff.getText().equals("Difficulty: Hard"))
            {
                mGameDiff.setText(R.string.DEasy);
                mCurrDiff = GameActivty.GameDifficulty.EASY;
            }
            else
            {
                mGameDiff.setText(R.string.DMedium);
                mCurrDiff = GameActivty.GameDifficulty.MEDIUM;
            }

        }

        else if(v.getId() == R.id.pvptext && motionEvent.getAction() == MotionEvent.ACTION_DOWN){
            mTxtViewCVC.setTextSize(mSubDp);
            mTxtViewPVC.setTextSize(mSubDp);
            mTxtViewPVP.setTextSize(mSelectDp);
            mCurrMode = GameActivty.GameMode.PVP;
        }

        else if(v.getId() == R.id.cvptext && motionEvent.getAction() == MotionEvent.ACTION_DOWN){
            mTxtViewCVC.setTextSize(mSubDp);
            mTxtViewPVP.setTextSize(mSubDp);
            mTxtViewPVC.setTextSize(mSelectDp);
            mCurrMode = GameActivty.GameMode.PVC;
        }

        else if(v.getId() == R.id.cvctext && motionEvent.getAction() == MotionEvent.ACTION_DOWN){
            mTxtViewPVC.setTextSize(mSubDp);
            mTxtViewPVP.setTextSize(mSubDp);
            mTxtViewCVC.setTextSize(mSelectDp);
            mCurrMode = GameActivty.GameMode.CVC;
        }

        else if(v.getId() == R.id.credit && motionEvent.getAction() == MotionEvent.ACTION_DOWN){

            mDialog.show();
        }

        return false;
    }
}
