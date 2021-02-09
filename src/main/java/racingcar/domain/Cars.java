package racingcar.domain;

import racingcar.utils.RandomGenerator;

import java.util.*;
import java.util.stream.Collectors;

public class Cars {
    public static final String DUPLICATE_NAME_ERROR_MESSAGE = "[ERROR] 동일한 이름이 있습니다.";

    private final List<Car> cars = new ArrayList<>();

    private Cars() {
    }

    public Cars(List<String> names) {
        validateDuplicateNames(names);
        createCars(names);
    }

    private void createCars(List<String> names) {
        names.stream()
                .map(Name::new)
                .map(Car::new)
                .forEach(cars::add);
    }

    public static void validateDuplicateNames(List<String> names) {
        Set<String> nameSet = new HashSet<>(names);
        if (nameSet.size() != names.size()) {
            throw new IllegalArgumentException(DUPLICATE_NAME_ERROR_MESSAGE);
        }
    }

    public List<Car> toList() {
        return Collections.unmodifiableList(new ArrayList<>(cars));
    }

    public Cars copy() {
        Cars carsCopied = new Cars();
        cars.stream()
                .map(Car::copy)
                .forEach(carsCopied.cars::add);

        return carsCopied;
    }

    public void moveCars() {
        cars.forEach(car -> car.move(RandomGenerator.generateRandomNumber()));
    }

    public List<Car> findWinners() {
        Position maxPosition = getMaxPosition();
        return cars.stream()
                .filter(car -> car.isSamePosition(maxPosition))
                .collect(Collectors.toList());
    }

    private Position getMaxPosition() {
        int maxPosition = cars.stream()
                .map(Car::getPosition)
                .mapToInt(Position::getPosition)
                .max()
                .orElseThrow(NoSuchElementException::new);

        return new Position(maxPosition);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cars cars1 = (Cars) o;
        return cars.equals(cars1.cars);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cars);
    }
}