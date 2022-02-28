package com.flipkartStock;

import org.testng.ITestResult;

public class Retry {

	 int i=0;
		

		public boolean retry(ITestResult result) {
			if(i<1) {
				i++;
				System.out.println("failed test"+result.getName());
				return true;
			}
			
			return false;
			
		}
}
