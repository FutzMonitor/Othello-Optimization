import java.util.Date;

import java.util.AbstractSet;

// Implementation to represent an OthelloPlayer with MiniMax algorithm.
public class GOATOthelloPlayer extends OthelloPlayer implements MiniMax {
    private int depthLimit;
    private int generatedNodes;
    private int staticEvaluations;
    private int totalNodes;

    private static final int DEFAULT_DEPTH = 5;

    public GOATOthelloPlayer(String name) {
        super(name);
        depthLimit = DEFAULT_DEPTH;
        generatedNodes = 0;
        staticEvaluations = 0;
        totalNodes = 0;
    }

    public GOATOthelloPlayer(String name, int depthLimit) {
        super(name);
        this.depthLimit = depthLimit;
        generatedNodes = 0;
        staticEvaluations = 0;
        totalNodes = 0;
    }

    public Square getMove(GameState currentState, Date deadline) {
        Square move = null;
        int evaluation = Integer.MIN_VALUE;
    
        Square validSquares[] = currentState.getValidMoves().toArray(new Square[0]);
        totalNodes+=validSquares.length;
        for (Square square:validSquares) {
            if (square!=null) {
                generatedNodes++;
                GameState gs = currentState.applyMove(square);
                int value = minValue(1, gs, Integer.MIN_VALUE, Integer.MAX_VALUE);
                if (evaluation < value) {
                    evaluation = value;
                    move = square;
                }
            }
        }
        if (move==null) {
            return validSquares[0];
        } else {
            return move;
        }
    }

    public int minValue(int depth, GameState state, int alpha, int beta) {
        if (depth>=depthLimit) {
            staticEvaluations++;
            return staticEvaluator(state);
        }
        int min = Integer.MAX_VALUE;

        Square validSquares[] = state.getValidMoves().toArray(new Square[0]);
        totalNodes+=validSquares.length;
        for (Square square:validSquares) {
            if (square!=null) {
                generatedNodes++;
                GameState gs = state.applyMove(square);
                min = Math.min(min, maxValue(depth+1, gs, alpha, beta));
                if (min<=beta) return min;
                beta = Math.min(beta, min);
            }
        }
        return min;
    }

    public int maxValue(int depth, GameState state, int alpha, int beta) {
        if (depth>=depthLimit) {
            staticEvaluations++;
            return staticEvaluator(state);
        }
        int max = Integer.MIN_VALUE;

        Square validSquares[] = state.getValidMoves().toArray(new Square[0]);
        totalNodes+=validSquares.length;
        for (Square square:validSquares) {
            if (square!=null) {
                generatedNodes++;
                GameState gs = state.applyMove(square);
                max = Math.max(max, minValue(depth+1, gs, alpha, beta));
                if (max>=beta) return max;
                alpha = Math.max(alpha, max);
            }
        }
        return max;
    }

    public int staticEvaluator(GameState state) {
        GameState.Player currentPlayer = state.getCurrentPlayer();
        int score = state.getScore(currentPlayer);
        if (state.getSquare(0, 0).equals(currentPlayer)) score+=2;
        if (state.getSquare(0, 7).equals(currentPlayer)) score+=2;
        if (state.getSquare(7, 0).equals(currentPlayer)) score+=2;
        if (state.getSquare(7, 7).equals(currentPlayer)) score+=2;

        AbstractSet<Square> validMoves = state.getValidMoves();
        int maxFlip = 0;

        for (Square move:validMoves) {
            maxFlip = Math.max(maxFlip, unstableCoins(state, move, state.getOpponent(currentPlayer)));
        }
        return score - maxFlip;
    }

    public int unstableCoins(GameState state, Square from, GameState.Player player) {
        boolean[][] wouldFlip = new boolean[8][8];
        int flips = 0;
        for (GameState.Direction direction : GameState.Direction.values()) {
            if (state.wouldFlip(from, player, direction)!=null) {
                Square to = state.wouldFlip(from, player, direction);
                switch (direction) {
                case UP:
                    for (int row=from.getRow()-1; row>to.getRow(); row--) {
                        wouldFlip[row][from.getCol()] = true;
                    }
                case DOWN:
                    for (int row=from.getRow()+1; row<to.getRow(); row++) {
                        wouldFlip[row][from.getCol()] = true;
                    }
                case LEFT:
                    for (int col=from.getCol()-1; col>to.getCol(); col--) {
                        wouldFlip[from.getRow()][col] = true;
                    }
                case RIGHT:
                    for (int col=from.getCol()+1; col<to.getCol(); col++) {
                        wouldFlip[from.getRow()][col] = true;
                    }
                case UPLEFT:
                    for (int row=from.getRow()-1, col=from.getCol()-1; row>to.getRow() && col>to.getCol(); row--, col--) {
                        wouldFlip[row][col] = true;
                    }
                case UPRIGHT:
                    for (int row=from.getRow()-1, col=from.getCol()+1; row>to.getRow() && col<to.getCol(); row--, col++) {
                        wouldFlip[row][col] = true;
                    }
                case DOWNLEFT:
                    for (int row=from.getRow()+1, col=from.getCol()-1; row<to.getRow() && col>to.getCol(); row++, col--) {
                        wouldFlip[row][col] = true;
                    }
                case DOWNRIGHT:
                    for (int row=from.getRow()+1, col=from.getCol()+1; row<to.getRow() && col<to.getCol(); row++, col++) {
                        wouldFlip[row][col] = true;
                    }
                default:
                }
            }
        }
        for (int i=0; i<8; i++) {
            for (int j=0; j<8; j++) {
                if (wouldFlip[i][j]) flips++;
            }
        }
        return flips;
    }

    public int getNodesGenerated() {
        return generatedNodes;
    }

    public int getStaticEvaluations() { 
        return staticEvaluations;
    }

    public double getAveBranchingFactor() {
        double totalNodes = this.totalNodes;
        double staticEvaluations = this.staticEvaluations;
        return totalNodes/(generatedNodes-staticEvaluations);
    }

    public double getEffectiveBranchingFactor() {
        double generatedNodes = this.generatedNodes;
        double staticEvaluations = this.staticEvaluations;
        return generatedNodes/(generatedNodes-staticEvaluations);
    }
}
