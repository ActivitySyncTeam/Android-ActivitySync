package com.activity_sync.presentation.models;

import com.google.gson.annotations.SerializedName;

public class Price
{
    @SerializedName("amount")
    private double amount;

    @SerializedName("currency")
    private String currency;

    public Price(double amount, String currency)
    {
        this.amount = amount;
        this.currency = currency;
    }

    public Price()
    {

    }

    public double getAmount()
    {
        return amount;
    }

    public void setAmount(double amount)
    {
        this.amount = amount;
    }

    public String getCurrency()
    {
        return currency;
    }

    public void setCurrency(String currency)
    {
        this.currency = currency;
    }
}
