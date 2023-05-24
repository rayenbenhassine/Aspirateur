import java.awt.*;
import javax.swing.*;

public class Grille extends JPanel
{
	private static final int nbPixelCoteCase = 100;

	private int nbCasesL, nbCasesH;
	private Aspirateur[][] m;
	private Aspirateur aspirateur;


	Grille(int nbCasesL, int nbCasesH) {
		this.nbCasesL = nbCasesL;
		this.nbCasesH = nbCasesH;
		this.m = new Aspirateur[nbCasesL][nbCasesH];
		Dimension preferredSize = new Dimension(nbCasesL * nbPixelCoteCase, nbCasesH * nbPixelCoteCase);
		setPreferredSize(preferredSize);
	}

	public void redessine(){
		repaint();
	}

	public void addAspirateur(Aspirateur aspirateur) throws SortieDeLaGrilleException {
		if(aspirateur.getX() > this.nbCasesL || aspirateur.getX() < 0 || aspirateur.getY() < 0 || aspirateur.getY() > this.nbCasesH){
			throw new SortieDeLaGrilleException();
		}
		this.m[aspirateur.getX()][aspirateur.getY()] = aspirateur;
		this.aspirateur = this.m[aspirateur.getX()][aspirateur.getY()];
		redessine();
	}

	public void removeAspirateur(int x, int y){
		this.m[x][y] = null;
		redessine();
	}

	public Aspirateur getAspirateur() {
		return aspirateur;
	}



	@Override
	//Fonction d'affichage de la grille appelée par repaint
	protected void paintComponent(Graphics g)
	{
	 	super.paintComponent(g);
		int i,j;
		for (i=0;i<nbCasesL;i++) {
			for (j = 0; j < nbCasesH; j++) {
				int cellX = 10 + (i * nbPixelCoteCase);
				int cellY = 10 + (j * nbPixelCoteCase);
				g.setColor(Color.WHITE);
				g.fillRect(cellX, cellY, nbPixelCoteCase, nbPixelCoteCase);
				if(m[i][j] != null){
					g.drawImage(this.aspirateur.getImage(),cellX+30, cellY+30, 40, 40, null);
				}
			}
		}

		// Redessine la grille
		g.setColor(Color.BLACK);
		g.drawRect(10, 10, nbCasesL*nbPixelCoteCase, nbCasesH*nbPixelCoteCase);

		for (i = 10; i <= nbCasesL*nbPixelCoteCase; i += nbPixelCoteCase) {
			g.drawLine(i, 10, i, nbCasesH*nbPixelCoteCase+10);
		}

		for (i = 10; i <= nbCasesH*nbPixelCoteCase; i += nbPixelCoteCase) {
			g.drawLine(10, i, nbCasesL*nbPixelCoteCase+10, i);
		}
	}
}
