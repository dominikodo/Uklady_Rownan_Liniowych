import java.io.File;
import java.io.FileNotFoundException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class UkladyRownan {

    private final double[][] macierz;
    UkladyRownan(){


    }

    void odczytZPliku (String nazwaPliku){//metoda odczytująca z pliku punkty

        File file = new File(nazwaPliku);//tworzenie obiektu file

        try {
            Scanner scanner = new Scanner(file);

            while (scanner.hasNextDouble()) {//dopóki są liczby double w pliku

                Punkt punkt=new Punkt();

                punkt.setLocation(scanner.nextDouble(), scanner.nextDouble());
                listaPunktow.add(punkt);

                System.out.println("punkt: ("+punkt.getX()+","+punkt.getY()+")");
            }

        }
        catch (FileNotFoundException e) {
            System.out.println("Nie ma takiego pliku lub nie da się otworzyć");
            System.exit(0);
        }
        catch (InputMismatchException e) {
            System.out.println("Dane w pliku nie są liczbami");
            System.exit(0);
        }
        //System.out.println(listaPunktow);
    }


    double obliczWyznacznik(double[][] tab){//metoda obliczajaca wyznacznik macierzy za pomoca rozwinięcia Laplace'a

        int stopien=tab.length;

        double wyznacznik=0;

        if(stopien==1){

            wyznacznik=tab[0][0];

        }
        else if(stopien==2){

            wyznacznik=tab[0][0]*tab[1][1]-tab[0][1]*tab[1][0];

        }
        else if(stopien==3){

            wyznacznik= tab[0][0]*tab[1][1]*tab[2][2] + tab[0][1]*tab[1][2]*tab[2][0] + tab[0][2]*tab[1][0]*tab[2][1]
                    - tab[0][2]*tab[1][1]*tab[2][0] - tab[0][1]*tab[1][0]*tab[2][2] - tab[0][0]*tab[1][2]*tab[2][1];

        }
        else if (stopien>3){

            int sign = 1;
            for (int i = 0; i < stopien; i++) {
                double[][] podmacierz = new double[stopien-1][stopien-1];
                int x = 0;
                for (int j = 1; j < stopien; j++) {
                    int y = 0;
                    for (int k = 0; k < stopien; k++) {
                        if (k == i) continue;
                        podmacierz[x][y] = tab[j][k];
                        y++;
                    }
                    x++;
                }
                wyznacznik += sign * tab[0][i] * obliczWyznacznik(podmacierz);
                sign *= -1;
            }

        }
        else{
            System.out.println("Nie można obliczyć wyznacznika");
            System.exit(0);
        }

        return wyznacznik;
    }

    double[][] obliczMacierzDopelnien(double[][] macierz){//metoda obliczajaca macierz dopelnien

        int n = macierz.length;
        double[][] macierzDopelnien = new double[n][n];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                double[][] podmacierz = new double[n-1][n-1];
                int x = 0;
                for (int k = 0; k < n; k++) {
                    if (k == i) continue;
                    int y = 0;
                    for (int l = 0; l < n; l++) {
                        if (l == j) continue;
                        podmacierz[x][y] = macierz[k][l];
                        y++;
                    }
                    x++;
                }
                macierzDopelnien[i][j] = Math.pow(-1, i+j) * obliczWyznacznik(podmacierz);
                System.out.printf("%f ",macierzDopelnien[i][j]);
            }
            System.out.printf("\n");
        }

        return macierzDopelnien;
    }

    double[][] transponujMacierz(double[][] macierz){//metoda transponujaca macierz

        int n = macierz.length;
        double[][] macierzTransponowana = new double[n][n];

        for(int j=0; j<n;  j++){
            for(int l=0; l<n; l++){

                macierzTransponowana[j][l]= macierz[l][j];

                System.out.printf("%f ",macierzTransponowana[j][l]);

            }
            System.out.printf("\n");

        }

        return macierzTransponowana;
    }

    double[][] obliczMacierzOdwrotna(double[][] macierz) {//metoda obliczajaca macierz odwrotna

        int n = macierz.length;
        double[][] macierzOdwrotna = new double[n][n];


        double wyznacznik = obliczWyznacznik(macierz);
        System.out.println("\nWyznacznik s: " + wyznacznik);

        System.out.println("\nMacierz dopelnien: ");
        double[][] macierzDopelnien = obliczMacierzDopelnien(macierz);

        System.out.println("\nMacierz transponowana: ");
        double[][] macierzTransponowana = transponujMacierz(macierzDopelnien);

        System.out.println("\nMacierz odwrotna: ");
        for (int j = 0; j < n; j++) {
            for (int l = 0; l < n; l++) {

                macierzOdwrotna[j][l] = macierzTransponowana[j][l] / wyznacznik;

                System.out.printf("%f ", macierzOdwrotna[j][l]);

            }
            System.out.printf("\n");

        }

        return macierzOdwrotna;
    }
}
