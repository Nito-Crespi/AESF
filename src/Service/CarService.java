package Service;

import java.util.ArrayList;

import Objects.Car;
import Resources.StateConstants;

public class CarService {

    public void showActiveCar(ArrayList<Car> cars) {
        new StateConstants();
        filterByState(cars, StateConstants.ACTIVE);
    }

    public void showInactiveCar(ArrayList<Car> cars) {
        new StateConstants();
        filterByState(cars, StateConstants.INACTIVE);
    }

    private void filterByState(ArrayList<Car> cars, String state) {
        for (int i = cars.size() - 1; i >= 0; i--) {
            if (!isMatchingState(cars.get(i), state)) {
                cars.remove(i);
            }
        }
    }

    private boolean isMatchingState(Car car, String type) {
        return car.getState().contains(type);
    }

}
