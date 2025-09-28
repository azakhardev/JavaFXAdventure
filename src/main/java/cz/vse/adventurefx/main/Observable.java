package cz.vse.adventurefx.main;

/**
 * Influencer
 */
public interface Observable {
    void addObserver(GameChange gameChange, Observer observer);
}
