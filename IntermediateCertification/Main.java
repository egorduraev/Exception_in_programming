import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.FileSystemException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws Exception {
        boolean flag = true;
        Scanner scanner = new Scanner(System.in);
        while (flag == true) {
            try {
                makeRecord(scanner);
                System.out.println("Данные введены");
            } catch (FileSystemException e) {
                System.out.println(e.getMessage());
            }
            System.out.println("Ввести еще? (y/n)");
            String answer = scanner.nextLine();
            if (answer.equals("n")) flag = false;
        }
    }

    public static void makeRecord(Scanner scanner) throws Exception{
        System.out.println("Введите ФИО, дату рождения [dd.mm.yyyy], номер телефона (число без разделителей) и пол(f/m), разделенные пробелом");

        String text;
        try {
            text = scanner.nextLine();
        } catch (Exception e) {
            throw new Exception("Ошибка при работе с консолью");
        }

        String[] array = text.split(" ");
        if (array.length != 6) {
            throw new Exception("Ошибка с количеством параметров");
        }

        String surname = array[0];
        String name = array[1];
        String second_name = array[2];

        SimpleDateFormat format = new SimpleDateFormat("dd.mm.yyyy");
        Date date;
        try {
            date = format.parse(array[3]);
        } catch (ParseException e) {
            throw new ParseException("Неверный формат даты рождения", e.getErrorOffset());
        }

        long phoneNumber;
        try {
            phoneNumber = Long.parseLong(array[4]);
        } catch (NumberFormatException e) {
            throw new NumberFormatException("Ошибка с форматом телефона");
        }

        String sex = array[5];
        if (!sex.toLowerCase().equals("m") && !sex.toLowerCase().equals("f")) {
            throw new RuntimeException("Ошибка с вводом пола");
        }

        String fileName = System.getProperty("user.dir") + "/" + surname.toLowerCase() + ".txt";
        File file = new File(fileName);
        try (FileWriter writer = new FileWriter(file, true)){
            if (file.length() > 0) {
                writer.write('\n');
                }
            writer.write(String.format("%s %s %s %s %s %s", surname, name, second_name, format.format(date), phoneNumber, sex));
            writer.flush();
            writer.close();
        } catch (IOException e) {
            throw new FileSystemException("Ошибка при работе с файлом");
        }
    }
}