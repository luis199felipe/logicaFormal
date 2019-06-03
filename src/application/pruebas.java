package application;

import model.Tree;

public class pruebas {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		verificarVal();
	
	}
	public static void verificarVal() {
		Tree<Character> arbol = new Tree<>();
		String exp = "(p)^((q)v(p))";
		crearArbol(arbol,exp);
		arbol.imprimirInOrder(arbol.getRaiz());
	}
	
	public static boolean esAtomico(char l) {
		
		if (l=='~' || l=='p' || l=='q') {
			return true;
		}else {
			return false;
		}
	}

	private static  Character crearArbol(Tree<Character> arbol,String exp) {
		//Algoritmo de descomposicion.
		if (esAtomico(exp.charAt(0))) {
			arbol.agregarOrd(exp.charAt(0),-1);
		}
		int contPar = 0;
		char simbolo = 0;
		for (int i = 0; i < exp.length(); i++) {
			char l = exp.charAt(i);
			contPar += compararParentesis(l);
			if (contPar==0 && i>0 && i+1<exp.length()-1) {
				simbolo = exp.charAt(i+1);
				arbol.agregarOrd(crearArbol(arbol, exp.substring(1, i)),-1);
				arbol.agregarOrd(crearArbol(arbol, exp.substring(i+2, exp.length())),1);
			}
		}
		return simbolo;
	}
	
	
	public static int compararParentesis(char l) {
		if (l=='(') {
			return 1;
		}else if(l==')') {
			return -1;
		}
		return 0;
	}
}
