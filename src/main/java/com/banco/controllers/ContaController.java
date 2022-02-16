package com.banco.controllers;

import com.banco.Enums.TipoConta;
import com.banco.dto.ContaDtoCadastro;
import com.banco.dto.ContaDtoConsulta;
import com.banco.dto.LancamentoDto;
import com.banco.dto.MapperContaDto;
import com.banco.entities.*;
import com.banco.services.ClienteService;
import com.banco.services.ContaService;
import com.banco.services.LancamentoService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping(path = "conta")
public class ContaController {

    private final ContaService contaService;
    private final ClienteService clienteService;
    private final LancamentoService lancamentoService;

    public ContaController(ContaService contaService, ClienteService clienteService, LancamentoService lancamentoService) {
        this.contaService = contaService;
        this.clienteService = clienteService;
        this.lancamentoService = lancamentoService;
    }

    @RequestMapping(path = "cadastrar")
    public ModelAndView cadastrarConta(@RequestParam Long idCliente) {
        ModelAndView mv = new ModelAndView("conta/cadastro.html");


        ContaDtoCadastro c = new ContaDtoCadastro();
        c.setCliente_id(idCliente);
        mv.addObject("conta", c);
        mv.addObject("tiposdeconta", TipoConta.values());
        mv.addObject("clientenome", clienteService.getCliente(idCliente).getNome());

        return mv;
    }

    @RequestMapping(method = RequestMethod.POST, path = "cadastrar")
    public ModelAndView cadastrarConta(ContaDtoCadastro c, RedirectAttributes redirectAttributes) {
        ModelAndView mv = new ModelAndView("redirect:/conta/listar");
        Cliente cliente = clienteService.getCliente(c.getCliente_id());
        if (c.getTipoconta().equals(TipoConta.CORRENTE)) {
            ContaCorrente conta = new ContaCorrente(cliente);
            contaService.addConta(conta);
        } else if (c.getTipoconta().equals(TipoConta.POUPANCA)) {
            ContaPoupanca conta = new ContaPoupanca(cliente);
            contaService.addConta(conta);
        }

        redirectAttributes.addFlashAttribute("msg", "Conta do cliente " + cliente.getNome()
                + " salva com sucesso!");


        return mv;
    }

    @RequestMapping(method = RequestMethod.GET, path = "listar")
    public ModelAndView listarContas(@RequestParam (required = false) Long idCliente) {
        ModelAndView mv = new ModelAndView("conta/listar.html");

        if (idCliente != null) {
            mv.addObject("cliente", clienteService.getCliente(idCliente));
            mv.addObject("listaContas", contaService.listaContasCliente(idCliente));
        }

        return mv;
    }

    @RequestMapping(method = RequestMethod.GET, path = "movimentar")
    public ModelAndView movimentarConta(@RequestParam Long numero) {
        ModelAndView mv = new ModelAndView("conta/movimentar.html");
        Conta conta = contaService.getConta(numero);
        ContaDtoConsulta contaDTO = MapperContaDto.fromEntity(conta);
        Cliente cliente = clienteService.getCliente(conta.getCliente().getId());
        Lancamento lancamento = new Lancamento();
        lancamento.setContaorigem(numero);
        mv.addObject("lancamento", lancamento);
        mv.addObject("conta", contaDTO);
        mv.addObject("cliente", cliente);
        return mv;
    }

    @RequestMapping(method = RequestMethod.POST, path = "movimentar")
    public ModelAndView movimentarConta(Lancamento lancamento) {
        System.out.println(lancamento);
        ModelAndView mv = new ModelAndView("conta/confirmacaomovimentacao.html");
        if (lancamento.getContaorigem().equals(lancamento.getContadestino())) {
            mv.addObject("msg", "Não é possível transferir para a mesma conta. Refaça a transação.");
            return mv;
        } else if (lancamento.getContadestino() != null && !lancamento.getTipo().equals("transferencia")) {
            mv.addObject("msg", "Informe conta de destino apenas na opção Transferencia.");
        }
        Conta conta;
        Conta contaDestino;
        try {
            conta = contaService.getConta(lancamento.getContaorigem());
            Cliente cliente = clienteService.getCliente(conta.getCliente().getId());
            mv.addObject("conta", conta);
            mv.addObject("cliente", cliente);
            if(lancamento.getTipo().equals("saque")) {
                conta.sacar(lancamento.getValor());
            } else if (lancamento.getTipo().equals("deposito")) {
                conta.depositar(lancamento.getValor());
            } else if (lancamento.getTipo().equals("transferencia")) {
                contaDestino = contaService.getConta(lancamento.getContadestino());
                mv.addObject("banco", contaDestino.getCliente().getBanco().getNome());
                mv.addObject("clientedestino", contaDestino.getCliente().getNome());
                if (!contaDestino.isAtiva()) {
                    mv.addObject("msg", "Não é possível transferir para conta encerrada.");
                    return mv;
                }
                conta.transferir(lancamento.getValor(), contaDestino);
            } else {
                mv.addObject("banco", "");
                mv.addObject("clientedestino", "");
            }
            lancamentoService.addLancamento(lancamento);
            contaService.putConta(conta);
        } catch (IllegalArgumentException iae) {
            mv.addObject ("msg", iae.getMessage());
        } catch (Exception e) {
            mv.addObject ("msg", e.getStackTrace());
        }

        mv.addObject("lancamento", lancamento);
        return mv;
    }

    @RequestMapping(path = "encerrar")
    public ModelAndView delBanco(@RequestParam Long id, RedirectAttributes redirectAttributes) {
        ModelAndView mv = new ModelAndView("redirect:/conta/listar");
        Conta c;
        try {
            c = contaService.encerrarConta(id);
            redirectAttributes.addFlashAttribute("msg", "Conta número " + c.getId() + " encerrada com sucesso.");

        }catch (Exception e) {
            redirectAttributes.addFlashAttribute("msg", e.getMessage());
        }
        return mv;
    }

    @RequestMapping(path = "extrato")
    public ModelAndView extrato(@RequestParam Long numero) {
        ModelAndView mv = new ModelAndView("conta/extrato.html");
        List<LancamentoDto> lancamentos = lancamentoService.extratoConta(numero);
        if (lancamentos.isEmpty()) mv.addObject("msg", "Conta sem movimentação.");
        mv.addObject("saldo", contaService.getConta(numero).getSaldo());
        mv.addObject("contaorigem", numero);
        mv.addObject("lancamentos", lancamentos);
        mv.addObject("clientenome", clienteService.getCliente(contaService.getConta(numero).getCliente().getId()).getNome());
        return mv;
    }
}
