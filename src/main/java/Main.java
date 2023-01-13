import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

class Main {

    public static void main(String[] args) throws IOException, XPathExpressionException, ParserConfigurationException, SAXException {

        File csvFile = new File("log.csv");
        File jsonFile = new File("basket.json");
        File xmlFile = new File("shop.xml");
        ClientLog log = new ClientLog();
        Scanner scanner = new Scanner(System.in);

        String[] products = new String[]{"Хлеб  ", "Яблоки", "Молоко"};
        int[] prices = new int[]{100, 200, 300};

        int[] sum = new int[]{0, 0, 0};
        int[] count = new int[]{0, 0, 0};

        int productNumber = 0;
        int productCount = 0;
        int sumProducts = 0;

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.parse("shop.xml");

        XPath xPath = XPathFactory.newInstance().newXPath();

        boolean doLoad = Boolean.parseBoolean(xPath
                .compile("config/load/enabled")
                .evaluate(doc));
        String loadFileName = xPath
                .compile("/config/load/fileName")
                .evaluate(doc);
        String loadFormat = xPath
                .compile("/config/load/format")
                .evaluate(doc);

        Basket basket = null;
        if (doLoad) {
            File loadFile = new File(loadFileName);
            switch (loadFormat) {
                case "json":basket = Basket.loadFromJson(loadFile); break;
                case "txt": basket = Basket.loadFromTxtFile(loadFile);break;

            }
        } else {
            basket = new Basket(products, prices);
        }

        boolean doSave = Boolean.parseBoolean(xPath
                .compile("/config/save/enabled")
                .evaluate(doc));
        String SaveFileName = xPath
                .compile("/config/save/fileName")
                .evaluate(doc);
        String saveFormat = xPath
                .compile("/config/save/format")
                .evaluate(doc);

        if (doSave) {
            File saveFile = new File(SaveFileName);
            switch (saveFormat) {
                case "json":basket.saveJson(saveFile);
                    break;
                case "txt":basket.saveTxt(saveFile);
                    break;
                case "bin":basket.saveBin(saveFile);
                    break;
            }
        } else {
            basket = new Basket(products,prices);
        }


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