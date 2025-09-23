package cz.vse.adventurefx.logic;

/**
 * Třída Password generuje náhodné osmiciferné heslo při načtení třídy
 * které je potřeba zadat pro dokončení hry - to umožní menší replayability
 */
public class Password {
    public static String password;

    static {
        generatePassword();
    }

    /**
     * Vygeneruje náhodné 8místné číselné heslo a uloží ho do statické proměnné 'password'.
     */
    private static void generatePassword() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 8; i++) {
            int randomNum = (int) (Math.random() * 10);
            sb.append(randomNum);
        }
        password = sb.toString();
    }
}