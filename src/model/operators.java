package model;

import java.util.ArrayList;

public class operators {

	ArrayList<String> oper;

	public operators() {
		// TODO Auto-generated constructor stub
		oper = new ArrayList<>();
		oper.add("~(),2");
		oper.add("()->(),1");
		oper.add("()<->(),1");
		oper.add("()^(),1");
		oper.add("()v(),1");
	}
	
	public String getOperator(int a) {
		
		return oper.get(a);
	}

}
