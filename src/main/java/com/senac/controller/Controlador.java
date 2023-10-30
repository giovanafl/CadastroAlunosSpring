package com.senac.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
	
	@GetMapping ("Deslogar") //Substituiu o Logout Servlet
	public String deslogar() {
		HttpSession session = dao.getSession();
		session.invalidate();
		return "index";
	}
	
	@GetMapping("listarAlunos") //Substituiu o Listar Servlet
	public String listarAlunos(Model model) {				
		ArrayList<Aluno> listaAlunos= dao.listarAlunos();
		model.addAttribute("listaAlunos",listaAlunos);	
		return "listarAlunos";
	}
	
	@PostMapping ("autenticar") //Substituiu o Login Servlet
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
		
		return "index";
	}
		
	@GetMapping("alterar") //Substituiu o Alterar Servlet
	public String alterar (@RequestParam("id") Integer id, Model model) { //String id = request.getParameter("id");	
		aluno.setId(id);	//aluno.setId(Integer.parseInt(id));	
		aluno = dao.pesquisarPorId(aluno); 	//aluno = dao.pesquisarPorId(aluno);	
		model.addAttribute("aluno", aluno); //request.setAttribute("aluno", aluno);
		return "alterarAluno"; /*RequestDispatcher dispatcher = request.getRequestDispatcher("alterarAluno.jsp");
									dispatcher.forward(request, response);
									*/
	}
	
	@PostMapping("confirmarAlterarAluno") //Substituiu o ConfirmarAlteracaoServlet
	public String confirmarAlterarAluno (Aluno aluno, Model model) {
		dao.alterarAluno(aluno);
		return "listarAlunos";
		
	}
		
	@PostMapping("cadastrar") //Substituiu o ConfirmarCadastro Servlet
	public String confirmarCadastro (Aluno aluno, Model model) {
		
		String matricula = criarMatricula(aluno.getIdade(),aluno.getSemestre());
		aluno.setMatricula(matricula);
		int id = dao.cadastrarAluno(aluno);
		aluno.setId(id);
		model.addAttribute("aluno", aluno);
		return "detalharAluno";
	}
	
	@GetMapping ("detalhar") //Substituiu o Detalhar Servlet
	public String detalhar(@RequestParam("id")Integer id, Model model) {
		aluno.setId(id);
		aluno = dao.pesquisarPorId(aluno);
		model.addAttribute("aluno", aluno);		
		return "detalharAluno";
	}
	
	@GetMapping ("pesquisa")
	public String pesquisarAluno (Aluno aluno, Model model) {
		ArrayList<Aluno> listaAlunos= dao.listarAlunos();
		model.addAttribute("listaAlunos",listaAlunos);
		return "listarAlunos";
	}
	
	@GetMapping("excluir") //Substituiu o Excluir Servlet
	public String excluirAluno (@PathVariable Integer id) {
		aluno.setId(id);
		dao.excluirAluno(aluno);
		return "redirect:/aluno/";
		
	}		
	
	
	
	private String criarMatricula(String idade, String semestre) {
	
		
		LocalDate dataAtual = LocalDate.now();
		int mes = dataAtual.getMonthValue();
		int ano = dataAtual.getYear();
		// Assume que o semestre 1 é de Janeiro a Junho e o semestre 2 é de Julho a Dezembro
		int semestreEscolha = (mes < 7) ? 1 : 2;
		
		Random random = new Random();		
		String matricula = String.valueOf(ano) + String.valueOf(mes) + String.valueOf(semestreEscolha) + String.valueOf(idade);
		
        // Gera quatro números aleatórios entre 0 e 9
        for (int i = 0; i < 4; i++) {
        	matricula += String.valueOf(random.nextInt(10)); 
        }
       
    
		return matricula;    
	}
}
