public class Time {
    private String nome;
    private int pontos;
    private int golsFeitos;
    private int golsSofridos;
    
    public Time(String nome) {
        this.nome = nome;
        this.pontos = 0;
        this.golsFeitos = 0;
        this.golsSofridos = 0;
    }
    
    public String getNome() {
        return nome;
    }
    
    public int getPontos() {
        return pontos;
    }
    
    public void adicionarPontos(int pontos) {
        this.pontos += pontos;
    }
    
    public int getGolsFeitos() {
        return golsFeitos;
    }
    
    public void adicionarGolsFeitos(int gols) {
        this.golsFeitos += gols;
    }
    
    public int getGolsSofridos() {
        return golsSofridos;
    }
    
    public void adicionarGolsSofridos(int gols) {
        this.golsSofridos += gols;
    }
    
    public int getSaldoGols() {
        return golsFeitos - golsSofridos;
    }
    
    @Override
    public String toString() {
        return String.format("%-20s | %3d | %3d | %3d",
                nome, pontos, golsFeitos - golsSofridos, golsFeitos);
    }
}
