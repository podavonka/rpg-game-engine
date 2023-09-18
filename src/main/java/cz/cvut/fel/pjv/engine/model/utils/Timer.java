package cz.cvut.fel.pjv.engine.model.utils;

import cz.cvut.fel.pjv.engine.model.game.GamePanel;

/**
 * Calculates time from updates rate.
 */
public class Timer {
    private int actualUpdates;

    public Timer() {
        this.actualUpdates = 0;
    }

    /**
     * Counts number of updates.
     */
    public void update() {
        actualUpdates++;
    }

    /**
     * Calculates updates from frames.
     *
     * @param timeInSeconds Time of updates.
     * @return Number of updates in a given time.
     */
    public int getUFS(int timeInSeconds) {
        return timeInSeconds * GamePanel.UPS;
    }

    /**
     * Translates time from the number
     * of updates to minutes and seconds.
     *
     * @return Line with formatted time.
     */
    public String getFormattedTime() {
        StringBuilder stringBuilder = new StringBuilder();
        int minutes = actualUpdates / GamePanel.UPS / 60;
        int seconds = actualUpdates / GamePanel.UPS % 60;

        if(minutes < 10)
            stringBuilder.append(0);
        stringBuilder.append(minutes);
        stringBuilder.append(":");

        if(seconds < 10)
            stringBuilder.append(0);
        stringBuilder.append(seconds);

        return stringBuilder.toString();
    }
}
