package cards;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

/**
 *
 * @author Daniel Banks
 */
public class Hand implements Serializable, Iterable<Card> {
    static final long serialVersionUID = 300L;
    
    ArrayList<Card> hand;
    private int heartCount;
    private int spadeCount;
    private int diamondCount;
    private int clubCount;
    private int handValue;
    
    public Hand(){
        hand = new ArrayList<>();
    }
    
    public Hand(List<Card> cards){
        hand = new ArrayList<>();
        addCollection(cards);
    }
    
    public Hand(Hand hand){
        this.hand = new ArrayList<>();
        addHand(hand);
    }
    
    public int getHandValue(){
        return handValue;
    }
    
    public void addCard(Card card){
        hand.add(card);
        handValue += card.getRank().getValue();
        incrementSuit(card.getSuit());
    }
    
    public void addCollection(List<Card> cards){
        for(Card card: cards){
            hand.add(card);
            handValue += card.getRank().getValue();
            incrementSuit(card.getSuit());
        }
    }
    
    public void addHand(Hand hand){
        for(Card card: hand.getCards()){
            this.hand.add(card);
            handValue += card.getRank().getValue();
            incrementSuit(card.getSuit());
        }
    }
    
    public ArrayList<Card> getCards(){
        return hand;
    }
    
    public boolean removeSingleCard(Card card){
        boolean contain = hand.contains(card);
        if(contain){
            decrementSuit(card.getSuit());
            handValue -= card.getRank().getValue();
            hand.remove(card);
        }
        return contain;
    }
    
    public boolean removeOtherHand(Hand hand){
        boolean contain = false;
        for(Card card: hand.getCards()){
            if(this.hand.contains(card)){
                contain = true;
            }else{
                return false;
            }
        }
        if(contain){
            for(Card card: hand.getCards()){
                decrementSuit(card.getSuit());
                handValue -= card.getRank().getValue();
                this.hand.remove(card);
            }
        }
        return true;
    }
    
    public Card removeCardIndex(int index){
        Card card = hand.get(index);
        decrementSuit(card.getSuit());
        handValue -= card.getRank().getValue();
        return hand.remove(index);
    }

    @Override
    public Iterator<Card> iterator(){
        return new CardIterator(this);
    }
    
    public int getHeartCount() {
        return heartCount;
    }
    
    public int getSpadeCount() {
        return spadeCount;
    }
    
    public int getDiamondCount() {
        return diamondCount;
    }
    
    public int getClubCount() {
        return clubCount;
    }
    
    public static class CardIterator implements Iterator<Card>{
        private Hand hand;
        private int position;
        
        CardIterator(Hand hand){
            this.hand = hand;
            position = 0;
        }
        
        @Override
        public boolean hasNext() {
            return position < hand.getCards().size();
        }

        @Override
        public Card next() {
            if(position >= hand.getCards().size()){
                throw new NoSuchElementException( "no more cards in hand" );
            }
            return hand.getCards().get(position++);
        }

        @Override
        public void remove() {
            hand.removeCardIndex(position);
        }
    }
    
    public void sort(){
        Collections.sort(hand, Card.CompareAscending.ASCENDING);
    }
    
    public void sortByRank(){
        Collections.sort(hand, Card.CompareRank.RANK);
    }
    
    public void incrementSuit(Card.Suit suit){
        switch(suit){
            case CLUBS: clubCount++;
                break;
            case DIAMONDS: diamondCount++;
                break;
            case HEARTS: heartCount++;
                break;
            case SPADES: spadeCount++;
                break;
        }
    }
    
    public void decrementSuit(Card.Suit suit){
        switch(suit){
            case CLUBS: clubCount--;
                break;
            case DIAMONDS: diamondCount--;
                break;
            case HEARTS: heartCount--;
                break;
            case SPADES: spadeCount--;
                break;
        }
    }
    
    public int countSuit(Card.Suit suit){
        switch(suit){
            case CLUBS: return clubCount;
            case DIAMONDS: return diamondCount;
            case HEARTS: return heartCount;
            case SPADES: return spadeCount;
            default: return 0;
        }
    }
    
    public int countRank(Card.Rank rank){
        int rankCount = 0;
        
        for(Card card: hand){
            if(card.getRank() == rank){
                rankCount++;
            }
        }
        return rankCount;
    }
    
    public boolean hasSuit(Card.Suit suit){
        for(Card card: hand){
            if(card.getSuit() == suit){
                return true;
            }
        }
        return false;
    }
    
    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        str.append("Hand< ");
        for(Card card: hand){
            str.append(card.toString()).append(", ");
        }
        str.append(">");
        return str.toString();
    }
    
    public String stringForHuman() {
        StringBuilder str = new StringBuilder();
        for(int i=0; i<hand.size(); i++){
            str.append("(").append(i).append(") ").append(hand.get(i).toString()).append("\n");
        }
        return str.toString();
    }
    
    public static void main(String[] args) {
        Hand hand = new Hand();
        Card card1 = new Card(Card.Rank.ACE, Card.Suit.CLUBS);
        hand.addCard(card1);
        hand.addCard(new Card(Card.Rank.SEVEN, Card.Suit.CLUBS));
        hand.addCard(new Card(Card.Rank.SEVEN, Card.Suit.DIAMONDS));
        hand.addCard(new Card(Card.Rank.EIGHT, Card.Suit.DIAMONDS));
        
//        System.out.println(hand.toString());
//        System.out.println("");
//        System.out.println("CountRank ACE "+ hand.countRank(Card.Rank.ACE));
//        System.out.println("");
//        System.out.println("CountSuit CLUBS "+ hand.countSuit(Card.Suit.CLUBS));
//        System.out.println("");
//        System.out.println("HandValue "+ hand.getHandValue());
//        System.out.println("");
//        System.out.println("HasSuit HEARTS "+hand.hasSuit(Card.Suit.HEARTS));
//        System.out.println("HasSuit CLUBS "+hand.hasSuit(Card.Suit.CLUBS));
//        System.out.println("");
//        hand.sort();
//        System.out.println("Sort by suit");
//        System.out.println(hand.toString());
//        System.out.println("");
//        hand.sortByRank();
//        System.out.println("Sort by rank");
//        System.out.println(hand.toString());
//        System.out.println("");
//        System.out.println("Removed "+card1+" = "+hand.removeSingleCard(card1));
//        System.out.println("CountSuit CLUBS "+ hand.countSuit(Card.Suit.CLUBS));
        
        System.out.println(hand);
        hand.sortByRank();
        System.out.println(hand);
    }
}
