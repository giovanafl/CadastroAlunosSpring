package com.senac.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.senac.dao.AlunoJDBCdao;
import com.senac.model.Aluno;

import jakarta.servlet.http.HttpSession;

@Controller
public class Controlador {

	@Autowired
	AlunoJDBCdao dao;
	Aluno aluno = new Aluno();
	
	@GetMapping ("Deslogar")
	public String deslogar() {
		HttpSession session = dao.getSession();
		session.invalidate();
		return "index.jsp";
	}
	
	@GetMapping("listarAlunos")
	public String listarAlunos(Model model) {				
		ArrayList<Aluno> listaAlunos= dao.listarAlunos();
		model.addAttribute("listaAlunos",listaAlunos);	
		return "listarAlunos.jsp";
	}
	
	@PostMapping ("autenticar")
	public String autenticar(@RequestParam("usuario") String usuario,
			@RequestParam ("senha") String senha,
			Model model,
			HttpSession session) {
		
		if (usuario.equals("admin") && senha.equals("admin")) {
					
					session = dao.getSession();
					session.setMaxInactiveInterval(60);
					
					session.setAttribute("usuario", usuario); // Armazena o usuário na sessão
			
					return "redirect:/listarAlunos"; //estou chamando um método pois aqui faria a mesma coisa que lá
		} else {
			model.addAttribute("error","1");
		}
		
		return "index.jsp";
	}
}
