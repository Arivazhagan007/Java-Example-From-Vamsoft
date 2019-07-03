package vam.samples;

import java.util.Arrays;
import org.apache.commons.math3.stat.ranking.NaNStrategy;
import org.apache.commons.math3.stat.ranking.NaturalRanking;
import org.apache.commons.math3.stat.ranking.TiesStrategy;

public class Main {

    public static void main(String[] args) {
        double[] arr = {Double.NaN, 10, 11, 12, 12, 12, 12, 15, 18, 19, 20};

        PercentilesScaledRanking1 ranking = new PercentilesScaledRanking1(NaNStrategy.REMOVED, TiesStrategy.MAXIMUM);
        double[] ranks = ranking.rank(arr);

        System.out.println(Arrays.toString(ranks));
        //prints:
        //[0.1, 0.2, 0.6, 0.6, 0.6, 0.6, 0.7, 0.8, 0.9, 1.0]
    }
}

class PercentilesScaledRanking1 extends NaturalRanking {

    public PercentilesScaledRanking1(NaNStrategy nanStrategy, TiesStrategy tiesStrategy) {
        super(nanStrategy, tiesStrategy);
    }

    @Override
    public double[] rank(double[] data) {
        double[] rank = super.rank(data);
        for (int i = 0; i < rank.length; i++) {
            rank[i] = rank[i] / rank.length;
        }
        return rank;
    }
}