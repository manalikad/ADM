package adm.data;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class UserInput {
	BufferedReader br;
	
	public String getIndustrytype() {
		System.out.println("Enter Industry type");
		br =new BufferedReader(new InputStreamReader(System.in));
		String s="";
		try {
			s = br.readLine();
		} catch (IOException e) {
			System.out.println("Enter valid industry type");
		}
		return s;
	}
	
	public String getpopulationRequired() {
		System.out.println("Enter population criteria");
		br =new BufferedReader(new InputStreamReader(System.in));
		String s="";
		try {
			s = br.readLine();
		} catch (IOException e) {
			System.out.println("Enter valid population");
		}
		return s;
	}
	
	public String getRequiredLiteracyRate() {
		System.out.println("Enter required literacy rate");
		br =new BufferedReader(new InputStreamReader(System.in));
		String s="";
		try {
			s = br.readLine();
		} catch (IOException e) {
			System.out.println("Enter valid literacy rate");
		}
		return s;
	}
	
	public String getRequiredTransportDensity() {
		System.out.println("Enter transport density");
		br =new BufferedReader(new InputStreamReader(System.in));
		String s="";
		try {
			s = br.readLine();
		} catch (IOException e) {
			System.out.println("Enter valid transport density");
		}
		return s;
	}
	
	public int getRequiredBudget() {
		System.out.println("Enter required budget in $/sqft");
		br =new BufferedReader(new InputStreamReader(System.in));
		String s="";
		int budget=0;
		try {
			s = br.readLine();
			budget = Integer.parseInt(s);
		} catch (IOException e) {
			System.out.println("Enter valid budget");
		}
		return budget;
	}
	
	public String convertToCamelCase(String s)
	{
		return (s.substring(0,1)).toUpperCase()+(s.substring(1)).toLowerCase();
	}
}
