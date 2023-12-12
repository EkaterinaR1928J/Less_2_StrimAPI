import java.util.Comparator;
import java.util.List;

// Конвеерные (промежуточные) операторы: filter()
public class ConveerOperatorConv {
    //получить список клиентов компании ВСК 1999 года рождения
    public static List<Patient> filterVSK1999(List<Patient> pats, String company, int year) {
        return pats.stream()
                .filter(patient -> patient.getCompany().equals(company) && patient.getBirthDate().getYear() == year)
                .toList();
    }

    //Получить список "здоровых" пациентов ("здоровым" считаю пациента с суммой расходов = 0)
    public static List<Patient> getListHealthyPatient(List<Patient> patients, int amount) {
        return  patients.stream()
                        .filter(p -> getAmountExpenses(p.getExpenses()) <= amount).toList();
    }
    public static int getAmountExpenses(List<Integer> expenses) {
        return expenses.stream()
                .reduce(0, (acc, p) -> acc + p);
    }


}
