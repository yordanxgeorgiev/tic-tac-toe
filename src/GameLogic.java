import java.util.ArrayList;
import java.util.Scanner;

public class GameLogic {
    private State state;
    Scanner scanner;

    public GameLogic()
    {
        state = new State();
        scanner = new Scanner(System.in);
    }

    public void start()
    {
        System.out.print("Play first or second? (0->first; 1->second): ");

        int choice = scanner.nextInt();
        if(choice != 1 && choice != 0)
        {
            System.out.println("Wrong input!");
            System.exit(-1);
        }

        gameLoop(choice);
    }

    private void gameLoop(int choice)
    {
        while(true)
        {
            if(choice == 0)
            {
                // player move
                playerMove();
                System.out.println("Your move:");
                state.print();
                endCheck(true);

                // computer move
                System.out.println();
                System.out.println("AI move:");
                min_value(state, -1000,1000);
                state = state.getAction();
                state.print();
                endCheck(false);
            }
            else
            {
                // computer move
                System.out.println();
                System.out.println("AI move:");
                max_value(state, -1000,1000);
                state = state.getAction();
                state.print();
                endCheck(false);

                // player move
                playerMove();
                System.out.println("Your move:");
                state.print();
                endCheck(true);
            }
        }
    }

    private void playerMove()
    {
        System.out.println();
        System.out.print("Enter move(two coordinates from 0 to 2): ");
        int row = scanner.nextInt();
        int col = scanner.nextInt();
        if(row < 0 || col < 0 || row > 2 || col > 2)
        {
            System.out.println("Wrong input!");
            System.exit(-1);
        }
        state = state.place(row,col);
    }

    private void endCheck(boolean playerMove)
    {
        if(state.is_terminal()) // if the game has finished
        {
            if(state.getUtillity() == 0) // check for draw
            {
                System.out.println("DRAW!");
                System.exit(0);
            }
            if(playerMove) // player wins
            {
                System.out.println("YOU WIN!");
                System.exit(0);
            }

            // otherwise player loses
            System.out.println("YOU LOSE!");
            System.exit(0);
        }
    }

    private int max_value(State st, int alpha, int beta)
    {
        if(st.is_terminal()) return st.getUtillity();
        int v = -1000;

        int max;
        ArrayList<State> successors = st.getSuccessors();
        for(State s : successors)
        {
            max = Math.max(v, min_value(s, alpha, beta));
            if(max > v)
            {
                v = max;
                st.setAction(s);
            }
            if(v >= beta)
            {
                st.setAction(state);
                return v;
            }
            if(alpha > v) alpha = v;
        }

        return v;
    }

    private int min_value(State st, int alpha, int beta)
    {
        if(st.is_terminal()) return st.getUtillity();
        int v = 1000;

        int min;
        ArrayList<State> successors = st.getSuccessors();
        for(State s : successors)
        {
            min = Math.min(v, max_value(s, alpha, beta));
            if(min < v)
            {
                v = min;
                st.setAction(s);
            }
            if(v <= alpha)
            {
                st.setAction(state);
                return v;
            }
            if(v < beta)
                beta = v;
        }
        return v;
    }

}
