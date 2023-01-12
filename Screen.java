import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Screen extends JPanel implements ActionListener{

    private DLList<Card> deck = new DLList<Card>();
    private DLList<Pair<Boolean, Card>> hand = new DLList<Pair<Boolean, Card>>();

    private boolean holdCard1 = false;
    private boolean holdCard2 = false;
    private boolean holdCard3 = false;
    private boolean holdCard4 = false;
    private boolean holdCard5 = false;

    private boolean canHold = true;
    
    private JButton hold1;
    private JButton hold2;
    private JButton hold3;
    private JButton hold4;
    private JButton hold5;
    private JButton draw;
    private JButton playAgain;

    private int drawWinCode = 0;
    private boolean endOfRound = false;

    private int points = 50;


    public Screen(){
        this.setLayout(null);
        
        //create deck
        for(int i = 0; i < 4; i++){
            for(int j = 1; j <= 13; j++){
                if(i ==0){
                    deck.add(new Card("club", j));
                }
                if(i == 1){
                    deck.add(new Card("spade", j));
                }
                if(i == 2){
                    deck.add(new Card("heart", j));
                }
                if(i == 3){
                    deck.add(new Card("diamond", j));
                }
            }
        }

        //shuffle deck
        deck = shuffle(deck);


        //add cards to hand
        for(int i = 0; i < 5; i++){
            hand.add(new Pair<Boolean, Card>(holdCard1, deck.get(0)));
            deck.remove(0);
            hand = sort(hand);
        }


        //jbuttons
        hold1 = new JButton();
        hold1.setFont(new Font("Arial", Font.BOLD, 20));
        hold1.setHorizontalAlignment(SwingConstants.CENTER);
        hold1.setBounds(55, 555, 100, 30);
        hold1.setText("Hold");
        this.add(hold1);
        hold1.addActionListener(this);

        hold2 = new JButton();
        hold2.setFont(new Font("Arial", Font.BOLD, 20));
        hold2.setHorizontalAlignment(SwingConstants.CENTER);
        hold2.setBounds(205, 556, 100, 30);
        hold2.setText("Hold");
        this.add(hold2);
        hold2.addActionListener(this);

        hold3 = new JButton();
        hold3.setFont(new Font("Arial", Font.BOLD, 20));
        hold3.setHorizontalAlignment(SwingConstants.CENTER);
        hold3.setBounds(355, 555, 100, 30);
        hold3.setText("Hold");
        this.add(hold3);
        hold3.addActionListener(this);

        hold4 = new JButton();
        hold4.setFont(new Font("Arial", Font.BOLD, 20));
        hold4.setHorizontalAlignment(SwingConstants.CENTER);
        hold4.setBounds(505, 555, 100, 30);
        hold4.setText("Hold");
        this.add(hold4);
        hold4.addActionListener(this);

        hold5 = new JButton();
        hold5.setFont(new Font("Arial", Font.BOLD, 20));
        hold5.setHorizontalAlignment(SwingConstants.CENTER);
        hold5.setBounds(655, 555, 100, 30);
        hold5.setText("Hold");
        this.add(hold5);
        hold5.addActionListener(this);

        draw = new JButton();
        draw.setFont(new Font("Arial", Font.BOLD, 20));
        draw.setHorizontalAlignment(SwingConstants.CENTER);
        draw.setBounds(455, 205, 200, 30);
        draw.setText("DRAW");
        this.add(draw);
        draw.addActionListener(this);
        
        playAgain = new JButton();
        playAgain.setFont(new Font("Arial", Font.BOLD, 20));
        playAgain.setHorizontalAlignment(SwingConstants.CENTER);
        playAgain.setBounds(455, 205, 200, 30);
        playAgain.setText("PLAY AGAIN");
        this.add(playAgain);
        playAgain.addActionListener(this);
    }

    public Dimension getPreferredSize(){
        return new Dimension(800, 600);
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);

        //set background color
        Color darkGreen = new Color(53, 101, 77);
        g.setColor(darkGreen);
        g.fillRect(0, 0, 800, 600);
        
        //show points
        g.setColor(Color.black);
        if(points > 0){
            g.drawString("Points: " + points, 500, 50);
        }
        else{
            g.drawString("Out of points", 500, 50);
        }

        //draw hand
        for(int i = 0; i < hand.size(); i++){
            if(hand.get(i).getKey()){
                g.setColor(Color.yellow);
                g.fillRoundRect(40 + i * 150, 390, 120, 160, 15, 15);
            }
            hand.get(i).getValue().drawMe(g, 50 + i * 150, 400);
        }

        //draw hold buttons
        draw.setVisible(canHold);
        playAgain.setVisible(!canHold);

        //draw instructions

        g.setColor(Color.BLACK);

        if(!endOfRound){
            g.drawString("Select up to 5 cards to keep, then redraw", 100, 100);
        }

        //draw points
        if(endOfRound){
            int xPos = 100;
            int yPos = 100;
            if(drawWinCode == 0){
                g.drawString("Nothing. 0 points", xPos, yPos);
            }
            else if(drawWinCode == 1){
                g.drawString("Royal Flush. 250 points", xPos, yPos);
            }
            else if(drawWinCode == 2){
                g.drawString("Straight Flush. 50 points", xPos, yPos);
            }
            else if(drawWinCode == 3){
                g.drawString("Four of a Kind. 25 points", xPos, yPos);
            }
            else if(drawWinCode == 4){
                g.drawString("Full House. 9 points", xPos, yPos);
            }
            else if(drawWinCode == 5){
                g.drawString("Flush. 6 points", xPos, yPos);
            }
            else if(drawWinCode == 6){
                g.drawString("Straight. 4 points", xPos, yPos);
            }
            else if(drawWinCode == 7){
                g.drawString("Three of a Kind. 3 points", xPos, yPos);
            }
            else if(drawWinCode == 8){
                g.drawString("Two Pair. 2 points", xPos, yPos);
            }
            else if(drawWinCode == 9){
                g.drawString("High Pair. 1 point", xPos, yPos);
            }
        }

        repaint();
    }


    public void actionPerformed(ActionEvent e){
        if(e.getSource() == hold1){
            if(holdCard1){
                holdCard1 = false;
            }
            else{
                holdCard1 = true;
            }
            hand.get(0).updateKey(holdCard1);
        }
        else if(e.getSource() == hold2){
            if(holdCard2){
                holdCard2 = false;
            }
            else{
                holdCard2 = true;
            }
            hand.get(1).updateKey(holdCard2);

        }
        else if(e.getSource() == hold3){
            if(holdCard3){
                holdCard3 = false;
            }
            else{
                holdCard3 = true;
            }
            hand.get(2).updateKey(holdCard3);

        }
        else if(e.getSource() == hold4){
            if(holdCard4){
                holdCard4 = false;
            }
            else{
                holdCard4 = true;
            }
            hand.get(3).updateKey(holdCard4);

        }
        else if(e.getSource() == hold5){
            if(holdCard5){
                holdCard5 = false;
            }
            else{
                holdCard5 = true;
            }
            hand.get(4).updateKey(holdCard5);

        }

        else if(e.getSource() == draw){
            endOfRound = true;

            for(int i = 0; i < 13; i++){
                count(i, hand);
            }
            for(int i = 0; i < hand.size(); i++){
                if(!hand.get(i).getKey()){
                    deck.add(hand.get(i).getValue());
                    hand.set(i, new Pair<Boolean, Card>(false, deck.get(0)));
                    deck.remove(0);
                }
            }
            hand = sort(hand);
            if(royalFlush(hand)){
                drawWinCode = 1;
                points += 250;
            }
            else if(straightFlush(hand)){
                drawWinCode = 2;
                points += 50;
            }
            else if(fourOfAKind(hand)){
                drawWinCode = 3;
                points += 25;
            }
            else if(fullHouse(hand)){
                drawWinCode = 4;
                points += 9;
            }
            else if(flush(hand)){
                drawWinCode = 5;
                points += 6;
            }
            else if(straight(hand)){
                drawWinCode = 6;
                points += 4;
            }
            else if(threeOfAKind(hand)){
                drawWinCode = 7;
                points += 3;
            }
            else if(countPairs(hand) == 2){
                drawWinCode = 8;
                points += 2;
            }
            else if(highPair(hand)){
                drawWinCode = 9;
                points++;
            }
            else{
                drawWinCode = 0;
            }
                
            canHold = false;
        }

        else if(e.getSource() == playAgain){
            if(points > 0){
                endOfRound = false;
                holdCard1 = false;
                holdCard2 = false;
                holdCard3 = false;
                holdCard4 = false;
                holdCard5 = false;
                //clear hand
                for(int i = 0; i < 5; i++){
                    deck.add(hand.get(0).getValue());
                    hand.get(0).updateKey(holdCard1);
                    hand.remove(0);
                }
                

                deck = shuffle(deck);

                //readd cards to hand
                for(int i = 0; i < 5; i++){
                    hand.add(new Pair<Boolean, Card>(holdCard1, deck.get(0)));
                    deck.remove(0);
                }
                hand = sort(hand);

                points--;
            }
            canHold = true;
        }
        repaint();
    }

    //shuffle method
    public DLList<Card> shuffle(DLList<Card> cards){
        for(int i = 0; i < cards.size(); i++){
            int randNum = (int)(Math.random() * 52);
            Card temp = cards.get(randNum);
            cards.set(randNum, cards.get(i));
            cards.set(i, temp);
        }
        return cards;
    }

    //sort hand for display and calculation
    public DLList<Pair<Boolean, Card>> sort(DLList<Pair<Boolean, Card>> cards){
        for(int i = cards.size() - 1; i > 0; i--){
            for(int j = 0; j < i; j++){
                if(cards.get(j).getValue().getNum() > cards.get(j + 1).getValue().getNum()){
                    Pair<Boolean, Card> temp = cards.get(j);
                    cards.set(j, cards.get(j + 1));
                    cards.set(j + 1, temp);
                }
            }
        }
        return cards;
    } 

    //sort deck for debug
    public DLList<Card> sortDeck(DLList<Card> cards){
        for(int i = cards.size() - 1; i > 0; i--){
            for(int j = 0; j < i; j++){
                if(cards.get(j).getNum() > cards.get(j + 1).getNum()){
                    Card temp = cards.get(j);
                    cards.set(j, cards.get(j + 1));
                    cards.set(j + 1, temp);
                }
            }
        }
        return cards;
    } 


    //win conditions
    public Boolean flush(DLList<Pair<Boolean, Card>> cards){
        String suit = cards.get(0).getValue().getSuit();     
        Boolean isFlush = true;
        for(int i = 1; i < 5; i++){
            if(!cards.get(i).getValue().getSuit().equals(suit)){
                isFlush = false;
            }
        }
        return isFlush;
    }

    public Boolean straight(DLList<Pair<Boolean, Card>> cards){
        Boolean isStraight = true;
        for(int i = 1; i < cards.size(); i++){
            if(cards.get(i).getValue().getNum() != cards.get(i - 1).getValue().getNum() + 1){
                if(i == 3 && cards.get(i + 1).getValue().getNum() == 1){
                    isStraight = true;
                }
                else{
                    isStraight = false;
                }
            }
        }

        return isStraight;
    }

    public Boolean straightFlush(DLList<Pair<Boolean, Card>> cards){
        if(straight(cards) && flush(cards)){
            return true;
        }
        return false;
    }

    public Boolean royalFlush(DLList<Pair<Boolean, Card>> cards){
        if(straightFlush(cards) && cards.get(0).getValue().getNum() == 1){
            return true;
        }
        return false;
    }

    public Boolean fourOfAKind(DLList<Pair<Boolean, Card>> cards){
        if((cards.get(0).getValue().getNum() == cards.get(1).getValue().getNum() && cards.get(1).getValue().getNum() == cards.get(2).getValue().getNum() && cards.get(2).getValue().getNum() == cards.get(3).getValue().getNum()) || (cards.get(1).getValue().getNum() == cards.get(2).getValue().getNum() && cards.get(2).getValue().getNum() == cards.get(3).getValue().getNum() && cards.get(3).getValue().getNum() == cards.get(4).getValue().getNum())){
            return true;
        }
        return false;
    }

    public int count(int num, DLList<Pair<Boolean, Card>> cards){
        int count = 0;
        for(int i = 0; i < cards.size(); i++){
            if(cards.get(i).getValue().getNum() == num){
                count++;
            }
        }
        return count;
    }

    public int countPairs(DLList<Pair<Boolean, Card>> cards){
        int count = 0;
        for(int i = 1; i <= 13; i++){
            if(count(i, cards) == 2){
                count++;
            }
        }
        return count;
    }

    public boolean highPair(DLList<Pair<Boolean, Card>> cards){
        
        for(int i = 11; i <= 13; i++){
            if(count(i, cards) == 2){
                return true;
            }
        }
        if(count(1, cards) == 2){
            return true;
        }
        return false;
    }

    public boolean threeOfAKind(DLList<Pair<Boolean, Card>> cards){
        for(int i = 1; i <= 13; i++){
            if(count(i, cards) == 3) {
                return true;
            }
        }
        return false;
    }

    public boolean fullHouse(DLList<Pair<Boolean, Card>> cards){
        Boolean fullHouse = false;
        Boolean hasOnePair = false;
        for(int i = 1; i <= 13; i++){
            if(count(i, cards) == 2){
                hasOnePair = true;
            }
        }
        if(hasOnePair && threeOfAKind(cards)){
            fullHouse = true;
        }
        return fullHouse;
    }


    
}
