package ui;

import dataaccess.DatabaseException;
import dataaccess.MyCourseRepository;
import dataaccess.MyStudentRepository;
import domain.Course;
import domain.CourseType;
import domain.InvalidValueException;
import domain.Student;


import java.sql.Date;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class Cli {

    Scanner scan;
    MyCourseRepository repo;

    MyStudentRepository studentrepo;


    public Cli(MyCourseRepository repo, MyStudentRepository studentrepo) {

        this.scan = new Scanner(System.in);
        this.repo = repo;
        this.studentrepo = studentrepo;
    }

    public void start() {

        String input = "-";
        while (!input.equals("x")) {
            showMenue();
            input = scan.nextLine();
            switch (input) {
                case "1":
                    addCourse();
                    break;
                case "2":
                    showAllCourses();
                    break;
                case "3":
                    showCourseDetails();
                    break;
                case "4":
                    updateCourseDetails();
                    break;
                case "5":
                    deleteCoures();
                    break;
                case "6":
                    courseSearch();
                    break;
                case "7":
                    runningCourses();
                    break;
                case "8":
                    courseSearchByName();
                    break;
                case "9":
                    courseSearchByDescription();
                    break;
                case "10":
                    courseSearchByBegindate();
                    break;
                case "11":
                    courseSearchByCourseType();
                    break;
                case "12":
                    addStudent();
                    break;
                case "13":
                    showAllStudents();
                    break;
                case "14":
                    updateStudentDetails();
                    break;
                case "15":
                    deleteStudent();
                    break;
                case "16":
                    studentSearchByName();
                    break;
                case "17":
                    studentSearchById();
                    break;
                case "18":
                    studentSearchByBirthDate();
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

    private void courseSearchByCourseType() {
        System.out.println("Bitte geben Sie den Kurstyp an den Sie suchen: ");
        List<Course> coursesList;
        try {
            CourseType courseType = CourseType.valueOf(scan.nextLine());
            coursesList = repo.findAllCoursesByCourseType(courseType);
            for (Course course : coursesList) {
                System.out.println(course);
            }
        } catch (IllegalArgumentException illegalArgumentException) {
            System.out.println("Eingabefehler: " + illegalArgumentException.getMessage());
        } catch (Exception exception) {
            System.out.println("Unbekannter Fehler bei der Ausgabe der Studenten");
        }
    }



    private void courseSearchByBegindate() {
        System.out.println("Bitte geben Sie das Beginndatum des Kurses ein (YYYY-MM-DD):");
        List<Course> coursesList;
        try {
            Date coursdate = Date.valueOf(scan.nextLine());
            coursesList = repo.findAllCoursesByStartDate(coursdate);
            for (Course course : coursesList) {
                System.out.println(course);
            }
        } catch (IllegalArgumentException illegalArgumentException) {
            System.out.println("Eingabefehler: " + illegalArgumentException.getMessage());
        } catch (Exception exception) {
            System.out.println("Unbekannter Fehler bei der Ausgabe der Studenten");
        }
    }

    private void courseSearchByDescription() {
        System.out.println("Geben Sie die Beschreibung des Kurses ein: ");
        String searchString = scan.nextLine();
        List<Course> courseList;

        try {
            courseList = repo.findAllCoursesByDescription(searchString);
            for (Course course : courseList) {
                System.out.println(course);
            }

        } catch (DatabaseException databaseException) {
            System.out.println("Datenbankfehler bei der Kurssuche: " + databaseException.getMessage());
        } catch (Exception e) {
            System.out.println("Unbekannter Fehler bei der Kurssuche: " + e.getMessage());
        }
    }




    private void courseSearchByName() {
        System.out.println("Geben Sie den Coursenamen ein: ");
        String searchString = scan.nextLine();
        List<Course> courseList;
        try {
            courseList = repo.findAllCoursesByName(searchString);
            for (Course course : courseList) {
                System.out.println(course);
            }

        } catch (DatabaseException databaseException) {
            System.out.println("Datenbankfehler bei der Studentensuche: " + databaseException.getMessage());
        } catch (Exception exception) {
            System.out.println("Unbekannter Fehler bei der Studentensuche: " + exception.getMessage());
        }
    }



    private void studentSearchByBirthDate() {
        System.out.println("Bitte geben Sie das Gebursdatum des gesuchten Studenten ein (YYYY-MM-DD):");
        List<Student> studentList;
        try {
            Date birthDate = Date.valueOf(scan.nextLine());
            studentList = studentrepo.findStudentbyBirthdate(birthDate);
            for (Student student : studentList) {
                System.out.println(student);
            }
        } catch (IllegalArgumentException illegalArgumentException) {
            System.out.println("Eingabefehler: " + illegalArgumentException.getMessage());
        } catch (Exception exception) {
            System.out.println("Unbekannter Fehler bei der Ausgabe der Studenten");
        }
    }


    private void studentSearchById() {
        System.out.println("Bitte geben Sie die ID des gesuchten Studenten ein!");

        try {
            Long gesuchteStudentenId = Long.valueOf(scan.nextLine());
            List<Student> studentList;
            studentList = studentrepo.findStudentById(gesuchteStudentenId);
            for (Student student : studentList) {
                System.out.println(student);
            }
        } catch (DatabaseException databaseException) {
            System.out.println("Datenbankfehler bei der Studentensuche: " + databaseException.getMessage());
        } catch (Exception exception) {
            System.out.println("Unbekannter Fehler bei der Studentensuche: " + exception.getMessage());
        }
    }


    private void studentSearchByName() {
        System.out.println("Geben Sie einen Suchbegriff an!");
        String searchString = scan.nextLine();
        List<Student> studentList;
        try {
            studentList = studentrepo.findStudentByName(searchString);
            for (Student student : studentList) {
                System.out.println(student);
            }

        } catch (DatabaseException databaseException) {
            System.out.println("Datenbankfehler bei der Studentensuche: " + databaseException.getMessage());
        } catch (Exception exception) {
            System.out.println("Unbekannter Fehler bei der Studentensuche: " + exception.getMessage());
        }
    }



    private void deleteStudent() {
        System.out.println("Welchen Studenten möchten Sie löschen? Bitte ID eingeben:");

        try {
            Long studentIdToDelete = Long.parseLong(scan.nextLine());
            studentrepo.deleteById(studentIdToDelete);
        } catch (IllegalArgumentException illegalArgumentException) {
            System.out.println("Eingabefehler: " + illegalArgumentException.getMessage());
        } catch (InvalidValueException invalidValueException) {
            System.out.println("Studentendaten nicht korrekt angegeben: " + invalidValueException.getMessage());
        } catch (DatabaseException databaseException) {
            System.out.println("Datenbankfehler beim Löschen: " + databaseException.getMessage());
        } catch (Exception exception) {
            System.out.println("Unbekannter Fehler beim Löschen: " + exception.getMessage());
        }
    }

    private void updateStudentDetails() {

            System.out.println("Für welche Studenten-ID möchten Sie die Studentendetails ändern?");

            try {
                Long studentId = Long.parseLong(scan.nextLine());
                Optional<Student> studentOptional = studentrepo.getByID(studentId);
                if (studentOptional.isEmpty()) {
                    System.out.println("Student mit der gegebenen ID nicht in der Datenbank!");
                } else {
                    Student student = studentOptional.get();

                    System.out.println("Änderungen für folgenden Studenten: ");
                    System.out.println(student);

                    String vorname, nachname, geburtsdatum;

                    System.out.println("Bitte neue Studentendaten angeben (Enter falls keine Änderung gewünscht ist):");
                    System.out.println("Vorname: ");
                    vorname = scan.nextLine();
                    System.out.println("Nachname: ");
                    nachname = scan.nextLine();
                    System.out.println("Geburtsdatum: ");
                    geburtsdatum = scan.nextLine();

                    Optional<Student> optionalStudentUpdated = studentrepo.update(
                            new Student(
                                    student.getId(),
                                    vorname.equals("") ? student.getVorname() : vorname,
                                    nachname.equals("") ? student.getNachname() : nachname,
                                    geburtsdatum.equals("") ? student.getGeburtstag() : Date.valueOf(geburtsdatum)
                            )
                    );

                    optionalStudentUpdated.ifPresentOrElse(
                            (s) -> System.out.println("Student aktualisiert " + s),
                            () -> System.out.println("Student konnte nicht aktualisiert werden")
                    );
                }
            } catch (InvalidValueException invalidValueException) {
                System.out.println("Eingabefehler bei Studentenupdate: " + invalidValueException.getMessage());
            } catch (Exception exception) {
                System.out.println("Unbekannter Fehler bei Studentenupdate: " + exception.getMessage());
            }
        }



    private void showAllStudents() {
        List<Student> list = null;

        try {
            list = studentrepo.getAll();
            if (list.size() > 0) {

                for (Student student : list) {
                    System.out.println(student);
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


    private void addStudent() {

        String vorname, nachname;
        Date geburtstag;


        try {
            System.out.println("Geben Sie die Daten für den Studenten ein!");
            System.out.println("Vorname: ");
            vorname = scan.nextLine();
            if (vorname.equals("")) throw new IllegalArgumentException("Eingabe darf nicht Leer sein!");
            System.out.println("Nachname: ");
            nachname = scan.nextLine();
            if (nachname.equals("")) throw new IllegalArgumentException("Eingabe darf nicht Leer sein!");
            System.out.println("Geburtsdatum (YYYY-MM-DD): ");
            geburtstag = Date.valueOf(scan.nextLine());

            Optional<Student> optionalStudent = studentrepo.insert(
                    new Student(vorname, nachname, geburtstag)
            );
            if (optionalStudent.isPresent()) {
                System.out.println("Student angelegt: " + optionalStudent.get());
            } else {
                System.out.println("Student konnte nicht angelegt werden");
            }
        } catch (IllegalArgumentException illegalArgumentException) {
            System.out.println("Eingabefehler: " + illegalArgumentException);
        } catch (InvalidValueException invalidValueException) {
            System.out.println("Studentendaten nicht korrekt angegeben: " + invalidValueException.getMessage());
        } catch (DatabaseException databaseException) {
            System.out.println("Datenbankfehler beim Einfügen: " + databaseException.getMessage());
        } catch (Exception e) {
            System.out.println("Unbekannter Fehler beim Einfügen " + e.getMessage());
        }
    }

    private void runningCourses() {

        System.out.println("Aktuell laufende Kurse: ");
        List<Course> list;

        try {
            list = repo.findAllRunningCourses();
            for (Course course : list) {
                System.out.println(course);

            }

        } catch (DatabaseException databaseException) {
            System.out.println("Datenbankfehler bei Kurs-Anzeige der laufenden Kurse: " + databaseException.getMessage());
        } catch (Exception exception) {
            System.out.println("Unbekannter Fehler bei Kurs-Anzeige für laufende Kurse: " + exception.getMessage());
        }
    }

    private void courseSearch() {
        System.out.println("Geben Sie einen Suchbegriff an!");
        String searchString = scan.nextLine();
        List<Course> courseList;

        try {
            courseList = repo.findAllCoursesByNameOrDescription(searchString);
            for (Course course : courseList) {
                System.out.println(course);
            }

        } catch (DatabaseException databaseException) {
            System.out.println("Datenbankfehler bei der Kurssuche: " + databaseException.getMessage());
        } catch (Exception e) {
            System.out.println("Unbekannter Fehler bei der Kurssuche: " + e.getMessage());
        }
    }

    private void deleteCoures() {

        System.out.println("Welchen Kurs möchten Sie löschen ? Bitte die ID angeben.");

        Long courseIdToDelete = Long.parseLong(scan.nextLine());

        try {
            repo.deleteById(courseIdToDelete);
            System.out.println("Kurs mit ID " + courseIdToDelete + "gelöscht!");
        } catch (DatabaseException databaseException) {
            System.out.println("Datenbankfehler beim Löschen: " + databaseException.getMessage());
        } catch (Exception e) {
            System.out.println("Unbekannter Fehler beim Löschen:" + e.getMessage());
        }
    }

    private void updateCourseDetails() {

        System.out.println("Für welchen Kurs-ID möchten Sie die Kursdetails ändern ?");
        Long courseID = Long.parseLong(scan.nextLine());

        try {
            Optional<Course> courseOptional = repo.getByID(courseID);
            if (courseOptional.isEmpty()) {

                System.out.println("Kurs mit der gegebenen ID nicht ibn der Datenbank!");
            } else {
                Course course = courseOptional.get();

                System.out.println("Änderungen für folgenen Kurs: ");
                System.out.println(course);

                String name, description, hours, dateFrom, dateTo, courseType;

                System.out.println("Bitte neue Kursdaten angeben (Enter, falls keine Änderung gewünscht ist): ");

                System.out.println("Name: ");
                name = scan.nextLine();
                System.out.println("Beschreibung: ");
                description = scan.nextLine();
                System.out.println("Stundenanzahl: ");
                hours = scan.nextLine();
                System.out.println("Startdatum (YYYY-MM-DD");
                dateFrom = scan.nextLine();
                System.out.println("Startdatum (YYYY-MM-DD");
                dateTo = scan.nextLine();
                System.out.println("Kurstyp (ZA/BF/FF/OE): ");
                courseType = scan.nextLine();


                Optional<Course> optionalCourseUpdated = repo.update(
                        new Course(
                                course.getId(),
                                name.equals("") ? course.getName() : name,
                                description.equals("") ? course.getDescription() : description,
                                hours.equals("") ? course.getHours() : Integer.parseInt(hours),
                                dateFrom.equals("") ? course.getBeginDate() : Date.valueOf(dateFrom),
                                dateTo.equals("") ? course.getEndDate() : Date.valueOf(dateTo),
                                courseType.equals("") ? course.getCourseType() : CourseType.valueOf(courseType)
                        )
                );
                //Wenn present ist, Wird kurs aktualisiert Lambda ausdruck.
                optionalCourseUpdated.ifPresentOrElse(
                        (c) -> System.out.println("Kurs aktualisiert: " + c),
                        () -> System.out.println("Kurs konnte nicht aktualisiert werden!")
                );

            }
        } catch (IllegalArgumentException illegalArgumentException) {

            System.out.println("EIngabefehler: " + illegalArgumentException.getMessage());
        } catch (InvalidValueException invalidValueException) {
            System.out.println("Kursdaten nicht korrekt angegeben: " + invalidValueException.getMessage());
        } catch (DatabaseException databaseException) {
            System.out.println("Datenbankfehler beim Einfügen: " + databaseException.getMessage());
        } catch (Exception e) {
            System.out.println("Unbekannter Fehler beim Einfügen " + e.getMessage());
        }
    }

    private void addCourse() {
        String name, description;
        int hours;
        Date dateFrom, dateTo;
        CourseType courseType;


        try {

            System.out.println("Bitte alle Kursdaten angeben:");
            System.out.println("Name");
            name = scan.nextLine();
            if (name.equals("")) throw new IllegalArgumentException("Eingabe darf nicht leer sein!");
            System.out.println("Beschreibung; ");
            description = scan.nextLine();
            if (description.equals("")) throw new IllegalArgumentException("Eingabe darf nicht leer sein!");
            System.out.println("Stundenanzahl: ");
            hours = Integer.parseInt(scan.nextLine());
            System.out.println("Startdatum (YYYY-MM-DD): ");
            dateFrom = Date.valueOf(scan.nextLine());
            System.out.println("Enddatum (YYYY-MM-DD): ");
            dateTo = Date.valueOf(scan.nextLine());
            System.out.println("Kurstyp: (ZA/BF/FF/OE): ");
            courseType = CourseType.valueOf(scan.nextLine());

            Optional<Course> optionalCourse = repo.insert(
                    new Course(name, description, hours, dateFrom, dateTo, courseType)
            );

            if (optionalCourse.isPresent()) {
                System.out.println("Kurs angelegt: " + optionalCourse.get());
            } else {
                System.out.println("Kurs konnte nicht angelegt werden!");
            }

        } catch (IllegalArgumentException illegalArgumentException) {

            System.out.println("EIngabefehler: " + illegalArgumentException.getMessage());
        } catch (InvalidValueException invalidValueException) {
            System.out.println("Kursdaten nicht korrekt angegeben: " + invalidValueException.getMessage());
        } catch (DatabaseException databaseException) {
            System.out.println("Datenbankfehler beim Einfügen: " + databaseException.getMessage());
        } catch (Exception e) {
            System.out.println("Unbekannter Fehler beim Einfügen " + e.getMessage());
        }

    }

    private void showCourseDetails() {
        System.out.println("Für welchen Kurs möchten Sie die Kursdetails anzeigen?");
        Long corseId = Long.parseLong(scan.nextLine());
        try {
            Optional<Course> courseOptional = repo.getByID(corseId);
            if (courseOptional.isPresent()) {
                System.out.println(courseOptional.get());
            } else {
                System.out.println("Kurs mit der ID " + corseId + " nicht gefunden!");
            }

        } catch (DatabaseException databaseException) {
            System.out.println("Datenbankfehler bei Kurs-Detailanzeige: " + databaseException.getMessage());
        } catch (Exception exception) {
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


    private void showMenue() {
        System.out.println("---------------------- KURSMANAGEMENT --------------------");
        System.out.println("(1) Kurs eingeben \t (2) ALle Kurse anzeigen \t" + "(3) Kursdetails anzeigen");
        System.out.println("(4) Kursdetails ändern \t (5) Kurs löschen \t (6) Kurssuche \\t\"");
        System.out.println("(7) Laufende Kurse \t (8) Kurs beim Namen suchen");
        System.out.println("(9) Kurs über Beschreibung suchen \t (10) Kurs beim Beginndatum suchen \t (11) Kurs über KursTyp suchen");
        System.out.println("---------------------- STUDENTMANAGEMENT ------------------");
        System.out.println("(12) Studenten anlegen \t (13) Studenten anzeigen \\t\"");
        System.out.println("(14) Studetendetails ändern \t (15) Studenten löschen \t (16) Studenten suchen beim Namen \t\"");
        System.out.println("(17) Studenten suchen nach Id \t (18) Studenten suchen nach Geburtsdatum \t () \t\"");
        System.out.println("(x) ENDE");
    }

    private void inputError() {
        System.out.println("Bitte nur die Zahlen der Menüauswahl eingeben!");
    }
}
