package pascal.subset.interpreter.tokens;

public enum KeyWords {
   PROCEDURE("PROCEDURE"),
   PROGRAM("PROGRAM"),
   VAR("VAR"),
   DIV("DIV"),
   BEGIN("BEGIN"),
   END("END");

   final String keyWord;

   KeyWords(final String keyWord) {
      this.keyWord = keyWord;
   }

   public String keyWord() {
      return keyWord;
   }

   public static boolean isKeyWord(final String s) {
      for (final KeyWords value : KeyWords.values()) {
         if (s.compareToIgnoreCase(value.keyWord) == 0) {
            return true;
         }
      }

      return false;
   }

   @Override
   public String toString() {
      return "KeyWord{" +
              "keyWord='" + keyWord + '\'' +
              '}';
   }
}
