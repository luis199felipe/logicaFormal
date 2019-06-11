package view;

import java.util.ArrayList;

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

			if (!b.equals("()")) {
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
	public void verificarVal() {
		Tree<Character> arbol = new Tree<>();
		String exp = fieldExpression.getText();

		
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

}
