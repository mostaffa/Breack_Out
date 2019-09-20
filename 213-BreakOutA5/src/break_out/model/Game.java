package break_out.model;

import java.util.List;
import java.util.ArrayList;

import break_out.controller.Controller;
import break_out.view.View;

/**
 * This object contains information about the game (the model in MVC)
 * 
 * @author dmlux, modified by Mostafa Othman, Azad Juma
 * 
 */
public class Game{

    /**
	 * A list of observer objects
	 */
	private List<View> observers = new ArrayList<View>();

	/**
     * The controller of the game
     */
    private Controller controller;
	
    /**
   	 * The current level
   	 */
    private Level level;
    
    /**
     * The first levelnumber
     */
    private int firstLevel = 1;
    
    /**
     * The last levelnumber
     */
    private int maxLevel = 4;  
       
    /**
	 * The total score of the game
	 */
    private int score = 0;
    
       
    /**
     * Der Konstruktor instanziiert ein neues Game-Objekt
     * @param controller Der zugeordnete Controller (siehe MVC)
     */
    public Game(Controller controller) {
        this.controller = controller;
        createLevel(firstLevel, 0);
    }

    
    // Die drei Methoden des Observer-Musters ----------------
    public void addObserver(View observer) {
		observers.add(observer);
	}

	public void removeObserver(View observer) {
		observers.remove(observer);
	}

	public void notifyObservers() {
		for (View observer : observers)
			observer.modelChanged(this);
	}
	// -------------------------------------------------------
	
	/**
	 * Getter for the Controller
	 * @return controller The controller of this game
	 */
     public Controller getController() {
    	 return controller;
     }
     
     /**
      * Getter for the current Level
      * @return level The current level of the game
      */
     public Level getLevel() {
    	 return level;
     }
     
    /**
     * Getter for the total score
     * @return score The current score of the game
     */
    public int getScore() {
        return score;
    }
    
    /**
     * Erzeugt den ersten oder naechsten Level, wenn die Levelnummer nicht groesser als maxLevel ist.
     * Sonst ist das Spiel bendet und es wird auf den Startscreen umgeschaltet.
     * @param levelnr Die Nummer des neuen Levels
     * @param score Der aktuelle Scorewert des Spiels nach Beendigung des vorherigen Levels
     */
    public void createLevel(int levelnr, int score) {
    	this.score = score;
    	if (levelnr <= maxLevel) {
    		// erzeugt neues Levelobjekt
    		level = new Level(this, levelnr, score);
    		// ruft die run-Methode des neuen Level-Objektes auf
        	level.start();
            // ruft Methode des Controlers auf, um auf das Spielfeld umzuschalten
            controller.toPlayground();
    	}
    	else {
    		// ruft Methode des Controlers auf, um bei Spielende auf den Startscreen umzuschalten
    		controller.toStartScreen();
    		
    	}
    	
    }
    
    /**
     * Setter for the total Score
     * @param score as an integer value
     */
    public void setScore(int score) {
    	this.score=score;
    }
    
    /**
     * Getter for maxLevel
     * @return maxLevel an integer value
     */
    public int getMaxLevel() {
    	return this.maxLevel;
    }
    
}
    


	
