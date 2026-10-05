import java.util.*;

class Stock {
    private String symbol;
    private double price;

    public Stock(String symbol, double price) {
        this.symbol = symbol;
        this.price = price;
    }

    public String getSymbol() {
        return symbol;
    }

    public double getPrice() {
        return price;
    }

    public void updatePrice(double newPrice) {
        this.price = newPrice;
    }
}


// User class
class User {
    private String name;
    private Portfolio portfolio;

    public User(String name, double initialBalance) {
        this.name = name;
        this.portfolio = new Portfolio(initialBalance);
    }

    public String getName() {
        return name;
    }

    public Portfolio getPortfolio() {
        return portfolio;
    }
}


// Transaction class
class Transaction {
    private String type;
    private String symbol;
    private int quantity;
    private double price;

    public Transaction(String type, String symbol, int quantity, double price) {
        this.type = type;
        this.symbol = symbol;
        this.quantity = quantity;
        this.price = price;
    }

    public String getType() {
        return type;
    }

    public String getSymbol() {
        return symbol;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getPrice() {
        return price;
    }
}


// Portfolio class
class Portfolio {
    private Map<String, Integer> holdings;
    private double balance;
    private double initialBalance;

    public Portfolio(double initialBalance) {
        this.balance = initialBalance;
        this.holdings = new HashMap<>();
        this.initialBalance = initialBalance;
    }

    public double getBalance() {
        return balance;
    }

    public void buyStock(Stock stock, int quantity) {

        // Quantity validation
        if (quantity <= 0) {
            System.out.println("Quantity must be greater than 0.");
            return;
        }

        double cost = stock.getPrice() * quantity;

        if (cost <= balance) {

            balance -= cost;

            holdings.put(
                stock.getSymbol(),
                holdings.getOrDefault(stock.getSymbol(), 0) + quantity
            );

            System.out.println(
                "Bought " + quantity + " shares of "
                + stock.getSymbol()
            );

        } else {
            System.out.println("Not enough balance to buy.");
        }
    }

    public void sellStock(Stock stock, int quantity) {

        // Quantity validation
        if (quantity <= 0) {
            System.out.println("Quantity must be greater than 0.");
            return;
        }

        if (holdings.containsKey(stock.getSymbol())
                && holdings.get(stock.getSymbol()) >= quantity) {

            double revenue = stock.getPrice() * quantity;

            balance += revenue;

            holdings.put(
                stock.getSymbol(),
                holdings.get(stock.getSymbol()) - quantity
            );

            System.out.println(
                "Sold " + quantity + " shares of "
                + stock.getSymbol()
            );

        } else {
            System.out.println(
                "You don’t own enough shares to sell."
            );
        }
    }


    // Display Portfolio
    public void displayPortfolio(
            Map<String, Stock> market,
            String userName) {

        System.out.println("\n========== Portfolio ==========");
        System.out.println("User: " + userName);

        double totalValue = balance;

        System.out.println("+------------+----------+------------+");
        System.out.println("| Stock      | Quantity | Total Value|");
        System.out.println("+------------+----------+------------+");

        for (String symbol : holdings.keySet()) {

            int qty = holdings.get(symbol);

            double stockValue =
                qty * market.get(symbol).getPrice();

            totalValue += stockValue;

            System.out.printf(
                "| %-10s | %-8d | $%-9.2f |%n",
                symbol,
                qty,
                stockValue
            );
        }

        System.out.println("+------------+----------+------------+");

        System.out.printf(
            "Cash Balance          : $%.2f%n",
            balance
        );

        System.out.printf(
            "Total Portfolio Value : $%.2f%n",
            totalValue
        );

        // Portfolio performance
        double profitLoss =
            totalValue - initialBalance;

        System.out.printf(
            "Profit / Loss         : $%.2f%n",
            profitLoss
        );

        if (profitLoss > 0) {

            System.out.println(
                "Status                : Profit"
            );

        } else if (profitLoss < 0) {

            System.out.println(
                "Status                : Loss"
            );

        } else {

            System.out.println(
                "Status                : No Profit / No Loss"
            );
        }
    }
}


public class StockTradingPlatform {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        // Get user name dynamically
        System.out.print("Enter your name: ");
        String userName = sc.nextLine();


        // Create market data
        Map<String, Stock> market =
            new HashMap<>();

        market.put(
            "AAPL",
            new Stock("AAPL", 150.0)
        );

        market.put(
            "GOOGL",
            new Stock("GOOGL", 2800.0)
        );

        market.put(
            "TSLA",
            new Stock("TSLA", 700.0)
        );


        // Create user
        User user =
            new User(userName, 5000.0);

        Portfolio portfolio =
            user.getPortfolio();


        while (true) {

            System.out.println(
                "\n=== Stock Trading Platform ==="
            );

            System.out.println(
                "User: " + user.getName()
            );

            System.out.println(
                "1. View Market Data"
            );

            System.out.println(
                "2. Buy Stock"
            );

            System.out.println(
                "3. Sell Stock"
            );

            System.out.println(
                "4. View Portfolio"
            );

            System.out.println(
                "5. Update Stock Price"
            );

            System.out.println(
                "6. Exit"
            );

            System.out.print(
                "Choose an option: "
            );

            int choice = sc.nextInt();


            switch (choice) {

                // View Market Data
                case 1:

                    System.out.println(
                        "\n========== Market Data =========="
                    );

                    System.out.println(
                        "+------------+------------+"
                    );

                    System.out.println(
                        "| Stock      | Price      |"
                    );

                    System.out.println(
                        "+------------+------------+"
                    );

                    for (Stock s : market.values()) {

                        System.out.printf(
                            "| %-10s | $%-9.2f |%n",
                            s.getSymbol(),
                            s.getPrice()
                        );
                    }

                    System.out.println(
                        "+------------+------------+"
                    );

                    break;


                // Buy Stock
                case 2:

                    System.out.print(
                        "Enter stock symbol to buy: "
                    );

                    String buySymbol =
                        sc.next().toUpperCase();

                    if (market.containsKey(buySymbol)) {

                        System.out.print(
                            "Enter quantity: "
                        );

                        int qty = sc.nextInt();

                        Stock stock =
                            market.get(buySymbol);

                        portfolio.buyStock(
                            stock,
                            qty
                        );

                        // Create transaction
                        if (qty > 0) {

                            Transaction transaction =
                                new Transaction(
                                    "BUY",
                                    buySymbol,
                                    qty,
                                    stock.getPrice()
                                );
                        }

                    } else {

                        System.out.println(
                            "Stock not found."
                        );
                    }

                    break;


                // Sell Stock
                case 3:

                    System.out.print(
                        "Enter stock symbol to sell: "
                    );

                    String sellSymbol =
                        sc.next().toUpperCase();

                    if (market.containsKey(sellSymbol)) {

                        System.out.print(
                            "Enter quantity: "
                        );

                        int qty = sc.nextInt();

                        Stock stock =
                            market.get(sellSymbol);

                        portfolio.sellStock(
                            stock,
                            qty
                        );

                        // Create transaction
                        if (qty > 0) {

                            Transaction transaction =
                                new Transaction(
                                    "SELL",
                                    sellSymbol,
                                    qty,
                                    stock.getPrice()
                                );
                        }

                    } else {

                        System.out.println(
                            "Stock not found."
                        );
                    }

                    break;


                // View Portfolio
                case 4:

                    portfolio.displayPortfolio(
                        market,
                        user.getName()
                    );

                    break;


                // Update Stock Price
                case 5:

                    System.out.print(
                        "Enter stock symbol to update: "
                    );

                    String updateSymbol =
                        sc.next().toUpperCase();

                    if (market.containsKey(updateSymbol)) {

                        System.out.print(
                            "Enter new price: "
                        );

                        double newPrice =
                            sc.nextDouble();

                        if (newPrice > 0) {

                            market.get(updateSymbol)
                                  .updatePrice(newPrice);

                            System.out.println(
                                "Price updated successfully."
                            );

                        } else {

                            System.out.println(
                                "Price must be greater than 0."
                            );
                        }

                    } else {

                        System.out.println(
                            "Stock not found."
                        );
                    }

                    break;


                // Exit
                case 6:

                    System.out.println(
                        "Exiting Stock Trading Platform..."
                    );

                    sc.close();
                    return;


                default:

                    System.out.println(
                        "Invalid option."
                    );
            }
        }
    }
}