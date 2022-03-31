import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {

        var icoDrops = new IcoDrops();
        icoDrops.login();
        var shortPortfolio = icoDrops.getShortPortfolio();

        var balances = shortPortfolio.getPortfolioGroups().stream()
                .map(portfolioGroup -> portfolioGroup.getPortfolioTotal().getTotalCap().getUsd()).toList();

        System.out.println(shortPortfolio.getUsername() + ": " + String.join(", ", balances));
    }

}
