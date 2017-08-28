package edu.fullsail.mgms.cse.tictactoe.jessicajohn;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;
import java.util.Vector;

public class GameActivty extends AppCompatActivity implements View.OnTouchListener {

    public enum GameMode{
        PVP, PVC, CVC,
    };

    public enum GameDifficulty{
        HARD, EASY, MEDIUM
    };

    public enum Winner{
        TBD, X, O, DRAW
    };

    public enum GamePlayer{
        X, O, NONE
    };

    private ArrayList<XOView> mXOViews;
    private MiniMax mComputer1;
    private MiniMax.GameBoard mBoard;
    private GameMode mMode;
    private GameDifficulty mDiff;
    GamePlayer mWhosTurn = GamePlayer.X;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_activty);
        initGame();
    }
    void initGame()
    {
        mXOViews = new ArrayList<XOView>();

        mXOViews.add((XOView)findViewById(R.id.XOView1));
        mXOViews.add((XOView)findViewById(R.id.XOView2));
        mXOViews.add((XOView)findViewById(R.id.XOView3));
        mXOViews.add((XOView)findViewById(R.id.XOView4));
        mXOViews.add((XOView)findViewById(R.id.XOView5));
        mXOViews.add((XOView)findViewById(R.id.XOView6));
        mXOViews.add((XOView)findViewById(R.id.XOView7));
        mXOViews.add((XOView)findViewById(R.id.XOView8));
        mXOViews.add((XOView)findViewById(R.id.XOView9));

        for (int i = 0; i < mXOViews.size(); i++){
            mXOViews.get(i).setOnTouchListener(this);
        }

        Bundle b = getIntent().getExtras();
        mMode = GameMode.values()[b.getInt("mode")];
        mDiff = GameDifficulty.values()[b.getInt("diff")];

        if(mMode == GameMode.PVC || mMode == GameMode.CVC){
            mComputer1 = new MiniMax();
        }
        mBoard = new MiniMax.GameBoard();
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        if(mMode == GameMode.CVC){
            //Do CVC Logic
            CVCLogic();
            return false;
        }
        if(motionEvent.getAction() == MotionEvent.ACTION_DOWN && mMode == GameMode.PVC){
            //Do PVC Logic
            PVCLogic(view);
        }
        else if(motionEvent.getAction() == MotionEvent.ACTION_DOWN && mMode == GameMode.PVP){
            PVPLogic(view, mWhosTurn);
        }

        return false;
    }

    private void PVCLogic(View v){
        for(int i = 0; i < mXOViews.size(); i++) {

            if (v.getId() == mXOViews.get(i).getId()) {

                mXOViews.get(i).Drawing(GamePlayer.X);
                mXOViews.get(i).invalidate();

                if (i == 0) {
                    mBoard.mBoard[0][0] = GamePlayer.X;
                } else if (i == 1) {
                    mBoard.mBoard[0][1] = GamePlayer.X;
                } else if (i == 2) {
                    mBoard.mBoard[0][2] = GamePlayer.X;
                } else if (i == 3) {
                    mBoard.mBoard[1][0] = GamePlayer.X;
                } else if (i == 4) {
                    mBoard.mBoard[1][1] = GamePlayer.X;
                } else if (i == 5) {
                    mBoard.mBoard[1][2] = GamePlayer.X;
                } else if (i == 6) {
                    mBoard.mBoard[2][0] = GamePlayer.X;
                } else if (i == 7) {
                    mBoard.mBoard[2][1] = GamePlayer.X;
                } else if (i == 8) {
                    mBoard.mBoard[2][2] = GamePlayer.X;
                }
            }
        }

            if(mBoard.isWinner(GamePlayer.X)){
                Intent intent = new Intent(this, OverActivity.class);
                Winner mWin = Winner.X;
                intent.putExtra("Win", mWin.ordinal());
                startActivity(intent);
                finish();
            }

            if(!mBoard.isDraw()){
                MiniMax.ComputerMove mMove = mComputer1.Run(GamePlayer.O, mBoard, mDiff);
                PlaceMove(mMove);
            }

            if(mBoard.isWinner(GamePlayer.O))
            {
                Intent i = new Intent(this, OverActivity.class);
                Winner win = Winner.O;
                i.putExtra("Win", win.ordinal());
                startActivity(i);
                finish();
            }
            if(mBoard.isDraw())
            {
                Intent i = new Intent(this, OverActivity.class);
                Winner win = Winner.DRAW;
                i.putExtra("Win", win.ordinal());
                startActivity(i);
                finish();
            }
    }

    private void CVCLogic(){
        if(!mBoard.isDraw() && !mBoard.isWinner(GamePlayer.X) && !mBoard.isWinner(GamePlayer.O))
        {
            MiniMax.ComputerMove move = mComputer1.Run(mWhosTurn, mBoard, mDiff);
            PlaceMove(move, mWhosTurn);

            if(mWhosTurn == GamePlayer.X)
                mWhosTurn = GamePlayer.O;
            else
                mWhosTurn = GamePlayer.X;

        }

        if(mBoard.isWinner(GamePlayer.O))
        {
            Intent i = new Intent(this, GamePlayer.class);
            Winner win = Winner.O;
            i.putExtra("Win", win.ordinal());
            startActivity(i);
            finish();
        }
        else if(mBoard.isWinner(GamePlayer.X))
        {
            Intent i = new Intent(this, OverActivity.class);
            Winner win = Winner.X;
            i.putExtra("Win", win.ordinal());
            startActivity(i);
            finish();
        }
        else if(mBoard.isDraw())
        {
            Intent i = new Intent(this, OverActivity.class);
            Winner win = Winner.DRAW;
            i.putExtra("Win", win.ordinal());
            startActivity(i);
            finish();
        }
    }

    private void PVPLogic(View view, GamePlayer m_whosTurn)
    {
        for (int i = 0; i < mXOViews.size(); i++)
        {
            if(view.getId() == mXOViews.get(i).getId())
            {
                mXOViews.get(i).Drawing(m_whosTurn);
                mXOViews.get(i).invalidate();
                if(i == 0)
                    mBoard.mBoard[0][0] = m_whosTurn;
                else if(i == 1)
                    mBoard.mBoard[0][1] = m_whosTurn;
                else if(i == 2)
                    mBoard.mBoard[0][2] = m_whosTurn;
                else if(i == 3)
                    mBoard.mBoard[1][0] = m_whosTurn;
                else if(i == 4)
                    mBoard.mBoard[1][1] = m_whosTurn;
                else if(i == 5)
                    mBoard.mBoard[1][2] = m_whosTurn;
                else if(i == 6)
                    mBoard.mBoard[2][0] = m_whosTurn;
                else if(i == 7)
                    mBoard.mBoard[2][1] = m_whosTurn;
                else if(i == 8)
                    mBoard.mBoard[2][2] = m_whosTurn;


            }
        }
        if(mBoard.isWinner(GamePlayer.O))
        {
            Intent i = new Intent(this, OverActivity.class);
            Winner win = Winner.O;
            i.putExtra("Win", win.ordinal());
            startActivity(i);
            finish();
        }
        if(mBoard.isWinner(GamePlayer.X))
        {
            Intent i = new Intent(this, OverActivity.class);
            Winner win = Winner.X;
            i.putExtra("Win", win.ordinal());
            startActivity(i);
            finish();
        }
        if(mBoard.isDraw())
        {
            Intent i = new Intent(this, OverActivity.class);
            Winner win = Winner.DRAW;
            i.putExtra("Win", win.ordinal());
            startActivity(i);
            finish();
        }

        if(mWhosTurn == GamePlayer.X)
           mWhosTurn = GamePlayer.O;
        else
            mWhosTurn = GamePlayer.X;
    }

    void PlaceMove(MiniMax.ComputerMove mMove){
        if(mMove != null){
            if(mMove.mRow == 0 && mMove.mColumn == 0){
                mXOViews.get(0).Drawing(GamePlayer.O);
                mXOViews.get(0).invalidate();
                mBoard.mBoard[0][0] = GamePlayer.O;
            }

            if(mMove.mRow == 0 && mMove.mColumn == 1){
                mXOViews.get(1).Drawing(GamePlayer.O);
                mXOViews.get(1).invalidate();
                mBoard.mBoard[0][1] = GamePlayer.O;
            }

            if(mMove.mRow == 0 && mMove.mColumn == 2){
                mXOViews.get(2).Drawing(GamePlayer.O);
                mXOViews.get(2).invalidate();
                mBoard.mBoard[0][2] = GamePlayer.O;
            }

            if(mMove.mRow == 1 && mMove.mColumn == 0){
                mXOViews.get(3).Drawing(GamePlayer.O);
                mXOViews.get(3).invalidate();
                mBoard.mBoard[1][0] = GamePlayer.O;
            }
            if(mMove.mRow == 1 && mMove.mColumn == 1){
                mXOViews.get(4).Drawing(GamePlayer.O);
                mXOViews.get(4).invalidate();
                mBoard.mBoard[1][1] = GamePlayer.O;
            }
            if(mMove.mRow == 1 && mMove.mColumn == 2){
                mXOViews.get(5).Drawing(GamePlayer.O);
                mXOViews.get(5).invalidate();
                mBoard.mBoard[1][2] = GamePlayer.O;
            }
            if(mMove.mRow == 2 && mMove.mColumn == 0){
                mXOViews.get(6).Drawing(GamePlayer.O);
                mXOViews.get(6).invalidate();
                mBoard.mBoard[2][0] = GamePlayer.O;
            }
            if(mMove.mRow == 2 && mMove.mColumn == 1){
                mXOViews.get(7).Drawing(GamePlayer.O);
                mXOViews.get(7).invalidate();
                mBoard.mBoard[2][1] = GamePlayer.O;
            }
            if(mMove.mRow == 2 && mMove.mColumn == 2){
                mXOViews.get(8).Drawing(GamePlayer.O);
                mXOViews.get(8).invalidate();
                mBoard.mBoard[2][2] = GamePlayer.O;
            }
        }
    }

    void PlaceMove(MiniMax.ComputerMove mMove, GamePlayer mWho){
        if(mMove != null){
            if(mMove.mRow == 0 && mMove.mColumn == 0){
                mXOViews.get(0).Drawing(mWho);
                mXOViews.get(0).invalidate();
                mBoard.mBoard[0][0] = mWho;
            }
            if(mMove.mRow == 0 && mMove.mColumn == 1){
                mXOViews.get(1).Drawing(mWho);
                mXOViews.get(1).invalidate();
                mBoard.mBoard[0][1] = mWho;
            }
            if(mMove.mRow == 0 && mMove.mColumn == 2){
                mXOViews.get(2).Drawing(mWho);
                mXOViews.get(2).invalidate();
                mBoard.mBoard[0][2] = mWho;
            }
            if(mMove.mRow == 1 && mMove.mColumn == 0){
                mXOViews.get(3).Drawing(mWho);
                mXOViews.get(3).invalidate();
                mBoard.mBoard[1][0] = mWho;
            }
            if(mMove.mRow == 1 && mMove.mColumn == 1){
                mXOViews.get(4).Drawing(mWho);
                mXOViews.get(4).invalidate();
                mBoard.mBoard[1][1] = mWho;
            }
            if(mMove.mRow == 1 && mMove.mColumn == 2){
                mXOViews.get(5).Drawing(mWho);
                mXOViews.get(5).invalidate();
                mBoard.mBoard[1][2] = mWho;
            }
            if(mMove.mRow == 2 && mMove.mColumn == 0){
                mXOViews.get(6).Drawing(mWho);
                mXOViews.get(6).invalidate();
                mBoard.mBoard[2][0] = mWho;
            }
            if(mMove.mRow == 2 && mMove.mColumn == 1){
                mXOViews.get(7).Drawing(mWho);
                mXOViews.get(7).invalidate();
                mBoard.mBoard[2][1] = mWho;
            }
            if(mMove.mRow == 2 && mMove.mColumn == 2){
                mXOViews.get(8).Drawing(mWho);
                mXOViews.get(8).invalidate();
                mBoard.mBoard[2][2] = mWho;
            }
        }
    }

}
