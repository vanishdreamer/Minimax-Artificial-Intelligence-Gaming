import java.util.*;
public class alphabeta_913794542_914309127_912692810 extends AIModule
{
    private int[] moves;
    @Override
    public void getNextMove(final GameStateModule state)
    {    
        int depth = 0;
        final int player = state.getActivePlayer();
        chosenMove = miniMax(state, depth, player);

    }

    public int miniMax(final GameStateModule state, int depth, int player){

        int i;
        int value = -10000000;
        int position = 0;
        int[] moves = new int[state.getWidth()];
        int alpha = -100000000;
        int beta = 10000000;

        for (i = 0; i < state.getWidth(); i++){
            if (state.canMakeMove(i)){
                state.makeMove(i);
                moves[i] = minV(state, depth + 1, player,alpha,beta);
                state.unMakeMove();
                if (moves[i] > value) {
                    value = moves[i];
                    position = i;
                }
            } 
        }
        return position;
    }

    // public int canMake4(final GameStateModule game, int player, int i, int j){
    //     int score = 0;
    //     if(i >= 3){
    //         score += 10;
    //         for (int n = i - 1; n >= i - 3; n--){
    //             if(game.getAt(n,j) == (player % 2 + 1)){
    //                 score -= 10;
    //                 break;
    //             }
    //         }
    //     }
    //     if (i <= 3){
    //         score += 10;
    //         for (int n = i + 1; n <= i + 3; n++){
    //             if(game.getAt(n,j) == (player % 2 + 1)){
    //                 score -= 10;
    //                 break;
    //             }
    //         }
    //     }
    //     if(j<= 2){
    //         score += 10;
    //         for (int n = j + 1; n <= j + 3; n++){
    //             if(game.getAt(i,n) == (player % 2 + 1)){
    //                 score -= 10;
    //                 break;
    //             }
    //         }
    //     }
    //     if((i >= 3) && (j <= 2)){
    //         score += 20;
    //         for (int n = i - 1; n >= i - 3; n--){
    //             if(game.getAt(n, j+(i-n)) == (player % 2 + 1)){
    //                 score -= 20;
    //                 break;
    //             }
    //         }
    //     }
    //     if((i <= 3) && (j <= 2)){
    //         score += 20;
    //         for (int n = i + 1; n >= i - 3; n++){
    //             if(game.getAt(n, j+(n-i)) == (player % 2 + 1)){
    //                 score -= 20;
    //                 break;
    //             }
    //         }
    //     }
    //     if((i >= 3) && (j >= 3)){
    //         score += 20;
    //         for (int n = i - 1; n >= i - 3; n--){
    //             if(game.getAt(n, j-(i-n)) == (player % 2 + 1)){
    //                 score -= 20;
    //                 break;
    //             }
    //         }
    //     }
    //     if((i <= 3) && (j <= 3)){
    //         score += 20;
    //         for (int n = i + 1; n >= i - 3; n++){
    //             if(game.getAt(n, j-(n-i)) == (player % 2 + 1)){
    //                 score -= 20;
    //                 break;
    //             }
    //         }
    //     }
    //     return score;
    // }

    public int evalFunc(final GameStateModule game, int player){
        double score = 0;
        double line_score = 0;
        double basic_score = 0;

        if(game.isGameOver()){
            if(game.getWinner() == player){
                return 10000000;
            } else{
                return -10000000;
            }
        }   
        double badscore = 0;
        
        for(int i = 0; i< game.getWidth(); i++){
            for(int j = 0; j< game.getHeight(); j++){
                if(game.getAt(i,j) == player){
                    basic_score += 100 - 2* Math.abs(3 - i) - Math.abs(2.5 - j);
                        if(j == game.getHeight() - 1){
                            basic_score -= 5;
                        }
               
                    for(int dx = -1; dx<= 1; dx++){
                        for(int dy = -1; dy<= 1; dy++){
                            if(dx != 0 && dy != 0){ 
                                int count = 0;
                                    for(int step = 1; step <= 3; step++){
                                        if(i + 3 * dx >= 0 && i + 3 * dx < game.getWidth() && j + 3 * dy >= 0 && j + 3 * dy < game.getHeight()){
                                            if(game.getAt(i,j) == game.getAt(i + step * dx,j + step * dy)){   
                                                count++;
                                            }
                                        }
                                    }
                                if(count == 2){
                                    line_score += 1000;
                                }
                            }
                        }
                    }
                }
            }
        }
        score = basic_score + line_score + badscore;
        return (int) score;
    }

    public int minV(final GameStateModule state, int depth, int player,int alpha, int beta) {

        int i;
        int value = 10000000;
        int flag = 0;
        int[] moves = new int[state.getWidth()];

        if (depth == 8){
            return evalFunc(state, player);
        }

        for (i = 0; i < state.getWidth(); i++){
            if (state.canMakeMove(i)){
                state.makeMove(i);
                moves[i] = maxV(state, depth + 1, player,alpha,beta);
                state.unMakeMove();
                flag = 1;
                if (moves[i] < value) {
                    value = moves[i];
                }
                beta = Math.min(beta,value);
                if (beta <= alpha){
                    break;
                }
            } 
        }

        if(flag == 0){
            return evalFunc(state, player);
        } else {
            return value;
        }
    }

    public int maxV(final GameStateModule state, int depth, int player,int alpha, int beta){
    
        int i;
        int value = -10000000;
        int flag = 0;
        int[] moves = new int[state.getWidth()];

        if (depth == 8){
            return evalFunc(state, player);
        }

        for (i = 0; i < state.getWidth(); i++){
            if (state.canMakeMove(i)){
                state.makeMove(i);
                moves[i] = minV(state, depth + 1, player,alpha,beta);
                state.unMakeMove();
                flag = 1;
                if (moves[i] > value) {
                    value = moves[i];
                }
                alpha = Math.max(alpha, value);
                if (beta <= alpha){
                    break;
                }
            } 
        }

        if(flag == 0){
            return evalFunc(state, player);
        } else {
            return value;
        }
        
    }
    
   
}