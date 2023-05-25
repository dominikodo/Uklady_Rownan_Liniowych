import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class UkladyRownan {

    private final ArrayList<Double> lista;
    UkladyRownan(){

        lista =new ArrayList<>();


    }

    void odczytZPliku (String nazwaPliku){//metoda odczytująca z pliku punkty

        File file = new File(nazwaPliku);//tworzenie obiektu file

        try {
            Scanner scanner = new Scanner(file);

            while (scanner.hasNextDouble()) {//dopóki są liczby double w pliku

                double liczba=scanner.nextDouble();
                lista.add(liczba);

                //System.out.println("pobrane liczny: "+liczba);
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

            int znak = 1;
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
                wyznacznik += znak * tab[0][i] * obliczWyznacznik(podmacierz);
                znak *= -1;
            }

        }
        else{
            System.out.println("Nie można obliczyć wyznacznika");
            System.exit(0);
        }

        return wyznacznik;
    }
    double[][] wyodrebnijMacierz(){

        double pierwszy  = lista.get(0);
        int wielkosc = (int) pierwszy;
        int zmienna=1;

        double[][] macierz = new double[wielkosc][wielkosc];

        for (int i =1; i<=wielkosc; i++){
            for (int j=1; j<=wielkosc; j++){
                macierz[i-1][j-1]=lista.get(zmienna);
                System.out.printf("%f ", macierz[i-1][j-1]);
                zmienna++;
            }
            System.out.println("");
        }
        return macierz;

    }

    double[] wyodrebnijWektor(){

        double pierwszy  = lista.get(0);
        int wielkosc = (int) pierwszy;
        int zmienna=0;

        double[] wektor = new double[wielkosc];

        for (int i = lista.size(); i>lista.size()-wielkosc; i--){
            wektor[wielkosc - zmienna -1 ]=lista.get(lista.size()-1 -zmienna);
            System.out.println(wektor[wielkosc -zmienna -1]+" ");
            zmienna++;
        }
        System.out.println("");
        return wektor;
    }
    void obliczRownanieKramer(){


        double[][] macierz = wyodrebnijMacierz();
        double[] wektor = wyodrebnijWektor();
        double wyznacznikGlowny = obliczWyznacznik(macierz);
        //System.out.println("Wyznacznik glowny = " + wyznacznikGlowny);
        if(wyznacznikGlowny==0){
            System.out.println("Układ równań jest sprzeczny lub ma nieskończenie wiele rozwiązań");
        }
        else {
            double[] wyznaczniki = new double[macierz.length];
            double[] wyniki = new double[macierz.length];
            for (int i = 0; i < macierz.length; i++) {
                double[][] macierzPomocnicza = new double[macierz.length][macierz.length];
                for (int j = 0; j < macierz.length; j++) {
                    for (int k = 0; k < macierz.length; k++) {
                        if (k == i) {
                            macierzPomocnicza[j][k] = wektor[j];
                        } else {
                            macierzPomocnicza[j][k] = macierz[j][k];

                        }
                        //System.out.printf("%f ",macierzPomocnicza[j][k]);
                    }
                    //System.out.println("");
                }
                wyznaczniki[i] = obliczWyznacznik(macierzPomocnicza);
                //System.out.println("Wyznacznik " + i + " = " + wyznaczniki[i]);
                wyniki[i] = wyznaczniki[i] / wyznacznikGlowny;
                System.out.println("x" + i + " = " + wyniki[i]);
            }

        }
    }


}
