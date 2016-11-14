package com.exlibris.primo.api.plugins.enrichment;

import java.util.Map;
import hk.edu.csids.*;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import com.exlibris.primo.api.common.IMappingTablesFetcher;
import com.exlibris.primo.api.common.IPrimoLogger;

public class DedupChineseHandling implements EnrichmentPlugin {

	@Override
	public Document enrich(Document xmlDoc, IEnrichmentDocUtils docUtil)
			throws Exception {
		// TODO Auto-generated method stub
		
		String dedupkeys[] = { "c3", "f5", "f7", "f10", "f11" };
		CJKStringHandling ch = new CJKStringHandling();
		NodeList nl;
		String dedupvalues[];
		for(int i=0; i<dedupkeys.length; i++){
			nl = xmlDoc.getElementsByTagName(dedupkeys[i]);
			if(nl.getLength() != 0){
				dedupvalues = docUtil.getValuesBySectionAndTag(xmlDoc, "dedup", dedupkeys[i]);
				if(dedupvalues[0] != null){
					ch.setOriginalString(dedupvalues[0]);
					if(ch.isCJK()){
						boolean csids = false;
						if(ch.getOriginalString().contains("CSIDS")){
							csids = true;						
						} //end if
						ch.removeNonCJKChars();
						ch.standardizeVariantChinese();
						ch.convertToSimpChinese();
						String resultStr = ch.getResultString();
						if(csids){
							resultStr = "CSIDS" + resultStr;
						} //end if
						docUtil.overrideValue(xmlDoc, "dedup", dedupkeys[i], resultStr);
					} //end if
				} //end if
			} //end if
		} //end for
		return xmlDoc;
	}

	@Override
	public void init(IPrimoLogger arg0, IMappingTablesFetcher arg1,
			Map<String, Object> arg2) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean shouldSkipFailedRecord(Document arg0, Exception arg1) {
		// TODO Auto-generated method stub
		return false;
	}

} //end class