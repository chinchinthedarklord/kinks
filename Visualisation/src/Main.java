import javax.swing.*;
import java.awt.*;
import java.io.*;

public class Main{
    public static void main(String[] args) throws IOException, InterruptedException {
        //Window window0 = new Window(); //окно для выбора модели

        //делаем окно видимым
        //window0.setVisible(true);

        String calculations = "g++ calculations_mexican_hat.cpp"; // команда для запуска вычислений на cpp
        Process proc1 = Runtime.getRuntime().exec(calculations);
        proc1.waitFor(); //ждем, пока команда дойдет

        String file_forming = "./a.out"; // команда для запуска вычислений на cpp
        Process proc2 = Runtime.getRuntime().exec(file_forming);
        proc2.waitFor(); //ждем, пока команда дойдет

        Window window1 = new Window(); //наше окно для графиков

        //добавляем числовые значения в окно
        TextFromFile topological_charge = new TextFromFile(30, 30, 240, 80, "resources/charge.dat", "topological charge Q = ");
        topological_charge.setBounds(240, 240, 240, 80);

        TextFromFile total_energy = new TextFromFile(30, 30, 240, 80, "resources/total_energy.dat", "total energy E = ");
        total_energy.setBounds(240, 320, 240, 80);

        TextFromFile velocity = new TextFromFile(30, 30, 240, 80, "resources/velocity.dat", "velocity c = ");
        velocity.setBounds(240, 400, 240, 80);


        //новые графики и их параметры
        Graph graph_energy = new Graph("./resources/energy.dat", 240, 240, 30, 200, 20, 20, "x, xoordinate", "energy e(x, t)", 0.06, 0, 0);
        Graph graph_solution = new Graph("./resources/solution.dat", 240, 240, 30, 100, 20, 20, "x, xoordinate", "phi(x, t), field", 0.06, 0, 1);
        Graph graph_potential = new Graph("./resources/graph_potential.dat", 240, 240, 50, 100, 20, 20, "phi, field", "V(phi), potential", 0, 2, 0);

        //располагаем графики в окне
        graph_energy.setBounds(240, 0, 240, 240);
        graph_solution.setBounds(0, 240, 240, 240);
        graph_potential.setBounds(0, 0, 240, 240);

        //добавляем график в окно
        window1.add(graph_energy);
        window1.add(graph_solution);
        window1.add(topological_charge);
        window1.add(total_energy);
        window1.add(velocity);
        window1.add(graph_potential);




        //делаем окно видимым
        window1.setVisible(true);

    }
}