package fi.yussiv.squash;

import fi.yussiv.squash.test.Test;
import fi.yussiv.squash.ui.GUI;
import static fi.yussiv.squash.ui.GUI.run;

public class Main {

    public static void main(String[] args) throws Exception {
        if (args.length > 0 && args[0].equals("test")) {
            Test.runTests();
        } else {
            run(new GUI(), 600, 250);
        }
    }
}
