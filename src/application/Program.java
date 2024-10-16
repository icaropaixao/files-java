package application;

import entities.Product;

import java.io.*;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

// DESAFIO:
/*
   fazer um programa para ler o caminho de um arquivo .csv
   contendo os dados de itens vendidos. Cada item possui um nome,
   preço unitario e quantidade, separados por virgula
   deve gerar um novo arquivo chamado "summary.csv",
   localizado em uma subpasta chamada "out" a partir da pasta
   original do arquivo de origem, contendo apenas o nome e o valor
   total para aquele item (Preço multiplicado pela quantidade)
  */

public class Program {
    public static void main(String[] args) throws ParseException {

        Scanner sc = new Scanner(System.in); // Scanner para ler a entrada do usuário

        List<Product> list = new ArrayList<>(); // Lista para armazenar objetos Product

        System.out.println("Digite o caminho do arquivo: ");
        String sourceFileStr = sc.nextLine(); // Caminho do arquivo que será lido

        File sourceFile = new File(sourceFileStr);
        String sourceFolderStr = sourceFile.getParent(); // Obtém a pasta onde o arquivo está localizado

        System.out.println(sourceFolderStr); // Exibe o diretório pai no console

        // Criando uma nova pasta chamada 'out' dentro da pasta do arquivo
        Boolean success = new File(sourceFolderStr + "\\out").mkdir();

        // Definindo o caminho para o arquivo de saída 'summary.csv' dentro da nova pasta
        String targetFileStr = sourceFolderStr + "\\out\\summary.csv";

        // Bloco try-with-resources para ler o arquivo de origem usando BufferedReader
        try (BufferedReader br = new BufferedReader(new FileReader(sourceFileStr))) {

            // Lendo o arquivo linha por linha
            String itemCsv = br.readLine();
            while (itemCsv != null) {

                // Dividindo cada linha do CSV em nome, preço e quantidade
                String[] fields = itemCsv.split(",");
                String name = fields[0]; // Nome do produto
                double price = Double.parseDouble(fields[1]); // Preço do produto
                int quantity = Integer.parseInt(fields[2]); // Quantidade do produto

                // Adicionando um novo objeto Product na lista
                list.add(new Product(name, price, quantity));

                itemCsv = br.readLine(); // Lendo a próxima linha do CSV
            }

            // Bloco try-with-resources para escrever no arquivo de saída usando BufferedWriter
            try (BufferedWriter bw = new BufferedWriter(new FileWriter(targetFileStr))){

                // Percorrendo a lista de produtos e escrevendo os dados formatados no arquivo
                for (Product item : list) {
                    bw.write(item.getName() + ", " + String.format("%.2f", item.Total())); // Escreve o nome e o total do produto
                    bw.newLine(); // Quebra de linha após cada produto
                }
                System.out.println(targetFileStr +  " criado com sucesso"); // Mensagem de sucesso
            }
            catch (IOException erro){
                System.out.println("Erro ao escrever no arquivo: " + erro.getMessage()); // Tratamento de erro para escrita no arquivo
            }
        }
        catch (IOException erro){
            System.out.println("Erro ao ler o arquivo: " + erro.getMessage()); // Tratamento de erro para leitura do arquivo
        }

        sc.close();
    }
}
