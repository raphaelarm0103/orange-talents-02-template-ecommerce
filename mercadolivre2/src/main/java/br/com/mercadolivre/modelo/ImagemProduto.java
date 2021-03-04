package br.com.mercadolivre.modelo;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "imagens_produto")
public class ImagemProduto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotBlank
    private String link;

    @NotNull
    @ManyToOne
    private Produto produto;

    @Deprecated
    public ImagemProduto() {
    }

    public ImagemProduto(Produto produto, String link) {
        this.produto = produto;
        this.link = link;
    }

    public long getId() {
        return id;
    }

    public String getLink() {
        return link;
    }

}