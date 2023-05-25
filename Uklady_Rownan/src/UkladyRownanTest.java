import java.util.Scanner;

public class UkladyRownanTest {

    public static void main(String[] args) {

        while (true) {

            UkladyRownan ukladyRownan = new UkladyRownan();
            Scanner scanner = new Scanner(System.in);

            String nazwaPliku;

            System.out.println("Podaj nazwe ścieżkę pliku: ");

            nazwaPliku = scanner.nextLine();

            ukladyRownan.odczytZPliku(nazwaPliku);

            ukladyRownan.obliczRownanieKramer();

            //C:\Users\piese\Desktop\Metody Obliczeniowe\Uklady_Rownan_Liniowych\Uklady_Rownan\plik.txt
        }

    }

}
