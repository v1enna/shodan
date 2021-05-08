package Control;

import Model.Article;
import Service.ArticleService;
import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/BlogServlet")
public class BlogServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;

	protected void doGet(
		HttpServletRequest request,
		HttpServletResponse response
	) throws ServletException, IOException {
		System.out.println("# BlogServlet > Session: " + request.getSession().getId());
		
		switch(request.getParameter("action")) {
			case "blog":
				int limit = request.getParameter("limit") != null 
							? Integer.parseInt(request.getParameter("limit")) 
							: 0;
	
				ArrayList<Article> articles = new ArticleService().getAllArticles(limit);
				
				if(articles != null) {
					request.getSession().setAttribute("articles", articles);
					response.setStatus(200);
				} else 
					response.setStatus(400);
				
				System.out.println("# BlogServlet > GET > Tutti gli articoli");
				
				break;
			
			case "article":
				int blog_id = request.getParameter("blog_id") != null 
							? Integer.parseInt(request.getParameter("blog_id")) 
							: 0;
				
				Article article = new ArticleService().getArticle(blog_id);
				
				System.out.println("# BlogServlet > GET > " + article.toString());
				
				request.getSession().setAttribute("article", article);
				
				break;
				
			default:
				System.out.println("# BlogServlet > GET > Nessuna azione specificata");
				
				break;
		}
	}
	
	protected void doPost(
			HttpServletRequest request,
			HttpServletResponse response
	) throws ServletException, IOException {
		System.out.println("# BlogServlet > Session: " + request.getSession().getId());
		
		switch(request.getParameter("action")) {
			case "addArticle":
			
				new ArticleService().addArticle(request.getParameter("add-article-title"),
						request.getParameter("article-shortTitle"), 
						request.getParameter("article-html"));
		
				request.setAttribute("messageArticleAdd", "Articolo aggiunto con successo");
				request.getRequestDispatcher("admin.jsp").forward(request, response);
				response.sendRedirect("admin.jsp");
		
				System.out.println("# BlogServlet > POST > Articolo aggiunto > " + request.getParameter("add-article-title"));
		
				break;
		
			case "deleteArticle":
		
				Article article = new ArticleService().getArticle(Integer.valueOf(request.getParameter("delete-article-id")));
				
				if(article != null) {
				new ArticleService().deleteArticle(Integer.valueOf(request.getParameter("delete-article-id")));
		
				request.setAttribute("messageArticleDelete", "Articolo eliminato con successo");
				request.getRequestDispatcher("admin.jsp").forward(request, response);
				
				System.out.println("# BlogServelt > POST > Articolo eliminato > " + article.getTitle());
				}
				else {
					request.setAttribute("errorMessageArticleDelete", "Articolo non presente");
					request.getRequestDispatcher("admin.jsp").forward(request, response);
					
					System.out.println("# BlogServelt > POST > Articolo insistente > " + request.getParameter("add-article-title"));
				}
		
			default:
				System.out.println("# BlogServelt > POST > Nessuna azione specificata");
			
			break;
		}
	}
	
}
