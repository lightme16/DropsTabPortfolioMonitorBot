import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("jsonschema2pojo")
public class TotalRealizedProfit {

    @SerializedName("USD")
    @Expose
    private Double usd;
    @SerializedName("BTC")
    @Expose
    private Double btc;
    @SerializedName("ETH")
    @Expose
    private Double eth;
    @SerializedName("BNB")
    @Expose
    private Double bnb;

    public Double getUsd() {
        return usd;
    }

    public void setUsd(Double usd) {
        this.usd = usd;
    }

    public Double getBtc() {
        return btc;
    }

    public void setBtc(Double btc) {
        this.btc = btc;
    }

    public Double getEth() {
        return eth;
    }

    public void setEth(Double eth) {
        this.eth = eth;
    }

    public Double getBnb() {
        return bnb;
    }

    public void setBnb(Double bnb) {
        this.bnb = bnb;
    }

}
