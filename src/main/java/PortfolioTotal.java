import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("jsonschema2pojo")
public class PortfolioTotal {

    @SerializedName("totalCap")
    @Expose
    private TotalCap totalCap;
    @SerializedName("initialCap")
    @Expose
    private InitialCap initialCap;
    @SerializedName("profit")
    @Expose
    private Profit profit;
    @SerializedName("change")
    @Expose
    private Change change;
    @SerializedName("change24hPercent")
    @Expose
    private Change24hPercent change24hPercent;
    @SerializedName("change24hAbsolute")
    @Expose
    private Change24hAbsolute change24hAbsolute;
    @SerializedName("hideAmounts")
    @Expose
    private Boolean hideAmounts;
    @SerializedName("totalRealizedProfit")
    @Expose
    private TotalRealizedProfit totalRealizedProfit;

    public TotalCap getTotalCap() {
        return totalCap;
    }

    public void setTotalCap(TotalCap totalCap) {
        this.totalCap = totalCap;
    }

    public InitialCap getInitialCap() {
        return initialCap;
    }

    public void setInitialCap(InitialCap initialCap) {
        this.initialCap = initialCap;
    }

    public Profit getProfit() {
        return profit;
    }

    public void setProfit(Profit profit) {
        this.profit = profit;
    }

    public Change getChange() {
        return change;
    }

    public void setChange(Change change) {
        this.change = change;
    }

    public Change24hPercent getChange24hPercent() {
        return change24hPercent;
    }

    public void setChange24hPercent(Change24hPercent change24hPercent) {
        this.change24hPercent = change24hPercent;
    }

    public Change24hAbsolute getChange24hAbsolute() {
        return change24hAbsolute;
    }

    public void setChange24hAbsolute(Change24hAbsolute change24hAbsolute) {
        this.change24hAbsolute = change24hAbsolute;
    }

    public Boolean getHideAmounts() {
        return hideAmounts;
    }

    public void setHideAmounts(Boolean hideAmounts) {
        this.hideAmounts = hideAmounts;
    }

    public TotalRealizedProfit getTotalRealizedProfit() {
        return totalRealizedProfit;
    }

    public void setTotalRealizedProfit(TotalRealizedProfit totalRealizedProfit) {
        this.totalRealizedProfit = totalRealizedProfit;
    }

}
