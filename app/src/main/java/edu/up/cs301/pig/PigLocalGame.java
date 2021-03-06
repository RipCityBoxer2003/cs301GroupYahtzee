package edu.up.cs301.pig;

import edu.up.cs301.game.GamePlayer;
import edu.up.cs301.game.LocalGame;
import edu.up.cs301.game.actionMsg.GameAction;
import edu.up.cs301.game.infoMsg.GameState;

import android.util.Log;

// dummy comment, to see if commit and push work from srvegdahl account

/**
 * class PigLocalGame controls the play of the game
 *
 * @author Andrew M. Nuxoll, modified by Steven R. Vegdahl
 * @version February 2016
 */
public class PigLocalGame extends LocalGame {

    private PigGameState masterGameState;
    /**
     * This ctor creates a new game state
     */
    public PigLocalGame() {
        //TODO  You will implement this constructor
        this.masterGameState = new PigGameState();
    }

    /**
     * can the player with the given id take an action right now?
     */
    @Override
    protected boolean canMove(int playerIdx) {
        //TODO  You will implement this method
        if (playerIdx == masterGameState.getPlayerId()) {
            return true;
        } else {

            return false;
        }

    }

    /**
     * This method is called when a new action arrives from a player
     *
     * @return true if the action was taken or false if the action was invalid/illegal.
     */
    @Override
    protected boolean makeMove(GameAction action) {
        //TODO  You will implement this method

        //hold action
        if (action instanceof PigHoldAction) {

            if (masterGameState.getPlayerId() == 0) {
                masterGameState.setP0Score(masterGameState.getP0Score() + masterGameState.getRunningTotal());
            } else if (masterGameState.getPlayerId() == 1) {
                masterGameState.setP1Score(masterGameState.getP1Score() + masterGameState.getRunningTotal());
            }


            return true;

            //roll action
        } else if (action instanceof PigRollAction) {
            int rand = (int)(Math.random() * 6);
            if (rand == 1) {
                masterGameState.setRunningTotal(0);
                if (masterGameState.getPlayerId() == 0) {
                    masterGameState.setPlayerId(1);
                } else {
                    masterGameState.setPlayerId(0);
                }
            } else {
                masterGameState.setRunningTotal(masterGameState.getRunningTotal() + rand);
            }

            return true;
        }
            return false;


    }//makeMove

    /**
     * send the updated state to a given player
     */
    @Override
    protected void sendUpdatedStateTo(GamePlayer p) {
        //TODO  You will implement this method
        PigGameState updatedGameState = new PigGameState(masterGameState);
        p.sendInfo(updatedGameState);
    }//sendUpdatedSate

    /**
     * Check if the game is over
     *
     * @return
     * 		a message that tells who has won the game, or null if the
     * 		game is not over
     */
    @Override
    protected String checkIfGameOver() {
        //TODO  You will implement this method
        if (masterGameState.getP0Score() > 50) {
            return  "player 0 wins, Score: " + masterGameState.getP0Score();
        } else if (masterGameState.getP1Score() > 50) {
            return "player 1 wins, Score: " + masterGameState.getP1Score();
        } else {
            return null;
        }
    }

    //todo create yahtzee methods
    protected boolean selectKeepers() {
        return false;
    }

    protected boolean selectScoreBox() {
        return false;
    }

}// class PigLocalGame
