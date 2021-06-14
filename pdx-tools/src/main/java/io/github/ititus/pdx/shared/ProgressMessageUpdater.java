package io.github.ititus.pdx.shared;

@FunctionalInterface
public interface ProgressMessageUpdater {

    void updateProgressMessage(int index, boolean visible, int workDone, int totalWork, String msg);

}