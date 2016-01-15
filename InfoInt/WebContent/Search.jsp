<%@page import="infoInt.StarPerson"%>
<%@page import="infoInt.NominationPerson"%>
<%@page import="infoInt.CombinedPerson"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.Iterator" %>
<%@page import="infoInt.DatabaseConnector"%>
<%@page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<% String query = request.getParameter("search");
   DatabaseConnector connector = new DatabaseConnector(query);
   ArrayList<CombinedPerson> resultList = connector.getResult();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<link rel="stylesheet" type="text/css" href="CSS/InfoInt.css" />
		<link rel="stylesheet" type="text/css" href="CSS/kube.css" />
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<title>Search Result</title>
	</head>
	<body>
		<div class="headline">
			<h2>Search Result for <i><%= request.getParameter("search") %></i></h2>
		</div>
	
		<div class="result">
			<table class="resultTable">
			<% 	if(resultList.isEmpty()) {
					out.println("The search had no result.");
				}
				Iterator<CombinedPerson> iterator = resultList.iterator();
				while(iterator.hasNext()) {
					CombinedPerson cP = iterator.next();
					out.println("<h3>" + cP.getName() + "</h3>");
					
					out.println("<caption>Oscar Nominations</caption>");
					if(cP.getNominationPersonList().isEmpty()) {
						out.println("<tr><td>This person has no oscar nominations.</td></tr>");
					}
					else {
						out.println("<tr><th>Film</th><th>Role</th><th>Category</th>" +
									"<th>Ceremony</th><th>Year</th><th>Won</th></tr>");
						Iterator<NominationPerson> nIter = cP.getNominationPersonList().iterator();
						while(nIter.hasNext()) {
							out.println("<tr>");
							NominationPerson nPerson = nIter.next();
							out.println("<td>" + nPerson.getFilm() + "</td>");
							out.println("<td>" + nPerson.getRole() + "</td>");
							out.println("<td>" + nPerson.getCategory() + "</td>");
							out.println("<td>" + nPerson.getCeremony() + "</td>");
							out.println("<td>" + nPerson.getYear() + "</td>");
							if(nPerson.getWon().equals("0")) {
								out.println("<td>No</td>");
							}
							else if (nPerson.getWon().equals("1")) {
								out.println("<td>Yes</td>");
							}
							out.println("</tr>");
						}
					}
					out.println("</table>");
					
					out.println("<table class='resultTable'>");
					out.println("<caption>Stars on the Walk of Fame</caption>");
					if(cP.getStarPersonList().isEmpty()) {
						out.println("<tr><td>This person has no star on the walk of fame.</td></tr>");
					}
					else {
						out.println("<tr><th>Category</th><th>Address</th></tr>");
						Iterator<StarPerson> sIter = cP.getStarPersonList().iterator();
						while(sIter.hasNext()) {
							out.println("<tr>");
							StarPerson sPerson = sIter.next();
							out.println("<td>" + sPerson.getCategory() + "</td>");
							out.println("<td>" + sPerson.getAddress() + "</td>");
							out.println("</tr>");
						}
					}
				}
			%>
			</table>
		</div>
	</body>
</html>