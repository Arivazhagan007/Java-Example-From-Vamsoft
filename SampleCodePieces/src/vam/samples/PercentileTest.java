package vam.samples;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;
import org.apache.commons.math3.stat.descriptive.rank.Percentile;
import org.apache.commons.math3.stat.ranking.NaNStrategy;
import org.apache.commons.math3.stat.ranking.NaturalRanking;
import org.apache.commons.math3.stat.ranking.TiesStrategy;

public class PercentileTest {

	public static void main(String[] args) throws FileNotFoundException, IOException {

		Properties vals = new Properties();
		vals.load(new FileInputStream("D:/CP-POP/workspace/SampleCodePieces/src/values.properties"));
		String values = vals.getProperty("val");

		String[] arrOfvals = values.split(",");

		// System.out.println(arrOfvals);

		for (String oneVal : arrOfvals) {
			// System.out.println(oneVal);
		}

		DescriptiveStatistics des = new DescriptiveStatistics();

		double[] rangeval = new double[arrOfvals.length];

		for (int j = 0; j < arrOfvals.length; j++) {
			rangeval[j] = Double.parseDouble(arrOfvals[j]);
			des.addValue(rangeval[j]);
		}

		double percentile = 0.80;
		double percentileIn100 = percentile * 100;
		Percentile per = new Percentile();
		per = per.withEstimationType(Percentile.EstimationType.R_1);

		des.setPercentileImpl(per);
		per.setData(rangeval);
		System.out.println("percentile from DescriptiveStatistics is + " + des.getPercentile(percentileIn100));

		System.out.println("mean from DescriptiveStatistics is + " + des.getMean());

		System.out.println("median from DescriptiveStatistics is + " + des.getPercentile(20));

		double percentileVal = per.evaluate(percentileIn100);

		System.out.println(percentileIn100 + "th percentile  using apache percentile class is " + percentileVal);

		System.out.println("20th percentile  using apache percentile class is " + per.evaluate(20));

		// PercentilesScaledRanking ranking = new PercentilesScaledRanking(NaNStrategy.REMOVED, TiesStrategy.AVERAGE);
		// double[] ranks = ranking.rank(rangeval);

		// System.out.println(Arrays.toString(ranks));

		int npos = (int) Math.ceil(percentile * rangeval.length) - 1;
		// System.out.println("npos is " + npos);
		System.out.println(percentile + " value using formula is " + rangeval[npos]);

	}

}

class PercentilesScaledRanking extends NaturalRanking {

	public PercentilesScaledRanking(NaNStrategy nanStrategy, TiesStrategy tiesStrategy) {
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