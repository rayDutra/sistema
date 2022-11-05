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

    @GetMapping("/apartamentos")
    public String apartamento (Model model) {
        List<Apartamento> listaDeApartamentos = db.query(
                "select cod_apto,num_porta,num_quartos,tipo,cod_prop from apartamento",
                (res, rowNum) -> {
                    Proprietario tipo = new Apartamento(
                            res.getInt("cod_apto"),
                            res.getInt("num_porta"),
                            res.getInt("num_quartos"),
                            res.getString("tipo"),
                            res.getInt("cod_prop"));
                    return tipo;
                });
        model.addAttribute("contatos", listaDeApartamentos);
        return "tipo";
    }
}