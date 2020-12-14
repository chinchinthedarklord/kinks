import java.awt.*;
import javax.swing.*;
import java.awt.geom.*;
import java.util.*;
import java.io.*;

public class TextFromFile extends JPanel implements Runnable{
    private int x_position;
    private int y_position;
    private int x_resolution;
    private int y_resolution;
    private String filename;
    private String text;
    public double value;

    public TextFromFile(int x_position, int y_position, int x_resolution, int y_resolution, String filename, String text){
        this.x_resolution = x_resolution;
        this.y_resolution = y_resolution;
        this.x_position = x_position;
        this.y_position = y_position;
        this.filename = filename;
        this.text = text;

        try{
            Scanner scanner = new Scanner(new File(filename), "UTF-8");
            scanner.useLocale(Locale.US);
            value = scanner.nextDouble();
        } catch(IOException e) {
            System.out.println(e.getMessage());
        }
        (new Thread(this)).start();
    }

    public void paint(Graphics g){
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        Paint paint = g2.getPaint();

        ((Graphics2D)g).setComposite(AlphaComposite.getInstance(AlphaComposite.SRC));

        g.drawString(this.text + this.value, this.x_position, this.y_position);
    }
    public void run(){ //функция, которая обновляет экран, чтобы все двигалось
        while(true) {
            try {
                super.repaint();
                Thread.sleep(1); //ждем
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
