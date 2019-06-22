package view;

import java.util.ArrayList;
import java.util.HashMap;

import application.Main;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import model.Tree;
import model.operators;

public class UserViewController implements EventHandler<Event> {

	
	@FXML
	private Button verificarValidez;
	@FXML
	private TextArea fieldExpression;
	@FXML
	private Button botonNegar;
	@FXML
	private Button botonOr;
	@FXML
	private Button botonAnd;
	@FXML
	private Button botonBionditional;
	@FXML
	private Button conditional;
	@FXML
	private Button delete;
	@FXML
	private Button next;
	@FXML
	private Button previous;

	private operators operators;

	private ArrayList<Integer> keyCaretInsert;
	
	

	public UserViewController() {

		operators = new operators();
		keyCaretInsert = new ArrayList<>();
	}

	
	
	@FXML
	private void initialize() {
		botonAnd.addEventFilter(MouseEvent.MOUSE_CLICKED, this);
		delete.addEventFilter(MouseEvent.MOUSE_CLICKED, this);
		fieldExpression.addEventFilter(KeyEvent.KEY_TYPED, this);
		next.addEventFilter(MouseEvent.MOUSE_CLICKED, this);
		previous.addEventFilter(MouseEvent.MOUSE_CLICKED, this);
	}

	public void asignarMain(Main main) {

	}

	@FXML

	public void asignarNegar() {

		String a = operators.getOperator(0);
		int posCaretPreInsertion = fieldExpression.getCaretPosition();
		fieldExpression.insertText(fieldExpression.getCaretPosition(), a.split(",")[0] + "");

		int posToMove = Integer.parseInt(a.split(",")[1]);
		fieldExpression.positionCaret(posCaretPreInsertion + posToMove);
		fieldExpression.requestFocus();
		keyCaretInsert.add(posCaretPreInsertion + posToMove);

	}

	@FXML
	public void asignarAnd() {

		String a = operators.getOperator(3);
		int posCaretPreInsertion = fieldExpression.getCaretPosition();
		fieldExpression.insertText(fieldExpression.getCaretPosition(), a.split(",")[0] + "");

		int posToMove = Integer.parseInt(a.split(",")[1]);
		fieldExpression.positionCaret(posCaretPreInsertion + posToMove);
		fieldExpression.requestFocus();
		keyCaretInsert.add(posCaretPreInsertion + posToMove);
	}

	@FXML
	public void asignarOr() {
		String a = operators.getOperator(4);
		int posCaretPreInsertion = fieldExpression.getCaretPosition();
		fieldExpression.insertText(fieldExpression.getCaretPosition(), a.split(",")[0] + "");

		int posToMove = Integer.parseInt(a.split(",")[1]);
		fieldExpression.positionCaret(posCaretPreInsertion + posToMove);
		fieldExpression.requestFocus();
		keyCaretInsert.add(posCaretPreInsertion + posToMove);

	}

	@FXML
	public void asignarConditional() {
		String a = operators.getOperator(1);
		int posCaretPreInsertion = fieldExpression.getCaretPosition();
		fieldExpression.insertText(fieldExpression.getCaretPosition(), a.split(",")[0] + "");

		int posToMove = Integer.parseInt(a.split(",")[1]);
		fieldExpression.positionCaret(posCaretPreInsertion + posToMove);
		fieldExpression.requestFocus();
		keyCaretInsert.add(posCaretPreInsertion + posToMove);

	}

	@FXML
	public void asignarBiconditional() {
		String a = operators.getOperator(2);
		int posCaretPreInsertion = fieldExpression.getCaretPosition();
		fieldExpression.insertText(fieldExpression.getCaretPosition(), a.split(",")[0] + "");

		int posToMove = Integer.parseInt(a.split(",")[1]);
		fieldExpression.positionCaret(posCaretPreInsertion + posToMove);
		fieldExpression.requestFocus();
		keyCaretInsert.add(posCaretPreInsertion + posToMove);

	}

	/*
	 * valida que solo se pueda ingresar algo si el caret esta en medio de dos
	 * parentesis
	 */
	@FXML
	public void validateCaret() {

		int caretPreInsertion = fieldExpression.getCaretPosition();

		String a = fieldExpression.getText();
		String b = "";

		if (caretPreInsertion > 0 && caretPreInsertion + 1 < a.length() + 1) {
			b = a.substring(caretPreInsertion - 1, caretPreInsertion + 1);

			if (!b.equals("()v()")) {
				System.out.println("Esta mal");
				fieldExpression.setEditable(false);
			} else {
				fieldExpression.setEditable(true);

			}
		} else {

			fieldExpression.setEditable(false);

		}

	}
	
	
	@FXML
	public void verificarFBF() {
		String campoFormula = "(p)^((q)v(p))";
		int atomicosIntroducidos = 3;
		verificarFormulaBienFormada(campoFormula,atomicosIntroducidos);
	}
	
	

	/*
	 * 
	 * prueba de capturas de eventos teclado y mouse (non-Javadoc)
	 * 
	 * @see javafx.event.EventHandler#handle(javafx.event.Event)
	 */
	@Override
	public void handle(Event e) {
		if (e.getEventType().equals(KeyEvent.KEY_TYPED)) {
			KeyEvent c = (KeyEvent) e;

			if (c.getCharacter().equals("")) {
				System.out.println("#$#%$$#%$#%$");

			}

		}
		if (e.getSource().equals(botonAnd)) {
			System.out.println(botonAnd.toString());
		}
		if (e.getSource().equals(delete)) {
			fieldExpression.clear();
		}
		if (e.getSource().equals(next)) {
			int actualPos = fieldExpression.getCaretPosition();

			if (actualPos + 1 < keyCaretInsert.size()) {
				int b = keyCaretInsert.get(actualPos + 1);
				fieldExpression.positionCaret(b);
			}

		}
		if (e.getSource().equals(previous)) {
			int actualPos = fieldExpression.getCaretPosition();
			if (actualPos - 1 > 0) {
				int b = keyCaretInsert.get(actualPos - 1);
				fieldExpression.positionCaret(b);
			}
		}
		

	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	private boolean verificarValidez() {
		ArrayList<String> formulas = new ArrayList<>();
		String conclusion = "";
		int[][] matriz = new int[(int) Math.pow(2,formulas.size() )][formulas.size()+1];
		
		String exp = "(p)^((q)v(p))";
		HashMap<Character,Character> valores = new HashMap<>();
		valores.put('p','0');
		valores.put('q','1');
		valores.put('1','1');
		valores.put('0','0');
		
		Tree<Character> arbol = verificarFormulaBienFormada(exp, 2);
		arbol.setValorHojas(arbol.getRaiz(), 0, valores);		
		arbol.evaluarInPreOrder(arbol.getRaiz(),valores);
		System.out.println(arbol.getRaiz().getValor());
		
		
		
		
		return true;

	}
	
	
	public Tree<Character> verificarFormulaBienFormada(String exp,int atomicosIntroducidos) {
		Tree<Character> arbol = new Tree<>();
		crearArbol(arbol, exp);

		if (arbol.contarHojas()>=atomicosIntroducidos) {
			return arbol;
		}
		return null;
	}
	
	
	public boolean esAtomico(char l) {

		if (l == '~' || l == 'p' || l == 'q') {
			return true;
		} else {
			return false;
		}
	}

	private Character crearArbol(Tree<Character> arbol, String exp) {

		int contPar = 0;
		char simbolo = 0;
		boolean flag = true;
		for (int i = 0; i < exp.length() && flag; i++) {
			char l = exp.charAt(i);
			contPar += compararParentesis(l);
			if (contPar == 0 && i > 0 && i + 1 < exp.length() - 1) {
				simbolo = exp.charAt(i + 1);
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

	public int compararParentesis(char l) {
		if (l == '(') {
			return 1;
		} else if (l == ')') {
			return -1;
		}
		return 0;
	}


}
