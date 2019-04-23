package org.jmsalonen.tictactoedemo;

import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class GameFragment extends Fragment implements View.OnClickListener {

    Button[] buttons; // game board tiles
    TextView info; // Textfield that shows the result at the end of the game.
    int[] board; // board tile values. 0 = empty, 1 = owned by X, 2 = owned by O
    int turn; // 1 = X, 2 = O
    int winner; // 0 = game in progress, 1 = X is winner, 2 = O is winner

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        initGame();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.game_board, container, false);
        initViews(rootView);
        updateBoard();
        return rootView;
    }

    private void initViews(View rootView) {
        info = rootView.findViewById(R.id.gameInfo);
        info.setText("");
        buttons = new Button[9];
        // set game_board.xml buttons to buttons[]
        for (int i = 0; i < buttons.length; ++i) {
            String buttonId = "button" + (i+1);
            // find button ID
            int resID = getResources().getIdentifier(buttonId, "id", "org.jmsalonen.tictactoedemo");
            buttons[i] = rootView.findViewById(resID);
            buttons[i].setOnClickListener(this);
        }
    }

    private void initGame() {
        board = new int[9];
        turn = 1;
        winner = 0;
    }

    private void restartGame() {
        initGame();
        initViews(getView());
    }

    @Override
    public void onClick(View view) {
        if (gameEnded()) {
            restartGame();
        }
        else {
            for (int i = 0; i < buttons.length; ++i) {
                if (buttons[i].getId() == view.getId()) {
                    makeMove(i);
                }
            }
        }
        updateBoard();
    }

    private void makeMove(int move) {
        if (board[move] == 0) {
            board[move] = turn;
            if (!gameEnded())
                nextTurn();
        }
    }

    // set board[] values to buttons[]
    private void updateBoard() {
        for (int i = 0; i < board.length; ++i) {
            if (board[i] == 1) {
                buttons[i].setText("X");
            }
            else if (board[i] == 2) {
                buttons[i].setText("O");
            }
            else
                buttons[i].setText("");
        }
        if (gameEnded()) {
            if (winner == 1)
                info.setText("Winner is X");
            else if (winner == 2)
                info.setText("Winner is O");
            else
                info.setText("Tie");
        }
    }

    private boolean gameEnded() {
        if (board[0] == board[1] && board[1] == board[2])
            if (board[0] != 0) {
                winner = turn;
                return true;
            }
        if (board[3] == board[4] && board[4] == board[5])
            if (board[3] != 0) {
                winner = turn;
                return true;
            }
        if (board[6] == board[7] && board[7] == board[8])
            if (board[6] != 0) {
                winner = turn;
                return true;
            }

        if (board[0] == board[3] && board[3] == board[6])
            if (board[0] != 0) {
                winner = turn;
                return true;
            }
        if (board[1] == board[4] && board[4] == board[7])
            if (board[1] != 0) {
                winner = turn;
                return true;
            }
        if (board[2] == board[5] && board[5] == board[8])
            if (board[2] != 0) {
                winner = turn;
                return true;
            }

        if (board[0] == board[4] && board[4] == board[8])
            if (board[0] != 0) {
                winner = turn;
                return true;
            }
        if (board[6] == board[4] && board[4] == board[2])
            if (board[6] != 0) {
                winner = turn;
                return true;
            }

        // check if board is full
        for (int i = 0; i < 9; ++i) {
            if (board[i] == 0)
                return false;
        }

        return true;
    }

    private void nextTurn() {
        turn = turn == 1 ? 2 : 1;
    }

    // save progress to string
    public String getState() {
        StringBuilder builder = new StringBuilder();
        builder.append(turn);
        builder.append(',');
        builder.append(winner);
        builder.append(',');

        for (int i = 0; i < board.length; ++i) {
            builder.append(board[i]);
            builder.append(',');
        }
        return builder.toString();
    }

    // restore progress from string
    public void setState(String gameData) {
        String[] data = gameData.split(",");
        int iterator = 0;
        turn = Integer.parseInt(data[iterator++]);
        winner = Integer.parseInt(data[iterator++]);

        for (int i = 0; i < board.length; ++i) {
            board[i] = Integer.parseInt(data[iterator++]);
        }
        updateBoard();
    }

}
