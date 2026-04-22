public class Main {

    // ENUM for all units (extended)
    enum LengthUnit {
        FEET(1.0),
        INCH(1.0 / 12.0),
        YARD(3.0),                         // 1 yard = 3 feet
        CENTIMETER(0.393701 / 12.0);       // 1 cm = 0.393701 inch → convert to feet

        private final double conversionFactor;

        LengthUnit(double conversionFactor) {
            this.conversionFactor = conversionFactor;
        }

        public double toBase(double value) {
            return value * conversionFactor; // convert everything → feet
        }
    }

    // Generic QuantityLength class (UNCHANGED)
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
            if (this == obj) return true;               // reflexive
            if (obj == null) return false;              // null check
            if (getClass() != obj.getClass()) return false; // type safety

            QuantityLength other = (QuantityLength) obj;

            return Double.compare(this.toBaseUnit(), other.toBaseUnit()) == 0;
        }
    }

    // MAIN METHOD (Testing UC4)
    public static void main(String[] args) {

        // Yard ↔ Feet
        System.out.println(
                new QuantityLength(1.0, LengthUnit.YARD)
                        .equals(new QuantityLength(3.0, LengthUnit.FEET))
        ); // true

        // Yard ↔ Inches
        System.out.println(
                new QuantityLength(1.0, LengthUnit.YARD)
                        .equals(new QuantityLength(36.0, LengthUnit.INCH))
        ); // true

        // CM ↔ Inches
        System.out.println(
                new QuantityLength(1.0, LengthUnit.CENTIMETER)
                        .equals(new QuantityLength(0.393701, LengthUnit.INCH))
        ); // true

        // Same unit
        System.out.println(
                new QuantityLength(2.0, LengthUnit.YARD)
                        .equals(new QuantityLength(2.0, LengthUnit.YARD))
        ); // true

        // Not equal
        System.out.println(
                new QuantityLength(1.0, LengthUnit.CENTIMETER)
                        .equals(new QuantityLength(1.0, LengthUnit.FEET))
        ); // false
    }
}