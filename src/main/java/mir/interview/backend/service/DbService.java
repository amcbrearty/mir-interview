package mir.interview.backend.service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.aerospike.client.AerospikeClient;
import com.aerospike.client.Bin;
import com.aerospike.client.Key;
import com.aerospike.client.Record;
import com.aerospike.client.query.Filter;
import com.aerospike.client.query.IndexType;
import com.aerospike.client.query.RecordSet;
import com.aerospike.client.query.Statement;

import mir.interview.backend.domain.Balance;
import mir.interview.backend.domain.Spend;

public class DbService {

    // replace with externalised configuration for port & server
    private static final int serverPort = 3000;
    private static final String serverHost = "172.28.128.3";

    // replace with externalised configuration for currency & balance
    private static final String PRESET_CURRENCY = "GBP";
    private static final Double PRESET_BALANCE = Double.valueOf("2000.0");

    // namespace change in the aerospace configuration
    private static final String NAMESPACE = "test";

    private static final String ACCOUNT_SET = "account";
    private static final String SPEND_SET = "spend";

    private static final String BALANCE_BIN = "balance";
    private static final String UUID_BIN = "uuid";
    private static final String DATE_BIN = "date";
    private static final String AMOUNT_BIN = "amount";
    private static final String DESCRIPTION_BIN = "description";
    private static final String CURRENCY_BIN = "currency";

    private AerospikeClient aerospikeClient;

    public DbService() {
        aerospikeClient = new AerospikeClient(serverHost, serverPort);
        aerospikeClient.createIndex(null, NAMESPACE, SPEND_SET, "spend_account_idx", UUID_BIN, IndexType.STRING);
    }

    public void createAccount(String accountUuid) {
        Key accountKey = new Key(NAMESPACE, ACCOUNT_SET, accountUuid);
        Bin accountBalance = new Bin(BALANCE_BIN, PRESET_BALANCE);
        Bin accountCurrency = new Bin(CURRENCY_BIN, PRESET_CURRENCY);

        aerospikeClient.put(null, accountKey, accountBalance, accountCurrency);
    }

    public Balance findBalance(String accountUuid) {
        return this.findBalance(new Key(NAMESPACE, ACCOUNT_SET, accountUuid));
    }

    public Balance findBalance(Key accountKey) {
        Record record = aerospikeClient.get(null, accountKey);

        return new Balance(record.bins.get(BALANCE_BIN).toString(), record.bins.get(CURRENCY_BIN).toString());
    }

    public Double updateBalance(String accountUuid, Double spendAmount) {
        Key accountKey = new Key(NAMESPACE, ACCOUNT_SET, accountUuid);
        Record balanceRecord = aerospikeClient.get(null, accountKey);

        Double currentBalance = Double.parseDouble(balanceRecord.bins.get(BALANCE_BIN).toString());
        Double updatedBalance = currentBalance - spendAmount;

        Bin balanceBin = new Bin(BALANCE_BIN, updatedBalance);

        aerospikeClient.put(null, accountKey, balanceBin);

        return updatedBalance;
    }

    public void addSpend(String accountUuid, Spend spend) {
        Key spendKey = new Key(NAMESPACE, SPEND_SET, UUID.randomUUID().toString());

        Bin accountBin = new Bin(UUID_BIN, accountUuid);
        Bin dateBin = new Bin(DATE_BIN, spend.getDate());
        Bin descriptionBin = new Bin(DESCRIPTION_BIN, spend.getDescription());
        Bin amountBin = new Bin(AMOUNT_BIN, spend.getAmount());
        Bin currencyBin = new Bin(CURRENCY_BIN, spend.getCurrency());

        aerospikeClient.put(null, spendKey, accountBin, dateBin, descriptionBin, amountBin, currencyBin);
    }

    public List<Spend> findTransactions(String accountUuid) {
        Statement transactionQuery = new Statement();

        transactionQuery.setNamespace(NAMESPACE);
        transactionQuery.setSetName(SPEND_SET);
        transactionQuery.setFilters(Filter.equal(UUID_BIN, accountUuid));

        RecordSet transactionQueryRs = aerospikeClient.query(null, transactionQuery);

        List<Spend> transactions = new ArrayList();

        transactionQueryRs.forEach(items -> {
            String date = items.record.bins.get(DATE_BIN).toString();
            String description = items.record.bins.get(DESCRIPTION_BIN).toString();
            Double amount = Double.valueOf(items.record.bins.get(AMOUNT_BIN).toString());
            String currency = items.record.bins.get(CURRENCY_BIN).toString();

            Spend spend = new Spend(date, description, amount, currency);

            transactions.add(spend);
        });

        return transactions;
    }

    public void closeDb() {
        aerospikeClient.close();
    }
}
