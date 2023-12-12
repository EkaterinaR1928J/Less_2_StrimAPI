import java.time.LocalDate;
import java.time.Month;
import java.util.*;
import java.util.stream.Collectors;
import java.util.Comparator;

// Терминальные операторы (оператор, завершающий стрим):
// toList(), collect(), forEach(), findFirst(), noneMatch(), anyMatch(), allMatch(), min(), max()
public class TerminalOperatorConv {
    //из списка пациентов получить список имен в формате Фамилия И.О.
    //Метод собирает элементы в List
    public static List<String> getNames(List<Patient> patients) {
        return patients.stream()
                .map(p -> getFIO(p))
                .toList();
    }

    private static String getFIO(Patient patient) {
        String s = patient.getFio();
        String[] sArr = s.split(" ");
        StringBuilder appFIO = new StringBuilder();
        appFIO.append(sArr[0])
                .append(" ")
                .append(sArr[1].charAt(0))
                .append(".")
                .append(sArr[2].charAt(0))
                .append(".");
        return appFIO.toString();
    }

    //Задание 1. Использование метода collect()
    //Метод собирает все элементы в список, множество или другую коллекцию,
    //сгруппировывает элементы по какому-нибудь критерию, объединяет всё в строку и т.д.
    //Из списка пациентов создать Map, где ключ - дата рождения, значение - ФИО (без преобразования)
    public static Map<LocalDate, String> getDateBirtAndFio (List<Patient> patients) {
        Map<LocalDate, String> mapDateBirtAndFio =
                patients.stream()
                        .collect(Collectors.toMap(p -> p.getBirthDate(), t -> t.getFio()));
        return mapDateBirtAndFio;
    }

    //Задание 2. Использование метода forEach()
    //Аналог for each (Consumer<T> выполняет некоторое действие над объектом типа T, при этом ничего не возвращая)
    //Вывести на экран только список расходов пациентов
    public static void printExpenses (List<Patient> patients) {
        patients.forEach(p -> System.out.println("id.patient=" + p.getId() + ": " + p.getExpenses()));
    }

    //Задание 4. Использование метода findFirst()
    //Метод вытаскивает первый элемент стрима
    //Получить первого пациента, кто родился в декабре 1999 года
    public static Optional<Patient> getFirstPatient121999 (List<Patient> patients, int month, int year) {
        return patients.stream()
                .filter(p -> p.getBirthDate().getYear() == year && p.getBirthDate().getMonthValue() == month)
                .findFirst();
    }

    //Задание 6. Использование метода noneMatch()
    //Метод вернёт true, если, пройдя все элементы стрима, ни один не удовлетворил условию predicate
    //Проверить, если ли хоть 1 человек старше n лет
    public static boolean getNoneAge100(List<Patient> patients, int age) {
        return patients.stream()
                .noneMatch(p -> (LocalDate.now().getYear() - p.getBirthDate().getYear()) > age);
    }

    //Задание 7. Использование метода anyMatch()
    //Метод вернёт true, если хотя бы один элемент стрима удовлетворяет условию predicate
    //Проверить, если ли хоть 1 человек старше n лет
    public static boolean getAnyAge100(List<Patient> patients, int age) {
        return patients.stream()
                .anyMatch(p -> (LocalDate.now().getYear() - p.getBirthDate().getYear()) > age);
    }

    //Вспомогательный метод
    //Посчитать, сколько пациентов старше n лет
    public static long countAge100(List<Patient> patients, int age) {
        return patients.stream()
                .filter(p -> (LocalDate.now().getYear() - p.getBirthDate().getYear()) > age).count();
    }

    //Задание 5. Использование метода allMatch()
    //Метод вернёт true, если все элементы стрима удовлетворяют условию
    //Проверить, если ли хоть 1 здоровый человек ("здоровым" считаю пациента с суммой расходов = 0)
    public static boolean getHealthyPatient(List<Patient> patients) {
        return patients.stream()
                .allMatch(p -> ConveerOperatorConv.getAmountExpenses(p.getExpenses()) > 0);
    }

    //Задание 3. Использование методов min(), max()
    //Методы находят минимальный/максимальный элемент, основываясь на переданном компараторе
    //Получить минимального и максимального пациента из потока по сумме всех расходов пациента

    //поиск пациента с min/max расходами (компаратор описан в классе amountExpensesConparator)
    public static Optional getMinMaxPatient(List<Patient> patients, String value) {
        Optional patient = null;
        Comparator<Patient> comp = new amountExpensesConparator();

        switch (value) {
            case "min": patient = patients.stream().min(comp); break;
            case "max": patient = patients.stream().max(comp); break;
        }
        return patient;
    }

    //вспомогательный метод
    //для проверки корректности расчета max выведем на экран 3 первых пациента по убыванию суммы расходов
    public static void get3PatientByMaxAmountExpenses(List<Patient> patients) {
        System.out.println("\n\tДля контроля - первые 3 пациента по убыванию сумм расходов: ");
        Comparator<Patient> comp = new Comparator<Patient>() {
            @Override
            public int compare(Patient p1, Patient p2) {
                return ConveerOperatorConv.getAmountExpenses(p2.getExpenses()) -
                        ConveerOperatorConv.getAmountExpenses(p1.getExpenses());
            }
        };
        patients.stream().sorted(comp).limit(3).toList().forEach(p ->
                System.out.println("\tРасходы: " + p.getExpenses() + " = "
                        + ConveerOperatorConv.getAmountExpenses(p.getExpenses()) + " -> id: " + p.getId() + ", " + p.getFio()));
    }

    //вспомогательный метод
    //для проверки корректности расчета min выведем на экран 3 первых пациента по возрастанию суммы расходов
    public static void get3PatientByMinAmountExpenses(List<Patient> patients) {
        Comparator<Patient> comp = new amountExpensesConparator();
        System.out.println("\n\tДля контроля - первые 3 пациента по возрастанию сумм расходов: ");
        patients.stream().sorted(comp).limit(3).toList().forEach(p ->
                System.out.println("\tРасходы: " + p.getExpenses() + " = "
                        + ConveerOperatorConv.getAmountExpenses(p.getExpenses()) + " -> id: " + p.getId() + ", " + p.getFio()));
    }


}

