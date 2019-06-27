package application;

import java.util.ArrayList;
import java.util.HashMap;

import model.Nodo;
import model.Tree;

public class Validador {

	private ArrayList<char[]> resultados;

	public ArrayList<char[]> getResultados() {
		return resultados;
	}

	public void setResultados(ArrayList<char[]> resultados) {
		this.resultados = resultados;
	}

	public Validador() {
		resultados = new ArrayList<>();
	}

	public boolean verificarValidezConjuntoFormulas(ArrayList<String> formulas, int atomicosIntroducidos) {
		// [Interpretaciones][formulas]
		char[][] matriz = new char[formulas.size()][(int) Math.pow(2, formulas.size())];

		for (int i = 0; i < formulas.size(); i++) {
			System.out.println("Formula " + i);
			matriz[i] = evaluarFormula(formulas.get(i), atomicosIntroducidos);
			// System.out.println(matriz[i].toString());
		}

		for (int j = 0; j < matriz[0].length; j++) {
			boolean InterpValida = true;
			for (int i = 0; i < matriz.length && InterpValida; i++) {
				System.out.println(i + " " + j + " " + matriz[i][j]);
				if (matriz[i][j] == '1') {
					if (i == matriz[0].length - 1) {
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
		return false;

	}

	private char[] evaluarFormula(String exp, int atomicosIntroducidos) {
		int saltos = (int) Math.pow(2, atomicosIntroducidos);
		char[] result = new char[saltos];

		for (int i = 0; i < result.length; i++) {
			HashMap<Character, Character> valores = new HashMap<>();
			valores = llenarHashMap(valores, saltos, atomicosIntroducidos, i);
			Tree<Character> arbol = verificarFormulaBienFormada(exp, 2);
			arbol.setValorHojas(arbol.getRaiz(), 0, valores);
			arbol.evaluarInPreOrder(arbol.getRaiz(), valores);
			result[i] = arbol.getRaiz().getValor();
			System.out.println(i + "     " + result[i]);
		}
		resultados.add(result);
		return result;
	}

	private HashMap<Character, Character> llenarHashMap(HashMap<Character, Character> valores, int saltos,
			int atomicosIntroducidos, int interpretacion) {
		String binario = Integer.toBinaryString(interpretacion);

		int tamano = binario.length();
		if (binario.length() < atomicosIntroducidos) {

			for (int i = 0; i < atomicosIntroducidos - tamano; i++) {
				binario = "0" + binario;
			}
		}

		// System.out.println(binario);

		for (int i = 0; i < atomicosIntroducidos; i++) {
			char a = (char) (112 + i);
			valores.put(a, binario.charAt(i));
		}

		return valores;
	}

	public Tree<Character> verificarFormulaBienFormada(String exp, int atomicosIntroducidos) {
		Tree<Character> arbol = new Tree<>();
		crearArbol(arbol, exp);
		return arbol;

	}

	public static boolean esAtomico(char l) {

		if (l == 'p' || l == 'r' || l == 'q' || l == 's' || l == 't' || l == 'u') {
			return true;
		} else {
			return false;
		}
	}

	private void crearArbol(Tree<Character> arbol, String exp) {
		if (exp.charAt(0) == '~') {

			Tree<Character> subArbol = new Tree<>();
			Nodo<Character> n = new Nodo<>('~');
			arbol.setRaiz(n);

			crearArbol(subArbol, exp.substring(2, exp.length() - 1));
			arbol.agregarOrdNodo(subArbol.getRaiz(), 1);
			// arbol.setRaiz(subArbol.getRaiz());
		} else if (esAtomico(exp.charAt(0))) {
			Nodo<Character> n = new Nodo<Character>(exp.charAt(0));
			arbol.setRaiz(n);
		} else {
			crearArbol2(arbol, exp);
		}
	}

	private void crearArbol2(Tree<Character> arbol, String exp) {

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
						if (exp1.charAt(2) == '~') {
							
							Tree<Character> subArbol = new Tree<>();
							
							System.out.println("Va a crear "+exp1.substring(4,exp1.length() - 2));
							if (esAtomico(exp1.substring(4, exp1.length() - 2).charAt(0))) {
								subArbol.agregarIzq(exp1.substring(4, exp1.length() - 2).charAt(0));
							} else {
								crearArbol2(subArbol, exp1.substring(2, exp1.length() - 1));
							}

							arbol.agregarOrdNodo(subArbol.getRaiz(), -1);
						} else {
							Tree<Character> subArbol = new Tree<>();
							Nodo<Character> n = new Nodo<>('~');
							subArbol.setRaiz(n);
							// System.out.println("Va a crear "+exp1.substring(2,exp1.length() - 1));
							if (esAtomico(exp1.substring(2, exp1.length() - 1).charAt(0))) {
								subArbol.agregarIzq(exp1.substring(2, exp1.length() - 1).charAt(0));
							} else {
								crearArbol2(subArbol, exp1.substring(2, exp1.length() - 1));
							}

							arbol.agregarOrdNodo(subArbol.getRaiz(), -1);
						}

					} else {
						arbol.agregarOrd(exp1.charAt(0), -1);
					}

				} else {
					Tree<Character> subArbol = new Tree<>();
					crearArbol2(subArbol, exp1);
					arbol.agregarOrdNodo(subArbol.getRaiz(), -1);
				}

				if (esAtomico(exp2.charAt(0)) || exp2.charAt(0) == '~') {
					if (exp2.charAt(0) == '~') {
						if (exp2.charAt(2) == '~') {
							Tree<Character> subArbol = new Tree<>();
							
							System.out.println("Va a crear 22"+exp2.substring(3,exp2.length() - 2));
							if (esAtomico(exp2.substring(3, exp2.length() - 2).charAt(0))) {
								subArbol.agregarIzq(exp1.substring(2, exp2.length() - 2).charAt(0));
							} else {
								crearArbol2(subArbol, exp2.substring(2, exp2.length() - 1));
							}
						} else {
							Tree<Character> subArbol = new Tree<>();
							Nodo<Character> n = new Nodo<>('~');
							subArbol.setRaiz(n);
							// System.out.println("Va a crear "+exp2.substring(2,exp1.length() - 1));
							if (esAtomico(exp2.substring(2, exp2.length() - 1).charAt(0))) {
								subArbol.agregarIzq(exp2.substring(2, exp2.length() - 1).charAt(0));
							} else {
								crearArbol2(subArbol, exp2.substring(2, exp2.length() - 1));
							}
							arbol.agregarOrdNodo(subArbol.getRaiz(), -1);
						}

					} else {
						arbol.agregarOrd(exp2.charAt(0), 1);
					}

				} else {
					Tree<Character> subArbol = new Tree<>();
					crearArbol2(subArbol, exp2);
					arbol.agregarOrdNodo(subArbol.getRaiz(), 1);
				}

				flag = false;
			}
		}

	}

	public int compararParentesis(char l) {
		if (l == '(') {
			return 1;
		} else if (l == ')') {
			return -1;
		}
		return 0;
	}

	public void limpiarResultados() {
		resultados.clear();

	}
}
