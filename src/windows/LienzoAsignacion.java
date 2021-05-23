package windows;

import grafos.Enlace;
import grafos.Grafos;
import grafos.Nodo;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Vector;

public class LienzoAsignacion extends JPanel implements MouseListener, MouseMotionListener {

    private int valOrigen;
    private int valDestino;
    private VentanaPrincipal ventanaPrincipal;

    private int cont = 0;

    public Vector<Nodo> vectorNodos;
    public Vector<Enlace>vectorEnlace;
    public Point p1, p2;

    Grafos g;
    ArrayList<String> vertices;
    HashMap<String,Integer> aux;

    @Override
    public void paint(Graphics g) {
        super.paint(g);

        for(Nodo nodos : ventanaPrincipal.vectorNodos) {
            nodos.pintar(g);
        }

        for(Enlace enlace:ventanaPrincipal.vectorEnlace) {
            enlace.pintar(g);
        }
    }


    public LienzoAsignacion(VentanaPrincipal ventanaPrincipal, int valOrigen, int valDestino){
        this.valOrigen = valOrigen;
        this.valDestino = valDestino;
        this.ventanaPrincipal = ventanaPrincipal;
        this.setBackground(new Color(26,68,65));
        this.setBounds(0, 0, 400, 300);
        this.addMouseListener(this);
        this.addMouseMotionListener(this);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if(e.getButton() == MouseEvent.BUTTON1) {
            try {
                String nombre = JOptionPane.showInputDialog("Ingrese nombre a nodo");
                if(nombre.isEmpty()==false) {
                    Nodo nodo = new Nodo();
                    nodo.setX(e.getX());
                    nodo.setY(e.getY());
                    nodo.setNombre(nombre);
                    nodo.setColor(new Color(9,11,48));
                    nodo.setNroActividad(ventanaPrincipal.vectorNodos.size());
                    ventanaPrincipal.vertices.add(nombre);
                    System.out.println("SE HA REINICIADO");
                    this.ventanaPrincipal.vectorNodos.add(nodo);
                    ventanaPrincipal.aux = new HashMap<String,Integer>();
                    ventanaPrincipal.g.crearVertice(nombre, ventanaPrincipal.aux);
                    repaint();
                    cont++;
                    System.out.println(cont);
                }else {
                    JOptionPane.showMessageDialog(null, "DEBE INGRESAR UN VALOR VALIDO");
                }
            }catch(NullPointerException ex) {

            }
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }
}
