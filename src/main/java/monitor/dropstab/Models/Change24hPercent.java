package monitor.dropstab.Models;

import javax.annotation.processing.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("jsonschema2pojo")
public class Change24hPercent {

    @SerializedName("USD")
    @Expose
    private String usd;
    @SerializedName("BTC")
    @Expose
    private String btc;
    @SerializedName("ETH")
    @Expose
    private String eth;
    @SerializedName("BNB")
    @Expose
    private String bnb;

    public String getUsd() {
        return usd;
    }

    public void setUsd(String usd) {
        this.usd = usd;
    }

    public String getBtc() {
        return btc;
    }

    public void setBtc(String btc) {
        this.btc = btc;
    }

    public String getEth() {
        return eth;
    }

    public void setEth(String eth) {
        this.eth = eth;
    }

    public String getBnb() {
        return bnb;
    }

    public void setBnb(String bnb) {
        this.bnb = bnb;
    }

}
