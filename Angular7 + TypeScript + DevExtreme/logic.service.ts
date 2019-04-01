import { Injectable } from '@angular/core';
import { Hint } from './hint';
import { ListItem } from './listItem';
import { BehaviorSubject, Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
/**
 * This class provides main game logic for the game
 */
export class LogicService {

  /**
   * variable declarations
   */
  private allNumbers: number [];
  private playerPrediction: string;
  private computerPrediction: string;
  private computerSelection: string;
  private userTurn: boolean;
  private computerTurn: boolean;
  private gameStarted: boolean;
  private turn: number;
  private computerHintToHuman: Hint;
  private humanHintToComputer: Hint;
  private playerData: ListItem[];
  private computerData: ListItem[];

  /**
   * BehviourSubject declarations and initializitons
   */
  private turnSourcePlayer = new BehaviorSubject<boolean>(false);
  private turnSourceComputer = new BehaviorSubject<boolean>(false);
  private gameSource = new BehaviorSubject<boolean>(false);
  private computerPredictionStr = new BehaviorSubject<string>('');
  private playerPredictionSource = new BehaviorSubject<string>('');
  private computerHint = new BehaviorSubject<Hint>(null);
  private statusSource = new BehaviorSubject<string>('To start game, press start button');
  private turnSource = new BehaviorSubject<number>(0);
  private tableSource = new BehaviorSubject<number>(0);

  /**
   * Data stream initializations
   */
  currentPlayerTurn = this.turnSourcePlayer.asObservable();
  currentComputerTurn = this.turnSourceComputer.asObservable();
  currentGameStatus = this.gameSource.asObservable();
  currentComputerPrediction = this.computerPredictionStr.asObservable();
  currentPlayerPrediction = this.playerPredictionSource.asObservable();
  currentComputerHint = this.computerHint.asObservable();
  currentStatus = this.statusSource.asObservable();
  currentTurn = this.turnSource.asObservable();
  currentTable = this.tableSource.asObservable();

  /**
   * Constructor for logic service object
   */
  constructor() {
    this.allNumbers = this.possibleNumbers();
    this.playerPrediction = '';
    this.computerPrediction = '';
    this.computerSelection = this.computerSelects();
    this.userTurn = false;
    this.computerTurn = false;
    this.turn = 0;
    this.computerHintToHuman = new Hint(0, 0);
    this.humanHintToComputer = new Hint(0, 0);
  }
  /**
   * Update signals for data streams. For each data stream,
   * there has to be a update method. So below, proper data 
   * stream update methods provided.
   */

  changeTable(num: number): void {
    this.tableSource.next(num);
  }
  changeUserTurn(turn: boolean): void {
    this.turnSourcePlayer.next(turn);
  }
  changeComputerTurn(turn: boolean): void {
    this.turnSourceComputer.next(turn);
  }
  changeGameStarted(isStarted: boolean): void {
    this.gameSource.next(isStarted);
  }
  changeComputerPrediction(prediction: string): void {
    this.computerPredictionStr.next(prediction);
  }
  changePlayerPrediction(preediction: string): void {
    this.playerPredictionSource.next(preediction);
  }
  changeComputerHint(hint: Hint): void {
    this.computerHint.next(hint);
  }
  changeStatus(message: string): void {
    this.statusSource.next(message);
  }
  changeTurn(turn: number): void {
    this.turnSource.next(turn);
  }
  /**
   * GETTERS AND SETTERS
   */
  getPlayerData(): ListItem[] {
    return this.playerData;
  }
  getComputerData(): ListItem[] {
    return this.computerData;
  }
  setComputerData(list: ListItem[]) {
    this.computerData = list;
  }
  setPlayerData(list: ListItem[]) {
    this.playerData = list;
  }
  getHumanHintToComputer(): Hint {
    return this.humanHintToComputer;
  }
  getComputerHintToHuman(): Hint {
    return this.computerHintToHuman;
  }
  setComputerHintToHuman(hint: Hint): void {
    this.computerHintToHuman = hint;
  }
  setHumanHintToComputer(hint: Hint): void {
    this.humanHintToComputer = hint;
  }
  getTurn(): number {
    return this.turn;
  }
  setTurn(turn: number): void {
    this.turn = turn;
  }
  setUserTurn(turn: boolean): void {
    this.userTurn = turn;
  }
  setComputerTurn(turn: boolean): void {
    this.computerTurn = turn;
  }
  getGameStarted(): boolean {
    return this.gameStarted;
  }
  setGameStarted(start: boolean) {
    this.gameStarted = start;
  }
  setPlayerPrediction(prediction: string): void {
    this.playerPrediction = prediction;
  }
  getPlayerPrediction(): string {
    return this.playerPrediction;
  }
  getComputerSelection(): string {
    return this.computerSelection;
  }
  getComputerPrediction(): string {
    return this.computerPrediction;
  }
  setComputerPrediction(prediction: string): void {
    this.computerPrediction = prediction;
  }
  getAllNumbers(): number[] {
    return this.allNumbers;
  }

  /**
   * Checks if a number has same number any of its digits. returns
   * true if any number is same,else false.
   * @param num an integer to be checked.
   */
  hasSameDigits(num: number): boolean {
    let myNum: string;
    myNum = num.toString();
    if (myNum.charAt(0) !== myNum.charAt(1) && myNum.charAt(0) !== myNum.charAt(2) && myNum.charAt(0) !== myNum.charAt(3) ) {
        if (myNum.charAt(1) !== myNum.charAt(2) && myNum.charAt(1) !== myNum.charAt(3)) {
            if (myNum.charAt(2) !== myNum.charAt(3)) {
                return true;
            }
            return false;
        }
        return false;
    }
    return false;
  }
  /**
   * Generates possible number's list and returns 
   * generated list
   */
  possibleNumbers(): number[]  {
    let list: number[];
    list = new Array();
    for (let i = 1000 ; i < 10000; i++) {
        if (this.hasSameDigits(i)) {
            list.push(i);
        }
    }
    return list;
  }
  /**
   * by using computer selection method, sets
   * computer prediction.
   */
  computerPredicts(): void {
    const prediction = this.computerSelects();
    this.setComputerPrediction(prediction);
  }
  /**
   * randomly selects a number from possible number
   * list and sets computer selection.
   */
  computerSelects(): string {
    let range: number;
    range = this.allNumbers.length;
    let index: number;
    index = Math.floor(Math.random() * range);
    let selection: number;
    selection = this.allNumbers[index];
    const strSelection = selection.toString();
    return strSelection;
  }
  /**
   * Compares two integers and returns a hint object as a result of comparison
   * @param prediction an operand to be compared as integer
   * @param selection an operand to be compared as integer
   */
  answerHint(prediction: number, selection: number): Hint {
    let pos = 0;
    let neg = 0;

    const strPrediction: string = prediction.toString();
    const strSelection: string = selection.toString();

    for (let i = 0 ; i < 4 ; i++) {
      let position = strPrediction.indexOf(strSelection.charAt(i));
      if (position >= 0) {
        if( position === i) {
          pos++;
        } else {
          neg++;
        }
      }
    }
    const hint: Hint =  new Hint(pos, neg);
    return hint;
  }

  /**
   * Prunes (updates) possible number list according to hint
   * parameter
   * @param hint a hint object to be used for pruning
   */
  computerPrunes(hint: Hint): void {
    const pos = hint.getPositive();
    const neg = hint.getNegative();

    console.log('heuristics : POS : ' + pos + ' NEG : ' + neg);
    console.log('Computer prediction : ' + this.getComputerPrediction());

    for (let i = 0; i < this.allNumbers.length; i++) {
      const pruneHint: Hint = this.answerHint(parseInt(this.computerPrediction, 0), this.allNumbers[i]);
      const p: number = pruneHint.getPositive();
      const n: number = pruneHint.getNegative();

      if ((pos !== p) || (neg !== n)) {
        this.allNumbers.splice(i--, 1);
      }
    }
  }
}
