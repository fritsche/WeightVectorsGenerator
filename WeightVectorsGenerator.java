// WeightVectorsGenerator.java

import java.util.Map;
import java.util.HashMap;
import java.util.Vector;

public class WeightVectorsGenerator {

	private	int h, m;
	private double t, d;

	public WeightVectorsGenerator (int m, int h, double d, double t) {
		this.m = m;
		this.h = h;
		this.t = t;
		this.d = d;
	}

	private void h1generator(int g) { // g = h
		for (int i=0; i<m; ++i){
			for (int j=0; j<m; ++j){
				printValue( (i == j) ? (g) : (0) );
			}
			System.out.println();	
		}
	}

	private void h2generator(){
		h1generator(h);
		for (int i = 0; i < (m-1); ++i) {
			for (int j = i+1; j < m ; ++j) {
				for (int k = 0; k < m; ++k){
					printValue( (k == i || k == j) ? 1 : 0 );
				}
				System.out.println();
			}
		}
	}

	protected void generate(){
		if (h == 1) {
			h1generator(h);
		} else if (h == 2) {
			h2generator();
		} 
		else {
			Vector<Integer> count = new Vector<Integer>();
			for (int i = 0; i < m; ++i){
				count.add(0);
			}
			for (int j = 1; j < Math.pow(h+1, m); ++j) {
				count = update(count);
				int accumulate = 0;
				for (int i: count ) {
					accumulate+=i;
				}
				if (h == accumulate )
					print(count);
			}
		}
	}

	private void printValue(int value){
		System.out.print((((1.0 - t)/ ((double) m)) + ( t * ((double) value)  * d)) + "\t");
	}

	protected void print(Vector<Integer> v){
		for (int i : v) {
			printValue(i);
		}
		System.out.println();
	}

	protected Vector<Integer> update (Vector<Integer> v) {
		for (int i = 0; i< v.size(); i = ((v.get(i) != 0) ? v.size() : i+1)) {
			v.set(i, v.get(i)+1);
			v.set(i, v.get(i)%(h+1));
		}
		return v;
	}

	public static void main(String[] args) {
		int h=3, m = 2;
		double t = 1.0, d;

		Map <String, String> arguments = new HashMap<String, String>();

		for (int i=0; i<args.length; i+=2) {
			arguments.put(args[i], args[i+1]);
		}

		if (arguments.get("-h") != null )
			h = Integer.parseInt(arguments.get("-h"));
		if (arguments.get("-m") != null )
			m = Integer.parseInt(arguments.get("-m"));
		if (arguments.get("-t") != null )
			t = Double.parseDouble(arguments.get("-t"));

		d = 1.0 / (double) h;
		(new WeightVectorsGenerator(m, h, d, t)).generate();
	}

}