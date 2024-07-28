package sample.cafekiosk.unit;

import org.junit.jupiter.api.Test;
import sample.cafekiosk.unit.beverages.Americano;
import sample.cafekiosk.unit.beverages.Latte;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class CafeKioskTest {

    @Test
    void add_수동테스트() {
        CafeKiosk cafeKiosk = new CafeKiosk();
        cafeKiosk.add(new Americano());

        System.out.println(">>>> 담긴 음료 수 : " + cafeKiosk.getBeverages().size());
        System.out.println(">>>> 담긴 음료 : " + cafeKiosk.getBeverages().get(0).getName());

        // 테스트를 작성해봤습니다. 근데 문제점이 있지 않습니까.
        // 테스트 코드의 장점은 테스트를 자동화할 수 있는 것이라 했습니다.
        // 근데 이건 직접 사람이 확인하고 있는데요.
        // 뭔가 잘못됐죠. 이건 수동테스트입니다.

        // 문제점
        // 1. 사람이 직접 확인해야함
        // 2. 다른 사람이 봤을때 뭘 검증해야하는지 모르고 어떤게 맞고 틀린지도 확인이 불가능 함
        // 3. 무조건 성공하는 케이스임

    }

    @Test
    void add_자동테스트() {
        CafeKiosk cafeKiosk = new CafeKiosk();
        cafeKiosk.add(new Americano());

        assertThat(cafeKiosk.getBeverages()).hasSize(1);
        assertThat(cafeKiosk.getBeverages().get(0).getName()).isEqualTo("아메리카노");
    }

    @Test
    void remove() {
        CafeKiosk cafeKiosk = new CafeKiosk();
        Americano americano = new Americano();

        cafeKiosk.add(americano);
        assertThat(cafeKiosk.getBeverages()).hasSize(1);

        cafeKiosk.remove(americano);
        assertThat(cafeKiosk.getBeverages()).isEmpty();
    }

    @Test
    void clear() {
        CafeKiosk cafeKiosk = new CafeKiosk();
        Americano americano = new Americano();
        Latte latte = new Latte();

        cafeKiosk.add(americano);
        cafeKiosk.add(latte);
        assertThat(cafeKiosk.getBeverages()).hasSize(2);

        cafeKiosk.clear();
        assertThat(cafeKiosk.getBeverages()).isEmpty();
    }

}