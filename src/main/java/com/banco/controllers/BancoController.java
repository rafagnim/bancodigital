package com.banco.controllers;

import com.banco.entities.Banco;
import com.banco.services.BancoService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.persistence.EntityNotFoundException;

@Controller
@RequestMapping(path = "banco")
public class BancoController {

    private final BancoService bService;

    public BancoController(BancoService bService) {
        this.bService = bService;
    }

    @RequestMapping(path = "cadastrar")
    public ModelAndView cadastrarBanco(@RequestParam(required = false) Long id) {
        ModelAndView mv = new ModelAndView("banco/cadastro.html");
        Banco banco;

        if(id == null) {
            banco = new Banco();
        } else {
            try {
                banco = bService.getBanco(id);
            } catch (EntityNotFoundException e) {
                banco = new Banco();
                mv.addObject("msg", e.getMessage());
            }
        }

        mv.addObject("banco", banco);
        return mv;
    }

    @RequestMapping(method = RequestMethod.POST, path = "cadastrar")
    public ModelAndView cadastrarBanco(Banco b, RedirectAttributes redirectAttributes) {
        ModelAndView mv = new ModelAndView("redirect:/banco");

        bService.addBanco(b);

        redirectAttributes.addFlashAttribute("msg", b.getNome() + " salvo com sucesso!");

        return mv;
    }

    @RequestMapping()
    public ModelAndView listarBancos() {
        ModelAndView mv = new ModelAndView("banco/listar.html");
        mv.addObject("listaBancos", bService.listaBancos());
        return mv;
    }

    @RequestMapping(path = "excluir")
    public ModelAndView delBanco(@RequestParam Long id, RedirectAttributes redirectAttributes) {
        ModelAndView mv = new ModelAndView("redirect:/banco");
        Banco b = new Banco();
        try {
            b = bService.delBanco(id);
            redirectAttributes.addFlashAttribute("msg", b.getNome() + " excluído.");
        }catch (Exception e) {
            redirectAttributes.addFlashAttribute("msg", "Erro ao excluir! Após ter quaisquer clientes ou contas vinculadas, o banco não pode mais ser excluído");
        }
        return mv;
    }
}
