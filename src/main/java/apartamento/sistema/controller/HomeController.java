package apartamento.sistema.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import apartamento.sistema.model.Proprietario;

@Controller
public class HomeController {

    @Autowired
    JdbcTemplate db;

    @GetMapping("/home")
    public String home() {
        return "home";
    }

    @GetMapping("/proprietarios")
    public String proprietario (Model model) {
        List<Proprietario> listaDeProprietarios = db.query(
                "select cod_prop,nome,telefone from proprietario",
                (res, rowNum) -> {
                    Proprietario contato = new Proprietario(
                            res.getInt("cod_prop"),
                            res.getString("nome"),
                            res.getString("telefone"));
                    return contato;
                });
        model.addAttribute("contatos", listaDeProprietarios);
        return "contato";
    }


@GetMapping("/apartamento")
    public String apartamento(Model model) {
        List<Apartamento> listaDeApartamentos = db.query(
                "select cod_apto,num_porta,num_quartos,tipo,cod_prop from apartamento",
                (res, rowNum) -> {
                    Apartamento a = new Apartamento(
                            res.getInt("cod_apto"),
                            res.getInt("num_porta"),
                            res.getInt("num_quartos"),
                            res.getString("tipo"));
                    return a;
                });
        model.addAttribute("contatos", listaDeApartamentos);
        return "apartamento";
    }

    @GetMapping("novo")
    public String exibeForm(Model model) {
        model.addAttribute("prop", new Proprietario());
        return "formulario";
    }

    @PostMapping("novo")
    public String cadastroProprietario(Proprietario proprietario) {
        System.out.println(proprietario.getNome());
        db.update(
                "update into proprietario (nome, telefone) values (?,?);",
                proprietario.getNome(),
                proprietario.getTelefone());
        return "home";
    }

    @GetMapping("excluir-contato")
    public String apagarContato(@RequestParam(value = "id", required = true) Integer cod) {
        System.out.println("--------------------> " + cod);
        db.update("delete from contatos where id = ?", cod);
        return "redirect:/contatos";
    }