package racingcar.domain;

import java.util.Objects;

public class Car {
    public static final Position INIT_POSITION = new Position(0);
    private static final int MOVABLE_VALUE = 4;

    private final Name name;
    private Position position;

    public Car(Name name) {
        this(name, INIT_POSITION);
    }

    public Car(Name name, Position position) {
        this.name = name;
        this.position = position;
    }

    public void move(int number) {
        if (isMovable(number)) {
            this.position = position.getPositionAfterMove();
        }
    }

    private boolean isMovable(int number) {
        return number >= MOVABLE_VALUE;
    }

    public boolean isSamePosition(Position position) {
        return this.position.equals(position);
    }

    public Position getPosition() {
        return new Position(position.getPosition());
    }

    public Name getName() {
        return new Name(name.getName());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Car car = (Car) o;
        return position.equals(car.position) && name.equals(car.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(position, name);
    }
}