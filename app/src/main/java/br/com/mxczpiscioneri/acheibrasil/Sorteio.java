package br.com.mxczpiscioneri.acheibrasil;

/**
 * Created by Matheus on 22/05/2016.
 */
public class Sorteio {
    private int id;
    private String nome;
    private String premio;
    private String foto1;
    private String foto2;
    private String inicio;
    private String termino;
    private String ganhador1;
    private String ganhador2;
    private String ganhador3;
    private String ganhador4;
    private String ganhador5;
    private String ganhador6;
    private String ganhador7;
    private String ganhador8;
    private String ganhador9;
    private String ganhador10;
    private int tipo;

    public Sorteio(int id, String nome, String premio, String inicio, String termino, int tipo) {
        setId(id);
        setNome(nome);
        setPremio(premio);
        setInicio(inicio);
        setTermino(termino);
        setTipo(tipo);
    }

    public Sorteio(int id, String nome, String premio, String foto1, String foto2, String inicio, String termino, String ganhador1, String ganhador2, String ganhador3, String ganhador4, String ganhador5, String ganhador6, String ganhador7, String ganhador8, String ganhador9, String ganhador10, int tipo) {
        setId(id);
        setNome(nome);
        setPremio(premio);
        setFoto1(foto1);
        setFoto2(foto2);
        setInicio(inicio);
        setTermino(termino);
        setGanhador1(ganhador1);
        setGanhador2(ganhador2);
        setGanhador3(ganhador3);
        setGanhador4(ganhador4);
        setGanhador5(ganhador5);
        setGanhador6(ganhador6);
        setGanhador7(ganhador7);
        setGanhador8(ganhador8);
        setGanhador9(ganhador9);
        setGanhador10(ganhador10);
        setTipo(tipo);
    }

    public Sorteio() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getPremio() {
        return premio;
    }

    public void setPremio(String premio) {
        this.premio = premio;
    }

    public String getFoto1() {
        return foto1;
    }

    public void setFoto1(String foto1) {
        this.foto1 = foto1;
    }

    public String getFoto2() {
        return foto2;
    }

    public void setFoto2(String foto2) {
        this.foto2 = foto2;
    }

    public String getGanhador1() {
        return ganhador1;
    }

    public void setGanhador1(String ganhador1) {
        this.ganhador1 = ganhador1;
    }

    public String getGanhador2() {
        return ganhador2;
    }

    public void setGanhador2(String ganhador2) {
        this.ganhador2 = ganhador2;
    }

    public String getGanhador3() {
        return ganhador3;
    }

    public void setGanhador3(String ganhador3) {
        this.ganhador3 = ganhador3;
    }

    public String getGanhador4() {
        return ganhador4;
    }

    public void setGanhador4(String ganhador4) {
        this.ganhador4 = ganhador4;
    }

    public String getGanhador5() {
        return ganhador5;
    }

    public void setGanhador5(String ganhador5) {
        this.ganhador5 = ganhador5;
    }

    public String getGanhador6() {
        return ganhador6;
    }

    public void setGanhador6(String ganhador6) {
        this.ganhador6 = ganhador6;
    }

    public String getGanhador7() {
        return ganhador7;
    }

    public void setGanhador7(String ganhador7) {
        this.ganhador7 = ganhador7;
    }

    public String getGanhador8() {
        return ganhador8;
    }

    public void setGanhador8(String ganhador8) {
        this.ganhador8 = ganhador8;
    }

    public String getGanhador9() {
        return ganhador9;
    }

    public void setGanhador9(String ganhador9) {
        this.ganhador9 = ganhador9;
    }

    public String getGanhador10() {
        return ganhador10;
    }

    public void setGanhador10(String ganhador10) {
        this.ganhador10 = ganhador10;
    }

    public String getInicio() {
        return inicio;
    }

    public void setInicio(String inicio) {
        this.inicio = inicio;
    }

    public String getTermino() {
        return termino;
    }

    public void setTermino(String termino) {
        this.termino = termino;
    }

    public int getTipo() {
        return tipo;
    }

    public void setTipo(int tipo) {
        this.tipo = tipo;
    }

    public String toString() {
        return String.valueOf(this.id);
    }
}
