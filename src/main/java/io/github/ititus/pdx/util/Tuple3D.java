package io.github.ititus.pdx.util;

public class Tuple3D {

    private static final Tuple3D ZERO = new Tuple3D(0, 0, 0);

    private static final Tuple3D ONE = new Tuple3D(1, 0, 0);
    private static final Tuple3D TWO = new Tuple3D(2, 0, 0);
    private static final Tuple3D THREE = new Tuple3D(3, 0, 0);
    private static final Tuple3D FOUR = new Tuple3D(4, 0, 0);
    private static final Tuple3D FIVE = new Tuple3D(5, 0, 0);
    private static final Tuple3D SIX = new Tuple3D(6, 0, 0);
    private static final Tuple3D SEVEN = new Tuple3D(7, 0, 0);
    private static final Tuple3D EIGHT = new Tuple3D(8, 0, 0);
    private static final Tuple3D NINE = new Tuple3D(9, 0, 0);
    private static final Tuple3D TEN = new Tuple3D(10, 0, 0);

    private static final Tuple3D ONE_ONE = new Tuple3D(1, 1, 0);
    private static final Tuple3D TWO_TWO = new Tuple3D(2, 2, 0);
    private static final Tuple3D THREE_THREE = new Tuple3D(3, 3, 0);
    private static final Tuple3D FOUR_FOUR = new Tuple3D(4, 4, 0);
    private static final Tuple3D FIVE_FIVE = new Tuple3D(5, 5, 0);
    private static final Tuple3D SIX_SIX = new Tuple3D(6, 6, 0);
    private static final Tuple3D SEVEN_SEVEN = new Tuple3D(7, 7, 0);
    private static final Tuple3D EIGHT_EIGHT = new Tuple3D(8, 8, 0);
    private static final Tuple3D NINE_NINE = new Tuple3D(9, 9, 0);
    private static final Tuple3D TEN_TEN = new Tuple3D(10, 10, 0);

    private static final Tuple3D ONE_ONE_ONE = new Tuple3D(1, 1, 1);
    private static final Tuple3D TWO_TWO_TWO = new Tuple3D(2, 2, 2);
    private static final Tuple3D THREE_THREE_THREE = new Tuple3D(3, 3, 3);
    private static final Tuple3D FOUR_FOUR_FOUR = new Tuple3D(4, 4, 4);
    private static final Tuple3D FIVE_FIVE_FIVE = new Tuple3D(5, 5, 5);
    private static final Tuple3D SIX_SIX_SIX = new Tuple3D(6, 6, 6);
    private static final Tuple3D SEVEN_SEVEN_SEVEN = new Tuple3D(7, 7, 7);
    private static final Tuple3D EIGHT_EIGHT_EIGHT = new Tuple3D(8, 8, 8);
    private static final Tuple3D NINE_NINE_NINE = new Tuple3D(9, 9, 9);
    private static final Tuple3D TEN_TEN_TEN = new Tuple3D(10, 10, 10);

    private final double d1, d2, d3;

    private Tuple3D(double d1, double d2, double d3) {
        this.d1 = d1;
        this.d2 = d2;
        this.d3 = d3;
    }

    public static Tuple3D of() {
        return ZERO;
    }

    public static Tuple3D of(double d1) {
        if (!Double.isFinite(d1)) {
            throw new IllegalArgumentException();
        }
        if (d1 == 0) {
            return of();
        } else if (d1 == 1) {
            return ONE;
        } else if (d1 == 2) {
            return TWO;
        } else if (d1 == 3) {
            return THREE;
        } else if (d1 == 4) {
            return FOUR;
        } else if (d1 == 5) {
            return FIVE;
        } else if (d1 == 6) {
            return SIX;
        } else if (d1 == 7) {
            return SEVEN;
        } else if (d1 == 8) {
            return EIGHT;
        } else if (d1 == 9) {
            return NINE;
        } else if (d1 == 10) {
            return TEN;
        }
        return new Tuple3D(d1, 0, 0);
    }

    public static Tuple3D of(double d1, double d2) {
        if (!Double.isFinite(d1) || !Double.isFinite(d2)) {
            throw new IllegalArgumentException();
        }
        if (d2 == 0) {
            return of(d1);
        } else if (d1 == d2) {
            if (d1 == 1) {
                return ONE_ONE;
            } else if (d1 == 2) {
                return TWO_TWO;
            } else if (d1 == 3) {
                return THREE_THREE;
            } else if (d1 == 4) {
                return FOUR_FOUR;
            } else if (d1 == 5) {
                return FIVE_FIVE;
            } else if (d1 == 6) {
                return SIX_SIX;
            } else if (d1 == 7) {
                return SEVEN_SEVEN;
            } else if (d1 == 8) {
                return EIGHT_EIGHT;
            } else if (d1 == 9) {
                return NINE_NINE;
            } else if (d1 == 10) {
                return TEN_TEN;
            }
        }
        return new Tuple3D(d1, d2, 0);
    }

    public static Tuple3D of(double d1, double d2, double d3) {
        if (!Double.isFinite(d1) || !Double.isFinite(d2) || !Double.isFinite(d3)) {
            throw new IllegalArgumentException();
        }
        if (d3 == 0) {
            return of(d1, d2);
        } else if (d1 == d2 && d2 == d3) {
            if (d1 == 1) {
                return ONE_ONE_ONE;
            } else if (d1 == 2) {
                return TWO_TWO_TWO;
            } else if (d1 == 3) {
                return THREE_THREE_THREE;
            } else if (d1 == 4) {
                return FOUR_FOUR_FOUR;
            } else if (d1 == 5) {
                return FIVE_FIVE_FIVE;
            } else if (d1 == 6) {
                return SIX_SIX_SIX;
            } else if (d1 == 7) {
                return SEVEN_SEVEN_SEVEN;
            } else if (d1 == 8) {
                return EIGHT_EIGHT_EIGHT;
            } else if (d1 == 9) {
                return NINE_NINE_NINE;
            } else if (d1 == 10) {
                return TEN_TEN_TEN;
            }
        }
        return new Tuple3D(d1, d2, d3);
    }

    public static Tuple3D of(double[] array) {
        if (array == null || array.length == 0) {
            return of();
        } else if (array.length == 1) {
            return of(array[0]);
        } else if (array.length == 2) {
            return of(array[0], array[1]);
        }
        return of(array[0], array[1], array[2]);
    }

    public double getD1() {
        return d1;
    }

    public double getD2() {
        return d2;
    }

    public double getD3() {
        return d3;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Tuple3D)) {
            return false;
        }
        Tuple3D that = (Tuple3D) o;
        return d1 == that.d1 && d2 == that.d2 && d3 == that.d3;
    }

    @Override
    public int hashCode() {
        return Util.hash(d1, d2, d3);
    }

    @Override
    public String toString() {
        return "Tuple3D{" + d1 +
                ", " + d2 +
                ", " + d3 +
                '}';
    }
}
