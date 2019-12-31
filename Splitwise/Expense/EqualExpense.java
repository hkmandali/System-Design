package SystemDesign.Splitwise.Expense;

import SystemDesign.Splitwise.Split.EqualSplit;
import SystemDesign.Splitwise.Split.Split;
import SystemDesign.Splitwise.User.User;

import java.util.List;

public class EqualExpense extends Expense {
    public EqualExpense(double amount, User paidBy, List<Split> splits, ExpenseMetadata expenseMetadata) {
        super(amount, paidBy, splits, expenseMetadata);
    }

    @Override
    public boolean validate() {
        for (Split split : getSplits()) {
            if (!(split instanceof EqualSplit)) {
                return false;
            }
        }

        return true;
    }
}
