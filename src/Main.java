public class Main {

    // 🔹 Enum for Units
    enum LengthUnit {
        FEET(1.0),
        INCHES(1.0 / 12.0),
        YARDS(3.0),
        CENTIMETERS(1.0 / 30.48);

        private final double toFeetFactor;

        LengthUnit(double toFeetFactor) {
            this.toFeetFactor = toFeetFactor;
        }

        // Convert to base unit (feet)
        public double toBase(double value) {
            return value * toFeetFactor;
        }

        // Convert from base unit (feet)
        public double fromBase(double baseValue) {
            return baseValue / toFeetFactor;
        }
    }

    // 🔹 QuantityLength Class
    static class QuantityLength {
        private final double value;
        private final LengthUnit unit;

        public QuantityLength(double value, LengthUnit unit) {
            if (unit == null || !Double.isFinite(value)) {
                throw new IllegalArgumentException("Invalid value or unit");
            }
            this.value = value;
            this.unit = unit;
        }

        public double getValue() {
            return value;
        }

        public LengthUnit getUnit() {
            return unit;
        }

        // 🔥 UC6: Addition Method
        public QuantityLength add(QuantityLength other) {
            if (other == null) {
                throw new IllegalArgumentException("Other length cannot be null");
            }

            // Convert both to base unit (feet)
            double thisInFeet = this.unit.toBase(this.value);
            double otherInFeet = other.unit.toBase(other.value);

            // Add
            double sumInFeet = thisInFeet + otherInFeet;

            // Convert back to unit of first operand
            double resultValue = this.unit.fromBase(sumInFeet);

            return new QuantityLength(resultValue, this.unit);
        }

        @Override
        public String toString() {
            return "Quantity(" + value + ", " + unit + ")";
        }
    }

    // 🔹 Main Method (Test Examples)
    public static void main(String[] args) {

        // Same unit
        System.out.println(
                new QuantityLength(1.0, LengthUnit.FEET)
                        .add(new QuantityLength(2.0, LengthUnit.FEET))
        ); // Quantity(3.0, FEET)

        // Cross unit (feet + inches)
        System.out.println(
                new QuantityLength(1.0, LengthUnit.FEET)
                        .add(new QuantityLength(12.0, LengthUnit.INCHES))
        ); // Quantity(2.0, FEET)

        // Reverse order (inches + feet)
        System.out.println(
                new QuantityLength(12.0, LengthUnit.INCHES)
                        .add(new QuantityLength(1.0, LengthUnit.FEET))
        ); // Quantity(24.0, INCHES)

        // Yards + feet
        System.out.println(
                new QuantityLength(1.0, LengthUnit.YARDS)
                        .add(new QuantityLength(3.0, LengthUnit.FEET))
        ); // Quantity(2.0, YARDS)

        // Centimeters + inches
        System.out.println(
                new QuantityLength(2.54, LengthUnit.CENTIMETERS)
                        .add(new QuantityLength(1.0, LengthUnit.INCHES))
        ); // ~5.08 cm

        // Zero case
        System.out.println(
                new QuantityLength(5.0, LengthUnit.FEET)
                        .add(new QuantityLength(0.0, LengthUnit.INCHES))
        ); // Quantity(5.0, FEET)

        // Negative case
        System.out.println(
                new QuantityLength(5.0, LengthUnit.FEET)
                        .add(new QuantityLength(-2.0, LengthUnit.FEET))
        ); // Quantity(3.0, FEET)
    }
}