import javax.swing.*;
import java.awt.event.*;
import java.util.*;
import java.io.*;

public class Flores010Proy2 implements ActionListener {

    JFrame ventana;
    JButton btn_tmp, btn_in, btn_in2, btn_ocultar, btn_consultar;
    JLabel lbl_valor, lbl_x, lbl_y, lbl_tiempo, lbl_movimientos;
    JTextField tf_valor, tf_x, tf_y;
    JTextArea ta_mejoresJugadores;  // JTextArea para mostrar los mejores jugadores
    JButton[] botones;  // Vector para almacenar los botones
    JButton espacioVacio;  // Botón para el espacio vacío
    javax.swing.Timer timer;  // Timer para contar el tiempo
    int segundosTranscurridos;  // Contador de segundos
    int contadorMovimientos;  // Contador de movimientos
    final String FILENAME = "mejores_jugadores.txt";  // Archivo para almacenar los mejores jugadores

    public static void main(String[] args) {
        new Flores010Proy2();
    }

    Flores010Proy2() {
        inicializarInterfaz();
        inicializarMejoresJugadores();
    }

    // Método para inicializar la interfaz gráfica
    private void inicializarInterfaz() {
        ventana = new JFrame("Rompecabeza");
        ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ventana.setExtendedState(JFrame.MAXIMIZED_BOTH);
        ventana.setLayout(null);

        inicializarEtiquetas();
        inicializarCamposDeTexto();
        inicializarBotones();
        inicializarEtiquetasDePresentacion();
        inicializarTimer();

        ventana.setVisible(true);
    }

    // Método para inicializar las etiquetas
    private void inicializarEtiquetas() {
        lbl_valor = new JLabel("Botones");
        lbl_valor.setBounds(50, 25, 100, 20);
        ventana.add(lbl_valor);

        lbl_x = new JLabel("X:");
        lbl_x.setBounds(160, 25, 20, 20);
        ventana.add(lbl_x);

        lbl_y = new JLabel("Y:");
        lbl_y.setBounds(160, 50, 20, 20);
        ventana.add(lbl_y);

        lbl_tiempo = new JLabel("Tiempo: 0s");
        lbl_tiempo.setBounds(50, 75, 100, 20);
        ventana.add(lbl_tiempo);

        lbl_movimientos = new JLabel("Movimientos: 0");
        lbl_movimientos.setBounds(50, 100, 150, 20);
        ventana.add(lbl_movimientos);
    }

    // Método para inicializar los campos de texto
    private void inicializarCamposDeTexto() {
        tf_valor = new JTextField();
        tf_valor.setBounds(105, 25, 50, 20);
        ventana.add(tf_valor);

        tf_x = new JTextField();
        tf_x.setBounds(185, 25, 50, 20);
        ventana.add(tf_x);

        tf_y = new JTextField();
        tf_y.setBounds(185, 50, 50, 20);
        ventana.add(tf_y);

        ta_mejoresJugadores = new JTextArea();  // Crear JTextArea para mostrar los mejores jugadores
        ta_mejoresJugadores.setBounds(700, 455, 300, 100);
        ta_mejoresJugadores.setEditable(false);
        ventana.add(ta_mejoresJugadores);
    }

    // Método para inicializar los botones
    private void inicializarBotones() {
        botones = new JButton[15];  // Crear el vector de botones

        btn_in = new JButton("Iniciar");
        btn_in.setBounds(100, 140, 70, 20);
        btn_in.addActionListener(this);
        ventana.add(btn_in);

        btn_in2 = new JButton("Iniciar2");
        btn_in2.setBounds(200, 140, 80, 20);
        btn_in2.addActionListener(this);
        ventana.add(btn_in2);

        btn_ocultar = new JButton("Ocultar");
        btn_ocultar.setBounds(300, 140, 80, 20);
        btn_ocultar.addActionListener(this);
        ventana.add(btn_ocultar);

        btn_consultar = new JButton("Consultar Mejores");
        btn_consultar.setBounds(400, 140, 150, 20);
        btn_consultar.addActionListener(this);
        ventana.add(btn_consultar);

        for (int i = 0; i < 15; i++) {
            botones[i] = new JButton(String.valueOf(i + 1));
            botones[i].setBounds(100 + 100 * (i % 4), 300 + 100 * (i / 4), 100, 100);
            botones[i].addActionListener(this);
            ventana.add(botones[i]);
        }

        // Inicializar el botón del espacio vacío
        espacioVacio = new JButton();
        espacioVacio.setBounds(100 + 100 * (15 % 4), 300 + 100 * (15 / 4), 100, 100);
        espacioVacio.setVisible(true);  // Inicialmente visible
        ventana.add(espacioVacio);
    }

    // Método para inicializar las etiquetas de presentación
    private void inicializarEtiquetasDePresentacion() {
        String[] textos = {
            "Proyecto Rompecabeza"
        };
        int yPosicion = 180;
        for (String texto : textos) {
            JLabel lbl = new JLabel(texto);
            lbl.setBounds(895, yPosicion, 400, 20);
            ventana.add(lbl);
            yPosicion += 25;
        }
    }

    // Método para inicializar el temporizador
    private void inicializarTimer() {
        segundosTranscurridos = 0;
        contadorMovimientos = 0;
        timer = new javax.swing.Timer(1000, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                segundosTranscurridos++;
                lbl_tiempo.setText("Tiempo: " + segundosTranscurridos + "s");
            }
        });
    }

    // Método para manejar los eventos de acción de los botones
    public void actionPerformed(ActionEvent e) 
    {
        if (e.getSource() == btn_in) 
        {
            timer.stop();  // Iniciar el temporizador
            segundosTranscurridos = 0;
            contadorMovimientos = 0;  // Reiniciar el contador de movimientos
            lbl_movimientos.setText("Movimientos: 0");
            timer.start();
            revolverBotones();
        } else if (e.getSource() == btn_in2) 
        {
            revolverBotonesAleatorios();
            ta_mejoresJugadores.setText("Los números se desordenaron aleatoriamente.");
        } else if (e.getSource() == btn_ocultar) 
        {
            espacioVacio.setVisible(!espacioVacio.isVisible());
            if (espacioVacio.isVisible()) {
                btn_ocultar.setText("Ocultar");
            } else {
                btn_ocultar.setText("Mostrar");
            }
        } else if (e.getSource() == btn_consultar) {
            consultarMejoresJugadores();
        } else if (e.getSource() == espacioVacio) {
            // No hacer nada cuando se presiona el espacio vacío
        } else 
        {
            btn_tmp = (JButton) e.getSource();
            int btnX = btn_tmp.getLocation().x;
            int btnY = btn_tmp.getLocation().y;

            // Verificar si el botón clicado está al lado del espacio vacío
            if (esAdyacenteEspacioVacio(btnX, btnY)) {
                moverBoton(btn_tmp, btnX, btnY);
                contadorMovimientos++;
                lbl_movimientos.setText("Movimientos: " + contadorMovimientos);
            }

            tf_valor.setText(btn_tmp.getText());
            tf_x.setText(String.valueOf(btn_tmp.getLocation().x));
            tf_y.setText(String.valueOf(btn_tmp.getLocation().y));
        }
    }

    // Método para verificar si el botón es adyacente al espacio vacío
    private boolean esAdyacenteEspacioVacio(int x, int y) {
        return (x == espacioVacio.getLocation().x && (y == espacioVacio.getLocation().y - 100 || y == espacioVacio.getLocation().y + 100)) ||
               (y == espacioVacio.getLocation().y && (x == espacioVacio.getLocation().x - 100 || x == espacioVacio.getLocation().x + 100));
    }

    // Método para mover el botón al espacio vacío
    private void moverBoton(JButton boton, int x, int y) {
        int targetX = espacioVacio.getLocation().x;
        int targetY = espacioVacio.getLocation().y;
        int stepX = (targetX - x) / 20;
        int stepY = (targetY - y) / 20;
        javax.swing.Timer timer = new javax.swing.Timer(5, new ActionListener() {
            int count = 0;

            public void actionPerformed(ActionEvent e) {
                if (count < 20) {
                    boton.setLocation(boton.getLocation().x + stepX, boton.getLocation().y + stepY);
                    count++;
                } else {
                    boton.setLocation(targetX, targetY);
                    espacioVacio.setLocation(x, y);
                    ((javax.swing.Timer) e.getSource()).stop();
                }
            }
        });
        timer.start();
    }

    // Método para revolver los botones, manteniendo el espacio vacío al final
    private void revolverBotones() {
        Random random = new Random();
        int[] posiciones = new int[16]; // Incluye un espacio vacío

        // Inicializar posiciones
        for (int i = 0; i < 15; i++) {
            posiciones[i] = i;
        }
        posiciones[15] = -1; // Espacio vacío

        // Barajar posiciones
        for (int i = 14; i >= 0; i--) {
            int j = random.nextInt(i + 1);
            int temp = posiciones[i];
            posiciones[i] = posiciones[j];
            posiciones[j] = temp;
        }

        // Asignar nuevas posiciones a los botones
        for (int i = 0; i < 15; i++) {
            int pos = posiciones[i];
            botones[i].setBounds(100 + 100 * (pos % 4), 300 + 100 * (pos / 4), 100, 100);
        }

        // Actualizar la posición del espacio vacío
        espacioVacio.setLocation(100 + 100 * (posiciones[16] % 4), 300 + 100 * (posiciones[16] / 4));
        espacioVacio.setVisible(true);
    }

    // Método para revolver los botones y colocar el espacio vacío en una posición aleatoria
    private void revolverBotonesAleatorios() {
        Random random = new Random();
        int[] posiciones = new int[16]; // Incluye un espacio vacío

        // Inicializar posiciones
        for (int i = 0; i < 15; i++) {
            posiciones[i] = i;
        }
        posiciones[15] = 15; // Espacio vacío

        // Barajar posiciones
        for (int i = 15; i > 0; i--) {
            int j = random.nextInt(i + 1);
            int temp = posiciones[i];
            posiciones[i] = posiciones[j];
            posiciones[j] = temp;
        }

        // Asignar nuevas posiciones a los botones
        for (int i = 0; i < 15; i++) {
            int pos = posiciones[i];
            botones[i].setBounds(100 + 100 * (pos % 4), 300 + 100 * (pos / 4), 100, 100);
        }

        // Colocar el espacio vacío en una posición aleatoria
        espacioVacio.setLocation(100 + 100 * (posiciones[15] % 4), 300 + 100 * (posiciones[15] / 4));
        espacioVacio.setVisible(true);
    }

    // Método para ordenar los botones en su posición inicial
    private void ordenarBotones() {
        for (int i = 0; i < 15; i++) {
            botones[i].setLocation(100 + 100 * (i % 4), 300 + 100 * (i / 4));
        }
        espacioVacio.setLocation(100 + 100 * (15 % 4), 300 + 100 * (15 / 4));
        espacioVacio.setVisible(true);
    }

    // Método para inicializar los mejores jugadores con datos predeterminados
    private void inicializarMejoresJugadores() {
        try {
            FileWriter fw = new FileWriter(FILENAME);
            fw.write("Rolando 30 10\n");
            fw.write("Gilberto 45 12\n");
            fw.write("Kevin 50 15\n");
            fw.write("Alma 55 20\n");
            fw.write("Juan 60 25\n");
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Método para consultar los mejores jugadores y mostrarlos en el JTextArea
    private void consultarMejoresJugadores() {
        try {
            FileReader fr = new FileReader(FILENAME);
            BufferedReader br = new BufferedReader(fr);
            StringBuilder sb = new StringBuilder();
            String linea;
            while ((linea = br.readLine()) != null) {
                sb.append(linea).append("\n");
            }
            ta_mejoresJugadores.setText(sb.toString());
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Método para verificar si el jugador actual está entre los mejores
    private void verificarMejoresJugadores(String nombre, int tiempo, int movimientos) {
        try {
            FileReader fr = new FileReader(FILENAME);
            BufferedReader br = new BufferedReader(fr);
            List<String> jugadores = new ArrayList<>();
            String linea;
            while ((linea = br.readLine()) != null) {
                jugadores.add(linea);
            }
            br.close();

            jugadores.add(nombre + " " + tiempo + " " + movimientos);
            jugadores.sort(Comparator.comparingInt(a -> Integer.parseInt(a.split(" ")[1])));

            if (jugadores.size() > 5) {
                jugadores = jugadores.subList(0, 5);
            }

            FileWriter fw = new FileWriter(FILENAME);
            for (String jugador : jugadores) {
                fw.write(jugador + "\n");
            }
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}




