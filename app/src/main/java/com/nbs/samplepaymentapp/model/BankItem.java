package com.nbs.samplepaymentapp.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Sidiq on 10/03/2016.
 */
public class BankItem {
    @SerializedName("bank_name")
    public String bankName;

    @SerializedName("account_number")
    public String accountNumber;

    @SerializedName("account_name")
    public String accountName;

    @SerializedName("bank_branch")
    public String bankBranch;
}
