package racingcar.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import racingcar.dto.CarDto;
import racingcar.dto.WinnersDto;
import racingcar.utils.FixedNumberGenerator;
import racingcar.utils.NumberGenerator;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static racingcar.domain.Cars.DUPLICATE_NAME_ERROR_MESSAGE;

class CarsTest {
    private static final int MOVABLE_NUMBER_FOR_TEST = 5;

    private static Stream<Arguments> provideRaceWinnerCases() {
        List<String> carNamesForTest = Arrays.asList("포비", "웨지", "삭정");

        List<String> winnerNames1 = Arrays.asList("포비");
        List<String> winnerNames2 = Arrays.asList("웨지");
        List<String> winnerNames3 = Arrays.asList("포비", "웨지");
        List<String> winnerNames4 = Arrays.asList("포비", "웨지", "삭정");

        return Stream.of(
                Arguments.of(carNamesForTest, winnerNames1),
                Arguments.of(carNamesForTest, winnerNames2),
                Arguments.of(carNamesForTest, winnerNames3),
                Arguments.of(carNamesForTest, winnerNames4)
        );
    }

    @DisplayName("정상적인 차 이름이 들어왔을 때 차들을 잘 생성하는지")
    @Test
    void carsConstructor_properCarNames_createCars() {
        List<String> names = Arrays.asList("포비", "데이브", "삭정");
        Cars cars = new Cars(names);

        assertThat(cars).isInstanceOf(Cars.class)
                .isEqualTo(new Cars(names));
    }

    @DisplayName("중복되는 차 이름이 들어왔을 때 예외 반환 하는지")
    @Test
    void validateDuplicateNames_givenDuplicateCarNames_throwIllegalArgumentException() {
        List<String> duplicateNames = Arrays.asList("포비", "포비", "웨지", "삭정");
        assertThatThrownBy(() -> new Cars(duplicateNames)).isInstanceOf(IllegalArgumentException.class)
                .hasMessage(DUPLICATE_NAME_ERROR_MESSAGE);
    }

    @DisplayName("자동차들이 모두 주어진 숫자에 따라 올바르게 움직이는지")
    @Test
    void moveCars_givenNumbersToMoveCars_allCarsGivenNumberOver3MoveForwardOtherwiseStay() {
        List<String> carNamesForTest = Arrays.asList("포비", "데이브", "삭정");
        Cars cars = new Cars(carNamesForTest);

        int[] numbersToMoveCars = {1, 3, 5};
        NumberGenerator numberGenerator = new FixedNumberGenerator(numbersToMoveCars);
        cars.moveCars(numberGenerator);

        int[] expectedPosition = {0, 0, 1};

        List<Car> carsAfterMove = cars.toList();
        for (int i = 0; i < expectedPosition.length; i++) {
            assertThat(carsAfterMove.get(i).getPosition()).isEqualTo(new Position(expectedPosition[i]));
        }
    }

    @DisplayName("우승자 제대로 가려내는지 테스트")
    @ParameterizedTest
    @MethodSource("provideRaceWinnerCases")
    void findWinners_proceedRacingAccordingToGivenWinnerNames_returnExpectedWinners(List<String> names, List<String> expectedWinnerNames) {
        Cars cars = new Cars(names);
        setWinnersAccordingToGivenWinnerNames(cars, expectedWinnerNames);
        WinnersDto winnersDto = new WinnersDto(cars.findWinners());

        List<String> winnerNames = winnersDto.getWinnerNames();
        assertThat(winnerNames).isEqualTo(expectedWinnerNames);
    }

    private void setWinnersAccordingToGivenWinnerNames(Cars cars, List<String> winnerNames) {
        cars.toList()
                .stream()
                .filter(car -> winnerNames.contains(new CarDto(car).getNameAsString()))
                .forEach(car -> car.move(MOVABLE_NUMBER_FOR_TEST));
    }
}