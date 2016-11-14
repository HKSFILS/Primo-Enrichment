package com.exlibris.primo.api.plugins.enrichment;

import java.util.Map;
import hk.edu.csids.*;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import com.exlibris.primo.api.common.IMappingTablesFetcher;
import com.exlibris.primo.api.common.IPrimoLogger;

public class FrbrChineseHandling implements EnrichmentPlugin {

	@Override
	public Document enrich(Document xmlDoc, IEnrichmentDocUtils docUtil)
			throws Exception {
		// TODO Auto-generated method stub
		
		String keys[] = { "k1", "k2", "k3"};
		String values[];
		CJKStringHandling ch = new CJKStringHandling();
		for(int i=0; i<keys.length; i++){
			NodeList nl = xmlDoc.getElementsByTagName(keys[i]);
			boolean changeFlag = false;
			if(nl.getLength() != 0){
				values = docUtil.getValuesBySectionAndTag(xmlDoc, "frbr", keys[i]);
				for(int j=0; j<values.length; j++){
					ch.setOriginalString(values[j]);
					if(ch.isCJK()){
						//ch.removeNonCJKChars();
						ch.standardizeVariantChinese();
						ch.convertToSimpChinese();
						if(values[j].contains("CSIDS")){
							ch.removeNonCJKChars();
							values[j] = ch.getResultString();
							values[j] = "$$KCSIDS" + values[j];
						} else {
							ch.removeNonCJKChars();
							values[j] = ch.getResultString();
							values[j] = "$$K" + values[j];
						} //end if
						
						if(keys[i].equals("k1")){
							values[j] = values[j] + "$$AA";
						} else if(keys[i].equals("k2")){
							values[j] = values[j] + "$$ATO";
						} else if(keys[i].equals("k3")){
							values[j] = values[j] + "$$AT";
						} //end if
						
						changeFlag = true;
					} //end if
				} //end for
				if(changeFlag){
					docUtil.removeField(xmlDoc, "frbr", keys[i]);
					docUtil.addTags(xmlDoc, "frbr", keys[i], values);
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