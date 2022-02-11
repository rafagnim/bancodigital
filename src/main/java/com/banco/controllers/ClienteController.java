package com.banco.controllers;

import com.banco.entities.Banco;
import com.banco.entities.Cliente;
import com.banco.services.BancoService;
import com.banco.services.ClienteService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Controller
@RequestMapping(path = "cliente")
public class ClienteController {

    private final ClienteService cService;
    private final BancoService bService;

    public ClienteController(ClienteService cService, BancoService bService) {
        this.cService = cService;
        this.bService = bService;
    }

    @RequestMapping(path = "cadastrar")
    public ModelAndView cadastrarCliente(@RequestParam(required = false) Long id) {
        ModelAndView mv = new ModelAndView("cliente/cadastro.html");
        Cliente cliente;

        if(id == null) {
            cliente = new Cliente();
        } else {
            try {
                cliente = cService.getCliente(id);
            } catch (EntityNotFoundException e) {
                cliente = new Cliente();
                mv.addObject("msg", e.getMessage());
            }
        }

        List<Banco> bancos = bService.listaBancos();
        mv.addObject("cliente", cliente);
        mv.addObject("listaBancos", bancos);
        if (bancos.isEmpty()) {
            mv.addObject("msg", "Ainda não há bancos cadastrados");
        }

        return mv;
    }

    @RequestMapping(method = RequestMethod.POST, path = "cadastrar")
    public ModelAndView cadastrarCliente(Cliente c, RedirectAttributes redirectAttributes) {
        ModelAndView mv = new ModelAndView("redirect:/cliente");

        cService.addCliente(c);

        redirectAttributes.addFlashAttribute("msg", c.getNome() + " salvo com sucesso!");

        return mv;
    }

    @RequestMapping(method = RequestMethod.GET, path = "listar")
    public ModelAndView listarClientes(@RequestParam(required = false) Long idBanco) {
        ModelAndView mv = new ModelAndView("cliente/listar.html");
        if (idBanco != null) {
            System.out.println("Fui chamado!" + idBanco);
            mv.addObject("listaClientes", cService.listaClientesPorIdBanco(idBanco));
        }
        List<Banco> bancos = bService.listaBancos();
        mv.addObject("listaBancos", bancos);

        return mv;
    }

    @RequestMapping(path = "excluir")
    public ModelAndView delCliente(@RequestParam Long id, RedirectAttributes redirectAttributes) {
        ModelAndView mv = new ModelAndView("redirect:/cliente");
        Cliente c = new Cliente();
        try {
            c = cService.delCliente(id);
            redirectAttributes.addFlashAttribute("msg", c.getNome() + " excluído.");
        }catch (Exception e) {
            redirectAttributes.addFlashAttribute("msg", "Erro ao excluir! Após ter quaisquer contas vinculadas, o cliente não pode mais ser excluído");
        }
        return mv;
    }
}
