import javax.swing.*;

public class SortieDeLaGrilleException extends Exception{
    public SortieDeLaGrilleException() {
        super("L'aspirateur est sorti de la grille !\nVeuillez choisir une autre séquence");
    }
    public void showDialog(JFrame window){
        JOptionPane.showMessageDialog(window, this.getMessage());
    }
}
