package whist;
import cards.Card;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import cards.Card.Suit;


/**
 * Trick Class used by BasicWhist
 * @author Daniel Banks
 */
public class Trick{
    public static Suit trumps;
    private Card[] trickCards;
    private Card leadCard;
    private int leadPlayer;
    private Card topCard;
    
    //p is the lead player

    /**
     * Trick constructor
     * @param p
     */
        public Trick(int p){
        leadPlayer = p;
        trickCards = new Card[BasicWhist.NOS_PLAYERS];
    }
    
    public static void setTrumps(Suit s){
        trumps=s;
    }
    
    public int getLeadPlayer(){
        return leadPlayer;
    }
    
/**
 * @return the Suit of the lead card.
 */
    public Suit getLeadSuit(){
        return leadCard.getSuit();
    }
    
    /**
     * @return all cards currently in this trick
     */
    public Card[] getCards(){
        return trickCards;
    }
    
    /**
     * @param index
     * @return Card at given index
     */
    public Card getCard(int index){
        return trickCards[index];
    }
    
    /**
     * @return currently winning card
     */
    public Card getTopCard(){
        return topCard;
    }
    
/**
 * Records the Card c played by Player p for this trick
 * @param c
 * @param p 
 */
    public void setCard(Card c, Player p){
        trickCards[p.getID()] = c;
        if(p.getID() == leadPlayer){
            leadCard = c;
        }
        topCard = trickCards[findWinner()];
    }

    
    //Remove all cards from trick
    public void clearTrick(){
        trickCards = new Card[4];
    }
/**
 * Returns the card played by player with id p for this trick
 * @param p
 * @return Card of given player
 */
    public Card getCard(Player p){
        return trickCards[p.getID()];
    }
    
/**
 * Finds the ID of the winner of a completed trick
 * @return ID of the winner
 */
    public int findWinner(){
        ArrayList<Card> finalCards = new ArrayList<>();
        ArrayList<Card> trumpCards = new ArrayList<>();
        boolean isTrump = false;
        Card winnerCard = null;
        
        for(Card card: trickCards){
            if(card != null){
                if(card.getSuit() == getLeadSuit()){
                    finalCards.add(card);
                }
                if(card.getSuit() == trumps){
                    finalCards.add(card);
                    isTrump = true;
                }
            }        
        }
        
        if(isTrump){
            for(Card card: finalCards){
                if(card.getSuit() == trumps){
                    trumpCards.add(card);
                }
            }
            Collections.sort(trumpCards);
            winnerCard = trumpCards.get(0);
        }else{
            Collections.sort(finalCards);
            winnerCard = finalCards.get(0);
        }
        return Arrays.asList(trickCards).indexOf(winnerCard);
    }

    @Override
    public String toString(){
        StringBuilder str = new StringBuilder();
        str.append("CURRENT TRICK").append("\n");
        str.append("Trick Cards - ").append(Arrays.toString(trickCards))
                .append("\n");
        str.append("      Trump - ").append(trumps).append("\n");
        str.append("  Lead Card - ").append(leadCard).append("\n");
        str.append("Lead Player - ").append(leadPlayer).append("\n");
        str.append("   Top Card - ").append(topCard).append("\n");
        
        return str.toString();
    }
    
    // FOR TESTING -------------------------------------------------------------
    public void setTrick(Card[] cards){
        trickCards = cards;
    }
    
    public void setLeadCard(Card card){
        leadCard = card;
    }
    
    public static void main(String[] args) {
        Trick trick = new Trick(2);

        Card[] cards = new Card[4];
        cards[0]=new Card(Card.Rank.FOUR, Suit.SPADES);
        cards[1]=new Card(Card.Rank.TEN, Suit.SPADES);
        cards[2]=new Card(Card.Rank.TEN, Suit.DIAMONDS);
        cards[3]=new Card(Card.Rank.TWO, Suit.CLUBS);
        
        trick.setLeadCard(new Card(Card.Rank.FOUR, Suit.SPADES));
        trick.setTrick(cards);
        Trick.setTrumps(Suit.HEARTS);
        
        System.out.println(trick.findWinner());
    }
}
