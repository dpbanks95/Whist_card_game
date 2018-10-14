package whist;

import cards.Card;
import cards.Deck;
import cards.Card.Suit;
import cards.Hand;
import java.util.Scanner;

/**
 * Main Whist Game Driver
 * @author Daniel Banks
 */
public class BasicWhist {
    static final int NOS_PLAYERS=4;
    static final int NOS_TRICKS=13;
    static final int WINNING_POINTS=7;
    int team1Points=0;
    int team2Points=0;
    static int team1total=0;
    static int team2total=0;
    Player[] players;
    
    public BasicWhist(Player[] pl){
        players = pl;
    }
    
    /**
     * Deals a hand of 13 cards to each player
     * @param newDeck
     */
    public void dealHands(Deck newDeck){
        for(int i=0;i<52;i++){
            players[i%NOS_PLAYERS].dealCard(newDeck.deal());
        }
    }
    
    /**
     * Method to create and run each trick
     * @param firstPlayer
     * @return Completed Trick
     */
    public Trick playTrick(Player firstPlayer){
        Trick t=new Trick(firstPlayer.getID());
        int playerID=firstPlayer.getID();
        
        for(int i=0;i<NOS_PLAYERS;i++){
            int next=(playerID+i)%NOS_PLAYERS;
            Card c = players[next].playCard(t);
            t.setCard(c,players[next]);
        }
        for(Player player: players){
            player.viewTrick(t);
        }
        return t;
    }
    
    // Method to create and run each round of play and tally scores
    public void playGame(){
        Deck d = new Deck();
        dealHands(d);
        int firstPlayer=(int)(NOS_PLAYERS*Math.random());
        //Pick a random sout for each round
        Suit trumps=Suit.randomSuit();
        Trick.setTrumps(trumps);
        
        int team1Tricks=0;
        int team2Tricks=0;
        
        for(int i=0;i<NOS_TRICKS;i++){
            Trick t=playTrick(players[firstPlayer]);
            System.out.println("");
            firstPlayer=t.findWinner();
            System.out.println("The Winner is Player "+firstPlayer);
            //Determine team points
            switch(firstPlayer){
                case 0: team1Tricks++;
                    break;
                case 2: team1Tricks++;
                    break;
                case 1: team2Tricks++;
                    break;
                case 3: team2Tricks++;
                    break;
            }
            System.out.println("Team 1 Tricks won - "+team1Tricks);
            System.out.println("Team 2 Tricks won - "+team2Tricks);
            System.out.println("");
            Scanner scan = new Scanner(System.in);
            System.out.println("---------------------------------------------");
        }
        
        if(team1Tricks>6){
            team1Points += team1Tricks-6;
        }
        if(team2Tricks>6){
            team2Points += team2Tricks-6;
        }
    }
    
    //
    public void playMatch(){
        team1Points=0;
        team2Points=0;
        while(team1Points<WINNING_POINTS && team2Points<WINNING_POINTS){
            System.out.println("---------------------------------------------");
            System.out.println("NEW ROUND");
            playGame();
            System.out.println("Team 1 Total points - " + team1Points);
            System.out.println("Team 2 Total points - " + team2Points);
        }
        if(team1Points>=WINNING_POINTS){
            System.out.println("Winning team is team 1 with "+
                    team1Points+" Points");
            team1total++;
        }
        else{
            System.out.println("Winning team is team 2 with "+
                    team2Points+" Points");
            team2total++;
        }
        System.out.println("");
        System.out.println("Team 1 Total Wins: "+team1total);
        System.out.println("Team 2 Total Wins: "+team2total);
        System.out.println("");
    }
    
    //4 Basic players
    public static void playBasicGame(){
        Player[] p = new Player[NOS_PLAYERS];
        for(int i=0;i<p.length;i++){
            p[i]=new BasicPlayer(i, new Hand(), new BasicStrategy(i));
        }
        
        BasicWhist bg=new BasicWhist(p);
        bg.playMatch(); //Just plays a single match
        System.out.println("BASIC GAME FINSHED");
    }
    
    //4 advanced players
    public static void playAdvGame(){
        Player[] p = new Player[NOS_PLAYERS];
        for(int i=0;i<p.length;i++){
            p[i]=new BasicPlayer(i, new Hand(), new AdvancedStrategy(i));
        }
        
        BasicWhist bg=new BasicWhist(p);
        bg.playMatch(); //Just plays a single match
        System.out.println("ADVANCED GAME FINSHED");
    }
    //2 basic partners, 2 advanced partners
    public static void playBasicAdvGame(){
        Player[] p = new Player[NOS_PLAYERS];
        
            p[0]=new BasicPlayer(0, new Hand(), new BasicStrategy(0));
            p[1]=new BasicPlayer(1, new Hand(), new AdvancedStrategy(1));
            p[2]=new BasicPlayer(2, new Hand(), new BasicStrategy(2));
            p[3]=new BasicPlayer(3, new Hand(), new AdvancedStrategy(3));
        
        BasicWhist bg=new BasicWhist(p);
        bg.playMatch(); //Just plays a single match
        System.out.println("BASIC/ADVANCED GAME FINSHED");
    }
    
    public static void main(String[] args) {
        boolean play = true;
        Scanner scan = new Scanner(System.in);
        
        while(play){
//            playBasicGame();
//            HumanStrategy.humanGame();
//            playAdvGame();
            playBasicAdvGame();
            
            System.out.println("");
            String replay= "";
            System.out.println("Type 'R' and hit enter to play again, "
                    + "otherwise type anything else.");
            replay = scan.nextLine();
            
            play = "R".equals(replay)||"r".equals(replay);
        }
        
    }
    
}
