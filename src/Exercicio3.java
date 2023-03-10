import java.io.File;
import java.util.ArrayList;
import java.util.List;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class Exercicio3 {
  public static void main(String[] args) throws Exception {
    File file = new File("./src/dados.xml");

    // Cria uma fábrica de builders de documentos XML
    DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();

    // Cria um builder de documentos XML
    DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();

    // Lê o arquivo XML e constrói um objeto Document que representa o XML
    Document doc = dBuilder.parse(file);

    // Obtém a lista de elementos "row"
    NodeList nodeList = doc.getElementsByTagName("row");

    // Cria uma lista para armazenar os valores de faturamento
    List<Double> faturamentos = new ArrayList<>();

    // Itera sobre os elementos "row" e extrai o valor de faturamento
    for (int i = 0; i < nodeList.getLength(); i++) {
      Node node = nodeList.item(i);
      if (node.getNodeType() == Node.ELEMENT_NODE) {
        Element element = (Element) node;
        double faturamento = Double.parseDouble(element.getElementsByTagName("valor").item(0).getTextContent());
        faturamentos.add(faturamento);
      }
    }

    // Obtém o menor e o maior valor de faturamento(no caso do menor, desconsiderei dias sem faturamento)
    double menorFaturamento = faturamentos.stream().filter(valor -> valor > 0.0).min(Double::compare).orElse(0.0);
    double maiorFaturamento = faturamentos.stream().max(Double::compare).orElse(0.0);

    // Calcula a média mensal de faturamento
    double mediaMensal = faturamentos.stream().filter(valor -> valor > 0.0).mapToDouble(Double::doubleValue).average().orElse(0.0);

    // Conta o número de dias em que o valor de faturamento diário foi superior à média mensal
    long diasAcimaDaMedia = faturamentos.stream().filter(f -> f > mediaMensal).count();

    // Imprime os resultados
    System.out.println("Menor faturamento: " + menorFaturamento);
    System.out.println("Maior faturamento: " + maiorFaturamento);
    System.out.println("Número de dias com faturamento acima da média: " + diasAcimaDaMedia);
  }
}
