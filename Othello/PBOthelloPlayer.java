import java.util.Date;
import java.util.AbstractSet;

// Implementation to represent an OthelloPlayer with MiniMax algorithm.
public class PBOthelloPlayer extends OthelloPlayer implements MiniMax {
    private int depthLimit;
    private int shallowSearchLimit;
    private int generatedNodes;
    private int staticEvaluations;
    private int totalNodes;

    private static final int DEFAULT_DEPTH = 5;

    public PBOthelloPlayer(String name) {
        super(name);
        depthLimit = DEFAULT_DEPTH;
        shallowSearchLimit = 3;
        generatedNodes = 0;
        staticEvaluations = 0;
        totalNodes = 0;
        
    }

    public PBOthelloPlayer(String name, int depthLimit, int shallowSearchLimit) {
        super(name);
        this.depthLimit = depthLimit;
        this.shallowSearchLimit = shallowSearchLimit;
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
                // Must be defined for PB Cut
                int bound = 4;
                min = Math.min(min, maxValue(depth+1, gs, alpha, beta));
                // Must be defined for PB Cut
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
                // Must be defined for PB Cut
                int bound = 4;
                // Must be changed for PB Cut
                if (max>=beta) return max;
                alpha = Math.max(alpha, max);
            }
        }
        return max;
    }

    public int staticEvaluator(GameState state) {
        return state.getScore(state.getCurrentPlayer());
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

