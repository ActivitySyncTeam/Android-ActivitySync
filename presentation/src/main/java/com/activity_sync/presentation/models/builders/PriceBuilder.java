package com.activity_sync.presentation.models.builders;

import com.activity_sync.presentation.models.Price;

public class PriceBuilder
{
    private double amount;
    private String currency;

    public PriceBuilder setAmount(double amount)
    {
        this.amount = amount;
        return this;
    }

    public PriceBuilder setCurrency(String currency)
    {
        this.currency = currency;
        return this;
    }

    public Price createPrice()
    {
        return new Price(amount, currency);
    }
}
