package com.webcheckers.model;

public class Move {

    private final Position start;
    private final Position end;

    public Move(Position start, Position end){
        this.start = start;
        this.end = end;
    }

    public Position getStart(){
        return start;
    }

    public Position getEnd() {
        return end;
    }

    public Move getFlippedMove() {
        Position newStart = new Position((7 - start.getRow()), (7 - start.getCell()));
        Position newEnd = new Position((7 - end.getRow()), (7 - end.getCell()));

        return new Move(newStart, newEnd);
    }
}
