import java.io.File;
import java.util.Scanner;

class Main {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        String[] products = new String[]{"Хлеб  ", "Яблоки", "Молоко"};
        int[] prices = new int[]{100, 200, 300};

        int[] sum = new int[]{0, 0, 0};
        int[] count = new int[]{0, 0, 0};

        int productNumber = 0;
        int productCount = 0;
        int sumProducts = 0;

        for (int i = 0; i < products.length; i++) {
            System.out.println((i + 1) + ". " + products[i] + " - " + prices[i] + " " + "руб/шт");
        }

        File basketFile = new File("basket.txt");

        while (true) {
            System.out.println("Выберите товар и количество или введите `end`");
            String input = scanner.nextLine();
            if ("end".equals(input)) {

                break;
            }
            try {
                String[] split = input.split(" ");
                productNumber = Integer.parseInt(split[0]) - 1;
                productCount = Integer.parseInt(split[1]);
                sum[productNumber] += prices[productNumber] * productCount;
                count[productNumber] += productCount;
            } catch (NumberFormatException a) {
                System.out.println("В параметре необходимо указать число или end");
            }
        }
        System.out.println(" ");
        System.out.println("Ваша корзина:");

        for (int i = 0; i < 3; i++) {
            if (count[i] != 0) {

                System.out.println(products[i] + " " + count[i] + "шт" + " " + prices[i] + "руб/шт" + " " + "Всего:" + " " + sum[i] + "руб");
            }
            sumProducts += sum[i];
        }
        System.out.println(" ");
        System.out.println("Итого" + " " + sumProducts + "р.");

    }
}