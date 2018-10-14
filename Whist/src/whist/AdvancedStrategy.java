package whist;

import cards.Card;
import cards.Hand;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * 
 * @author Daniel Banks
 */
public class AdvancedStrategy implements Strategy{
    int id;
    Trick t;
    int partner = 0;
    
    //Store Cards from previous tricks
    ArrayList <Card> playedCards = new ArrayList<>();
    //Store Partners Played Cards
    ArrayList <Card> partnerCards = new ArrayList<>();
    
    //Sort cards in hand
    Hand nonTrumpOrLeadCards = new Hand();
    Hand trumpCards = new Hand();
    Hand leadCards = new Hand();
    
    public AdvancedStrategy(int id) {
        this.id = id;
    }
    
/**
 * Choose a card from hand h to play in trick t 
 * @param h
 * @param tr
 * @return 
 */  
    @Override
    public Card chooseCard(Hand h, Trick tr) {
        System.out.println(h);
        System.out.println(tr.toString());
        t=tr;
        Boolean isFirst = true;
        Boolean partnerPlayed = false;
        Boolean partnerWinning = false;
        Boolean topTrump = false;
        Boolean lastPlayer = false;
        
        int playerCount = 0;
        
        nonTrumpOrLeadCards.getCards().clear();
        trumpCards.getCards().clear();
        leadCards.getCards().clear();
        
        if(playedCards.size() == 52){
            playedCards.clear();
        }
        
        Card[] trickCards = t.getCards();
        
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
        //Check if first player
        for(Card card: trickCards){
            if (card != null){
                isFirst = false;
                playerCount++;
            }
        }
        //Check if you're the last player
        if(playerCount == 3){
            lastPlayer = true;
        }
        //If first, play highest card
        if(isFirst){
            for(Card card: h.getCards()){
                if(card.getSuit() == Trick.trumps){   //Trump
                    trumpCards.addCard(card);
                }
            }
            trumpCards.sortByRank();
            // if you have trump Ace, lead with it
            if(!trumpCards.getCards().isEmpty()){
                if(trumpCards.getCards().get(0).getRank() == Card.Rank.ACE){
                    return trumpCards.getCards().get(0);
                }
            }
            //Dont have trump ace then lead with highest card
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
        if(t.getTopCard().getSuit() == Trick.trumps && Trick.trumps 
                != t.getLeadSuit()){
            topTrump = true;
        }
        //if partner is winning, discard low card
        if(partnerWinning){
            return discardLowest();
            
        }else{//If parnter is not winning play best card, if you can win
            if(!leadCards.getCards().isEmpty()){//play lead suit
                leadCards.sortByRank();
                if(lastPlayer){
                    for(int i = leadCards.getCards().size()-1; i>=0; i--){
                        if(leadCards.getCards().get(i).getRank().getCardRank() 
                                > t.getTopCard().getRank().getCardRank() ){
                            return leadCards.getCards().get(i);
                        }
                    }
                }
                //player highest lead > trick top card & trick top card 
                //isn't trump, play highest lead
                else if(leadCards.getCards().get(0).getRank().getCardRank() 
                        > t.getTopCard().getRank().getCardRank() 
                        && topTrump == false){
                    return leadCards.getCards().get(0);
                }
                
            }else if(!trumpCards.getCards().isEmpty()){//play trump
                trumpCards.sortByRank();
                if(lastPlayer){
                    //if last player, and no trumps, play lowest trump
                    if(topTrump == false){
                        return trumpCards.getCards().get(
                                trumpCards.getCards().size()-1);
                    }
                    //If there is other trump, play lowest needed to beat
                    else if(topTrump == true){
                        for(int i = trumpCards.getCards().size()-1; i>=0; i--){
                            if(trumpCards.getCards().get(i).getRank()
                                    .getCardRank() > t.getTopCard().
                                            getRank().getCardRank() ){
                                return trumpCards.getCards().get(i);
                            }
                        }
                    }
                }
                //play highest trump if trick top card isn't trump
                else if(topTrump == false){
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
        playedCards.addAll(Arrays.asList(c.getCards()));
        partnerCards.add(c.getCard(partner));
        
    }
}
