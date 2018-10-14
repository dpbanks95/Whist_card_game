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
public class Deck implements Serializable, Iterable<Card> {
    static final long serialVersionUID = 200L;
    
    private List<Card> deck;
    private int currentCard;
    
    //Create Deck then fill and shuffle it
    public Deck(){
        deck = new ArrayList<>();
        fillDeck();
        shuffle();
    }
    
    private void fillDeck(){
        for (Card.Suit suit : Card.Suit.values()) {
            for (Card.Rank rank : Card.Rank.values()) {
                deck.add(new Card(rank, suit));
            }
        }
    }
    
    //Shuffle a deck
    public void shuffle() {
        for(int i = deck.size()-1; i > 0; i--) {
            int rand = (int)(Math.random()*(i+1));
            Card temp = deck.get(i);
            deck.set(i, deck.get(rand));
            deck.set(rand, temp);
        }
    }
    
    public int size(){
        return deck.size();
    }
    
    public void newDeck(){
        deck = new ArrayList<>();
        fillDeck();
        shuffle();
    }
    
    @Override
    public Iterator<Card> iterator(){
        return new CardIterator(this);
    }
    
    public static class CardIterator implements Iterator<Card>{
        private Deck deck;
        private int position;
        
        CardIterator(Deck deck){
            this.deck = deck;
            position = 0;
        }
        
        @Override
        public boolean hasNext() {
            return position < deck.getDeck().size();
        }

        @Override
        public Card next() {
            if(position >= deck.getDeck().size()){
                throw new NoSuchElementException( "no more cards in deck" );
            }
            return deck.getDeck().get(position++);
        }

        @Override
        public void remove() {
            deck.getDeck().remove(position);
        }
        
    }
    
    public static class SecondCardIterator implements Iterator<Card>{
        private Deck deck;
        private int position;
        
        SecondCardIterator(Deck deck){
            try {
                this.deck = (Deck)deck.clone();
            } catch (CloneNotSupportedException ex) {
                System.out.println(ex);
            }
            Collections.sort(this.deck.getDeck());
            position = 0;
        }
        
        @Override
        public boolean hasNext() {
            return position < deck.getDeck().size();
        }

        @Override
        public Card next() {
            if(position >= deck.getDeck().size()){
                throw new NoSuchElementException( "no more cards in deck" );
            }
            return deck.getDeck().get(position++);
        }

        @Override
        public void remove() {
            deck.getDeck().remove(position);
        }
    }
    
    //Deal the top card from the Deck
    public Card deal(){
        CardIterator iterator = new CardIterator(this);
        if(!iterator.hasNext()){
            throw new IllegalStateException("No cards are left in the deck.");
        }
        Card dealCard = iterator.next();
        deck.remove(dealCard);
        return dealCard;
    }
    
    public List<Card> getDeck(){
        return deck;
    }
    
    public static void main(String[] args) {
        Deck deck = new Deck();
        System.out.println(deck.getDeck());
        deck.shuffle();
        System.out.println(deck.getDeck());
        
        // Deal Hand
        try {
            int i=0;
            while (i < 13) {
                System.out.println(deck.deal());
                System.out.println(deck.size());
                i++;
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        System.out.println(deck.getDeck());
        System.out.println(deck.size());
        
        // Shuffled Iterate
//        Iterator<Card> iterator = new CardIterator(deck);
//        while(iterator.hasNext()){
//            Card next = iterator.next();
//            System.out.println(next);
//        }
        
        // Ordered Iterate
//        Iterator<Card> secondIterator = new SecondCardIterator(deck);
//        while(secondIterator.hasNext()){
//            Card next = secondIterator.next();
//            System.out.println(next);
//        }
        
        // Default iterate
//        Iterator<Card> iterator = deck.iterator();
//        while(iterator.hasNext()){
//            Card next = iterator.next();
//            System.out.println(next);
//        }
        
        
    }
}
