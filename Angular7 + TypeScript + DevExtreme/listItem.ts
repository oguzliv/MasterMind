export class ListItem {
    private prediction: number;
    private hint: string;
    constructor(prediction: number, hint: string) {
        this.prediction = prediction;
        this.hint = hint;
    }
    setPrediction(num: number): void {
        this.prediction = num;
    }
    setHint(str: string): void {
        this.hint = str;
    }
    getPrediction(): number {
        return this.prediction;
    }
    getHint(): string {
        return this.hint;
    }
    toString() {
        return 'Prediction: ' + this.prediction + ' Hint: ' + this.hint;
    }
}
