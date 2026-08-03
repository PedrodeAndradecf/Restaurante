package Backend.Restaurante.mappers;

import Backend.Restaurante.domain.CategoriaProduto;
import Backend.Restaurante.domain.Produto;
import Backend.Restaurante.dto.request.ProdutoRequest;

public class ProdutoMapper {
    private ProdutoMapper(){};

    public static Produto toEntity(ProdutoRequest dto, CategoriaProduto categoria){
        Produto produto = new Produto();
        produto.setCategoria(categoria);
        produto.setNome(dto.nome());
        produto.setDescricao(dto.descricao());
        produto.setPreco(dto.preco());
        produto.setDisponivel(dto.disponivel()!= null ? dto.disponivel() : true);
        produto.setTempoPreparoMinutos(dto.tempoPreparoMinutos());

        return produto;
    }
}
