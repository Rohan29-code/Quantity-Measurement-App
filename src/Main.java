public class Main {

    // ENUM with conversion factors (base = FEET)
    enum LengthUnit {
        FEET(1.0),
        INCH(1.0 / 12.0),
        YARD(3.0),
        CENTIMETER(0.393701 / 12.0); // cm → inch → feet

        private final double factor;

        LengthUnit(double factor) {
            this.factor = factor;
        }

        public double toBase(double value) {
            return value * factor; // convert → feet
        }

        public double fromBase(double baseValue) {
            return baseValue / factor; // feet → target
        }
    }

    // Quantity class (IMMUTABLE STYLE)
    static class QuantityLength {
        private final double value;
        private final LengthUnit unit;

        public QuantityLength(double value, LengthUnit unit) {
            if (unit == null)
                throw new IllegalArgumentException("Unit cannot be null");
            if (!Double.isFinite(value))
                throw new IllegalArgumentException("Invalid number");

            this.value = value;
            this.unit = unit;
        }

        // Convert this object → new unit (instance method)
        public QuantityLength convertTo(LengthUnit targetUnit) {
            if (targetUnit == null)
                throw new IllegalArgumentException("Target unit cannot be null");

            double base = unit.toBase(value);
            double converted = targetUnit.fromBase(base);

            return new QuantityLength(converted, targetUnit);
        }

        public double toBaseUnit() {
            return unit.toBase(value);
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null || getClass() != obj.getClass()) return false;

            QuantityLength other = (QuantityLength) obj;

            return Math.abs(this.toBaseUnit() - other.toBaseUnit()) < 1e-6;
        }

        @Override
        public String toString() {
            return value + " " + unit;
        }
    }

    // 🔥 STATIC API METHOD (MAIN UC5 FEATURE)
    public static double convert(double value, LengthUnit source, LengthUnit target) {

        if (source == null || target == null)
            throw new IllegalArgumentException("Units cannot be null");

        if (!Double.isFinite(value))
            throw new IllegalArgumentException("Invalid value");

        double base = source.toBase(value);     // → feet
        return target.fromBase(base);           // → target
    }

    // 🔹 METHOD OVERLOADING (as asked)
    public static void demonstrateLengthConversion(double value,
                                                   LengthUnit from,
                                                   LengthUnit to) {
        double result = convert(value, from, to);
        System.out.println(value + " " + from + " = " + result + " " + to);
    }

    public static void demonstrateLengthConversion(QuantityLength q,
                                                   LengthUnit to) {
        QuantityLength converted = q.convertTo(to);
        System.out.println(q + " = " + converted);
    }

    // MAIN METHOD (TESTING)
    public static void main(String[] args) {

        // Static conversion
        demonstrateLengthConversion(1.0, LengthUnit.FEET, LengthUnit.INCH);     // 12
        demonstrateLengthConversion(3.0, LengthUnit.YARD, LengthUnit.FEET);     // 9
        demonstrateLengthConversion(36.0, LengthUnit.INCH, LengthUnit.YARD);    // 1
        demonstrateLengthConversion(1.0, LengthUnit.CENTIMETER, LengthUnit.INCH);

        // Instance conversion
        QuantityLength q = new QuantityLength(2.0, LengthUnit.YARD);
        demonstrateLengthConversion(q, LengthUnit.FEET); // 6 feet

        // Equality check (still works)
        System.out.println(
                new QuantityLength(1.0, LengthUnit.YARD)
                        .equals(new QuantityLength(3.0, LengthUnit.FEET))
        ); // true
    }
}