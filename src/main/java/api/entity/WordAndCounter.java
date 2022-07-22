package api.entity;

import java.util.Objects;

public class WordAndCounter implements Comparable<WordAndCounter> {
    private String word;
    private int counter;

    public WordAndCounter(String word, int counter) {
        this.word = word;
        this.counter = counter;
    }
    
    public void incrementCounter() {
    	counter++;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public int getCounter() {
        return counter;
    }

    public void setCounter(int counter) {
        this.counter = counter;
    }

    @Override
    public int compareTo(WordAndCounter o) { //Sorted from biggest value to lowest
        return o.counter - counter;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WordAndCounter that = (WordAndCounter) o;
        return counter == that.counter && word.equals(that.word);
    }

    @Override
    public int hashCode() {
        return Objects.hash(word, counter);
    }

    @Override
    public String toString() {
        return "WordAndCounter{" +
                "word='" + word + '\'' +
                ", counter=" + counter +
                '}';
    }
}
