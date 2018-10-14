package cards;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Random;

/**
 *
 * @author Daniel Banks
 */
public class Card implements Serializable, Comparable<Card> {
    static final long serialVersionUID = 100L;
    
    private Rank rank;
    Suit suit;
    private Iterator<Object> iterator;
    
    public Card(Rank rank, Suit suit){
        this.rank = rank;
        this.suit = suit;
    }
    
    @Override
    public int compareTo(Card o) {
        if(this.suit.ordinal() < o.suit.ordinal()){
            return -1;
        }else if(this.suit.ordinal() > o.suit.ordinal()){
            return 1;
        }else{
            if(this.rank.ordinal() < o.rank.ordinal()){
                return -1;
            }else if(this.rank.ordinal() > o.rank.ordinal()){
                return 1;
            }else{
                return 0;
            }
        }
    }
    
    //Nested class to sort a list of cards in ascending order by suit then rank
    public static class CompareAscending{
        public static Comparator<Card> ASCENDING = new Comparator<Card>(){
            @Override
            public int compare(Card o1, Card o2) {
                if(o1.suit.ordinal() < o2.suit.ordinal()){
                    return -1;
                }else if(o1.suit.ordinal() > o2.suit.ordinal()){
                    return 1;
                }else{
                    if(o1.rank.ordinal() < o2.rank.ordinal()){
                        return 1;
                    }else if(o1.rank.ordinal() > o2.rank.ordinal()){
                        return -1;
                    }else{
                        return 0;
                    }
                }
            }
        };
    }
    
    public static class CompareRank{
        public static Comparator<Card> RANK = new Comparator<Card>(){
            @Override
            public int compare(Card o1, Card o2) {
                if(o1.rank.ordinal() > o2.rank.ordinal()){
                    return 1;
                }else if(o1.rank.ordinal() < o2.rank.ordinal()){
                    return -1;
                }else{
                    return 0;
                }
            }
        };
    }
    
    /**
     * Enum for each Card Rank
     */
    public static enum Rank{
        ACE(11, 13),KING(10, 12),QUEEN(10, 11),JACK(10, 10),TEN(10, 9),
        NINE(9, 8),EIGHT(8, 7),SEVEN(7, 6),SIX(6, 5),FIVE(5, 4),FOUR(4, 3),
        THREE(3, 2),TWO(2, 1);
        
        int cardValue;
        int cardRank;
        
        public static Rank[] values = values();
        
        //cardValue stores the value of that rank, cardRank stores the 
        //position of that rank
        Rank(int cardValue, int cardRank){
            this.cardValue = cardValue;
            this.cardRank = cardRank;
        }
        
        public int getCardRank() {
            return cardRank;
        }
        
        public int getValue(){
            return cardValue;
        }
        
        public Rank getPrevious(){
            return values[(ordinal()-1) % values.length];
        }
    }
    
    /**
     * Enum for each Card Suit
     */
    public static enum Suit{
        SPADES,HEARTS,DIAMONDS,CLUBS;
        
        public static Suit[] values = values();
        
        public static Suit randomSuit(){
            int pick = new Random().nextInt(values.length);
            return values[pick];
        }
    }
    
    /**
     * Method to get the max value card in a list of Card objects
     * @param cards
     * @return Max value card
     */
    public static Card max(List<Card> cards){
        Card maxCard = cards.get(0);
        Iterator<Card> iterator = cards.iterator();
        while(iterator.hasNext()){
            int compare = maxCard.compareTo(iterator.next());
            if (compare == 1){
                maxCard = iterator.next();
            }
        }
        return maxCard;
    }
    
    public Rank getRank(){
        return this.rank;
    }
    
    public Suit getSuit(){
        return this.suit;
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        str.append(this.rank).append(" ").append(this.suit);
        return str.toString();
    }
    
    public static void main(String[] args) {
        List<Card> cards = new ArrayList<>();
        cards.add(new Card(Rank.TEN, Suit.DIAMONDS));
        cards.add(new Card(Rank.FOUR, Suit.SPADES));
        cards.add(new Card(Rank.TEN, Suit.SPADES));
        cards.add(new Card(Rank.TWO, Suit.CLUBS));
        cards.add(new Card(Rank.SIX, Suit.HEARTS));
        cards.add(new Card(Rank.KING, Suit.HEARTS));
        cards.add(new Card(Rank.THREE, Suit.CLUBS));
//        System.out.println(Rank.ACE.getValue());
//        System.out.println(cards.toString());
//        
//        Collections.sort(cards);
//        System.out.println(cards.toString());
//        
//        Collections.sort(cards, Card.CompareAscending.ASCENDING);
//        System.out.println(cards.toString());
//        
//        Collections.sort(cards, Card.CompareRank.RANK);
//        System.out.println(cards.toString());
//        
//        System.out.println(cards.get(2));
//        System.out.println(cards.get(2).getRank());
//        System.out.println(cards.get(2).getSuit());
//        
        System.out.println(Card.max(cards)); 
    }
}
