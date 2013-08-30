package biz;

import java.util.List;

import org.snu.ids.ha.index.Keyword;
import org.snu.ids.ha.index.KeywordExtractor;
import org.snu.ids.ha.index.KeywordList;
import org.snu.ids.ha.ma.MExpression;
import org.snu.ids.ha.ma.MorphemeAnalyzer;
import org.snu.ids.ha.ma.Sentence;
import org.snu.ids.ha.util.Timer;

import entity.KkmResult;

public class kkmModule {
	public static KkmResult maaTest(String data)
	{
		KkmResult result=null;
		String SmplStr2 = "";
		String Exp = "";
		
		try {
			MorphemeAnalyzer ma = new MorphemeAnalyzer();
			ma.createLogger(null);
			//Timer timer = new Timer();
			//timer.start();
			List<MExpression> ret = ma.analyze(data);
			//timer.stop();
			//timer.printMsg("Time");
			ret = ma.postProcess(ret);

			ret = ma.leaveJustBest(ret);

			List<Sentence> stl = ma.divideToSentences(ret);
			for( int i = 0; i < stl.size(); i++ ) {
				Sentence st = stl.get(i);
				//System.out.println("=============================================  " + st.getSentence());
				for( int j = 0; j < st.size(); j++ ) {
					SmplStr2 += st.get(j).getSmplStr2()+" ";
					Exp += st.get(j).getExp()+" ";
					//returnData += st.get(j).getExp()+"  -  "+st.get(j).getSmplStr2();
					//returnData += ",   FirstMorp:"+st.get(j).getFirstMorp();
					//returnData += ",   LastMorp:"+st.get(j).getLastMorp();
					//returnData += "\r\n";//Textarea에서 줄바꿈을 위해 포함
			
				}
			}
			
			result = new KkmResult(SmplStr2,Exp);
			ma.closeLogger();
		} catch (Exception e) {
			e.printStackTrace();
		}
		//System.out.println("처리완료");
		return result;
	}
	
	public static String keTest(String data){
		String returnData = "";
		KeywordExtractor ke = new KeywordExtractor();
		KeywordList kl = ke.extractKeyword(data, true);
		for( int i = 0; i < kl.size(); i++ ) {
			Keyword kwrd = kl.get(i);
			//System.out.println(kwrd.getString() + "\t" + kwrd.getCnt());
			returnData += kwrd.getString() + "\t" + kwrd.getCnt();
			returnData += "\r\n";//Textarea에서 줄바꿈을 위해 포함
		}
		
		return returnData;
	}
}
