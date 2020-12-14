import java.awt.*;
import javax.swing.*;
import java.awt.geom.*;
import java.util.*;
import java.io.*;

public class Graph extends JPanel implements Runnable{ //для движущегося кинка
    private double dt; //задержка по времени

    private int grid = 5; //длина засечки на графике
    private int x_resolution; //разрешение панельки
    private int y_resolution; //разрешение панельки
    private int x_scale; // масштабы по осям
    private int y_scale; // масштабы по осям
    private int x_margin; // отступ от края
    private int y_margin; // отступ от края
    private int x0; //смещение засечек графика
    private int y0; //смещение засечек графика

    private String x_name; //названия для осей
    private String y_name; //названия для осей

    String filename; //имя файла, из которого читаем график
    ArrayList<Double> x = new ArrayList<Double>(); // координаты по x
    ArrayList<Double> y = new ArrayList<Double>(); // координаты по y

    public Graph(String filename, int x_resolution, int y_resolution, int x_scale, int y_scale, int x_margin, int y_margin, String x_name, String y_name, double dt, int x0, int y0){
        //чтение файла с точками для графика
        //сразу нужный масштаб и координаты на панели
        this.x_resolution = x_resolution;
        this.y_resolution = y_resolution;
        this.x_scale = x_scale;
        this.y_scale = y_scale;
        this.x_margin = x_margin;
        this.y_margin = y_margin;
        this.x_name = x_name;
        this.y_name = y_name;
        this.dt = dt;
        this.x0 = x0;
        this.y0 = y0;

        try{
            Scanner scanner = new Scanner(new File(filename), "UTF-8");
            scanner.useLocale(Locale.US);
            while (scanner.hasNextLine()) {
                x.add((Double) (this.x_scale * (scanner.nextDouble() + this.x0)  + this.x_margin));
                y.add((Double) (this.y_resolution - this.y_scale * (scanner.nextDouble() + this.y0) - this.y_margin) );
            }
        } catch(IOException e) {
            System.out.println(e.getMessage());
        }

        (new Thread(this)).start();
    }

    public void paint(Graphics g){
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        Paint paint = g2.getPaint();

        //команда для обновления экрана (движущийся кинк)
        ((Graphics2D)g).setComposite(AlphaComposite.getInstance(AlphaComposite.SRC));

        //рисование осей
        g2.draw(new Line2D.Double(this.x_margin, this.y_resolution - this.y_margin, this.x_resolution - this.x_margin, this.y_resolution - this.y_margin)); // ось x
        g2.draw(new Line2D.Double(this.x_margin, this.y_margin, this.x_margin, this.y_resolution - this.y_margin)); // ось y

        //рисование кривой на панели (без осей)
        for(int i = 0; i < x.size() - 1; i++) {
            if ((x.get(i + 1) < this.x_resolution - this.x_margin) && (x.get(i + 1) > this.x_margin) &&(y.get(i + 1) < this.y_resolution - this.y_margin) && (y.get(i + 1) > this.y_margin)) {
                //рисуем, только если мы внутри нашего экрана
                g2.draw(new Line2D.Double(x.get(i), y.get(i), x.get(i + 1), y.get(i + 1)));
            }
        }

        //названия осей
        Font currentFont = g.getFont();
        Font newFont = currentFont.deriveFont(currentFont.getSize() * 0.8F);
        g.setFont(newFont);

        //g.drawString(this.x_name, (int) (this.x_resolution / 2), this.y_resolution - this.y_margin / 2);
        g.drawString(this.y_name, this.x_margin, (int) (this.y_margin / 2));

        //засечки на осях
        int k = 0;
        while( k * this.x_scale <= this.x_resolution - 2 * this.x_margin){
            g2.draw(new Line2D.Double(this.x_margin + k * this.x_scale, this.y_resolution - this.y_margin, this.x_margin + k * this.x_scale, this.y_resolution - this.y_margin - this.grid));
            g.drawString(k - this.x0 +"", this.x_margin + k * this.x_scale, this.y_resolution - this.y_margin / 2);
            k++;
        }
        k = 0;
        while( k * this.y_scale <= this.y_resolution - 2 * this.y_margin){
            g2.draw(new Line2D.Double(this.x_margin, this.y_resolution - this.y_margin - k * this.y_scale, this.x_margin + this.grid, this.y_resolution - this.y_margin - k * this.y_scale));
            g.drawString(k - this.y0 +"",0 , this.y_resolution - this.y_margin - k * this.y_scale);
            k++;
        }
    }

    public void move(double dt){ //функция, которая двигает график
        double c = 1.;
        if(x.get(0) > this.x_resolution - this.x_margin){
            double delta_x = x.get(x.size() - 1) - this.x_margin;
            x.set(x.size() - 1, (double) this.x_margin);
            for (int i = 0; i < x.size(); i++) {
                x.set(i, x.get(i) - delta_x);
            }
        }
        try{
            Scanner scanner_velocity = new Scanner(new File("resources/velocity.dat"), "UTF-8");
            scanner_velocity.useLocale(Locale.US);
            c = scanner_velocity.nextDouble();
        } catch(IOException e) {
            System.out.println(e.getMessage());
        }
        for (int i = 0; i < x.size(); i++) {
            x.set(i, x.get(i) + c * dt);
        }
    }

    public void run(){ //функция, которая обновляет экран, чтобы все двигалось
        while(true) {
            if (this.dt != 0) {
                try {
                    move(this.dt);
                    super.repaint();
                    Thread.sleep(1); //ждем
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
