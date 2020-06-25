package session;

public class FullLine {
    private LineType lineType;
    private int position;
    private Status winMark;


    public FullLine(LineType lineType, int position, Status winMark) {
        this.lineType = lineType;
        this.position = position;
        this.winMark = winMark;
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

    public Status getWinMark() {
        return winMark;
    }
}
