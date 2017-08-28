package edu.fullsail.mgms.cse.tictactoe.jessicajohn;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by ushnashah on 8/27/17.
 */

public class MiniMax {

    public class ComputerMove{
        int mRow;
        int mColumn;
        int mRank;
        boolean mCreated;

        ComputerMove(int _row, int _col){
            mRow = _row;
            mColumn = _col;
            mRank = 0;
            mCreated = true;
        }
    }

    public static class GameBoard{

        GameActivty.GamePlayer[][] mBoard = new GameActivty.GamePlayer[3][3];

        GameBoard(){
            for(int r = 0; r < 3; r++ ){
                for(int c = 0; c < 3; c++){
                    mBoard[r][c] = GameActivty.GamePlayer.NONE;
                }
            }
        }

        boolean isItAValidMove(GameActivty.GamePlayer mWhosTurnIsIt, int mRow, int mCol){
            //Check to see if this is a valid move
            if(mBoard[mRow][mCol] == GameActivty.GamePlayer.NONE)
                return true;
            return false;
        }

        public void copyThis(GameBoard mCopy){
            //Make a deep copy of the game board
            for(int r = 0; r < 3; r++){
                for(int c = 0; c < 3; c++){
                    if(mCopy.mBoard[r][c] == GameActivty.GamePlayer.O)
                        mBoard[r][c] = GameActivty.GamePlayer.O;
                    else if(mCopy.mBoard[r][c] == GameActivty.GamePlayer.X)
                        mBoard[r][c] = GameActivty.GamePlayer.X;
                    else
                        mBoard[r][c] = GameActivty.GamePlayer.NONE;
                }
            }
        }

        public void makeYourMove(GameActivty.GamePlayer mWhosTurnIsIt, int mRow, int mCol){
            //Check to see whos's turn is it
            mBoard[mRow][mCol] = mWhosTurnIsIt;
        }

        public boolean isTerminal(){
            //Check for empty cells
            for(int r = 0; r < 3; r++){
                for(int c = 0; c < 3; c++){
                    if(mBoard[r][c] == GameActivty.GamePlayer.NONE)
                        return false;
                }
            }
            return true;
        }

        public boolean isWinner(GameActivty.GamePlayer whoIsIt){
            //Check for the winning strikes
            if(mBoard[0][0] == whoIsIt && mBoard[0][1] == whoIsIt && mBoard[0][2] == whoIsIt)
                return true;
            if(mBoard[1][0] == whoIsIt && mBoard[1][1] == whoIsIt && mBoard[1][2] == whoIsIt)
                return true;
            if(mBoard[2][0] == whoIsIt && mBoard[2][1] == whoIsIt && mBoard[2][2] == whoIsIt)
                return true;
            if(mBoard[0][0] == whoIsIt && mBoard[1][0] == whoIsIt && mBoard[2][0] == whoIsIt)
                return true;
            if(mBoard[0][1] == whoIsIt && mBoard[1][1] == whoIsIt && mBoard[2][1] == whoIsIt)
                return true;
            if(mBoard[0][2] == whoIsIt && mBoard[1][2] == whoIsIt && mBoard[2][2] == whoIsIt)
                return true;
            if(mBoard[0][0] == whoIsIt && mBoard[1][1] == whoIsIt && mBoard[2][2] == whoIsIt)
                return true;
            if(mBoard[0][2] == whoIsIt && mBoard[1][1] == whoIsIt && mBoard[2][0] == whoIsIt)
                return true;
            return false;
        }

        public boolean isDraw()
        {
            //If all the cells are occupied and none have the winning strikes consider draw
            if(isTerminal())
                if(!isWinner(GameActivty.GamePlayer.X))
                    if(!isWinner(GameActivty.GamePlayer.O))
                        return true;
            return false;
        }

        public boolean isEmpty(){
            for(int r = 0; r < 3; r++){
                for(int c = 0; c < 3; c++){
                    if(mBoard[r][c] != GameActivty.GamePlayer.NONE)
                        return false;
                }
            }
            return true;
        }
    }

    public ComputerMove Run(GameActivty.GamePlayer mWhosTurnIsIt, GameBoard mGameBoard, GameActivty.GameDifficulty mGameDiff){
        ComputerMove mNextMove;

        if(mGameBoard.isEmpty()){
            //Generate random move
            Random mRandom = new Random();

            while(true){
                int r = mRandom.nextInt(3);
                int c = mRandom.nextInt(3);
                if(mGameBoard.isItAValidMove(mWhosTurnIsIt,r,c)){
                    mNextMove = new ComputerMove(r, c);
                    return mNextMove;
                }
            }
        }

        if(mGameDiff == GameActivty.GameDifficulty.MEDIUM)
            mNextMove = GetBestMove(mWhosTurnIsIt, mGameBoard, 1);
        else if(mGameDiff == GameActivty.GameDifficulty.HARD)
            mNextMove = GetBestMove(mWhosTurnIsIt, mGameBoard, 2);
        else{
            Random rand = new Random();
            while(true){
                int r = rand.nextInt(3);
                int c = rand.nextInt(3);
                if(mGameBoard.isItAValidMove(mWhosTurnIsIt,r,c)){
                    mNextMove = new ComputerMove(r, c);
                    break;
                }
            }
        }
        return mNextMove;
    }

    private ComputerMove GetBestMove(GameActivty.GamePlayer mWhosTurnIsIt, GameBoard mGameBoard, int mDepth){
        //Minimax algorithm logic
        ComputerMove mBestMove = null;
        ArrayList<ComputerMove> mMoves = new ArrayList<ComputerMove>();
        GameBoard mNewState = new GameBoard();

        for(int r = 0; r < 3; r++){
            for(int c = 0; c < 3; c++){
                if(mGameBoard.isItAValidMove(mWhosTurnIsIt,r,c));
                    mMoves.add(new ComputerMove(r,c));
            }
        }

        int i = 0;
        try{
            for(; i < mMoves.size(); i++ ){
                mNewState.copyThis(mGameBoard);
                mNewState.makeYourMove(mWhosTurnIsIt, mMoves.get(i).mRow,mMoves.get(i).mColumn);

                if(mNewState.isWinner(mWhosTurnIsIt) || mNewState.isDraw() || mDepth == 0){
                    mMoves.get(i).mRank = Evaluate(mNewState);
                }
                else{
                    ComputerMove move = GetBestMove(GetNextPlayer(mWhosTurnIsIt), mNewState, mDepth - 1);
                    if(move == null)
                        return null;
                    mMoves.get(i).mRank = move.mRank;
                }

                //Player 1 val = 1
                if(mWhosTurnIsIt == GameActivty.GamePlayer.X){
                    if(mBestMove == null || mMoves.get(i).mRank > mBestMove.mRank){
                        mBestMove = mMoves.get(i);
                    }
                }
                //Player 2 val = -1
                else if(mBestMove == null || mMoves.get(i).mRank < mBestMove.mRank){
                        mBestMove = mMoves.get(i);
                }
            }
        }catch(Exception e){
            System.out.println("Error occured while looping at depth" + " " + mDepth + " " + " at loop" + " " + i);
        }

        return mBestMove;
    }

    private int Evaluate(GameBoard mGameBoard){
        int mScore = 0;
        int mNumX = 0;
        int mNumO = 0;
        int mNumNone = 0;

        for(int r = 0; r < 3; r++){
            for(int c = 0; c < 3; c++){
                if(mGameBoard.mBoard[r][c] == GameActivty.GamePlayer.X)
                    mNumX++;
                else if(mGameBoard.mBoard[r][c] == GameActivty.GamePlayer.O)
                    mNumO++;
                else
                    mNumNone++;
            }
        }

        mScore += mNumX + (-mNumO);

        //Checking to if there is any winner
        if(mGameBoard.isWinner(GameActivty.GamePlayer.X))
            mScore += mNumNone * 10000;
        else if(mGameBoard.isWinner(GameActivty.GamePlayer.O))
            mScore += mNumNone * 10000;

        return mScore;
    }

    private GameActivty.GamePlayer GetNextPlayer(GameActivty.GamePlayer mPlayer){
        if(mPlayer == GameActivty.GamePlayer.X)
            return GameActivty.GamePlayer.O;
        else
            return GameActivty.GamePlayer.X;
    }
}
