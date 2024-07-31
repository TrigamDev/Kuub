package dev.trigam.kuub.client.render.system.window;

public class DisplaySettings {

    public int x;
    public int y;
    public int width;
    public int height;

    public DisplayMode displayMode;
    public int fpsLimit;

    public boolean centerWindow;

    public String title;

    public DisplaySettings ( int x, int y, int width, int height, String title, DisplayMode displayMode, int fpsLimit ) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;

        this.displayMode = displayMode;
        this.fpsLimit = fpsLimit;

        this.centerWindow = false;

        this.title = title;
    }

    public DisplaySettings ( int width, int height, String title, DisplayMode displayMode, int fpsLimit ) {
        this.width = width;
        this.height = height;

        this.displayMode = displayMode;
        this.fpsLimit = fpsLimit;

        this.centerWindow = true;

        this.title = title;
    }

}
