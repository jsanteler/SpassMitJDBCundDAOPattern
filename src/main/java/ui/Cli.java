package ui;

import dataaccess.DatabaseException;
import dataaccess.MyCourseRepository;
import domain.Course;

import java.sql.SQLOutput;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class Cli {

    Scanner scan;
    MyCourseRepository repo;

    public Cli(MyCourseRepository repo){

        this.scan = new Scanner(System.in);
        this.repo = repo;
    }

    public void start(){

        String input = "-";
        while (!input.equals("x")){
            showMenue();
            input = scan.nextLine();
            switch (input){
                case "1":
                    System.out.println("Kurseingabe");
                    break;
                case "2":
                    showAllCourses();
                    break;
                case "3":
                    showCourseDetails();
                    break;
                case "x":
                    System.out.println("Auf Wiedersehen!");
                    break;
                default:
                    inputError();
                    break;
            }
        }
        scan.close();
    }

    private void showCourseDetails() {
        System.out.println("Für welchen Kurs möchten Sie die Kursdetails anzeigen?");
        Long corseId = Long.parseLong(scan.nextLine());
        try
        {
            Optional<Course> courseOptional = repo.getByID(corseId);
            if(courseOptional.isPresent()){
                System.out.println(courseOptional.get());
            }else {
                System.out.println("Kurs mit der ID " + corseId + " nicht gefunden!");
            }

        }catch (DatabaseException databaseException){
            System.out.println("Datenbankfehler bei Kurs-Detailanzeige: " + databaseException.getMessage());
        }catch (Exception exception){
            System.out.println("Unbekannter Fehler der Kurs-Detailanzeige: " + exception.getMessage());
        }
    }

    private void showAllCourses() {

        List<Course> list = null;

        try {
            list = repo.getAll();
            if (list.size() > 0) {

                for (Course course : list) {
                    System.out.println(course);
                }
            } else {
                System.out.println("Kursliste leer!");
            }
        } catch (DatabaseException databaseException) {
            System.out.println("Datenbankfehler bei Anzeige aller Kurse: " + databaseException.getMessage());
        } catch (Exception exception) {

            System.out.println("Unbekannter Fehler bei Anzeige aller Kurse: " + exception.getMessage());
        }
    }


    private void showMenue(){
        System.out.println("---------------------- KURSMANAGEMENT --------------------");
        System.out.println("(1) Kurs eingeben \t (2) ALle Kurse anzeigen \t" + "(3) Kursdetails anzeigen");
        System.out.println("(x) ENDE");
    }

    private void inputError(){
        System.out.println("Bitte nur die Zahlen der Menüauswahl eingeben!");
    }
}
