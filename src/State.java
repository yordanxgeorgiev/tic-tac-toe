import java.util.ArrayList;

public class State {
    private static final int boardWidth = 3;
    private static final char emptyChar = '-';
    private static final char playerOneChar = 'X';
    private static final char playerTwoChar = 'O';
    private final char[][] board;
    private int utility;
    private State action;
    private boolean playerToMove;
    private int depth;

    public State()
    {
        board = new char[boardWidth][boardWidth];
        for(int i = 0 ; i < boardWidth; i++)
        {
            for(int j = 0; j < boardWidth; j++)
            {
                board[i][j] = emptyChar;
            }
        }
    }

    public State(char[][] new_board, boolean player, int new_depth)
    {
        board = new_board;
        playerToMove = player;
        depth = new_depth;
    }

    public State place(int row, int col)
    {
        char symbol;
        if(playerToMove) symbol = playerTwoChar;
        else symbol = playerOneChar;

        char[][] new_board = new char[boardWidth][boardWidth];
        for(int i = 0; i < boardWidth; i++)
        {
            System.arraycopy(board[i], 0, new_board[i], 0, boardWidth);
        }
        if(new_board[row][col] != emptyChar)
        {
            System.out.println("Error, field taken! (invalid move)");
            System.exit(-1);
        }
        new_board[row][col] = symbol;

        return new State(new_board, !playerToMove, depth+1);
    }

    public void print()
    {
        for(int i = 0 ; i < boardWidth; i++)
        {
            System.out.print(" ");
            for(int j = 0; j < boardWidth; j++)
            {
                System.out.print(board[i][j] + " ");
            }
            System.out.println();
        }
    }

    public boolean is_terminal()
    {
        // checking the diagonals
        if(board[1][1] != emptyChar &&
                ((board[0][0] == board[1][1] && board[1][1] == board[2][2]) ||
                (board[2][0] == board[1][1] && board[1][1] == board[0][2])))
        {
            if(board[1][1] == playerOneChar) utility = 1;
            else utility = -1;
            return true;
        }
        
        // checking rows and columns
        for(int i = 0; i < boardWidth; i++)
        {
            if(board[i][0] != emptyChar && board[i][0] == board[i][1] && board[i][1] == board[i][2])
            {
                if(board[i][0] == playerOneChar) utility = 1;
                else utility = -1;
                return true;
            }
            if(board[0][i] != emptyChar && board[0][i] == board[1][i] && board[1][i] == board[2][i])
            {
                if(board[0][i] == playerOneChar) utility = 1;
                else utility = -1;
                return true;
            }
        }

        for(int i = 0; i < boardWidth; i++)
        {
            for(int j = 0; j < boardWidth; j++)
            {
                if(board[i][j] == emptyChar) return false;
            }
        }
        utility = 0;
        return true;
    }
    
    public int getUtillity()
    {
        if(utility == -1)
        {
            utility = depth-10;
        }
        else if(utility == 1)
        {
            utility = 10-depth;
        }
        return utility;
    }

    public ArrayList<State> getSuccessors()
    {
        ArrayList<State> successors = new ArrayList<>();

        for(int i = 0; i < boardWidth; i++)
        {
            for(int j = 0; j < boardWidth; j++)
            {
                if(board[i][j] == emptyChar)
                {
                    successors.add(place(i,j));
                }
            }
        }

        return successors;
    }

    public void setAction(State action)
    {
        this.action = action;
    }

    public State getAction()
    {
        return action;
    }

}
