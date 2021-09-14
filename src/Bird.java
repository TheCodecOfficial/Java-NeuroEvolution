
class Bird {

    NeuralNetwork brain;

    public float x, y;
    float vy;

    boolean dead = false;

    int score;

    double[] data;

    public Bird(NeuralNetwork brain) {
        this.brain = brain;

        x = 80;
        y = 400;
        vy = 0;

        dead = false;

        score = 0;

        data = new double[brain.layerSizes[0]];
    }

    public void reset() {
        dead = false;
        x = 80;
        y = 400;
        vy = 0;
        score = 0;
    }

    public void update() {
        vy += 0.2; // Gravity Constant
        y += vy;

        if (!dead)
            score++;
    }

    /*public void checkCollisions(obstacles){
        for (let i = 0; i < obstacles.length; i++){
            let obstacle = obstacles[i];
            if (this.x + 10 >= obstacle.x - obstacle.dx && this.x - 10 <= obstacle.x + obstacle.dx && !(this.y - 10 >= obstacle.y - obstacle.dy && this.y + 10 <= obstacle.y + obstacle.dy)){
                this.dead = true;
            }
        }
        if (this.y > height) this.dead = true;
    }*/

    public void jump() {
        vy = -4;
    }

    public void setData(double[] additionalData) {
        data[0] = y;
        data[1] = vy;
        for (int i = 2; i < data.length; i++)
            data[i] = additionalData[i - 2];
    }

    public void think() {
        double result = brain.evaluate(data).data[0][0];
        if (result > 0)
            jump();
    }

    public void increment() {
        score++;
    }

}