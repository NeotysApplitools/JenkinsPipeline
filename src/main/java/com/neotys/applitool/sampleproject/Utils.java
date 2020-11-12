package com.neotys.applitool.sampleproject;


public class Utils {
	/**
	 * fetches cloud name
	 * @param cloudName
	 * @return
	 * @throws Exception
	 */
	public static String fetchServerName(String cloudName) throws Exception {
		//Verifies if cloudName is hardcoded, else loads from Maven properties 
		String finalCloudName = cloudName.equalsIgnoreCase("<<servername>>") ? System.getProperty("servername") : cloudName;
		//throw exceptions if cloudName isnt passed:
		if(finalCloudName == null || finalCloudName.equalsIgnoreCase("<<servername>>"))
			throw new Exception("Please replace <<servername>> with your applitool cloud name or pass it as maven properties: -Dservername=<<servername>");
		else
			return finalCloudName;
	}

	public static String fetchApitoken(String apiKey) throws Exception {
		//Verifies if cloudName is hardcoded, else loads from Maven properties
		String finalCloudName = apiKey.equalsIgnoreCase("<<apiKey>>") ? System.getProperty("apiKey") : apiKey;
		//throw exceptions if cloudName isnt passed:
		if(finalCloudName == null || finalCloudName.equalsIgnoreCase("<<apiKey>>"))
			throw new Exception("Please replace <<apiKey>> with your applitool api key or pass it as maven properties: -apiKey=<<apiKey>>");
		else
			return finalCloudName;
	}



	public static String fetchApplicationURL(String applicationURL) throws Exception {
		//Verifies if cloudName is hardcoded, else loads from Maven properties
		String finalApplicationURL = applicationURL.equalsIgnoreCase("<<applicationURL>>") ? System.getProperty("applicationURL") : applicationURL;
		//throw exceptions if cloudName isnt passed:
		if(finalApplicationURL == null || finalApplicationURL.equalsIgnoreCase("<<applicationURL>>"))
			throw new Exception("Please replace <<applicationURL>> with the url of your konakart applcication or pass it as maven properties: -DapplicationURL=<<applicationURL>>");
		else
			return finalApplicationURL;
	}
}

