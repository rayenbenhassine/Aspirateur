import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

class Aspirateur {
    private Image image;
    private int x;
    private int y;
    private Orientation orientation;
    private Grille grille;

    public Aspirateur(int x, int y, Orientation orientation, Grille grille){
        this.x = x;
        this.y = y;
        this.grille = grille;
        setOrientation(orientation);
    }


    public Orientation getOrientation() {
        return orientation;
    }


    public Image getImage() {
        return image;
    }

    private void setOrientation(Orientation o){
        switch (o) {
            case N -> {
                this.image = new ImageIcon("assets/aspirateurN.png").getImage();
                this.orientation = Orientation.N;
            }
            case E -> {
                this.image = new ImageIcon("assets/aspirateurE.png").getImage();
                this.orientation = Orientation.E;
            }
            case S -> {
                this.image = new ImageIcon("assets/aspirateurS.png").getImage();
                this.orientation = Orientation.S;
            }
            default -> {
                this.image = new ImageIcon("assets/aspirateurW.png").getImage();
                this.orientation = Orientation.W;
            }
        }
    }

    public void pivoterDroite(){
        if(this.orientation == Orientation.N)
            setOrientation(Orientation.E);
        else if(this.orientation == Orientation.E)
            setOrientation(Orientation.S);
        else if(this.orientation == Orientation.S)
            setOrientation(Orientation.W);
        else
            setOrientation(Orientation.N);
    }

    public void pivoterGauche(){
        if(this.orientation == Orientation.N)
            setOrientation(Orientation.W);
        else if(this.orientation == Orientation.W)
            setOrientation(Orientation.S);
        else if(this.orientation == Orientation.S)
            setOrientation(Orientation.E);
        else
            setOrientation(Orientation.N);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    private void avancer(){
        switch (this.orientation) {
            case E -> {
                this.x += 1;
            }
            case W -> {
                this.x -= 1;
            }
            case S -> {
                this.y += 1;
            }
            default -> {
                this.y -= 1;
            }
        }

    }

    public void deplacer(ArrayList<Character> commandes) throws SortieDeLaGrilleException{
        for (Character commande : commandes){
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ex){
                ex.printStackTrace();
            }
            this.grille.removeAspirateur(x, y);

            switch (commande) {
                case 'A' -> {
                    avancer();
                }
                case 'D' -> {
                    pivoterDroite();
                }
                case 'G' -> {
                    pivoterGauche();
                }
            }
            this.grille.addAspirateur(this);

        }

    }
}
