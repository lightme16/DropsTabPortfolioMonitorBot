package monitor.icodrops.Models;

import java.util.List;
import javax.annotation.processing.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import monitor.icodrops.Models.PortfolioGroup;

@Generated("jsonschema2pojo")
public class ShortPortfolio {

    @SerializedName("username")
    @Expose
    private String username;
    @SerializedName("btcPriceUsd")
    @Expose
    private String btcPriceUsd;
    @SerializedName("btcChange24h")
    @Expose
    private String btcChange24h;
    @SerializedName("ethPriceUsd")
    @Expose
    private String ethPriceUsd;
    @SerializedName("ethChange24h")
    @Expose
    private String ethChange24h;
    @SerializedName("btcDominance")
    @Expose
    private String btcDominance;
    @SerializedName("globalInitialType")
    @Expose
    private String globalInitialType;
    @SerializedName("portfolioGroups")
    @Expose
    private List<PortfolioGroup> portfolioGroups = null;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getBtcPriceUsd() {
        return btcPriceUsd;
    }

    public void setBtcPriceUsd(String btcPriceUsd) {
        this.btcPriceUsd = btcPriceUsd;
    }

    public String getBtcChange24h() {
        return btcChange24h;
    }

    public void setBtcChange24h(String btcChange24h) {
        this.btcChange24h = btcChange24h;
    }

    public String getEthPriceUsd() {
        return ethPriceUsd;
    }

    public void setEthPriceUsd(String ethPriceUsd) {
        this.ethPriceUsd = ethPriceUsd;
    }

    public String getEthChange24h() {
        return ethChange24h;
    }

    public void setEthChange24h(String ethChange24h) {
        this.ethChange24h = ethChange24h;
    }

    public String getBtcDominance() {
        return btcDominance;
    }

    public void setBtcDominance(String btcDominance) {
        this.btcDominance = btcDominance;
    }

    public String getGlobalInitialType() {
        return globalInitialType;
    }

    public void setGlobalInitialType(String globalInitialType) {
        this.globalInitialType = globalInitialType;
    }

    public List<PortfolioGroup> getPortfolioGroups() {
        return portfolioGroups;
    }

    public void setPortfolioGroups(List<PortfolioGroup> portfolioGroups) {
        this.portfolioGroups = portfolioGroups;
    }

}
