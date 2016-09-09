package br.com.mxczpiscioneri.acheibrasil;

import android.util.Log;

/**
 * Created by Matheus on 04/05/2016.
 */
public class Estabelecimento {
    private int id;
    private int tipo;
    private String nome;
    private String endereco_completo;
    private String email;
    private String telefone;
    private String celular;
    private String endereco;
    private String estado;
    private String cidade;
    private String bairro;
    private String numero;
    private String complemento;
    private String cep;
    private String foto_principal;
    private String foto_menu;
    private String foto_extra;
    private String categoria1;
    private String categoria2;
    private String categoria3;
    private String categoria4;
    private String categoria5;
    private String facebook;
    private String instagram;
    private String twitter;
    private String whatsapp;
    private String website;
    private String observacao;
    private String propaganda1;
    private String propaganda2;

    public Estabelecimento(int id, String nome, String endereco_completo) {
        setId(id);
        setNome(nome);
        setEndereco_completo(endereco_completo);
    }

    public Estabelecimento() {

    }

    public int getTipo() {
        return tipo;
    }

    public void setTipo(int tipo) {
        this.tipo = tipo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getComplemento() {
        return complemento;
    }

    public void setComplemento(String complemento) {
        this.complemento = complemento;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getFoto_principal() {
        return foto_principal;
    }

    public void setFoto_principal(String foto_principal) {
        this.foto_principal = foto_principal;
    }

    public String getFoto_menu() {
        return foto_menu;
    }

    public void setFoto_menu(String foto_menu) {
        this.foto_menu = foto_menu;
    }

    public String getFoto_extra() {
        return foto_extra;
    }

    public void setFoto_extra(String foto_extra) {
        this.foto_extra = foto_extra;
    }

    public String getCategoria1() {
        return categoria1;
    }

    public void setCategoria1(String categoria1) {
        this.categoria1 = categoria1;
    }

    public String getCategoria2() {
        return categoria2;
    }

    public void setCategoria2(String categoria2) {
        this.categoria2 = categoria2;
    }

    public String getCategoria3() {
        return categoria3;
    }

    public void setCategoria3(String categoria3) {
        this.categoria3 = categoria3;
    }

    public String getCategoria4() {
        return categoria4;
    }

    public void setCategoria4(String categoria4) {
        this.categoria4 = categoria4;
    }

    public String getCategoria5() {
        return categoria5;
    }

    public void setCategoria5(String categoria5) {
        this.categoria5 = categoria5;
    }

    public String getFacebook() {
        return facebook;
    }

    public void setFacebook(String facebook) {
        this.facebook = facebook;
    }

    public String getInstagram() {
        return instagram;
    }

    public void setInstagram(String instagram) {
        this.instagram = instagram;
    }

    public String getTwitter() {
        return twitter;
    }

    public void setTwitter(String twitter) {
        this.twitter = twitter;
    }

    public String getWhatsapp() {
        return whatsapp;
    }

    public void setWhatsapp(String whatsapp) {
        this.whatsapp = whatsapp;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }


    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
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

    public String getEndereco_completo() {
        return endereco_completo;
    }

    public void setEndereco_completo(String endereco_completo) {
        this.endereco_completo = endereco_completo;
    }

    public String getPropaganda1() {
        return propaganda1;
    }

    public void setPropaganda1(String propaganda1) {
        this.propaganda1 = propaganda1;
    }

    public String getPropaganda2() {
        return propaganda2;
    }

    public void setPropaganda2(String propaganda2) {
        this.propaganda2 = propaganda2;
    }

    public String toString(){
        return String.valueOf(this.id);
    }
}
