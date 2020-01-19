import java.util.List;
import java.util.ArrayList;

public class Strategy {

    @FunctionalInterface
    interface BillingStrategy {
        int calculatePrice(final int rawPrice);

        static BillingStrategy happyHour() {
            return (rawPrice) -> rawPrice / 2;
        }

        static BillingStrategy normal() {
            return (rawPrice) -> rawPrice;
        }
    }

    static class Customer {

        private final List<Integer> drinks = new ArrayList<>();
        private BillingStrategy strategy;

        public Customer(final BillingStrategy billingStrategy) {
            this.strategy = billingStrategy;
        }

        public void setBillingStrategy(final BillingStrategy billingStrategy) {
            this.strategy = billingStrategy;
        }

        public void addDrink(final int rawPrice) {
            addDrink(rawPrice, 1);
        }

        public void addDrink(final int rawPrice, final int quantity) {
            drinks.add(strategy.calculatePrice(quantity * rawPrice));
        }

        public void printBill() {
            final int sum = drinks.stream().reduce(0, Integer::sum);
            System.out.println(String.format("Total due: %.02f", sum / 100.0));
            drinks.clear();
        }
    }

    private Strategy() {
        throw new AssertionError();
    }

    public static void main(String[] args) {
        final var customer = new Customer(BillingStrategy.normal());
        customer.addDrink(4);
        customer.addDrink(4, 2);

        customer.setBillingStrategy(BillingStrategy.happyHour());
        customer.addDrink(10, 2);

        customer.printBill();
    }
}