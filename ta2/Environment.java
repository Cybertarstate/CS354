// This class provides a stubbed-out environment.
// You are expected to implement the methods.
// Accessing an undefined variable should throw an exception.

// Hint!
// Use the Java API to implement your Environment.
// Browse:
//   https://docs.oracle.com/javase/tutorial/tutorialLearningPaths.html
// Read about Collections.
// Focus on the Map interface and HashMap implementation.
// Also:
//   https://www.tutorialspoint.com/java/java_map_interface.htm
//   http://www.javatpoint.com/java-map
// and elsewhere.

import java.util.ArrayList;
import java.util.LinkedHashSet;

public class Environment {

	private ArrayList<String> map = new ArrayList<String>();
	private ArrayList<Double> values = new ArrayList<Double>();
	private int i = 0;
	//arguments: pos = position in the program where the variable is being assigned
	//           var = the variable name being assigned
	//           val = the value being assigned to the variable
	//Members:
	//   map = list of variable names
	//   values = list of variable values
	//   i = index counter for the number of variables stored
	//returns 0
	public double put(String var, double val) {
		this.map.add(var);
		this.values.add(val);
		i++;
		return 0; 
	}
	//arguments: pos = position in the program where the variable is being accessed
	//           var = the variable name being accessed
	//Members:
	//   map = list of variable names
	//   values = list of variable values
	//   i = index counter for the number of variables stored
	//   EvalException = exception thrown when an evaluation error occurs
	//returns the value associated with the variable name, throws exception if not found
	public double get(int pos, String var) throws EvalException {
		for (int j = i - 1; j >= 0; j--) {
			if (this.map.get(j).equals(var)) {
				return this.values.get(j);
			}
		}
		throw new EvalException(pos, "variable" + var + "not defined");
	}
	//arguments: none
	//Members:
	//   map = list of variable names
	//returns a string of all variable declarations in C syntax
		
	public String toC() {
	    if (map.isEmpty()) return "";
		
		LinkedHashSet<String> uniqueVars = new LinkedHashSet<>(map);
		StringBuilder sb = new StringBuilder("double");
		String sep = " ";
		for (String v : uniqueVars) {
			sb.append(sep).append(v);
			sep = ",";
		}
		sb.append(";\n");   
		return sb.toString();
	}

}
