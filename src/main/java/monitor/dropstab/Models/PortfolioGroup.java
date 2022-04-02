package monitor.dropstab.Models;

import javax.annotation.processing.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("jsonschema2pojo")
public class PortfolioGroup {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("sortOrder")
    @Expose
    private Integer sortOrder;
    @SerializedName("shareType")
    @Expose
    private String shareType;
    @SerializedName("portfolioSharingType")
    @Expose
    private String portfolioSharingType;
    @SerializedName("portfolioTotal")
    @Expose
    private PortfolioTotal portfolioTotal;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(Integer sortOrder) {
        this.sortOrder = sortOrder;
    }

    public String getShareType() {
        return shareType;
    }

    public void setShareType(String shareType) {
        this.shareType = shareType;
    }

    public String getPortfolioSharingType() {
        return portfolioSharingType;
    }

    public void setPortfolioSharingType(String portfolioSharingType) {
        this.portfolioSharingType = portfolioSharingType;
    }

    public PortfolioTotal getPortfolioTotal() {
        return portfolioTotal;
    }

    public void setPortfolioTotal(PortfolioTotal portfolioTotal) {
        this.portfolioTotal = portfolioTotal;
    }

}
