import java.util.ArrayList;
import java.util.HashSet;

public class VenusMiniMaxPruning2 extends Player {

    private static final int LIMIT = 10;
    private static int DEBUG = 1;

    public VenusMiniMaxPruning2(byte player) {
        super(player);
        this.team = "42";
    }

    @Override
    public Move move(OthelloState state, HashSet<Move> legalMoves) throws InterruptedException {
        double max = Double.NEGATIVE_INFINITY;
        double beta = Double.POSITIVE_INFINITY;
        
        Move maxMove = null;
        
//        ArrayList<Move> arrayMove = new ArrayList<Move>();
//        arrayMove.addAll(legalMoves);
        
        for (Move move : legalMoves) {
//            System.out.println(move.toString());
//        	if(DEBUG == 1) System.out.println("MainB = (" + max + "," + beta + ")");
        	
            double tempMax = minValue(OthelloGame.transition(state, move), 1, max, beta);
            if (max < tempMax) {
                max = tempMax;
                maxMove = move;
            }
            
//            if(DEBUG == 1) System.out.println("MainA = (" + max + "," + beta + ")");
        }
        DEBUG--;
        return maxMove;
    }

    public double minValue(OthelloState state, int depth, double alpha, double beta) {
        // System.out.println(depth);
    	
        HashSet<Move> legalMoves = OthelloGame.getAllLegalMoves(state.getBoard(), state.getPlayer());
        if (legalMoves.size() == 0 || depth > LIMIT) {
//        	if(DEBUG == 1) System.out.println("test");
            return OthelloGame.computeScore(state.getBoard(), this.player);
        }
        double min = Double.POSITIVE_INFINITY;
        
        for (Move move : legalMoves) {
        	
            OthelloState newState = OthelloGame.transition(state, move);
            
//            if(DEBUG == 1) {
//            	for (int i = 0; i < depth; i++) System.out.print("\t");
//        		System.out.println("MinB = (" + alpha + "," + beta + ")");
//            }
            
            double temp = maxValue(newState, depth + 1, alpha, beta);
            min = Math.min(min, temp);
            
//            if(DEBUG == 1) {
//        		for (int i = 0; i < depth+1; i++) System.out.print("\t");
//        		System.out.println("Data = " + temp);
//        	}
            
            if(min <= alpha) return min;
            beta = Math.min(beta, min);
            
//            if(DEBUG == 1) {
//        		for (int i = 0; i < depth; i++) System.out.print("\t");
//        		System.out.println("MinA = (" + alpha + "," + beta + ")");
//        	}
        }
        return min;
    }

    public double maxValue(OthelloState state, int depth, double alpha, double beta) {
        // System.out.println(depth);
        HashSet<Move> legalMoves = OthelloGame.getAllLegalMoves(state.getBoard(), state.getPlayer());
        if (legalMoves.size() == 0 || depth > LIMIT) {
//        	if(DEBUG == 1) System.out.println("test");
            return OthelloGame.computeScore(state.getBoard(), this.player);
        }
        double max = Double.NEGATIVE_INFINITY;
        
        for (Move move : legalMoves) {
            OthelloState newState = OthelloGame.transition(state, move);
            
//            if(DEBUG == 1) {
//        		for (int i = 0; i < depth; i++) System.out.print("\t");
//        		System.out.println("MaxB = (" + alpha + "," + beta + ")");
//        	}
            
            double temp = minValue(newState, depth + 1, alpha, beta);
            max = Math.max(max, temp);
            
//            if(DEBUG == 1) {
//        		for (int i = 0; i < depth+1; i++) System.out.print("\t");
//        		System.out.println("Data = " + temp);
//        	}
            
            if(max >= beta) return max;
            alpha = Math.max(alpha, max);
            
//            if(DEBUG == 1) {
//        		for (int i = 0; i < depth; i++) System.out.print("\t");
//        		System.out.println("MaxA = (" + alpha + "," + beta + ")");
//        	}
        }
        return max;
    }

}