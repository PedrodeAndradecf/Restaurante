package Backend.Restaurante.mappers;

import Backend.Restaurante.domain.CategoriaProduto;
import Backend.Restaurante.domain.Produto;
import Backend.Restaurante.dto.request.ProdutoRequest;
import Backend.Restaurante.dto.response.ProdutoResponse;

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


    public static ProdutoResponse toResponse (Produto produto){
        return new ProdutoResponse(
                produto.getId(),
                produto.getNome(),
                produto.getDescricao(),
                produto.getPreco(),
                produto.getDisponivel(),
                produto.getTempoPreparoMinutos(),
                produto.getCategoria().getId(),
                produto.getCategoria().getNome(),
                produto.getCriadoEm()
        );
    }
}
