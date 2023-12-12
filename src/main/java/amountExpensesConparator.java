import java.util.Comparator;

//Компаратор по сумме расходов
public class amountExpensesConparator implements Comparator<Patient> {

    @Override
    public int compare(Patient p1, Patient p2) {
        return ConveerOperatorConv.getAmountExpenses(p1.getExpenses()) -
                        ConveerOperatorConv.getAmountExpenses(p2.getExpenses());
    }
}
