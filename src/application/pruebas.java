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
		crearArbol(arbol, exp);
		arbol.imprimirInOrder(arbol.getRaiz());
	}

	public static boolean esAtomico(char l) {

		if (l == '~' || l == 'p' || l == 'q') {
			return true;
		} else {
			return false;
		}
	}

	private static Character crearArbol(Tree<Character> arbol, String exp) {

		int contPar = 0;
		char simbolo = 0;
		boolean flag = true;
		for (int i = 0; i < exp.length() && flag; i++) {
			char l = exp.charAt(i);
			contPar += compararParentesis(l);
			if (contPar == 0 && i > 0 && i + 1 < exp.length() - 1) {
				simbolo = exp.charAt(i + 1);
				if (arbol.estaVacio()) {
					arbol.agregar(exp.charAt(i + 1));	
				}
				String exp1 = exp.substring(1, i);
				String exp2 = exp.substring(i + 3, exp.length() - 1);
				System.out.println("Parte 1:" + exp1);
				System.out.println("Parte 2:" + exp2);
				if (esAtomico(exp1.charAt(0))) {
					arbol.agregarOrd(exp1.charAt(0), -1);
				} else {
					Tree<Character> subArbol = new Tree<>();
					crearArbol(subArbol, exp2);
					arbol.agregarOrdNodo(subArbol.getRaiz(), 1);
				}

				if (esAtomico(exp2.charAt(0))) {
					arbol.agregarOrd(exp1.charAt(0), 1);
				} else {
					Tree<Character> subArbol = new Tree<>();
					crearArbol(subArbol, exp2);
					arbol.agregarOrdNodo(subArbol.getRaiz(), 1);
				}

				flag = false;
			}
		}
		return simbolo;

	}

	public static int compararParentesis(char l) {
		if (l == '(') {
			return 1;
		} else if (l == ')') {
			return -1;
		}
		return 0;
	}
}
