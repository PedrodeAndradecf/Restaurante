package Backend.Restaurante.service;

import Backend.Restaurante.client.PagamentoClient;
import Backend.Restaurante.repository.FechamentoContaRepository;
import Backend.Restaurante.repository.MesaRepository;
import Backend.Restaurante.repository.PagamentoRepository;
import Backend.Restaurante.repository.PedidoRepository;

public class PagamentoService {

    private final PagamentoClient pagamentoClient;
    private final FechamentoContaRepository fechamentoRepository;
    private final PedidoRepository pedidoRepositroy;
    private final MesaRepository mesaRepository;
    private final PagamentoRepository pagamentoRepository;



    public PagamentoService(
            PagamentoClient pagamentoClient,
            FechamentoContaRepository fechamentoRepository,
            PedidoRepository pedidoRepository,
            MesaRepository mesaRepository,
            PagamentoRepository pagamentoRepository
    ){
        this.pagamentoClient = pagamentoClient;
        this.fechamentoRepository = fechamentoRepository;
        this.pedidoRepositroy = pedidoRepository;
        this.mesaRepository = mesaRepository;
        this.pagamentoRepository = pagamentoRepository;
    }

    

}
