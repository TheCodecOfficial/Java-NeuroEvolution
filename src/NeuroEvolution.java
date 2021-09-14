
import processing.core.PApplet;

public class NeuroEvolution extends PApplet {

    static Bird bird;

    public static void main(String[] args) {

        String[] processingArgs = { "Flappy Bingus" };
        NeuroEvolution mySketch = new NeuroEvolution();
        PApplet.runSketch(processingArgs, mySketch);

        NeuralNetwork brain = new NeuralNetwork(new int[] { 4, 4, 1 });
        bird = new Bird(brain);

    }

    public void settings() {
        size(800, 800);
    }

    public void draw() {
        background(155);
        bird.setData(new double[]{0, 0});
        bird.think();
        bird.update();

        if (bird.dead)
            return;
        fill(255, 0, 50);
        rectMode(CENTER);
        rect(bird.x, bird.y, 20, 20);

        frameRate(120);
        fill(0);
        text("Generation " + "0", 10, 20);
        text("FPS: " + floor(frameRate), 10, 35);
        text("Highscore: " + "0", 10, 50);

    }

    public void mousePressed() {
        bird.jump();
    }
}