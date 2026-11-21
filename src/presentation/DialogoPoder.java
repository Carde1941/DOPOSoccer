package presentation;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

/** Ventanita que explica el tiro de poder de un personaje. */
public class DialogoPoder extends JDialog {

    public DialogoPoder(Component origen, Pais pais, Color color) {
        super(SwingUtilities.getWindowAncestor(origen), pais.getPoder(), Dialog.ModalityType.APPLICATION_MODAL);
        setUndecorated(true);
        setBackground(new Color(0, 0, 0, 0));

        PanelNeon panel = new PanelNeon(color);
        panel.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.insets = new Insets(3, 8, 3, 8);

        panel.add(texto(pais.getPoder().toUpperCase(), Estilo.BOTON.deriveFont(24f), Estilo.AMARILLO), c);
        panel.add(texto(pais.toString(), Estilo.NORMAL, Estilo.TEXTO), c);
        agregar(panel, c, "Cómo sale el tiro", pais.getTiro());
        agregar(panel, c, "Efecto si golpea al rival", pais.getEfecto());
        agregar(panel, c, "Duración", pais.getDuracion() + " segundos");
        agregar(panel, c, "Daño", pais.getDano() + " × multiplicador de Kick");

        BotonJuego cerrar = new BotonJuego("Entendido", color);
        cerrar.setFont(Estilo.BOTON.deriveFont(14f));
        cerrar.setPreferredSize(new Dimension(150, 36));
        cerrar.addActionListener(e -> dispose());
        c.fill = GridBagConstraints.NONE;
        c.insets = new Insets(10, 8, 2, 8);
        panel.add(cerrar, c);

        getContentPane().add(panel, BorderLayout.CENTER);
        pack();
        setLocationRelativeTo(origen);
    }

    private void agregar(PanelNeon panel, GridBagConstraints c, String titulo, String valor) {
        panel.add(texto(titulo.toUpperCase(), Estilo.NORMAL.deriveFont(12f), Estilo.CIAN), c);
        String html = "<html><div style='width:260px;text-align:center'>" + valor + "</div></html>";
        panel.add(texto(html, Estilo.NORMAL.deriveFont(14f), Estilo.TEXTO), c);
    }

    private static JLabel texto(String contenido, Font fuente, Color color) {
        JLabel l = new JLabel(contenido, SwingConstants.CENTER);
        l.setFont(fuente);
        l.setForeground(color);
        return l;
    }
}