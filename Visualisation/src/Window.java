import javax.swing.JFrame;

public class Window extends JFrame{ //класс окна, в котором все будет выводиться
    public Window(){
        super("Kink's simulation"); //название окна
        setBounds(100, 100, 560, 560); // размеры окна
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //закрытие программы по выходу
    }
}
