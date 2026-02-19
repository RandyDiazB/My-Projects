public class Main {
    public static void main(String[] args) {
        palindromeCheck("tacocat");
    }
         
   public static boolean palindromeCheck(String str){
        String wordHolder = "";
        int wordLength =  str.length();
    
        for (int i = wordLength - 1; i >= 0; i--){
            wordHolder = wordHolder + str.charAt(i);
            System.out.print(wordHolder);
        }

        

        if (str.equals(wordHolder)){
            System.out.print("true");
            return true;
        }else{
            System.out.print("false");
            return false;
    

        }

    }   
    
}
