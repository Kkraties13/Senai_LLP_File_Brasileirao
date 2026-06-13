import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class Main {
    
    public static void main(String[] args) {
        HashMap<String, Time> times = new HashMap<>();
        
        // Ler arquivo jogos.txt
        try (BufferedReader br = new BufferedReader(new FileReader("jogos.txt"))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                if (linha.trim().isEmpty()) continue;
                
                // Parse da linha: rodada,timeA,timeB,resultado
                String[] partes = linha.split(",");
                
                if (partes.length < 4) continue;
                
                String timeANome = partes[1].trim();
                String timeBNome = partes[2].trim();
                String resultado = partes[3].trim();
                
                // Ignorar linhas com dados inválidos
                if (!resultado.contains("x")) continue;
                
                // Criar times se não existirem
                if (!times.containsKey(timeANome)) {
                    times.put(timeANome, new Time(timeANome));
                }
                if (!times.containsKey(timeBNome)) {
                    times.put(timeBNome, new Time(timeBNome));
                }
                
                // Parse do resultado (ex: "2x1")
                String[] golsArray = resultado.split("x");
                if (golsArray.length != 2) continue;
                
                try {
                    int golsA = Integer.parseInt(golsArray[0]);
                    int golsB = Integer.parseInt(golsArray[1]);
                    
                    Time timeA = times.get(timeANome);
                    Time timeB = times.get(timeBNome);
                    
                    // Atualizar gols
                    timeA.adicionarGolsFeitos(golsA);
                    timeA.adicionarGolsSofridos(golsB);
                    
                    timeB.adicionarGolsFeitos(golsB);
                    timeB.adicionarGolsSofridos(golsA);
                    
                    // Atualizar pontos
                    if (golsA > golsB) {
                        timeA.adicionarPontos(3);
                    } else if (golsB > golsA) {
                        timeB.adicionarPontos(3);
                    } else {
                        timeA.adicionarPontos(1);
                        timeB.adicionarPontos(1);
                    }
                } catch (NumberFormatException e) {
                    // Ignorar linhas com formato inválido
                    continue;
                }
            }
        } catch (IOException e) {
            System.err.println("Erro ao ler arquivo: " + e.getMessage());
            return;
        }
        
        // Converter para lista e ordenar
        ArrayList<Time> classificacao = new ArrayList<>(times.values());
        Collections.sort(classificacao, new Comparator<Time>() {
            @Override
            public int compare(Time t1, Time t2) {
                // Primeiro por pontos (decrescente)
                if (t1.getPontos() != t2.getPontos()) {
                    return t2.getPontos() - t1.getPontos();
                }
                // Depois por saldo de gols (decrescente)
                return t2.getSaldoGols() - t1.getSaldoGols();
            }
        });
        
        // Exibir tabela
        System.out.println("\n╔═══╦══════════════════════╦═════════╦════════╗");
        System.out.println("║ # ║ Time                 ║ Pontos  ║ Saldo  ║");
        System.out.println("╠═══╬══════════════════════╬═════════╬════════╣");
        
        for (int i = 0; i < classificacao.size(); i++) {
            Time time = classificacao.get(i);
            System.out.printf("║%2dº║ %-20s ║ %3d    ║ %3d   ║\n", 
                    (i + 1), time.getNome(), time.getPontos(), time.getSaldoGols());
        }
        
        System.out.println("╚═══╩══════════════════════╩═════════╩════════╝");
    }
}
