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

        Scanner sc = new Scanner(System.in);

        List<Product> list = new ArrayList<>();

        System.out.println("Digite o caminho do arquivo: ");
        String sourceFileStr = sc.nextLine(); // caminho do arquivo

        File sourceFile = new File(sourceFileStr);
        String sourceFolderStr = sourceFile.getParent(); // pega a pasta anterior

        System.out.println(sourceFolderStr);

        // criando pasta OUT dentro da pasta Temp
        Boolean succes = new File(sourceFolderStr + "\\out").mkdir();

        String targetFileStr = sourceFolderStr + "\\out\\summary.csv"; // criando o arquivo summary dentro da pasta Out

        // lendo os arquivos
        try (BufferedReader br = new BufferedReader(new FileReader(sourceFileStr))) {

            // pegando itens
            String itemCsv = br.readLine();
            while (itemCsv != null) {

                String[] fields = itemCsv.split(",");
                String name = fields[0];
                double price = Double.parseDouble(fields[1]);
                int quantity = Integer.parseInt(fields[2]);

                list.add(new Product(name,price,quantity)); // adicionando produtos novos na lista

                itemCsv = br.readLine();
            }
            // gravação dos arquivos
            try (BufferedWriter bw = new BufferedWriter(new FileWriter(targetFileStr))){

                // percorrer a lista de produtos
                for (Product item : list) {
                    bw.write(item.getName() + ", " + String.format("%.2f",item.Total()));
                    bw.newLine(); // quebra de linha
                }
                System.out.println(targetFileStr +  "Criado com successo");
            }
            catch (IOException erro){
                System.out.println("Erro writer file"+ erro.getMessage());
            }
        }
        catch (IOException erro){
            System.out.println("Erro"+ erro.getMessage());
        }




        sc.close();
    }
}