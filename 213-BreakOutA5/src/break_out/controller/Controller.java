package break_out.controller;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import break_out.model.Game;
import break_out.view.Field;
import break_out.view.StartScreen;
import break_out.view.View;
//import break_out.controller.JSONReader;

/**
 * The controller takes care of the input events and reacts on those events by
 * manipulating the view and updates the model.
 * @author Mostafa Othman
 * @author Azad Juma
 */
public class Controller implements ActionListener, KeyListener {

    /**
     * The game as model that is connected to this controller
     */
    private Game game;

    /**
     * The view that is connected to this controller
     */
    private View view;

    
    /**
     * The constructor expects a view to construct itself.
     * 
     * @param view The view that is connected to this controller
     */
    public Controller(View view) {
        this.view = view;

        // Assigning the listeners
        assignActionListener();
        assignKeyListener();
    }

    /**
     * The controller gets all buttons out of the view with this method and adds
     * this controller as an action listener. Every time the user pushed a
     * button the action listener (this controller) gets an action event.
     */
    private void assignActionListener() {
        // Get the start screen to add this controller as action
        // listener to the buttons.
        view.getStartScreen().addActionListenerToStartButton(this);
        view.getStartScreen().addActionListenerToQuitButton(this);
    }
    
    /**
     * With this method the controller adds himself as a KeyListener.
     * Every time the user pushed a key the KeyListener (this controller) gets an KeyEvent.
     */
    private void assignKeyListener() {
        // Get the field to add this controller as KeyListener
        view.getField().addKeyListener(this);
    }

    /**
     * Jedesmal wenn der Nutzer einen Button anklickt, erhaelt der Actionlistener (dieser Controller) 
     * ein Actionevent und startet diese Methode
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        // Das StartScreen-Objekt wird fuer den Zugriff auf seine Methoden vom bekannten View-Objekt besorgt.
        StartScreen startScreen = view.getStartScreen();
        
        // Der Startbutton wurde angeklickt
        if (startScreen.getStartButton().equals(e.getSource())) {
            // Der Name des Spielers wird von der Eingabe geholt
            String playersName = startScreen.getPlayersName();
            playersName = playersName.trim();
            if (playersName.length() < 1) {
                // Ist der Name leer, ist er ungueltig und eine Fehlermeldung wird angezeigt
                startScreen.showError("Der Name ist ungültig");
            } else {    
            	// Neues Game-Objekt erzeugen und 
    	        game = new Game(this);
    	        // dem View-Objekt bekanntgeben
    	        view.setGame(game);
            }
        }

        // Der Spielendebutton wurde angeklickt
        else if (startScreen.getQuitButton().equals(e.getSource())) {
            System.exit(0);
        }
    }
 
    /**
     * Methode des KeyListeners
     */
    @Override
    public void keyTyped(KeyEvent e) {
        
    }

    /**
     * Methode des KeyListeners
     */
    @Override
    public void keyPressed(KeyEvent e) {
    	if(e.getKeyCode()==KeyEvent.VK_SPACE) {
    		if(this.game.getLevel().ballWasStarted()) {
    			this.game.getLevel().stopBall();
    		}else {
    			game.getLevel().startBall();
    		}
    	}
    	if(e.getKeyCode()==KeyEvent.VK_LEFT) {
    		this.game.getLevel().getPaddle().setBewegungsrichtung(-1);
    	}
    	if(e.getKeyCode()==KeyEvent.VK_RIGHT) {
    		this.game.getLevel().getPaddle().setBewegungsrichtung(+1);
    	}
    	if(e.getKeyCode()==KeyEvent.VK_ESCAPE) {
    		this.game.getLevel().setBeendet(true);
    		toStartScreen();
    	}
    		
    }

    /**
     * Methode des KeyListeners
     */
    @Override
    public void keyReleased(KeyEvent e) {
    	if(e.getKeyCode()==KeyEvent.VK_LEFT || e.getKeyCode()==KeyEvent.VK_RIGHT)
    		this.game.getLevel().getPaddle().setBewegungsrichtung(0);
    }

    
    /**
     * Mit dieser Methode erfolgt das Umschalten vom Spielfeld zum StartScreen
     */
    public void toStartScreen() {
    	
    	//int finalScore=this.view.getGame().getScore();//+this.view.getGame().getLevel().getLevelScore();
		this.view.getStartScreen().addPlayerNameAndScore();
    	view.showScreen(StartScreen.class.getName());
    	view.getStartScreen().requestFocusInWindow();
    	
    }
    
    /**
     * Mit dieser Methode erfolgt das Umschalten vom StartScreen zum Spielfeld
     */
    public void toPlayground() {
    	view.showScreen(Field.class.getName());
    	view.getField().requestFocusInWindow();
    }
    /**
     * 
     */
    
   
}
