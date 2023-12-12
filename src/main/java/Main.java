import java.util.*;

public class Main {
    public static void main(String[] args) {
        System.out.println("\n=====================================\n" +
                           "Занятие 2. Изучение методов StrimAPI \n" +
                           "=====================================");

        //получаем List<Patient> из текстового dump.txt
        List<Patient> patients = Dump.getDump();
//        System.out.println(patients.size());      //для проверки корректности считывания базы выводим длину массива пациентов
//        patients.forEach(System.out::println);    //для проверки выводим содержимое базы

        //----- Использование метода collect() -----
        System.out.println("\n" +
                "-----------------------------------------------------------------------------------------" +
                "\nЗадание 1. Из списка пациентов создать Map, где ключ - дата рождения," +
                        " значение - ФИО (без преобразования)");
        TerminalOperatorConv.getDateBirtAndFio(patients).forEach((k, v) ->
                System.out.println("value: " + v + ", key: " + k + " г.р."));

        //----- Использование метода forEach() -----
        System.out.println("\n" +
                "-----------------------------------------------------------------------------------------" +
                "\nЗадание 2. Вывести на экран только только список расходов пациентов");
        TerminalOperatorConv.printExpenses(patients);

        //----- Использование методов min(), max() -----
        System.out.println("\n" +
                "-----------------------------------------------------------------------------------------" +
                "\nЗадание 3. Получить минимального и максимального пациента из потока по сумме всех расходов пациента");
        System.out.println("\nПациент с максимальными расходами: ");
        System.out.println(TerminalOperatorConv.getMinMaxPatient(patients, "max"));
        //для проверки корректности расчета max выведем на экран 3 первых пациента по убыванию суммы расходов
        TerminalOperatorConv.get3PatientByMaxAmountExpenses(patients);

        System.out.println("\nПациент с минимальными расходами: ");
        System.out.println(TerminalOperatorConv.getMinMaxPatient(patients, "min"));
        //для проверки корректности расчета min выведем на экран 3 первых пациента по возрастанию суммы расходов
        TerminalOperatorConv.get3PatientByMinAmountExpenses(patients);

        //----- Использование метода findFirst() -----
        System.out.println("\n" +
                "-----------------------------------------------------------------------------------------" +
                "\nЗадание 4. Получить первого пациента, кто родился в декабре 1999 года");
        int month = 12;     //можно задать произвольный месяц для контроля
        int year = 1999;    //можно задать произвольный год для контроля
        if (TerminalOperatorConv.getFirstPatient121999(patients, month, year).isEmpty()) {
            System.out.printf("\nНет пациентов с месяцем рождения %d.%d", month, year);
        } else {
            System.out.printf("\nПервый пациент с месяцем рождения %d.%d: \n", month, year);
            System.out.println(TerminalOperatorConv.getFirstPatient121999(patients, month, year));
        }

        //----- Использование метода allMatch() -----
        System.out.println("\n" +
                "-----------------------------------------------------------------------------------------" +
                "\nЗадание 5. Проверить, если ли хоть 1 здоровый человек (allMatch())");
        if (!TerminalOperatorConv.getHealthyPatient(patients)) {
            //Получить "здоровых" пациентов ("здоровым" считаю пациента с суммой расходов = 0)
            System.out.println("Есть \"здоровые\" пациенты:");
            ConveerOperatorConv.getListHealthyPatient(patients, 0)
                    .forEach(p -> System.out.println("\tРасходы: " + p.getExpenses() + " = "
                            + ConveerOperatorConv.getAmountExpenses(p.getExpenses()) + " -> id: " + p.getId() + ", " + p.getFio()));
        } else {
            System.out.println("Нет \"здоровых\" пациентов:");
        }
        //для контроля, что нулевые значения в базе есть/нет, вызываем метод для расходов 500
        System.out.println("Для контроля: список пациентов с расходами < 500:");
        ConveerOperatorConv.getListHealthyPatient(patients, 500)
                .forEach(p -> System.out.println("\tРасходы: " + p.getExpenses() + " = "
                        + ConveerOperatorConv.getAmountExpenses(p.getExpenses()) + " -> id: " + p.getId() + ", " + p.getFio()));

        //----- Использование метода noneMatch() -----
        System.out.println("\n" +
                "-----------------------------------------------------------------------------------------" +
                "\nЗадание 6. Проверить, если ли хоть 1 человек старше 100 лет (или произвольный возраст) (noneMatch())");
        int age = 100;       //можно задать произвольный возраст для контроля
        if (TerminalOperatorConv.getNoneAge100(patients, age)) {
            System.out.printf("\nНет пациентов старше %d лет", age);
        } else {System.out.printf("\nЕсть пациенты старше %d лет и их количество: %d", age,
                (int)TerminalOperatorConv.countAge100(patients, age));}

        //----- Использование метода anyMatch() -----
        System.out.println("\n" +
                "-----------------------------------------------------------------------------------------" +
                "\nЗадание 7. Проверить, если ли хоть 1 человек старше 70 лет (или произвольный возраст) (anyMatch())");
        int age1 = 70;
        if (!TerminalOperatorConv.getAnyAge100(patients, age1)) {
            System.out.printf("\nНет пациентов старше %d лет", age1);
        } else {System.out.printf("\nЕсть пациенты старше %d лет и их число: %d", age1,
                (int)TerminalOperatorConv.countAge100(patients, age1));}


//        //Методы filter и forEach, рассмотренные на занятии
//        ConveerOperatorConv.filterVSK1999(patients,"ВСК", 1999).forEach(System.out::println);
//        TerminalOperatorConv.getNames(patients).forEach(System.out::println);
    }

}