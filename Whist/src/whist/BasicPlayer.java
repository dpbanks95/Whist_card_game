package whist;

import cards.*;
import java.util.Collections;

/**
 * Class to create a Basic Player
 * @author Daniel Banks
 */
public class BasicPlayer implements Player {
    Hand hand;
    Strategy strategy;
    Trick currentTrick;
    int id;

    /**
     * BasicPlayer Constructor
     * @param id
     * @param hand
     * @param strategy
     */
    public BasicPlayer(int id, Hand hand, Strategy strategy) {
        this.hand = hand;
        this.strategy = strategy;
        this.id = id;
    } 
    
/**
 * Adds card c to this players hand
 * @param c 
 */ 
    @Override
    public void dealCard(Card c) {
        hand.addCard(c);
    }
    
/** 
 * Allows for external setting of player strategy
 * @param s 
 */    
    @Override
    public void setStrategy(Strategy s) {
        strategy = s;
    }
    
/**
 * Determines which of the players cards to play based on the in play trick t 
 * and player strategy
 * @param t
 * @return card to play
 */
    @Override
    public Card playCard(Trick t) {
        System.out.println("");
        System.out.println("PLAYER "+id);
        Card c = strategy.chooseCard(this.hand, t);
        Collections.sort(hand.getCards());
//        System.out.println("Player "+id+" hand: "+hand.toString());
        System.out.println("Card Played: "+c);
        this.hand.removeSingleCard(c);
        return c;
    }
    
/**
 * Game passes the players the completed trick
 * @param t
 */    
    @Override
    public void viewTrick(Trick t) {
        strategy.updateData(t);
    }

    /**
     * Set the public static trump in the Trick class
     * @param s
     */
    @Override
    public void setTrumps(Card.Suit s) {
        Trick.trumps = s;
    }

    /**
     * Get the id of this player
     * @return player ID
     */
    @Override
    public int getID() {
        return id;
    }
    
}
