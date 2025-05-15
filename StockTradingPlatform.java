import java.util.*;

class Stock {
    String symbol;
    double price;

    public Stock(String symbol, double price) {
        this.symbol = symbol;
        this.price = price;
    }

    // Simulate a change in the stock price (fluctuates randomly)
    public void updatePrice() {
        double change = price * (Math.random() * 0.2 - 0.1); // +/- 10%
        price += change;
        price = Math.round(price * 100.0) / 100.0; // Round to 2 decimal places
    }
}

class Portfolio {
    Map<String, Integer> ownedStocks = new HashMap<>();
    double availableCash = 10000.00; // Starting balance 💰

    // Buy stocks if you have enough cash
    public void buyStock(String symbol, int quantity, double price) {
        double totalCost = price * quantity;

        if (availableCash >= totalCost) {
            ownedStocks.put(symbol, ownedStocks.getOrDefault(symbol, 0) + quantity);
            availableCash -= totalCost;
            System.out.printf("✅ Bought %d shares of %s at Rs%.2f each. Remaining Cash: Rs%.2f%n",
                              quantity, symbol, price, availableCash);
        } else {
            System.out.println("❌ Not enough cash for that purchase.");
        }
    }

    // Sell stocks if you own enough
    public void sellStock(String symbol, int quantity, double price) {
        int owned = ownedStocks.getOrDefault(symbol, 0);

        if (owned >= quantity) {
            ownedStocks.put(symbol, owned - quantity);
            availableCash += price * quantity;
            System.out.printf("💸 Sold %d shares of %s at Rs%.2f each. New Cash Balance: Rs%.2f%n",
                              quantity, symbol, price, availableCash);
        } else {
            System.out.println("❌ You don't own that many shares to sell.");
        }
    }

    // Show current portfolio summary
    public void viewPortfolio(Map<String, Stock> market) {
        System.out.println("\n📊 --- Your Portfolio ---");
        for (String symbol : ownedStocks.keySet()) {
            int shares = ownedStocks.get(symbol);
            double currentPrice = market.get(symbol).price;
            double value = shares * currentPrice;
            System.out.printf("%s: %d shares @ Rs%.2f = Rs%.2f%n", symbol, shares, currentPrice, value);
        }
        System.out.printf("💵 Available Cash: Rs%.2f%n", availableCash);
    }
}

public class StockTradingPlatform {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Portfolio myPortfolio = new Portfolio();

        // Simulate a mini stock market
        Map<String, Stock> market = new HashMap<>();
        market.put("AAPL", new Stock("AAPL", 150.00));
        market.put("GOOG", new Stock("GOOG", 2800.00));
        market.put("TSLA", new Stock("TSLA", 700.00));

        System.out.println("📈 Welcome to the Simulated Stock Trading Platform!");
        System.out.println("You have Rs10,000 to start trading.\n");

        boolean running = true;

        while (running) {
            // Update stock prices
            for (Stock stock : market.values()) {
                stock.updatePrice();
            }

            System.out.println("\n📉 Market Snapshot:");
            for (Stock stock : market.values()) {
                System.out.printf("%s: Rs%.2f%n", stock.symbol, stock.price);
            }

            // Menu
            System.out.println("\nWhat would you like to do?");
            System.out.println("1. Buy Stock");
            System.out.println("2. Sell Stock");
            System.out.println("3. View Portfolio");
            System.out.println("4. Exit");
            System.out.print("Choose an option: ");

            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    System.out.print("Enter stock symbol to buy: ");
                    String buySymbol = scanner.nextLine().toUpperCase();
                    if (market.containsKey(buySymbol)) {
                        System.out.print("Enter number of shares: ");
                        int buyQty = Integer.parseInt(scanner.nextLine());
                        myPortfolio.buyStock(buySymbol, buyQty, market.get(buySymbol).price);
                    } else {
                        System.out.println("❌ That stock is not in our market.");
                    }
                    break;

                case "2":
                    System.out.print("Enter stock symbol to sell: ");
                    String sellSymbol = scanner.nextLine().toUpperCase();
                    if (market.containsKey(sellSymbol)) {
                        System.out.print("Enter number of shares: ");
                        int sellQty = Integer.parseInt(scanner.nextLine());
                        myPortfolio.sellStock(sellSymbol, sellQty, market.get(sellSymbol).price);
                    } else {
                        System.out.println("❌ That stock is not in our market.");
                    }
                    break;

                case "3":
                    myPortfolio.viewPortfolio(market);
                    break;

                case "4":
                    running = false;
                    System.out.println("👋 Thanks for using the trading platform. Happy investing!");
                    break;

                default:
                    System.out.println("❌ Invalid option. Please try again.");
            }
        }

        scanner.close();
    }
}
