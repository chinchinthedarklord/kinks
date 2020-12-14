#include <iostream>
#include <cstdlib>
#include <cmath>
#include <fstream>

using namespace std;

double MexicanHat(double phi){ //потенциал механизма Хиггса
    return 0.25 * (phi * phi - 1) * (phi * phi - 1);
}

double MexicanHatPrime(double phi){ //его производная для решения
    return (phi * phi - 1) * phi;
}

double TopologicalCharge(double inf_minus, double inf_plus){ //топологический заряд
    return (inf_plus - inf_minus) / 2;
}

int main() 
{
    const int N = 1000;
    double scale = 2.0;
    double phi_V;
    double V;
    double c = 0.5; //скорость волны

    //записываем график потенциала
    ofstream out;
    out.open("resources/graph_potential.dat"); //открываем файл для графика на запись

    if(out.is_open()){
        for(int i = 0; i < N; i++){
            phi_V = 2 * scale * ((double)i - (double) N / 2) / ((double) N);
            out << phi_V << " " << MexicanHat(phi_V) << endl;
        }
        phi_V = scale;
        out << phi_V << " " << MexicanHat(phi_V);
    }

    //записываем график решения
    ofstream out_wave;
    out_wave.open("resources/solution.dat");

    scale = 5.;

    double phi[N];
    phi[N / 2] = 0.0;
    double phi_prime[N];
    phi_prime[N / 2] = 1 / sqrt(2.0);
    double dt = ((double) 2 * scale)/((double) N);

    double x;


    if(out_wave.is_open()){
        // моделируем правую часть решения
        for(int i = 0; i <= N / 2 - 1; i++){
            phi[N / 2 + i + 1] = phi[N / 2 + i] + phi_prime[N / 2 + i] * dt;
            phi_prime[N / 2 + i + 1] = phi_prime[N / 2 + i] + MexicanHatPrime(phi[N / 2 + i + 1]) * dt;

            if (phi[N / 2 + i] >= 1.0){
                phi[N / 2 + i + 1] = 1.0;
                phi_prime[N / 2 + i + 1] = 0.0;
            }
        }
        //моделируем левую часть решения
        for(int i = 0; i <= N / 2 - 1; i++){
            phi[N / 2 - i - 1] = phi[N / 2 - i] + phi_prime[N / 2 - i] * (-dt);
            phi_prime[N / 2 - i - 1] = phi_prime[N / 2 - i] + MexicanHatPrime(phi[N / 2 - i  - 1]) * (-dt);
            if (phi[N / 2 - i - 1] < -1){
                phi[N / 2 - i - 1] = -1.0;
                phi_prime[N / 2 - i - 1] = 0.0;
            }
        }
        //кидаем все в файл
        for(int i = 0; i < N; i++){
            x = 2 * scale * ((double) i - ((double)N / 2)) / N * sqrt(1 - c * c);
            out_wave << x << " " << phi[i] << endl;
        }
        x = scale * sqrt(1 - c * c);
        out_wave << x << " " << phi[N - 1];
    }
    //записываем значение энергии и ее плотность
    double energy[N];
    double total_energy = 0;
    for (int i = 0; i < N; i++){
        energy[i] = 0.5 * phi_prime[i] * phi_prime[i] + MexicanHat(phi[i]);
        total_energy = total_energy + energy[i] * dt;
    }

    ofstream out_energy;
    out_energy.open("resources/energy.dat");
    if(out_energy.is_open()){
        for(int i = 0; i < N - 1; i++){
            x = 2 * scale * ((double) i - ((double)N / 2)) / N * sqrt(1 - c * c);
            out_energy << x << " " << energy[i] << endl;
        }
        x = scale * sqrt(1 - c * c);
        out_energy << x << " " << energy[N-1];
    }

    //значение полной энергии
     ofstream out_total_energy;
     out_total_energy.open("resources/total_energy.dat");
     out_total_energy << total_energy;

    //записываем значение топологического заряда
    double topological_charge = TopologicalCharge(phi[1], phi[N-1]);

    ofstream out_charge;
    out_charge.open("resources/charge.dat");
    out_charge << topological_charge;

     ofstream out_velocity;
     out_velocity.open("resources/velocity.dat");
     out_velocity << c;

    //записываем значение дивергенции нетеровского тока
    return 0; 
}