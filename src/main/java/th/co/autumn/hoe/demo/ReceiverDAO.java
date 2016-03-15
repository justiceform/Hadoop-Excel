package th.co.autumn.hoe.demo;

import org.apache.hadoop.io.ObjectWritable;
import org.apache.hadoop.io.Text;

/**
 * Created by tek on 3/16/2016 AD.
 */
public class ReceiverDAO extends ObjectWritable {

    public static String currentDate ;

    public ReceiverDAO(String bank, String bus, String amount) {
        this.bank = bank;
        this.bus = bus;
        this.amount = amount;

    }

    private String bank ;
    private String date ;
    private String bus ;
    private String amount ;

    public String getCurrentDate(){
        return this.currentDate;
    }
    public String getBank() {
        return bank;
    }

    public void setBank(String bank) {
        this.bank = bank;
    }

    public String getBus() {
        return bus;
    }

    public void setBus(String bus) {
        this.bus = bus;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
