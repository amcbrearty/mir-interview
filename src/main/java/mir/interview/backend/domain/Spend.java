package mir.interview.backend.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Spend {

    private String date;
    private String description;
    private Double amount;
    private String currency;

    public Spend(@JsonProperty("date") String date,
                 @JsonProperty("description") String description,
                 @JsonProperty("amount") Double amount,
                 @JsonProperty("currency") String currency) {

        this.date = date;
        this.description = description;
        this.amount = amount;
        this.currency = currency;
    }

    public String getDate() {
        return this.date;
    }

    public String getDescription() {
        return this.description;
    }

    public Double getAmount() {
        return this.amount;
    }

    public String getCurrency() {
        return this.currency;
    }

    @Override
    public String toString() {
        return this.getDescription();
    }
}
