package com.example.fernando.myfirstapp;

public class CC {
    Long number;
    String expireDate;
    Integer CCV_num;
    Integer Zip;
    String cardnum;

    public CC (Long number, String expireDate, Integer CCV_num, Integer Zip){
        this.number = number;
        this.expireDate = expireDate;
        this.CCV_num = CCV_num;
        this.Zip = Zip;
        String num = number.toString();
        int length = num.length();
        cardnum = "Credit Card x-" + num.substring(length - 4);
    }

    public String getCardnum() {
        return cardnum;
    }
}
