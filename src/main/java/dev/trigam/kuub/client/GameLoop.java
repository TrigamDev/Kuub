package dev.trigam.kuub.client;

import dev.trigam.kuub.client.render.system.window.Window;
import dev.trigam.kuub.util.Time;

import static org.lwjgl.glfw.GLFW.glfwWindowShouldClose;

public abstract class GameLoop {

    public Window window;
    private boolean running;
    private final int fpsLimit; private final int tickRate;

    public int frameCounter; public int tickCounter;
    public long lastFPSTime; public long lastTPSTime;
    public int fps; public int tps;

    private long lastTime;

    public GameLoop ( int fpsLimit, int tickRate ) {
        this.running = false;

        this.fpsLimit = fpsLimit;
        this.tickRate = tickRate;
        this.fps = 0;
        this.tps = 0;

        this.lastTime = System.nanoTime();
    }

    public void start () {
        this.running = true;

        double deltaRenderTime = 0;
        double deltaTickTime = 0;

        while ( this.running ) {
            if ( this.window != null && glfwWindowShouldClose( this.window.window ) ) this.close();

            long now = System.nanoTime();
            double delta = ( now - this.lastTime ) / Time.secondsToNanos(1);
            this.lastTime = now;

            deltaRenderTime += delta;
            deltaTickTime += delta;

            boolean render = false;

            // FPS
            if ( deltaRenderTime > 1.0 / this.fpsLimit || this.fpsLimit == 0 ) {
                render();
                frameCounter++;
                deltaRenderTime -= 1.0 / this.fpsLimit;
                render = true;
            }
            // TPS
            if ( deltaTickTime > 1.0 / this.tickRate ) {
                tick();
                tickCounter++;
                deltaTickTime -= 1.0 / this.tickRate;
            }

            // Update FPS and TPS every second
            if ( ( now - this.lastFPSTime ) / Time.secondsToNanos(1) >= 1.0 ) {
                this.fps = frameCounter;
                this.frameCounter = 0;
                this.lastFPSTime = now;
            }
            if ( ( now - this.lastTPSTime ) / Time.secondsToNanos(1) >= 1.0 ) {
                this.tps = tickCounter;
                this.tickCounter = 0;
                this.lastTPSTime = now;
            }

            if ( render ) {
                //System.out.printf("FPS: %d\nTPS: %d\n\n", this.fps, this.tps);
            }

            // Prevent CPU hogging
            try { Thread.sleep(1); }
            catch (InterruptedException e) { e.printStackTrace(); }
        }
    }

    public void close () {
        this.running = false;
    }

    public abstract void tick();
    public abstract void render();

}