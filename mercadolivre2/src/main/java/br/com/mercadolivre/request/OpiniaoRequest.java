package br.com.mercadolivre.request;


import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;



import br.com.mercadolivre.modelo.Opiniao;
import br.com.mercadolivre.modelo.Produto;
import br.com.mercadolivre.modelo.Usuario;


public class OpiniaoRequest {
    @NotNull
    @Min(1)
    @Max(5)
    private Integer nota;

    @NotBlank
    private String titulo;

    @NotBlank
    @Size(max = 500)
    private String descricao;

    @Deprecated
    public OpiniaoRequest() {
    }

    public OpiniaoRequest(@NotNull @Min(1) @Max(5) Integer nota, @NotBlank String titulo, @NotBlank @Size(max = 500) String descricao) {
        this.nota = nota;
        this.titulo = titulo;
        this.descricao = descricao;
    }

    public Opiniao converter( @NotNull Produto produto, Usuario usuario) {
       

        return new Opiniao(nota, titulo, descricao, produto, usuario);


    }

    @Override
    public String toString() {
        return "OpiniaoRequest{" +
                "nota=" + nota +
                ", titulo='" + titulo + '\'' +
                ", descricao='" + descricao + '\'' +
                '}';
    }

    public Integer getNota() {
        return nota;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getDescricao() {
        return descricao;
    }
}