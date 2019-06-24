package application;

import java.util.ArrayList;
import java.util.HashMap;

import model.Nodo;
import model.Tree;

public class pruebas {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		// evaluarFormula("((p)e(q))^((q)e(r))", 3); // Aqui van la expresion y el
		// numero de atomicos

//		HashMap<Character,Character> valores = new HashMap<>();
//		valores.put('p', '0');
//		valores.put('q', '0');
//		valores.put('1','1');
//		valores.put('0','0');
//		String exp = "(~(p))^((q)v(p))";
//		Tree<Character> arbol = verificarFormulaBienFormada(exp, 2);
//		arbol.setValorHojas(arbol.getRaiz(), 0, valores);		
//		arbol.evaluarInPreOrder(arbol.getRaiz(),valores);
//		System.out.println(arbol.getRaiz().getValor());

		
		//Ejemplo de consecuencia logica valida
		ArrayList<String> formulas = new ArrayList<>();
		String exp1 = "(p)^(q)";
		String conclusion = "(p)v(q)";

		formulas.add(exp1);
		formulas.add(conclusion);
		
		boolean validez = verificarValidezConjuntoFormulas(formulas, 2);
		System.out.println(validez);
	}

	private static boolean verificarValidezConjuntoFormulas(ArrayList<String> formulas, int atomicosIntroducidos) {
		// [Interpretaciones][formulas]
		char[][] matriz = new char[formulas.size()][(int) Math.pow(2, formulas.size())];

		for (int i = 0; i < formulas.size(); i++) {
			System.out.println("Formula " + i);
			matriz[i] = evaluarFormula(formulas.get(i), atomicosIntroducidos);
		}

		for (int j = 0; j < matriz[0].length; j++) {
			boolean InterpValida = true;
			for (int i = 0; i < matriz.length && InterpValida; i++) {
				//System.err.println(i + " " + j + " " + matriz[i][j]);
				if (matriz[i][j] == '1') {
					if (j == matriz[0].length - 1) {
						return true;
					}
				} else {
					if (i == matriz.length - 1) {
						return false;
					} else {
						InterpValida = false;
					}
				}
			}
		}
		return true;

	}

	private static char[] evaluarFormula(String exp, int atomicosIntroducidos) {
		int saltos = (int) Math.pow(2, atomicosIntroducidos);
		char[] result = new char[saltos];

		int cont = 1;

		for (int i = 0; i < result.length; i++) {
			HashMap<Character, Character> valores = new HashMap<>();
			valores = llenarHashMap(valores, saltos, atomicosIntroducidos, i);
			Tree<Character> arbol = verificarFormulaBienFormada(exp, 2);
			arbol.setValorHojas(arbol.getRaiz(), 0, valores);
			arbol.evaluarInPreOrder(arbol.getRaiz(), valores);
			result[i] = arbol.getRaiz().getValor();
			System.out.println(i + " " + result[i]);
		}

		return result;
	}

	private static HashMap<Character, Character> llenarHashMap(HashMap<Character, Character> valores, int saltos,
			int atomicosIntroducidos, int interpretacion) {
		String binario = Integer.toBinaryString(interpretacion);

		int tamano = binario.length();
		if (binario.length() < atomicosIntroducidos) {

			for (int i = 0; i < atomicosIntroducidos - tamano; i++) {

				binario = "0" + binario;
			}
		}

		for (int i = 0; i < atomicosIntroducidos; i++) {
			char a = (char) (112 + i);
			valores.put(a, binario.charAt(i));
		}

		return valores;
	}

	public static Tree<Character> verificarFormulaBienFormada(String exp, int atomicosIntroducidos) {
		Tree<Character> arbol = new Tree<>();
		crearArbol(arbol, exp);

		if (arbol.contarHojas() >= atomicosIntroducidos) {
			return arbol;
		}
		return null;
	}

	public static boolean esAtomico(char l) {

		if (l == 'p' || l == 'r' || l == 'q') {
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
				if (arbol.estaVacio()) {
					arbol.agregarIzq(exp.charAt(i + 1));
				}
				String exp1 = exp.substring(1, i);
				String exp2 = exp.substring(i + 3, exp.length() - 1);

				if (esAtomico(exp1.charAt(0)) || exp1.charAt(0) == '~') {
					if (exp1.charAt(0) == '~') {
						Tree<Character> subArbol = new Tree<>();
						Nodo<Character> n = new Nodo<>('~');
						subArbol.setRaiz(n);
						// System.out.println("Va a crear "+exp1.substring(2,exp1.length() - 1));
						if (esAtomico(exp1.substring(2, exp1.length() - 1).charAt(0))) {
							subArbol.agregarIzq(exp1.substring(2, exp1.length() - 1).charAt(0));
						} else {
							crearArbol(subArbol, exp1.substring(2, exp1.length() - 1));
						}

						arbol.agregarOrdNodo(subArbol.getRaiz(), -1);
					} else {
						arbol.agregarOrd(exp1.charAt(0), -1);
					}

				} else {
					Tree<Character> subArbol = new Tree<>();
					crearArbol(subArbol, exp1);
					arbol.agregarOrdNodo(subArbol.getRaiz(), -1);
				}

				if (esAtomico(exp2.charAt(0)) || exp2.charAt(0) == '~') {
					if (exp2.charAt(0) == '~') {
						Tree<Character> subArbol = new Tree<>();
						Nodo<Character> n = new Nodo<>('~');
						subArbol.setRaiz(n);
						// System.out.println("Va a crear "+exp2.substring(2,exp1.length() - 1));
						if (esAtomico(exp2.substring(2, exp2.length() - 1).charAt(0))) {
							subArbol.agregarIzq(exp2.substring(2, exp2.length() - 1).charAt(0));
						} else {
							crearArbol(subArbol, exp2.substring(2, exp2.length() - 1));
						}
						arbol.agregarOrdNodo(subArbol.getRaiz(), -1);
					} else {
						arbol.agregarOrd(exp2.charAt(0), 1);
					}

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
