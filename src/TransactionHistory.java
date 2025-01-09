/*
** Class Name: IFT-210

** Author: Kyle Hodo

** Date Created: 06/12/2023

** Purpose: This grabs all the values entered in PortfolioManger.java
*********** stores them in the values created here in order to print all
*********** all data in a toPrint() method that fits with the corresponding
*********** transHistory method in PortfolioManager.java.
*/



public class TransactionHistory {
    private String ticker, transDate, transType;
    private double qty, costBasis;


    // overload constructor

    public TransactionHistory(String ticker, String transDate, String transType, double qty, double costBasis) {
        this.ticker = ticker;
        this.transDate = transDate;
        this.transType = transType;
        this.qty = qty;
        this.costBasis = costBasis;


    }

    // setter methods

    public void setTicker(String ticker) {
        this.ticker = ticker;

    }

    public void setTransDate(String transDate) {
        this.transDate = transDate;
    }

    public void setTransType(String transType) {
        this.transType = transType;

    }

    public void setQty(double quantity) {
        this.qty = quantity;
    }

    public void setCostBasis(double costBasis) {
        this.costBasis = costBasis;

    }

    // getter methods 

    public String getTicker() {
        return this.ticker;

    }

    public String getTransDate() {
        return this.transDate;
    }

    public String getTransType() {
        return this.transType;
    }

    public double getQty() {
        return this.qty;
    }

    public double getCostBasis() {
        return this.costBasis;
    }


    // toPrint() method



    public void toPrint() {
        System.out.printf("%-12s %-8s %-16s $%-16s %-8s\n",
         transDate, ticker, qty, costBasis, transType);
    }



}