export class Hint {
    private positive: number;
    private negative: number;

    constructor(pos: number, neg: number) {
        this.positive = pos;
        this.negative = neg;
    }
    getPositive(): number {
        return this.positive;
    }
    getNegative(): number {
        return this.negative;
    }
    setPositive(pos: number): void {
        this.positive = pos;
    }
    setNegative(neg: number): void {
        this.negative = neg;
    }
    toString(): string {
        return 'POS: ' + this.positive + ' NEG: ' + this.negative;
    }
}
