package mir.interview.backend.domain;

public class Balance {

    private String balance;
    private String currency;

    public Balance(String balance, String currency) {
        this.balance = balance;
        this.currency = currency;
    }

    public String getBalance() {
        return this.balance;
    }

    public String getCurrency() {
        return this.currency;
    }
}
