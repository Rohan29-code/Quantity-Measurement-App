public class Main {

    // ENUM for units
    enum LengthUnit {
        FEET(1.0),
        INCH(1.0 / 12.0);

        private final double conversionFactor;

        LengthUnit(double conversionFactor) {
            this.conversionFactor = conversionFactor;
        }

        public double toBase(double value) {
            return value * conversionFactor; // convert to feet
        }
    }

    // Generic QuantityLength class
    static class QuantityLength {
        private double value;
        private LengthUnit unit;

        public QuantityLength(double value, LengthUnit unit) {
            if (unit == null) {
                throw new IllegalArgumentException("Unit cannot be null");
            }
            this.value = value;
            this.unit = unit;
        }

        public double toBaseUnit() {
            return unit.toBase(value);
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;           // reflexive
            if (obj == null) return false;          // null check
            if (getClass() != obj.getClass()) return false; // type check

            QuantityLength other = (QuantityLength) obj;

            return Double.compare(this.toBaseUnit(), other.toBaseUnit()) == 0;
        }
    }

    // MAIN METHOD (Testing)
    public static void main(String[] args) {

        QuantityLength q1 = new QuantityLength(1.0, LengthUnit.FEET);
        QuantityLength q2 = new QuantityLength(12.0, LengthUnit.INCH);
        QuantityLength q3 = new QuantityLength(2.0, LengthUnit.FEET);

        System.out.println("1 ft == 12 inch ? " + q1.equals(q2)); // true
        System.out.println("1 ft == 2 ft ? " + q1.equals(q3));    // false
        System.out.println("1 inch == 1 inch ? " +
                new QuantityLength(1.0, LengthUnit.INCH)
                        .equals(new QuantityLength(1.0, LengthUnit.INCH))); // true
    }
}