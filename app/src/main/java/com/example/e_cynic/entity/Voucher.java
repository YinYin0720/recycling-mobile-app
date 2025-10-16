package com.example.e_cynic.entity;

public class Voucher
{
    public Integer voucherImage;
    public String voucherName;
    public Integer voucherPoints;
    public Long redeemDate;

    public Voucher(Integer voucherImage)
    {
        this.voucherImage = voucherImage;
    }

    public Voucher(Integer voucherImage, String voucherName, Integer voucherPoints)
    {
        this.voucherImage = voucherImage;
        this.voucherName = voucherName;
        this.voucherPoints = voucherPoints;
    }

    @Override
    public String toString()
    {
        return "Voucher{" +
                "voucherImage=" + voucherImage +
                ", voucherName='" + voucherName + '\'' +
                ", voucherPoints=" + voucherPoints +
                ", redeemDate=" + redeemDate +
                '}';
    }

}
