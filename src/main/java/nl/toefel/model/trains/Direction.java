package nl.toefel.model.trains;

public enum Direction {
    LEFT('<') {
        @Override
        public Direction opposite() {
            return RIGHT;
        }
    },
    RIGHT('>') {
        @Override
        public Direction opposite() {
            return LEFT;
        }
    }, TOP('^') {
        @Override
        public Direction opposite() {
            return BOTTOM;
        }
    }, BOTTOM('v') {
        @Override
        public Direction opposite() {
            return TOP;
        }
    };

    private final char directionSymbol;

    Direction(char directionSymbol) {
        this.directionSymbol = directionSymbol;
    }

    public char getDirectionSymbol() {
        return directionSymbol;
    }

    public abstract Direction opposite();
}
