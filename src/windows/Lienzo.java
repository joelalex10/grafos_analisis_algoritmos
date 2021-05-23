package windows;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Vector;

import grafos.*;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class Lienzo extends JPanel implements MouseListener, MouseMotionListener {

	private VentanaPrincipal ventanaPrincipal;

	public Point p1, p2;
	
	private Nodo nodoMover;
	public int indexNodo;
	
	
	public Lienzo(VentanaPrincipal ventanaPrincipal) {
		this.setBackground(new Color(66,68,65));
		this.setBounds(0, 0, 400, 300);

		this.ventanaPrincipal = ventanaPrincipal;
		this.addMouseListener(this);
		this.addMouseMotionListener(this);

		
		
	}
	
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		
		for(Nodo nodos : ventanaPrincipal.vectorNodos) {
			nodos.pintar(g);
		}
		
		for(Enlace enlace:ventanaPrincipal.vectorEnlace) {
			enlace.pintar(g);
		}
		
		
		//g.setColor(Color.blue);
		//g.drawLine(0, 0, 400, 300);
		//g.fillOval(10, 10, 25, 25);
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
					this.ventanaPrincipal.vectorNodos.add(nodo);
					ventanaPrincipal.aux = new HashMap<String,Integer>();
					ventanaPrincipal.g.crearVertice(nombre, ventanaPrincipal.aux);
					repaint();
				}else {
					JOptionPane.showMessageDialog(null, "DEBE INGRESAR UN VALOR VALIDO");
				}
			}catch(NullPointerException ex) {	
				
			}
		}
		
		if(e.getButton() == MouseEvent.BUTTON3) {
			
			for(Nodo nodo: ventanaPrincipal.vectorNodos) {
				
				
				String val = "";
				
				if(new Rectangle(nodo.getX() - Nodo.d/2, nodo.getY() - 
						Nodo.d/2, Nodo.d*3, Nodo.d).contains(e.getPoint())) {
					
					if(p1 == null) {
						indexNodo = ventanaPrincipal.vectorNodos.indexOf(nodo);
						nodo.setColor(Color.green);
						repaint();
						p1 = new Point(nodo.getX(), nodo.getY());
					}
					else {	
						Nodo auxiliar = null;
						int indexAuxiliar = 0;
						for (int i=0;i<ventanaPrincipal.vectorNodos.size();i++) {
							if(ventanaPrincipal.vectorNodos.get(i).getX()==p1.getX() &&
									ventanaPrincipal.vectorNodos.get(i).getY()==p1.getY()) {
								auxiliar = ventanaPrincipal.vectorNodos.get(i);
								indexAuxiliar = i;
							}
						}
						
						p2 = new Point(nodo.getX(), nodo.getY());
						nodo.setColor(Color.green);
						repaint();
						
						boolean isConnected = ventanaPrincipal.g.isConnected(nodo.getNombre(), auxiliar.getNombre());
						boolean auxConnected = ventanaPrincipal.g.isConnected(auxiliar.getNombre(), nodo.getNombre());
						boolean isNodoEquals;
						if(auxConnected) {
							
							
							JOptionPane.showMessageDialog(null, "YA EXISTE UNA RELACION ENTRE LOS NODOS");
							auxiliar.setColor(new Color(9,11,48));
							nodo.setColor(new Color(9,11,48));
							repaint();
							p1 = null;
							p2 = null;
							
							return;
						}else {
							
							
							if(auxiliar.getNombre().equals(nodo.getNombre())) {
								try {
									int atributo= Integer.parseInt(JOptionPane.showInputDialog("Ingrese valor para hacer ciclo: "));
									
									Enlace enlace = new Enlace();
									enlace.setX1(p1.x);
									enlace.setY1(p1.y);
									enlace.setX2(p2.x);
									enlace.setY2(p2.y);
									enlace.setAtributo(atributo);
									enlace.setThickness(1);
									enlace.setTextcolor(Color.orange);
									enlace.setTextDirection(20);
									enlace.setNodoInicio(auxiliar);
									enlace.setNodoFin(nodo);
									enlace.setCiclo(true);
									enlace.setNroActividadNodoInicio(auxiliar.getNroActividad());
									enlace.setNroActividadNodoFin(nodo.getNroActividad());
									this.ventanaPrincipal.vectorEnlace.add(enlace);

									ventanaPrincipal.aux = ventanaPrincipal.g.getVertice(auxiliar.getNombre());
									ventanaPrincipal.aux.put(nodo.getNombre(),atributo);
									
								}catch(NumberFormatException numE) {
									JOptionPane.showMessageDialog(null, "Debe ingresar un valor numerico");
									auxiliar.setColor(new Color(9,11,48));
									nodo.setColor(new Color(9,11,48));
									
									repaint();
									p1 = null;
									p2 = null;
								}
								
								
								
							}else {
								try {
									int atributo= Integer.parseInt(JOptionPane.showInputDialog("Ingrese valor: "));
									if(isConnected) {
										
										Enlace enlace = new Enlace();
										enlace.setX1(p1.x);
										enlace.setY1(p1.y);
										enlace.setX2(p2.x);
										enlace.setY2(p2.y);
										enlace.setAtributo(atributo);
										enlace.setThickness(1);
										enlace.setTextcolor(Color.cyan);
										enlace.setTextDirection(20);
										enlace.setNodoInicio(auxiliar);
										enlace.setNodoFin(nodo);
										enlace.setCiclo(false);
										enlace.setNroActividadNodoInicio(auxiliar.getNroActividad());
										enlace.setNroActividadNodoFin(nodo.getNroActividad());
										System.out.println("la actividad es"+ventanaPrincipal.vectorEnlace.size());
										//vectorNodos.get(indexAuxiliar).getEnlaceSalientes().add(enlace);
										//nodo.getEnlaceEntrantes().add(enlace);
										
										this.ventanaPrincipal.vectorEnlace.add(enlace);
										
									}else {
										
										Enlace enlace = new Enlace();
										enlace.setX1(p1.x);
										enlace.setY1(p1.y);
										enlace.setX2(p2.x);
										enlace.setY2(p2.y);
										enlace.setAtributo(atributo);
										enlace.setThickness(1);
										enlace.setTextcolor(Color.green);
										enlace.setTextDirection(-20);
										enlace.setNodoInicio(auxiliar);
										enlace.setNodoFin(nodo);
										enlace.setCiclo(false);
										enlace.setNroActividadNodoInicio(auxiliar.getNroActividad());
										enlace.setNroActividadNodoFin(nodo.getNroActividad());
										System.out.println("EL nro actidad es : "+indexAuxiliar);
										//vectorNodos.get(indexAuxiliar).getEnlaceSalientes().add(enlace);
										//nodo.getEnlaceEntrantes().add(enlace);
										
										/**
										System.out.println(vectorNodos.get(indexAuxiliar).getEnlaceSalientes());
										System.out.println(nodo.getEnlaceEntrantes());*/
										
										this.ventanaPrincipal.vectorEnlace.add(enlace);
									}

									ventanaPrincipal.aux = ventanaPrincipal.g.getVertice(auxiliar.getNombre());
									ventanaPrincipal.aux.put(nodo.getNombre(),atributo);
									
									//System.out.println("inicio: "+auxiliar.getNombre());
									//System.out.println("final: "+nodo.getNombre());
								}catch(NumberFormatException numE) {
									JOptionPane.showMessageDialog(null, "Debe ingresar un valor numerico");
									auxiliar.setColor(new Color(9,11,48));
									nodo.setColor(new Color(9,11,48));
									repaint();
									p1 = null;
									p2 = null;	
								}
							}
							
							
							
							auxiliar.setColor(new Color(9,11,48));
							nodo.setColor(new Color(9,11,48));
							
							repaint();
							p1 = null;
							p2 = null;
							
						}
						
						
						
					}
				}
			}	
		}
		
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		repaint();
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		repaint();
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		int cont =0;
		for(Nodo nodo : ventanaPrincipal.vectorNodos) {
			if(new Rectangle(nodo.getX() - Nodo.d/2, nodo.getY() 
					- Nodo.d/2, Nodo.d*3, Nodo.d ).contains(e.getPoint())) {
				nodoMover = nodo;
				indexNodo = cont;
				break;
			}
			cont ++;
			
		}
		repaint();
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		nodoMover = null;
		indexNodo = -1;
		repaint();
		
	}
	
	public ArrayList<String> getVertices() {
		return ventanaPrincipal.vertices;
		
	}

	public void setVertices(ArrayList<String> vertices) {
		this.ventanaPrincipal.vertices = vertices;
	}
	@Override
	public void mouseDragged(MouseEvent e) {
		if(nodoMover != null) {
			
			Nodo nodo = new Nodo();
			nodo.setX(e.getX());
			nodo.setY(e.getY());
			nodo.setNombre(nodoMover.getNombre());
			nodo.setColor(new Color(9,11,48));
			nodo.setNroActividad(nodoMover.getNroActividad());
			//nodo.setEnlaceEntrantes(nodoMover.getEnlaceEntrantes());
			//nodo.setEnlaceSalientes(nodoMover.getEnlaceSalientes());
			
			
			this.ventanaPrincipal.vectorNodos.set(indexNodo, nodo);
			
			int indexEnlace = 0;
			for(Enlace enlace: ventanaPrincipal.vectorEnlace) {
				if(new Rectangle(enlace.getX1()- Nodo.d/2, enlace.getY1() - Nodo.d/2, Nodo.d*3, Nodo.d).contains(e.getPoint())) {
					
					Enlace enlaceAux = new Enlace();
					enlaceAux.setX1(e.getX());
					enlaceAux.setY1(e.getY());
					enlaceAux.setX2(enlace.getX2());
					enlaceAux.setY2(enlace.getY2());
					enlaceAux.setAtributo(enlace.getAtributo());
					enlaceAux.setThickness(enlace.getThickness());
					enlaceAux.setTextcolor(enlace.getTextcolor());
					enlaceAux.setTextDirection(enlace.getTextDirection());
					enlaceAux.setNodoInicio(enlace.getNodoInicio());
					enlaceAux.setNodoFin(enlace.getNodoFin());
					enlaceAux.setCiclo(enlace.isCiclo());
					enlaceAux.setNroActividadNodoInicio(enlace.getNroActividadNodoInicio());
					enlaceAux.setNroActividadNodoFin(enlace.getNroActividadNodoFin());
					this.ventanaPrincipal.vectorEnlace.set(indexEnlace,enlaceAux);
					//System.out.println("MOVISTE");
				}
				
				else if(new Rectangle(enlace.getX2()- Nodo.d/2, enlace.getY2() - Nodo.d/2, Nodo.d*3, Nodo.d).contains(e.getPoint())) {
					
					Enlace enlaceAux = new Enlace();
					enlaceAux.setX1(enlace.getX1());
					enlaceAux.setY1(enlace.getY1());
					enlaceAux.setX2(e.getX());
					enlaceAux.setY2(e.getY());
					enlaceAux.setAtributo(enlace.getAtributo());
					enlaceAux.setThickness(enlace.getThickness());
					enlaceAux.setTextcolor(enlace.getTextcolor());
					enlaceAux.setTextDirection(enlace.getTextDirection());
					enlaceAux.setNodoInicio(enlace.getNodoInicio());
					enlaceAux.setNodoFin(enlace.getNodoFin());
					enlaceAux.setCiclo(enlace.isCiclo());
					enlaceAux.setNroActividadNodoInicio(enlace.getNroActividadNodoInicio());
					enlaceAux.setNroActividadNodoFin(enlace.getNroActividadNodoFin());
					this.ventanaPrincipal.vectorEnlace.set(indexEnlace, enlaceAux);
					//System.out.println(enlace.getAtributo());
				}
				indexEnlace++;	
			}
		}
		repaint();
		
	}

	@Override
	public void mouseMoved(MouseEvent arg0) {
		repaint();
	}
}
