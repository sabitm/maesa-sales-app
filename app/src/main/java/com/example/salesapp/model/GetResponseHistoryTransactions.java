package com.example.salesapp.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GetResponseHistoryTransactions {
    @SerializedName("transaction")
    @Expose
    private List<HistoryTransactions> historyTransactions = null;

    public List<HistoryTransactions> getHistoryTransactions() {
        return historyTransactions;
    }

    public void setHistoryTransactions(List<HistoryTransactions> historyTransactions) {
        this.historyTransactions = historyTransactions;
    }
}
