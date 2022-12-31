import java.io.*;
import java.util.Arrays;

public class Basket {
    private String[] products;
    private int[] productsCountList;
    private int[] prices;

    public Basket(String[] products, int[] prices) {
        this.products = products;
        this.prices = prices;
    }

    public void addToCart(int productNum, int amount) {
        this.productsCountList[productNum] += amount;
    }

    public void printCart() {
        System.out.println(" Список товаров: ");
        for (int i = 0; i < products.length; i++) {
            System.out.printf("Покупки: " + i + 1, products[i], prices[i]);
        }
    }

    public void saveTxt(File textFile) throws IOException {
        try (PrintWriter out = new PrintWriter(textFile)) {
            byte[] bytes = new byte[products.length];
            for (int e = 0; e < products.length; e++) {
                bytes[e] = (byte) prices[e];
                out.print(bytes[e] + " ");
            }
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public static Basket loadFromTxtFile(File textFile) {
        String[] products;
        int[] productsCountList;
        int[] prices;
        StringBuilder stringBuilder = new StringBuilder();
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(textFile))) {
            String stripe;
            while ((stripe = bufferedReader.readLine()) != null) {
                stringBuilder.append(stripe);
                stringBuilder.append("/n");
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Object[] o = stringBuilder.toString().lines().toArray();
        products = o[0].toString().split(" ");
        prices = Arrays.stream(o[1].toString().split(" ")).mapToInt(n -> Integer.parseInt(n)).toArray();
        productsCountList = Arrays.stream(o[2].toString().split(" ")).mapToInt(n -> Integer.parseInt(n)).toArray();

        Basket basket = new Basket(products, prices);
        basket.setProductsCountList(productsCountList);
        return basket;
    }

    public String[] getProducts() {
        return products;
    }

    public void setProducts(String[] products) {
        this.products = products;
    }

    public int[] getProductsCountList() {
        return productsCountList;
    }

    public void setProductsCountList(int[] productsCountList) {
        this.productsCountList = productsCountList;
    }

    public int[] getPrices() {
        return prices;
    }

    public void setPrices(int[] prices) {
        this.prices = prices;
    }
}