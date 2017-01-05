package mir.interview.backend.domain;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Spend {

    private Date date;
    private String description;
    private Double amount;
    private String currency;

    public Spend(@JsonProperty("date") Date date,
                 @JsonProperty("description") String description,
                 @JsonProperty("amount") Double amount,
                 @JsonProperty("currency") String currency) {

        this.date = date;
        this.description = description;
        this.amount = amount;
        this.currency = currency;
    }

    public Date getDate() {
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
}
