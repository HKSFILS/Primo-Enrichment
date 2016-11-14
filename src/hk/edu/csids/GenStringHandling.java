package hk.edu.csids;

import java.text.Normalizer;
import java.text.Normalizer.Form;

public class GenStringHandling {
	
	 public static String removeAccents(String text) {
		    return text == null ? null :
		        Normalizer.normalize(text, Form.NFD)
		            .replaceAll("\\p{InCombiningDiacriticalMarks}+", "");
	} //removeAccents()
	 
	 public static String trimSpecialChars(String str) {
			String[] specChars = { 
					"\\=", "\\-", "\\^", "#", "/", "@", "%", "&", "~",
					"[\\s|\t]and[\\s|\t]", "[\\s|\t]or[\\s|\t]"};
			
			String[] specChars2 = {"《", "》", "〈", "〉", ",", "「", "」",
					"\"", "？", "‘",  "’", "‚", "“", "”", "†", "‡", "‰", "‹", "›", "♠",
					"♣", "♥", "♥", "♦", "‾", "←", "↑", "→", "↓", "™", "\\+", "\\*", "'",
					"\\.", "\\\\", "\\+", "\\?", "\\[", "\\]", "\\$", "\\(", "\\)", "\\{", "\\}",
					 "\\!", "\\<", "\\>", "\\|", "。", "、", ":"};

			for (int i = 0; i < specChars.length; i++) {
				str = str.replaceAll(specChars[i], " ");
			} // end for
			
			for (int i = 0; i < specChars2.length; i++) {
				str = str.replaceAll(specChars2[i], "");
			} // end for

			str = str.replaceAll("^\\s{1,}|\\s{1,}$|^\t{1,}|\t${1,}", "");
			str = str.replaceAll("\\s{2,}|\t{2,}", " ");
			str = str.trim();
			return str;
	} // end trimSpecialChars()
	 
	public static String processGeneralString(String str) {
			str = str.toLowerCase();
			str = GenStringHandling.trimSpecialChars(str);
			str = GenStringHandling.removeAccents(str);
			//remove articles
			str = str.replaceAll("^a |^the |^an |^le |^la |^ |^die |^der |^das " +
								 "|^el |\"|&quot;|quot|&apos|apos|;", 
								 "");
			str = str.replaceAll("&| & | and | but |  |\\/|\\-", " ");
			return str;
	} // end generalStringProcess()
	
	public static String normalizeString(String str){
		if(str == null){
			return "";
		} //end if
        str = str.toUpperCase();
        str = str.replace(" ", "");
        str = str.replace("\"", "");
        str = str.replace("/", "");
        str = str.replace("-", "");
        return str;
	} //end normalizeString()
	
	public static String extractNumeric(String str){
		if(str.contains("-")){
			str = str.replaceAll("[^0-9]", "");
			str = "-" + str;
		} else {
			str = str.replaceAll("[^0-9]", "");
		} //end if
        return str;
	} //extractNumeric
	
	
} //end class