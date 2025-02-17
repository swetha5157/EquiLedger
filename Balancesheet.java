import java.util.*;
public class Balancesheet{
   static class Asset{
        private String name;
        private double value;
        private boolean isCurrent;

        public Asset(String name,double value,boolean isCurrent){
            this.name=name;
            this.value=value;
            this.isCurrent=isCurrent;
        }

        public double getValue() {
            return value;
        }

        public boolean isCurrent() {
            return isCurrent;
        }

        @Override
        public String toString() {
            return name + ": Rs." + String.format("%.2f", value) + (isCurrent ? "(Current-asset)" : "(Noncurrent-asset)");
        }
    }
   static  class Liability{
        private String name;
        private double value;
        private boolean isShortTerm;
        public Liability(String name,double value,boolean isShortTerm){
            this.name=name;
            this.value=value;
            this.isShortTerm=isShortTerm;
        }
        public double getValue(){
            return value;
        }
        public boolean isShortTerm(){
            return isShortTerm;
        }
        @Override
        public String toString(){
                 return name+": Rs."+String.format("%.2f",value)+(isShortTerm?"(Short-term)":"(Long-term)");
        }
    }
   static class BalanceSheet{
        private List<Asset>assets;
        private List<Liability>liabilities;
        public BalanceSheet(){
            this.assets=new ArrayList<>();
            this.liabilities=new ArrayList<>();
        }
        public void addAsset(Asset asset){
            assets.add(asset);
        }
        
        public void addLiability(Liability liability) {
            liabilities.add(liability);
        }
        
        private double calculateTotalAssets(boolean current) {
            return assets.stream().filter(a -> a.isCurrent() == current).mapToDouble(Asset::getValue).sum();
        }

        private double calculateTotalLiabilities(boolean shortTerm) {
            return liabilities.stream().filter(l -> l.isShortTerm() == shortTerm).mapToDouble(Liability::getValue)
                    .sum();
        }

        private double calculateTotalAssets() {
            return assets.stream().mapToDouble(Asset::getValue).sum();
        }

        private double calculateTotalLiabilities() {
            return liabilities.stream().mapToDouble(Liability::getValue).sum();
        }

        public double calculateEquity() {
            return calculateTotalAssets() - calculateTotalLiabilities();
        }
      public void display(){
        System.out.println("\n======STARTUP BALANCE SHEET======");
        System.out.println("Assets:");
        System.out.println("\nCurrent Assets:");
        assets.stream().filter(Asset::isCurrent).forEach(System.out::println);
        System.out.printf("%-45s Rs.%-10.2f\n","Total Current Assets:       $%.2f%n", calculateTotalAssets(true));

        System.out.println("\nNon-Current Assets:");
        assets.stream().filter(a -> !a.isCurrent()).forEach(System.out::println);
        System.out.printf("%-45s Rs.%-10.2f\n","Total Non-Current Assets:   $%.2f%n", calculateTotalAssets(false));

        System.out.printf("\nTotal Assets:               $%.2f%n", calculateTotalAssets());

        System.out.println("\nLiabilities:");
        System.out.println("\nShort-term Liabilities:");
        liabilities.stream().filter(Liability::isShortTerm).forEach(System.out::println);
        System.out.printf("%-45s Rs.%-10.2f\n","Total Short-term Liabilities:  $%.2f%n", calculateTotalLiabilities(true));

        System.out.println("\nLong-term Liabilities:");
        liabilities.stream().filter(l -> !l.isShortTerm()).forEach(System.out::println);
        System.out.printf("%-45s Rs.%-10.2f\n","Total Long-term Liabilities:   $%.2f%n", calculateTotalLiabilities(false));

        System.out.printf("%-45s Rs.%-10.2f\n","\nTotal Liabilities:          $%.2f%n", calculateTotalLiabilities());

        System.out.printf("%-45s Rs.%-10.2f\n","\nOwner's Equity:             $%.2f%n", calculateEquity());
        System.out.println("==========================================");

      }

    }
    public static void main(String args[]){
            Scanner s=new Scanner(System.in);
            BalanceSheet ns=new BalanceSheet();
            System.out.println("Enter asset details(name,value,type). Type done when finish:");
            String[] assetTypes = {
                    "Cash and cash equivalents", "Marketable securities", "Inventories",
                    "Accounts receivable, net and other",
                    "Property and equipment, net", "Goodwill", "Other assets"
            };
            for(String assetType:assetTypes){
                System.out.println("Enter value for "+assetType+": Rs.");
                double value=s.nextDouble();
                boolean isCurrent = assetType.equals("Cash and cash equivalents")
                        || assetType.equals("Marketable securities") ||
                        assetType.equals("Inventories") || assetType.equals("Accounts receivable, net and other");
                ns.addAsset(new Asset(assetType, value, isCurrent));
            }
              String[] liabilityTypes = {
                "Income tax payable", "Sales tax liability", "Debt on business loans", "Contracts you can't cancel without penalty",
                "Lease agreements", "Insurance payable", "Benefits payable", "Taxes on investments", "Accrued liabilities"
        };

        for (String liabilityType : liabilityTypes) {
            System.out.print("Enter value for " + liabilityType + ": $");
            double value = s.nextDouble();
            System.out.print("Is this short-term? (yes/no): ");
            boolean isShortTerm = s.next().equalsIgnoreCase("yes");
            ns.addLiability(new Liability(liabilityType, value, isShortTerm));
        }

        ns.display();
        s.close();
    }
}