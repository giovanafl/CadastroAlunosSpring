<%@page import="java.util.ArrayList, java.util.List, com.senac.model.Aluno" %>  
<html>
<head>
<meta charset="UTF-8">
<title>Alunos Cadastrados</title>
</head>
<body>

<%
	String usuario = (String) session.getAttribute("usuario");
	if (usuario == null) {
		response.sendRedirect("index.jsp?error=2");
	}
%>

	
<br><br>
Clique <a href="cadastrarAluno.jsp">aqui</a> para cadastar um novo aluno

<% @SuppressWarnings("unchecked")
List<Aluno> listaAlunos = (List<Aluno>) request.getAttribute("listaAlunos"); %>


<h2>Alunos Cadastrados</h2>

<form action="pesquisa" method="post">       
		<select name="tipoPesquisa">   
				<option value="nome">Nome</option>                  
                <option value="matricula">Matricula</option>         		
        </select>
        <input type="text" name="valor">
        <input type="submit" value="Pesquisar">       
 </form>



<table border="1">

	<tr> 
		<th> Detalhar </th>
		<th> Matricula </th>
		<th> Nome </th>
		<th> Idade </th>
		<th> Sexo </th>
		<th> Semestre </th>		
		<th> Excluir </th>
	
	</tr>   
	
	<%  if (listaAlunos != null) {
			for (Aluno aluno : listaAlunos) { %>
		<tr>	
			<td> <a href="detalhar?id=<%= aluno.getId() %>"> Detalhar</a></td>
			<td> <%= aluno.getMatricula() %>   </td>
			<td> <%= aluno.getNome() %>   </td>
			<td> <%= aluno.getIdade()  %>   </td>
			<td> <%= aluno.getGenero() %>   </td>
			<td> <%= aluno.getSemestre() %>   </td>
			<td> <a href="excluir?id=<%= aluno.getId() %>">Excluir</a></td>
		</tr>
		<% } }%>
	
	



</table>
<br> 
<form action="RelatorioServlet" method="get">
<input type="submit" value="Gerar Relatorio">   <br>
</form>
<a href = "Deslogar">Sair</a>




</body>
</html>