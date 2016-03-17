package ud.bi0.dragonSphereZ.math;

import java.util.function.IntToDoubleFunction;

public class MathFunction {
	
	public static IntToDoubleFunction Constant(double constant) {
		return (i) -> constant;
	}
	
	public static IntToDoubleFunction Lerp(int n, double start, double end) {
		return (i) -> i * (end - start) / n;
	}
	
}
