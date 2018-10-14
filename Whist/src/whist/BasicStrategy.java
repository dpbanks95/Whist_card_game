package whist;

import cards.Card;
import cards.Deck;
import cards.Hand;
import java.util.Arrays;

/**
 * Class for a Basic Whist Strategy
 * @author Daniel Banks
 */
public class BasicStrategy implements Strategy {
    
    int id;
    Trick t;

    /**
     * BasicStrategy Constructor
     * @param id
     */
    public BasicStrategy(int id) {
        this.id = id;
    }
    
    //Sort cards in hand
    Hand nonTrumpOrLeadCards = new Hand();
    Hand trumpCards = new Hand();
    Hand leadCards = new Hand();
    
/**
 * Choose a card from hand h to play in trick t 
 * @param h
 * @param tr
 * @return 
 */  
    @Override
    public Card chooseCard(Hand h, Trick tr) {
        t=tr;
        Boolean isFirst = true;
        Boolean partnerPlayed = false;
        Boolean partnerWinning = false;
        Boolean topTrump = false;
        int partner = 0;
        
        nonTrumpOrLeadCards.getCards().clear();
        trumpCards.getCards().clear();
        leadCards.getCards().clear();
        
        Card[] trickCards = t.getCards();
//        System.out.println("Current Trick: "+ Arrays.toString(trickCards));
        
        //Check if first player
        for(Card card: trickCards){
            if (card != null){
                isFirst = false;
                break;
            }
        }
        //If first, play highest card
        if(isFirst){
            h.sortByRank();
            return h.getCards().get(0);
        }
        //Determin if cards in Hand are Trump/Lead
        for(Card card: h.getCards()){
            if(card.getSuit() == t.getLeadSuit()){      //Lead
                leadCards.addCard(card);
            }else if(card.getSuit() == Trick.trumps){   //Trump
                trumpCards.addCard(card);
            }else                                       //Other
                nonTrumpOrLeadCards.addCard(card);
        }       
        //Find partner
        switch(id){
            case 0: partner = 2;
                    break;
            case 1: partner = 3;
                    break;
            case 2: partner = 0;
                    break;
            case 3: partner = 1;
                    break;
        }
        //check if partner has played
        if(trickCards[partner] != null){
            partnerPlayed = true;
        }
        //If partner has played, check if they're winning
        if(partnerPlayed){
            if(trickCards[partner] == t.getTopCard()){
                partnerWinning = true;
            }
        }
        //Determine if top card in trick is trump
        if(t.getTopCard().getSuit() == Trick.trumps 
                && Trick.trumps != t.getLeadSuit()){
            topTrump = true;
        }
        //if partner is winning, discard low card
        if(partnerWinning){
            return discardLowest();
            
        }else{//If parnter is not winning play best card, if you can win
            if(!leadCards.getCards().isEmpty()){//play lead suit
                leadCards.sortByRank();
                //player highest lead > trick top card & trick top card 
                //isn't trump, play highest lead
                if(leadCards.getCards().get(0).getRank().getCardRank() 
                        > t.getTopCard().getRank().getCardRank() 
                        && topTrump == false){
                    return leadCards.getCards().get(0);
                }
                
            }else if(!trumpCards.getCards().isEmpty()){//play trump
                trumpCards.sortByRank();
                //play highest trump if trick top card isn't trump
                if(topTrump == false){
                    return trumpCards.getCards().get(0);
                    
                //if top card is trump, play trump if higher than top card
                }else if(topTrump == true){
                    if(trumpCards.getCards().get(0).getRank().getCardRank() 
                            > t.getTopCard().getRank().getCardRank()){
                        return trumpCards.getCards().get(0);   
                    }
                }
            }
        }
        return discardLowest();
    }
    
    //Discard the lowest card in toy hand that you can
    public Card discardLowest(){
        if(!leadCards.getCards().isEmpty()){//play lowest lead
            leadCards.sortByRank();
            return leadCards.getCards().get(leadCards.getCards().size()-1);
            
        //play lowest non trump or lead
        }else if(!nonTrumpOrLeadCards.getCards().isEmpty()){
            nonTrumpOrLeadCards.sortByRank();
            return nonTrumpOrLeadCards.getCards().get(
                    nonTrumpOrLeadCards.getCards().size()-1);
            
        }else{//play lowest trump
            trumpCards.sortByRank();
            return trumpCards.getCards().get(trumpCards.getCards().size()-1);
        }
    }
    
/**
 * Update internal memory to include completed trick c
 * @param c 
 */   
    @Override
    public void updateData(Trick c) {
        t=c;
    }
    
    //FOR TESTING---------------------------------------------------------------
        public static void main(String[] args) {
        BasicStrategy strat = new BasicStrategy(0);
        Hand hand1 = new Hand();
        Hand hand2 = new Hand();
        Hand hand3 = new Hand();
        Hand hand4 = new Hand();
        Deck deck = new Deck();
        Trick trick = new Trick(0);
        
        for(int i = 0; i<13;i++){
            hand1.addCard(deck.deal());
            hand2.addCard(deck.deal());
            hand3.addCard(deck.deal());
            hand4.addCard(deck.deal());
        }
        
        Trick.setTrumps(Card.Suit.CLUBS);
        System.out.println("Trump = CLUBS");
        BasicPlayer player1 = new BasicPlayer(0, hand1, strat);
        BasicPlayer player2 = new BasicPlayer(1, hand2, strat);
        BasicPlayer player3 = new BasicPlayer(2, hand3, strat);
        BasicPlayer player4 = new BasicPlayer(3, hand4, strat);
        
        hand1.sort();
        hand2.sort();
        hand3.sort();
        hand4.sort();
        
        System.out.println("Hand 1: "+hand1.toString());
        System.out.println("Hand 2: "+hand2.toString());
        System.out.println("Hand 3: "+hand3.toString());
        System.out.println("Hand 4: "+hand4.toString());
        System.out.println("");
        
        System.out.println("P1");
        trick.setCard(player1.playCard(trick), player1);
        System.out.println("P2");
        trick.setCard(player2.playCard(trick), player2);
        System.out.println("P3");
        trick.setCard(player3.playCard(trick), player3);
        System.out.println("P4");
        trick.setCard(player4.playCard(trick), player4);
        
        System.out.println(Arrays.toString(trick.getCards()));
        System.out.println("");
        
        hand1.sort();
        hand2.sort();
        hand3.sort();
        hand4.sort();
        System.out.println("Hand 1: "+hand1.toString());
        System.out.println("Hand 2: "+hand2.toString());
        System.out.println("Hand 3: "+hand3.toString());
        System.out.println("Hand 4: "+hand4.toString());
    }
}
