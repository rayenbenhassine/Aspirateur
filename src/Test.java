import javax.swing.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;

public class Test {

	public static void main(String[] args) {
		int nbCasesL = 10, nbCasesH = 10, positionInitialX = 5, positionInitialY = 5; //Vous pouvez changer ces parametres
		Orientation orientationInitial = Orientation.E; //vous pouvez essayer une autre orientation


		JFrame window = new JFrame(); //création d'une fenetre graphique
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		Grille grille = new Grille(nbCasesL, nbCasesH); //création d'une grille
		try{
			//ajout d'un aspirateur dans la grille
			grille.addAspirateur(new Aspirateur(positionInitialX - 1, positionInitialY - 1, orientationInitial, grille));
		}catch (SortieDeLaGrilleException e){
			e.showDialog(window);
		}

		JPanel commandesPanel = new JPanel();
		commandesPanel.setLayout(new BoxLayout(commandesPanel, BoxLayout.X_AXIS));
		commandesPanel.setSize(window.getContentPane().getWidth(), 50);
		commandesPanel.add(new JLabel("Choisir les commandes : "));
		//ajout des bouttons pour le choix des commandes
		JButton[] buttons = new JButton[]{
				new JButton("A"),
				new JButton("D"),
				new JButton("G"),
		};

		JPanel labelPanel = new JPanel();
		labelPanel.setLayout(new BoxLayout(labelPanel, BoxLayout.X_AXIS));
		JLabel text = new JLabel("Commandes choisis : ");
		labelPanel.add(text);

		JLabel directions = new JLabel();
		labelPanel.add(directions);
		ArrayList<Character> commandes = new ArrayList<>();
		for (JButton button : buttons) {
			button.addActionListener(new AbstractAction() {
				@Override
				public void actionPerformed(ActionEvent e) {
					directions.setText(directions.getText() + ((JButton) e.getSource()).getText() + " ");
					commandes.add(((JButton) e.getSource()).getText().charAt(0));
				}
			});
			commandesPanel.add(button);
		}

		JLabel postionLabel = new JLabel("x = " + (grille.getAspirateur().getX() + 1) + " y = " + (grille.getAspirateur().getY() + 1));

		JButton deplacerButton = new JButton("Déplacer");
		deplacerButton.addActionListener(e -> {
			deplacerButton.setEnabled(false);
			SwingWorker<Void, Void> worker = new SwingWorker<>() {
				@Override
				protected Void doInBackground() throws SortieDeLaGrilleException {
					int initialX = grille.getAspirateur().getX();
					int initialY = grille.getAspirateur().getY();
					Orientation initialOrientation = grille.getAspirateur().getOrientation();
					try{
						grille.getAspirateur().deplacer(commandes);
						postionLabel.setText("x = " + (grille.getAspirateur().getX() + 1) + " y = " + (grille.getAspirateur().getY() + 1));
					}catch (SortieDeLaGrilleException e){
						e.showDialog(window);
						grille.addAspirateur(new Aspirateur(initialX, initialY, initialOrientation, grille));
					}
					return null;
				}

				@Override
				protected void done() {
					deplacerButton.setEnabled(true);
					commandes.clear();
					directions.setText("");
				}
			};

			worker.execute();
		});


		JScrollPane scrollPane = new JScrollPane(grille);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

		JPanel contentPane = new JPanel();
		contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));
		contentPane.add(commandesPanel);
		contentPane.add(labelPanel);
		contentPane.add(deplacerButton);
		contentPane.add(postionLabel);
		contentPane.add(scrollPane);

		window.setContentPane(contentPane);
		window.setSize(700, 700);
		window.setVisible(true);
	}
}
