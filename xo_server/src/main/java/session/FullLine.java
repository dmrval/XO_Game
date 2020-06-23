package session;

public class FullLine {
    private LineType lineType;
    private int position;

    public FullLine(LineType lineType, int position) {
        this.lineType = lineType;
        this.position = position;
    }

    public FullLine() {
        this.lineType = LineType.NO_FULL_LINES;
    }

    public LineType getLineType() {
        return lineType;
    }

    public int getPosition() {
        return position;
    }
}
