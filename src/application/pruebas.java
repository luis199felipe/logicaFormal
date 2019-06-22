package application;

import java.util.ArrayList;
import java.util.HashMap;

import model.Tree;

public class pruebas {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//verificarVal();
		verificarValidez();
	}
	
	
	
	
	
	private static boolean verificarValidez() {
		ArrayList<String> formulas = new ArrayList<>();
		String conclusion = "";
		int[][] matriz = new int[(int) Math.pow(2,formulas.size() )][formulas.size()+1];
		
		String exp = "(p)^((q)v(p))";
		int[] eval = evaluarFormula(exp, 2);
		
		//System.out.println(arbol.getRaiz().getValor());
		
		return true;

	}
	private static int[] evaluarFormula(String exp,int atomicosIntroducidos) {
		int saltos = (int)Math.pow(2, atomicosIntroducidos);
		int[] result = new int[saltos];
		
		int cont = 1;
		
		for (int i = 0; i < result.length; i++) {
			HashMap<Character,Character> valores = new HashMap<>();
			valores = llenarHashMap(valores,saltos,atomicosIntroducidos,i+1);
			Tree<Character> arbol = verificarFormulaBienFormada(exp, 2);
			arbol.setValorHojas(arbol.getRaiz(), 0, valores);		
			arbol.evaluarInPreOrder(arbol.getRaiz(),valores);
			result[i] = (int)arbol.getRaiz().getValor();
		}
		
		
		return result;
	}
	
	

	private static HashMap<Character, Character> llenarHashMap(HashMap<Character, Character> valores, int saltos, int atomicosIntroducidos,int interpretacion) {
	
		for (int i = 1; i <= atomicosIntroducidos; i++) {
			char a = (char)(112+i);
			/*
			 if (interpretacion%isaltos/(int)Math.pow(2, i)) {
					valores.put(a,'0');
			 }else{
					valores.put(a,'1');
			}*/		
			

			
			
		}
		
		return valores;		
	}





	public static Tree<Character> verificarFormulaBienFormada(String exp,int atomicosIntroducidos) {
		Tree<Character> arbol = new Tree<>();
		crearArbol(arbol, exp);

		if (arbol.contarHojas()>=atomicosIntroducidos) {
			return arbol;
		}
		return null;
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
				if (arbol.estaVacio()) {
					arbol.agregarIzq(exp.charAt(i + 1));	
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
