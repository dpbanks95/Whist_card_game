package whist;

import cards.Card;
import cards.Hand;
import java.util.Scanner;
import static whist.BasicWhist.NOS_PLAYERS;

/**
 *
 * @author Daniel Banks
 */
public class HumanStrategy implements Strategy{
    Hand leadCards = new Hand();
    Card chosenCard = null;
    boolean isFirst = true;
    
    int id;
    Trick trick;
    Hand hand;
    
    public HumanStrategy(int id) {
        this.id = id;
    }
    
    @Override
    public Card chooseCard(Hand h, Trick t) {
        trick = t;
        hand = h;
        
        Scanner scan = new Scanner(System.in);
        boolean validCard = true;
        boolean chooseCard = true;
        boolean validAnswer = true;
        chosenCard = null;
        isFirst = true;
        
        leadCards.getCards().clear();
        
        //Check if first player
        for(Card card: t.getCards()){
            if (card != null){
                isFirst = false;
                break;
            }
        }
        
        if(!isFirst){
            //Determin if cards in Hand are Trump/Lead
            for(Card card: h.getCards()){
                if(card.getSuit() == t.getLeadSuit()){//Lead
                    leadCards.addCard(card);
                }
            }
        }
        
        h.sort();
        System.out.println("");
        System.out.println(t.toString());
        printHand(h);
        
        pickCard();
        
        System.out.println("Play card "+chosenCard+"? ('yes' or 'no')");
        String playCard = scan.next();

        if("no".equals(playCard)||"No".equals(playCard)||"NO".equals(playCard)){
            chooseCard = false;
        }else if("yes".equals(playCard)||"Yes".equals(playCard)
                ||"YES".equals(playCard)){
            chooseCard = true;
        }else{
            System.err.println("INVALID ANSWER");
            validAnswer = false;
        }
        
        while(!validAnswer){
            System.out.println("Play card "+chosenCard+"? ('yes' or 'no')");
            playCard = scan.next();
            
            if("no".equals(playCard)||"No".equals(playCard)
                    ||"NO".equals(playCard)){
                chooseCard = false;
                validAnswer = true;
            }else if("yes".equals(playCard)||"Yes".equals(playCard)
                    ||"YES".equals(playCard)){
                chooseCard = true;
                validAnswer = true;
            }else{
                System.err.println("INVALID ANSWER");
                validAnswer = false;
            }
        }    
        
        while(!chooseCard){
            pickCard();
            System.out.println("Play card "+chosenCard+"? ('yes' or 'no')");
            playCard = scan.next();
            
            chooseCard = !("no".equals(playCard)||"No".equals(playCard)
                    ||"NO".equals(playCard));
        }
        
        System.out.println("Played "+chosenCard);
        
        return chosenCard;
    }
    
    //Pick card

    /**
     *
     */
        public void pickCard(){
        Scanner scan = new Scanner(System.in);
        boolean validCard= true;
        int handSize = hand.getCards().size()-1;
        System.out.println("Pick a Valid Card (enter number to left of cards "
                + "above, between 0"+"-"+handSize+"): ");
        
        while (!scan.hasNextInt()){
            System.err.println("INVALID INPUT");
            System.out.println("Pick a Valid Card (enter number to left of "
                    + "cards above, between 0"+"-"+handSize+"): ");
            scan.next();
        }
        int card = scan.nextInt();
        
        //Card chosen < 0 or > hand size
        if(card<0||card>=hand.getCards().size()){
            validCard = false;
        }
        
        while(!validCard){
            System.err.println("INVALID CARD");
            System.out.println("Pick a Valid Card (enter number to left of "
                    + "cards above, between 0"+"-"+handSize+"): ");
        
            while (!scan.hasNextInt()){
                System.err.println("INVALID INPUT");
                System.out.println("Pick a Valid Card (enter number to left "
                        + "of cards above, between 0"+"-"+handSize+"): ");
                scan.next();
            }
            card = scan.nextInt();

            //Card is valid if between 0 and hand size
            validCard = !(card<0||card>=hand.getCards().size());
        }
        
        chosenCard = hand.getCards().get(card);
        
        if(!isFirst){
            Boolean validateCard = validateCard(chosenCard);
            if(!validateCard){
                System.err.println("INVALID CARD, MUST BE SAME SUIT AS LEAD "
                        + "IF YOU HAVE IT");
                System.out.println("");
                pickCard();
            }
        }
    }
    
    //Validate the chosen card
    public Boolean validateCard(Card c){
        boolean validCard = true;
        //If you have lead cards, must play same suit card
        if(!leadCards.getCards().isEmpty()){
            if(leadCards.getCards().contains(c)){
                validCard = true;
            }else{
                validCard = false;
                return validCard;
            }
        }
        return validCard;
    }
    
    public void printHand(Hand h){
        System.out.println("YOUR HAND");
        System.out.println(h.stringForHuman());
    }
    
    @Override
    public void updateData(Trick c) {
    }

    void setId(int id) {
        this.id = id;
    }
    
    //3 basic Players and 1 Human Player
    public static void humanGame(){
        Player[] p = new Player[NOS_PLAYERS];
        for(int i=0;i<p.length-1;i++){
            p[i]=new BasicPlayer(i, new Hand(), new BasicStrategy(i));
        }
        p[p.length-1] = new BasicPlayer(p.length-1, new Hand(), 
                new HumanStrategy(3));
        BasicWhist bg=new BasicWhist(p);
        bg.playMatch();
        System.out.println("HUMAN GAME FINISHED");
    }    
}
