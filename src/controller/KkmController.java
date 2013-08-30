package controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import DB.Biz.NewsDB_Biz;
import biz.kkmModule;

import com.Entity.Knowledge;
import com.Entity.Morpheme;
import com.Entity.PatternStructure;
import com.KnowledgeExtractor.KnowledgeExtractor;
import com.Parser.PatternParser;
import com.Parser.SentenceParser;
import com.Entity.SentenceStructure;


import entity.KkmResult;
import entity.NewsSentence;

/**
 * Servlet implementation class KkmController
 */
@WebServlet("/KkmController")
public class KkmController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	@Override
	public void init(ServletConfig config) throws ServletException {
		// TODO Auto-generated method stub
		super.init(config);

		//kkmModule.maaTest("초기화 및 실행");
	}
    /**
     * Default constructor. 
     */
    public KkmController() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=euc-kr");
		request.setCharacterEncoding("euc-kr");
		PrintWriter out = response.getWriter();
		
		
		
		String data = request.getParameter("string");
		
		/*
		SentenceStructure result;
		KkmResult returnData = null;
		String returnKeData = "";
		//String patternData ="";
		if(request.getParameter("action").equals("maa")){
			System.out.println("ma 테스트");
			
			
			
			returnData = kkmModule.maaTest(data);
			
			//sentenceData.add(returnData.getSmplStr2());
			//patternData = patternMatcherModule.patternMatcher(returnData.getSmplStr2());
			
			
			KnowledgeExtractor KE = new KnowledgeExtractor();
			ArrayList<String> patterns = KE.PatternFileReader();
			
			System.out.println(patterns.get(0));
			
			PatternParser PP = new PatternParser();
			SentenceParser SP = new SentenceParser();
			String resultS = "";
			String resultO = "";
			for(int i=0;i<patterns.size();i++){
				PatternStructure PS =  PP.PatternSubstitution(patterns.get(i));
				SentenceStructure SS = SP.SentenceSubstitution(returnData.getExp(),returnData.getSmplStr2());
				Knowledge knowledge = KE.KnowledgeExtractorSequence(PS,SS);
				if(knowledge.getO()!=null&&knowledge.getS()!=null){
					com.Entity.SentenceStructure S = knowledge.getS();
					com.Entity.SentenceStructure O = knowledge.getO();
					
					for(int x=0;x < S.getSentenceStructure().size(); x++){
						ArrayList<Morpheme> morphemeList = S.getSentenceStructure().get(x).getWord();
						for(int y=0; y < morphemeList.size();y++){
							resultS+=morphemeList.get(y).getCharacter();
						}
						resultS+=" ";
					}
					
					for(int x=0;x < O.getSentenceStructure().size(); x++){
						resultO+=O.getSentenceStructure().get(x).getExp();
						resultO+=" ";
					}
				}
				
			}
			
			
			
			
			
			
			request.setAttribute("exp", returnData.getExp());
			request.setAttribute("String", returnData.getSmplStr2());
			request.setAttribute("results", resultS);
			request.setAttribute("resulto", resultO);
		}else{
			System.out.println("ke 테스트");
			returnKeData = kkmModule.keTest(data);
			request.setAttribute("String", returnKeData);
		}
		
		request.setAttribute("input", data);
		//request.setAttribute("patternData", patternData);
		
		RequestDispatcher rd = request.getRequestDispatcher("index.jsp");
		rd.forward(request, response);
		*/
		
		ArrayList<NewsSentence> newsResult;
		NewsDB_Biz dbBiz = new NewsDB_Biz();
		newsResult = dbBiz.selectAll();
		
		

		
		
		
		
		
		
		
		for(int z =0; z < newsResult.size();z++){
			//System.out.println(newsResult.get(z).getSentence());
			
			newsResult.get(z).setSentence(newsResult.get(z).getSentence().replace("/", "／"));//파싱을 위한 / 체거
			newsResult.get(z).setSentence(newsResult.get(z).getSentence().replace("+", "＋"));//파싱을 위한 / 체거
			KnowledgeExtractor KE = new KnowledgeExtractor();
			ArrayList<String> patterns = KE.PatternFileReader();
			ArrayList<Knowledge> resultKnowledge = new ArrayList<Knowledge>();
			
			KkmResult returnData = kkmModule.maaTest(newsResult.get(z).getSentence());
			 for(int i=0;i<patterns.size();i++){
				String resultS = "";
				String resultO = "";
				PatternParser PP = new PatternParser();
				SentenceParser SP = new SentenceParser();
				PatternStructure PS =  PP.PatternSubstitution(patterns.get(i));
				SentenceStructure SS = SP.SentenceSubstitution(returnData.getExp(),returnData.getSmplStr2());
				Knowledge knowledge = new Knowledge();
				knowledge = KE.KnowledgeExtractorSequence(PS,SS);
				if(knowledge.getO()!=null&&knowledge.getS()!=null){
					resultKnowledge.add(knowledge);
					com.Entity.SentenceStructure S = knowledge.getS();
					com.Entity.SentenceStructure O = knowledge.getO();
					
					for(int x=0;x < S.getSentenceStructure().size(); x++){
						ArrayList<Morpheme> morphemeList = S.getSentenceStructure().get(x).getWord();
						for(int y=0; y < morphemeList.size();y++){
							resultS+=morphemeList.get(y).getCharacter();
						}
						resultS+=" ";
					}
					
					for(int x=0;x < O.getSentenceStructure().size(); x++){
						resultO+=O.getSentenceStructure().get(x).getExp();
						resultO+=" ";
					}
					out.println("<font size=4>");
					out.println("매치-------"+patterns.get(i)+"<br>");
					
					out.println(newsResult.get(z).getSentence()+"<br>");
					out.println(returnData.getSmplStr2()+"<br>");
					out.println("S :"+resultS+"<br>");
					out.println("O:"+resultO+"<br>");
					out.println(" <br>");
					out.println("</font>");
					
					
					//Log.println("Knowledge", );
					//Log.println("Knowledge", returnData.getExp());
					//Log.println("Knowledge", "S :"+resultS+"   O:"+resultO);
					
				}
				
				
				
			}
			
		}
		
		//request.setAttribute("knowledge", resultKnowledge);
		//request.setAttribute("String", "ok");
		
		//RequestDispatcher rd = request.getRequestDispatcher("index.jsp");
		//rd.forward(request, response);
		
		
	}

}
