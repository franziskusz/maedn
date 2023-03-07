package main.gui;

//Import's
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.geom.RoundRectangle2D;

import javax.swing.JButton;

import main.model.enums.PlayerColor;

/**
 * Sorgt dafür dass der Button abgerundete Ecken hat und gerendert wird
 * @Credits JavaForum Robat und individuelle Anpassungen
 * https://www.java-forum.org/thema/runde-buttons-erstellen.182494/
 */
public class RoundButton extends JButton {

    private Shape shape;
    private int arc;
    private boolean isQuadratic;
    private static PlayerColor color = PlayerColor.RED;

    public RoundButton(String text, int arc) {
        this(text, arc, true);
    }

    public RoundButton(String text, int arc, boolean isQuadratic) {
        super(text);
        this.arc = arc;
        this.isQuadratic= isQuadratic;
        

        initComponent();
    }

    private void initComponent() {
        if(isQuadratic) {
            final Dimension size = getPreferredSize();
            size.width = size.height = Math.max(size.width, size.height);
            setPreferredSize(size);
        }
        setContentAreaFilled(false);
        setFocusable(false);
    }
    
    @Override
    protected void paintBorder(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);

        g2d.setColor(getBackground().darker());
        g2d.draw(new RoundRectangle2D.Double(0, 0, getWidth()-1, getHeight()-1, arc, arc));
    }

    @Override
    public boolean contains( int x, int y ) {
        if(shape == null || !shape.getBounds().equals(getBounds())) {
            this.shape = new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), arc, arc);
        }
        return shape.contains(x, y);
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);       
        
        //verändert den Hntergrund des Buttons beim klicken abhängig vom Spieler, der gerade dran ist
        switch(color){
        
        case RED:
        	g2d.setColor(getModel().isArmed() ? new Color (255,0,0,255): getBackground());
        	break;
        case BLUE:
        	g2d.setColor(getModel().isArmed() ? new Color (30,144,255,255): getBackground());
        	break;
        case GREEN:
        	g2d.setColor(getModel().isArmed() ? new Color (0,255,0,225): getBackground());
        	break;
        case YELLOW:
        	g2d.setColor(getModel().isArmed() ? new Color (255,255,0,225): getBackground());
        	break;
        	
        }
        g2d.fill(new RoundRectangle2D.Double(0, 0, getWidth()-1, getHeight()-1, arc, arc));
        
        
        
        super.paintComponent(g);
    }

   
    /*
     * setzt die Farbe des Buttons
     */
    public static void setColor (PlayerColor color) {
    	RoundButton.color=color;
    }
}

