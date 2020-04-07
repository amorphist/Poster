package az.siftoshka.habitube.entities.firebase;

public class Media {

    private int id;
    private float rate;

    public Media() {
    }

    public Media(int id, float rate) {
        this.id = id;
        this.rate = rate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public float getRate() {
        return rate;
    }

    public void setRate(float rate) {
        this.rate = rate;
    }
}
