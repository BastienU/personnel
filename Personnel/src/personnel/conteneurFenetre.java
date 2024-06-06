package personnel;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class conteneurFenetre extends JPanel{

	private JLabel etiquette;
	
	public conteneurFenetre() {
		
		super();
		this.proprietesConteneur();
	}
	
	private void proprietesConteneur() {
		
		this.setLayout(null);
		this.proprietesEtiquette();
	}
	
	private void proprietesEtiquette() {
		
		etiquette = new JLabel();
		this.setBounds(20, 10, 350, 20);
		etiquette.setText("Texte de l'étiquette.");
		this.add(etiquette);
	}
}
