package com.landg.ecom.util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class GenerateScript {
	
	public int readFile(String location, String destination, String application) {
		BufferedReader objReader = null;
		System.out.println(location);
		int count = 0;
		try {
			String strCurrentLine;
			
			objReader = new BufferedReader(new FileReader(location));
			String partyid="";
			String preSelectQuery="",postSelectQuery="",insertQuery="";
			
			while ((strCurrentLine = objReader.readLine()) != null) {
				partyid = strCurrentLine;
				System.out.println(partyid);
				if(application == "REP") {
					generateREPScript(partyid);
				}
				else if(application == "MyAccounts") {
					
					genrateMyAccountScript(partyid);
				}
				count++;
			}

		} catch (IOException e) {

			e.printStackTrace();

		} finally {

			try {
				if (objReader != null)
					objReader.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		
		return count;
	}
	
	private String[] generateREPScript(String partyid) {
		String[] queries = null;
		
		return queries;
		
	}
	
	private String genrateMyAccountScript(String partyid) {
		String query="";
		
		return query;
	}

}
