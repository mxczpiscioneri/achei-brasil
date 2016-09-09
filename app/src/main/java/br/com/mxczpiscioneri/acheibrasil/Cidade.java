package br.com.mxczpiscioneri.acheibrasil;

/**
 * Created by Matheus on 08/05/2016.
 */
public class Cidade {
    private int id;
    private String nome;
    private String uf;

    public Cidade(int id, String nome, String uf) {
        setId(id);
        setNome(nome);
        setUf(uf);
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

    public String getUf() {
        return uf;
    }

    public void setUf(String uf) {
        this.uf = uf;
    }

    public String toString(){
        return this.nome + " - "+ this.uf;
    }
}
