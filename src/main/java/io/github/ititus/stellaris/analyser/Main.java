package io.github.ititus.stellaris.analyser;

import io.github.ititus.stellaris.analyser.save.StellarisSave;

public class Main {

    private static final String SAVE = "C:\\Users\\Vella\\Documents\\Paradox Interactive\\Stellaris\\save games\\mpomnidirective_20173703";

    private static final String[] TEST_FILES = {"C:\\Users\\Vella\\Desktop\\test.txt"/*, "C:\\Users\\Vella\\Documents\\Paradox Interactive\\Stellaris\\settings.txt", "C:\\Users\\Vella\\Documents\\Paradox Interactive\\Stellaris\\user_empire_designs.txt"*/};

    public static void main(String[] args) {

        StellarisSave stellarisSave = StellarisSave.loadNewest(SAVE);
        /*List<PdxScriptFile> testScripts = Arrays.stream(TEST_FILES).map(File::new).map(PdxScriptFile::new).collect(Collectors.toList());
        for (PdxScriptFile testScript : testScripts) {
            System.out.println(testScript.toPdxScript());
        }*/
        System.out.println("done");

    }
}
