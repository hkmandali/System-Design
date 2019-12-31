package SystemDesign.Splitwise.MainProgram;
// this has been taken directly from work at tech and has not been implemented
import SystemDesign.Splitwise.Expense.ExpenseType;
import SystemDesign.Splitwise.HelperServices.ExpenseManager;
import SystemDesign.Splitwise.Split.EqualSplit;
import SystemDesign.Splitwise.Split.ExactSplit;
import SystemDesign.Splitwise.Split.PercentSplit;
import SystemDesign.Splitwise.Split.Split;
import SystemDesign.Splitwise.User.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Starter {
    public static void main(String[] args) {
    ExpenseManager expenseManager = new ExpenseManager();

    expenseManager.addUser(new User("u1", "Hemu","1234"));
    expenseManager.addUser(new User("u2", "Teja", "1234"));
    expenseManager.addUser(new User("u3", "renu", "1234"));
    expenseManager.addUser(new User("u4", "sravani",  "1234"));

    Scanner scanner = new Scanner(System.in);
        while (true) {
        String command = scanner.nextLine();
        String[] commands = command.split(" ");
        String commandType = commands[0];

        switch (commandType) {
            case "SHOW":
                if (commands.length == 1) {
                    expenseManager.showBalances();
                } else {
                    expenseManager.showBalance(commands[1]);
                }
                break;
            case "EXPENSE":
                String paidBy = commands[1];
                Double amount = Double.parseDouble(commands[2]);
                int noOfUsers = Integer.parseInt(commands[3]);
                String expenseType = commands[4 + noOfUsers];
                List<Split> splits = new ArrayList<>();
                switch (expenseType) {
                    case "EQUAL":
                        for (int i = 0; i < noOfUsers; i++) {
                            splits.add(new EqualSplit(expenseManager.userMap.get(commands[4 + i])));
                        }
                        expenseManager.addExpense(ExpenseType.EQUAL, amount, paidBy, splits, null);
                        break;
                    case "EXACT":
                        for (int i = 0; i < noOfUsers; i++) {
                            splits.add(new ExactSplit(expenseManager.userMap.get(commands[4 + i]), Double.parseDouble(commands[5 + noOfUsers + i])));
                        }
                        expenseManager.addExpense(ExpenseType.EXACT, amount, paidBy, splits, null);
                        break;
                    case "PERCENT":
                        for (int i = 0; i < noOfUsers; i++) {
                            splits.add(new PercentSplit(expenseManager.userMap.get(commands[4 + i]), Double.parseDouble(commands[5 + noOfUsers + i])));
                        }
                        expenseManager.addExpense(ExpenseType.PERCENT, amount, paidBy, splits, null);
                        break;
                }
                break;
        }
    }
}
}
