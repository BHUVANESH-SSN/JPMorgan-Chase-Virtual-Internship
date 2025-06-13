package com.jpmc.midascore.component;

import com.jpmc.midascore.entity.UserRecord;
import com.jpmc.midascore.foundation.Incentive;
import com.jpmc.midascore.foundation.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class TransactionHandler {
    static final Logger logger = LoggerFactory.getLogger(TransactionHandler.class);
    private final DatabaseConduit databaseConduit;
    private final IncentiveQuerier incentiveQuerier;

    public TransactionHandler(DatabaseConduit databaseConduit, IncentiveQuerier incentiveQuerier) {
        this.databaseConduit = databaseConduit;
        this.incentiveQuerier = incentiveQuerier;
    }

    public void handleTransaction(Transaction transaction) {
        if (databaseConduit.isValid(transaction)) {
            Incentive incentive = incentiveQuerier.query(transaction);
            transaction.setIncentive(incentive.getAmount());
            databaseConduit.save(transaction);
        }
    }
}
