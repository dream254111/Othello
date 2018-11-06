import java.util.HashSet;

public class VenusMiniMaxPruning extends Player {

    private static final int LIMIT = 2;

    public VenusMiniMaxPruning(byte player) {
        super(player);
        this.team = "Venus";
    }

    @Override
    public Move move(OthelloState state, HashSet<Move> legalMoves) throws InterruptedException {
        double max = Double.NEGATIVE_INFINITY;
        double beta = Double.POSITIVE_INFINITY;
        
        Move maxMove = null;
        
        for (Move move : legalMoves) {
        	OthelloState newState = OthelloGame.transition(state, move);
        	System.out.println("MB(a,B) = (" + max + "," + beta + ")");
            double tempMax = Math.max(max, maxValue(newState, 1, max, beta)); //Add alpha and beta
            System.out.println("MA(a,B) = (" + max + "," + beta + ")");
            if (max < tempMax) {
            	max = tempMax;
            	maxMove = move;
            	System.out.println("Change MAX = " + max);
            }
            
        }
        
//        for (Move move : legalMoves) {
//            System.out.println(move.toString());
//            double tempMax = minValue(OthelloGame.transition(state, move), 1, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY);
//            if (max < tempMax) {
//                max = tempMax;
//                maxMove = move;
//            }
//        }
        return maxMove;
    }

    public double minValue(OthelloState state, int depth, double alpha, double beta) {
//         System.out.println(depth);
        HashSet<Move> legalMoves = OthelloGame.getAllLegalMoves(state.getBoard(), state.getPlayer());
        if (legalMoves.size() == 0 || depth > LIMIT) {
            return OthelloGame.computeScore(state.getBoard(), this.player);
            /**
             * @task: write evaluation function HERE!!!
             * @run: comment "return OthelloGame.computeScore(state.getBoard(), this.player);"
             */
        }
        double min = Double.POSITIVE_INFINITY;
        for (Move move : legalMoves) {
            OthelloState newState = OthelloGame.transition(state, move);
            
            for (int i = 0; i < depth; i++) System.out.print("\t");
            System.out.println("MinB(a,B) = (" + alpha + "," + beta + ")");
            
            min = Math.min(min, maxValue(newState, depth + 1, alpha, beta)); 	//Add alpha and beta
            
            for (int i = 0; i < depth+1; i++) System.out.print("\t");
            System.out.println(min);
            
            if(min <= alpha) return min;										//Add this Line
            beta = Math.min(beta, min);											//Add this Line too
            
            for (int i = 0; i < depth; i++) System.out.print("\t");
            System.out.println("MinA(a,B) = (" + alpha + "," + beta + ")");
        }
        return min;
    }

    public double maxValue(OthelloState state, int depth, double alpha, double beta) {
//         System.out.println(depth);
        HashSet<Move> legalMoves = OthelloGame.getAllLegalMoves(state.getBoard(), state.getPlayer());
        if (legalMoves.size() == 0 || depth > LIMIT) {
            return OthelloGame.computeScore(state.getBoard(), this.player);
            /**
             * @task: write evaluation function HERE!!!
             * @run: comment "return OthelloGame.computeScore(state.getBoard(), this.player);"
             */
        }
        double max = Double.NEGATIVE_INFINITY;
        for (Move move : legalMoves) {
            OthelloState newState = OthelloGame.transition(state, move);
            
            for (int i = 0; i < depth; i++) System.out.print("\t");
            System.out.println("MaxB(a,B) = (" + alpha + "," + beta + ")");
            
            max = Math.max(max, minValue(newState, depth + 1, alpha, beta));	//Add alpha and beta
            
            for (int i = 0; i < depth+1; i++) System.out.print("\t");
            System.out.println(max);
            
            if(max >= beta) return max;										//Add this Line
            alpha = Math.max(alpha, max);											//Add this Line too
            
            for (int i = 0; i < depth; i++) System.out.print("\t");
            System.out.println("MaxA(a,B) = (" + alpha + "," + beta + ")");
        }
        return max;
    }

}
