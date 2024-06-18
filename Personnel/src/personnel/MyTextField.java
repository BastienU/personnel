package personnel;

import java.awt.*;
import java.awt.event.*;
import java.time.LocalDate;

public class MyTextField extends TextField{
    int limit;
    public MyTextField() {
    }
    
    // Méthode permettant de limiter le nombre de caractères pouvant être saisis dans une zone de texte.
    public MyTextField(int show,int limit) {
        super(show);
        this.limit = limit;
        addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent evt) {
               if (getText().length() >= MyTextField.this.limit)
                    evt.consume();
            }
       });
    }

	public void setText(LocalDate getdateArrivee) {
		// TODO Auto-generated method stub
		
	}
}
