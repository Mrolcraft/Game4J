package fr.perroquets.game4j;

public enum Dimensions {

    DEUX_PAR_DEUX(4,2,1), TROIS_PAR_TROIS(9,3, 2), QUATRE_PAR_QUATRE(16,4, 3), CINQ_PAR_CINQ(25,5,4), SIX_PAR_SIX(36,6,5), SEPT_PAR_SEPT(49,7,6);

    private int area;
    private int size;
    private int max_loop;

    Dimensions(int area, int size, int max_loop) {
        this.area = area;
        this.size = size;
        this.max_loop = max_loop;
    }

    public int getArea() {
        return area;
    }

    public int getMax_loop() {
        return max_loop;
    }

    public int getSize() {
        return size;
    }

    public static Dimensions getFromArea(int area) {
        for (Dimensions dimensions:
             values()) {
            if(dimensions.getArea() == area) return dimensions;
        }
        return null;
    }
}
