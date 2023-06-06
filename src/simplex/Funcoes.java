package simplex;

public class Funcoes {
    public static boolean isNumericInteger(String str) {
    	try {
    		Integer.parseInt(str);
    		return true;
    	} catch (NumberFormatException e) {
    		return false; 
    	}
    }
    public static boolean isNumericDouble(String str) {
    	try {
    		Double.parseDouble(str);
    		return true;
    	} catch (NumberFormatException e) {
    		return false; 
    	}
    }
    
    public class Retorno {
    	
    }
}
