import java.util.*;
import java.io.*;

class Expense {
    String title;
    double amount;
    String category;
    String date;

    public Expense(String title, double amount, String category, String date) {
        this.title = title;
        this.amount = amount;
        this.category = category;
        this.date = date;
    }

    public String toString() {
        return title + " | Rs." + amount + " | " + category + " | " + date;
    }
}

public class ExpenseTracker {
    static ArrayList<Expense> expenses = new ArrayList<>();

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // Load saved data
        loadFromFile();

        while (true) {
            System.out.println("\n===== Expense Tracker =====");
            System.out.println("1. Add Expense");
            System.out.println("2. View Expenses");
            System.out.println("3. Total Spending");
            System.out.println("4. Delete Expense");
            System.out.println("5. Filter by Category");
            System.out.println("6. Exit");
            System.out.print("Choose: ");

            int choice;

            try {
                choice = sc.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Invalid input! Enter a number.");
                sc.nextLine(); // clear wrong input
                continue;
            }

            sc.nextLine(); // clear buffer

            switch (choice) {
                case 1:
                    addExpense(sc);
                    break;
                case 2:
                    viewExpenses();
                    break;
                case 3:
                    totalSpending();
                    break;
                case 4:
                    deleteExpense(sc);
                    break;
                case 5:
                    filterByCategory(sc);
                    break;
                case 6:
                    System.out.println("Exiting...");
                    System.exit(0);
                default:
                    System.out.println("Invalid choice!");
            }
        }
    }

    static void addExpense(Scanner sc) {
        System.out.print("Enter title: ");
        String title = sc.nextLine();

        System.out.print("Enter amount: ");
        double amount;

        try {
            amount = sc.nextDouble();
        } catch (InputMismatchException e) {
            System.out.println("Invalid amount!");
            sc.nextLine();
            return;
        }

        sc.nextLine();

        System.out.print("Enter category: ");
        String category = sc.nextLine();

        System.out.print("Enter date (dd-mm-yyyy): ");
        String date = sc.nextLine();

        expenses.add(new Expense(title, amount, category, date));
        saveToFile();

        System.out.println("✅ Expense Added!");
    }

    static void viewExpenses() {
        if (expenses.isEmpty()) {
            System.out.println("No expenses yet!");
            return;
        }

        System.out.println("\n--- All Expenses ---");
        for (int i = 0; i < expenses.size(); i++) {
            System.out.println(i + ". " + expenses.get(i));
        }
    }

    static void totalSpending() {
        double total = 0;
        for (Expense e : expenses) {
            total += e.amount;
        }
        System.out.println("💰 Total Spending: Rs." + total);
    }

    static void deleteExpense(Scanner sc) {
        viewExpenses();

        if (expenses.isEmpty()) return;

        System.out.print("Enter index to delete: ");
        int index;

        try {
            index = sc.nextInt();
        } catch (InputMismatchException e) {
            System.out.println("Invalid input!");
            sc.nextLine();
            return;
        }

        sc.nextLine();

        if (index >= 0 && index < expenses.size()) {
            expenses.remove(index);
            saveToFile();
            System.out.println("Expense deleted!");
        } else {
            System.out.println("Invalid index!");
        }
    }

    static void filterByCategory(Scanner sc) {
        System.out.print("Enter category: ");
        String cat = sc.nextLine();

        boolean found = false;

        System.out.println("\n--- Filtered Expenses ---");
        for (Expense e : expenses) {
            if (e.category.equalsIgnoreCase(cat) || e.title.equalsIgnoreCase(cat)) {
                System.out.println(e);
                found = true;
            }
        }

        if (!found) {
            System.out.println("No expenses found.");
        }
    }

    // Save data
    static void saveToFile() {
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter("expenses.txt"));

            for (Expense e : expenses) {
                bw.write(e.title + "," + e.amount + "," + e.category + "," + e.date);
                bw.newLine();
            }

            bw.close();
        } catch (IOException e) {
            System.out.println("Error saving data.");
        }
    }

    // Load data
    static void loadFromFile() {
        try {
            BufferedReader br = new BufferedReader(new FileReader("expenses.txt"));
            String line;

            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");

                expenses.add(new Expense(
                        data[0],
                        Double.parseDouble(data[1]),
                        data[2],
                        data[3]
                ));
            }

            br.close();
        } catch (IOException e) {
            System.out.println("No previous data found.");
        }
    }
}