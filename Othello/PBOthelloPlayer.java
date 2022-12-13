import java.util.Date;

// Implementation to represent an OthelloPlayer with MiniMax algorithm.
public class PBOthelloPlayer extends OthelloPlayer implements MiniMax {
	private final double Percentile = 1.5;
	private final int sigma = 1;
	private PBEstimation model;
    private int depthLimit;
    private int shallowSearchLimit;
    private int generatedNodes;
    private int staticEvaluations;
    private int totalNodes;

    private static final int DEFAULT_DEPTH = 8;

    public PBOthelloPlayer(String name) {
        super(name);
        depthLimit = DEFAULT_DEPTH;
        shallowSearchLimit = 4;
        generatedNodes = 0;
        staticEvaluations = 0;
        totalNodes = 0;
        model = new PBEstimation();
        
        // dataPair = new Pair(0,0);
    }

    public PBOthelloPlayer(String name, int depthLimit, int shallowDepthLimit) {
        super(name);
        this.depthLimit = depthLimit;
        this.shallowSearchLimit = shallowDepthLimit;
        generatedNodes = 0;
        staticEvaluations = 0;
        totalNodes = 0;
        model = new PBEstimation();
        // dataPair = new Pair(0,0);
    }


    public Square getMove(GameState currentState, Date deadline) {

        // ADDING TO TRY TO COLLECT EVALUATION PAIRS FOR PROBCUT REGRESSION MODELS
        int myScore = currentState.getScore(currentState.getCurrentPlayer());
        int oppScore = currentState.getScore(currentState.getOpponent(currentState.getCurrentPlayer()));
        int pieces = myScore + oppScore;
        // END

        Square move = null;
        int evaluation = Integer.MIN_VALUE;
    
        Square validSquares[] = currentState.getValidMoves().toArray(new Square[0]);
        totalNodes+=validSquares.length;
        for (Square square:validSquares) {
            if (square!=null) {
                generatedNodes++;
                GameState gs = currentState.applyMove(square);
                int value = minValue(1, shallowSearchLimit, gs, Integer.MIN_VALUE, Integer.MAX_VALUE);
                if (evaluation < value) {
                    evaluation = value;
                    move = square;
                }
            }
        }
        if (move==null) {
            return validSquares[0];
        } else {

            // ADDING FOR PROBCUT DATA COLLECTION
            // dataPair.set_b(evaluation);
            // try {
            //    String file_name = "data-collection/d-" + String.valueOf(pieces) + ".txt";
            //    FileWriter write_data = new FileWriter(file_name, true);
            //    write_data.write(dataPair.print());
            //    write_data.write('\n');
            //    write_data.close();
            //    System.out.println("Successfully wrote to the data file.");
            // } catch (IOException e) {
            //    System.out.println("An error occurred writing to the data file.");
            //    e.printStackTrace();
            // }
            // END
            
            return move;
        }
    }


    public int minValue(int depth, int searchLimit, GameState state, int alpha, int beta) {
        if (depth>=depthLimit) {
            staticEvaluations++;
            return staticEvaluator(state);
        }
        
        int myPieces = state.getScore(state.getCurrentPlayer());
        int oppPieces = state.getScore(state.getOpponent(state.getCurrentPlayer()));
        int totalPieces = myPieces + oppPieces;
        if(model.estimateV(totalPieces, 0) >= beta) {
        	// implement cut-off
        }

        // ADDING FOR PROBCUT DATA COLLECTION
        // if(depth == shallowDepthLimit) {
        //    dataPair.set_a(staticEvaluator(state));
        //}
        // END

        int min = Integer.MAX_VALUE;

        Square validSquares[] = state.getValidMoves().toArray(new Square[0]);
        totalNodes+=validSquares.length;
        for (Square square:validSquares) {
            if (square!=null) {
                generatedNodes++;
                GameState gs = state.applyMove(square);

                // Must be defined for PB Cut
                double a = model.getIntercept(totalPieces), b = model.getCoeff(totalPieces);
                int bound = (int) (Percentile * sigma + beta - b / a);
                min = Math.min(min, maxValue(depth+1, searchLimit, gs, alpha, beta));
                // Must be defined for PB Cut
                if (minValue(depth, searchLimit, state, bound, bound + 1) >= bound) return beta;
                beta = Math.min(beta, min);
            }
        }
        return min;
    }
    

    public int maxValue(int depth, int searchLimit, GameState state, int alpha, int beta) {
        if (depth>=depthLimit) {
            staticEvaluations++;
            return staticEvaluator(state);
        }
        int myPieces = state.getScore(state.getCurrentPlayer());
        int oppPieces = state.getScore(state.getOpponent(state.getCurrentPlayer()));
        int totalPieces = myPieces + oppPieces;
        if(model.estimateV(totalPieces, 0) <= alpha) {
        	// implement cut-off
        }
        
        // ADDING FOR PROBCUT DATA COLLECTION
        // if(depth == shallowDepthLimit) {
        //     dataPair.set_a(staticEvaluator(state));
        // }
        // END

        int max = Integer.MIN_VALUE;

        Square validSquares[] = state.getValidMoves().toArray(new Square[0]);
        totalNodes+=validSquares.length;
        for (Square square:validSquares) {
            if (square!=null) {
                generatedNodes++;
                GameState gs = state.applyMove(square);
                
                double a = model.getIntercept(totalPieces), b = model.getCoeff(totalPieces);
                int bound = (int) ((-1 * Percentile) * sigma + alpha - b / a);
                max = Math.max(max, minValue(depth+1, searchLimit, gs, alpha, beta));
                if (minValue(depth, searchLimit, state, bound, bound + 1) >= bound) return beta;
                // Must be defined for PB Cut
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
