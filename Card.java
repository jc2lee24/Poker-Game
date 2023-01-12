import java.awt.*;
import java.awt.image.*;
import javax.imageio.*;
import java.io.*;

public class Card{

    public String suit;
    public int number;
    public String displayNumber;

    private BufferedImage heart;
    private BufferedImage diamond;
    private BufferedImage club;
    private BufferedImage spade;

    private Image scaledHeart;
    private Image scaledDiamond;
    private Image scaledClub;
    private Image scaledSpade;


    public Card(String suit, int number){
        this.suit = suit;
        this.number = number;

        try {
            heart = ImageIO.read(new File("heart.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            club = ImageIO.read(new File("club.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            diamond = ImageIO.read(new File("diamond.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            spade = ImageIO.read(new File("spade.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        scaledHeart = heart.getScaledInstance(10, 10, 50);
        scaledClub = club.getScaledInstance(15, 10, 50);
        scaledDiamond = diamond.getScaledInstance(15, 10, 50);
        scaledSpade = spade.getScaledInstance(10, 10, 50);


    }

    public String getDisplay(){
        if(number > 1 && number <= 10){
            return number + "";
        }
        else if(number == 1){
                return "A";
        }
        else if(number == 11){
            return "J";
        }
        else if(number == 12){
            return "Q";
        }
        return "K";
    }

    public String getSuit(){
        return suit;
    }

    public int getNum(){
        return number;
    }

    public void drawMe(Graphics g, int x, int y){
        g.setColor(Color.white);
        g.fillRoundRect(x, y, 100, 140, 10, 10);
        int offsetX = 45;
        int offsetY = 60;
        if(suit.equals("spade") || suit.equals("club")){
            g.setColor(Color.black);
            if(suit.equals("spade")){
                g.drawImage(scaledSpade, x + offsetX,  y + offsetY, null);
            }
            else if(suit.equals("club")){
                g.drawImage(scaledClub, x + offsetX, y + offsetY, null);
            }
        }
        else{
            g.setColor(Color.red);
            if(suit.equals("heart")){
                g.drawImage(scaledHeart, x + offsetX,  y + offsetY, null);
            }
            else if(suit.equals("diamond")){
                g.drawImage(scaledDiamond, x + offsetX, y + offsetY, null);
            }
        }
        g.drawString(getDisplay(), x + 5, y + 15);
        g.drawString(getDisplay(), x + 85, y + 130);

        
    }

    @Override
    public String toString(){
        return "[" + suit + " " + getDisplay() + "]";
    }
}