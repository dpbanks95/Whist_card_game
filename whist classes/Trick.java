package whist;
import cards.Card;
import cards.Card.Suit;


/**
 * Skeleton class for storing information about whist tricks
 * @author ajb
 */
public class Trick{
   public static Suit trumps;
   
   public Trick(int p){}    //p is the lead player 
   
   public static void setTrumps(Suit s){
       trumps=s;
   }
    
/**
 * 
 * @return the Suit of the lead card.
 */    
    public Suit getLeadSuit(){
        throw new UnsupportedOperationException("get lead suit not supported yet."); 
    }
/**
 * Records the Card c played by Player p for this trick
 * @param c
 * @param p 
 */
    public void setCard(Card c, Player p){
        throw new UnsupportedOperationException("set card not supported yet."); 
    }
/**
 * Returns the card played by player with id p for this trick
 * @param p
 * @return 
 */    
    public Card getCard(Player p){
        throw new UnsupportedOperationException("get card not supported yet."); 
    }
    
/**
 * Finds the ID of the winner of a completed trick
 */    
    public int findWinner(){
        throw new UnsupportedOperationException("get find winner not supported yet."); 
    }
}
