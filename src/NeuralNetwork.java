
import java.util.function.Function;

/*// Simple activation function TANH
function sigmoid(x) {
    return Math.tanh(x);
}

// Simple mutation function
function mut(x) {
    return (random() < MUTATION_RATE) ? random() : x;
}*/

class NeuralNetwork {

    int layers;
    int[] layerSizes;
    Matrix[] weights;
    int totalParameters;

    final double MUTATION_RATE = 0.01;

    Function<Double, Double> sigmoid = x -> Math.tanh(x);
    Function<Double, Double> mutate = x -> (Math.random() < MUTATION_RATE) ? Math.random() : x; 

    public NeuralNetwork(int[] size) {
        layers = size.length;
        layerSizes = size;
        weights = new Matrix[layers - 1];

        for (int i = 0; i < layers - 1; i++) {
            weights[i] = new Matrix(layerSizes[i + 1], layerSizes[i] + 1);
            weights[i].randomize();

            totalParameters += layerSizes[i + 1] * (layerSizes[i] + 1);
        }
    }

    public Matrix evaluate(double[] input) {

        // Convert to neuralArray, which adds an additional entry 1 for bias
        Matrix values = Matrix.getNeuralVector(input);

        // Loop through weights and propagate values
        for (int i = 0; i < layers - 1; i++) {
            Matrix newValues = Matrix.multiply(weights[i], values);
            newValues.map(sigmoid);
            if (i < layers - 2) newValues.extendByOne();
            values = newValues;
        }
        return values;
    }

    // Randomly changes each weight and bias with chanche mutationRate
    public void mutate() {
        for (int i = 0; i < layers - 1; i++) {
            weights[i].map(mutate);
        }
    }

    // Returns a new but identical neural network
    public NeuralNetwork copy() {

        NeuralNetwork net = new NeuralNetwork(new int[]{});

        net.layers = layers;
        net.layerSizes = new int[layers];
        for (int i = 0; i < layers; i++) net.layerSizes[i] = layerSizes[i];
        net.totalParameters = totalParameters;

        net.weights = new Matrix[weights.length];
        for (int i = 0; i < weights.length; i++) net.weights[i] = weights[i].copy();

        return net;
    }
}